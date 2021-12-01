package compiler;

import compiler.exceptions.scanner.UnrecognizedCharacterException;
import compiler.lexer.Lexer;
import compiler.lexer.Lexer.Token;
import compiler.lexer.Lexer.TokenType;
import compiler.parser.GrxParser;
import compiler.parser.GrxTokenGenerator;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public final class Compiler {
    private final Lexer lexer;
    private final GrxParser parser;
    private final StringBuilder lexerLogger;
    private final HashMap<String, Integer> grxTokens;

    public Compiler(String sourceProgram) {
        this.lexer = new Lexer(new StringReader(sourceProgram));
        this.parser = new GrxParser();
        this.lexerLogger = new StringBuilder();
        this.grxTokens = GrxTokenGenerator.generate();
    }

    public String getLexerLogs() {
        return lexerLogger.toString();
    }

    public void compile() {
        Token token = null;
        try {
            do {
                token = lexer.yylex();
                lexerLogger.append(token).append('\n');
                int grxToken = grxTokens.get(token.getType().toString());
                parser.parse(grxToken, token);
            } while (token.getType() != TokenType.ENDINPUT);
        } catch (IOException
                | UnrecognizedCharacterException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.printf("Syntax error: %s\n", token);
        }
    }
}
