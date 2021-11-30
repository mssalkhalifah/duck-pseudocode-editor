package compiler.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class GrxParserTableFactory {
    public static List<Integer> build(String tableType) {
        String path = "src/main/resources/Grammax/GrxParser.java";

        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            Scanner scanner = new Scanner(fileInputStream);
            switch (tableType) {
                case "Action": return tableBuilder(scanner, "actionTable");
                case "Goto": return tableBuilder(scanner, "gotoTable");
                default: throw new IllegalArgumentException("Invalid table type: \"Action\" or \"Goto\"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<Integer> tableBuilder(Scanner scanner, String tableType) {
        LinkedList<Integer> tableLL = new LinkedList<>();
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().contains(tableType)) {
                boolean eof = false;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (line.contains("}")) {
                        line = line.substring(0, line.lastIndexOf('}'));
                        eof = true;
                    }

                    tableLL.addAll(
                            Arrays.stream(line.split(","))
                                    .mapToInt(action -> Integer.parseInt(action.trim()))
                                    .boxed()
                                    .collect(Collectors.toList())
                    );

                    if (eof) return new ArrayList<>(tableLL);
                }
            }
        }

        return null;
    }
}
