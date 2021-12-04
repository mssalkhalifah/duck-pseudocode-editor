package compiler.parser;

import main.EntryPoint;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class GrxParserTableFactory {
    private GrxParserTableFactory() {}

    public static List<Integer> build(String tableType) {
        String path = Objects.requireNonNull(
                EntryPoint.class.getClassLoader().getResource("Grammax/GrxParser.java")).getPath();
        //String path = "src/main/resources/Grammax/GrxParser.java";

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
        ArrayList<Integer> tableList
                = new ArrayList<>((tableType.equalsIgnoreCase("actionTable") ? 21462 : 12775));
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().contains(tableType)) {
                boolean eof = false;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (line.contains("}")) {
                        line = line.substring(0, line.lastIndexOf('}'));
                        eof = true;
                    }

                    tableList.addAll(
                            Arrays.stream(line.split(","))
                                    .mapToInt(action -> Integer.parseInt(action.trim()))
                                    .boxed()
                                    .collect(Collectors.toList())
                    );

                    if (eof) return tableList;
                }
            }
        }

        return null;
    }
}
