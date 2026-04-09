import utils.EnvLoader;
import utils.HttpInputFetcher;

import java.util.List;

public class Day1 {

    private static final String URL = "https://adventofcode.com/2025/day/1/input";
    private static final String TEST_DATA = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
            """;

    // wrongs pt.1: 43, 47
    // wrongs pt.2: 4522, 58303, 6491, 6928
    public static void execute() {
//        var lines = linesFromTestData();
        var lines = linesFromUrl();
        int prevPos = 50;
        int pos = 50;
        int zeroes = 0;
        for (String line : lines) {
            var direction = line.startsWith("R") ? 1 : -1;
            var step = Integer.parseInt(line.substring(1));
            pos += step * direction;
            if (pos > 99) {
                zeroes += pos / 100;
                pos = pos % 100;
            } else if (pos < 0) {
                if (prevPos != 0) {
                    zeroes += 1;
                }
                zeroes += Math.abs(pos / 100);
                pos = (100 + (pos % 100)) % 100;
            } else if (pos == 0) {
                 zeroes += 1;
            }
            prevPos = pos;
        }

        System.out.println("Zeroes - " + zeroes);
    }

    public static List<String> linesFromUrl() {
        return new HttpInputFetcher(EnvLoader.loadCookie()).fetchLines(URL);
    }

    public static List<String> linesFromTestData() {
        return TEST_DATA.lines()
                .filter(line -> !line.isBlank())
                .toList();
    }

}
