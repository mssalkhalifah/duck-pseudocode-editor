package compiler;

import compiler.exceptions.scanner.UnrecognizedCharacterException;
import compiler.exceptions.semantic.InvalidTypeException;
import compiler.exceptions.semantic.VariableDefinedException;
import compiler.exceptions.semantic.VariableUndefinedException;
import compiler.lexer.Lexer;
import compiler.lexer.Lexer.Token;
import compiler.lexer.Lexer.TokenType;
import compiler.parser.GrxParser;
import compiler.parser.GrxTokenGenerator;
import compiler.symboltable.SymbolTable;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public final class Compiler {
    private final Lexer lexer;
    private final GrxParser parser;
    private final StringBuilder lexerLogger;
    private String scopeOutputLog;
    private final HashMap<String, Integer> grxTokens;

    public Compiler(String sourceProgram) {
        this.lexer = new Lexer(new StringReader(sourceProgram));
        this.parser = new GrxParser();
        this.lexerLogger = new StringBuilder();
        this.grxTokens = GrxTokenGenerator.generate();
        this.scopeOutputLog = "";
    }

    public String getLexerLogs() {
        return lexerLogger.toString();
    }

    public String getScopeOutputLog() {
        return scopeOutputLog;
    }

    public void compile() {
        Token token;
        try {
            do {
                token = lexer.yylex();
                lexerLogger.append(token).append('\n');
                parser.parse(grxTokens.get(token.getType().toString()), token);
                handleScopes(token);
            } while (token.getType() != TokenType.ENDINPUT);
            System.out.println(lexerLogger);
            System.out.println(SymbolTable.getOutputLog());
        } catch (IOException
                | UnrecognizedCharacterException
                | VariableDefinedException
                | InvalidTypeException
                | VariableUndefinedException
                | RuntimeException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        finally {
            this.scopeOutputLog = SymbolTable.getOutputLog();
            SymbolTable.clear();
        }
    }

    private void handleScopes(Token token) {
        int scopeStartLine = token.getLineNumber() + 1;
        switch (token.getType()) {
            case WHILE:{
                SymbolTable.addScope("While " + scopeStartLine);
                break;
            }
            case DO:{
                SymbolTable.addScope("Do " + scopeStartLine);
                break;
            }
            case FOR:{
                SymbolTable.addScope("For " + scopeStartLine);
                break;
            }
            case IF:{
                SymbolTable.addScope("If " + scopeStartLine);
                break;
            }
            case ELSE:{
                SymbolTable.exitCurrentScope();
                SymbolTable.addScope("Else " + scopeStartLine);
                break;
            }
            case ENDO:
            case ENDFOR:
            case ENDWHILE:
            case ENIF: {
                SymbolTable.exitCurrentScope();
                break;
            }
        }
    }
}
