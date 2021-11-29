package compiler;

import compiler.exceptions.scanner.UnrecognizedCharacterException;
import compiler.lexer.Lexer;
import compiler.lexer.Lexer.Token;
import compiler.lexer.Lexer.TokenType;
import compiler.parser.GrxParser;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public final class Compiler {
    private final Lexer lexer;
    private final GrxParser parser;
    private final StringBuilder lexerLogger;
    private final HashMap<TokenType, Integer> tokenTypeMap;

    public Compiler(String sourceProgram) {
        this.lexer = new Lexer(new StringReader(sourceProgram));
        this.parser = new GrxParser();
        this.lexerLogger = new StringBuilder();

        this.tokenTypeMap = new HashMap<>();
        tokenTypeMap.put(TokenType.SEMICOL, 0);
        tokenTypeMap.put(TokenType.STR, 1);
        tokenTypeMap.put(TokenType.FLOAT, 2);
        tokenTypeMap.put(TokenType.NUM_CONST, 3);
        tokenTypeMap.put(TokenType.COLON, 4);
        tokenTypeMap.put(TokenType.ENDINPUT, 5);
        tokenTypeMap.put(TokenType.ID, 6);
        tokenTypeMap.put(TokenType.ASSIGN, 7);
        tokenTypeMap.put(TokenType.STARTINPUT, 8);
        tokenTypeMap.put(TokenType.INT, 9);
    }

    public String getLexerLogs() {
        return lexerLogger.toString();
    }

    public void compile() {
        try {
            Token token;

            do {
                token = lexer.yylex();
                lexerLogger.append(token).append('\n');
                parser.parse(tokenTypeMap.get(token.getType()), token);
            } while (token.getType() != TokenType.ENDINPUT);
        } catch (IOException
                | UnrecognizedCharacterException
                | RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void getTokenType(int tokenType) {
        switch (tokenType) {
            case 0: {
                
            }
        }
    }
}
