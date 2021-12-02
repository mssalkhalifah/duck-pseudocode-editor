package compiler.exceptions.semantic;

public class InvalidTypeException extends Exception {
    public InvalidTypeException(String invalidType, String expectedType, int lineNumber) {
        super(String.format(
                "Expected value of type %s, got %s at line %d", expectedType, invalidType, lineNumber
        ));
    }
}
