package days;

import helpers.VelocityMovingObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day10 {

    public static void main(String[] args) {
        runA();
        runB();
    }

    static int ticksPassed = 0;
    public static void runA() {
        String input = getInput();

        String[] inputArray = input.split("\n");

        ArrayList<VelocityMovingObject> lights = createLights(inputArray);
        boolean converging = true;

        while(converging) {
//            showMessage(lights);

            tick(lights);
            ticksPassed++;
            converging = stillConverging(lights);
        }

        tickBack(lights);

        showMessage(lights);
        ticksPassed--;
        System.out.println("answer A: ticks passed" + ticksPassed  );
    }

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[35m";
    public static void showMessage(List<VelocityMovingObject> lights) {
        Collections.sort(lights);

        // find lowest X
        int lowestX = 5000000;
        int highestX = -5000000;
        int lowestY = lights.get(0).getCoordinate().y;
        int highestY = lights.get(lights.size()-1).getCoordinate().y;

        for(VelocityMovingObject light : lights) {
            if(light.getCoordinate().x < lowestX) {
                lowestX = light.getCoordinate().x;
            }
            if(light.getCoordinate().x > highestX) {
                highestX = light.getCoordinate().x;
            }
        }

        List<VelocityMovingObject> workingCopy = new ArrayList<>();
        for(VelocityMovingObject light : lights) {
            workingCopy.add(light);
        }


        for(int y = lowestY; y <= highestY; y++) {
            for(int x = lowestX; x <= highestX; x++ ) {
                if(workingCopy.size() > 0 ) {
                    if (workingCopy.get(0).getCoordinate().getCoords().equals("" + x + "," + y)) {
                        System.out.print(ANSI_YELLOW + "#" + ANSI_RESET);
                        // There may be more lights at the same coord, gotta get them all.
                        while(workingCopy.size() > 0 && workingCopy.get(0).getCoordinate().getCoords().equals("" + x + "," + y)) {
                            workingCopy.remove(0);
                        }
                    } else {
                        System.out.print(ANSI_BLACK + "." + ANSI_RESET);
                    }
                } else {
                    System.out.print(ANSI_BLACK + "." + ANSI_RESET);
                }
            }
            System.out.println(" ");
        }
        System.out.println("\n------------------------------------------------ \n");

    }


    static int maxDistance = 5000000;
    public static boolean stillConverging(List<VelocityMovingObject> lights) {
        // Find the point where the lights start drifting apart again.
        Collections.sort(lights);
        int smallestY = lights.get(0).getCoordinate().y;
        int biggestY = lights.get(lights.size()-1).getCoordinate().y;
        if(biggestY - smallestY > maxDistance ) {
            return false;
        }
        maxDistance = biggestY - smallestY;
        return true;

    }

    public static void tickBack(ArrayList<VelocityMovingObject> lights) {
        for(VelocityMovingObject light : lights) {
            light.moveBack();
        }
    }

    public static void tick(ArrayList<VelocityMovingObject> lights) {
        for(VelocityMovingObject light : lights) {
            light.move();
        }
    }

    public static ArrayList<VelocityMovingObject> createLights(String[] inputArray) {

        ArrayList<VelocityMovingObject> lights = new ArrayList<>();
        for(String line : inputArray) {
            String[] pieces =line.split("=<");

            // Set x and y
            int x = Integer.valueOf(pieces[1].substring(0, pieces[1].indexOf(",")).trim());
            int y = Integer.valueOf(pieces[1].substring(pieces[1].indexOf(",")+1, pieces[1].indexOf(">")).trim());

            // Set velocities.
            int velX = Integer.valueOf(pieces[2].substring(0, pieces[2].indexOf(",")).trim());;
            int velY = Integer.valueOf(pieces[2].substring(pieces[2].indexOf(",")+1, pieces[2].indexOf(">")).trim());;

            lights.add(new VelocityMovingObject(x, y, velX, velY));
        }
        return lights;
    }


    public static void runB() {
        String input = getTestInput();
//        String input = getInput();
        System.out.println("answer B: ");
    }

    public static String getTestInput() {
        return "position=< 9,  1> velocity=< 0,  2>\n" +
                "position=< 7,  0> velocity=<-1,  0>\n" +
                "position=< 3, -2> velocity=<-1,  1>\n" +
                "position=< 6, 10> velocity=<-2, -1>\n" +
                "position=< 2, -4> velocity=< 2,  2>\n" +
                "position=<-6, 10> velocity=< 2, -2>\n" +
                "position=< 1,  8> velocity=< 1, -1>\n" +
                "position=< 1,  7> velocity=< 1,  0>\n" +
                "position=<-3, 11> velocity=< 1, -2>\n" +
                "position=< 7,  6> velocity=<-1, -1>\n" +
                "position=<-2,  3> velocity=< 1,  0>\n" +
                "position=<-4,  3> velocity=< 2,  0>\n" +
                "position=<10, -3> velocity=<-1,  1>\n" +
                "position=< 5, 11> velocity=< 1, -2>\n" +
                "position=< 4,  7> velocity=< 0, -1>\n" +
                "position=< 8, -2> velocity=< 0,  1>\n" +
                "position=<15,  0> velocity=<-2,  0>\n" +
                "position=< 1,  6> velocity=< 1,  0>\n" +
                "position=< 8,  9> velocity=< 0, -1>\n" +
                "position=< 3,  3> velocity=<-1,  1>\n" +
                "position=< 0,  5> velocity=< 0, -1>\n" +
                "position=<-2,  2> velocity=< 2,  0>\n" +
                "position=< 5, -2> velocity=< 1,  2>\n" +
                "position=< 1,  4> velocity=< 2,  1>\n" +
                "position=<-2,  7> velocity=< 2, -2>\n" +
                "position=< 3,  6> velocity=<-1, -1>\n" +
                "position=< 5,  0> velocity=< 1,  0>\n" +
                "position=<-6,  0> velocity=< 2,  0>\n" +
                "position=< 5,  9> velocity=< 1, -2>\n" +
                "position=<14,  7> velocity=<-2,  0>\n" +
                "position=<-3,  6> velocity=< 2, -1>";
    }

    public static String getTestAnswer() {
        return "3";
    }

    public static String getInput() {
        return "position=< 53050, -42120> velocity=<-5,  4>\n" +
                "position=<-21031, -52696> velocity=< 2,  5>\n" +
                "position=<-42187, -31543> velocity=< 4,  3>\n" +
                "position=< 31896, -10392> velocity=<-3,  1>\n" +
                "position=<-42187, -42121> velocity=< 4,  4>\n" +
                "position=<-52704,  42488> velocity=< 5, -4>\n" +
                "position=< 53041, -52700> velocity=<-5,  5>\n" +
                "position=<-10420, -20969> velocity=< 1,  2>\n" +
                "position=< 31856,  53065> velocity=<-3, -5>\n" +
                "position=<-42134,  21335> velocity=< 4, -2>\n" +
                "position=<-31557, -52698> velocity=< 3,  5>\n" +
                "position=<-52752,  10758> velocity=< 5, -1>\n" +
                "position=<-42174,  42495> velocity=< 4, -4>\n" +
                "position=< 21276, -20974> velocity=<-2,  2>\n" +
                "position=<-42139,  10759> velocity=< 4, -1>\n" +
                "position=<-10451,  21343> velocity=< 1, -2>\n" +
                "position=< 53048, -10388> velocity=<-5,  1>\n" +
                "position=<-42155,  21343> velocity=< 4, -2>\n" +
                "position=<-42170, -20974> velocity=< 4,  2>\n" +
                "position=<-10440,  10766> velocity=< 1, -1>\n" +
                "position=< 42487, -10388> velocity=<-4,  1>\n" +
                "position=< 10742,  42492> velocity=<-1, -4>\n" +
                "position=<-10395, -31543> velocity=< 1,  3>\n" +
                "position=< 10711, -42121> velocity=<-1,  4>\n" +
                "position=< 10698,  31919> velocity=<-1, -3>\n" +
                "position=< 53022, -10390> velocity=<-5,  1>\n" +
                "position=<-31582,  10765> velocity=< 3, -1>\n" +
                "position=< 21328, -20966> velocity=<-2,  2>\n" +
                "position=<-20982,  21339> velocity=< 2, -2>\n" +
                "position=<-42167,  10757> velocity=< 4, -1>\n" +
                "position=< 31860, -52699> velocity=<-3,  5>\n" +
                "position=< 53058, -10389> velocity=<-5,  1>\n" +
                "position=<-21030, -20974> velocity=< 2,  2>\n" +
                "position=<-21033, -20965> velocity=< 2,  2>\n" +
                "position=<-52748,  53068> velocity=< 5, -5>\n" +
                "position=< 21319,  53067> velocity=<-2, -5>\n" +
                "position=< 31852,  53072> velocity=<-3, -5>\n" +
                "position=<-10456,  21334> velocity=< 1, -2>\n" +
                "position=<-20982,  10766> velocity=< 2, -1>\n" +
                "position=<-42178,  42493> velocity=< 4, -4>\n" +
                "position=<-42182, -10395> velocity=< 4,  1>\n" +
                "position=< 31860, -42120> velocity=<-3,  4>\n" +
                "position=<-21016, -20965> velocity=< 2,  2>\n" +
                "position=<-42187, -20967> velocity=< 4,  2>\n" +
                "position=< 10751, -10391> velocity=<-1,  1>\n" +
                "position=< 53043, -10394> velocity=<-5,  1>\n" +
                "position=<-42136,  21343> velocity=< 4, -2>\n" +
                "position=< 10746, -42124> velocity=<-1,  4>\n" +
                "position=<-31609,  21334> velocity=< 3, -2>\n" +
                "position=< 31885, -31550> velocity=<-3,  3>\n" +
                "position=< 10730,  31916> velocity=<-1, -3>\n" +
                "position=< 21333, -20965> velocity=<-2,  2>\n" +
                "position=<-10431, -42124> velocity=< 1,  4>\n" +
                "position=< 53030,  10760> velocity=<-5, -1>\n" +
                "position=< 42455,  31917> velocity=<-4, -3>\n" +
                "position=<-10451,  53067> velocity=< 1, -5>\n" +
                "position=<-42168,  42497> velocity=< 4, -4>\n" +
                "position=<-31609,  10766> velocity=< 3, -1>\n" +
                "position=<-10443,  53073> velocity=< 1, -5>\n" +
                "position=< 10714,  10766> velocity=<-1, -1>\n" +
                "position=<-52716, -10393> velocity=< 5,  1>\n" +
                "position=<-42131,  42494> velocity=< 4, -4>\n" +
                "position=<-52716, -20969> velocity=< 5,  2>\n" +
                "position=< 31888,  42493> velocity=<-3, -4>\n" +
                "position=<-10412, -10392> velocity=< 1,  1>\n" +
                "position=<-42174, -10394> velocity=< 4,  1>\n" +
                "position=<-52708, -20970> velocity=< 5,  2>\n" +
                "position=< 31868,  21336> velocity=<-3, -2>\n" +
                "position=<-10415, -31542> velocity=< 1,  3>\n" +
                "position=< 31865,  31919> velocity=<-3, -3>\n" +
                "position=<-21020, -42126> velocity=< 2,  4>\n" +
                "position=<-10412,  53070> velocity=< 1, -5>\n" +
                "position=<-52715,  10757> velocity=< 5, -1>\n" +
                "position=< 21299,  10763> velocity=<-2, -1>\n" +
                "position=<-52720, -31544> velocity=< 5,  3>\n" +
                "position=< 21299,  31911> velocity=<-2, -3>\n" +
                "position=<-42150, -20972> velocity=< 4,  2>\n" +
                "position=<-31578, -42121> velocity=< 3,  4>\n" +
                "position=< 31860, -52696> velocity=<-3,  5>\n" +
                "position=< 42448, -20965> velocity=<-4,  2>\n" +
                "position=< 53054, -20967> velocity=<-5,  2>\n" +
                "position=<-31590,  21338> velocity=< 3, -2>\n" +
                "position=<-42163, -10393> velocity=< 4,  1>\n" +
                "position=< 42461, -52702> velocity=<-4,  5>\n" +
                "position=<-42150,  21341> velocity=< 4, -2>\n" +
                "position=<-10419,  42491> velocity=< 1, -4>\n" +
                "position=<-52748,  10760> velocity=< 5, -1>\n" +
                "position=<-20977, -52698> velocity=< 2,  5>\n" +
                "position=< 31900, -42125> velocity=<-3,  4>\n" +
                "position=< 10735,  21338> velocity=<-1, -2>\n" +
                "position=< 53031, -31547> velocity=<-5,  3>\n" +
                "position=<-52721,  10757> velocity=< 5, -1>\n" +
                "position=<-52751, -31547> velocity=< 5,  3>\n" +
                "position=< 42486, -10388> velocity=<-4,  1>\n" +
                "position=<-52738,  31914> velocity=< 5, -3>\n" +
                "position=<-20977,  10763> velocity=< 2, -1>\n" +
                "position=<-42169,  53069> velocity=< 4, -5>\n" +
                "position=< 42448,  42492> velocity=<-4, -4>\n" +
                "position=<-10447, -10396> velocity=< 1,  1>\n" +
                "position=< 42441,  10762> velocity=<-4, -1>\n" +
                "position=<-52756, -42121> velocity=< 5,  4>\n" +
                "position=<-10415, -52696> velocity=< 1,  5>\n" +
                "position=<-52756,  21342> velocity=< 5, -2>\n" +
                "position=<-31554, -31547> velocity=< 3,  3>\n" +
                "position=< 21331,  42493> velocity=<-2, -4>\n" +
                "position=< 42453, -52697> velocity=<-4,  5>\n" +
                "position=< 53040,  31911> velocity=<-5, -3>\n" +
                "position=<-10443,  31920> velocity=< 1, -3>\n" +
                "position=<-52754, -20969> velocity=< 5,  2>\n" +
                "position=< 10719,  31920> velocity=<-1, -3>\n" +
                "position=< 10750,  21339> velocity=<-1, -2>\n" +
                "position=<-21009,  42490> velocity=< 2, -4>\n" +
                "position=<-20996,  10764> velocity=< 2, -1>\n" +
                "position=<-52761,  21343> velocity=< 5, -2>\n" +
                "position=< 10735, -20968> velocity=<-1,  2>\n" +
                "position=<-21028, -20974> velocity=< 2,  2>\n" +
                "position=< 31881, -52705> velocity=<-3,  5>\n" +
                "position=< 31860,  31917> velocity=<-3, -3>\n" +
                "position=<-42153, -31546> velocity=< 4,  3>\n" +
                "position=< 42448,  53074> velocity=<-4, -5>\n" +
                "position=<-31578,  42496> velocity=< 3, -4>\n" +
                "position=< 42439,  10757> velocity=<-4, -1>\n" +
                "position=<-10404, -20974> velocity=< 1,  2>\n" +
                "position=<-20993, -20966> velocity=< 2,  2>\n" +
                "position=< 42429, -52697> velocity=<-4,  5>\n" +
                "position=< 31910,  42488> velocity=<-3, -4>\n" +
                "position=< 53038, -31549> velocity=<-5,  3>\n" +
                "position=< 31888,  31912> velocity=<-3, -3>\n" +
                "position=<-31576,  42488> velocity=< 3, -4>\n" +
                "position=< 10714,  10765> velocity=<-1, -1>\n" +
                "position=< 53041,  10762> velocity=<-5, -1>\n" +
                "position=< 21301, -42122> velocity=<-2,  4>\n" +
                "position=< 21319, -42121> velocity=<-2,  4>\n" +
                "position=< 31909, -52705> velocity=<-3,  5>\n" +
                "position=<-10435, -52705> velocity=< 1,  5>\n" +
                "position=<-42170,  42492> velocity=< 4, -4>\n" +
                "position=<-20977,  31912> velocity=< 2, -3>\n" +
                "position=<-42171,  31918> velocity=< 4, -3>\n" +
                "position=< 31884, -42123> velocity=<-3,  4>\n" +
                "position=< 21286,  53065> velocity=<-2, -5>\n" +
                "position=<-10446,  31911> velocity=< 1, -3>\n" +
                "position=<-52711, -52700> velocity=< 5,  5>\n" +
                "position=< 42473,  53066> velocity=<-4, -5>\n" +
                "position=< 10759,  10758> velocity=<-1, -1>\n" +
                "position=<-20982, -42123> velocity=< 2,  4>\n" +
                "position=<-42131,  21341> velocity=< 4, -2>\n" +
                "position=<-10405,  21334> velocity=< 1, -2>\n" +
                "position=<-42147, -52698> velocity=< 4,  5>\n" +
                "position=<-21013,  53074> velocity=< 2, -5>\n" +
                "position=<-31557, -31545> velocity=< 3,  3>\n" +
                "position=<-10403,  21343> velocity=< 1, -2>\n" +
                "position=< 53014, -10388> velocity=<-5,  1>\n" +
                "position=<-42160, -31544> velocity=< 4,  3>\n" +
                "position=<-42169, -20965> velocity=< 4,  2>\n" +
                "position=<-42163, -31549> velocity=< 4,  3>\n" +
                "position=< 10730, -52701> velocity=<-1,  5>\n" +
                "position=<-31586, -10397> velocity=< 3,  1>\n" +
                "position=<-20976, -10388> velocity=< 2,  1>\n" +
                "position=<-21005, -10396> velocity=< 2,  1>\n" +
                "position=<-42155, -20965> velocity=< 4,  2>\n" +
                "position=< 42486,  31920> velocity=<-4, -3>\n" +
                "position=< 53055,  10766> velocity=<-5, -1>\n" +
                "position=< 21295, -10397> velocity=<-2,  1>\n" +
                "position=< 21324, -10397> velocity=<-2,  1>\n" +
                "position=< 42479,  31911> velocity=<-4, -3>\n" +
                "position=<-21017, -31551> velocity=< 2,  3>\n" +
                "position=<-31602, -20966> velocity=< 3,  2>\n" +
                "position=<-10396,  10766> velocity=< 1, -1>\n" +
                "position=< 42481, -42123> velocity=<-4,  4>\n" +
                "position=< 21331, -31550> velocity=<-2,  3>\n" +
                "position=<-52727, -10394> velocity=< 5,  1>\n" +
                "position=<-20991, -10388> velocity=< 2,  1>\n" +
                "position=< 31881, -42119> velocity=<-3,  4>\n" +
                "position=<-42182, -20973> velocity=< 4,  2>\n" +
                "position=<-31551,  42497> velocity=< 3, -4>\n" +
                "position=<-31567,  42488> velocity=< 3, -4>\n" +
                "position=< 53054,  53066> velocity=<-5, -5>\n" +
                "position=<-31601, -20973> velocity=< 3,  2>\n" +
                "position=<-42163,  31918> velocity=< 4, -3>\n" +
                "position=< 42439, -52700> velocity=<-4,  5>\n" +
                "position=< 21310,  42488> velocity=<-2, -4>\n" +
                "position=<-42155, -42120> velocity=< 4,  4>\n" +
                "position=<-31584,  42494> velocity=< 3, -4>\n" +
                "position=< 53035, -42128> velocity=<-5,  4>\n" +
                "position=<-20977, -42127> velocity=< 2,  4>\n" +
                "position=<-10406,  42497> velocity=< 1, -4>\n" +
                "position=< 31861,  21335> velocity=<-3, -2>\n" +
                "position=< 31889, -20966> velocity=<-3,  2>\n" +
                "position=< 31877, -20969> velocity=<-3,  2>\n" +
                "position=< 31864, -20973> velocity=<-3,  2>\n" +
                "position=< 21323, -10391> velocity=<-2,  1>\n" +
                "position=< 10711,  10766> velocity=<-1, -1>\n" +
                "position=<-52720,  53073> velocity=< 5, -5>\n" +
                "position=<-31602, -10394> velocity=< 3,  1>\n" +
                "position=<-31575,  53065> velocity=< 3, -5>\n" +
                "position=<-31597, -10388> velocity=< 3,  1>\n" +
                "position=<-31586, -52704> velocity=< 3,  5>\n" +
                "position=<-42128, -20974> velocity=< 4,  2>\n" +
                "position=<-20980,  42497> velocity=< 2, -4>\n" +
                "position=<-20985, -52704> velocity=< 2,  5>\n" +
                "position=<-42170, -10397> velocity=< 4,  1>\n" +
                "position=< 53038, -42123> velocity=<-5,  4>\n" +
                "position=< 53038,  42491> velocity=<-5, -4>\n" +
                "position=<-42176,  21334> velocity=< 4, -2>\n" +
                "position=< 31868, -42127> velocity=<-3,  4>\n" +
                "position=< 42447, -52705> velocity=<-4,  5>\n" +
                "position=<-52747, -20974> velocity=< 5,  2>\n" +
                "position=<-31561, -10397> velocity=< 3,  1>\n" +
                "position=< 10719, -42128> velocity=<-1,  4>\n" +
                "position=< 53009, -31547> velocity=<-5,  3>\n" +
                "position=< 21296,  10766> velocity=<-2, -1>\n" +
                "position=<-21020,  42494> velocity=< 2, -4>\n" +
                "position=<-42131, -42126> velocity=< 4,  4>\n" +
                "position=< 21292,  31915> velocity=<-2, -3>\n" +
                "position=<-42163,  42493> velocity=< 4, -4>\n" +
                "position=<-42185, -42128> velocity=< 4,  4>\n" +
                "position=<-20977, -10389> velocity=< 2,  1>\n" +
                "position=< 10754,  53068> velocity=<-1, -5>\n" +
                "position=< 21325, -42128> velocity=<-2,  4>\n" +
                "position=< 31876, -20965> velocity=<-3,  2>\n" +
                "position=< 10700,  10757> velocity=<-1, -1>\n" +
                "position=< 53025, -52701> velocity=<-5,  5>\n" +
                "position=< 53056,  21334> velocity=<-5, -2>\n" +
                "position=< 53058,  31919> velocity=<-5, -3>\n" +
                "position=< 53039,  53070> velocity=<-5, -5>\n" +
                "position=< 21335, -10388> velocity=<-2,  1>\n" +
                "position=<-52722,  53074> velocity=< 5, -5>\n" +
                "position=<-10412, -10393> velocity=< 1,  1>\n" +
                "position=<-52744,  31920> velocity=< 5, -3>\n" +
                "position=<-21017, -20970> velocity=< 2,  2>\n" +
                "position=<-31566, -52697> velocity=< 3,  5>\n" +
                "position=< 21332,  53065> velocity=<-2, -5>\n" +
                "position=<-31602, -42124> velocity=< 3,  4>\n" +
                "position=<-42171, -42123> velocity=< 4,  4>\n" +
                "position=< 42430, -10391> velocity=<-4,  1>\n" +
                "position=<-10445, -10392> velocity=< 1,  1>\n" +
                "position=< 31913,  21342> velocity=<-3, -2>\n" +
                "position=<-52748, -20966> velocity=< 5,  2>\n" +
                "position=< 53050, -42128> velocity=<-5,  4>\n" +
                "position=<-10424,  53068> velocity=< 1, -5>\n" +
                "position=<-31597, -42126> velocity=< 3,  4>\n" +
                "position=<-42162,  10762> velocity=< 4, -1>\n" +
                "position=<-21031, -20969> velocity=< 2,  2>\n" +
                "position=< 53064,  10766> velocity=<-5, -1>\n" +
                "position=<-52727,  21336> velocity=< 5, -2>\n" +
                "position=<-20977, -52699> velocity=< 2,  5>\n" +
                "position=<-31574,  53066> velocity=< 3, -5>\n" +
                "position=< 31873, -31551> velocity=<-3,  3>\n" +
                "position=< 31856,  42491> velocity=<-3, -4>\n" +
                "position=<-42139,  10765> velocity=< 4, -1>\n" +
                "position=< 42453,  10760> velocity=<-4, -1>\n" +
                "position=<-31566,  10757> velocity=< 3, -1>\n" +
                "position=< 10742, -31548> velocity=<-1,  3>\n" +
                "position=< 42453, -20967> velocity=<-4,  2>\n" +
                "position=<-10421, -52700> velocity=< 1,  5>\n" +
                "position=< 42485,  31914> velocity=<-4, -3>\n" +
                "position=< 31889, -20969> velocity=<-3,  2>\n" +
                "position=<-42126,  21335> velocity=< 4, -2>\n" +
                "position=<-31602, -42121> velocity=< 3,  4>\n" +
                "position=< 42453,  42493> velocity=<-4, -4>\n" +
                "position=< 53009, -10397> velocity=<-5,  1>\n" +
                "position=<-52748,  42497> velocity=< 5, -4>\n" +
                "position=< 21287,  42489> velocity=<-2, -4>\n" +
                "position=<-52756,  53067> velocity=< 5, -5>\n" +
                "position=<-21029,  10757> velocity=< 2, -1>\n" +
                "position=<-31565, -42128> velocity=< 3,  4>\n" +
                "position=< 21331, -42126> velocity=<-2,  4>\n" +
                "position=<-10443,  21341> velocity=< 1, -2>\n" +
                "position=<-20989, -52699> velocity=< 2,  5>\n" +
                "position=<-31591,  10757> velocity=< 3, -1>\n" +
                "position=<-52724,  21342> velocity=< 5, -2>\n" +
                "position=<-42160, -31549> velocity=< 4,  3>\n" +
                "position=< 42481, -10392> velocity=<-4,  1>\n" +
                "position=<-10443, -20971> velocity=< 1,  2>\n" +
                "position=<-20997,  10762> velocity=< 2, -1>\n" +
                "position=< 10750, -10397> velocity=<-1,  1>\n" +
                "position=<-31597, -20968> velocity=< 3,  2>\n" +
                "position=< 53017, -52700> velocity=<-5,  5>\n" +
                "position=< 21276, -31542> velocity=<-2,  3>\n" +
                "position=<-52720, -31545> velocity=< 5,  3>\n" +
                "position=< 21326,  31916> velocity=<-2, -3>\n" +
                "position=<-42163,  10764> velocity=< 4, -1>\n" +
                "position=<-31589,  10766> velocity=< 3, -1>\n" +
                "position=< 31884,  21336> velocity=<-3, -2>\n" +
                "position=<-31578,  10763> velocity=< 3, -1>\n" +
                "position=< 10706, -31546> velocity=<-1,  3>\n" +
                "position=<-42171,  31919> velocity=< 4, -3>\n" +
                "position=<-52721,  53065> velocity=< 5, -5>\n" +
                "position=< 53054,  31914> velocity=<-5, -3>\n" +
                "position=< 21319, -20972> velocity=<-2,  2>\n" +
                "position=< 21335,  10757> velocity=<-2, -1>\n" +
                "position=< 10716, -42119> velocity=<-1,  4>\n" +
                "position=< 42463, -20969> velocity=<-4,  2>\n" +
                "position=< 31889,  21341> velocity=<-3, -2>\n" +
                "position=<-52740, -10393> velocity=< 5,  1>\n" +
                "position=< 42490, -31543> velocity=<-4,  3>\n" +
                "position=< 10706, -52701> velocity=<-1,  5>\n" +
                "position=< 21315,  31919> velocity=<-2, -3>\n" +
                "position=< 31862, -52700> velocity=<-3,  5>\n" +
                "position=< 31884, -52701> velocity=<-3,  5>\n" +
                "position=< 31870, -10397> velocity=<-3,  1>\n" +
                "position=< 42441, -42123> velocity=<-4,  4>\n" +
                "position=<-42171,  10763> velocity=< 4, -1>\n" +
                "position=<-42171,  21341> velocity=< 4, -2>\n" +
                "position=<-31591,  53069> velocity=< 3, -5>\n" +
                "position=< 10726,  53066> velocity=<-1, -5>\n" +
                "position=<-42134, -20965> velocity=< 4,  2>\n" +
                "position=<-42183,  31920> velocity=< 4, -3>\n" +
                "position=< 31857,  21334> velocity=<-3, -2>\n" +
                "position=<-31597, -42123> velocity=< 3,  4>\n" +
                "position=< 21312, -42120> velocity=<-2,  4>\n" +
                "position=<-20996,  42497> velocity=< 2, -4>\n" +
                "position=<-10399,  53065> velocity=< 1, -5>\n" +
                "position=< 31860, -20969> velocity=<-3,  2>\n" +
                "position=<-31550, -20965> velocity=< 3,  2>\n" +
                "position=<-42162, -42124> velocity=< 4,  4>\n" +
                "position=<-20977, -10393> velocity=< 2,  1>\n" +
                "position=< 10735, -52697> velocity=<-1,  5>\n" +
                "position=< 53049, -42119> velocity=<-5,  4>\n" +
                "position=< 42453, -42122> velocity=<-4,  4>\n" +
                "position=< 10746, -42126> velocity=<-1,  4>\n" +
                "position=<-31569, -20965> velocity=< 3,  2>\n" +
                "position=< 42461,  31918> velocity=<-4, -3>\n" +
                "position=< 31896,  53072> velocity=<-3, -5>\n" +
                "position=< 53009, -52701> velocity=<-5,  5>\n" +
                "position=<-20977,  31919> velocity=< 2, -3>\n" +
                "position=<-31594, -20969> velocity=< 3,  2>";

    }
}
