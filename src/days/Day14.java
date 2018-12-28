package days;

import helpers.CircularLinkedList;

public class Day14 {

    public static void main(String[] args) {
        runA();
        runB();
    }

    static Elf elf1;
    static Elf elf2;

    static CircularLinkedList<Integer> scoreBoard;
    static CircularLinkedList.Node lastAddedNode;

    public static void setUp() {

        scoreBoard = new CircularLinkedList<Integer>();

        scoreBoard.addObject(3);
        elf1 = new Elf(scoreBoard.getNode());

        scoreBoard.addObject(7);
        elf2 = new Elf(scoreBoard.getNode());

        lastAddedNode = scoreBoard.getNode();
    }

    public static void runA() {
        int repeats = 157901000;
        setUp();
        int i = 2;
        while(i < repeats + 10) {
            int sum = (Integer) elf1.currentRecipe.getObject() + (Integer) elf2.currentRecipe.getObject();
            try {
                i += createNewRecipes(sum);
            } catch (Exception e) {
                // Cheated by using an exception...
                i -= String.valueOf(findNumber).length();
                if(e.getMessage()== "2") {
                    i += 2;
                } else {
                    i++;
                }
                System.out.println("Answer B = " + i);
                break;
            }
            elf1.pickCurrentRecipe(scoreBoard);
            elf2.pickCurrentRecipe(scoreBoard);
//            printScoreboard();
        }


        scoreBoard.setCurrent(lastAddedNode).previous(9);
        if(i > repeats +10) { // Created an extra recipe
            scoreBoard.previous();
        }
        String answer = "";
        for(int j = 0; j < 10; j++) {
            answer += scoreBoard.getCurrent();
            scoreBoard.next();
        }
        System.out.println("answer A: " + answer);
    }


    public static void printScoreboard() {
        scoreBoard.setCurrent(lastAddedNode);
        do {
          System.out.print(scoreBoard.getNext() + " ");

        } while(scoreBoard.getNode() != lastAddedNode);
        System.out.println();
    }

    public static int createNewRecipes(int sum) throws Exception {
        int secondDigit = sum % 10;
        int firstDigit = sum / 10;
        scoreBoard.setCurrent(lastAddedNode);

        // Add recipes to scoreboard
        if(firstDigit != 0) {
            scoreBoard.addObject(firstDigit);
            checkDigit(firstDigit);
        }
        scoreBoard.addObject(secondDigit);
        try {
            checkDigit(secondDigit);
        } catch (Exception e) {
            throw new Exception("2");
        }

        lastAddedNode = scoreBoard.getNode();
        return sum > 9 ? 2 : 1;
    }

    static int lastNumbers = 37;
    static int findNumber = 157901;
    static int oom = Math.toIntExact((Math.round(Math.pow(10, String.valueOf(findNumber).length()))));

    public static void checkDigit(int digit) throws Exception {
        lastNumbers = (lastNumbers * 10 + digit) % oom;
        if(lastNumbers == findNumber) {
            int abc = 1;
            throw new Exception();
            // do something smart?
        }
    }


    public static void runB() {
        String input = getTestInput();
//        String input = getInput();
        System.out.println("answer B: ");
    }

    public static String getTestInput() {
        return "";
    }

    public static String getTestAnswer() {
        return "";
    }

    public static String getInput() {
        return "";
    }

    public static class Elf {
        CircularLinkedList.Node currentRecipe;

        Elf(CircularLinkedList.Node recipe) {
            currentRecipe = recipe;
        }

        public void setRecipe(CircularLinkedList.Node recipe) {
            currentRecipe = recipe;
        }

        public void pickCurrentRecipe(CircularLinkedList<Integer> scoreboard) {
            int steps = 1 + (Integer) currentRecipe.getObject();
            scoreboard.setCurrent(currentRecipe);
            scoreboard.next(steps);
            currentRecipe = scoreboard.getNode();

        }
    }

}
