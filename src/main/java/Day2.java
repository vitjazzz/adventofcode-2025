import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import utils.EnvLoader;
import utils.HttpInputFetcher;

public class Day2 {

    private static final String URL = "https://adventofcode.com/2025/day/2/input";
    private static final String TEST_DATA = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124";

    // wrongs pt.1:
    // wrongs pt.2: 31680313979
    public static void execute() {
//        var ranges = rangesFromTestData();
        var ranges = rangesFromUrl();
        long fakeIdsSum = 0;
        for (Range range : ranges) {
            var currentRangeFakeIds = new HashSet<Long>();
            var fromLong = Long.parseLong(range.from);
            var toLong = Long.parseLong(range.to);
            var toLength = range.to.length();
            var part = 1;
            while (true) {
                var partStr = String.valueOf(part);
                for (int i = 2; i <= toLength; i++) {
                    var fakeId = partStr.repeat(i);
                    if (fakeId.length() > toLength) {
                        continue;
                    }
                    var fakeIdLong = Long.parseLong(fakeId);
                    if (fakeIdLong >= fromLong
                            && fakeIdLong <= toLong
                            && !currentRangeFakeIds.contains(fakeIdLong)) {
                        fakeIdsSum += fakeIdLong;
                        currentRangeFakeIds.add(fakeIdLong);
                    }
                }
                if (Long.parseLong(partStr.repeat(2)) > toLong) {
                    break;
                }
                part++;
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
