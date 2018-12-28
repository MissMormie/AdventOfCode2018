package days;

import helpers.Coordinate;

import java.util.*;

import static java.lang.System.exit;

public class Day06 {

    public static void main(String[] args) {
        runA();
        runB();
    }

    public static void runA() {
        String input = getTestInput();
        input = getInput();

        // Read in all coords.
        ArrayList<Coordinate> points = readInCoords(input);

        // Find box for coords
        int smallestX = points.stream().min((coord1,  coord2) -> coord1.x - coord2.x).get().x;
        int biggestX = points.stream().max((coord1,  coord2) -> coord1.x - coord2.x).get().x;
        int smallestY = points.stream().min((coord1,  coord2) -> coord1.y - coord2.y).get().y;
        int biggestY = points.stream().max((coord1,  coord2) -> coord1.y - coord2.y).get().y;


        // Loop over all spots and find area they belong to.
        Map<Coordinate, ArrayList<String>> coordMap = new HashMap<>(); //
        for(int y = smallestY; y <= biggestY; y++) {
            for (int x = smallestX; x <= biggestX; x++) {
                Optional<Coordinate> point = findSingleClosestPoint(points, x, y);
                if(!point.isPresent()) // Not a single point closest
                    continue;
                if(!coordMap.containsKey(point.get())) {
                    coordMap.put(point.get(), new ArrayList());
                }
                coordMap.get(point.get()).add(Coordinate.makeCoordString(x, y));
            }
        }

        // Take out edge areas


        Set<Coordinate> removeCoords = new HashSet<>();
        boolean sidesOnly = false;
        for(int y = smallestY; y <= biggestY; y++) {
            if(y != smallestY && y != biggestY) {
                sidesOnly = true;
            } else {
                sidesOnly = false;
            }
            for (int x = smallestX; x <= biggestX; x++) {
                Optional<Coordinate> closest = findSingleClosestPoint(points, x, y);
                addPointToSet( closest, removeCoords);
                if(sidesOnly && x == smallestX) x = biggestX -1;
            }
        }

        // remove from map edge cases
        for(Coordinate coord : removeCoords) {
            coordMap.remove(coord);
        }

        Map.Entry<Coordinate, ArrayList<String>> result = coordMap.entrySet().stream().max((e, e1) -> e.getValue().size() - e1.getValue().size()).get();

        // Count amount of occurrences



        System.out.println("answer A: " + result.getValue().size());
    }

    public static void addPointToSet( Optional<Coordinate> coord, Set<Coordinate> coordSet) {
        if(coord.isPresent()) {
            coordSet.add(coord.get());
        }
    }


    public static int calculateTotalDistanceFromAllPoints(ArrayList<Coordinate> points, int x, int y) {
        return points
                .stream()
                .map(coordinate -> coordinate.getManhattanDistance(x, y))
                .reduce(0, Integer::sum);
    }

    public static Optional<Coordinate> findSingleClosestPoint(ArrayList<Coordinate> points, int x, int y) {
        // This works for a single min, but there might be several.
        Optional<Coordinate> opt = points.stream().min((c1, c2) -> c1.getManhattanDistance(x,y) - c2.getManhattanDistance(x, y));

        // Checking if other points have the same distance
        int distance = opt.get().getManhattanDistance(x,y);
        long occurences = points.stream().filter(c -> c.getManhattanDistance(x,y) == distance).count();
        if(occurences > 1) {
            return Optional.empty();
        }
        return  opt;
    }


    public static ArrayList<Coordinate> readInCoords(String input) {
        String[] inputArray = input.split("\n");
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for(String line: inputArray) {
            int x = Integer.valueOf(line.substring(0, line.indexOf(",")));
            int y = Integer.valueOf(line.substring(line.indexOf(" ")+1));
            coordinates.add(new Coordinate(x, y));
        }

        return coordinates;
    }

    public static void runB() {
        // Test case


        String input = getTestInput();
        input = getInput();

        // Read in all coords.
        ArrayList<Coordinate> points = readInCoords(input);

        // Find box for coords
        int smallestX = points.stream().min((coord1,  coord2) -> coord1.x - coord2.x).get().x;
        int biggestX = points.stream().max((coord1,  coord2) -> coord1.x - coord2.x).get().x;
        int smallestY = points.stream().min((coord1,  coord2) -> coord1.y - coord2.y).get().y;
        int biggestY = points.stream().max((coord1,  coord2) -> coord1.y - coord2.y).get().y;


        // Loop over all spots and find area they belong to.
        int count = 0;
        for(int y = smallestY; y <= biggestY; y++) {
            for (int x = smallestX; x <= biggestX; x++) {
                int distanceFromAllPoints = calculateTotalDistanceFromAllPoints(points, x, y);

                if(distanceFromAllPoints >= 10_000) continue; // max distance

                count++;
            }
        }

        System.out.println("answer B: " + count);
    }

    public static String getTestInput() {
        return "1, 1\n" +
                "1, 6\n" +
                "8, 3\n" +
                "3, 4\n" +
                "5, 5\n" +
                "8, 9";
    }

    public static String getTestAnswer() {
        return "17";
    }

    public static String getInput() {
        return "342, 203\n" +
                "79, 64\n" +
                "268, 323\n" +
                "239, 131\n" +
                "246, 87\n" +
                "161, 93\n" +
                "306, 146\n" +
                "43, 146\n" +
                "57, 112\n" +
                "241, 277\n" +
                "304, 303\n" +
                "143, 235\n" +
                "253, 318\n" +
                "97, 103\n" +
                "200, 250\n" +
                "67, 207\n" +
                "345, 149\n" +
                "133, 222\n" +
                "232, 123\n" +
                "156, 359\n" +
                "80, 224\n" +
                "51, 145\n" +
                "138, 312\n" +
                "339, 294\n" +
                "297, 256\n" +
                "163, 311\n" +
                "241, 321\n" +
                "126, 66\n" +
                "145, 171\n" +
                "359, 184\n" +
                "241, 58\n" +
                "108, 312\n" +
                "117, 118\n" +
                "101, 180\n" +
                "58, 290\n" +
                "324, 42\n" +
                "141, 190\n" +
                "270, 149\n" +
                "209, 294\n" +
                "296, 345\n" +
                "68, 266\n" +
                "233, 281\n" +
                "305, 183\n" +
                "245, 230\n" +
                "161, 295\n" +
                "335, 352\n" +
                "93, 66\n" +
                "227, 59\n" +
                "264, 249\n" +
                "116, 173";

    }
}
