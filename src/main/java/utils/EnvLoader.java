package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EnvLoader {

    public static String loadCookie() {
        Path envFile = Path.of(".env");
        if (Files.exists(envFile)) {
            try {
                return Files.readAllLines(envFile).stream()
                        .filter(line -> line.startsWith("AOC_COOKIE="))
                        .map(line -> line.substring("AOC_COOKIE=".length()))
                        .findFirst()
                        .orElseGet(() -> System.getenv("AOC_COOKIE"));
            } catch (IOException ignored) {
            }
        }
        return System.getenv("AOC_COOKIE");
    }

}
