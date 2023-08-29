package com.language.mylang;

import java.util.ArrayList;
import java.util.List;

import static com.language.mylang.TokenType.*;

public class Parser {
    private static class ParseError extends RuntimeException {}
    private final List<Token> tokens;
    private int current =0;

    Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    List<ASTNode> parse(){
        List<ASTNode> ASTNodes = new ArrayList<>();
        while(!isAtEnd()){
            ASTNodes.add(Declaration());



        }

        return ASTNodes;
    }
    private ASTNode Declaration(){
        try{
            if(match(VAR)) return varDeclaration();

            return statement();
        }catch( ParseError error){
            synchronize();
            return null;
        }


    }

    private ASTNode varDeclaration() {
        Token name = consume(IDENTIFIER, "Expects a variable name");

        Expression initializer = null;
        if(match(ASSIGN)){
            initializer = expression();
        }
        if(!isAtEnd()){
            consume(NEWLINE,"Expect NEWLINE after expression");
        }
        return new ASTNode.Var(name,initializer);

    }

    private ASTNode statement() {
        if(match(PRINT)) return printStatement();
        if(match(IF)) return ifStatement();
        if(match(WHILE)) return whileStatement();

        if(match(OPEN_BRACE)){
            if(match(NEWLINE)){
                //consume(NEWLINE,"error with NEWLINE at start of block?"); fixed?
                return new ASTNode.Block(block());
            }
            throw new RuntimeError(tokens.get(current),"error with opening brace");

        }
        return expressionStatement();
    }

    private ASTNode whileStatement() {
        Expression condition = expression();
        consume(COLON, "expecting : after the while condition");

        match(NEWLINE);
        ASTNode statement = statement();
        return new ASTNode.While(condition,statement);
    }

    private ASTNode ifStatement() {
        Expression condition = expression();
        consume(COLON, "expecting : after the if condition");

        match(NEWLINE);

        ASTNode thenBranch = statement();

        match(NEWLINE);
        ASTNode elseBranch = null;
        if(match(ELSE)){
            match(NEWLINE);
            elseBranch = statement();
        }

        return new ASTNode.If(condition,thenBranch,elseBranch);

    }

    private List<ASTNode> block() {
        List<ASTNode> statements = new ArrayList<>();

        while(!check(CLOSE_BRACE) && !isAtEnd()){
            statements.add(Declaration());
        }
        consume(CLOSE_BRACE, "expect a } to close block");
        if(!isAtEnd()){
            consume(NEWLINE, "newline af");
        }

        return statements;
    }

    private ASTNode expressionStatement() {
        Expression expr = expression();


        if(!isAtEnd()){
            consume(NEWLINE,"Expect newline after expression");
        }

        return new ASTNode.Expr(expr);
    }

    private ASTNode printStatement(){
        Expression expr = expression();

        if(!isAtEnd()){
            consume(NEWLINE,"Expect newline after expression");
        }
        return new ASTNode.Print(expr);
    }



    private boolean match(TokenType... types){
        for(TokenType type : types){
            if(check(type)){
                advance();
                return true;
            }
        }
        return false;

    }

    private  boolean isAtEnd(){
        return peek().type == EOF;
    }

    private boolean check(TokenType type) {
        if(isAtEnd()) return false;

        return peek().type == type;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous(){
        return tokens.get(current-1);
    }

    private Expression expression(){
        return assignment();


    }

    private Expression assignment(){
        Expression expression = or();

        if(match(ASSIGN)){
            Token equals = previous();
            Expression value = assignment();

            if(expression instanceof Expression.VarExpression){
                Token name = ((Expression.VarExpression) expression).name;
                return new Expression.Assign(name,value);
            }
            error(equals,"Invalid assign");
        }
        return expression;

    }

    private Expression or(){
        Expression expression =  and();

        if(match(OR)){
            Token operator = previous();

            Expression right = and();
            expression = new Expression.Logical(expression,operator,right);
        }

        return expression;
    }

    private Expression and(){
        Expression expression = equality();

        if(match(AND)){
            Token operator = previous();
            Expression right = equality();
            expression = new Expression.Logical(expression,operator,right);

        }
        return expression;
    }


    private Expression equality() {
        Expression expression = comparison();

        while(match(NOT_EQUAL,EQUAL_EQUAL)){
            Token operator = previous();

            Expression right = comparison();
            expression = new Expression.binary(expression,operator,right);


        }
        return expression;
    }

    private Expression comparison() {
        Expression expression = term();

        while(match(GREATER,EQUAL_GREATER,LESSER,EQUAL_LESSSER)){
            Token operator =  previous();
            Expression right = term();
            expression = new Expression.binary(expression,operator,right);

        }
        return expression;
    }

    private Expression term() {
        Expression expression = factor();

        while(match(ADD,SUBTRACT)){
            Token operator = previous();
            Expression right = factor();
            expression = new Expression.binary(expression,operator,right);

        }
        return expression;

    }

    private Expression factor() {
        Expression expression = unary();

        while(match(DIVIDE,MULTIPLY)){
            Token operator = previous();
            Expression right = unary();
            expression = new Expression.binary(expression,operator,right);
        }
        return expression;
    }

    private Expression unary() {
        if(match(NOT,SUBTRACT)){
            Token operator = previous();
            return new Expression.Unary(operator,unary());

        }
        return functionCall();
    }

    private Expression functionCall(){
        Expression expression = primary();

        while(true){
            if(match(OPEN_PARENTHESES)){
                expression = parseCall(expression);
            }
            else{
                break;
            }
        }


        return expression;
    }

    private Expression parseCall(Expression call){
        List<Expression> arguments = new ArrayList<>();

        if(check(OPEN_PARENTHESES)){
            do{
                arguments.add(expression());
            } while(match(COMMA));
        }

        Token paren = consume(CLOSE_PARENTASHES,"Expct ) after arguments");

        return new Expression.Call(call,paren,arguments);
    }

    private Expression primary() {
        if(match(FALSE)) return new Expression.Literal(false);
        if(match(TRUE)) return new Expression.Literal(true);
        if(match(NULL)) return new Expression.Literal(null);

        if(match(FLOAT,INTEGER,STRING)){
            return new Expression.Literal(previous().literal);
        }

        if(match(IDENTIFIER)){
            return new Expression.VarExpression(previous());
        }

        if(match(OPEN_PARENTHESES)){
            Expression expression = expression();

            consume(CLOSE_PARENTASHES,"Expected ) after expression");
            return new Expression.Grouping(expression);
        }
        throw error(peek(),"Expected an expression?");

    }

    private Token consume(TokenType tokenType, String message) {
        if(check(tokenType)) return advance();

        else{
            throw error(peek(),message);
        }



    }

    private ParseError error(Token token, String message){
        Main.error(token,message);
        return new ParseError();

    }

    private Token advance() {
        if(!isAtEnd()) current++;

        return previous();
    }

    private void synchronize(){
        advance();

        while(!isAtEnd()){


            if(previous().type == NEWLINE) return;

            switch (peek().type) {
                case IF -> {
                }
                case CLASS -> {
                }
                case RETURN -> {
                    return;
                }
            }

            advance();
        }


    }

}
