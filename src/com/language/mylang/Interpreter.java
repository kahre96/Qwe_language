package com.language.mylang;



import java.util.List;
import java.util.Optional;



class Interpreter implements Expression.Visitor<Object> {


    private Environment environment = new Environment();

    void interpret (List<ASTNode> ASTNodes){
        try{
            for(ASTNode ASTNode : ASTNodes){
                execute(ASTNode);
            }
        }
        catch (RuntimeError error){
            Main.runTimeError(error);
        }
    }

    private  void execute(ASTNode ASTNode){
        ASTNode.accept(this);
    }

    @Override
    public Object VisitBin(Expression.binary expr) {

        switch (expr.operator.type) {
            case SUBTRACT -> {


                return Subtract(expr);
            }
            case DIVIDE -> {
                return Divide(expr);
            }
            case MULTIPLY -> {
                return  Multiply(expr);

            }
            case ADD -> {
                return Add(expr);

            }
            case GREATER -> {
                return GreaterComp(expr);
            }
            case EQUAL_GREATER -> {
                return GreaterEqualComp(expr);
            }
            case LESSER -> {
                return LesserComp(expr);
            }
            case EQUAL_LESSSER -> {
                return LesserEqualComp(expr);
            }
            case EQUAL_EQUAL -> {
                return EqualEqual(expr);
            }
            case NOT_EQUAL -> {
                return NotEqual(expr);
            }

        }


        return null;

    }

    private Object Multiply(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return ((int)left * (int)right);
            if(right instanceof  Double) return (int)left * (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left * (double)right;
            if(right instanceof  Integer) return (double)left * (int)right;
        }


        throw new RuntimeError(expr.operator,"can only multiply integers and doubles");

    }

    private Object Divide(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left / (int)right;
            if(right instanceof  Double) return (int)left / (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left / (double)right;
            if(right instanceof  Integer) return (double)left / (int)right;
        }

        throw new RuntimeError(expr.operator,"can only multiply integers and doubles");
    }
    private Object Add(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left + (int)right;
            if(right instanceof  Double) return (int)left + (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left + (double)right;
            if(right instanceof  Integer) return (double)left + (int)right;
        }
        throw new RuntimeError(expr.operator,"can only add numbers or strings!");
    }
    private Object Subtract(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left - (int)right;
            if(right instanceof  Double) return (int)left - (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left - (double)right;
            if(right instanceof  Integer) return (double)left - (int)right;
        }

        throw new RuntimeError(expr.operator,"can only multiply integers and doubles");
    }
    private Object GreaterComp(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left > (int)right;
            if(right instanceof  Double) return (int)left > (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left > (double)right;
            if(right instanceof  Integer) return (double)left > (int)right;
        }

        throw new RuntimeError(expr.operator,"can only compare integers and doubles");




    }
    private Object LesserComp(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left > (int)right;
            if(right instanceof  Double) return (int)left > (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left > (double)right;
            if(right instanceof  Integer) return (double)left > (int)right;
        }

        throw new RuntimeError(expr.operator,"can only compare integers and doubles");

    }
    private Object LesserEqualComp(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left <= (int)right;
            if(right instanceof  Double) return (int)left <= (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left <= (double)right;
            if(right instanceof  Integer) return (double)left <= (int)right;
        }

        throw new RuntimeError(expr.operator,"can only compare integers and doubles");

    }
    private Object GreaterEqualComp(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left >= (int)right;
            if(right instanceof  Double) return (int)left >= (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left >= (double)right;
            if(right instanceof  Integer) return (double)left >= (int)right;
        }

        throw new RuntimeError(expr.operator,"can only compare integers and doubles");

    }
    private Object EqualEqual(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left == (int)right;
            if(right instanceof  Double) return (int)left == (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left == (double)right;
            if(right instanceof  Integer) return (double)left == (int)right;
        }

        throw new RuntimeError(expr.operator,"can only compare integers and doubles");

    }
    private Object NotEqual(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if(left instanceof  Integer){
            if(right instanceof Integer) return (int)left != (int)right;
            if(right instanceof  Double) return (int)left != (double)right;
        }
        if(left instanceof Double){
            if(right instanceof  Double) return (double)left != (double)right;
            if(right instanceof  Integer) return (double)left != (int)right;
        }

        throw new RuntimeError(expr.operator,"can only compare integers and doubles");

    }

    private List<Object>  BinaryHelper(Expression.binary expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);


        Number left2 = checkNumber(left);
        Number right2 = checkNumber(right);

        //Number ans = left2+right2;
        return List.of(left,right);


    }

    private Number checkNumber(Object num) {
        if(num instanceof Integer){
            return (int)num;
        }
        return (double)num;
    }

    @Override
    public Object VisitUnary(Expression.Unary expr) {
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case SUBTRACT ->{
                if(right instanceof Integer){
                    return -(int)right;
                }
                return -(double) right;
            }
            case NOT ->{
                return !(boolean) right;
            }
            default -> {
                return null;
            }
        }

    }

    @Override
    public Object VisitGrouping(Expression.Grouping expr) {
        return evaluate(expr.expression);
    }

    private Object evaluate(ASTNode expression) {
        return expression.accept(this);
    }

    @Override
    public Object VisitLiteral(Expression.Literal expr) {
        return expr.value;
    }

    @Override
    public Object VisitAssignExpression(Expression.Assign expr) {
        Object value = evaluate(expr.value);
        environment.assign(expr.name,value);
        return null;
    }

    @Override
    public Object VisitLogicalExpression(Expression.Logical expr) {
        Object left = evaluate(expr.left);

        if (expr.operator.type == TokenType.OR) {
            if (isTruth(left)) return left;
        } else {
            if (!isTruth(left)) return left;
        }

        return evaluate(expr.right);

    }

    @Override
    public Object VisitCallExpression(Expression.Call expr) {
        return ;
    }

    @Override
    public Void visitBLockStatement(ASTNode.Block stmt) {
        executeBlock(stmt.statements, new Environment(environment));
        return null;
    }

    private void executeBlock(List<ASTNode> statements, Environment environment) {
        Environment previous = this.environment;

        // save global env to previous


        try{
            // makes global env fresh
            this.environment = environment;

            for(ASTNode statement : statements){
                execute(statement);
            }

        }finally {
            //restore the global env once done
            this.environment = previous;
        }
    }

    @Override
    public Void visitClassStatement(ASTNode.Class stmt) {
        return null;
    }

    @Override
    public Void visitExpressionStatement(ASTNode.Expr stmt) {
        evaluate(stmt.expression);
        return null;
    }

    @Override
    public Void visitPrintStatement(ASTNode.Print stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(value.toString());
        return null;
    }

    @Override
    public Void visitIfStatement(ASTNode.If stmt) {
        if(isTruth(evaluate(stmt.condition))){
            execute(stmt.thenBranch);
        }
        else stmt.elseBranch.ifPresent(this::execute);
        return null;
    }

    @Override
    public Void visitReturnStatement(ASTNode.Return stmt) {
        return null;
    }

    @Override
    public Void visitWhileStatement(ASTNode.While stmt) {
        while(isTruth(evaluate(stmt.condition))){
            execute(stmt.statement);
        }

        return null;
    }

    @Override
    public Void visitVarStatement(ASTNode.Var stmt) {
        Object value = null;

        if(stmt.initializer != null){
            value = evaluate(stmt.initializer);
        }
        environment.define(stmt.name.lexeme,value);
        return null;
    }

    @Override
    public Object VisitVarExpression(Expression.VarExpression expr) {
        return environment.get(expr.name);
    }



    private boolean isTruth(Object object){
        //what is true?
        if (object == null) return  false;
        if(object instanceof String){
            if(object.equals("cake")) return false;
        }
        if( object instanceof  Boolean) return (boolean) object;

        return true;
    }

}
