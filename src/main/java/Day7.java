import java.util.List;

import utils.EnvLoader;
import utils.HttpInputFetcher;

public class Day7 {

    private static final String URL = "https://adventofcode.com/2025/day/7/input";
    private static final String TEST_DATA = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
              """;


    // wrongs pt.1:
    // wrongs pt.2: 3178, 1764
    public static void execute() {
//        var lines = linesFromTestData();
        var lines = linesFromUrl();
        char[][] grid = parseGrid(lines);
        Point start = findStart(grid);
        long[][] counts = new long[grid.length][grid[0].length];

        System.out.println(calculateTimelines(start, grid, counts));
    }


    private static long calculateTimelines(Point p, char[][] grid, long[][] counts) {
        if (p.j < 0 || p.j >= grid[0].length) {
            return 0;
        }
        int i = p.i;
        for (; i <= grid.length; i++) {
            if (i == grid.length) {
                return 1;
            }
            if (grid[i][p.j] == '^') {
                break;
            }
        }
        if (counts[i][p.j] > 0) {
            return counts[i][p.j];
        }
        long timelines = 0
                + calculateTimelines(new Point(i, p.j - 1), grid, counts)
                + calculateTimelines(new Point(i, p.j + 1), grid, counts);
        counts[i][p.j] = timelines;
        return timelines;
    }

    private static List<String> linesFromUrl() {
        return new HttpInputFetcher(EnvLoader.loadCookie()).fetchLines(URL).stream()
                .toList();
    }

    private static List<String> linesFromTestData() {
        return TEST_DATA.lines().toList();
    }

    private static char[][] parseGrid(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();

        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        return grid;
    }

    private static Point findStart(char[][] grid) {
        int startRow = -1, startCol = -1;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                }
            }
        }
        return new Point(startRow, startCol);
    }

    private record Point(int i, int j) {
    }
}
