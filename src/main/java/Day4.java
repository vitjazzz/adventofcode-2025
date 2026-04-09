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
        var initialArr = clone(arr);
        var neighborsCountArr = buildNeighborsCountArray(arr);
        for (int i = 0; i < neighborsCountArr.length; i++) {
            for (int j = 0; j < neighborsCountArr[0].length; j++) {
                if (neighborsCountArr[i][j] != -1 && neighborsCountArr[i][j] < 4) {
                    removeBlock(i, j, arr, neighborsCountArr);
                }
            }
        }

        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (initialArr[i][j] != arr[i][j]) {
                    res++;
                }
            }
        }

        System.out.println(res);
    }

    private static void removeBlock(int i, int j, char[][] arr, int[][] neighborsCountArr) {
        if (arr[i][j] == '.') {
            return;
        }
        arr[i][j] = '.';
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (k == 0 && l == 0) {
                    continue;
                }
                if (arr[i + k][j + l] == '.') {
                    continue;
                }
                neighborsCountArr[i + k][j + l] -= 1;
                if (neighborsCountArr[i + k][j + l] < 4) {
                    removeBlock(i + k, j + l, arr, neighborsCountArr);
                }
            }
        }
    }

    private static int[][] buildNeighborsCountArray(char[][] arr) {
        var neighborsCountArr = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == '.') {
                    neighborsCountArr[i][j] = -1;
                    continue;
                }
                int neighbors = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (k == 0 && l == 0) {
                            continue;
                        }
                        if (arr[i + k][j + l] == '@') {
                            neighbors++;
                        }
                    }
                }
                neighborsCountArr[i][j] = neighbors;
            }
        }
        return neighborsCountArr;
    }

    private static char[][] toArray(List<String> ranges) {
        char[][] result = new char[ranges.size()][];

        for (int i = 0; i < ranges.size(); i++) {
            result[i] = ranges.get(i).toCharArray();
        }

        return result;
    }

    private static char[][] toBorderedArray(char[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        char[][] borderedArray = new char[rows + 2][cols + 2];

        for (int i = 0; i < borderedArray.length; i++) {
            for (int j = 0; j < borderedArray[i].length; j++) {
                if (i == 0 || i == borderedArray.length - 1 || j == 0 || j == borderedArray[i].length - 1) {
                    borderedArray[i][j] = '.';
                } else {
                    borderedArray[i][j] = array[i - 1][j - 1];
                }
            }
        }
        return borderedArray;
    }

    private static List<String> rangesFromUrl() {
        return new HttpInputFetcher(EnvLoader.loadCookie()).fetchLines(URL).stream()
                .filter(line -> !line.isBlank())
                .toList();
    }

    private static List<String> rangesFromTestData() {
        return TEST_DATA.lines()
                .filter(line -> !line.isBlank())
                .toList();
    }



    private static char[][] clone(char[][] original) {
        var copy = new char[original.length][];

        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

}
