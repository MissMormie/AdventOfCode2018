package days;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day01 {

    public static void main(String[] args) {
        String input = getInput();
        String[] pieces = input.split("\n");
        int currentFrequency = 0;
        List<Integer> listOfFrequencies = new LinkedList<>();
        // Get ending frequency.
        for(String piece : pieces) {
            int number = Integer.valueOf(piece);
            listOfFrequencies.add(number);
            currentFrequency += number;
        }

        currentFrequency = 0;
        // get first repeating frequency
        boolean found = false;
        Set<Integer> usedFrequencies = new HashSet();
        while(found == false) {
            for(int frequency : listOfFrequencies) {
                currentFrequency += frequency;
                if(usedFrequencies.contains(currentFrequency)) {
                    System.out.println("first double: " +currentFrequency);
                    found = true;
                    break;
                }
                usedFrequencies.add(currentFrequency);
            }
        }

        System.out.println(currentFrequency);

    }



    public static String getInput() {
        return "+13\n" +
                "-18\n" +
                "+13\n" +
                "+10\n" +
                "+12\n" +
                "-4\n" +
                "+17\n" +
                "-16\n" +
                "-6\n" +
                "+10\n" +
                "+4\n" +
                "-1\n" +
                "+7\n" +
                "+13\n" +
                "-1\n" +
                "+16\n" +
                "-7\n" +
                "-6\n" +
                "+18\n" +
                "-6\n" +
                "+13\n" +
                "-8\n" +
                "+2\n" +
                "+16\n" +
                "-5\n" +
                "-14\n" +
                "-5\n" +
                "-2\n" +
                "+11\n" +
                "+17\n" +
                "+17\n" +
                "+9\n" +
                "-14\n" +
                "-17\n" +
                "+7\n" +
                "+16\n" +
                "-15\n" +
                "-13\n" +
                "-13\n" +
                "-11\n" +
                "+1\n" +
                "+6\n" +
                "-5\n" +
                "-4\n" +
                "-19\n" +
                "+9\n" +
                "-7\n" +
                "-18\n" +
                "+7\n" +
                "+8\n" +
                "-17\n" +
                "-6\n" +
                "-12\n" +
                "+9\n" +
                "-12\n" +
                "-13\n" +
                "+5\n" +
                "-12\n" +
                "+8\n" +
                "+9\n" +
                "+5\n" +
                "+11\n" +
                "-19\n" +
                "+11\n" +
                "-16\n" +
                "+15\n" +
                "-18\n" +
                "-12\n" +
                "+19\n" +
                "-18\n" +
                "-7\n" +
                "+5\n" +
                "-9\n" +
                "+13\n" +
                "+13\n" +
                "+8\n" +
                "-16\n" +
                "+3\n" +
                "-16\n" +
                "+7\n" +
                "-9\n" +
                "-12\n" +
                "-18\n" +
                "-12\n" +
                "+14\n" +
                "+18\n" +
                "-11\n" +
                "+2\n" +
                "+11\n" +
                "-18\n" +
                "-3\n" +
                "+5\n" +
                "-8\n" +
                "+18\n" +
                "+12\n" +
                "+18\n" +
                "+8\n" +
                "+4\n" +
                "+25\n" +
                "-13\n" +
                "+11\n" +
                "+7\n" +
                "-8\n" +
                "+16\n" +
                "+14\n" +
                "-7\n" +
                "-16\n" +
                "+22\n" +
                "-12\n" +
                "-8\n" +
                "+19\n" +
                "+6\n" +
                "+5\n" +
                "+8\n" +
                "+1\n" +
                "+26\n" +
                "+17\n" +
                "+14\n" +
                "+13\n" +
                "+18\n" +
                "+5\n" +
                "-17\n" +
                "-1\n" +
                "+6\n" +
                "-1\n" +
                "-3\n" +
                "-18\n" +
                "-6\n" +
                "+15\n" +
                "+16\n" +
                "+7\n" +
                "+18\n" +
                "+8\n" +
                "+13\n" +
                "+3\n" +
                "-18\n" +
                "-9\n" +
                "-17\n" +
                "+13\n" +
                "-5\n" +
                "+2\n" +
                "-5\n" +
                "+11\n" +
                "-9\n" +
                "+4\n" +
                "+13\n" +
                "+8\n" +
                "+2\n" +
                "-11\n" +
                "-11\n" +
                "-22\n" +
                "+13\n" +
                "-19\n" +
                "-19\n" +
                "+18\n" +
                "+10\n" +
                "+3\n" +
                "+10\n" +
                "+21\n" +
                "-13\n" +
                "+16\n" +
                "+1\n" +
                "+15\n" +
                "+17\n" +
                "+17\n" +
                "-5\n" +
                "+16\n" +
                "+17\n" +
                "+10\n" +
                "+4\n" +
                "+1\n" +
                "-3\n" +
                "-14\n" +
                "+18\n" +
                "-17\n" +
                "-6\n" +
                "-9\n" +
                "+17\n" +
                "-6\n" +
                "+1\n" +
                "-17\n" +
                "+7\n" +
                "-6\n" +
                "+2\n" +
                "+3\n" +
                "-11\n" +
                "-16\n" +
                "-17\n" +
                "-11\n" +
                "-1\n" +
                "-2\n" +
                "-17\n" +
                "+22\n" +
                "+3\n" +
                "+12\n" +
                "-13\n" +
                "+8\n" +
                "+20\n" +
                "+8\n" +
                "+6\n" +
                "-1\n" +
                "-4\n" +
                "+19\n" +
                "+17\n" +
                "+13\n" +
                "+8\n" +
                "+4\n" +
                "+6\n" +
                "+20\n" +
                "+9\n" +
                "+2\n" +
                "-3\n" +
                "+18\n" +
                "+6\n" +
                "-18\n" +
                "-8\n" +
                "+13\n" +
                "-4\n" +
                "+3\n" +
                "+13\n" +
                "+15\n" +
                "-7\n" +
                "+11\n" +
                "-15\n" +
                "+7\n" +
                "-17\n" +
                "+9\n" +
                "+18\n" +
                "+15\n" +
                "-12\n" +
                "+11\n" +
                "+6\n" +
                "+7\n" +
                "-17\n" +
                "+18\n" +
                "-4\n" +
                "-1\n" +
                "-15\n" +
                "-15\n" +
                "+4\n" +
                "-12\n" +
                "+11\n" +
                "+15\n" +
                "-12\n" +
                "-13\n" +
                "-17\n" +
                "+1\n" +
                "+2\n" +
                "+3\n" +
                "-17\n" +
                "+10\n" +
                "-7\n" +
                "-6\n" +
                "+10\n" +
                "-20\n" +
                "-11\n" +
                "-15\n" +
                "+8\n" +
                "-2\n" +
                "-12\n" +
                "+18\n" +
                "-16\n" +
                "-8\n" +
                "+4\n" +
                "+13\n" +
                "+20\n" +
                "-15\n" +
                "+11\n" +
                "+12\n" +
                "-16\n" +
                "+15\n" +
                "-12\n" +
                "-13\n" +
                "+12\n" +
                "-17\n" +
                "+16\n" +
                "+23\n" +
                "-18\n" +
                "-12\n" +
                "+2\n" +
                "+11\n" +
                "+3\n" +
                "-13\n" +
                "+17\n" +
                "+21\n" +
                "-24\n" +
                "+6\n" +
                "+11\n" +
                "-23\n" +
                "-18\n" +
                "-28\n" +
                "-1\n" +
                "-2\n" +
                "+16\n" +
                "+4\n" +
                "-13\n" +
                "+18\n" +
                "-16\n" +
                "-3\n" +
                "+5\n" +
                "+17\n" +
                "-4\n" +
                "-7\n" +
                "-13\n" +
                "-9\n" +
                "-16\n" +
                "-8\n" +
                "-7\n" +
                "+22\n" +
                "-19\n" +
                "-10\n" +
                "+6\n" +
                "+3\n" +
                "+23\n" +
                "-4\n" +
                "+5\n" +
                "-15\n" +
                "-17\n" +
                "-8\n" +
                "-8\n" +
                "+6\n" +
                "+6\n" +
                "-2\n" +
                "-15\n" +
                "+12\n" +
                "+6\n" +
                "-11\n" +
                "-9\n" +
                "-9\n" +
                "+15\n" +
                "-26\n" +
                "-6\n" +
                "-18\n" +
                "-7\n" +
                "-19\n" +
                "-2\n" +
                "+1\n" +
                "+3\n" +
                "+12\n" +
                "-11\n" +
                "-23\n" +
                "-4\n" +
                "-15\n" +
                "+9\n" +
                "-14\n" +
                "-5\n" +
                "-28\n" +
                "-9\n" +
                "+16\n" +
                "+22\n" +
                "+7\n" +
                "-16\n" +
                "+7\n" +
                "-1\n" +
                "+17\n" +
                "-12\n" +
                "+18\n" +
                "+12\n" +
                "-10\n" +
                "+4\n" +
                "-5\n" +
                "+14\n" +
                "-12\n" +
                "-6\n" +
                "+25\n" +
                "+13\n" +
                "-10\n" +
                "-9\n" +
                "+13\n" +
                "+21\n" +
                "-17\n" +
                "-9\n" +
                "-4\n" +
                "+18\n" +
                "-24\n" +
                "-1\n" +
                "-14\n" +
                "+10\n" +
                "+42\n" +
                "+24\n" +
                "-7\n" +
                "-6\n" +
                "+12\n" +
                "+8\n" +
                "+4\n" +
                "+24\n" +
                "-4\n" +
                "+18\n" +
                "+1\n" +
                "-24\n" +
                "+21\n" +
                "+17\n" +
                "+16\n" +
                "+51\n" +
                "-21\n" +
                "+70\n" +
                "-8\n" +
                "+15\n" +
                "+16\n" +
                "+2\n" +
                "-3\n" +
                "+10\n" +
                "+19\n" +
                "+15\n" +
                "+8\n" +
                "+21\n" +
                "+11\n" +
                "+10\n" +
                "-2\n" +
                "-7\n" +
                "-7\n" +
                "+5\n" +
                "-19\n" +
                "+7\n" +
                "-16\n" +
                "-1\n" +
                "+2\n" +
                "-14\n" +
                "-4\n" +
                "-14\n" +
                "+19\n" +
                "-7\n" +
                "-11\n" +
                "+7\n" +
                "+5\n" +
                "-10\n" +
                "-15\n" +
                "+4\n" +
                "-10\n" +
                "-11\n" +
                "-13\n" +
                "-11\n" +
                "+12\n" +
                "+13\n" +
                "-10\n" +
                "+3\n" +
                "+18\n" +
                "-9\n" +
                "+1\n" +
                "+12\n" +
                "+12\n" +
                "+18\n" +
                "-6\n" +
                "+4\n" +
                "-5\n" +
                "-3\n" +
                "+18\n" +
                "+1\n" +
                "-4\n" +
                "-33\n" +
                "+17\n" +
                "-4\n" +
                "+7\n" +
                "-13\n" +
                "+52\n" +
                "-3\n" +
                "-3\n" +
                "-18\n" +
                "-3\n" +
                "-5\n" +
                "+13\n" +
                "+9\n" +
                "+18\n" +
                "+23\n" +
                "-21\n" +
                "-15\n" +
                "-16\n" +
                "-23\n" +
                "-9\n" +
                "-4\n" +
                "-40\n" +
                "-3\n" +
                "+86\n" +
                "+18\n" +
                "-2\n" +
                "+27\n" +
                "-1\n" +
                "-12\n" +
                "+71\n" +
                "-8\n" +
                "+20\n" +
                "+37\n" +
                "-36\n" +
                "+43\n" +
                "-40\n" +
                "-69\n" +
                "-13\n" +
                "+35\n" +
                "+4\n" +
                "+6\n" +
                "-17\n" +
                "-178\n" +
                "-56\n" +
                "-14\n" +
                "-243\n" +
                "+3\n" +
                "+204\n" +
                "-22\n" +
                "+517\n" +
                "-71500\n" +
                "-244\n" +
                "-6\n" +
                "+19\n" +
                "-18\n" +
                "+1\n" +
                "+19\n" +
                "-23\n" +
                "-18\n" +
                "+10\n" +
                "+6\n" +
                "-14\n" +
                "+9\n" +
                "-7\n" +
                "+19\n" +
                "-11\n" +
                "-5\n" +
                "+23\n" +
                "+8\n" +
                "-6\n" +
                "-18\n" +
                "-20\n" +
                "-18\n" +
                "-7\n" +
                "+2\n" +
                "-11\n" +
                "+8\n" +
                "-18\n" +
                "+7\n" +
                "-14\n" +
                "+15\n" +
                "-19\n" +
                "-17\n" +
                "-10\n" +
                "-12\n" +
                "-6\n" +
                "+10\n" +
                "-6\n" +
                "+5\n" +
                "-7\n" +
                "+5\n" +
                "-11\n" +
                "-2\n" +
                "-15\n" +
                "+10\n" +
                "+1\n" +
                "-9\n" +
                "+18\n" +
                "+14\n" +
                "-10\n" +
                "-12\n" +
                "-13\n" +
                "+19\n" +
                "-15\n" +
                "-10\n" +
                "-5\n" +
                "-16\n" +
                "-12\n" +
                "+14\n" +
                "-15\n" +
                "-4\n" +
                "-9\n" +
                "-9\n" +
                "-11\n" +
                "-6\n" +
                "+2\n" +
                "+17\n" +
                "+11\n" +
                "-5\n" +
                "+19\n" +
                "+18\n" +
                "-19\n" +
                "+3\n" +
                "+3\n" +
                "+11\n" +
                "-3\n" +
                "+12\n" +
                "+7\n" +
                "+5\n" +
                "-10\n" +
                "-4\n" +
                "-6\n" +
                "-2\n" +
                "+7\n" +
                "-3\n" +
                "-7\n" +
                "+19\n" +
                "+2\n" +
                "+9\n" +
                "-8\n" +
                "+9\n" +
                "-15\n" +
                "-2\n" +
                "+18\n" +
                "-23\n" +
                "-23\n" +
                "-15\n" +
                "-6\n" +
                "+17\n" +
                "+17\n" +
                "-5\n" +
                "+14\n" +
                "-13\n" +
                "-18\n" +
                "-11\n" +
                "-10\n" +
                "-19\n" +
                "-5\n" +
                "-13\n" +
                "+12\n" +
                "-5\n" +
                "+16\n" +
                "+15\n" +
                "-6\n" +
                "-16\n" +
                "-8\n" +
                "-21\n" +
                "-2\n" +
                "+7\n" +
                "-3\n" +
                "-16\n" +
                "-6\n" +
                "+14\n" +
                "+7\n" +
                "+3\n" +
                "+5\n" +
                "-19\n" +
                "+6\n" +
                "+7\n" +
                "+11\n" +
                "-2\n" +
                "+4\n" +
                "-9\n" +
                "-16\n" +
                "-4\n" +
                "-15\n" +
                "-6\n" +
                "+7\n" +
                "-12\n" +
                "-8\n" +
                "-13\n" +
                "-8\n" +
                "+9\n" +
                "+3\n" +
                "+4\n" +
                "-6\n" +
                "+14\n" +
                "+13\n" +
                "-15\n" +
                "-18\n" +
                "-3\n" +
                "-2\n" +
                "+19\n" +
                "-1\n" +
                "+8\n" +
                "-14\n" +
                "+3\n" +
                "-17\n" +
                "-13\n" +
                "-2\n" +
                "+12\n" +
                "-2\n" +
                "+15\n" +
                "+13\n" +
                "+22\n" +
                "-3\n" +
                "+8\n" +
                "+16\n" +
                "-13\n" +
                "+15\n" +
                "-13\n" +
                "-6\n" +
                "-18\n" +
                "+10\n" +
                "-7\n" +
                "-5\n" +
                "+7\n" +
                "+12\n" +
                "-1\n" +
                "+5\n" +
                "+2\n" +
                "+10\n" +
                "+17\n" +
                "+22\n" +
                "-10\n" +
                "-5\n" +
                "+25\n" +
                "+5\n" +
                "-13\n" +
                "-10\n" +
                "-16\n" +
                "+19\n" +
                "-9\n" +
                "+5\n" +
                "+20\n" +
                "+10\n" +
                "-1\n" +
                "+9\n" +
                "-5\n" +
                "+23\n" +
                "-17\n" +
                "-14\n" +
                "-18\n" +
                "+9\n" +
                "-20\n" +
                "+14\n" +
                "+36\n" +
                "+16\n" +
                "+11\n" +
                "-24\n" +
                "-2\n" +
                "-3\n" +
                "-1\n" +
                "+20\n" +
                "-3\n" +
                "+8\n" +
                "+21\n" +
                "+11\n" +
                "+17\n" +
                "+14\n" +
                "+8\n" +
                "+3\n" +
                "+18\n" +
                "-15\n" +
                "+8\n" +
                "-16\n" +
                "+12\n" +
                "+8\n" +
                "+13\n" +
                "+16\n" +
                "-3\n" +
                "-16\n" +
                "+7\n" +
                "-15\n" +
                "+5\n" +
                "-2\n" +
                "+6\n" +
                "+10\n" +
                "-5\n" +
                "+2\n" +
                "-4\n" +
                "+18\n" +
                "+15\n" +
                "-3\n" +
                "-17\n" +
                "+4\n" +
                "+8\n" +
                "+15\n" +
                "-19\n" +
                "-11\n" +
                "+19\n" +
                "-1\n" +
                "+14\n" +
                "-19\n" +
                "+8\n" +
                "+3\n" +
                "+10\n" +
                "+16\n" +
                "-23\n" +
                "+2\n" +
                "-4\n" +
                "-9\n" +
                "+12\n" +
                "+18\n" +
                "-32\n" +
                "+8\n" +
                "-16\n" +
                "+25\n" +
                "+2\n" +
                "+4\n" +
                "-41\n" +
                "-8\n" +
                "-3\n" +
                "-19\n" +
                "+9\n" +
                "-2\n" +
                "+1\n" +
                "-9\n" +
                "+4\n" +
                "-17\n" +
                "-9\n" +
                "+11\n" +
                "+3\n" +
                "+20\n" +
                "-17\n" +
                "-9\n" +
                "+16\n" +
                "-18\n" +
                "-9\n" +
                "-12\n" +
                "+5\n" +
                "-3\n" +
                "+16\n" +
                "-23\n" +
                "+59\n" +
                "-23\n" +
                "+12\n" +
                "-16\n" +
                "-8\n" +
                "+15\n" +
                "+8\n" +
                "+25\n" +
                "-77\n" +
                "-48\n" +
                "+1\n" +
                "-26\n" +
                "+9\n" +
                "-17\n" +
                "-13\n" +
                "-29\n" +
                "-7\n" +
                "-3\n" +
                "-22\n" +
                "+18\n" +
                "-21\n" +
                "-26\n" +
                "-17\n" +
                "+2\n" +
                "+11\n" +
                "+11\n" +
                "+6\n" +
                "+11\n" +
                "+13\n" +
                "+13\n" +
                "+23\n" +
                "+24\n" +
                "+2\n" +
                "+17\n" +
                "-48\n" +
                "-38\n" +
                "-21\n" +
                "-10\n" +
                "+18\n" +
                "-27\n" +
                "-19\n" +
                "+6\n" +
                "+12\n" +
                "-14\n" +
                "-5\n" +
                "+2\n" +
                "+8\n" +
                "-14\n" +
                "-7\n" +
                "+2\n" +
                "-18\n" +
                "-10\n" +
                "-12\n" +
                "+13\n" +
                "+2\n" +
                "-18\n" +
                "+10\n" +
                "+3\n" +
                "+13\n" +
                "+6\n" +
                "-1\n" +
                "-4\n" +
                "-20\n" +
                "+4\n" +
                "-12\n" +
                "+9\n" +
                "-3\n" +
                "+1\n" +
                "+14\n" +
                "+17\n" +
                "-2\n" +
                "-12\n" +
                "+7\n" +
                "-16\n" +
                "-2\n" +
                "-14\n" +
                "-18\n" +
                "-5\n" +
                "+17\n" +
                "-10\n" +
                "+7\n" +
                "+5\n" +
                "-7\n" +
                "-3\n" +
                "-13\n" +
                "+15\n" +
                "+7\n" +
                "-5\n" +
                "-18\n" +
                "+4\n" +
                "-14\n" +
                "-9\n" +
                "-13\n" +
                "-16\n" +
                "-8\n" +
                "-6\n" +
                "+10\n" +
                "-1\n" +
                "+19\n" +
                "+14\n" +
                "-13\n" +
                "+4\n" +
                "+13\n" +
                "-9\n" +
                "+8\n" +
                "+6\n" +
                "-2\n" +
                "-15\n" +
                "+13\n" +
                "-8\n" +
                "+21\n" +
                "+11\n" +
                "-18\n" +
                "+16\n" +
                "-10\n" +
                "+6\n" +
                "+3\n" +
                "+20\n" +
                "+8\n" +
                "-9\n" +
                "-12\n" +
                "-20\n" +
                "+18\n" +
                "-12\n" +
                "-13\n" +
                "-13\n" +
                "+3\n" +
                "-2\n" +
                "-10\n" +
                "-1\n" +
                "+19\n" +
                "-4\n" +
                "-9\n" +
                "-7\n" +
                "+3\n" +
                "-4\n" +
                "-18\n" +
                "-16\n" +
                "+19\n" +
                "-12\n" +
                "+16\n" +
                "+5\n" +
                "+15\n" +
                "-11\n" +
                "-6\n" +
                "-21\n" +
                "+4\n" +
                "-19\n" +
                "-13\n" +
                "+10\n" +
                "+4\n" +
                "+7\n" +
                "-43\n" +
                "+34\n" +
                "+40\n" +
                "+23\n" +
                "+9\n" +
                "+14\n" +
                "+19\n" +
                "+11\n" +
                "+22\n" +
                "+9\n" +
                "-11\n" +
                "+22\n" +
                "+11\n" +
                "+11\n" +
                "-20\n" +
                "-17\n" +
                "+7\n" +
                "+15\n" +
                "-23\n" +
                "+6\n" +
                "-17\n" +
                "-8\n" +
                "-8\n" +
                "-4\n" +
                "+24\n" +
                "+18\n" +
                "+8\n" +
                "+17\n" +
                "-22\n" +
                "+19\n" +
                "+71889";
    }
}

