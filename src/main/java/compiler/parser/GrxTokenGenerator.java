package compiler.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public final class GrxTokenGenerator {
    private GrxTokenGenerator() {}

    public static HashMap<String, Integer> generate() {
        String path = "src/main/resources/Grammax/GrxParser.java";
        HashMap<String, Integer> grxTokens = new HashMap<>();

        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            Scanner scanner = new Scanner(fileInputStream, StandardCharsets.UTF_8);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.contains("T_")) {
                    String token = line.substring(line.indexOf("T_") + 2, line.lastIndexOf('=')).trim();
                    String value = line.substring(line.indexOf('=') + 1, line.lastIndexOf(';')).trim();
                    grxTokens.put(token, Integer.parseInt(value));
                }
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert grxTokens.size() > 0 : "No tokens added";

        return grxTokens;
    }
}
