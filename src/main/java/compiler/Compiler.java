package compiler;

import compiler.exceptions.scanner.UnrecognizedCharacterException;
import compiler.lexer.Lexer;
import compiler.lexer.Lexer.Token;
import compiler.lexer.Lexer.TokenType;

import java.io.IOException;
import java.io.StringReader;

public final class Compiler {
    private final Lexer lexer;
    private final StringBuilder lexerLogger;

    public Compiler(String sourceProgram) {
        this.lexer = new Lexer(new StringReader(sourceProgram));
        this.lexerLogger = new StringBuilder();
    }

    public String getLexerLogs() {
        return lexerLogger.toString();
    }

    public void compile() {
        try {
            Token token = lexer.yylex();
            lexerLogger.append(token).append('\n');
            while (token.getType() != TokenType.ENDINPUT) {
                token = lexer.yylex();
                lexerLogger.append(token).append('\n');
            }
        } catch (IOException | UnrecognizedCharacterException e) {
            e.printStackTrace();
        }
    }
}
