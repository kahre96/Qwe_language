package com.language.mylang;


import java.util.List;
import java.util.Optional;

abstract class ASTNode {
    interface Visitor<R>{
        R visitBLockStatement(Block stmt);
        R visitClassStatement(Class stmt);
        R visitExpressionStatement(Expr stmt);
        R visitPrintStatement(Print stmt);
        R visitIfStatement(If stmt);
        R visitReturnStatement(Return stmt);
        R visitWhileStatement(While stmt);
        R visitVarStatement(Var stmt);
        R visitFunctionStatement(Function stmt);
        R VisitVarExpression(Expression.VarExpression expr);
        R VisitBin(Expression.binary expr);
        R VisitUnary(Expression.Unary expr);
        R VisitGrouping(Expression.Grouping expr);
        R VisitLiteral(Expression.Literal expr);
        R VisitAssignExpression(Expression.Assign expr);
        R VisitLogicalExpression(Expression.Logical expr);
        R VisitCallExpression(Expression.Call expr);
        R VisitListExpression(Expression.qweList expr);





    }

    static class Block extends ASTNode {

        final List<ASTNode> statements;

        Block(List<ASTNode> statements){
            this.statements = statements;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBLockStatement(this);
        }
    }

    static class Class extends ASTNode {

        @Override
        <R> R accept(Visitor<R> visitor) {
            return null;
        }
    }
    static class Expr extends ASTNode {

        final Expression expression;

        Expr(Expression expression){
            this.expression = expression;
        }
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStatement(this);
        }
    }
    static class Function extends ASTNode {

        final Token name;
        final List<Token> parameters;

        final List<ASTNode> body;

        Function(Token name, List<Token> parameters, List<ASTNode> body){

            this.body=body;
            this.parameters = parameters;
            this.name = name;
        }


        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionStatement(this);
        }
    }
    static class If extends ASTNode {

        final Expression condition;
        final ASTNode thenBranch;
        final Optional<ASTNode>  elseBranch;

        If(Expression condition, ASTNode thenBranch, ASTNode elseBranch){
            this.condition = condition;
            this.elseBranch = Optional.ofNullable(elseBranch);
            this.thenBranch = thenBranch;
        }
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitIfStatement(this);
        }
    }
    static class Print extends ASTNode {

        final Expression expression;
        Print(Expression expression){
            this.expression = expression;

        }
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrintStatement(this);
        }
    }
    static class Return extends ASTNode {

        final Token keyword;
        final Expression value;

        Return(Token keyword, Expression value){
            this.keyword = keyword;
            this.value = value;
        }



        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitReturnStatement(this);
        }
    }
    static class Var extends ASTNode {

        final Token name;

        final Expression initializer;

        Var(Token name, Expression initializer){
            this.name = name;
            this.initializer = initializer;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitVarStatement(this);
        }
    }

    static class While extends ASTNode {

        final Expression condition;
        final ASTNode statement;


        While(Expression condition, ASTNode statement){
            this.condition = condition;
            this.statement = statement;
        }


        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitWhileStatement(this);
        }
    }


    abstract <R> R accept(Visitor<R> visitor);
}

