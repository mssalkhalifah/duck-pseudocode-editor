package compiler.symboltable;

import compiler.lexer.Lexer;

public class Symbol {
    int lineNumber;
    Lexer.TokenType type;
    Object value;

    public Symbol(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return String.format("Symbol{%d, %s, %s}", lineNumber, type, value);
    }
}
