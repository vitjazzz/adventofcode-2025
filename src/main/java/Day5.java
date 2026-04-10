import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
            """;

    // wrongs pt.1:
    // wrongs pt.2: 388947125157441
    public static void execute() {
//        var lines = linesFromTestData();
        var lines = linesFromUrl();
        var input = parseInput(lines);


        var sortedRanges = input.ranges.stream()
                .sorted(Comparator.comparingLong(o -> o.from))
                .toList();
        var mergedRanges = new LinkedList<Range>();
        for (Range range : sortedRanges) {
            for (Iterator<Range> it = mergedRanges.iterator(); it.hasNext();) {
                var next = it.next();
                if (range.from > next.to) {
                    continue;
                }
                if (range.to < next.from) {
                    System.out.println("Error!!!!!");
                    break;
                }
                it.remove();
                range = new Range(Math.min(range.from, next.from), Math.max(range.to, next.to));
            }
            mergedRanges.add(range);
        }

        long freshIds = 0;
        for (Range mergedRange : mergedRanges) {
            freshIds += 1 + mergedRange.to - mergedRange.from;
        }

        System.out.println(freshIds);
    }

    private static Range findRange(List<Range> ranges, long point) {
        for (var range : ranges) {
            if (point >= range.from && point <= range.to) {
                return range;
            }
        }
        return null;
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
