import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import utils.EnvLoader;
import utils.HttpInputFetcher;

public class Day4 {


    private static final String URL = "https://adventofcode.com/2025/day/4/input";
    private static final String TEST_DATA = """
           ..@@.@@@@.
           @@@.@.@.@@
           @@@@@.@.@@
           @.@@@@..@.
           @@.@@@@.@@
           .@@@@@@@.@
           .@.@.@.@@@
           @.@@@.@@@@
           .@@@@@@@@.
           @.@.@@@.@.
            """;

    // wrongs pt.1:
    // wrongs pt.2:
    public static void execute() {
//        var ranges = rangesFromTestData();
        var ranges = rangesFromUrl();
        var arr = toArray(ranges);
        arr = toBorderedArray(arr);
        int reachableTiles = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (Objects.equals(arr[i][j], ".")) {
                    continue;
                }
                int neighborBlocks = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (k == 0 && l == 0) {
                            continue;
                        }
                        if (Objects.equals(arr[i + k][j + l], "@")) {
                            neighborBlocks++;
                        }
                    }
                }
                if (neighborBlocks < 4) {
                    reachableTiles++;
                }
            }
        }
        System.out.println(reachableTiles);
    }

    private static String[][] toArray(List<List<String>> ranges) {
        return ranges.stream()
                .map(innerList -> innerList.toArray(new String[0]))
                .toArray(String[][]::new);
    }

    private static String[][] toBorderedArray(String[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        String[][] borderedArray = new String[rows + 2][cols + 2];

        for (int i = 0; i < borderedArray.length; i++) {
            for (int j = 0; j < borderedArray[i].length; j++) {
                if (i == 0 || i == borderedArray.length - 1 || j == 0 || j == borderedArray[i].length - 1) {
                    borderedArray[i][j] = ".";
                } else {
                    borderedArray[i][j] = array[i - 1][j - 1];
                }
            }
        }
        return borderedArray;
    }

    private static List<List<String>> rangesFromUrl() {
        return new HttpInputFetcher(EnvLoader.loadCookie()).fetchLines(URL).stream()
                .filter(line -> !line.isBlank())
                .map(Day4::parseRange)
                .toList();
    }

    private static List<List<String>> rangesFromTestData() {
        return TEST_DATA.lines()
                .filter(line -> !line.isBlank())
                .map(Day4::parseRange)
                .toList();
    }

    private static List<String> parseRange(String range) {
        return Arrays.stream(range.split(""))
                .filter(s -> !s.isBlank())
                .toList();
    }

}
