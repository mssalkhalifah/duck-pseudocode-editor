package compiler.exceptions.semantic;

public class VariableDefinedException extends Exception {
    public VariableDefinedException(String definedVar, int definedVarLine) {
        super(String.format(
                "The variable %s already defined at line %d", definedVar, definedVarLine
        ));
    }
}
