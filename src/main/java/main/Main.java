package main;

import compiler.exceptions.scanner.LexerException;
import compiler.lexer.Lexer;

import java.io.*;

public class Main {
    public Main() {

        try {

            InputStream stream = Main.class.getClassLoader().getResourceAsStream("jflexTest");

            if (stream == null) {
                throw new FileNotFoundException("Source file not found");
            }

            Reader reader = new InputStreamReader(stream);
            Lexer lexer = new Lexer(reader);

            Lexer.Token token = lexer.yylex();
            System.out.println(token);
            while (token.getType() != Lexer.TokenType.ENDINPUT) {
                token = lexer.yylex();
                System.out.println(token);
            }

        } catch (IOException | LexerException e) {
            e.printStackTrace();
        }
    }
}
