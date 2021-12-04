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
        this.compiler.compile();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("scope_output.txt"))) {
            writer.write(this.compiler.getScopeOutputLog());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Sample_Table.txt"))) {
            writer.write(this.compiler.getLexerLogs());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void done() {
        super.done();
    }
}
