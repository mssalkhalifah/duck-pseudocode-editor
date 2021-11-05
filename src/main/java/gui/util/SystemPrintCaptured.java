package gui.util;

import javax.swing.*;
import java.io.*;
import java.util.Objects;

public final class SystemPrintCaptured extends PrintStream {
    private final JTextArea textArea;
    private final String indent;
    private boolean atLineStart;

    public SystemPrintCaptured(JTextArea textArea, PrintStream printStream, String indent) {
        super(printStream);
        this.textArea = textArea;
        this.indent = indent;
        this.atLineStart = false;
    }

    public SystemPrintCaptured(JTextArea textArea, PrintStream printStream) {
        this(textArea, printStream, "");
    }

    @Override
    public void print(boolean b) {
        synchronized (this) {
            super.print(b);
            write(String.valueOf(b));
        }
    }

    @Override
    public void print(char c) {
        synchronized (this) {
            super.print(c);
            write(String.valueOf(c));
        }
    }

    @Override
    public void print(char[] s) {
        synchronized (this) {
            super.print(s);
            write(String.valueOf(s));
        }
    }

    @Override
    public void print(double d) {
        synchronized (this) {
            super.print(d);
            write(String.valueOf(d));
        }
    }

    @Override
    public void print(float f) {
        synchronized (this) {
            super.print(f);
            write(String.valueOf(f));
        }
    }

    @Override
    public void print(int i) {
        synchronized (this) {
            super.print(i);
            write(String.valueOf(i));
        }
    }

    @Override
    public void print(long l) {
        synchronized (this) {
            super.print(l);
            write(String.valueOf(l));
        }
    }

    @Override
    public void print(Object o) {
        synchronized (this) {
            super.print(o);
            write(String.valueOf(o));
        }
    }

    @Override
    public void print(String s) {
        synchronized (this) {
            super.print(s);
            write(Objects.requireNonNullElse(s, "null"));
        }
    }

    @Override
    public void println() {
        synchronized (this) {
            newLine();
            super.println();
        }
    }

    @Override
    public void println(boolean x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(char x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(int x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(long x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(float x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(double x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(char[] x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(String x) {
        synchronized (this) {
            print(x);
            newLine();
            super.println();
        }
    }

    @Override
    public void println(Object x) {
        String s = String.valueOf(x);
        synchronized (this) {
            print(s);
            newLine();
            super.println();
        }
    }

    private void writeToTextArea(String string) {
        if (textArea != null) {
            synchronized (textArea) {
                textArea.setCaretPosition(textArea.getDocument().getLength());
                textArea.append(string);
            }
        }
    }

    private void writeWithIndent(String string) {
        if (atLineStart) {
            writeToTextArea(indent + string);
            atLineStart = false;
        } else {
            writeToTextArea(string);
        }
    }

    private void write(String string) {
        String[] str = string.split("\n", -1);

        if (str.length == 0) {
            return;
        }

        for (int i = 0; i < str.length - 1; i++) {
            writeWithIndent(str[i]);
            writeWithIndent("\n");
        }

        String last = str[str.length - 1];
        if (!last.equals("")) {
            writeWithIndent(last);
        }
    }

    private void newLine() {
        write("\n");
    }
}
