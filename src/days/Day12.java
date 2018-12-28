package days;

import java.util.ArrayList;

public class Day12 {
    public static void main(String[] args) {

        String input = getInput();
        String[] inputArr = input.split("\n");

        String initialState = inputArr[0].substring(15);
        System.out.println(initialState);


        // Create linked list with current situation
        ArrayList<String> pots = new ArrayList<>();
        ArrayList<String> notes = getNotes(inputArr);

        for(char ch: initialState.toCharArray()) {
            pots.add(String.valueOf(ch));
        }

        // add empty plant pots
        for(int i =0; i < 86; i++) {
            pots.add(0,".");
            pots.add(".");
        }
        for(int i =0; i < 86; i++) {
            pots.add(".");
        }



        ArrayList<String> workingCopy = new ArrayList<>();
        for(int gen = 0; gen < 120; gen++) {
            // Copy list and edit that
            workingCopy = (ArrayList<String>) pots.clone();

            // loop through current situation per 5 pots and compare to instructions.
            for (int i = 2; i < pots.size() - 2; i++) {
                String fivePots = pots.get(i - 2) + pots.get(i - 1) + pots.get(i) + pots.get(i + 1) + pots.get(i + 2);
                if (hasPlantInNextGen(fivePots, notes)) {
                    workingCopy.set(i, "#");
                } else {
                    workingCopy.set(i, ".");
                }
            }

            pots = workingCopy;
            System.out.println(pots.toString());
        }

        // count number of pots containing a plant
        int plantCounter = 0;
        for(int j = 0; j < pots.size(); j++) {
            if(pots.get(j).equals("#")) {
                plantCounter += j - 86;
            }
        }

//        System.out.println(pots.toString());
//        System.out.println(workingCopy.toString());

        System.out.println("answer is: " + plantCounter);
    }

    public static ArrayList<String> getNotes(String[] input) {
        ArrayList<String>  notes = new ArrayList<>();
        for (int i = 2; i < input.length; i++) {
            if(input[i].charAt(input[i].length() -1) == '#'){
                notes.add(input[i].substring(0, 5));
            }
        }
        return notes;
    }

    public static boolean hasPlantInNextGen(String fivePots, ArrayList<String> positiveNotes) {
        for(String note : positiveNotes) {
            if(fivePots.equals(note))
                return true;
        }
        return false;
    }

    public static String getInput() {
        return "initial state: #.##.###.#.##...##..#..##....#.#.#.#.##....##..#..####..###.####.##.#..#...#..######.#.....#..##...#\n" +
                "\n" +
                ".#.#. => .\n" +
                "...#. => #\n" +
                "..##. => .\n" + //
                "....# => .\n" +
                "##.#. => #\n" +
                ".##.# => #\n" +
                ".#### => #\n" +
                "#.#.# => #\n" +
                "#..#. => #\n" +
                "##..# => .\n" +
                "##### => .\n" +
                "...## => .\n" +
                ".#... => .\n" +
                "###.. => #\n" +
                "#..## => .\n" +
                "#...# => .\n" +
                ".#..# => #\n" +
                ".#.## => .\n" +
                "#.#.. => #\n" +
                "..... => .\n" +
                "####. => .\n" +
                "##.## => #\n" +
                "..### => #\n" +
                "#.... => .\n" +
                "###.# => .\n" +
                ".##.. => #\n" +
                "#.### => #\n" +
                "..#.# => .\n" +
                ".###. => #\n" +
                "##... => #\n" +
                "#.##. => #\n" +
                "..#.. => #";
    }
}
