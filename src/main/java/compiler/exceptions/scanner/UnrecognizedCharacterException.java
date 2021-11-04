package compiler.exceptions.scanner;

public class UnrecognizedCharacterException extends LexerException {
    public UnrecognizedCharacterException(String unrecognizedChar, int currentPosition, int currentLineNumber) {
        super(String
                .format("At line %d position %d %s is unrecognized",
                        currentLineNumber, currentPosition, unrecognizedChar));
    }
}
