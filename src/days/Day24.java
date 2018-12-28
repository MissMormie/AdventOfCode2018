package days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Day24 {

    public static void main(String[] args) {
        test();
//        runA();
//        runB();
    }

    public static void test() {
        String immuneSystemInput = getTestInputImmuneSystem();
        List<Group> immuneSystem = new ArrayList<>();
        int counter = 1;
        for(String line : immuneSystemInput.split("\n")) {
            immuneSystem.add(new Group(line, "Immune System group", counter));
            counter++;
        }

        // Set up infection
        String infectionInput = getTestInputInfection();
        List<Group> infection = new ArrayList<>();
        counter = 1;
        for(String line : infectionInput.split("\n")) {
            infection.add(new Group(line, "Infection group", counter));
            counter++;
        }

        selectTarget(infection, immuneSystem);


    }

    public static void runA() {
        // Set up immune system
        String immuneSystemInput = getTestInputImmuneSystem();
        immuneSystemInput = getInputImmuneSystem();
        List<Group> immuneSystem = new ArrayList<>();
        int counter = 1;
        for(String line : immuneSystemInput.split("\n")) {
            immuneSystem.add(new Group(line, "Immune System group", counter));
            counter++;
        }

        // Set up infection
        String infectionInput = getTestInputInfection();
        infectionInput = getInputInfection();
        List<Group> infection = new ArrayList<>();
        counter = 1;
        for(String line : infectionInput.split("\n")) {
            infection.add(new Group(line, "Infection group", counter));
            counter++;
        }

        int count = 1;
        while(infection.size() > 0 && immuneSystem.size() > 0) {
            System.out.println("ROUND " + count++ + " --------------------------------------");

            // Sort both groups in attacking order.
            selectTarget(immuneSystem, infection);
            selectTarget(infection, immuneSystem);

            fight(infection, immuneSystem);

            // Take out killed troops and reset.
            infection = removeKilledTroops(infection);
            immuneSystem = removeKilledTroops(immuneSystem);


        }
        List<Group> winningTeam;
        if(infection.size() != 0) {
            winningTeam = infection;
            System.out.println("infection wins");
        } else {
            winningTeam = immuneSystem;
            System.out.println("immune system wins");
        }

        System.out.println("answer A: " + winningTeam.stream().mapToInt(g -> g.numUnits).sum());
    }

    public static List<Group> removeKilledTroops(List<Group> groups) {
        groups.stream().forEach(g -> g.resetGroup());
        return groups.stream().filter(g -> g.canAttack() == true).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * During the target selection phase, each group attempts to choose one target.
     * In decreasing order of effective power, groups choose their targets; in a tie,
     * the group with the higher initiative chooses first.
     *
     * At the end of the target selection phase, each group has selected zero or one groups to attack,
     * and each group is being attacked by zero or one groups.
     * @param groups
     * @param targets
     */
    public static void selectTarget(List<Group> groups, List<Group> targets) {

        groups.sort(new Comparator<Group>() {
            @Override
            public int compare(Group g1, Group g2) {
                if(g1.getEffectivePower() != g2.getEffectivePower()) {
                    return g2.getEffectivePower() - g1.getEffectivePower();
                }
                return g2.initiative - g1.initiative;
            }
        });

        groups.stream().forEach(g -> g.selectTarget(targets));

    }

    /**
     * Groups attack in decreasing order of initiative, regardless of whether they are part of the infection or the
     * immune system. (If a group contains no units, it cannot attack.)
     * @param infection
     * @param immuneSystem
     */
    public static void fight(List<Group> infection, List<Group> immuneSystem) {
        ArrayList<Group> combinedGroups = new ArrayList<>();
        combinedGroups.addAll(infection);
        combinedGroups.addAll(immuneSystem);

        combinedGroups.sort(new Comparator<Group>() {
            @Override
            public int compare(Group g1, Group g2) {
                return g2.initiative - g1.initiative;
            }
        });
        combinedGroups
                .stream()
                .forEach(Group::attack);
    }

    public static class Group {
        /**
         * Units within a group all have the same hit points (amount of damage a unit can
         * take before it is destroyed), attack damage (the amount of damage each unit deals),
         * an attack type, an initiative (higher initiative units attack first and win ties),
         * and sometimes weaknesses or immunities.
         */

        int numUnits;
        int hitpoints;
        int attackDmg;
        String attackType;
        int initiative;
        ArrayList<String> weaknesses = new ArrayList<>();
        ArrayList<String> immunities = new ArrayList<>();
        Group target = null;
        boolean isAttacked = false;
        String team;
        int id;
        int effectivePower; // todo remove

        @Override
        public String toString() {
            return team + " " + id + " effective power: " + effectivePower;
        }

        public Group(String input, String team, int id) {
            this.team = team;
            this.id = id;

            // Yeah i should do something smart with a regex here, if i could.. :)
            String[] pieces = input.split(" ");
            numUnits = Integer.valueOf(pieces[0]);
            hitpoints = Integer.valueOf(pieces[4]);
            initiative = Integer.valueOf(pieces[pieces.length -1]);

            if(input.indexOf("(") > -1) {
                setSpecials(input.substring(input.indexOf("(")+1, input.indexOf(")")));
            }

            for(int i = 0; i < pieces.length; i++) {
                if(pieces[i].equals("does")) {
                    attackDmg = Integer.valueOf(pieces[i+1]);
                    attackType = pieces[i + 2];
                    break;
                }
            }

            effectivePower = getEffectivePower(); // todo remove
            int i = 1;
        }

        public void resetGroup() {
            target = null;
            isAttacked = false;
        }

        /**
         *
         * @return true killed the enemy
         */
        public boolean attack() {
            if(canAttack() && target != null) {
                System.out.print(team + " " + id + " attacks defending group");
                return target.getsAttacked(this);
            }
            return false;
        }

        public boolean notUnderAttack() {
            return !isAttacked;
        }

        /**
         * The attacking group chooses to target the group in the enemy army to which it would
         * deal the most damage (after accounting for weaknesses and immunities, but not
         * accounting for whether the defending group has enough units to actually receive all
         * of that damage).
         * If an attacking group is considering two defending groups to which it would deal
         * equal damage, it chooses to target the defending group with the largest effective
         * power; if there is still a tie, it chooses the defending group with the highest
         * initiative. If it cannot deal any defending groups damage, it does not choose a
         * target. Defending groups can only be chosen as a target by one attacking group.
         * @param enemygroups
         * @return
         */
        public boolean selectTarget(List<Group> enemygroups) {
            target = null;

            // Shouldn't get to target selection if it can't attack, but let's double check this.
            if(!canAttack()) {
                return false;
            }

            // The attacking group chooses to target the group in the enemy army to which it
            // would deal the most damage
            OptionalInt max = enemygroups
                    .stream()
                    .filter(Group::notUnderAttack)
                    .mapToInt(g -> g.getPotentialDamage(this))
                    .max();

            if(!max.isPresent() || max.getAsInt() <= 0) { // can't deal damage
                return false;
            }


            List<Group> potentialTargets = enemygroups
                    .stream()
                    .filter(g -> g.getPotentialDamage(this) == max.getAsInt())
                    .collect(toList());

            if(potentialTargets.size() == 1) {
                target = potentialTargets.get(0);
            } else {
                // If an attacking group is considering two defending groups to which it would deal
                // equal damage, it chooses to target the defending group with the largest effective
                // power;
                int highestPower = potentialTargets.stream().mapToInt(g -> g.getEffectivePower()).max().getAsInt();
                List<Group> powerTargets = potentialTargets
                        .stream()
                        .filter(g -> g.getEffectivePower() == highestPower)
                        .collect(toList());

                if(powerTargets.size() == 1) {
                    target = powerTargets.get(0);
                } else {
                    // if there is still a tie, it chooses the defending group with
                    // the highest initiative.
                    target = powerTargets.stream().max(Comparator.comparingInt(g -> g.initiative)).get();
                }
            }

            target.setIsAttacked(true);
            return true;
        }

        private void setSpecials(String specials) {
            String[] special = specials.split("; ");
            for(String s : special) {
                setSpecial(s);
            }
        }

        private void setSpecial(String special) {
            special = special.replace(",", "");
            String[] words = special.split(" ");
            ArrayList<String> specialList;
            if(words[0].equals("weak")) {
                specialList = weaknesses;
            } else {
                specialList = immunities;
            }

            for(int i = 2; i < words.length; i++) {
                specialList.add(words[i]);
            }
        }

        /**
         * Each group also has an effective power: the number of units in that group
         * multiplied by their attack damage.
         * @return
         */
        public int getEffectivePower() {
            effectivePower = numUnits * attackDmg;
            return  effectivePower;
        }

        public void setIsAttacked(boolean b) {
            isAttacked = b;
        }


        public boolean canAttack() {
            return  numUnits > 0;
        }

        /**
         * Groups never have zero or negative units; instead, the group is removed from combat.
         * @return false if group no longer exists.
         */
        public boolean getsAttacked(Group attackers) {
            int dmg = getPotentialDamage(attackers);
            int casualties = dmg / hitpoints;
            System.out.println(id + ", killing " + casualties + " units");
            numUnits -= casualties;
            if(numUnits < 1) {
                numUnits = 0;
                System.out.println(team + " killed. No units left");
                return false;
            }
            return true;
        }

        public int getPotentialDamage(Group attackers) {
            int result;
            if(immunities.contains(attackers.attackType))
                result = 0;

            if(weaknesses.contains(attackers.attackType)) {
                result =  attackers.getEffectivePower() * 2;
            } else {
                result = attackers.getEffectivePower();
            }

            return result;
        }
    }

    public static String getTestInputImmuneSystem() {
        return "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2\n" +
                "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4508 fire damage at initiative 3\n";
    }

    public static String getTestInputInfection() {
        return "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1";
    }

    public static String getInputImmuneSystem() {
        return "89 units each with 11269 hit points (weak to fire, radiation) with an attack that does 1018 slashing damage at initiative 7\n" +
                "371 units each with 8033 hit points with an attack that does 204 bludgeoning damage at initiative 15\n" +
                "86 units each with 12112 hit points (immune to slashing, bludgeoning; weak to cold) with an attack that does 1110 slashing damage at initiative 18\n" +
                "4137 units each with 10451 hit points (weak to slashing; immune to radiation) with an attack that does 20 slashing damage at initiative 11\n" +
                "3374 units each with 6277 hit points (weak to slashing, cold) with an attack that does 13 cold damage at initiative 10\n" +
                "1907 units each with 1530 hit points (immune to fire, bludgeoning; weak to radiation) with an attack that does 7 fire damage at initiative 9\n" +
                "1179 units each with 6638 hit points (immune to radiation; weak to slashing, bludgeoning) with an attack that does 49 fire damage at initiative 20\n" +
                "4091 units each with 7627 hit points with an attack that does 17 bludgeoning damage at initiative 17\n" +
                "6318 units each with 7076 hit points with an attack that does 8 bludgeoning damage at initiative 2\n" +
                "742 units each with 1702 hit points (weak to radiation; immune to slashing) with an attack that does 22 radiation damage at initiative 13";
    }

    public static String getInputInfection() {
        return "3401 units each with 31843 hit points (weak to cold, fire) with an attack that does 16 slashing damage at initiative 19\n" +
                "1257 units each with 10190 hit points with an attack that does 16 cold damage at initiative 8\n" +
                "2546 units each with 49009 hit points (weak to bludgeoning, radiation; immune to cold) with an attack that does 38 bludgeoning damage at initiative 6\n" +
                "2593 units each with 12475 hit points with an attack that does 9 cold damage at initiative 1\n" +
                "2194 units each with 25164 hit points (weak to bludgeoning; immune to cold) with an attack that does 18 bludgeoning damage at initiative 14\n" +
                "8250 units each with 40519 hit points (weak to bludgeoning, radiation; immune to slashing) with an attack that does 8 bludgeoning damage at initiative 16\n" +
                "1793 units each with 51817 hit points (immune to bludgeoning) with an attack that does 46 radiation damage at initiative 3\n" +
                "288 units each with 52213 hit points (immune to bludgeoning) with an attack that does 339 fire damage at initiative 4\n" +
                "22 units each with 38750 hit points (weak to fire) with an attack that does 3338 slashing damage at initiative 5\n" +
                "2365 units each with 25468 hit points (weak to radiation, cold) with an attack that does 20 fire damage at initiative 12";
    }
}
