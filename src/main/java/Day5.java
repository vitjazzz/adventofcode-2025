import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.EnvLoader;
import utils.HttpInputFetcher;

public class Day5 {


    private static final String URL = "https://adventofcode.com/2025/day/5/input";
    private static final String TEST_DATA = """
            3-5
            10-14
            16-20
            12-18
                       
            1
            5
            8
            11
            17
            32
            """;

    // wrongs pt.1:
    // wrongs pt.2:
    public static void execute() {
        var lines = linesFromUrl();
        var input = parseInput(lines);

        int freshIds = 0;

        for (Long id : input.ids) {
            for (Range range : input.ranges) {
                if (id >= range.from && id <= range.to) {
                    freshIds += 1;
                    break;
                }
            }
        }
        System.out.println(freshIds);
    }


    private static List<String> linesFromUrl() {
        return new HttpInputFetcher(EnvLoader.loadCookie()).fetchLines(URL).stream()
                .toList();
    }

    private static List<String> linesFromTestData() {
        return TEST_DATA.lines().toList();
    }

    private record InputData(List<Range> ranges, List<Long> ids) {}

    private record Range(Long from, Long to) {}

    private static InputData parseInput(List<String> lines) {
        List<Range> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        boolean parsingRanges = true;

        for (String line : lines) {
            if (line.isBlank()) {
                parsingRanges = false;
                continue;
            }

            if (parsingRanges) {
                ranges.add(parseRange(line));
            } else {
                ids.add(Long.parseLong(line));
            }
        }

        return new InputData(ranges, ids);
    }

    private static Range parseRange(String range) {
        var parts = range.split("-");
        return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

}
