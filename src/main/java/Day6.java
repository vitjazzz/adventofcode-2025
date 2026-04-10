import java.util.ArrayList;
import java.util.List;

import utils.EnvLoader;
import utils.HttpInputFetcher;

public class Day6 {

    private static final String URL = "https://adventofcode.com/2025/day/6/input";
    private static final String TEST_DATA = """
            123 328  51 64\s
               45 64  387 23\s
                6 98  215 314
              *   +   *   + \s
            """;


    // wrongs pt.1:
    // wrongs pt.2: 388947125157441
    public static void execute() {
//        var lines = linesFromTestData();
        var lines = linesFromUrl();
        var problems = parse(lines);


        long sum = 0;
        for (Problem problem : problems) {
            long solvedProblem = problem.operation() == '*' ? 1 : 0;
            for (Long number : problem.numbers) {
                solvedProblem = problem.operation() == '*'
                        ? solvedProblem * number
                        : solvedProblem + number;
            }
            sum  += solvedProblem;
        }

        System.out.println(sum);
    }

    private static List<String> linesFromUrl() {
        return new HttpInputFetcher(EnvLoader.loadCookie()).fetchLines(URL).stream()
                .toList();
    }

    private static List<String> linesFromTestData() {
        return TEST_DATA.lines().toList();
    }

    public static List<Problem> parse(List<String> lines) {
        String opsLine = lines.get(lines.size() - 1);

        List<String[]> splitRows = lines.subList(0, lines.size() - 1).stream()
                .map(line -> line.trim().split("\\s+"))
                .toList();

        String[] operations = opsLine.trim().split("\\s+");

        int columns = operations.length;

        List<Problem> result = new ArrayList<>();

        for (int col = 0; col < columns; col++) {
            List<Long> numbers = new ArrayList<>();

            for (String[] row : splitRows) {
                if (col < row.length) {
                    numbers.add(Long.parseLong(row[col]));
                }
            }

            char op = operations[col].charAt(0);

            result.add(new Problem(numbers, op));
        }

        return result;
    }


    public record Problem(List<Long> numbers, char operation) {}
}
