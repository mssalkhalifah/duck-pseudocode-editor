package compiler.symboltable;

import compiler.exceptions.semantic.InvalidTypeException;
import compiler.exceptions.semantic.VariableDefinedException;
import compiler.lexer.Lexer;

import java.util.HashMap;

public class SymbolTable {
    private static class Symbol {
        int lineNumber;
        Lexer.TokenType type;
        Object value;

        public Symbol(int lineNumber) {
            this.lineNumber = lineNumber;
        }
    }

    private static final HashMap<String, Symbol> symbolTable = new HashMap<>();

    public static void addSymbol(Lexer.Token token) throws VariableDefinedException {
        if (token.getType() != Lexer.TokenType.ID) throw new IllegalArgumentException("Expected ID token");

        if (!symbolTable.containsKey(token.getValue())) {
            symbolTable.put(token.getValue(), new Symbol(token.getLineNumber()));
        } else if (
                symbolTable.get(token.getValue()).value != null
                        && symbolTable.get(token.getValue()).type !=null) {
            throw new VariableDefinedException(token.getValue(), symbolTable.get(token.getValue()).lineNumber);
        } else {
            throw new RuntimeException("Unexpected error");
        }
    }

    public static void setAttributes(Lexer.Token idToken, Lexer.Token typeToken, Lexer.Token valueToken)
            throws InvalidTypeException {
        if (idToken.getType() != Lexer.TokenType.ID) throw new IllegalArgumentException("Expected ID token");
        if (!symbolTable.containsKey(idToken.getValue()))
            throw new IllegalArgumentException(String.format("Symbol table does not have %s", idToken.getValue()));

        switch (typeToken.getType()) {
            case INT: {
                if (valueToken.getType() == Lexer.TokenType.ID) {
                    setAttributeIdToken(idToken, valueToken, Lexer.TokenType.INT);
                    return;
                }

                try {
                    Object value = Integer.parseInt(valueToken.getValue());
                    symbolTable.get(idToken.getValue()).type = typeToken.getType();
                    symbolTable.get(idToken.getValue()).value = value;
                } catch (NumberFormatException e) {
                    throw new InvalidTypeException(valueToken.getType().toString(), "int", valueToken.getLineNumber());
                }
            }
            case FLOAT: {
                if (valueToken.getType() == Lexer.TokenType.ID) {
                    setAttributeIdToken(idToken, valueToken, Lexer.TokenType.FLOAT);
                    return;
                }

                try {
                    Object value = Float.parseFloat(valueToken.getValue());
                    symbolTable.get(idToken.getValue()).type = typeToken.getType();
                    symbolTable.get(idToken.getValue()).value = value;
                } catch (NumberFormatException e) {
                    throw new
                            InvalidTypeException(valueToken.getType().toString(), "float", valueToken.getLineNumber());
                }
            }
            case STR: {
                if (valueToken.getType() == Lexer.TokenType.ID) {
                    setAttributeIdToken(idToken, valueToken, Lexer.TokenType.STR);
                    return;
                }

                if (valueToken.getType() != Lexer.TokenType.STR_CONST)
                    throw new InvalidTypeException(
                            valueToken.getType().toString(), "string", valueToken.getLineNumber());

                symbolTable.get(idToken.getValue()).value = valueToken.getValue();
            }
            case CHAR: {
                if (valueToken.getType() == Lexer.TokenType.ID) {
                    setAttributeIdToken(idToken, valueToken, Lexer.TokenType.CHAR);
                    return;
                }

                if (valueToken.getType() != Lexer.TokenType.CHAR_CONST)
                    throw new InvalidTypeException(
                            valueToken.getType().toString(), "char", valueToken.getLineNumber());

                symbolTable.get(idToken.getValue()).value = valueToken.getValue();
            }
            case BOOL: {
                if (valueToken.getType() == Lexer.TokenType.ID) {
                    setAttributeIdToken(idToken, valueToken, Lexer.TokenType.BOOL);
                    return;
                }

                if (valueToken.getType() != Lexer.TokenType.BOOL_CONST)
                    throw new InvalidTypeException(
                            valueToken.getType().toString(), "bool", valueToken.getLineNumber());

                symbolTable.get(idToken.getValue()).value = Boolean.valueOf(valueToken.getValue());
            }
            default:
                throw new IllegalArgumentException("Unexpected var type");
        }
    }

    private static void setAttributeIdToken(Lexer.Token idToken,
                                     Lexer.Token otherIdToken,
                                     Lexer.TokenType expectedType) throws InvalidTypeException {
        Lexer.TokenType otherIdTokenType = symbolTable.get(otherIdToken.getValue()).type;

        if (otherIdTokenType != expectedType)
            throw new InvalidTypeException(
                    otherIdTokenType.toString(),
                    expectedType.toString(),
                    otherIdToken.getLineNumber());

        symbolTable.get(idToken.getValue()).value =
                symbolTable.get(otherIdToken.getValue()).value;
    }
}
