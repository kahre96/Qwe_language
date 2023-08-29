package com.language.mylang;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Main {
    private static final Interpreter interpeter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;
    public static void main(String[] args) {


        /*System.out.println(System.getProperty("user.dir"));
        try {
            runfile("src/vartests.qwe");
        }catch(IOException e){
            throw new RuntimeException(e);
        }*/

        if(args.length > 1){
            System.out.println("To many args?");
        }
        else if(args.length == 1) {
            try {
                runfile(args[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                runInteractive();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }




    }

    private static void runInteractive() throws  IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(input);

        for(;;){
            System.out.println("> ");
            String line = buffer.readLine();
            if (line == null) break;
            run(line);
        }

    }

    private static void runfile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    private static void run(String source){
        MyScanner scanner  = new MyScanner(source);
        List<Token> tokens =  scanner.scanTokens();


        for (Token token : tokens){
            token.print_type();
            token.print_literal();
        }

        Parser parser = new Parser(tokens);
        List<ASTNode> ASTNodes = parser.parse();


        // Stop if there was a syntax error.
        if (hadError) System.exit(65);
        if (hadRuntimeError) System.exit(70);

        var printer = new AstPrinter();
        printer.print(ASTNodes);
        interpeter.interpret(ASTNodes);
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    private static void report(int line, String where,
                               String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }


    public static void runTimeError(RuntimeError error) {
        System.err.println(error.getMessage() +
                "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }
}