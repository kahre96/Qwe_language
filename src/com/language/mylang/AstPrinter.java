package com.language.mylang;
import java.lang.System;
import java.util.List;

class AstPrinter implements ASTNode.Visitor<Void> {
    static int depth = 0;


   void print(List<ASTNode> nodes) {


        for(ASTNode node : nodes){
            node.accept(this);

        }
    }


    @Override
    public Void visitBLockStatement(ASTNode.Block stmt) {
        depth++;
        pad(depth);
        System.out.println("BLock");
        for(ASTNode statement : stmt.statements){
            statement.accept(this);
        }
        depth--;



        return null;
    }

    @Override
    public Void visitClassStatement(ASTNode.Class stmt) {
        return null;
    }

    @Override
    public Void visitExpressionStatement(ASTNode.Expr stmt) {
        depth++;
        System.out.println("Expressionstatement: ");
        stmt.expression.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visitPrintStatement(ASTNode.Print stmt) {
        depth++;
        pad(depth);
        System.out.println("Print: ");
        stmt.expression.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visitIfStatement(ASTNode.If stmt) {
        depth++;
        pad(depth);
        System.out.println("if:");
        stmt.condition.accept(this);
        stmt.thenBranch.accept(this);
        pad(depth);
        System.out.println("else:");
        stmt.elseBranch.ifPresent(astNode -> astNode.accept(this));
        depth--;
        return null;
    }

    @Override
    public Void visitReturnStatement(ASTNode.Return stmt) {
        System.out.println("return");
        return null;
    }

    @Override
    public Void visitWhileStatement(ASTNode.While stmt) {
        depth++;
        pad(depth);
        System.out.println("while:");
        stmt.condition.accept(this);
        stmt.statement.accept(this);
        depth--;

        return null;
    }

    @Override
    public Void visitVarStatement(ASTNode.Var stmt) {
        depth++;
        pad(depth);
        System.out.println("var " + stmt.name.lexeme);
        stmt.initializer.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void VisitVarExpression(Expression.VarExpression expr) {
        depth++;
        pad(depth);
        System.out.println(expr.name.lexeme);
        depth--;
        return null;
    }

    @Override
    public Void VisitBin(Expression.binary expr) {
        depth++;

        expr.left.accept(this);
        pad(depth);
        System.out.println(expr.operator.lexeme);

        expr.right.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void VisitUnary(Expression.Unary expr) {
        depth ++;
        pad(depth);
        System.out.print(expr.operator.lexeme);
        expr.right.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void VisitGrouping(Expression.Grouping expr) {
        depth++;
        pad(depth);
        System.out.println("Grouping: ");
        expr.expression.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void VisitLiteral(Expression.Literal expr) {
        depth++;
        pad(depth);
        System.out.println(expr.value.toString());
        depth--;
        return null;
    }

    @Override
    public Void VisitAssignExpression(Expression.Assign expr) {

        return null;
    }

    @Override
    public Void VisitLogicalExpression(Expression.Logical expr) {
        depth++;


        expr.left.accept(this);
        pad(depth);
        System.out.println(expr.operator.lexeme);
        expr.right.accept(this);
        depth--;
        return null;
    }


    private void pad(int depth){
        for(int i = 0; i<depth;i++){
            System.out.print("   ");
        }
    }

}
