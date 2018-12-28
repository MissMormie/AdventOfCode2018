package days;

import helpers.Coordinate;

import java.util.*;

public class Day11 {
//
    public static int gridSerial = 5535;
    public static int gridWidth = 300;
    public static int gridHeight = 300;

//    public static int gridSerial = 8;
//    public static int gridWidth = 5;
//    public static int gridHeight = 5;

//    public static int gridSerial = 18;
//    public static int gridWidth = 300;
//    public static int gridHeight = 300;
    // Expected answer for B: 90,269,16

    public static void main(String[] args) {
        runA();
        runB();
    }

    public static void runA() {
        Map<String, Integer> powerGrid = fillGrid();

        calculateGrid(powerGrid);
    }


    public static void runB() {
        Map<String, Integer> powerGrid = fillGrid();
        MaxSquare answer = calculateGridWithBestSquare(powerGrid);

        System.out.println("answer B: " + answer.toString());
    }

    public static MaxSquare calculateGridWithBestSquare(Map<String, Integer> powerGrid) {
        List<MaxSquare> squares = new ArrayList<>();
        for(int x = 1; x <= gridWidth - 3; x++) {
            System.out.println("calculating x: " + x);
            for (int y = 1; y <= gridHeight - 3; y++) {
                // x,y is topleft
                MaxSquare bestSquare = calculateBestSquare(powerGrid, x, y);
                squares.add(bestSquare);
            }
        }

        Collections.sort(squares);
        return squares.get(0);

    }


    public static void calculateGrid(Map<String, Integer> powerGrid) {
        Map<String, Integer> powerSquared = new HashMap<>();
        for(int x = 1; x <= gridWidth - 3; x++) {
            for (int y = 1; y <= gridHeight - 3; y++) {
                // x,y is topleft
                int squarePower = calculate3x3(powerGrid, x, y);
                powerSquared.put("" + x + "," + y, squarePower);
            }
        }

        int highestEnergy = -10;
        String coord = "";
        for(Map.Entry<String, Integer> entry : powerSquared.entrySet()) {
            if(entry.getValue() > highestEnergy) {
                highestEnergy = entry.getValue();
                coord = entry.getKey();
            }
        }

        System.out.println("Answer A Coord: " + coord + " energy: " + highestEnergy);

        // Get highest in grid.
    }

    public static MaxSquare calculateBestSquare(Map<String, Integer> powerGrid, int x, int y) {
        int maxSquareWidth = gridWidth - x;
        int maxSquareHeight = gridHeight - y;
        int maxSquare = maxSquareWidth > maxSquareHeight ? maxSquareHeight : maxSquareWidth;
        // Calculate 3x3 grid first, add row and column for 4, 5 etc.

        Map<Integer, Integer> powerSquares = new HashMap<>(); // First int is size of square, second power in square.

        int lastSquare = calculate3x3(powerGrid, x, y);

        int squarePower = lastSquare;
        powerSquares.put(3, squarePower);

        // Loop through the available square sizes.
        for(int i = 3; i <= maxSquare ; i++) { // i = 3 because we already did 3*3 square

            // Calculate next column
            for(int j = 0; j <= (i - 1); j++) { // last item of column is also part of row
                squarePower += getPower(powerGrid, x + i, y + j);
            }

            // Calculate next row
            for(int xCoord = x; xCoord <= x + i; xCoord++) {
                squarePower += getPower(powerGrid, xCoord, y +i);
            }

            // Save result
            powerSquares.put(i + 1, squarePower);
        }


        // Create a mini
        Integer max= powerSquares.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        return new MaxSquare(max, powerSquares.get(max), x, y);
    }

    public static class MaxSquare implements Comparable<MaxSquare> {
        public final int size;
        public final int power;
        public final Coordinate coord;

        public MaxSquare(int size, int power, int x, int y) {
            this.size = size;
            this.power = power;
            this.coord = new Coordinate(x, y);
        }

        @Override
        public int compareTo(MaxSquare o) {
            if(power > o.power) {
                return -1;
            } else if(power == o.power) {
                return 0;
            }
            // todo order by highest power
            return 1;
        }

        @Override
        public String toString() {
            return coord.getCoords() + "," + size;
        }
    }

    public static int getPower(Map<String, Integer> powerGrid, int x, int y){
        return powerGrid.get("" + (x) + "," + (y));
    }

    public static int calculate3x3(Map<String, Integer> powerGrid, int x, int y) {

        List<String> coords = new ArrayList();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                coords.add("" + (x + i) + "," + (y + j));
            }
        }

        int squarePower = 0;
        for(String coord : coords) {
            squarePower += powerGrid.get(coord);
        }
        return squarePower;
    }


    public static Map<String, Integer> fillGrid() {
        Map<String, Integer> powerGrid = new HashMap<>();

        for(int x = 1; x <= gridWidth; x++) {
            for(int y = 1; y <= gridHeight; y++) {
                Coordinate coord = new Coordinate(x, y);
                // Find the fuel cell's rack ID, which is its X coordinate plus 10.
                int rackId = x + 10;
                // Begin with a power level of the rack ID times the Y coordinate.
                int powerLevel = rackId * y;
                // Increase the power level by the value of the grid serial number (your puzzle input).
                powerLevel += gridSerial;
                // Set the power level to itself multiplied by the rack ID
                powerLevel *= rackId;
                // Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
                int hundredDigit = (powerLevel % 1000) / 100;
                // Subtract 5 from the power level
                powerLevel = hundredDigit - 5;

                // todo Save to map (probably shouldn't be map, get back later..)
                powerGrid.put(coord.getCoords(), powerLevel);
            }
        }
        return  powerGrid;
    }



}
