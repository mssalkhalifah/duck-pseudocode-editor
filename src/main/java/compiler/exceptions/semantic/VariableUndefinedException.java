package compiler.exceptions.semantic;

public class VariableUndefinedException extends Exception {
    public VariableUndefinedException(String undefinedVar, int lineNumber) {
        super(
                String.format("The variable %s is undefined at line %d", undefinedVar, lineNumber)
        );
    }
}
