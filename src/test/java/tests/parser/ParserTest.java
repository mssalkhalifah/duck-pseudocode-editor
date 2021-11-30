package tests.parser;

import compiler.Compiler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParserTest {
    private static Compiler compiler;

    @BeforeAll
    public static void setup() throws IOException {
        String sourceProgram = Files
                .readString(Path
                        .of("/Users/mssalkhalifah/Dev/JavaProjects/" +
                                "duck-pseudocode-editor/src/test/java/tests/parser/source_program"));
        System.out.println("Source Program:\n" + sourceProgram);
        compiler = new Compiler(sourceProgram);
    }

    @Test
    public void parseTest() {
        compiler.compile();
        System.out.println("\n" + compiler.getLexerLogs());
    }
}
