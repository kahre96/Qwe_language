package com.language.mylang;


enum TokenType{
    OPEN_PARENTHESES,   //(
    CLOSE_PARENTASHES,  //)
    OPEN_BRACE,     //{
    CLOSE_BRACE,  //}
    OPEN_BRACKET,   //[
    CLOSE_BRACKET,  // ]

    ASSIGN, //=
    GREATER, //>
    LESSER,     //<
    EQUAL_EQUAL, //  ==
    EQUAL_GREATER, // >=
    EQUAL_LESSSER, // <=

    NOT_EQUAL,   // !=

    //BINARY OPERATORS
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    AND,
    OR,
    XOR,
    MOD,
    NOT,    //!
    BITOR,
    BITAND,
    BITXOR,
    COMMA, //,


    //types
    STRING,
    INTEGER,
    FLOAT,
    BOOL,
    LIST,
    CHAR,


    //RESERVED WORDS
    MAIN,
    CLASS,
    IF,
    ELSE,
    RETURN,
    WHILE,
    FOR,
    TRUE,
    FALSE,
    NULL,

    //Statements
    PRINT,
    BLOCK,
    FUNCTION,
    VAR,



    // special
    IDENTIFIER,
    SEMICOLON,
    COLON,
    NEWLINE,
    EOF




}
public class Token {
    final TokenType type;
    final String lexeme;

    final Object literal;

    final int line;




    Token(TokenType type, String lexeme, Object literal, int line){
        this.type=type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    void print_literal(){
        System.out.println("literal: "+literal);
    }
    void print_type(){
        System.out.println("type: "+ type);
    }


}
