package com.language.mylang;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.language.mylang.TokenType.*;

public class MyScanner {

    final String source;
    final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    MyScanner(String source){
        this.source = source;


    }

    List<Token> scanTokens(){

        while(!isEnd()){

            start = current;

            scanToken();

        }


        tokens.add(new Token(EOF,"", null,line));
        return tokens;
    }

    private void scanToken() {
        char c = source.charAt(current++);

        switch(c){
            case '"':  handleStrings(); break;
            case '\'': handleChars(); break;
            case '(': addToken(OPEN_PARENTHESES,c);break;
            case ')': addToken(CLOSE_PARENTASHES,c);break;
            case '[': addToken(OPEN_BRACKET,c);break;
            case ']': addToken(CLOSE_BRACKET,c);break;
            case '{': addToken(OPEN_BRACE,c);break;
            case '}': addToken(CLOSE_BRACE,c);break;
            case '+': addToken(ADD,c);break;
            case '-': addToken(SUBTRACT,c);break;
            case ';': addToken(SEMICOLON,c);break;
            case ':': addToken(COLON,c);break;
            case '*': addToken(MULTIPLY,c);break;
            case ',': addToken(COMMA,c); break;
            case '=': addToken(match('=') ?   EQUAL_EQUAL : ASSIGN,c);break;
            case '>': addToken(match('=') ?  EQUAL_GREATER : GREATER ,c);break;
            case '<': addToken(match('=') ?  EQUAL_LESSSER : LESSER,c);break;
            case '!': addToken(match('=') ?  NOT_EQUAL : NOT,c); break;
            case '|': addToken(match('|') ? OR : BITOR,c); break;
            case '&': addToken(match('&') ? AND : BITAND,c); break;


            case '/':
                if (match('/')){

                    while(peek() != '\n' && !isEnd()){
                        current++;

                    }
                }
                else{
                    addToken(DIVIDE,c);
                }
            case '\t':
                // Ignore whitespace.
                break;
            case ' ', '\r': break; // ignore empty space and \r


            case '\n':
                addToken(NEWLINE,c);
                line++;
                break;
            default:
                if(Character.isDigit(c)){
                    handleNumber();
                }
                else if(Character.isAlphabetic(c)){
                    identifier();
                }
                else{

                    System.out.println("unexpected char!: " + c);
                }
                //ERROR stuff!


        }

    }



    private void identifier() {

        while(Character.isLetterOrDigit(peek())) current++;

        String word = source.substring(start,current);

        TokenType type = reserved_words.get(word);

        if(type == null){
            type = IDENTIFIER;
        }
        addToken(type,word);

    }

    private void handleNumber() {
        while (Character.isDigit(peek())) current++;

        if (peek() == '.' && Character.isDigit(peekNext())){
            current++; // step past the dot
            while (Character.isDigit(peek())) current++;
            addToken(FLOAT,Double.parseDouble(source.substring(start,current)));

        }
        else{
            addToken(INTEGER,Integer.parseInt(source.substring(start,current)));
        }
    }

    private void handleStrings() {
        while( peek() != '"' && !isEnd()){
            current++;
        }

        String value = source.substring(start+1,current);
        addToken(STRING,value);
        current++; // step past the "



    }
    private void handleChars() {
        addToken(CHAR,peek());
        match('\'');
        current++;
        current++;
    }

    private void addToken(TokenType type, Object literal) {
        tokens.add(new Token(type,source.substring(start,current),literal,line));
    }



    private boolean match(char expected){
        if(isEnd()){
            return false;
        }

        if (expected != peek()){
            return false;
        }

        current++;

        return true;
    }

    private char peek(){
        if(isEnd())return '\0';
        return source.charAt(current);
    }
    private char peekNext(){
        if(current+1 >= source.length())return '\0';
        return source.charAt(current+1);
    }

    private boolean isEnd() {
        return current >= source.length();
    }

    private static final Map<String,TokenType> reserved_words;

    static {
        reserved_words = new HashMap<>();

        reserved_words.put("main", MAIN);
        reserved_words.put("class", CLASS);
        reserved_words.put("if", IF);
        reserved_words.put("else", ELSE);
        reserved_words.put("return", RETURN);
        reserved_words.put("while", WHILE);
        reserved_words.put("for", FOR);
        reserved_words.put("true", TRUE);
        reserved_words.put("false", FALSE);
        reserved_words.put("print", PRINT);
        reserved_words.put("var",VAR);
        reserved_words.put("List",LIST);
        reserved_words.put("fn", FUNCTION);


    }


}
