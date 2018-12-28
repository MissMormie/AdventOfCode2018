package days;

import helpers.OpcodeSample;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {

    public static void main(String[] args) {
        runA();
        runB();
    }

    public static void runA() {
        String input = getInput();
        String[] inputArray = input.split("\n");


        int threeOrMoreMatches = 0;
        for(int i = 0; i < inputArray.length; i = i + 4) {
            int[] before = getNumbersInInput(inputArray[i]);
            int[] instruction = getNumbersInInput(inputArray[i+1]);
            int[] after = getNumbersInInput(inputArray[i+2]);

            OpcodeSample sample = new OpcodeSample(before);

            if(sample.getNumberOfMatches(after, instruction) >= 3) {
                threeOrMoreMatches++;
            }
        }
        System.out.println("answer A: " + threeOrMoreMatches);
    }

    public static int[] getNumbersInInput(String input) {
        String[] numInput;
        if(input.contains("[")) {
            input = input.substring(input.indexOf("[")+1, input.length()-1);
            numInput = input.split(", ");
        } else {
            numInput = input.split(" ");
        }

        int[] result = new int[4];
        for(int i = 0; i < numInput.length; i++) {
            result[i] = Integer.valueOf(numInput[i]);
        }
        return  result;
    }


    public static void runB() {
        String input = getInput();
        String[] inputArray = input.split("\n");


        int threeOrMoreMatches = 0;
        Map<OpcodeSample.Opcodes, Set<Integer>> opcodePossibilities = new HashMap<>();
        for(OpcodeSample.Opcodes opcode : OpcodeSample.Opcodes.values()) {
            opcodePossibilities.put(opcode, Stream.of(0, 1, 2, 3 ,4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15).collect(Collectors.toSet()));
        }

        for(int i = 0; i < inputArray.length; i = i + 4) {
            int[] before = getNumbersInInput(inputArray[i]);
            int[] instruction = getNumbersInInput(inputArray[i+1]);
            int[] after = getNumbersInInput(inputArray[i+2]);

            OpcodeSample sample = new OpcodeSample(before);
            sample.checkImpossibles(opcodePossibilities, instruction, after);

        }
        Map<Integer, OpcodeSample.Opcodes> opcodesMap =  cleanImpossibles(opcodePossibilities);

        String[] lines = getInputB().split("\n");
        OpcodeSample opcodeProcess = null;
        for(String line : lines) {
            if(opcodeProcess == null) {
                opcodeProcess = new OpcodeSample(getNumbersInInput(line));
            } else {
                opcodeProcess.runOpcode(getNumbersInInput(line) , opcodesMap);
            }
        }

        System.out.println("answer B: " + opcodeProcess.register);
    }

// 480 - 526
//    not 415, 418
    public static  Map<Integer, OpcodeSample.Opcodes> cleanImpossibles(Map<OpcodeSample.Opcodes, Set<Integer>> possibilities) {
        Map<Integer, OpcodeSample.Opcodes> opcodesMap = new HashMap();

        while (!possibilities.isEmpty()){
            for(OpcodeSample.Opcodes opcode : OpcodeSample.Opcodes.values()) {
                // Check if there are numbers that can be removed.
                if(possibilities.containsKey(opcode)) {
                    for (Map.Entry<Integer, OpcodeSample.Opcodes> entry : opcodesMap.entrySet()) {
                        possibilities.get(opcode).remove(entry.getKey());
                    }
                }
                if(possibilities.containsKey(opcode) && possibilities.get(opcode).size() == 1) { // found number for opcode.
                    opcodesMap.put(possibilities.get(opcode).iterator().next(),opcode);
                    possibilities.remove(opcode);
                }
            }
        }

        return opcodesMap;
    }
    public static String getTestInput() {
        return "Before: [3, 2, 1, 1]\n" +
                "9 2 1 2\n" +
                "After:  [3, 2, 2, 1]";
    }

    public static String getTestAnswer() {
        return "";
    }

    public static String getInput() {
        return "Before: [0, 2, 2, 2]\n" +
                "11 3 3 3\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [3, 2, 1, 1]\n" +
                "11 2 3 3\n" +
                "After:  [3, 2, 1, 0]\n" +
                "\n" +
                "Before: [1, 2, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [1, 2, 1, 1]\n" +
                "\n" +
                "Before: [1, 0, 2, 2]\n" +
                "14 0 2 2\n" +
                "After:  [1, 0, 0, 2]\n" +
                "\n" +
                "Before: [3, 2, 3, 3]\n" +
                "10 1 3 3\n" +
                "After:  [3, 2, 3, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "14 0 2 3\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "6 2 2 0\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 1, 3, 1]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 1]\n" +
                "\n" +
                "Before: [1, 1, 1, 3]\n" +
                "10 2 3 1\n" +
                "After:  [1, 0, 1, 3]\n" +
                "\n" +
                "Before: [3, 3, 0, 2]\n" +
                "3 2 3 0\n" +
                "After:  [1, 3, 0, 2]\n" +
                "\n" +
                "Before: [2, 1, 1, 0]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [2, 3, 3, 2]\n" +
                "7 3 1 0\n" +
                "After:  [1, 3, 3, 2]\n" +
                "\n" +
                "Before: [1, 0, 2, 1]\n" +
                "12 0 1 0\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 0, 0]\n" +
                "0 0 3 3\n" +
                "After:  [0, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "0 0 1 0\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 1, 0]\n" +
                "0 0 1 0\n" +
                "After:  [0, 2, 1, 0]\n" +
                "\n" +
                "Before: [3, 0, 3, 2]\n" +
                "7 3 0 1\n" +
                "After:  [3, 1, 3, 2]\n" +
                "\n" +
                "Before: [1, 3, 2, 2]\n" +
                "7 3 1 2\n" +
                "After:  [1, 3, 1, 2]\n" +
                "\n" +
                "Before: [0, 3, 1, 3]\n" +
                "9 0 0 2\n" +
                "After:  [0, 3, 0, 3]\n" +
                "\n" +
                "Before: [0, 1, 1, 3]\n" +
                "0 0 3 0\n" +
                "After:  [0, 1, 1, 3]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "2 1 2 1\n" +
                "After:  [1, 0, 2, 3]\n" +
                "\n" +
                "Before: [3, 3, 3, 2]\n" +
                "7 3 0 1\n" +
                "After:  [3, 1, 3, 2]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "2 1 2 3\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 0, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [1, 1, 2, 1]\n" +
                "\n" +
                "Before: [3, 1, 1, 1]\n" +
                "1 2 2 0\n" +
                "After:  [2, 1, 1, 1]\n" +
                "\n" +
                "Before: [1, 2, 2, 0]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 0]\n" +
                "\n" +
                "Before: [0, 3, 1, 1]\n" +
                "1 2 2 0\n" +
                "After:  [2, 3, 1, 1]\n" +
                "\n" +
                "Before: [3, 3, 2, 2]\n" +
                "11 3 3 3\n" +
                "After:  [3, 3, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 3, 3]\n" +
                "8 3 2 3\n" +
                "After:  [0, 1, 3, 1]\n" +
                "\n" +
                "Before: [0, 3, 2, 0]\n" +
                "0 0 3 3\n" +
                "After:  [0, 3, 2, 0]\n" +
                "\n" +
                "Before: [0, 3, 3, 2]\n" +
                "0 0 2 2\n" +
                "After:  [0, 3, 0, 2]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "10 1 3 1\n" +
                "After:  [3, 0, 3, 3]\n" +
                "\n" +
                "Before: [1, 1, 1, 1]\n" +
                "11 3 3 2\n" +
                "After:  [1, 1, 0, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 1]\n" +
                "0 0 2 1\n" +
                "After:  [0, 0, 1, 1]\n" +
                "\n" +
                "Before: [1, 0, 0, 3]\n" +
                "12 0 1 2\n" +
                "After:  [1, 0, 1, 3]\n" +
                "\n" +
                "Before: [0, 0, 3, 2]\n" +
                "15 3 2 0\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [3, 0, 1, 1]\n" +
                "12 3 1 0\n" +
                "After:  [1, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 1, 0, 2]\n" +
                "3 2 3 3\n" +
                "After:  [3, 1, 0, 1]\n" +
                "\n" +
                "Before: [0, 2, 0, 3]\n" +
                "0 0 1 2\n" +
                "After:  [0, 2, 0, 3]\n" +
                "\n" +
                "Before: [3, 2, 0, 2]\n" +
                "3 2 3 3\n" +
                "After:  [3, 2, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 0]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 0, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 3]\n" +
                "14 0 2 3\n" +
                "After:  [1, 3, 2, 0]\n" +
                "\n" +
                "Before: [0, 2, 2, 3]\n" +
                "9 0 0 3\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 0]\n" +
                "2 1 2 1\n" +
                "After:  [1, 0, 2, 0]\n" +
                "\n" +
                "Before: [3, 0, 1, 2]\n" +
                "1 2 2 0\n" +
                "After:  [2, 0, 1, 2]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "2 1 2 3\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 3, 3, 2]\n" +
                "7 3 1 0\n" +
                "After:  [1, 3, 3, 2]\n" +
                "\n" +
                "Before: [3, 3, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 3, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 2]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 2]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "2 1 2 3\n" +
                "After:  [3, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 2, 2, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 2, 2, 3]\n" +
                "\n" +
                "Before: [3, 3, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [3, 3, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 3]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [3, 3, 0, 3]\n" +
                "8 3 0 3\n" +
                "After:  [3, 3, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 3]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [3, 1, 2, 1]\n" +
                "2 1 2 2\n" +
                "After:  [3, 1, 0, 1]\n" +
                "\n" +
                "Before: [3, 3, 0, 2]\n" +
                "3 2 3 3\n" +
                "After:  [3, 3, 0, 1]\n" +
                "\n" +
                "Before: [2, 2, 0, 1]\n" +
                "11 3 3 2\n" +
                "After:  [2, 2, 0, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 0]\n" +
                "14 0 2 0\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [3, 2, 1, 3]\n" +
                "10 1 3 1\n" +
                "After:  [3, 0, 1, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "2 1 2 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [3, 2, 3, 3]\n" +
                "15 1 2 2\n" +
                "After:  [3, 2, 2, 3]\n" +
                "\n" +
                "Before: [1, 1, 3, 0]\n" +
                "3 3 2 0\n" +
                "After:  [1, 1, 3, 0]\n" +
                "\n" +
                "Before: [3, 3, 3, 3]\n" +
                "8 3 2 1\n" +
                "After:  [3, 1, 3, 3]\n" +
                "\n" +
                "Before: [0, 1, 1, 1]\n" +
                "9 0 0 3\n" +
                "After:  [0, 1, 1, 0]\n" +
                "\n" +
                "Before: [3, 0, 0, 2]\n" +
                "3 2 3 2\n" +
                "After:  [3, 0, 1, 2]\n" +
                "\n" +
                "Before: [0, 2, 2, 2]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 2, 2]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "2 1 2 2\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 3]\n" +
                "0 0 3 2\n" +
                "After:  [0, 1, 0, 3]\n" +
                "\n" +
                "Before: [3, 2, 1, 3]\n" +
                "8 3 0 1\n" +
                "After:  [3, 1, 1, 3]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "13 1 2 3\n" +
                "After:  [3, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "6 2 1 1\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [2, 3, 2, 3]\n" +
                "8 2 0 3\n" +
                "After:  [2, 3, 2, 1]\n" +
                "\n" +
                "Before: [0, 0, 1, 3]\n" +
                "0 0 1 3\n" +
                "After:  [0, 0, 1, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "8 2 0 0\n" +
                "After:  [1, 1, 2, 1]\n" +
                "\n" +
                "Before: [1, 0, 3, 3]\n" +
                "12 0 1 3\n" +
                "After:  [1, 0, 3, 1]\n" +
                "\n" +
                "Before: [3, 2, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [3, 2, 1, 1]\n" +
                "\n" +
                "Before: [3, 3, 2, 2]\n" +
                "7 3 1 1\n" +
                "After:  [3, 1, 2, 2]\n" +
                "\n" +
                "Before: [2, 2, 2, 1]\n" +
                "8 2 0 1\n" +
                "After:  [2, 1, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 0]\n" +
                "14 0 2 0\n" +
                "After:  [0, 3, 2, 0]\n" +
                "\n" +
                "Before: [2, 3, 3, 2]\n" +
                "15 3 2 3\n" +
                "After:  [2, 3, 3, 2]\n" +
                "\n" +
                "Before: [2, 0, 2, 3]\n" +
                "10 2 3 1\n" +
                "After:  [2, 0, 2, 3]\n" +
                "\n" +
                "Before: [0, 3, 2, 2]\n" +
                "7 3 1 2\n" +
                "After:  [0, 3, 1, 2]\n" +
                "\n" +
                "Before: [3, 0, 2, 2]\n" +
                "7 3 0 2\n" +
                "After:  [3, 0, 1, 2]\n" +
                "\n" +
                "Before: [1, 0, 2, 3]\n" +
                "10 2 3 1\n" +
                "After:  [1, 0, 2, 3]\n" +
                "\n" +
                "Before: [0, 0, 3, 2]\n" +
                "11 3 3 3\n" +
                "After:  [0, 0, 3, 0]\n" +
                "\n" +
                "Before: [1, 0, 2, 1]\n" +
                "12 0 1 2\n" +
                "After:  [1, 0, 1, 1]\n" +
                "\n" +
                "Before: [1, 0, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 2]\n" +
                "11 3 3 3\n" +
                "After:  [1, 3, 2, 0]\n" +
                "\n" +
                "Before: [2, 2, 3, 0]\n" +
                "15 1 2 0\n" +
                "After:  [2, 2, 3, 0]\n" +
                "\n" +
                "Before: [2, 3, 3, 2]\n" +
                "15 3 2 2\n" +
                "After:  [2, 3, 2, 2]\n" +
                "\n" +
                "Before: [3, 3, 0, 3]\n" +
                "8 3 0 1\n" +
                "After:  [3, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 2, 2, 3]\n" +
                "6 3 1 0\n" +
                "After:  [0, 2, 2, 3]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "2 1 2 1\n" +
                "After:  [3, 0, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "8 2 0 1\n" +
                "After:  [2, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "14 0 2 2\n" +
                "After:  [1, 1, 0, 3]\n" +
                "\n" +
                "Before: [0, 2, 1, 2]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 1, 2]\n" +
                "\n" +
                "Before: [0, 1, 0, 3]\n" +
                "10 1 3 1\n" +
                "After:  [0, 0, 0, 3]\n" +
                "\n" +
                "Before: [1, 3, 2, 0]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 0]\n" +
                "\n" +
                "Before: [2, 0, 2, 3]\n" +
                "10 2 3 3\n" +
                "After:  [2, 0, 2, 0]\n" +
                "\n" +
                "Before: [0, 3, 1, 1]\n" +
                "0 0 1 2\n" +
                "After:  [0, 3, 0, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 3]\n" +
                "14 0 2 2\n" +
                "After:  [1, 3, 0, 3]\n" +
                "\n" +
                "Before: [0, 2, 1, 1]\n" +
                "6 0 0 0\n" +
                "After:  [1, 2, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 2, 0]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 0, 3, 1]\n" +
                "11 3 3 0\n" +
                "After:  [0, 0, 3, 1]\n" +
                "\n" +
                "Before: [3, 0, 2, 1]\n" +
                "12 3 1 1\n" +
                "After:  [3, 1, 2, 1]\n" +
                "\n" +
                "Before: [3, 0, 0, 1]\n" +
                "12 3 1 1\n" +
                "After:  [3, 1, 0, 1]\n" +
                "\n" +
                "Before: [3, 0, 2, 1]\n" +
                "12 3 1 2\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [0, 0, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 1, 0]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 1, 0]\n" +
                "\n" +
                "Before: [0, 2, 1, 0]\n" +
                "6 0 0 0\n" +
                "After:  [1, 2, 1, 0]\n" +
                "\n" +
                "Before: [2, 0, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [2, 1, 2, 1]\n" +
                "\n" +
                "Before: [3, 3, 1, 3]\n" +
                "1 2 2 2\n" +
                "After:  [3, 3, 2, 3]\n" +
                "\n" +
                "Before: [0, 0, 1, 1]\n" +
                "12 3 1 2\n" +
                "After:  [0, 0, 1, 1]\n" +
                "\n" +
                "Before: [1, 0, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [1, 0, 3, 1]\n" +
                "\n" +
                "Before: [1, 0, 1, 2]\n" +
                "12 2 1 3\n" +
                "After:  [1, 0, 1, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 3]\n" +
                "10 2 3 3\n" +
                "After:  [0, 1, 1, 0]\n" +
                "\n" +
                "Before: [3, 3, 0, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 3, 0, 2]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "2 1 2 2\n" +
                "After:  [0, 1, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 3, 3]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 3, 3]\n" +
                "\n" +
                "Before: [3, 2, 1, 1]\n" +
                "11 2 3 1\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [0, 1, 2, 0]\n" +
                "2 1 2 1\n" +
                "After:  [0, 0, 2, 0]\n" +
                "\n" +
                "Before: [2, 2, 0, 3]\n" +
                "6 3 3 3\n" +
                "After:  [2, 2, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 2]\n" +
                "2 1 2 1\n" +
                "After:  [2, 0, 2, 2]\n" +
                "\n" +
                "Before: [0, 1, 3, 2]\n" +
                "15 3 2 0\n" +
                "After:  [2, 1, 3, 2]\n" +
                "\n" +
                "Before: [0, 1, 2, 2]\n" +
                "2 1 2 3\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 3, 2, 3]\n" +
                "10 2 3 2\n" +
                "After:  [1, 3, 0, 3]\n" +
                "\n" +
                "Before: [1, 2, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [1, 2, 3, 1]\n" +
                "\n" +
                "Before: [0, 2, 1, 1]\n" +
                "1 2 2 2\n" +
                "After:  [0, 2, 2, 1]\n" +
                "\n" +
                "Before: [2, 3, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [2, 3, 1, 1]\n" +
                "\n" +
                "Before: [1, 0, 0, 2]\n" +
                "3 2 3 2\n" +
                "After:  [1, 0, 1, 2]\n" +
                "\n" +
                "Before: [2, 1, 0, 2]\n" +
                "3 2 3 3\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [0, 3, 3, 2]\n" +
                "15 3 2 1\n" +
                "After:  [0, 2, 3, 2]\n" +
                "\n" +
                "Before: [3, 0, 3, 0]\n" +
                "3 3 2 2\n" +
                "After:  [3, 0, 1, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 1]\n" +
                "11 3 3 1\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 0, 1]\n" +
                "0 0 3 1\n" +
                "After:  [0, 0, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 3]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 0, 3]\n" +
                "\n" +
                "Before: [2, 2, 1, 3]\n" +
                "1 2 2 0\n" +
                "After:  [2, 2, 1, 3]\n" +
                "\n" +
                "Before: [0, 2, 2, 3]\n" +
                "10 1 3 1\n" +
                "After:  [0, 0, 2, 3]\n" +
                "\n" +
                "Before: [1, 1, 1, 0]\n" +
                "1 2 2 2\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [2, 3, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [2, 1, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 3, 2]\n" +
                "15 3 2 1\n" +
                "After:  [1, 2, 3, 2]\n" +
                "\n" +
                "Before: [3, 2, 2, 3]\n" +
                "8 3 0 3\n" +
                "After:  [3, 2, 2, 1]\n" +
                "\n" +
                "Before: [2, 2, 2, 3]\n" +
                "10 2 3 3\n" +
                "After:  [2, 2, 2, 0]\n" +
                "\n" +
                "Before: [0, 0, 1, 3]\n" +
                "0 0 3 3\n" +
                "After:  [0, 0, 1, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 1]\n" +
                "0 0 3 0\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 2, 2, 3]\n" +
                "10 1 3 0\n" +
                "After:  [0, 2, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 2, 3]\n" +
                "9 0 0 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [0, 0, 2, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 0, 2, 3]\n" +
                "\n" +
                "Before: [3, 2, 3, 1]\n" +
                "8 2 3 2\n" +
                "After:  [3, 2, 0, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 1]\n" +
                "13 1 2 3\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 2, 3, 1]\n" +
                "15 1 2 0\n" +
                "After:  [2, 2, 3, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "6 3 1 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [0, 2, 3, 3]\n" +
                "0 0 1 1\n" +
                "After:  [0, 0, 3, 3]\n" +
                "\n" +
                "Before: [0, 1, 0, 2]\n" +
                "0 0 3 1\n" +
                "After:  [0, 0, 0, 2]\n" +
                "\n" +
                "Before: [2, 0, 1, 3]\n" +
                "10 2 3 1\n" +
                "After:  [2, 0, 1, 3]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "2 1 2 3\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 1]\n" +
                "13 1 2 1\n" +
                "After:  [2, 0, 3, 1]\n" +
                "\n" +
                "Before: [1, 0, 2, 0]\n" +
                "14 0 2 3\n" +
                "After:  [1, 0, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 2]\n" +
                "2 1 2 2\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [1, 2, 0, 2]\n" +
                "3 2 3 0\n" +
                "After:  [1, 2, 0, 2]\n" +
                "\n" +
                "Before: [2, 1, 1, 1]\n" +
                "11 2 3 2\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [1, 3, 3, 2]\n" +
                "15 3 2 2\n" +
                "After:  [1, 3, 2, 2]\n" +
                "\n" +
                "Before: [1, 2, 3, 3]\n" +
                "10 1 3 0\n" +
                "After:  [0, 2, 3, 3]\n" +
                "\n" +
                "Before: [0, 1, 3, 2]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "6 3 2 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [0, 2, 1, 3]\n" +
                "6 3 3 3\n" +
                "After:  [0, 2, 1, 1]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "8 2 1 0\n" +
                "After:  [1, 2, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 3, 3]\n" +
                "10 1 3 2\n" +
                "After:  [0, 2, 0, 3]\n" +
                "\n" +
                "Before: [0, 3, 2, 0]\n" +
                "0 0 1 2\n" +
                "After:  [0, 3, 0, 0]\n" +
                "\n" +
                "Before: [1, 0, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [1, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 0, 1, 2]\n" +
                "12 2 1 0\n" +
                "After:  [1, 0, 1, 2]\n" +
                "\n" +
                "Before: [0, 3, 3, 3]\n" +
                "9 0 0 0\n" +
                "After:  [0, 3, 3, 3]\n" +
                "\n" +
                "Before: [1, 0, 2, 1]\n" +
                "14 0 2 0\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [2, 0, 3, 2]\n" +
                "11 3 3 1\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "8 2 1 3\n" +
                "After:  [0, 2, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 0]\n" +
                "9 0 0 0\n" +
                "After:  [0, 1, 1, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 0]\n" +
                "14 0 2 3\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 3, 0, 1]\n" +
                "9 0 0 0\n" +
                "After:  [0, 3, 0, 1]\n" +
                "\n" +
                "Before: [1, 0, 3, 1]\n" +
                "8 2 3 0\n" +
                "After:  [0, 0, 3, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "8 3 0 1\n" +
                "After:  [3, 1, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "0 0 1 2\n" +
                "After:  [0, 1, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [2, 3, 3, 0]\n" +
                "3 3 2 2\n" +
                "After:  [2, 3, 1, 0]\n" +
                "\n" +
                "Before: [0, 3, 2, 3]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 2, 3]\n" +
                "\n" +
                "Before: [3, 3, 1, 0]\n" +
                "1 2 2 3\n" +
                "After:  [3, 3, 1, 2]\n" +
                "\n" +
                "Before: [3, 0, 1, 0]\n" +
                "1 2 2 2\n" +
                "After:  [3, 0, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 0]\n" +
                "2 1 2 2\n" +
                "After:  [0, 1, 0, 0]\n" +
                "\n" +
                "Before: [2, 1, 0, 1]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 0, 1]\n" +
                "\n" +
                "Before: [0, 0, 1, 0]\n" +
                "12 2 1 0\n" +
                "After:  [1, 0, 1, 0]\n" +
                "\n" +
                "Before: [2, 0, 0, 2]\n" +
                "3 2 3 2\n" +
                "After:  [2, 0, 1, 2]\n" +
                "\n" +
                "Before: [2, 2, 3, 1]\n" +
                "15 0 2 1\n" +
                "After:  [2, 2, 3, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 0]\n" +
                "0 0 1 1\n" +
                "After:  [0, 0, 1, 0]\n" +
                "\n" +
                "Before: [1, 1, 1, 3]\n" +
                "10 2 3 2\n" +
                "After:  [1, 1, 0, 3]\n" +
                "\n" +
                "Before: [0, 1, 2, 0]\n" +
                "2 1 2 3\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 3, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [2, 3, 3, 1]\n" +
                "\n" +
                "Before: [3, 1, 3, 2]\n" +
                "13 1 2 1\n" +
                "After:  [3, 0, 3, 2]\n" +
                "\n" +
                "Before: [0, 1, 1, 3]\n" +
                "1 2 2 3\n" +
                "After:  [0, 1, 1, 2]\n" +
                "\n" +
                "Before: [2, 0, 3, 1]\n" +
                "8 2 3 1\n" +
                "After:  [2, 0, 3, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 0]\n" +
                "0 0 3 3\n" +
                "After:  [0, 1, 1, 0]\n" +
                "\n" +
                "Before: [1, 2, 3, 1]\n" +
                "15 1 2 2\n" +
                "After:  [1, 2, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 2]\n" +
                "2 1 2 3\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 0, 2, 1]\n" +
                "11 3 3 3\n" +
                "After:  [1, 0, 2, 0]\n" +
                "\n" +
                "Before: [2, 0, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 2]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 2]\n" +
                "\n" +
                "Before: [1, 3, 1, 2]\n" +
                "11 3 3 2\n" +
                "After:  [1, 3, 0, 2]\n" +
                "\n" +
                "Before: [1, 0, 2, 2]\n" +
                "12 0 1 1\n" +
                "After:  [1, 1, 2, 2]\n" +
                "\n" +
                "Before: [2, 1, 3, 0]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 2, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [2, 2, 1, 1]\n" +
                "\n" +
                "Before: [3, 2, 3, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 2, 3, 2]\n" +
                "\n" +
                "Before: [1, 2, 3, 1]\n" +
                "8 2 3 3\n" +
                "After:  [1, 2, 3, 0]\n" +
                "\n" +
                "Before: [3, 3, 0, 2]\n" +
                "7 3 0 3\n" +
                "After:  [3, 3, 0, 1]\n" +
                "\n" +
                "Before: [0, 0, 3, 0]\n" +
                "3 3 2 2\n" +
                "After:  [0, 0, 1, 0]\n" +
                "\n" +
                "Before: [1, 2, 2, 2]\n" +
                "14 0 2 3\n" +
                "After:  [1, 2, 2, 0]\n" +
                "\n" +
                "Before: [3, 1, 1, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 1, 1, 3]\n" +
                "\n" +
                "Before: [1, 1, 3, 0]\n" +
                "13 1 2 2\n" +
                "After:  [1, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 3]\n" +
                "9 0 0 2\n" +
                "After:  [0, 0, 0, 3]\n" +
                "\n" +
                "Before: [0, 3, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 3]\n" +
                "10 1 3 1\n" +
                "After:  [2, 0, 0, 3]\n" +
                "\n" +
                "Before: [0, 0, 3, 3]\n" +
                "8 3 2 2\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "11 3 3 1\n" +
                "After:  [2, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 1, 2, 2]\n" +
                "\n" +
                "Before: [0, 2, 1, 1]\n" +
                "11 3 3 2\n" +
                "After:  [0, 2, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 2, 0]\n" +
                "\n" +
                "Before: [3, 2, 3, 1]\n" +
                "15 1 2 0\n" +
                "After:  [2, 2, 3, 1]\n" +
                "\n" +
                "Before: [2, 0, 3, 2]\n" +
                "15 0 2 3\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [2, 3, 1, 2]\n" +
                "7 3 1 0\n" +
                "After:  [1, 3, 1, 2]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "11 3 3 0\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 3, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [0, 3, 1, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 1]\n" +
                "13 1 2 2\n" +
                "After:  [0, 1, 0, 1]\n" +
                "\n" +
                "Before: [3, 3, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [3, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 3]\n" +
                "2 1 2 2\n" +
                "After:  [0, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 1, 1, 1]\n" +
                "1 2 2 0\n" +
                "After:  [2, 1, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 1, 2]\n" +
                "1 2 2 1\n" +
                "After:  [2, 2, 1, 2]\n" +
                "\n" +
                "Before: [0, 2, 2, 0]\n" +
                "6 0 0 3\n" +
                "After:  [0, 2, 2, 1]\n" +
                "\n" +
                "Before: [0, 3, 3, 0]\n" +
                "9 0 0 0\n" +
                "After:  [0, 3, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "11 3 3 2\n" +
                "After:  [2, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 0, 0, 1]\n" +
                "12 3 1 2\n" +
                "After:  [0, 0, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "15 0 2 2\n" +
                "After:  [2, 1, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [0, 1, 3, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 2, 0]\n" +
                "\n" +
                "Before: [0, 2, 0, 1]\n" +
                "9 0 0 3\n" +
                "After:  [0, 2, 0, 0]\n" +
                "\n" +
                "Before: [1, 2, 2, 0]\n" +
                "14 0 2 0\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 1]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 2, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 2, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "14 0 2 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [0, 3, 2, 2]\n" +
                "7 3 1 0\n" +
                "After:  [1, 3, 2, 2]\n" +
                "\n" +
                "Before: [2, 1, 3, 0]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 0, 1]\n" +
                "11 3 3 3\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [2, 0, 3, 0]\n" +
                "15 0 2 1\n" +
                "After:  [2, 2, 3, 0]\n" +
                "\n" +
                "Before: [3, 0, 0, 2]\n" +
                "3 2 3 3\n" +
                "After:  [3, 0, 0, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 0]\n" +
                "2 1 2 3\n" +
                "After:  [3, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "2 1 2 1\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [1, 0, 2, 3]\n" +
                "6 2 2 1\n" +
                "After:  [1, 1, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "13 1 2 3\n" +
                "After:  [2, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 1, 1]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 1, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 2]\n" +
                "0 0 2 0\n" +
                "After:  [0, 1, 1, 2]\n" +
                "\n" +
                "Before: [3, 0, 0, 1]\n" +
                "12 3 1 2\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [0, 2, 1, 3]\n" +
                "0 0 2 1\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "14 0 2 3\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 3, 2, 2]\n" +
                "7 3 1 1\n" +
                "After:  [1, 1, 2, 2]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "3 3 2 2\n" +
                "After:  [0, 1, 1, 0]\n" +
                "\n" +
                "Before: [1, 2, 3, 0]\n" +
                "15 1 2 3\n" +
                "After:  [1, 2, 3, 2]\n" +
                "\n" +
                "Before: [1, 0, 1, 3]\n" +
                "12 2 1 0\n" +
                "After:  [1, 0, 1, 3]\n" +
                "\n" +
                "Before: [3, 3, 1, 3]\n" +
                "8 3 0 3\n" +
                "After:  [3, 3, 1, 1]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [0, 2, 1, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 1]\n" +
                "14 0 2 2\n" +
                "After:  [1, 3, 0, 1]\n" +
                "\n" +
                "Before: [3, 0, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [3, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [0, 0, 1, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 0, 1, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [2, 2, 1, 1]\n" +
                "1 2 2 1\n" +
                "After:  [2, 2, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "13 1 2 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [0, 2, 2, 3]\n" +
                "0 0 2 0\n" +
                "After:  [0, 2, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 3, 0, 3]\n" +
                "9 0 0 2\n" +
                "After:  [0, 3, 0, 3]\n" +
                "\n" +
                "Before: [2, 0, 1, 0]\n" +
                "1 2 2 2\n" +
                "After:  [2, 0, 2, 0]\n" +
                "\n" +
                "Before: [1, 0, 3, 2]\n" +
                "11 3 3 3\n" +
                "After:  [1, 0, 3, 0]\n" +
                "\n" +
                "Before: [1, 0, 2, 2]\n" +
                "6 2 2 1\n" +
                "After:  [1, 1, 2, 2]\n" +
                "\n" +
                "Before: [0, 2, 0, 3]\n" +
                "9 0 0 3\n" +
                "After:  [0, 2, 0, 0]\n" +
                "\n" +
                "Before: [2, 0, 1, 1]\n" +
                "12 2 1 3\n" +
                "After:  [2, 0, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 2]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 1, 2, 2]\n" +
                "2 1 2 1\n" +
                "After:  [0, 0, 2, 2]\n" +
                "\n" +
                "Before: [3, 0, 3, 2]\n" +
                "15 3 2 0\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 3]\n" +
                "\n" +
                "Before: [1, 1, 2, 2]\n" +
                "2 1 2 1\n" +
                "After:  [1, 0, 2, 2]\n" +
                "\n" +
                "Before: [1, 2, 1, 3]\n" +
                "10 2 3 3\n" +
                "After:  [1, 2, 1, 0]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "8 2 1 1\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 3, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [0, 3, 2, 1]\n" +
                "\n" +
                "Before: [3, 0, 2, 1]\n" +
                "12 3 1 3\n" +
                "After:  [3, 0, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 3, 2]\n" +
                "15 3 2 3\n" +
                "After:  [1, 3, 3, 2]\n" +
                "\n" +
                "Before: [2, 3, 0, 2]\n" +
                "3 2 3 2\n" +
                "After:  [2, 3, 1, 2]\n" +
                "\n" +
                "Before: [3, 3, 2, 2]\n" +
                "6 2 2 2\n" +
                "After:  [3, 3, 1, 2]\n" +
                "\n" +
                "Before: [3, 3, 2, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 3, 2, 2]\n" +
                "\n" +
                "Before: [2, 1, 0, 2]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [1, 2, 2, 2]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 2]\n" +
                "\n" +
                "Before: [0, 1, 0, 3]\n" +
                "0 0 1 1\n" +
                "After:  [0, 0, 0, 3]\n" +
                "\n" +
                "Before: [2, 1, 1, 3]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 1, 3]\n" +
                "\n" +
                "Before: [2, 0, 1, 1]\n" +
                "12 3 1 0\n" +
                "After:  [1, 0, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "2 1 2 1\n" +
                "After:  [2, 0, 2, 0]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "10 1 3 2\n" +
                "After:  [3, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 1, 1, 0]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 1, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "10 1 3 3\n" +
                "After:  [2, 1, 3, 0]\n" +
                "\n" +
                "Before: [1, 0, 0, 1]\n" +
                "12 0 1 3\n" +
                "After:  [1, 0, 0, 1]\n" +
                "\n" +
                "Before: [3, 0, 1, 3]\n" +
                "12 2 1 3\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [1, 1, 3, 1]\n" +
                "8 2 3 1\n" +
                "After:  [1, 0, 3, 1]\n" +
                "\n" +
                "Before: [1, 2, 2, 3]\n" +
                "14 0 2 0\n" +
                "After:  [0, 2, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 1, 3]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "11 3 3 3\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 2]\n" +
                "2 1 2 2\n" +
                "After:  [2, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 1, 3, 1]\n" +
                "13 1 2 1\n" +
                "After:  [0, 0, 3, 1]\n" +
                "\n" +
                "Before: [1, 1, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [1, 1, 3, 0]\n" +
                "\n" +
                "Before: [3, 2, 0, 1]\n" +
                "11 3 3 2\n" +
                "After:  [3, 2, 0, 1]\n" +
                "\n" +
                "Before: [2, 2, 3, 2]\n" +
                "15 0 2 0\n" +
                "After:  [2, 2, 3, 2]\n" +
                "\n" +
                "Before: [1, 3, 2, 3]\n" +
                "14 0 2 0\n" +
                "After:  [0, 3, 2, 3]\n" +
                "\n" +
                "Before: [1, 2, 3, 3]\n" +
                "10 1 3 2\n" +
                "After:  [1, 2, 0, 3]\n" +
                "\n" +
                "Before: [1, 0, 1, 2]\n" +
                "1 2 2 2\n" +
                "After:  [1, 0, 2, 2]\n" +
                "\n" +
                "Before: [3, 3, 1, 3]\n" +
                "8 3 0 0\n" +
                "After:  [1, 3, 1, 3]\n" +
                "\n" +
                "Before: [1, 3, 3, 3]\n" +
                "8 3 2 0\n" +
                "After:  [1, 3, 3, 3]\n" +
                "\n" +
                "Before: [3, 3, 2, 2]\n" +
                "7 3 0 1\n" +
                "After:  [3, 1, 2, 2]\n" +
                "\n" +
                "Before: [1, 2, 3, 3]\n" +
                "15 1 2 2\n" +
                "After:  [1, 2, 2, 3]\n" +
                "\n" +
                "Before: [3, 1, 2, 0]\n" +
                "2 1 2 2\n" +
                "After:  [3, 1, 0, 0]\n" +
                "\n" +
                "Before: [2, 2, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [2, 2, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 2]\n" +
                "9 0 0 2\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 2, 0, 3]\n" +
                "0 0 2 2\n" +
                "After:  [0, 2, 0, 3]\n" +
                "\n" +
                "Before: [0, 3, 1, 1]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [3, 0, 3, 1]\n" +
                "12 3 1 3\n" +
                "After:  [3, 0, 3, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 2]\n" +
                "7 3 1 3\n" +
                "After:  [1, 3, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [0, 2, 3, 1]\n" +
                "\n" +
                "Before: [3, 1, 1, 2]\n" +
                "11 3 3 3\n" +
                "After:  [3, 1, 1, 0]\n" +
                "\n" +
                "Before: [1, 0, 2, 3]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 3]\n" +
                "\n" +
                "Before: [1, 2, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [1, 1, 3, 0]\n" +
                "\n" +
                "Before: [3, 0, 1, 3]\n" +
                "10 2 3 3\n" +
                "After:  [3, 0, 1, 0]\n" +
                "\n" +
                "Before: [2, 0, 3, 1]\n" +
                "12 3 1 2\n" +
                "After:  [2, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 1, 3, 2]\n" +
                "11 3 3 1\n" +
                "After:  [3, 0, 3, 2]\n" +
                "\n" +
                "Before: [0, 3, 2, 0]\n" +
                "9 0 0 3\n" +
                "After:  [0, 3, 2, 0]\n" +
                "\n" +
                "Before: [1, 0, 2, 3]\n" +
                "12 0 1 2\n" +
                "After:  [1, 0, 1, 3]\n" +
                "\n" +
                "Before: [3, 3, 1, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 3, 1, 3]\n" +
                "\n" +
                "Before: [3, 3, 2, 0]\n" +
                "6 2 2 3\n" +
                "After:  [3, 3, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [1, 2, 2, 0]\n" +
                "8 2 1 1\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 1, 3, 2]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "10 1 3 1\n" +
                "After:  [2, 0, 2, 3]\n" +
                "\n" +
                "Before: [1, 3, 2, 0]\n" +
                "14 0 2 3\n" +
                "After:  [1, 3, 2, 0]\n" +
                "\n" +
                "Before: [0, 2, 3, 2]\n" +
                "15 1 2 1\n" +
                "After:  [0, 2, 3, 2]\n" +
                "\n" +
                "Before: [1, 3, 0, 2]\n" +
                "3 2 3 1\n" +
                "After:  [1, 1, 0, 2]\n" +
                "\n" +
                "Before: [3, 1, 2, 0]\n" +
                "2 1 2 1\n" +
                "After:  [3, 0, 2, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 2]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 2]\n" +
                "\n" +
                "Before: [1, 3, 3, 0]\n" +
                "3 3 2 0\n" +
                "After:  [1, 3, 3, 0]\n" +
                "\n" +
                "Before: [0, 3, 0, 2]\n" +
                "7 3 1 1\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "13 1 2 2\n" +
                "After:  [3, 1, 0, 3]\n" +
                "\n" +
                "Before: [3, 2, 1, 3]\n" +
                "10 2 3 1\n" +
                "After:  [3, 0, 1, 3]\n" +
                "\n" +
                "Before: [1, 1, 3, 2]\n" +
                "13 1 2 2\n" +
                "After:  [1, 1, 0, 2]\n" +
                "\n" +
                "Before: [1, 2, 2, 3]\n" +
                "14 0 2 3\n" +
                "After:  [1, 2, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 1, 2]\n" +
                "11 3 3 3\n" +
                "After:  [0, 1, 1, 0]\n" +
                "\n" +
                "Before: [3, 1, 3, 1]\n" +
                "13 1 2 1\n" +
                "After:  [3, 0, 3, 1]\n" +
                "\n" +
                "Before: [2, 1, 1, 3]\n" +
                "1 2 2 2\n" +
                "After:  [2, 1, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 1, 0]\n" +
                "1 2 2 3\n" +
                "After:  [0, 1, 1, 2]\n" +
                "\n" +
                "Before: [1, 1, 3, 3]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 3]\n" +
                "\n" +
                "Before: [1, 3, 1, 2]\n" +
                "7 3 1 3\n" +
                "After:  [1, 3, 1, 1]\n" +
                "\n" +
                "Before: [1, 0, 3, 2]\n" +
                "12 0 1 0\n" +
                "After:  [1, 0, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 3, 0]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 1, 3]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 2, 3, 3]\n" +
                "10 1 3 2\n" +
                "After:  [2, 2, 0, 3]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "15 0 2 0\n" +
                "After:  [2, 1, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 0, 1]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [3, 0, 0, 1]\n" +
                "11 3 3 1\n" +
                "After:  [3, 0, 0, 1]\n" +
                "\n" +
                "Before: [1, 0, 2, 3]\n" +
                "6 3 3 0\n" +
                "After:  [1, 0, 2, 3]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "2 1 2 1\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 0]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 0, 0, 3]\n" +
                "6 3 3 1\n" +
                "After:  [0, 1, 0, 3]\n" +
                "\n" +
                "Before: [1, 3, 0, 2]\n" +
                "7 3 1 1\n" +
                "After:  [1, 1, 0, 2]\n" +
                "\n" +
                "Before: [2, 2, 1, 2]\n" +
                "11 3 3 2\n" +
                "After:  [2, 2, 0, 2]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 0, 0, 2]\n" +
                "11 3 3 2\n" +
                "After:  [0, 0, 0, 2]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 2, 0, 3]\n" +
                "10 1 3 3\n" +
                "After:  [2, 2, 0, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "2 1 2 3\n" +
                "After:  [2, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 0, 2, 0]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "6 3 1 1\n" +
                "After:  [2, 0, 2, 3]\n" +
                "\n" +
                "Before: [0, 0, 3, 2]\n" +
                "0 0 1 1\n" +
                "After:  [0, 0, 3, 2]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "9 0 0 2\n" +
                "After:  [0, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 1, 3, 1]\n" +
                "0 0 3 2\n" +
                "After:  [0, 1, 0, 1]\n" +
                "\n" +
                "Before: [0, 2, 3, 2]\n" +
                "15 3 2 2\n" +
                "After:  [0, 2, 2, 2]\n" +
                "\n" +
                "Before: [3, 0, 1, 2]\n" +
                "7 3 0 3\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 1, 1, 2]\n" +
                "7 3 0 1\n" +
                "After:  [3, 1, 1, 2]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "8 2 0 0\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 0]\n" +
                "13 1 2 3\n" +
                "After:  [2, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 3, 0, 0]\n" +
                "9 0 0 3\n" +
                "After:  [0, 3, 0, 0]\n" +
                "\n" +
                "Before: [3, 1, 1, 2]\n" +
                "1 2 2 2\n" +
                "After:  [3, 1, 2, 2]\n" +
                "\n" +
                "Before: [0, 0, 1, 0]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 1, 0]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "11 3 3 0\n" +
                "After:  [0, 2, 2, 1]\n" +
                "\n" +
                "Before: [2, 3, 1, 2]\n" +
                "11 3 3 3\n" +
                "After:  [2, 3, 1, 0]\n" +
                "\n" +
                "Before: [3, 3, 3, 2]\n" +
                "7 3 0 2\n" +
                "After:  [3, 3, 1, 2]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "15 0 2 2\n" +
                "After:  [2, 1, 2, 2]\n" +
                "\n" +
                "Before: [3, 1, 1, 2]\n" +
                "7 3 0 3\n" +
                "After:  [3, 1, 1, 1]\n" +
                "\n" +
                "Before: [0, 0, 3, 1]\n" +
                "11 3 3 1\n" +
                "After:  [0, 0, 3, 1]\n" +
                "\n" +
                "Before: [0, 3, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 3, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [2, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 0, 0, 1]\n" +
                "12 3 1 0\n" +
                "After:  [1, 0, 0, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [1, 3, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 1]\n" +
                "14 0 2 0\n" +
                "After:  [0, 3, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 1, 2]\n" +
                "7 3 1 1\n" +
                "After:  [1, 1, 1, 2]\n" +
                "\n" +
                "Before: [2, 0, 2, 1]\n" +
                "12 3 1 3\n" +
                "After:  [2, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 0, 1, 0]\n" +
                "1 2 2 3\n" +
                "After:  [3, 0, 1, 2]\n" +
                "\n" +
                "Before: [1, 3, 0, 2]\n" +
                "7 3 1 0\n" +
                "After:  [1, 3, 0, 2]\n" +
                "\n" +
                "Before: [0, 0, 3, 1]\n" +
                "8 2 3 0\n" +
                "After:  [0, 0, 3, 1]\n" +
                "\n" +
                "Before: [2, 0, 3, 1]\n" +
                "15 0 2 3\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [0, 0, 0, 1]\n" +
                "0 0 3 1\n" +
                "After:  [0, 0, 0, 1]\n" +
                "\n" +
                "Before: [0, 3, 1, 3]\n" +
                "1 2 2 2\n" +
                "After:  [0, 3, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 1, 3]\n" +
                "10 1 3 0\n" +
                "After:  [0, 1, 1, 3]\n" +
                "\n" +
                "Before: [0, 2, 1, 2]\n" +
                "11 3 3 1\n" +
                "After:  [0, 0, 1, 2]\n" +
                "\n" +
                "Before: [3, 2, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [3, 2, 2, 1]\n" +
                "\n" +
                "Before: [3, 3, 2, 2]\n" +
                "7 3 1 2\n" +
                "After:  [3, 3, 1, 2]\n" +
                "\n" +
                "Before: [0, 2, 2, 2]\n" +
                "0 0 3 3\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 2]\n" +
                "2 1 2 3\n" +
                "After:  [3, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 0, 1, 2]\n" +
                "12 0 1 0\n" +
                "After:  [1, 0, 1, 2]\n" +
                "\n" +
                "Before: [0, 0, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [0, 0, 3, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [1, 1, 2, 1]\n" +
                "\n" +
                "Before: [3, 3, 1, 1]\n" +
                "11 3 3 1\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [1, 0, 0, 2]\n" +
                "12 0 1 3\n" +
                "After:  [1, 0, 0, 1]\n" +
                "\n" +
                "Before: [1, 2, 1, 3]\n" +
                "1 2 2 2\n" +
                "After:  [1, 2, 2, 3]\n" +
                "\n" +
                "Before: [3, 0, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [3, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 0, 0, 1]\n" +
                "0 0 2 3\n" +
                "After:  [0, 0, 0, 0]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "13 1 2 1\n" +
                "After:  [3, 0, 3, 3]\n" +
                "\n" +
                "Before: [1, 2, 1, 0]\n" +
                "1 2 2 2\n" +
                "After:  [1, 2, 2, 0]\n" +
                "\n" +
                "Before: [0, 3, 2, 1]\n" +
                "9 0 0 0\n" +
                "After:  [0, 3, 2, 1]\n" +
                "\n" +
                "Before: [0, 3, 3, 1]\n" +
                "8 2 3 0\n" +
                "After:  [0, 3, 3, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 1, 2]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 1, 2]\n" +
                "\n" +
                "Before: [1, 1, 1, 3]\n" +
                "10 2 3 3\n" +
                "After:  [1, 1, 1, 0]\n" +
                "\n" +
                "Before: [3, 0, 1, 0]\n" +
                "12 2 1 0\n" +
                "After:  [1, 0, 1, 0]\n" +
                "\n" +
                "Before: [3, 2, 1, 2]\n" +
                "7 3 0 3\n" +
                "After:  [3, 2, 1, 1]\n" +
                "\n" +
                "Before: [1, 1, 3, 2]\n" +
                "11 3 3 3\n" +
                "After:  [1, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 3, 3, 2]\n" +
                "15 3 2 2\n" +
                "After:  [0, 3, 2, 2]\n" +
                "\n" +
                "Before: [0, 0, 0, 0]\n" +
                "9 0 0 2\n" +
                "After:  [0, 0, 0, 0]\n" +
                "\n" +
                "Before: [1, 1, 3, 0]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 1, 3, 3]\n" +
                "9 0 0 3\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 0, 1, 2]\n" +
                "1 2 2 0\n" +
                "After:  [2, 0, 1, 2]\n" +
                "\n" +
                "Before: [1, 2, 3, 3]\n" +
                "8 3 2 3\n" +
                "After:  [1, 2, 3, 1]\n" +
                "\n" +
                "Before: [3, 3, 3, 2]\n" +
                "15 3 2 1\n" +
                "After:  [3, 2, 3, 2]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "13 1 2 1\n" +
                "After:  [0, 0, 3, 0]\n" +
                "\n" +
                "Before: [2, 0, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [2, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 2, 0, 3]\n" +
                "10 1 3 2\n" +
                "After:  [3, 2, 0, 3]\n" +
                "\n" +
                "Before: [3, 3, 1, 2]\n" +
                "1 2 2 0\n" +
                "After:  [2, 3, 1, 2]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "6 3 2 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [0, 2, 2, 3]\n" +
                "6 2 2 3\n" +
                "After:  [0, 2, 2, 1]\n" +
                "\n" +
                "Before: [3, 3, 3, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 3, 3, 2]\n" +
                "\n" +
                "Before: [0, 1, 1, 2]\n" +
                "6 0 0 3\n" +
                "After:  [0, 1, 1, 1]\n" +
                "\n" +
                "Before: [0, 0, 3, 0]\n" +
                "0 0 2 0\n" +
                "After:  [0, 0, 3, 0]\n" +
                "\n" +
                "Before: [3, 1, 2, 2]\n" +
                "2 1 2 2\n" +
                "After:  [3, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 1, 1]\n" +
                "1 2 2 2\n" +
                "After:  [1, 1, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 2]\n" +
                "14 0 2 2\n" +
                "After:  [1, 1, 0, 2]\n" +
                "\n" +
                "Before: [2, 1, 1, 1]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [2, 2, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 2, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "2 1 2 2\n" +
                "After:  [1, 1, 0, 3]\n" +
                "\n" +
                "Before: [3, 1, 2, 1]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 3, 1]\n" +
                "6 0 0 3\n" +
                "After:  [0, 2, 3, 1]\n" +
                "\n" +
                "Before: [0, 2, 3, 2]\n" +
                "15 3 2 0\n" +
                "After:  [2, 2, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 3, 1]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [3, 1, 3, 1]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 3]\n" +
                "\n" +
                "Before: [1, 0, 2, 3]\n" +
                "12 0 1 0\n" +
                "After:  [1, 0, 2, 3]\n" +
                "\n" +
                "Before: [3, 0, 2, 3]\n" +
                "8 3 0 0\n" +
                "After:  [1, 0, 2, 3]\n" +
                "\n" +
                "Before: [0, 0, 1, 1]\n" +
                "12 2 1 0\n" +
                "After:  [1, 0, 1, 1]\n" +
                "\n" +
                "Before: [0, 0, 3, 0]\n" +
                "0 0 1 2\n" +
                "After:  [0, 0, 0, 0]\n" +
                "\n" +
                "Before: [0, 3, 1, 2]\n" +
                "7 3 1 1\n" +
                "After:  [0, 1, 1, 2]\n" +
                "\n" +
                "Before: [2, 1, 1, 2]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 2, 3, 1]\n" +
                "0 0 2 0\n" +
                "After:  [0, 2, 3, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [3, 1, 2, 1]\n" +
                "\n" +
                "Before: [3, 3, 1, 2]\n" +
                "7 3 1 2\n" +
                "After:  [3, 3, 1, 2]\n" +
                "\n" +
                "Before: [0, 1, 0, 1]\n" +
                "0 0 3 2\n" +
                "After:  [0, 1, 0, 1]\n" +
                "\n" +
                "Before: [2, 2, 2, 3]\n" +
                "10 2 3 1\n" +
                "After:  [2, 0, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 1, 3]\n" +
                "1 2 2 1\n" +
                "After:  [0, 2, 1, 3]\n" +
                "\n" +
                "Before: [3, 2, 2, 1]\n" +
                "11 3 3 3\n" +
                "After:  [3, 2, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 0, 3]\n" +
                "9 0 0 0\n" +
                "After:  [0, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 0, 2, 2]\n" +
                "8 2 0 3\n" +
                "After:  [2, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 0, 1, 1]\n" +
                "12 2 1 0\n" +
                "After:  [1, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 2, 2, 3]\n" +
                "10 2 3 2\n" +
                "After:  [3, 2, 0, 3]\n" +
                "\n" +
                "Before: [1, 0, 3, 2]\n" +
                "15 3 2 1\n" +
                "After:  [1, 2, 3, 2]\n" +
                "\n" +
                "Before: [1, 3, 1, 2]\n" +
                "11 3 3 3\n" +
                "After:  [1, 3, 1, 0]\n" +
                "\n" +
                "Before: [3, 0, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [2, 0, 1, 2]\n" +
                "12 2 1 3\n" +
                "After:  [2, 0, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "13 1 2 3\n" +
                "After:  [2, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 2]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 2, 2, 0]\n" +
                "0 0 2 0\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [3, 2, 2, 1]\n" +
                "6 2 2 0\n" +
                "After:  [1, 2, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 3, 2, 1]\n" +
                "\n" +
                "Before: [1, 2, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [1, 2, 2, 1]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "10 1 3 3\n" +
                "After:  [3, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 1, 1, 3]\n" +
                "0 0 2 1\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [3, 0, 3, 3]\n" +
                "6 3 3 3\n" +
                "After:  [3, 0, 3, 1]\n" +
                "\n" +
                "Before: [1, 3, 3, 2]\n" +
                "7 3 1 1\n" +
                "After:  [1, 1, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 3, 1]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 3, 1]\n" +
                "\n" +
                "Before: [3, 2, 2, 3]\n" +
                "10 1 3 3\n" +
                "After:  [3, 2, 2, 0]\n" +
                "\n" +
                "Before: [2, 0, 3, 1]\n" +
                "12 3 1 1\n" +
                "After:  [2, 1, 3, 1]\n" +
                "\n" +
                "Before: [0, 2, 0, 3]\n" +
                "10 1 3 2\n" +
                "After:  [0, 2, 0, 3]\n" +
                "\n" +
                "Before: [2, 3, 1, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 3, 1, 3]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [2, 3, 3, 3]\n" +
                "15 0 2 0\n" +
                "After:  [2, 3, 3, 3]\n" +
                "\n" +
                "Before: [2, 2, 3, 3]\n" +
                "15 1 2 2\n" +
                "After:  [2, 2, 2, 3]\n" +
                "\n" +
                "Before: [0, 3, 1, 2]\n" +
                "7 3 1 0\n" +
                "After:  [1, 3, 1, 2]\n" +
                "\n" +
                "Before: [0, 1, 1, 3]\n" +
                "6 3 3 0\n" +
                "After:  [1, 1, 1, 3]\n" +
                "\n" +
                "Before: [1, 0, 1, 1]\n" +
                "11 2 3 0\n" +
                "After:  [0, 0, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [2, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 3, 0, 2]\n" +
                "3 2 3 1\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [2, 0, 1, 3]\n" +
                "10 2 3 0\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [0, 3, 0, 1]\n" +
                "9 0 0 2\n" +
                "After:  [0, 3, 0, 1]\n" +
                "\n" +
                "Before: [3, 2, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [3, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 1, 1]\n" +
                "11 3 3 2\n" +
                "After:  [0, 1, 0, 1]\n" +
                "\n" +
                "Before: [2, 3, 3, 2]\n" +
                "7 3 1 2\n" +
                "After:  [2, 3, 1, 2]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 1, 2, 1]\n" +
                "\n" +
                "Before: [2, 0, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [2, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 0, 1, 1]\n" +
                "12 3 1 2\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 0, 1, 1]\n" +
                "12 3 1 3\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 0, 1, 0]\n" +
                "1 2 2 1\n" +
                "After:  [3, 2, 1, 0]\n" +
                "\n" +
                "Before: [3, 0, 2, 1]\n" +
                "11 3 3 1\n" +
                "After:  [3, 0, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 1, 3]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 1, 3]\n" +
                "\n" +
                "Before: [0, 2, 2, 3]\n" +
                "0 0 3 1\n" +
                "After:  [0, 0, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 1]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 1]\n" +
                "\n" +
                "Before: [1, 0, 1, 3]\n" +
                "1 2 2 3\n" +
                "After:  [1, 0, 1, 2]\n" +
                "\n" +
                "Before: [1, 3, 2, 1]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 2]\n" +
                "2 1 2 1\n" +
                "After:  [3, 0, 2, 2]\n" +
                "\n" +
                "Before: [0, 1, 3, 2]\n" +
                "15 3 2 1\n" +
                "After:  [0, 2, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 0, 1]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 3]\n" +
                "6 0 0 3\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 0, 2]\n" +
                "3 2 3 2\n" +
                "After:  [1, 3, 1, 2]\n" +
                "\n" +
                "Before: [2, 1, 1, 0]\n" +
                "1 2 2 0\n" +
                "After:  [2, 1, 1, 0]\n" +
                "\n" +
                "Before: [0, 2, 0, 2]\n" +
                "6 0 0 0\n" +
                "After:  [1, 2, 0, 2]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "8 3 0 3\n" +
                "After:  [3, 1, 3, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 1, 2]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 1, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 1]\n" +
                "12 3 1 3\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [3, 0, 1, 3]\n" +
                "12 2 1 0\n" +
                "After:  [1, 0, 1, 3]\n" +
                "\n" +
                "Before: [2, 2, 2, 3]\n" +
                "6 3 2 1\n" +
                "After:  [2, 0, 2, 3]\n" +
                "\n" +
                "Before: [1, 2, 2, 3]\n" +
                "14 0 2 2\n" +
                "After:  [1, 2, 0, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "2 1 2 2\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 2, 2, 2]\n" +
                "0 0 1 2\n" +
                "After:  [0, 2, 0, 2]\n" +
                "\n" +
                "Before: [1, 0, 1, 3]\n" +
                "10 2 3 2\n" +
                "After:  [1, 0, 0, 3]\n" +
                "\n" +
                "Before: [1, 1, 3, 3]\n" +
                "13 1 2 2\n" +
                "After:  [1, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 2, 3, 2]\n" +
                "15 1 2 2\n" +
                "After:  [2, 2, 2, 2]\n" +
                "\n" +
                "Before: [0, 0, 0, 1]\n" +
                "6 0 0 2\n" +
                "After:  [0, 0, 1, 1]\n" +
                "\n" +
                "Before: [1, 1, 0, 2]\n" +
                "3 2 3 1\n" +
                "After:  [1, 1, 0, 2]\n" +
                "\n" +
                "Before: [2, 1, 0, 2]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [1, 1, 3, 0]\n" +
                "13 1 2 1\n" +
                "After:  [1, 0, 3, 0]\n" +
                "\n" +
                "Before: [1, 3, 3, 2]\n" +
                "15 3 2 1\n" +
                "After:  [1, 2, 3, 2]\n" +
                "\n" +
                "Before: [1, 2, 1, 1]\n" +
                "1 2 2 2\n" +
                "After:  [1, 2, 2, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 2]\n" +
                "6 2 2 2\n" +
                "After:  [1, 3, 1, 2]\n" +
                "\n" +
                "Before: [0, 3, 3, 1]\n" +
                "8 2 3 3\n" +
                "After:  [0, 3, 3, 0]\n" +
                "\n" +
                "Before: [3, 2, 3, 3]\n" +
                "10 1 3 2\n" +
                "After:  [3, 2, 0, 3]\n" +
                "\n" +
                "Before: [1, 1, 3, 1]\n" +
                "13 1 2 3\n" +
                "After:  [1, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 2, 2, 1]\n" +
                "6 2 2 1\n" +
                "After:  [2, 1, 2, 1]\n" +
                "\n" +
                "Before: [1, 2, 2, 1]\n" +
                "14 0 2 1\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [0, 0, 1, 1]\n" +
                "9 0 0 2\n" +
                "After:  [0, 0, 0, 1]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 2, 0]\n" +
                "8 2 1 2\n" +
                "After:  [0, 2, 1, 0]\n" +
                "\n" +
                "Before: [3, 0, 0, 3]\n" +
                "8 3 0 2\n" +
                "After:  [3, 0, 1, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "2 1 2 1\n" +
                "After:  [2, 0, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 1, 3]\n" +
                "6 3 2 1\n" +
                "After:  [1, 0, 1, 3]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [0, 1, 1, 1]\n" +
                "\n" +
                "Before: [3, 0, 0, 2]\n" +
                "7 3 0 1\n" +
                "After:  [3, 1, 0, 2]\n" +
                "\n" +
                "Before: [2, 1, 0, 3]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [0, 2, 1, 2]\n" +
                "1 2 2 3\n" +
                "After:  [0, 2, 1, 2]\n" +
                "\n" +
                "Before: [2, 0, 1, 1]\n" +
                "1 2 2 1\n" +
                "After:  [2, 2, 1, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 0]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "11 3 3 1\n" +
                "After:  [0, 0, 2, 1]\n" +
                "\n" +
                "Before: [2, 0, 1, 0]\n" +
                "12 2 1 0\n" +
                "After:  [1, 0, 1, 0]\n" +
                "\n" +
                "Before: [2, 1, 0, 3]\n" +
                "10 1 3 3\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [2, 2, 2, 3]\n" +
                "10 1 3 0\n" +
                "After:  [0, 2, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 2, 2]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 2, 2]\n" +
                "\n" +
                "Before: [1, 2, 2, 1]\n" +
                "14 0 2 0\n" +
                "After:  [0, 2, 2, 1]\n" +
                "\n" +
                "Before: [3, 2, 0, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 2, 0, 2]\n" +
                "\n" +
                "Before: [3, 3, 0, 2]\n" +
                "7 3 1 3\n" +
                "After:  [3, 3, 0, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 2]\n" +
                "2 1 2 2\n" +
                "After:  [1, 1, 0, 2]\n" +
                "\n" +
                "Before: [3, 2, 3, 3]\n" +
                "6 3 3 2\n" +
                "After:  [3, 2, 1, 3]\n" +
                "\n" +
                "Before: [1, 0, 3, 0]\n" +
                "3 3 2 0\n" +
                "After:  [1, 0, 3, 0]\n" +
                "\n" +
                "Before: [2, 3, 0, 1]\n" +
                "11 3 3 3\n" +
                "After:  [2, 3, 0, 0]\n" +
                "\n" +
                "Before: [1, 1, 1, 3]\n" +
                "6 3 2 0\n" +
                "After:  [0, 1, 1, 3]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 3, 2]\n" +
                "\n" +
                "Before: [0, 1, 1, 2]\n" +
                "11 3 3 2\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [1, 0, 1, 1]\n" +
                "12 3 1 1\n" +
                "After:  [1, 1, 1, 1]\n" +
                "\n" +
                "Before: [2, 0, 0, 1]\n" +
                "12 3 1 3\n" +
                "After:  [2, 0, 0, 1]\n" +
                "\n" +
                "Before: [3, 1, 3, 2]\n" +
                "15 3 2 2\n" +
                "After:  [3, 1, 2, 2]\n" +
                "\n" +
                "Before: [2, 2, 3, 3]\n" +
                "15 0 2 2\n" +
                "After:  [2, 2, 2, 3]\n" +
                "\n" +
                "Before: [0, 0, 2, 3]\n" +
                "6 2 2 2\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [3, 2, 2, 2]\n" +
                "11 3 3 1\n" +
                "After:  [3, 0, 2, 2]\n" +
                "\n" +
                "Before: [2, 0, 3, 1]\n" +
                "15 0 2 0\n" +
                "After:  [2, 0, 3, 1]\n" +
                "\n" +
                "Before: [3, 1, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [3, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 2]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 0, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [2, 0, 3, 1]\n" +
                "\n" +
                "Before: [1, 1, 0, 2]\n" +
                "3 2 3 0\n" +
                "After:  [1, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 1, 0, 1]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 0, 1]\n" +
                "\n" +
                "Before: [3, 0, 1, 0]\n" +
                "12 2 1 3\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 1]\n" +
                "11 3 3 1\n" +
                "After:  [2, 0, 0, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 0]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [2, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "9 0 0 0\n" +
                "After:  [0, 2, 2, 1]\n" +
                "\n" +
                "Before: [3, 2, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [3, 2, 3, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [3, 0, 3, 2]\n" +
                "11 3 3 1\n" +
                "After:  [3, 0, 3, 2]\n" +
                "\n" +
                "Before: [1, 2, 2, 3]\n" +
                "10 1 3 0\n" +
                "After:  [0, 2, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "13 1 2 1\n" +
                "After:  [2, 0, 3, 3]\n" +
                "\n" +
                "Before: [1, 2, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [1, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 2, 2]\n" +
                "11 3 3 0\n" +
                "After:  [0, 1, 2, 2]\n" +
                "\n" +
                "Before: [0, 0, 0, 2]\n" +
                "3 2 3 1\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [1, 0, 2, 0]\n" +
                "14 0 2 0\n" +
                "After:  [0, 0, 2, 0]\n" +
                "\n" +
                "Before: [2, 2, 1, 1]\n" +
                "11 3 3 3\n" +
                "After:  [2, 2, 1, 0]\n" +
                "\n" +
                "Before: [0, 2, 1, 0]\n" +
                "0 0 2 0\n" +
                "After:  [0, 2, 1, 0]\n" +
                "\n" +
                "Before: [2, 3, 3, 3]\n" +
                "6 3 3 0\n" +
                "After:  [1, 3, 3, 3]\n" +
                "\n" +
                "Before: [1, 2, 3, 2]\n" +
                "15 3 2 3\n" +
                "After:  [1, 2, 3, 2]\n" +
                "\n" +
                "Before: [2, 2, 2, 3]\n" +
                "10 1 3 1\n" +
                "After:  [2, 0, 2, 3]\n" +
                "\n" +
                "Before: [3, 1, 2, 2]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 2]\n" +
                "\n" +
                "Before: [3, 1, 1, 3]\n" +
                "8 3 0 0\n" +
                "After:  [1, 1, 1, 3]\n" +
                "\n" +
                "Before: [3, 2, 3, 1]\n" +
                "15 1 2 2\n" +
                "After:  [3, 2, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "10 2 3 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 3, 1, 0]\n" +
                "1 2 2 2\n" +
                "After:  [2, 3, 2, 0]\n" +
                "\n" +
                "Before: [0, 1, 3, 2]\n" +
                "13 1 2 2\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 2, 0, 1]\n" +
                "9 0 0 0\n" +
                "After:  [0, 2, 0, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 1]\n" +
                "6 0 0 1\n" +
                "After:  [0, 1, 3, 1]\n" +
                "\n" +
                "Before: [1, 3, 1, 2]\n" +
                "7 3 1 0\n" +
                "After:  [1, 3, 1, 2]\n" +
                "\n" +
                "Before: [0, 3, 1, 3]\n" +
                "10 2 3 3\n" +
                "After:  [0, 3, 1, 0]\n" +
                "\n" +
                "Before: [2, 0, 1, 0]\n" +
                "12 2 1 2\n" +
                "After:  [2, 0, 1, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [1, 1, 1, 1]\n" +
                "\n" +
                "Before: [1, 3, 2, 0]\n" +
                "14 0 2 2\n" +
                "After:  [1, 3, 0, 0]\n" +
                "\n" +
                "Before: [0, 1, 3, 3]\n" +
                "10 1 3 2\n" +
                "After:  [0, 1, 0, 3]\n" +
                "\n" +
                "Before: [1, 0, 2, 1]\n" +
                "5 3 2 2\n" +
                "After:  [1, 0, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 0]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 2]\n" +
                "0 0 3 1\n" +
                "After:  [0, 0, 2, 2]\n" +
                "\n" +
                "Before: [0, 1, 0, 0]\n" +
                "0 0 1 3\n" +
                "After:  [0, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 2, 3, 2]\n" +
                "15 1 2 2\n" +
                "After:  [0, 2, 2, 2]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 2, 0]\n" +
                "9 0 0 0\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [3, 1, 3, 0]\n" +
                "13 1 2 2\n" +
                "After:  [3, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 0, 3, 0]\n" +
                "3 3 2 0\n" +
                "After:  [1, 0, 3, 0]\n" +
                "\n" +
                "Before: [0, 3, 2, 2]\n" +
                "6 0 0 0\n" +
                "After:  [1, 3, 2, 2]\n" +
                "\n" +
                "Before: [3, 2, 3, 0]\n" +
                "15 1 2 0\n" +
                "After:  [2, 2, 3, 0]\n" +
                "\n" +
                "Before: [3, 1, 3, 0]\n" +
                "3 3 2 0\n" +
                "After:  [1, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 0, 2, 1]\n" +
                "12 3 1 0\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [0, 0, 0, 1]\n" +
                "0 0 2 1\n" +
                "After:  [0, 0, 0, 1]\n" +
                "\n" +
                "Before: [0, 0, 3, 1]\n" +
                "12 3 1 3\n" +
                "After:  [0, 0, 3, 1]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "8 3 2 0\n" +
                "After:  [1, 1, 3, 3]\n" +
                "\n" +
                "Before: [2, 1, 0, 1]\n" +
                "11 3 3 2\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [0, 3, 1, 3]\n" +
                "9 0 0 0\n" +
                "After:  [0, 3, 1, 3]\n" +
                "\n" +
                "Before: [1, 2, 1, 2]\n" +
                "1 2 2 0\n" +
                "After:  [2, 2, 1, 2]\n" +
                "\n" +
                "Before: [0, 1, 2, 2]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 2]\n" +
                "\n" +
                "Before: [3, 2, 0, 3]\n" +
                "10 1 3 0\n" +
                "After:  [0, 2, 0, 3]\n" +
                "\n" +
                "Before: [1, 2, 0, 2]\n" +
                "11 3 3 2\n" +
                "After:  [1, 2, 0, 2]\n" +
                "\n" +
                "Before: [0, 1, 1, 1]\n" +
                "11 2 3 2\n" +
                "After:  [0, 1, 0, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "10 2 3 2\n" +
                "After:  [3, 1, 0, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "2 1 2 3\n" +
                "After:  [2, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 3, 1, 0]\n" +
                "9 0 0 2\n" +
                "After:  [0, 3, 0, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 1]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 3, 1]\n" +
                "\n" +
                "Before: [1, 2, 3, 3]\n" +
                "15 1 2 0\n" +
                "After:  [2, 2, 3, 3]\n" +
                "\n" +
                "Before: [0, 0, 2, 0]\n" +
                "9 0 0 3\n" +
                "After:  [0, 0, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 0]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [0, 3, 1, 2]\n" +
                "7 3 1 2\n" +
                "After:  [0, 3, 1, 2]\n" +
                "\n" +
                "Before: [0, 3, 0, 0]\n" +
                "0 0 3 3\n" +
                "After:  [0, 3, 0, 0]\n" +
                "\n" +
                "Before: [3, 1, 2, 1]\n" +
                "2 1 2 3\n" +
                "After:  [3, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 1, 3]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 1, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "8 2 0 3\n" +
                "After:  [2, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 3, 2, 2]\n" +
                "6 0 0 2\n" +
                "After:  [0, 3, 1, 2]\n" +
                "\n" +
                "Before: [1, 0, 1, 2]\n" +
                "1 2 2 3\n" +
                "After:  [1, 0, 1, 2]\n" +
                "\n" +
                "Before: [3, 3, 3, 2]\n" +
                "7 3 1 1\n" +
                "After:  [3, 1, 3, 2]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "2 1 2 1\n" +
                "After:  [2, 0, 2, 3]\n" +
                "\n" +
                "Before: [1, 2, 3, 1]\n" +
                "11 3 3 3\n" +
                "After:  [1, 2, 3, 0]\n" +
                "\n" +
                "Before: [1, 0, 2, 1]\n" +
                "11 3 3 1\n" +
                "After:  [1, 0, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 3, 0]\n" +
                "\n" +
                "Before: [3, 0, 2, 2]\n" +
                "7 3 0 3\n" +
                "After:  [3, 0, 2, 1]\n" +
                "\n" +
                "Before: [2, 0, 3, 2]\n" +
                "15 3 2 0\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [3, 3, 3, 3]\n" +
                "8 3 0 1\n" +
                "After:  [3, 1, 3, 3]\n" +
                "\n" +
                "Before: [1, 0, 2, 2]\n" +
                "14 0 2 0\n" +
                "After:  [0, 0, 2, 2]\n" +
                "\n" +
                "Before: [2, 0, 2, 2]\n" +
                "11 3 3 2\n" +
                "After:  [2, 0, 0, 2]\n" +
                "\n" +
                "Before: [2, 0, 3, 0]\n" +
                "15 0 2 2\n" +
                "After:  [2, 0, 2, 0]\n" +
                "\n" +
                "Before: [2, 2, 3, 3]\n" +
                "8 3 2 2\n" +
                "After:  [2, 2, 1, 3]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "14 0 2 2\n" +
                "After:  [1, 1, 0, 1]\n" +
                "\n" +
                "Before: [2, 2, 3, 2]\n" +
                "15 0 2 3\n" +
                "After:  [2, 2, 3, 2]\n" +
                "\n" +
                "Before: [2, 2, 2, 0]\n" +
                "8 2 1 0\n" +
                "After:  [1, 2, 2, 0]\n" +
                "\n" +
                "Before: [3, 2, 0, 2]\n" +
                "7 3 0 3\n" +
                "After:  [3, 2, 0, 1]\n" +
                "\n" +
                "Before: [0, 3, 1, 0]\n" +
                "0 0 1 0\n" +
                "After:  [0, 3, 1, 0]\n" +
                "\n" +
                "Before: [3, 0, 0, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 0, 0, 2]\n" +
                "\n" +
                "Before: [3, 3, 0, 2]\n" +
                "3 2 3 2\n" +
                "After:  [3, 3, 1, 2]\n" +
                "\n" +
                "Before: [2, 0, 2, 0]\n" +
                "8 2 0 2\n" +
                "After:  [2, 0, 1, 0]\n" +
                "\n" +
                "Before: [2, 1, 0, 0]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 3]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "15 3 2 1\n" +
                "After:  [2, 2, 3, 2]\n" +
                "\n" +
                "Before: [3, 2, 0, 2]\n" +
                "7 3 0 2\n" +
                "After:  [3, 2, 1, 2]\n" +
                "\n" +
                "Before: [1, 2, 2, 2]\n" +
                "14 0 2 2\n" +
                "After:  [1, 2, 0, 2]\n" +
                "\n" +
                "Before: [2, 1, 3, 1]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 1]\n" +
                "\n" +
                "Before: [1, 3, 3, 2]\n" +
                "7 3 1 3\n" +
                "After:  [1, 3, 3, 1]\n" +
                "\n" +
                "Before: [1, 0, 0, 2]\n" +
                "12 0 1 2\n" +
                "After:  [1, 0, 1, 2]\n" +
                "\n" +
                "Before: [0, 0, 3, 3]\n" +
                "6 3 3 2\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [3, 1, 3, 2]\n" +
                "13 1 2 0\n" +
                "After:  [0, 1, 3, 2]\n" +
                "\n" +
                "Before: [1, 3, 2, 2]\n" +
                "14 0 2 3\n" +
                "After:  [1, 3, 2, 0]\n" +
                "\n" +
                "Before: [2, 2, 3, 0]\n" +
                "3 3 2 2\n" +
                "After:  [2, 2, 1, 0]\n" +
                "\n" +
                "Before: [3, 1, 3, 0]\n" +
                "13 1 2 1\n" +
                "After:  [3, 0, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 1, 2]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 1, 2]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "13 1 2 3\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [0, 2, 3, 3]\n" +
                "8 3 2 3\n" +
                "After:  [0, 2, 3, 1]\n" +
                "\n" +
                "Before: [0, 2, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [0, 2, 2, 1]\n" +
                "\n" +
                "Before: [0, 2, 2, 3]\n" +
                "10 1 3 3\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "14 0 2 0\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 1]\n" +
                "13 1 2 2\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [0, 3, 3, 0]\n" +
                "0 0 2 2\n" +
                "After:  [0, 3, 0, 0]\n" +
                "\n" +
                "Before: [2, 3, 1, 1]\n" +
                "1 2 2 0\n" +
                "After:  [2, 3, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 2, 3]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 2, 2, 2]\n" +
                "8 2 0 0\n" +
                "After:  [1, 2, 2, 2]\n" +
                "\n" +
                "Before: [1, 0, 0, 1]\n" +
                "12 0 1 0\n" +
                "After:  [1, 0, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "13 1 2 2\n" +
                "After:  [2, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 1, 2, 3]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [2, 2, 1, 1]\n" +
                "1 2 2 0\n" +
                "After:  [2, 2, 1, 1]\n" +
                "\n" +
                "Before: [3, 3, 1, 2]\n" +
                "7 3 1 1\n" +
                "After:  [3, 1, 1, 2]\n" +
                "\n" +
                "Before: [3, 3, 1, 2]\n" +
                "1 2 2 3\n" +
                "After:  [3, 3, 1, 2]\n" +
                "\n" +
                "Before: [0, 0, 2, 0]\n" +
                "0 0 3 3\n" +
                "After:  [0, 0, 2, 0]\n" +
                "\n" +
                "Before: [1, 1, 3, 1]\n" +
                "11 3 3 2\n" +
                "After:  [1, 1, 0, 1]\n" +
                "\n" +
                "Before: [3, 0, 3, 0]\n" +
                "3 3 2 0\n" +
                "After:  [1, 0, 3, 0]\n" +
                "\n" +
                "Before: [2, 2, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [2, 2, 3, 1]\n" +
                "\n" +
                "Before: [0, 1, 0, 2]\n" +
                "3 2 3 1\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "2 1 2 0\n" +
                "After:  [0, 1, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 3, 3]\n" +
                "8 3 2 1\n" +
                "After:  [0, 1, 3, 3]\n" +
                "\n" +
                "Before: [0, 0, 3, 0]\n" +
                "3 3 2 1\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [3, 0, 3, 1]\n" +
                "12 3 1 2\n" +
                "After:  [3, 0, 1, 1]\n" +
                "\n" +
                "Before: [3, 1, 3, 2]\n" +
                "13 1 2 3\n" +
                "After:  [3, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 1, 2, 0]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [3, 3, 3, 2]\n" +
                "15 3 2 2\n" +
                "After:  [3, 3, 2, 2]\n" +
                "\n" +
                "Before: [3, 0, 3, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 0, 3, 2]\n" +
                "\n" +
                "Before: [3, 0, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [3, 0, 2, 1]\n" +
                "\n" +
                "Before: [2, 2, 3, 2]\n" +
                "15 0 2 1\n" +
                "After:  [2, 2, 3, 2]\n" +
                "\n" +
                "Before: [0, 2, 2, 2]\n" +
                "9 0 0 3\n" +
                "After:  [0, 2, 2, 0]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "8 3 2 3\n" +
                "After:  [3, 1, 3, 1]\n" +
                "\n" +
                "Before: [0, 0, 1, 3]\n" +
                "12 2 1 1\n" +
                "After:  [0, 1, 1, 3]\n" +
                "\n" +
                "Before: [1, 0, 1, 3]\n" +
                "12 2 1 2\n" +
                "After:  [1, 0, 1, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 2]\n" +
                "2 1 2 3\n" +
                "After:  [2, 1, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 1]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 3, 0]\n" +
                "\n" +
                "Before: [3, 1, 2, 3]\n" +
                "2 1 2 2\n" +
                "After:  [3, 1, 0, 3]\n" +
                "\n" +
                "Before: [1, 1, 3, 3]\n" +
                "13 1 2 3\n" +
                "After:  [1, 1, 3, 0]\n" +
                "\n" +
                "Before: [2, 2, 3, 1]\n" +
                "15 1 2 3\n" +
                "After:  [2, 2, 3, 2]\n" +
                "\n" +
                "Before: [3, 1, 2, 2]\n" +
                "11 3 3 0\n" +
                "After:  [0, 1, 2, 2]\n" +
                "\n" +
                "Before: [3, 1, 3, 3]\n" +
                "8 3 2 2\n" +
                "After:  [3, 1, 1, 3]\n" +
                "\n" +
                "Before: [0, 3, 3, 3]\n" +
                "0 0 3 3\n" +
                "After:  [0, 3, 3, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 3]\n" +
                "2 1 2 1\n" +
                "After:  [0, 0, 2, 3]\n" +
                "\n" +
                "Before: [0, 1, 3, 3]\n" +
                "13 1 2 2\n" +
                "After:  [0, 1, 0, 3]\n" +
                "\n" +
                "Before: [3, 2, 3, 1]\n" +
                "11 3 3 2\n" +
                "After:  [3, 2, 0, 1]\n" +
                "\n" +
                "Before: [0, 2, 3, 3]\n" +
                "8 3 2 2\n" +
                "After:  [0, 2, 1, 3]\n" +
                "\n" +
                "Before: [3, 1, 3, 0]\n" +
                "3 3 2 2\n" +
                "After:  [3, 1, 1, 0]\n" +
                "\n" +
                "Before: [3, 0, 0, 2]\n" +
                "7 3 0 3\n" +
                "After:  [3, 0, 0, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 3]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 3, 3]\n" +
                "\n" +
                "Before: [2, 1, 2, 1]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 3, 2]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 2]\n" +
                "\n" +
                "Before: [0, 3, 3, 3]\n" +
                "8 3 2 3\n" +
                "After:  [0, 3, 3, 1]\n" +
                "\n" +
                "Before: [1, 2, 0, 3]\n" +
                "10 1 3 2\n" +
                "After:  [1, 2, 0, 3]\n" +
                "\n" +
                "Before: [1, 2, 2, 2]\n" +
                "14 0 2 0\n" +
                "After:  [0, 2, 2, 2]\n" +
                "\n" +
                "Before: [0, 3, 2, 2]\n" +
                "7 3 1 3\n" +
                "After:  [0, 3, 2, 1]\n" +
                "\n" +
                "Before: [1, 1, 2, 1]\n" +
                "5 3 2 0\n" +
                "After:  [1, 1, 2, 1]\n" +
                "\n" +
                "Before: [1, 2, 1, 1]\n" +
                "11 3 3 0\n" +
                "After:  [0, 2, 1, 1]\n" +
                "\n" +
                "Before: [2, 1, 0, 1]\n" +
                "4 1 0 0\n" +
                "After:  [0, 1, 0, 1]\n" +
                "\n" +
                "Before: [3, 2, 2, 3]\n" +
                "8 2 1 3\n" +
                "After:  [3, 2, 2, 1]\n" +
                "\n" +
                "Before: [3, 1, 2, 2]\n" +
                "7 3 0 3\n" +
                "After:  [3, 1, 2, 1]\n" +
                "\n" +
                "Before: [2, 1, 1, 3]\n" +
                "1 2 2 1\n" +
                "After:  [2, 2, 1, 3]\n" +
                "\n" +
                "Before: [0, 0, 2, 0]\n" +
                "0 0 1 2\n" +
                "After:  [0, 0, 0, 0]\n" +
                "\n" +
                "Before: [0, 3, 0, 1]\n" +
                "0 0 3 2\n" +
                "After:  [0, 3, 0, 1]\n" +
                "\n" +
                "Before: [0, 2, 1, 3]\n" +
                "0 0 3 1\n" +
                "After:  [0, 0, 1, 3]\n" +
                "\n" +
                "Before: [1, 3, 3, 0]\n" +
                "3 3 2 3\n" +
                "After:  [1, 3, 3, 1]\n" +
                "\n" +
                "Before: [0, 0, 3, 1]\n" +
                "9 0 0 2\n" +
                "After:  [0, 0, 0, 1]\n" +
                "\n" +
                "Before: [0, 0, 2, 1]\n" +
                "12 3 1 1\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 2, 2]\n" +
                "9 0 0 2\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [2, 0, 3, 2]\n" +
                "15 0 2 0\n" +
                "After:  [2, 0, 3, 2]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "0 0 1 3\n" +
                "After:  [0, 1, 2, 0]\n" +
                "\n" +
                "Before: [1, 2, 2, 0]\n" +
                "14 0 2 2\n" +
                "After:  [1, 2, 0, 0]\n" +
                "\n" +
                "Before: [0, 2, 0, 3]\n" +
                "9 0 0 2\n" +
                "After:  [0, 2, 0, 3]\n" +
                "\n" +
                "Before: [1, 2, 2, 3]\n" +
                "6 3 1 1\n" +
                "After:  [1, 0, 2, 3]\n" +
                "\n" +
                "Before: [1, 0, 2, 0]\n" +
                "6 2 2 1\n" +
                "After:  [1, 1, 2, 0]\n" +
                "\n" +
                "Before: [0, 0, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 3, 0, 1]\n" +
                "0 0 2 1\n" +
                "After:  [0, 0, 0, 1]\n" +
                "\n" +
                "Before: [3, 3, 2, 1]\n" +
                "6 2 2 1\n" +
                "After:  [3, 1, 2, 1]\n" +
                "\n" +
                "Before: [1, 0, 3, 0]\n" +
                "3 3 2 2\n" +
                "After:  [1, 0, 1, 0]\n" +
                "\n" +
                "Before: [1, 2, 2, 0]\n" +
                "14 0 2 3\n" +
                "After:  [1, 2, 2, 0]\n" +
                "\n" +
                "Before: [3, 2, 2, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 2, 2, 2]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "13 1 2 2\n" +
                "After:  [0, 1, 0, 0]\n" +
                "\n" +
                "Before: [1, 1, 3, 1]\n" +
                "8 2 3 2\n" +
                "After:  [1, 1, 0, 1]\n" +
                "\n" +
                "Before: [2, 1, 1, 1]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 1, 0]\n" +
                "\n" +
                "Before: [0, 1, 2, 1]\n" +
                "5 3 2 3\n" +
                "After:  [0, 1, 2, 1]\n" +
                "\n" +
                "Before: [0, 1, 0, 3]\n" +
                "6 3 3 0\n" +
                "After:  [1, 1, 0, 3]\n" +
                "\n" +
                "Before: [0, 0, 3, 1]\n" +
                "12 3 1 0\n" +
                "After:  [1, 0, 3, 1]\n" +
                "\n" +
                "Before: [0, 3, 0, 2]\n" +
                "9 0 0 1\n" +
                "After:  [0, 0, 0, 2]\n" +
                "\n" +
                "Before: [2, 2, 2, 1]\n" +
                "5 3 2 1\n" +
                "After:  [2, 1, 2, 1]\n" +
                "\n" +
                "Before: [2, 3, 3, 1]\n" +
                "15 0 2 1\n" +
                "After:  [2, 2, 3, 1]\n" +
                "\n" +
                "Before: [2, 2, 2, 2]\n" +
                "8 2 0 3\n" +
                "After:  [2, 2, 2, 1]\n" +
                "\n" +
                "Before: [1, 2, 3, 1]\n" +
                "8 2 3 2\n" +
                "After:  [1, 2, 0, 1]\n" +
                "\n" +
                "Before: [0, 1, 3, 0]\n" +
                "9 0 0 3\n" +
                "After:  [0, 1, 3, 0]\n" +
                "\n" +
                "Before: [1, 2, 3, 2]\n" +
                "15 3 2 1\n" +
                "After:  [1, 2, 3, 2]\n" +
                "\n" +
                "Before: [1, 1, 2, 0]\n" +
                "2 1 2 2\n" +
                "After:  [1, 1, 0, 0]\n" +
                "\n" +
                "Before: [3, 1, 1, 3]\n" +
                "10 1 3 1\n" +
                "After:  [3, 0, 1, 3]\n" +
                "\n" +
                "Before: [3, 3, 2, 3]\n" +
                "10 2 3 3\n" +
                "After:  [3, 3, 2, 0]\n" +
                "\n" +
                "Before: [2, 1, 3, 3]\n" +
                "4 1 0 2\n" +
                "After:  [2, 1, 0, 3]\n" +
                "\n" +
                "Before: [0, 1, 0, 2]\n" +
                "0 0 1 2\n" +
                "After:  [0, 1, 0, 2]\n" +
                "\n" +
                "Before: [2, 1, 1, 0]\n" +
                "4 1 0 3\n" +
                "After:  [2, 1, 1, 0]\n" +
                "\n" +
                "Before: [3, 2, 1, 0]\n" +
                "1 2 2 1\n" +
                "After:  [3, 2, 1, 0]\n" +
                "\n" +
                "Before: [2, 1, 0, 2]\n" +
                "4 1 0 1\n" +
                "After:  [2, 0, 0, 2]\n" +
                "\n" +
                "Before: [0, 0, 3, 3]\n" +
                "0 0 3 3\n" +
                "After:  [0, 0, 3, 0]\n" +
                "\n" +
                "Before: [3, 1, 3, 2]\n" +
                "6 2 1 0\n" +
                "After:  [0, 1, 3, 2]\n" +
                "\n" +
                "Before: [3, 3, 1, 2]\n" +
                "7 3 0 0\n" +
                "After:  [1, 3, 1, 2]\n" +
                "\n" +
                "Before: [1, 1, 3, 2]\n" +
                "13 1 2 3\n" +
                "After:  [1, 1, 3, 0]";

    }

    public static String getInputB() {
        return "14 3 3 2\n" +
                "14 3 3 0\n" +
                "14 2 2 1\n" +
                "13 0 2 1\n" +
                "4 1 2 1\n" +
                "1 3 1 3\n" +
                "14 2 3 1\n" +
                "14 2 0 0\n" +
                "6 0 2 0\n" +
                "4 0 3 0\n" +
                "1 0 3 3\n" +
                "14 2 3 0\n" +
                "6 0 2 2\n" +
                "4 2 2 2\n" +
                "1 3 2 3\n" +
                "5 3 0 0\n" +
                "14 3 2 1\n" +
                "4 2 0 2\n" +
                "9 2 1 2\n" +
                "4 1 0 3\n" +
                "9 3 2 3\n" +
                "13 1 2 1\n" +
                "4 1 3 1\n" +
                "1 1 0 0\n" +
                "5 0 1 2\n" +
                "14 0 0 3\n" +
                "14 2 1 0\n" +
                "4 0 0 1\n" +
                "9 1 3 1\n" +
                "2 1 0 1\n" +
                "4 1 2 1\n" +
                "1 1 2 2\n" +
                "5 2 3 3\n" +
                "14 3 1 1\n" +
                "14 1 0 0\n" +
                "14 3 1 2\n" +
                "9 0 1 1\n" +
                "4 1 3 1\n" +
                "4 1 3 1\n" +
                "1 3 1 3\n" +
                "5 3 1 0\n" +
                "14 2 0 1\n" +
                "14 0 2 3\n" +
                "15 1 3 2\n" +
                "4 2 3 2\n" +
                "1 2 0 0\n" +
                "5 0 0 1\n" +
                "14 1 1 2\n" +
                "14 2 1 3\n" +
                "14 2 2 0\n" +
                "8 0 3 2\n" +
                "4 2 3 2\n" +
                "1 1 2 1\n" +
                "14 3 3 0\n" +
                "14 0 2 2\n" +
                "14 3 1 3\n" +
                "14 2 3 0\n" +
                "4 0 1 0\n" +
                "1 0 1 1\n" +
                "5 1 3 3\n" +
                "14 2 0 1\n" +
                "14 3 1 2\n" +
                "14 1 0 0\n" +
                "12 1 2 1\n" +
                "4 1 1 1\n" +
                "1 1 3 3\n" +
                "5 3 3 0\n" +
                "4 1 0 1\n" +
                "9 1 0 1\n" +
                "14 0 0 2\n" +
                "14 2 1 3\n" +
                "3 2 3 3\n" +
                "4 3 3 3\n" +
                "1 3 0 0\n" +
                "5 0 2 3\n" +
                "4 0 0 2\n" +
                "9 2 3 2\n" +
                "4 1 0 0\n" +
                "9 0 1 0\n" +
                "14 3 3 1\n" +
                "13 1 2 0\n" +
                "4 0 3 0\n" +
                "1 3 0 3\n" +
                "5 3 1 0\n" +
                "14 3 3 3\n" +
                "13 3 2 3\n" +
                "4 3 2 3\n" +
                "4 3 2 3\n" +
                "1 3 0 0\n" +
                "5 0 2 2\n" +
                "14 2 3 0\n" +
                "14 2 3 3\n" +
                "7 0 1 3\n" +
                "4 3 2 3\n" +
                "1 3 2 2\n" +
                "5 2 1 1\n" +
                "14 1 0 3\n" +
                "14 2 2 2\n" +
                "1 3 3 3\n" +
                "4 3 2 3\n" +
                "1 1 3 1\n" +
                "5 1 2 2\n" +
                "4 1 0 1\n" +
                "9 1 0 1\n" +
                "14 3 0 3\n" +
                "14 1 3 3\n" +
                "4 3 1 3\n" +
                "1 2 3 2\n" +
                "14 3 1 1\n" +
                "4 3 0 0\n" +
                "9 0 3 0\n" +
                "14 1 0 3\n" +
                "9 3 1 3\n" +
                "4 3 1 3\n" +
                "1 3 2 2\n" +
                "5 2 0 1\n" +
                "4 1 0 2\n" +
                "9 2 1 2\n" +
                "14 2 3 3\n" +
                "4 3 0 0\n" +
                "9 0 2 0\n" +
                "8 0 3 0\n" +
                "4 0 2 0\n" +
                "4 0 3 0\n" +
                "1 1 0 1\n" +
                "14 1 0 0\n" +
                "14 0 2 3\n" +
                "14 2 3 2\n" +
                "5 0 2 3\n" +
                "4 3 1 3\n" +
                "1 1 3 1\n" +
                "5 1 2 0\n" +
                "4 2 0 1\n" +
                "9 1 1 1\n" +
                "14 2 0 3\n" +
                "15 2 3 2\n" +
                "4 2 1 2\n" +
                "4 2 2 2\n" +
                "1 0 2 0\n" +
                "5 0 1 2\n" +
                "4 0 0 3\n" +
                "9 3 1 3\n" +
                "14 0 1 0\n" +
                "14 3 0 0\n" +
                "4 0 2 0\n" +
                "1 2 0 2\n" +
                "5 2 0 0\n" +
                "14 2 0 2\n" +
                "4 0 0 1\n" +
                "9 1 3 1\n" +
                "7 2 1 3\n" +
                "4 3 3 3\n" +
                "1 3 0 0\n" +
                "14 1 1 3\n" +
                "9 3 1 3\n" +
                "4 3 3 3\n" +
                "1 3 0 0\n" +
                "5 0 2 3\n" +
                "14 1 1 0\n" +
                "7 2 1 0\n" +
                "4 0 1 0\n" +
                "4 0 2 0\n" +
                "1 0 3 3\n" +
                "5 3 1 1\n" +
                "4 1 0 3\n" +
                "9 3 3 3\n" +
                "4 0 0 0\n" +
                "9 0 2 0\n" +
                "4 3 0 2\n" +
                "9 2 3 2\n" +
                "14 2 3 2\n" +
                "4 2 1 2\n" +
                "1 1 2 1\n" +
                "5 1 0 3\n" +
                "14 0 0 2\n" +
                "14 3 0 0\n" +
                "14 0 1 1\n" +
                "6 2 0 0\n" +
                "4 0 1 0\n" +
                "1 0 3 3\n" +
                "5 3 1 2\n" +
                "14 1 3 1\n" +
                "14 2 0 3\n" +
                "4 2 0 0\n" +
                "9 0 3 0\n" +
                "0 1 3 1\n" +
                "4 1 2 1\n" +
                "1 2 1 2\n" +
                "5 2 3 0\n" +
                "14 3 3 2\n" +
                "14 1 3 3\n" +
                "14 1 3 1\n" +
                "1 1 3 3\n" +
                "4 3 2 3\n" +
                "1 0 3 0\n" +
                "5 0 0 1\n" +
                "14 3 2 3\n" +
                "14 3 0 0\n" +
                "13 3 2 3\n" +
                "4 3 2 3\n" +
                "1 1 3 1\n" +
                "5 1 0 3\n" +
                "14 2 0 2\n" +
                "14 1 1 0\n" +
                "14 1 3 1\n" +
                "5 0 2 1\n" +
                "4 1 2 1\n" +
                "1 3 1 3\n" +
                "5 3 3 1\n" +
                "14 3 0 2\n" +
                "14 1 3 3\n" +
                "14 3 0 0\n" +
                "1 3 3 2\n" +
                "4 2 1 2\n" +
                "4 2 1 2\n" +
                "1 2 1 1\n" +
                "14 3 2 2\n" +
                "14 2 1 0\n" +
                "6 0 2 0\n" +
                "4 0 1 0\n" +
                "1 1 0 1\n" +
                "14 1 3 0\n" +
                "14 1 1 2\n" +
                "14 3 2 3\n" +
                "14 2 3 3\n" +
                "4 3 2 3\n" +
                "1 3 1 1\n" +
                "5 1 1 2\n" +
                "4 2 0 0\n" +
                "9 0 2 0\n" +
                "4 3 0 1\n" +
                "9 1 1 1\n" +
                "14 0 2 3\n" +
                "15 0 3 3\n" +
                "4 3 1 3\n" +
                "1 3 2 2\n" +
                "5 2 1 1\n" +
                "14 0 1 2\n" +
                "4 2 0 3\n" +
                "9 3 1 3\n" +
                "11 0 3 3\n" +
                "4 3 1 3\n" +
                "1 3 1 1\n" +
                "14 0 2 3\n" +
                "14 2 0 2\n" +
                "15 0 3 0\n" +
                "4 0 3 0\n" +
                "1 0 1 1\n" +
                "5 1 3 2\n" +
                "14 2 1 3\n" +
                "4 0 0 1\n" +
                "9 1 3 1\n" +
                "14 2 0 0\n" +
                "8 0 3 0\n" +
                "4 0 3 0\n" +
                "1 0 2 2\n" +
                "5 2 1 3\n" +
                "14 3 2 0\n" +
                "14 0 3 2\n" +
                "14 0 3 1\n" +
                "6 2 0 2\n" +
                "4 2 1 2\n" +
                "1 3 2 3\n" +
                "5 3 3 0\n" +
                "4 0 0 2\n" +
                "9 2 2 2\n" +
                "4 2 0 3\n" +
                "9 3 3 3\n" +
                "14 1 2 2\n" +
                "4 2 1 2\n" +
                "1 0 2 0\n" +
                "14 1 2 2\n" +
                "14 1 0 3\n" +
                "14 3 1 1\n" +
                "1 3 3 1\n" +
                "4 1 2 1\n" +
                "1 0 1 0\n" +
                "5 0 3 2\n" +
                "14 1 0 0\n" +
                "14 0 3 1\n" +
                "1 0 0 3\n" +
                "4 3 2 3\n" +
                "1 3 2 2\n" +
                "5 2 0 1\n" +
                "14 2 3 2\n" +
                "14 1 3 3\n" +
                "5 0 2 2\n" +
                "4 2 3 2\n" +
                "1 2 1 1\n" +
                "5 1 0 2\n" +
                "14 2 1 0\n" +
                "14 0 3 1\n" +
                "9 3 1 3\n" +
                "4 3 1 3\n" +
                "4 3 1 3\n" +
                "1 3 2 2\n" +
                "5 2 2 3\n" +
                "14 2 3 1\n" +
                "14 3 0 2\n" +
                "14 3 1 0\n" +
                "2 0 1 2\n" +
                "4 2 1 2\n" +
                "1 2 3 3\n" +
                "5 3 3 2\n" +
                "14 1 2 1\n" +
                "14 2 2 0\n" +
                "14 1 2 3\n" +
                "11 0 3 3\n" +
                "4 3 2 3\n" +
                "1 3 2 2\n" +
                "5 2 3 1\n" +
                "14 1 1 0\n" +
                "14 2 2 2\n" +
                "14 2 2 3\n" +
                "5 0 2 0\n" +
                "4 0 3 0\n" +
                "4 0 2 0\n" +
                "1 1 0 1\n" +
                "14 3 1 3\n" +
                "14 3 2 0\n" +
                "7 2 0 2\n" +
                "4 2 2 2\n" +
                "1 2 1 1\n" +
                "5 1 3 2\n" +
                "14 2 2 0\n" +
                "14 1 1 3\n" +
                "14 2 2 1\n" +
                "11 0 3 1\n" +
                "4 1 1 1\n" +
                "1 1 2 2\n" +
                "5 2 1 3\n" +
                "14 1 2 1\n" +
                "4 0 0 0\n" +
                "9 0 1 0\n" +
                "14 2 1 2\n" +
                "5 0 2 0\n" +
                "4 0 2 0\n" +
                "1 0 3 3\n" +
                "5 3 1 1\n" +
                "4 1 0 0\n" +
                "9 0 1 0\n" +
                "14 2 3 3\n" +
                "4 0 0 2\n" +
                "9 2 1 2\n" +
                "0 0 3 0\n" +
                "4 0 1 0\n" +
                "4 0 2 0\n" +
                "1 1 0 1\n" +
                "14 0 2 0\n" +
                "14 2 0 2\n" +
                "14 3 2 2\n" +
                "4 2 1 2\n" +
                "1 2 1 1\n" +
                "5 1 3 2\n" +
                "14 1 0 1\n" +
                "14 2 2 0\n" +
                "15 0 3 0\n" +
                "4 0 1 0\n" +
                "1 0 2 2\n" +
                "5 2 3 3\n" +
                "4 3 0 1\n" +
                "9 1 3 1\n" +
                "14 2 0 2\n" +
                "4 1 0 0\n" +
                "9 0 0 0\n" +
                "7 2 1 0\n" +
                "4 0 2 0\n" +
                "1 3 0 3\n" +
                "5 3 1 0\n" +
                "4 2 0 2\n" +
                "9 2 3 2\n" +
                "14 0 0 3\n" +
                "14 1 0 1\n" +
                "3 3 2 3\n" +
                "4 3 2 3\n" +
                "1 0 3 0\n" +
                "14 3 2 1\n" +
                "14 1 0 2\n" +
                "14 1 0 3\n" +
                "1 3 3 2\n" +
                "4 2 2 2\n" +
                "4 2 2 2\n" +
                "1 0 2 0\n" +
                "5 0 2 3\n" +
                "4 0 0 0\n" +
                "9 0 1 0\n" +
                "14 2 2 1\n" +
                "14 3 3 2\n" +
                "12 1 2 2\n" +
                "4 2 2 2\n" +
                "1 2 3 3\n" +
                "5 3 1 2\n" +
                "14 2 1 3\n" +
                "14 1 0 1\n" +
                "0 1 3 1\n" +
                "4 1 1 1\n" +
                "1 1 2 2\n" +
                "5 2 0 1\n" +
                "14 2 1 2\n" +
                "14 0 3 3\n" +
                "14 3 0 0\n" +
                "10 3 2 0\n" +
                "4 0 3 0\n" +
                "1 1 0 1\n" +
                "5 1 3 0\n" +
                "4 2 0 2\n" +
                "9 2 3 2\n" +
                "4 1 0 1\n" +
                "9 1 3 1\n" +
                "3 3 2 2\n" +
                "4 2 2 2\n" +
                "4 2 3 2\n" +
                "1 0 2 0\n" +
                "5 0 3 3\n" +
                "14 1 0 1\n" +
                "4 0 0 0\n" +
                "9 0 0 0\n" +
                "14 3 3 2\n" +
                "4 1 2 0\n" +
                "4 0 3 0\n" +
                "1 0 3 3\n" +
                "4 0 0 1\n" +
                "9 1 2 1\n" +
                "14 1 0 0\n" +
                "14 2 1 2\n" +
                "5 0 2 0\n" +
                "4 0 1 0\n" +
                "1 3 0 3\n" +
                "5 3 1 0\n" +
                "14 1 3 3\n" +
                "14 3 1 1\n" +
                "7 2 1 1\n" +
                "4 1 2 1\n" +
                "4 1 3 1\n" +
                "1 0 1 0\n" +
                "5 0 3 3\n" +
                "14 3 0 0\n" +
                "14 1 1 1\n" +
                "4 2 0 2\n" +
                "9 2 3 2\n" +
                "13 0 2 1\n" +
                "4 1 2 1\n" +
                "4 1 1 1\n" +
                "1 1 3 3\n" +
                "5 3 3 2\n" +
                "14 1 1 1\n" +
                "14 1 1 3\n" +
                "14 1 2 0\n" +
                "14 3 1 0\n" +
                "4 0 2 0\n" +
                "1 2 0 2\n" +
                "14 0 3 1\n" +
                "14 2 1 0\n" +
                "4 2 0 3\n" +
                "9 3 2 3\n" +
                "8 0 3 1\n" +
                "4 1 1 1\n" +
                "1 1 2 2\n" +
                "5 2 1 1\n" +
                "14 1 3 3\n" +
                "14 3 0 2\n" +
                "14 3 2 0\n" +
                "4 3 2 0\n" +
                "4 0 2 0\n" +
                "1 1 0 1\n" +
                "14 2 1 2\n" +
                "4 1 0 0\n" +
                "9 0 0 0\n" +
                "14 0 2 3\n" +
                "15 2 3 2\n" +
                "4 2 1 2\n" +
                "4 2 3 2\n" +
                "1 2 1 1\n" +
                "5 1 1 0\n" +
                "14 3 1 1\n" +
                "14 2 3 2\n" +
                "10 3 2 2\n" +
                "4 2 3 2\n" +
                "1 0 2 0\n" +
                "5 0 0 2\n" +
                "4 2 0 0\n" +
                "9 0 2 0\n" +
                "15 0 3 1\n" +
                "4 1 3 1\n" +
                "4 1 1 1\n" +
                "1 2 1 2\n" +
                "5 2 2 3\n" +
                "14 1 2 0\n" +
                "14 2 3 2\n" +
                "14 0 0 1\n" +
                "5 0 2 1\n" +
                "4 1 1 1\n" +
                "4 1 2 1\n" +
                "1 3 1 3\n" +
                "5 3 0 1\n" +
                "14 1 2 3\n" +
                "5 0 2 3\n" +
                "4 3 2 3\n" +
                "1 1 3 1\n" +
                "14 3 0 2\n" +
                "14 2 1 0\n" +
                "14 0 0 3\n" +
                "6 0 2 0\n" +
                "4 0 3 0\n" +
                "1 0 1 1\n" +
                "14 3 2 0\n" +
                "14 2 3 3\n" +
                "14 1 2 2\n" +
                "13 0 2 0\n" +
                "4 0 3 0\n" +
                "1 1 0 1\n" +
                "5 1 3 3\n" +
                "14 2 2 1\n" +
                "14 3 1 0\n" +
                "12 1 0 2\n" +
                "4 2 2 2\n" +
                "1 3 2 3\n" +
                "5 3 2 1\n" +
                "4 1 0 2\n" +
                "9 2 1 2\n" +
                "4 1 0 3\n" +
                "9 3 1 3\n" +
                "14 1 3 0\n" +
                "1 0 3 0\n" +
                "4 0 1 0\n" +
                "1 1 0 1\n" +
                "5 1 1 3\n" +
                "14 3 1 2\n" +
                "14 2 1 0\n" +
                "14 0 3 1\n" +
                "6 0 2 0\n" +
                "4 0 1 0\n" +
                "1 3 0 3\n" +
                "5 3 0 1\n" +
                "14 2 1 0\n" +
                "14 3 3 3\n" +
                "6 0 2 0\n" +
                "4 0 1 0\n" +
                "1 1 0 1\n" +
                "5 1 2 0\n" +
                "4 0 0 1\n" +
                "9 1 1 1\n" +
                "14 2 1 2\n" +
                "14 2 1 3\n" +
                "15 2 3 1\n" +
                "4 1 3 1\n" +
                "1 1 0 0\n" +
                "5 0 1 2\n" +
                "14 1 3 1\n" +
                "14 2 1 0\n" +
                "14 1 0 3\n" +
                "11 0 3 1\n" +
                "4 1 3 1\n" +
                "4 1 1 1\n" +
                "1 2 1 2\n" +
                "5 2 0 0\n" +
                "14 2 0 2\n" +
                "14 2 1 1\n" +
                "14 0 0 3\n" +
                "15 1 3 3\n" +
                "4 3 3 3\n" +
                "1 3 0 0\n" +
                "5 0 1 1\n" +
                "4 1 0 0\n" +
                "9 0 3 0\n" +
                "14 2 1 3\n" +
                "14 0 1 2\n" +
                "13 0 2 0\n" +
                "4 0 1 0\n" +
                "1 1 0 1\n" +
                "5 1 3 0\n" +
                "14 2 0 2\n" +
                "14 2 2 1\n" +
                "4 0 0 3\n" +
                "9 3 0 3\n" +
                "14 3 2 2\n" +
                "4 2 1 2\n" +
                "1 0 2 0\n" +
                "5 0 3 1\n" +
                "14 2 1 3\n" +
                "14 2 2 0\n" +
                "4 0 0 2\n" +
                "9 2 0 2\n" +
                "8 0 3 3\n" +
                "4 3 1 3\n" +
                "1 3 1 1\n" +
                "14 2 2 2\n" +
                "4 2 0 3\n" +
                "9 3 2 3\n" +
                "14 1 0 0\n" +
                "1 0 0 0\n" +
                "4 0 3 0\n" +
                "1 1 0 1\n" +
                "5 1 0 0\n" +
                "14 1 0 2\n" +
                "4 1 0 1\n" +
                "9 1 0 1\n" +
                "4 3 0 3\n" +
                "9 3 0 3\n" +
                "14 3 2 1\n" +
                "4 1 3 1\n" +
                "1 0 1 0\n" +
                "5 0 1 3\n" +
                "14 0 2 2\n" +
                "14 3 0 1\n" +
                "14 3 2 0\n" +
                "6 2 0 2\n" +
                "4 2 2 2\n" +
                "1 3 2 3\n" +
                "5 3 3 1\n" +
                "14 1 3 3\n" +
                "14 0 2 2\n" +
                "6 2 0 2\n" +
                "4 2 3 2\n" +
                "1 2 1 1\n" +
                "5 1 1 2\n" +
                "14 1 0 0\n" +
                "4 1 0 3\n" +
                "9 3 0 3\n" +
                "14 0 1 1\n" +
                "9 0 1 3\n" +
                "4 3 2 3\n" +
                "4 3 1 3\n" +
                "1 2 3 2\n" +
                "14 2 3 3\n" +
                "14 2 2 1\n" +
                "14 2 1 0\n" +
                "8 0 3 1\n" +
                "4 1 2 1\n" +
                "1 1 2 2\n" +
                "14 2 3 1\n" +
                "14 1 1 0\n" +
                "0 0 3 1\n" +
                "4 1 2 1\n" +
                "1 1 2 2\n" +
                "5 2 0 1\n" +
                "14 1 1 3\n" +
                "14 2 3 0\n" +
                "14 0 0 2\n" +
                "11 0 3 2\n" +
                "4 2 2 2\n" +
                "4 2 1 2\n" +
                "1 1 2 1\n" +
                "5 1 3 3\n" +
                "4 3 0 2\n" +
                "9 2 2 2\n" +
                "14 2 3 1\n" +
                "14 3 2 0\n" +
                "12 1 0 1\n" +
                "4 1 3 1\n" +
                "1 3 1 3\n" +
                "14 3 0 2\n" +
                "4 2 0 1\n" +
                "9 1 1 1\n" +
                "14 2 3 0\n" +
                "0 1 0 2\n" +
                "4 2 3 2\n" +
                "1 3 2 3\n" +
                "14 3 1 0\n" +
                "14 2 3 1\n" +
                "14 1 2 2\n" +
                "13 0 2 2\n" +
                "4 2 3 2\n" +
                "1 3 2 3\n" +
                "5 3 1 1\n" +
                "14 1 3 3\n" +
                "4 3 0 0\n" +
                "9 0 2 0\n" +
                "14 1 3 2\n" +
                "11 0 3 0\n" +
                "4 0 2 0\n" +
                "1 0 1 1\n" +
                "5 1 2 2\n" +
                "14 0 1 3\n" +
                "14 2 2 0\n" +
                "14 1 3 1\n" +
                "0 1 0 0\n" +
                "4 0 1 0\n" +
                "1 0 2 2\n" +
                "14 1 2 3\n" +
                "14 2 1 0\n" +
                "0 1 0 0\n" +
                "4 0 2 0\n" +
                "1 2 0 2\n" +
                "4 1 0 1\n" +
                "9 1 3 1\n" +
                "14 1 0 0\n" +
                "1 3 3 0\n" +
                "4 0 3 0\n" +
                "1 2 0 2\n" +
                "5 2 3 1\n" +
                "4 2 0 3\n" +
                "9 3 2 3\n" +
                "14 1 3 0\n" +
                "4 1 0 2\n" +
                "9 2 2 2\n" +
                "5 0 2 3\n" +
                "4 3 3 3\n" +
                "4 3 2 3\n" +
                "1 1 3 1\n" +
                "4 0 0 0\n" +
                "9 0 3 0\n" +
                "14 0 1 3\n" +
                "7 2 0 3\n" +
                "4 3 2 3\n" +
                "1 1 3 1\n" +
                "5 1 1 3\n" +
                "14 1 1 1\n" +
                "7 2 0 2\n" +
                "4 2 2 2\n" +
                "1 3 2 3\n" +
                "14 2 0 0\n" +
                "4 0 0 2\n" +
                "9 2 3 2\n" +
                "14 2 1 1\n" +
                "12 1 2 2\n" +
                "4 2 2 2\n" +
                "1 3 2 3\n" +
                "5 3 1 2\n" +
                "14 1 1 0\n" +
                "14 1 2 1\n" +
                "14 1 1 3\n" +
                "1 1 0 1\n" +
                "4 1 1 1\n" +
                "1 2 1 2\n" +
                "5 2 3 1\n" +
                "14 3 3 0\n" +
                "14 0 1 2\n" +
                "14 3 3 3\n" +
                "6 2 0 0\n" +
                "4 0 3 0\n" +
                "1 1 0 1\n" +
                "5 1 0 0\n" +
                "14 3 0 1\n" +
                "4 3 0 2\n" +
                "9 2 2 2\n" +
                "4 1 0 3\n" +
                "9 3 0 3\n" +
                "10 3 2 3\n" +
                "4 3 2 3\n" +
                "1 0 3 0\n" +
                "5 0 1 1\n" +
                "4 2 0 3\n" +
                "9 3 3 3\n" +
                "4 0 0 0\n" +
                "9 0 1 0\n" +
                "5 0 2 2\n" +
                "4 2 2 2\n" +
                "4 2 1 2\n" +
                "1 2 1 1\n" +
                "5 1 0 3\n" +
                "14 2 0 2\n" +
                "4 1 0 1\n" +
                "9 1 0 1\n" +
                "9 0 1 1\n" +
                "4 1 1 1\n" +
                "1 1 3 3\n" +
                "5 3 1 0\n" +
                "14 0 0 3\n" +
                "14 1 0 1\n" +
                "10 3 2 2\n" +
                "4 2 2 2\n" +
                "4 2 3 2\n" +
                "1 0 2 0\n" +
                "5 0 0 1\n" +
                "14 2 2 0\n" +
                "14 0 1 2\n" +
                "14 2 3 3\n" +
                "8 0 3 0\n" +
                "4 0 1 0\n" +
                "4 0 1 0\n" +
                "1 0 1 1\n" +
                "4 2 0 2\n" +
                "9 2 2 2\n" +
                "4 2 0 0\n" +
                "9 0 1 0\n" +
                "5 0 2 2\n" +
                "4 2 3 2\n" +
                "1 1 2 1\n" +
                "5 1 0 3\n" +
                "4 1 0 1\n" +
                "9 1 3 1\n" +
                "14 2 2 2\n" +
                "14 0 1 0\n" +
                "7 2 1 2\n" +
                "4 2 1 2\n" +
                "1 2 3 3\n" +
                "5 3 3 1\n" +
                "4 2 0 0\n" +
                "9 0 2 0\n" +
                "14 3 2 2\n" +
                "14 2 1 3\n" +
                "8 0 3 2\n" +
                "4 2 2 2\n" +
                "4 2 3 2\n" +
                "1 2 1 1\n" +
                "5 1 0 0\n" +
                "14 0 2 3\n" +
                "4 2 0 1\n" +
                "9 1 2 1\n" +
                "14 1 3 2\n" +
                "14 3 2 3\n" +
                "4 3 2 3\n" +
                "4 3 1 3\n" +
                "1 0 3 0\n" +
                "14 3 2 3\n" +
                "4 0 0 1\n" +
                "9 1 3 1\n" +
                "14 2 2 2\n" +
                "7 2 1 1\n" +
                "4 1 1 1\n" +
                "1 0 1 0\n" +
                "5 0 3 3\n" +
                "14 1 0 0\n" +
                "14 2 0 1\n" +
                "5 0 2 2\n" +
                "4 2 3 2\n" +
                "1 3 2 3\n" +
                "5 3 0 0\n" +
                "14 0 0 1\n" +
                "14 2 3 2\n" +
                "14 2 3 3\n" +
                "15 2 3 2\n" +
                "4 2 3 2\n" +
                "1 2 0 0\n" +
                "5 0 2 2\n" +
                "14 2 2 1\n" +
                "14 0 0 0\n" +
                "14 3 3 3\n" +
                "2 3 1 1\n" +
                "4 1 1 1\n" +
                "4 1 3 1\n" +
                "1 2 1 2\n" +
                "5 2 2 3\n" +
                "4 0 0 0\n" +
                "9 0 1 0\n" +
                "14 0 0 2\n" +
                "4 1 0 1\n" +
                "9 1 1 1\n" +
                "1 1 0 2\n" +
                "4 2 3 2\n" +
                "1 3 2 3\n" +
                "5 3 2 1\n" +
                "14 3 2 3\n" +
                "14 3 1 2\n" +
                "4 0 2 0\n" +
                "4 0 3 0\n" +
                "1 0 1 1\n" +
                "5 1 3 2\n" +
                "14 3 3 0\n" +
                "14 3 2 1\n" +
                "4 3 0 3\n" +
                "9 3 1 3\n" +
                "1 3 3 1\n" +
                "4 1 3 1\n" +
                "1 2 1 2\n" +
                "5 2 2 0\n" +
                "4 3 0 3\n" +
                "9 3 0 3\n" +
                "14 2 1 2\n" +
                "14 0 3 1\n" +
                "15 2 3 3\n" +
                "4 3 2 3\n" +
                "1 0 3 0\n" +
                "5 0 1 1\n" +
                "14 2 3 0\n" +
                "14 1 3 2\n" +
                "14 2 3 3\n" +
                "8 0 3 3\n" +
                "4 3 3 3\n" +
                "1 1 3 1\n" +
                "5 1 1 3\n" +
                "14 2 3 2\n" +
                "14 2 1 1\n" +
                "14 3 3 0\n" +
                "2 0 1 0\n" +
                "4 0 1 0\n" +
                "1 3 0 3\n" +
                "5 3 2 1\n" +
                "14 1 0 0\n" +
                "14 2 0 3\n" +
                "14 0 1 2\n" +
                "3 2 3 3\n" +
                "4 3 2 3\n" +
                "1 3 1 1\n" +
                "4 1 0 3\n" +
                "9 3 3 3\n" +
                "14 1 0 2\n" +
                "4 2 0 0\n" +
                "9 0 2 0\n" +
                "13 3 2 3\n" +
                "4 3 3 3\n" +
                "1 1 3 1\n" +
                "5 1 0 3\n" +
                "14 3 0 0\n" +
                "14 1 1 1\n" +
                "14 0 2 2\n" +
                "13 0 2 0\n" +
                "4 0 1 0\n" +
                "1 3 0 3\n" +
                "14 1 2 0\n" +
                "4 1 2 1\n" +
                "4 1 2 1\n" +
                "4 1 1 1\n" +
                "1 1 3 3\n" +
                "5 3 3 1\n" +
                "14 3 2 0\n" +
                "14 2 1 3\n" +
                "2 0 3 3\n" +
                "4 3 1 3\n" +
                "4 3 3 3\n" +
                "1 3 1 1\n" +
                "14 3 1 2\n" +
                "14 2 2 0\n" +
                "14 1 0 3\n" +
                "11 0 3 2\n" +
                "4 2 2 2\n" +
                "1 2 1 1\n" +
                "5 1 0 2\n" +
                "14 2 0 1\n" +
                "14 1 3 0\n" +
                "14 2 0 3\n" +
                "0 0 3 3\n" +
                "4 3 2 3\n" +
                "4 3 2 3\n" +
                "1 2 3 2\n" +
                "14 2 3 0\n" +
                "14 1 1 3\n" +
                "14 0 3 1\n" +
                "11 0 3 1\n" +
                "4 1 3 1\n" +
                "1 1 2 2\n" +
                "4 1 0 1\n" +
                "9 1 0 1\n" +
                "4 0 0 3\n" +
                "9 3 3 3\n" +
                "2 3 0 0\n" +
                "4 0 3 0\n" +
                "1 2 0 2\n" +
                "5 2 2 3\n" +
                "14 0 2 2\n" +
                "14 3 0 1\n" +
                "14 2 3 0\n" +
                "7 0 1 1\n" +
                "4 1 3 1\n" +
                "1 3 1 3\n" +
                "5 3 2 0";
    }

}
