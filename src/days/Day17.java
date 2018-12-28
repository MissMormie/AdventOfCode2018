package days;

import helpers.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day17 {
    public static Coordinate spring = new Coordinate(500, 0);

    public static void main(String[] args) {
        runA();
        runB();
    }

    public static void runA() {
        String input = getTestInput();
//        String input = getInput();
        String[] inputArray = input.split("\n");

        Map<Integer, ArrayList<Integer>> scannedGround = new HashMap<>();


        for(String line : inputArray) {
            int x, y;
            String firstCoord = line.substring(0,1);
            int firstNumCoord = Integer.valueOf(line.substring(2, line.indexOf(",")));

            int secondNumCoord = Integer.valueOf(line.substring(line.indexOf(" ")+3, line.indexOf(".")));
            int lastNumCoord = Integer.valueOf(line.substring(line.indexOf("..")+2));

        }
        System.out.println("answer A: ");
    }

    public static void runB() {
        String input = getTestInput();
//        String input = getInput();
        System.out.println("answer B: ");
    }

    public static void addToMap(int x, int y, Map<Integer, ArrayList<Integer>> map) {
        if(!map.containsKey(y)) {
            map.put(y, new ArrayList<>());
        }
        map.get(y).add(x);
    }

    public static String getTestInput() {
        return "x=495, y=2..7\n" +
                "y=7, x=495..501\n" +
                "x=501, y=3..7\n" +
                "x=498, y=2..4\n" +
                "x=506, y=1..2\n" +
                "x=498, y=10..13\n" +
                "x=504, y=10..13\n" +
                "y=13, x=498..504";
    }

    public static String getTestAnswer() {
        return "";
    }

    public static String getInput() {
        return "";

    }

}
