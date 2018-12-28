package days;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Day02 {

    public static void main(String[] args) {
//        runA();
        runB();
    }

//
//    To make sure you didn't miss any, you scan the likely candidate boxes again, counting the number that have an ID
//    containing exactly two of any letter and then separately counting those with exactly three of any letter. You can
//    multiply those two counts together to get a rudimentary checksum and compare it to what your device predicts.
//
//    For example, if you see the following box IDs:
//
//    abcdef contains no letters that appear exactly two or three times.
//    bababc contains two a and three b, so it counts for both.
//    abbcde contains two b, but no letter appears exactly three times.
//    abcccd contains three c, but no letter appears exactly two times.
//    aabcdd contains two a and two d, but it only counts once.
//    abcdee contains two e.
//    ababab contains three a and three b, but it only counts once.

    public static void runA() {
        String input = getInput();
        String[] boxCodes = input.split("\n");
        int hasTwo = 0;
        int hasThree = 0;
        for(String boxCode : boxCodes) {
            if(containsCharacter(2, boxCode)) {
                hasTwo++;
            }
            if(containsCharacter(3, boxCode)) {
                hasThree++;
            }
        }

        System.out.println("answerA: hastwo: " + hasTwo + " hasThree: " +hasThree + " result = " + hasThree * hasTwo);
    }

    public static boolean containsCharacter(int repetitions, String code) {
        Map<Character, Integer> charMap = new TreeMap<>();
        char[] characters = code.toCharArray();
        for(char character : characters) {
                int occurences = charMap.getOrDefault(character, 0) +1;
                charMap.put(character, occurences);
        }

        for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
            if(entry.getValue() == repetitions) {
                return true;
            }
        }
        return false;
    }

//    The IDs abcde and axcye are close, but they differ by two characters (the second and fourth).
//    However, the IDs fghij and fguij differ by exactly one character, the third (h and u). Those must be the correct boxes.

    public static void runB() {
        String input = getInput();
        String[] boxCodes = input.split("\n");
        int lowestDiffTotal = 100;
        int indexLowestDiffBoxA = 0;
        int indexLowestDiffBoxB = 0;

        for(int i =0; i < boxCodes.length -1; i++) { // -1 because last doesn't need to be compared to anything anymore.
            String currentCode = boxCodes[i];
            for(int j = i+1; j < boxCodes.length; j++) {
                String compareBox = boxCodes[j];
                int difference = countDifferences(currentCode, compareBox);
                if(difference < lowestDiffTotal) {
                    lowestDiffTotal = difference;
                    indexLowestDiffBoxA = i;
                    indexLowestDiffBoxB = j;
                }
            }
        }

        System.out.println("answerb:\n" + boxCodes[indexLowestDiffBoxA] + "\n" + boxCodes[indexLowestDiffBoxB]);
    }

    public static int getIndexOfClosestBox() {

        return 0;
    }

    public static int countDifferences(String a, String b) {
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        int differences = 0;
        for(int i = 0; i < aChars.length; i ++) {
            if(aChars[i] != bChars[i]) {
                differences++;
            }
        }
        return differences;
    }

    public static String getInput() {
        return "efmyhuxcqqldtwjzvisepargvo\n" +
                "efuyhuxckqldtwjrvrsbpargno\n" +
                "efmyhuxckqlxtwjxvisbpargoo\n" +
                "efmyhuxczqbdtwjzvisbpargjo\n" +
                "efmyhugckqldtwjzvisfpargnq\n" +
                "tfmyhuxckqljtwjzvisbpargko\n" +
                "efmyhuxckqldtvuzvisbpavgno\n" +
                "efmyhufcrqldtwjzvishpargno\n" +
                "tfmyhuxbkqlduwjzvisbpargno\n" +
                "efayhtxckqldbwjzvisbpargno\n" +
                "efgyhuxckuldtwjzvisbpardno\n" +
                "efmyhuxckuldtwizvisbpargqo\n" +
                "efmyhuxcknldtjjzvihbpargno\n" +
                "efmyhuxcnqddtwjzvisbpafgno\n" +
                "efmyhubokqldtwjzvisbpargdo\n" +
                "efmhhuxckqldtwdzvisbpjrgno\n" +
                "efmyhuxckqldtwjrcisbpargny\n" +
                "efmyhuxckqsdtwjzlisbpargng\n" +
                "effyhuxckqlqtwjzjisbpargno\n" +
                "nfmyhjxckqldtwjzcisbpargno\n" +
                "efmyhvxckqldtwszvwsbpargno\n" +
                "efmyhuxckqldtwutvisbpprgno\n" +
                "kfmyhuxckqldtwzuvisbpargno\n" +
                "efhyhtxckqldtwjmvisbpargno\n" +
                "efmyhuhckqldtwjzvisbpxwgno\n" +
                "efmyhuxcfqldtrjzvitbpargno\n" +
                "efmyhudckqldtwjfvisbparvno\n" +
                "ekmyhuxckqlstwjzvisbdargno\n" +
                "efmyhuxckqlxtwjftisbpargno\n" +
                "etsyhuxckqldtwjzvisbpargnf\n" +
                "exmyhusckqldtwjzvisbpakgno\n" +
                "efmyhubckqlrtljzvisbpargno\n" +
                "efmyhuxckwldtwjovizbpargno\n" +
                "efmyhulckqzdtwjzvisbpargpo\n" +
                "efmyhuxckbcdlwjzvisbpargno\n" +
                "zfmyhulckqbdtwjzvisbpargno\n" +
                "efmyquxckfldtwazvisbpargno\n" +
                "efxyhuxakqldtwjzvisupargno\n" +
                "efmlhuxckkedtwjzvisbpargno\n" +
                "efhyhuxwkqldtwjzvisbparjno\n" +
                "efmyhuxfkqldtwjzvisvparyno\n" +
                "efmyhuxckqfdtijzvisblargno\n" +
                "efmyhuxckqldtfjzvisbwhrgno\n" +
                "efmymuxcknldtwzzvisbpargno\n" +
                "eomybuxckqldtwkzvisbpargno\n" +
                "pfmyhuxckqldtwgzvasbpargno\n" +
                "vfmyhuxcoqldtwjzvisbparvno\n" +
                "eflyhuxckqldtwjzvirypargno\n" +
                "efmyvuxckqldtwizvisbpaqgno\n" +
                "epmyhuxckqldtwjzvesbparpno\n" +
                "efoyhuxckoldtwjmvisbpargno\n" +
                "efmyhuxckqydtwpzvisbpaqgno\n" +
                "efmyhuxckqldezbzvisbpargno\n" +
                "efmyhuxckqldtwjzvisboalxno\n" +
                "efmyhuxckqldtwuzvipbjargno\n" +
                "efmymuxcuqldtwjzvasbpargno\n" +
                "jfmyhuxckqldtwjzvzsbpargdo\n" +
                "nfmyhuxckqlntsjzvisbpargno\n" +
                "efmxhuxckqgdtwjzvisbparjno\n" +
                "efmyhuxckpldtpjzvpsbpargno\n" +
                "efmyhuxcyqldtwjhvisbpargqo\n" +
                "efmyhexgkqydtwjzvisbpargno\n" +
                "ffmyhuxckqldtwjzvisbpafgnk\n" +
                "efmyfuxckqldtwjpvisbpartno\n" +
                "efmyhoxckcmdtwjzvisbpargno\n" +
                "efmyhuxxkqldtwjzviabparyno\n" +
                "jfmyhuxakqldtwgzvisbpargno\n" +
                "efmjhuxckqcdtwjzvisbjargno\n" +
                "efmyhuxccqldtwjzxisbxargno\n" +
                "efmyhurckqldiwjzvrsbpargno\n" +
                "efmyhuxckasdtwjzvisboargno\n" +
                "efmyhvxckmldtwjgvisbpargno\n" +
                "efmyhuxckoldtwjuvisbpardno\n" +
                "efmyduxckqldtwjgvzsbpargno\n" +
                "ejmyhuxckqldtwbzvisbpargnb\n" +
                "efmymuxchqldtwjzvibbpargno\n" +
                "efmyhjxckqldtwjgvinbpargno\n" +
                "efmyhuxhyqldtwbzvisbpargno\n" +
                "efmyhuxckqldtwjzvisbpzignq\n" +
                "efmyuueckqldxwjzvisbpargno\n" +
                "qfmyhyxckqldtwizvisbpargno\n" +
                "efmyhupckqldtwjzvpgbpargno\n" +
                "efmycuxckqldtwjzvfdbpargno\n" +
                "efmyhugcrqldtwjfvisbpargno\n" +
                "efmyhexckqldtwjzvischargno\n" +
                "efmyhuxckqldtljzvasbpamgno\n" +
                "efmyzdxckqldtwjovisbpargno\n" +
                "efmyhuxccqldtwjzvdsbpaigno\n" +
                "ufmyhuxekqldtwjzvisbpargne\n" +
                "efmyhuxckqldfwozvisgpargno\n" +
                "afmyhuackqldtwjzvisbdargno\n" +
                "efmyauxckqldtwjzvisiparmno\n" +
                "efmysuxckqldtwjzvisbeaggno\n" +
                "efmyhuxckqldtwjzvisbgzigno\n" +
                "efryhuxlkqldtwozvisbpargno\n" +
                "lfmyhuxckqldtwjzvhsbparuno\n" +
                "efmyhzxckqldswjzvisqpargno\n" +
                "efmyhuxrkqldtwjzvisgpargco\n" +
                "efmyhudckqldtwjzyisbkargno\n" +
                "efmyhuacqqldtwjzviabpargno\n" +
                "jfmyhuxckqldtwvzvicbpargno\n" +
                "efmkhuxckqlftejzvisbpargno\n" +
                "nfmyhuxckqldnwjzvisbxargno\n" +
                "efmyhuxckqldtwjvvisjpyrgno\n" +
                "efmyhuxcmxldtwjzvisbpargto\n" +
                "efmyhuxckqldtwqbvpsbpargno\n" +
                "efmyhuxckzldjwjzvisbplrgno\n" +
                "efmywgxckqldtwxzvisbpargno\n" +
                "efmsguxckqldhwjzvisbpargno\n" +
                "nfmyhuxlkqldtwjzvisbgargno\n" +
                "etmyhuxckqldtwjzvqsbptrgno\n" +
                "efmyxuxckqldtfjzvisbyargno\n" +
                "cfmihuxckqldtwjzvisbpargnf\n" +
                "jfzyhuxckqldtwjzviscpargno\n" +
                "efmyhuxckqldtmjzvisbpbzgno\n" +
                "bfmyhuzckqldcwjzvisbpargno\n" +
                "efmyhuxckqldtmjzvmslpargno\n" +
                "efqyvuxckqldtwazvisbpargno\n" +
                "efmecrxckqldtwjzvisbpargno\n" +
                "efmyhuuckqldtwjzvisrpargnt\n" +
                "efmphuxckqldtwjzvisbparmho\n" +
                "ifmyhuxckqldtwjzvismpsrgno\n" +
                "efmyhuookqldywjzvisbpargno\n" +
                "efmyhfxckyldtwjnvisbpargno\n" +
                "efmyhxhckqldtwjzvisqpargno\n" +
                "efryhuxcfqldtwjzvisbparkno\n" +
                "efmyhutckqldpwjzvixbpargno\n" +
                "efmyoukckqldtwjzvisbpargko\n" +
                "efmyhuxckqldtwjzviseparynv\n" +
                "efmyhuxcksldvwjzvisbnargno\n" +
                "efmyhuxckqrdtwlzmisbpargno\n" +
                "efmyhuxcwqldtwjzviqapargno\n" +
                "eymyhuxckqrdtwkzvisbpargno\n" +
                "efmyhuxckqldtwjzpisopargnj\n" +
                "efmyhuxikqldtwjzvirupargno\n" +
                "efmyhuxcuzldtnjzvisbpargno\n" +
                "efmyhxxikqldtwjzvisbpalgno\n" +
                "efmyhuxceqldtwjzvdsbparguo\n" +
                "efmyhuxwkqldtwjmvisbparxno\n" +
                "efmyhuxpkqldtwjzvisfpargfo\n" +
                "efmyfuxckaldtwjzvirbpargno\n" +
                "efmyhuxckqrdtwjzvismprrgno\n" +
                "efmyhuxckqldzwjzvisbpnrgfo\n" +
                "efmyhfuckqldtwjyvisipargno\n" +
                "efmyhuxcpqlqfwjzvisbpargno\n" +
                "efmyyuxckqldtwjzvrsepargno\n" +
                "efmphuxckqlptqjzvisbpargno\n" +
                "efmyhuxnfqldtwjzvisbpmrgno\n" +
                "efmyhuxckqldtwjzkisnpnrgno\n" +
                "mfmyhuxckqldtwjzvisbzarcno\n" +
                "efmyhuxckqldtwlzviszpargwo\n" +
                "efmytuxckqndtwjqvisbpargno\n" +
                "efmyzuxckqldtwjzvisbaargjo\n" +
                "efmihuxckqlutwjzvimbpargno\n" +
                "efmyhuxckqldgwjzvixbparono\n" +
                "tfmyduxckqldtyjzvisbpargno\n" +
                "ejmyhuockqldtwjzvidbpargno\n" +
                "efmyheyckqkdtwjzvisbpargno\n" +
                "efmyhuxckqldtwjzoisbpargfj\n" +
                "efqyhuxcxqldtwxzvisbpargno\n" +
                "jfmyhaxckqldtwjzvisbvargno\n" +
                "hfmyhqxckqldtwjzvisbparvno\n" +
                "efmyhukckqlrtwjzvqsbpargno\n" +
                "efmyhuxckqldvwmzvisbparrno\n" +
                "efoyhuxckqldtwjzvilwpargno\n" +
                "ejmyhuxckqldtwjzxisbprrgno\n" +
                "efmyhuxckqldtsjzvisupdrgno\n" +
                "efzyhjxckqldtwjzvisbpasgno\n" +
                "ebmyhulckqldtwjzvisbpargnr\n" +
                "efmyhuxcjqlntwjzqisbpargno\n" +
                "efmlocxckqldtwjzvisbpargno\n" +
                "efmyhuxckqldtwjzvizkpargnm\n" +
                "ebmyhuxckqldtwjzvlfbpargno\n" +
                "efmyhuxckqldtwjyvisbpjrgnq\n" +
                "afmyhuxckqldtwjzvpsbpargnv\n" +
                "efmyxuxckqwdzwjzvisbpargno\n" +
                "efmyhuxskqlqthjzvisbpargno\n" +
                "efmyhuxckqldtwdzvisbearglo\n" +
                "mfmyhuxckqldtzjzvisbparggo\n" +
                "efmyhuqckqodtwjzvisbpadgno\n" +
                "efmyhuxctqldywjzvisspargno\n" +
                "efmyhuxckqqdtwjnvisbporgno\n" +
                "efmyhixckqldowjzvisbpaagno\n" +
                "efmyhuxckqldtsszvisbpargns\n" +
                "edmyhuxckqpdtwjzrisbpargno\n" +
                "efsyhuxckqldtijzvisbparano\n" +
                "efmyhuxckqxdzwjzviqbpargno\n" +
                "efmyhuxckqldtwjzviqqpsrgno\n" +
                "efmyhuockqlatwjzvisbpargho\n" +
                "efmyhuxckqldtwjzvishkavgno\n" +
                "vfmyhuxckqldtwjzvksbaargno\n" +
                "efmahuxckqudtwbzvisbpargno\n" +
                "ewmyhixckqudtwjzvisbpargno\n" +
                "efmywuxczqldtwjzvisbpargao\n" +
                "efmyhuqjkqldtwyzvisbpargno\n" +
                "efmyhuxekqldtwjzmksbpargno\n" +
                "efmyhuxcoqtdtwjzvinbpargno\n" +
                "ebmyhuxkkqldtwjzvisbdargno\n" +
                "ecmyhnxckqldtwnzvisbpargno\n" +
                "efmyhuxbkqldtwjzvksbpaigno\n" +
                "efayhuxckqidtwjzvisbpavgno\n" +
                "efmrhuxckqldswjzvisbpaugno\n" +
                "efmyhuuckqldtwjyvisipargno\n" +
                "xfmyhuxckqldawjzvosbpargno\n" +
                "efmyhuxckklhtwjzvisbpargnq\n" +
                "efmyhmxcaqldzwjzvisbpargno\n" +
                "efiyhuxcksldtwjzvisbpamgno\n" +
                "zfmyhuzckqldtwjzvisbparhno\n" +
                "efmyhuxckqlvtwjdvisbparsno\n" +
                "efmyhmxckaldtwjzmisbpargno\n" +
                "efmysuxcqoldtwjzvisbpargno\n" +
                "efmyhuxckqldtwjzvisbsargrb\n" +
                "effyhuxckqldtwjzvisbpwfgno\n" +
                "efmyhuxclqmdtwjzxisbpargno\n" +
                "edmohuxckqldtwjziisbpargno\n" +
                "efmyhuxckpldtwjzviubpaegno\n" +
                "efmyhuxcpqldtwjzjimbpargno\n" +
                "ehmyhuxckqldtwjzsisbpargnq\n" +
                "efmyhcxcdqldtwjzvisbqargno\n" +
                "efmjhuxckqldmwjzviybpargno\n" +
                "efeyhzxckqlxtwjzvisbpargno\n" +
                "efmyhuxczqadtwazvisbpargno\n" +
                "efmahuxckqldtwjzvisbpafgnl\n" +
                "efmyouxckqldtwjzvizbpacgno\n" +
                "emmrhuxckqldtwjzvisqpargno\n" +
                "exmyhuxckqlftwjnvisbpargno\n" +
                "efuyhuxckqldrwjzvisbpargnw\n" +
                "efmywuxfkqldtwjztisbpargno\n" +
                "efmyhuxdkqldtwjzvisbpqrzno\n" +
                "eemyhuxckqldrwjzvisbpajgno\n" +
                "efmyiuxckqldtbjzvrsbpargno\n" +
                "eqmyhuxckqldlwjzfisbpargno\n" +
                "efmyhuxckqlitwuzvisbpvrgno\n" +
                "ecoyhuxckqldtwjzvishpargno\n" +
                "efmyhuxckcldtwjzlisbparlno\n" +
                "efmyhsxcknldtwjfvisbpargno\n" +
                "efmyhuxckqldtwjrvosbpargbo\n" +
                "enmehuxckzldtwjzvisbpargno\n" +
                "hfmyhuxckqqdtwjzvisbpawgno\n" +
                "efmyhufckcjdtwjzvisbpargno\n" +
                "efmxhuxckqldthjzvisfpargno\n" +
                "efmyaukckqldtwjsvisbpargno\n" +
                "efmyhukckqldtwpzvisbpmrgno\n" +
                "dfmyhuxckqldtwjzvisbvarmno\n" +
                "afmbhuxckqldtwjzvssbpargno\n" +
                "efmyhuxchqldtwezvisbpargzo\n" +
                "efmphuxckqlxjwjzvisbpargno\n" +
                "efhyxuxckqldtwjzvisbpargko\n" +
                "sfmyhexckqldtwjzvisbqargno\n" +
                "efmghuxckqldtwjzvitbparnno";

    }
}
