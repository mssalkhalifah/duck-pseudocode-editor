package compiler.symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ScopeNode {
    private ScopeNode parent;
    private final HashMap<String, Symbol> symbolTable;
    private final List<ScopeNode> children;
    private final String scopeLabel;

    public ScopeNode(String scopeLabel) {
        this.scopeLabel = scopeLabel;
        this.symbolTable = new LinkedHashMap<>();
        this.children = new ArrayList<>();
    }

    public HashMap<String, Symbol> getSymbolTable() {
        return symbolTable;
    }

    public void addChild(ScopeNode child) {
        children.add(child);
    }

    public List<ScopeNode> getChildren() {
        return children;
    }

    public ScopeNode getParent() {
        return parent;
    }

    public void setParent(ScopeNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.format("Scope{%s}", scopeLabel);
    }
}
