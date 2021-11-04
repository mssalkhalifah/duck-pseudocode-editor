package controller;

import compiler.Compiler;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class CompilerWorker extends SwingWorker<Void, Void> {
    private final Compiler compiler;

    public CompilerWorker(String sourceProgram) {
        this.compiler = new Compiler(sourceProgram);
    }

    @Override
    protected Void doInBackground() {
        System.out.println("Tokenizing source program...\n");
        this.compiler.compile();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Sample_Table.txt"));
            writer.write(this.compiler.getLexerLogs());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(this.compiler.getLexerLogs());
        return null;
    }

    @Override
    protected void done() {
        System.out.println("\nLexer done.");
        super.done();
    }
}
