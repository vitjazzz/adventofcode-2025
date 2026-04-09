import java.util.Arrays;
import java.util.List;

import utils.EnvLoader;
import utils.HttpInputFetcher;

public class Day2 {

    private static final String URL = "https://adventofcode.com/2025/day/2/input";
    private static final String TEST_DATA = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124";

    // wrongs pt.1:
    // wrongs pt.2:
    public static void execute() {
//        var ranges = rangesFromTestData();
        var ranges = rangesFromUrl();
        long fakeIdsSum = 0;
        for (Range range : ranges) {
            var fromLong = Long.parseLong(range.from);
            var toLong = Long.parseLong(range.to);
            var closestHalf = range.from.length() % 2 == 0
                    ? range.from.substring(0, range.from.length() / 2)
                    : "1" + "0".repeat(range.from.length() / 2);
            var closestHalfLong = Long.parseLong(closestHalf);
            while (true) {
                var fakeId = String.valueOf(closestHalfLong) + closestHalfLong;
                var fakeIdLong = Long.parseLong(fakeId);
                if (fakeIdLong > toLong) {
                    break;
                } else if (fakeIdLong >= fromLong) {
                    fakeIdsSum += fakeIdLong;
                }
                closestHalfLong++;
            }
        }
        System.out.println(fakeIdsSum);

    }

    private static List<Range> rangesFromUrl() {
        return Arrays.stream(new HttpInputFetcher(EnvLoader.loadCookie()).fetchLines(URL).get(0)
                        .split(","))
                .filter(line -> !line.isBlank())
                .map(Day2::parseRange)
                .toList();
    }

    private static List<Range> rangesFromTestData() {
        return Arrays.stream(TEST_DATA.split(","))
                .filter(line -> !line.isBlank())
                .map(Day2::parseRange)
                .toList();
    }

    private static Range parseRange(String range) {
        return new Range(
                range.split("-")[0],
                range.split("-")[1])
                ;
    }

    private record Range(String from, String to){}

}
