package compiler.symboltable;

import compiler.exceptions.semantic.InvalidTypeException;
import compiler.exceptions.semantic.VariableDefinedException;
import compiler.exceptions.semantic.VariableUndefinedException;
import compiler.lexer.Lexer.Token;
import compiler.lexer.Lexer.TokenType;
import utils.PrettyPrintingMap;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolTable {
    private static final ScopeNode root = new ScopeNode("Root");
    private static ScopeNode currentNode = root;

    private SymbolTable() {}

    public static void addScope(String newScopeLabel) {
        ScopeNode newScopeNode = new ScopeNode(newScopeLabel);
        newScopeNode.setParent(currentNode);
        currentNode.addChild(newScopeNode);
        currentNode = newScopeNode;
    }

    public static void exitCurrentScope() {
        currentNode = currentNode.getParent();
    }

    public static void addSymbol(Token idToken) {
        if (idToken.getType() != TokenType.ID) throw new IllegalArgumentException("Expected ID token");

        if (!currentNode.getSymbolTable().containsKey(idToken.getValue())) {
            currentNode.getSymbolTable().put(idToken.getValue(), new Symbol(idToken.getLineNumber()));
        }
    }

    public static Token initAttributes(Token idToken, Token typeToken, Token valueToken)
            throws VariableDefinedException, InvalidTypeException, VariableUndefinedException {
        ScopeNode currentScopeNode = getCurrentValidScopeNode(idToken);
        Symbol currentSymbol = currentScopeNode.getSymbolTable().get(idToken.getValue());

        if (currentSymbol.type != null)
            throw new VariableDefinedException(idToken.getValue(), currentSymbol.lineNumber + 1);

        currentSymbol.type = typeToken.getType();

        if (valueToken.getType() == TokenType.ID) {
            setValueIdToken(currentScopeNode, idToken, valueToken, typeToken.getType());
        } else {
            setValue(currentSymbol, typeToken.getType(), valueToken);
        }

        return idToken;
    }

    public static Token updateAttribute(Token idToken, Token assignOP, Token valueToken)
            throws VariableUndefinedException, InvalidTypeException {
        ScopeNode currentScopeNode = getCurrentValidScopeNode(idToken);
        Symbol currentSymbol = currentScopeNode.getSymbolTable().get(idToken.getValue());

        if (currentSymbol.type == null)
            throw new VariableUndefinedException(idToken.getValue(), idToken.getLineNumber() + 1);

        if (assignOP.getType() != TokenType.ASSIGN)
            throw new IllegalArgumentException("Invalid assign operator");

        if (valueToken.getType() == TokenType.ID) {
            setValueIdToken(currentScopeNode, idToken, valueToken, currentSymbol.type);
        } else {
            setValue(currentSymbol, currentSymbol.type, valueToken);
        }

        return idToken;
    }

    private static ScopeNode getCurrentValidScopeNode(Token idToken) throws VariableUndefinedException {
        if (idToken.getType() != TokenType.ID) throw new IllegalArgumentException("Expected ID token");

        ScopeNode currentScopeNode = getTokenTraversal(currentNode, idToken);

        if (!currentScopeNode.getSymbolTable().containsKey(idToken.getValue()))
            //throw new IllegalArgumentException(String.format("Token %s does not exist", idToken));
            throw new VariableUndefinedException(idToken.getValue(), idToken.getLineNumber() + 1);

        return currentScopeNode;
    }

    private static ScopeNode getTokenTraversal(ScopeNode currentNode, Token key) {
        if (currentNode.getParent() == null || currentNode.getSymbolTable().containsKey(key.getValue())) {
            return currentNode;
        }

        return getTokenTraversal(currentNode.getParent(), key);
    }

    private static void setValue(Symbol currentSymbol, TokenType type, Token valueToken)
            throws InvalidTypeException {
        switch (type) {
            case INT:{
                try {
                    currentSymbol.value = Integer.parseInt(valueToken.getValue());
                } catch (NumberFormatException e) {
                    throw new InvalidTypeException(
                            (valueToken.getType() == TokenType.NUM_CONST)
                                    ? "FLOAT" : valueToken.getType().toString() , type.toString(), valueToken.getLineNumber() + 1);
                }

                break;
            }
            case FLOAT:{
                currentSymbol.value = Float.parseFloat(valueToken.getValue());
                break;
            }
            case STR:{
                if (valueToken.getType() != TokenType.STR_CONST)
                    throw new InvalidTypeException(
                            valueToken.getType().toString(), type.toString(), valueToken.getLineNumber() + 1);

                currentSymbol.value = setCharactersValue(valueToken);
                break;
            }
            case CHAR:{
                if (valueToken.getType() != TokenType.CHAR_CONST)
                    throw new InvalidTypeException(
                            valueToken.getType().toString(), type.toString(), valueToken.getLineNumber() + 1);

                String value = setCharactersValue(valueToken);

                if (value.length() > 1)
                    throw new InvalidTypeException(
                            valueToken.getType().toString(), type.toString(), valueToken.getLineNumber() + 1);

                currentSymbol.value = (value.length() == 0) ? Character.MIN_VALUE : value.charAt(0);
                break;
            }
            case BOOL:{
                currentSymbol.value = Boolean.valueOf(valueToken.getValue());
                break;
            }
        }
    }

    private static String setCharactersValue(Token valueToken) {
        String value = valueToken.getValue().substring(1, valueToken.getValue().length() - 1);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("\\n", "\n");
        tokens.put("\\t", "\t");
        tokens.put("\\\"", "\"");
        tokens.put("\\'", "'");
        tokens.put("\\\\", "\\\\");

        Pattern pattern = Pattern.compile("\\\\n|\\\\t|\\\\\"|\\\\'|\\\\\\\\");
        Matcher matcher = pattern.matcher(value);

        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            String matched = matcher.group();
            matcher.appendReplacement(stringBuilder, tokens.get(matched));

        }
        matcher.appendTail(stringBuilder);

        return stringBuilder.toString();
    }

    private static void setValueIdToken(ScopeNode currentNode, Token idToken, Token otherIdToken, TokenType expectedType)
            throws InvalidTypeException, VariableUndefinedException {
        Symbol otherSymbol;

        if (otherIdToken.getValue().equals(idToken.getValue())
                && otherIdToken.getLineNumber() == idToken.getLineNumber()) {
            otherSymbol = getTokenTraversal(currentNode.getParent(), otherIdToken)
                    .getSymbolTable().get(otherIdToken.getValue());
        } else {
            otherSymbol = getTokenTraversal(currentNode, otherIdToken).getSymbolTable().get(otherIdToken.getValue());
        }
        
        if (otherSymbol == null) {
            throw new VariableUndefinedException(otherIdToken.getValue(), otherIdToken.getLineNumber() + 1);
        }

        if (otherSymbol.type != expectedType)
            throw new InvalidTypeException(
                    otherSymbol.type.toString(), expectedType.toString(), otherSymbol.lineNumber + 1);

        currentNode.getSymbolTable().get(idToken.getValue()).value = otherSymbol.value;
    }

    public static String getOutputLog() {
        Queue<ScopeNode> scopeNodeQueue = new ArrayDeque<>();
        StringBuilder stringBuilder = new StringBuilder();
        scopeNodeQueue.add(root);

        while (!scopeNodeQueue.isEmpty()) {
            ScopeNode currentScopeNode = scopeNodeQueue.remove();

            stringBuilder.append(String.format("\n<=== %s ===>\n", (currentScopeNode.getParent() == null
                    ? currentScopeNode
                    : currentScopeNode + " Parent > " + currentScopeNode.getParent())));
            stringBuilder.append(PrettyPrintingMap.getMapString(currentScopeNode.getSymbolTable())).append('\n');

            scopeNodeQueue.addAll(currentScopeNode.getChildren());
        }

        return stringBuilder.toString();
    }

    public static void clear() {
        currentNode = root;
        root.getSymbolTable().clear();
        root.getChildren().clear();
    }
}
