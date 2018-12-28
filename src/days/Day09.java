package days;

import helpers.CircularLinkedList;

import java.math.BigInteger;


public class Day09 {
    // Test values
    //    private int numPlayers = 10;
    //    int numMarbles = 1618;
    //    int answer = 8317;

    private int numPlayers = 418;
    int numMarbles = 71339*100;

    public static void main(String[] args) {
        new Day09();
    }

    public Day09() {
        CircularLinkedList<Integer> marbleList = new CircularLinkedList<>();
        marbleList.addObject(0); // First marble

        Player[] players = new Player[numPlayers];
        // Create playersList
        for(int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i);
        }

        // Loop through marbles/players.
        for(int i = 0; i < numMarbles; i++) {
            Player currentPlayer = players[i % numPlayers];
            if(i % 23 == 0) {
                scoreMarble(i, marbleList, currentPlayer);
            } else {
                addMarble(i, marbleList);
            }
        }

        // Loop through players for high score

        BigInteger highestScore = new BigInteger("0");
        for(Player player: players) {
            if(player.score.compareTo(highestScore) == +1)
                highestScore = player.score;
            System.out.println("player: " + player.id + " score: " + player.score);
        }

        System.out.println("highest score: " + highestScore);
    }

    public void scoreMarble(Integer marble, CircularLinkedList<Integer> marbleList, Player player) {
        for(int i = 0; i < 7; i++) {
            marbleList.getPrevious();
        }
        Integer removedMarble = marbleList.removeCurrent();
        player.addToScore(marble + removedMarble);
    }

    public void addMarble(Integer marble, CircularLinkedList<Integer> marbleList) {
        marbleList.getNext();
        marbleList.addObject(marble);
    }

    public class Player {
        int id;
        BigInteger score = new BigInteger("0");
        Player(int id) {
            this.id = id;
        }

        public void addToScore(int num) {
            score = new BigInteger(String.valueOf(num)).add(score);
        }
    }
}
