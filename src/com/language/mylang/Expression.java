package com.language.mylang;

import java.util.List;

abstract class Expression extends ASTNode {


    static class binary extends Expression {
        final Expression left;
        final Expression right;
        final Token operator;
        binary(Expression left, Token operator, Expression right){
            this.left = left;
            this.right = right;
            this.operator = operator;

        }


        @Override
        <T> T accept(ASTNode.Visitor<T> visitor) {
            return visitor.VisitBin(this);
        }
    }


    static class Grouping extends Expression {

        final Expression expression;

        Grouping(Expression expression){
            this.expression = expression;
        }

        @Override
        <T> T accept(ASTNode.Visitor<T> visitor) {
            return visitor.VisitGrouping(this);
        }
    }


    static class Literal extends Expression {

        final Object value;


        Literal(Object value){
            this.value = value;
        }

        @Override
        <T> T accept(ASTNode.Visitor<T> visitor) {
            return visitor.VisitLiteral(this);
        }
    }


    static class Unary extends Expression {

        final Token operator;
        final Expression right;

        Unary(Token operator, Expression right){
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(ASTNode.Visitor<T> visitor) {
            return visitor.VisitUnary(this);
        }
    }

    static class VarExpression extends Expression{


        final Token name;

        VarExpression(Token name){
            this.name = name;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.VisitVarExpression(this);
        }
    }
    static class Assign extends Expression{

        final Token name;
        final Expression value;

        Assign(Token name,Expression value){
            this.name = name;
            this.value = value;

        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.VisitAssignExpression(this);
        }
    }

    static class Logical extends  Expression{

        final Expression left;
        final Token operator;
        final Expression right;


        Logical(Expression left, Token operator, Expression right){
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.VisitLogicalExpression(this);
        }
    }

    static class Call extends  Expression{

        final Expression callee;
        final Token par;
        final List<Expression> arguments;

        Call(Expression callee, Token par, List<Expression> arguments){
            this.arguments=arguments;
            this.par = par;
            this.callee = callee;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.VisitCallExpression(this);
        }
    }
}



