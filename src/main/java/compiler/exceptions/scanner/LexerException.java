package compiler.exceptions.scanner;

public abstract class LexerException extends Exception {
    protected LexerException(String message) {
        super(message);
    }
}
