import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import utils.EnvLoader;
import utils.HttpInputFetcher;

public class Day3 {

    private static final String URL = "https://adventofcode.com/2025/day/3/input";
    private static final String TEST_DATA = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
            """;

    // wrongs pt.1:
    // wrongs pt.2:
    public static void execute() {
//        var ranges = rangesFromTestData();
        var ranges = rangesFromUrl();
        var res = 0;
        for (List<Integer> range : ranges) {
            int largestLeftIndex = 0;
            int largestLeftValue = 0;
            for (int i = 0; i < range.size() - 1; i++) {
                if (range.get(i) > largestLeftValue) {
                    largestLeftValue = range.get(i);
                    largestLeftIndex = i;
                }
            }
            int largestRightValue = 0;
            for (int i = largestLeftIndex + 1; i < range.size(); i++) {
                if (range.get(i) > largestRightValue) {
                    largestRightValue = range.get(i);
                }
            }
            var joltageStr = String.valueOf(largestLeftValue) + largestRightValue;
            var joltage = Integer.parseInt(joltageStr);
            res += joltage;
        }
        System.out.println(res);

    }

    private static List<List<Integer>> rangesFromUrl() {
        return new HttpInputFetcher(EnvLoader.loadCookie()).fetchLines(URL).stream()
                .filter(line -> !line.isBlank())
                .map(Day3::parseRange)
                .toList();
    }

    private static List<List<Integer>> rangesFromTestData() {
        return TEST_DATA.lines()
                .filter(line -> !line.isBlank())
                .map(Day3::parseRange)
                .toList();
    }

    private static List<Integer> parseRange(String range) {
        return Arrays.stream(range.split(""))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .toList();
    }

}
