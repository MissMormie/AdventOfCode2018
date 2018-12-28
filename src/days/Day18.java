package days;

import helpers.Coordinate;
import helpers.Util;

import java.util.*;

public class Day18 {
    public static void main(String[] args) {
        runA();
        runB();
    }

    public static void runA() {
        String input = getInput();
        Map<String, Acre> landscape = buildLandscape(input);

        waitMinutes(10, landscape);



//        String input = getInput();
        System.out.println("answer A: " + calculateAnswer(landscape));
    }

    public static int calculateAnswer(Map<String, Acre> landscape) {
        int trees = 0;
        int open = 0;
        int lumberyard = 0;

        for (Map.Entry<String, Acre> entry : landscape.entrySet()) {
            switch(entry.getValue().acreType) {
                case OPEN: open++; break;
                case TREES: trees++; break;
                case LUMBERYARD: lumberyard++; break;
            }
        }

        return trees * lumberyard;

    }

    public static void waitMinutes(int minutes, Map<String,Acre> landscape) {
        for(int i = 0; i < minutes; i ++ ) {
            for (Map.Entry<String, Acre> entry : landscape.entrySet()) {
                entry.getValue().tick();
            }
        }
    }

    public static Map<String, Acre> buildLandscape(String input) {
        Map<String,Acre> acreMap = new HashMap();
        String[] lines = input.split("\n");
        for (int y = 0; y < lines.length; y++) {
            char[] chars = lines[y].toCharArray();
            for(int x = 0; x < chars.length; x++) {
                String coords = Util.makeCoordString(x, y);
                acreMap.put(coords, new Acre(chars[x], x, y));
            }
        }

        for(Map.Entry<String, Acre> entry : acreMap.entrySet()) {
            entry.getValue().findAdjacent(acreMap);
        }

        return acreMap;
    }


    public static void runB() {
        String input = getInput();
        Map<String, Acre> landscape = buildLandscape(input);

//        waitMinutes(10001, landscape);

        waitTillNoMoreChanges( landscape);

//        String input = getInput();
        System.out.println("answer B: " + calculateAnswer(landscape));

    }

    public static void waitTillNoMoreChanges(Map<String,Acre> landscape) {
        int newAnswer = 0;
        Map<Integer, Integer> answerCount = new HashMap<>();

        int doTicks = 1000;

        int counter = 0;
        do {
            counter++;
            waitMinutes(1, landscape);

            newAnswer = calculateAnswer(landscape);
            answerCount.put(newAnswer, answerCount.getOrDefault(newAnswer, 0) + 1);
        } while (counter < doTicks);

        int repeatsAfter = 0;
        for(Map.Entry<Integer, Integer> entry : answerCount.entrySet()) {
            if(entry.getValue() > 3) {
                repeatsAfter++;
                if(entry.getKey() < 190164) {
                    System.out.println(entry.getKey());
                }
            }
        }

        int runs = 1000000000;

        int ticksLeft = (runs - doTicks) % repeatsAfter;

        int ticksy = 10000 % 28;
        waitMinutes(ticksLeft, landscape);
        int i = 0;

    }

    public static enum AcreType {
        OPEN, TREES, LUMBERYARD;
    }

    public static class Acre extends Coordinate {
        ArrayList<Acre> adjacentAcres = new ArrayList();
        AcreType acreType;

        int currentTick = 0;
        ArrayList<AcreType> historicalTypes = new ArrayList<>();

        public Acre(char acre, int x, int y) {
            super(x, y);
            if(acre == '.') {
                acreType = AcreType.OPEN;
            } else if(acre == '|') {
                acreType = AcreType.TREES;
            } else {
                acreType = AcreType.LUMBERYARD;
            }
            historicalTypes.add(acreType);
            int i = 1;
        }

        public AcreType getHistoricalAcreType(int tick) {
            try {
                return  historicalTypes.get(tick);
            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        }

        public void findAdjacent(Map<String,Acre> acreMap) {
            for(int x = -1; x < 2; x++ ) {
                for(int y = -1; y < 2; y++ ) { // loop adjecent acres
                    String coords = Util.makeCoordString(this.x - x, this.y -y);
                    if(!coords.equals(getCoords())) {
                        Acre acre = acreMap.get(coords);
                        if (acre != null) {
                            adjacentAcres.add(acre);
                        }
                    }
                }
            }
        }

        public void tick() {
            int trees = 0;
            int open = 0;
            int lumberyard = 0;
            for(Acre acre : adjacentAcres) {
                switch(acre.getHistoricalAcreType(currentTick)) {
                    case OPEN: open++; break;
                    case TREES: trees++; break;
                    case LUMBERYARD: lumberyard++; break;
                }
            }

            switch(acreType) {
                case OPEN:
                    // An open acre will become filled with trees if three or more adjacent acres contained trees.
                    // Otherwise, nothing happens.
                    acreType = trees >= 3 ? AcreType.TREES : AcreType.OPEN;
                    break;
                case TREES:
                    // An acre filled with trees will become a lumberyard if three or more adjacent acres were lumberyards.
                    // Otherwise, nothing happens.
                    acreType = lumberyard >= 3 ? AcreType.LUMBERYARD : AcreType.TREES;
                    break;
                case LUMBERYARD:
                    // An acre containing a lumberyard will remain a lumberyard if it was adjacent to at least one
                    // other lumberyard and at least one acre containing trees. Otherwise, it becomes open.
                    if(!(lumberyard > 0 && trees > 0))
                        acreType = AcreType.OPEN;
                    break;

            }


            currentTick++;
            historicalTypes.add(acreType);
        }
    }



    public static String getTestInput() {
        return ".#.#...|#.\n" +
                ".....#|##|\n" +
                ".|..|...#.\n" +
                "..|#.....#\n" +
                "#.#|||#|#|\n" +
                "...#.||...\n" +
                ".|....|...\n" +
                "||...#|.#|\n" +
                "|.||||..|.\n" +
                "...#.|..|.";
    }

    public static String getTestAnswer() {
        return "";
    }

    public static String getInput() {
        return ".||....##....|#|#.....|||...|............|......#.\n" +
                "#....|#|..#....#....|...#.||...||..|||#|..#..|.##.\n" +
                ".#|##|.#.|#|..|||....#|..|....|.##.#||#.|.#|..|..|\n" +
                "|...|#.|...#..|...|#.|.#...##....|.||#...|...|...#\n" +
                ".....|..#||..........###...#.||.###.|..#|#||..|#..\n" +
                "|||.|##...|.|||#......###....|#.#|...|.#..|.|.|##.\n" +
                ".......|.####.||##|.##....#............||#..#..|..\n" +
                "..#|.|....#..|...|||..|...............|.|#..|.||#.\n" +
                ".||#.#||.|.|.#.#|....#.#|..|.|#|.|.....|.#...|#..#\n" +
                "......#..|...#....||#.#.#|..#...#.|||||..|#....|#|\n" +
                "#.#|..#|#||#|||..#...#.........|.|..#...|......|.#\n" +
                "|....|#..|##.....|...||.#....#......#...|..|#|#..#\n" +
                ".|##.|...|......##.##.........#.#..|.||........|..\n" +
                ".#||#.#...|.|.|.#|.#..|.|#..|##.|..|#....##.||....\n" +
                "...|..|.#........#.|###|.#|...||.#....|..#.....#|.\n" +
                "........#.###..###.....#....#|#|...#||..|..|....#|\n" +
                ".|...........||.|...#|..|#....#|.#..|#..|..|.|.|..\n" +
                "#.#..|.|||....|.....#|#...##.#......|..|..#..#.#|#\n" +
                ".#|.#.........|.....##|##.#.#...#|...........#|#..\n" +
                "..##|.|.|.##|.##..|..|...#|#..|.....|.|.#...#...||\n" +
                ".|||..#.#.|.|#......####.........#|.|.#|.|.|.#.|..\n" +
                "|#...|.........#.##..|....|....#|...||.#.|...|#.|#\n" +
                "##.|.#..|#|#|#.|#.|##|..#.|..##.#....##.#...#|.|..\n" +
                ".||#..#.#....|.#.#..#|.|.#|##|#.#||....#....#...|.\n" +
                "#...#...|.|||.....#.|.#|..#......#.#...#.#|...|#||\n" +
                "...##...|.#.##..||..#|.....|....#.##.#.|..|.|#.#.|\n" +
                "..#..|...#.|..#......||....#.#|..##|.#....#.|.|...\n" +
                "||.....|..|##.....##......#|.......|##.|.#.|.#.|..\n" +
                "#|.|...|.|#...|...........#......|......|...#|.#..\n" +
                "#|###|..#.##.||..#....|####.#.......#|..|...|.....\n" +
                "........#.|.........#..##.#.#...|.#.....|.|.#.#|#.\n" +
                ".##.##.#.|#|..#.###|...#....|#.|#.#|#....#.|...|..\n" +
                "||...#......|||..#.|.||.|.|#..........#...#.|.|..#\n" +
                "|.....|..|....#|.#|.#...|#..|#|#..#.###.|.....#.#.\n" +
                "..#...|#..#...|||..###.|#.|..|......|.||....#.....\n" +
                ".##.#||..#....#.#.|..|.....#...|..#.|....#..##..||\n" +
                "........#..............#.||.#........|.|...|.|....\n" +
                "..#.#..##..|.|..|#....||#...|.#...#|..|##..|...##|\n" +
                "......#|##..#..........#...||.#|.||.|..|..|....|.#\n" +
                "##..|.##|..#|#|#.|....|.#|..|#.#...#..##|#.##|.|..\n" +
                "|...#.|.#.#..|..|....|#||...#..#....#..#|.......#.\n" +
                "....#.###.#.|.....#.|.#.#.#...|.#|#...#|.....|.##.\n" +
                "#......#..#.|.....||.#..|....|...#|||....|..|#.|..\n" +
                "..|.#|##...#.#..#|...|........||#.#.#||.|#.#|#...|\n" +
                "...|.|.#...|.|.....###|#.##|.#.....#|..|#|#|#.|#|.\n" +
                ".##..|#.#..#....|#....|..|.||.#|..|.|.|.|..#.#.|..\n" +
                ".#..|.|##||...|......|...#..#|.#....#...#|||...||.\n" +
                ".|#.#....|#.#|.|....||.||.##|#...#.||.#.......#|..\n" +
                "#...||.....|.|...|||...||....#..#....||.|#.|...|#.\n" +
                "...|#..||....|#..|....|...|.||#..||..#.|..##......";

    }

}
