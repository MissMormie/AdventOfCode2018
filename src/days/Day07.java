package days;

import java.util.*;

public class Day07 {

    public static void main(String[] args) {
        runA();
        runB();
    }

    public static void runA() {
//        String input = getTestInput();
        String input = getInput();

//        int numWorkers = 2;
        int numWorkers = 5;

        String[] inputArray = input.split("\n");
        Map<String, String> stepsWithDependencies = new HashMap<>(); // Key is starting step
        Map<String,String> reversedSteps = new HashMap<>(); // Key is finished step.
        Set<String> unfinishedStepsSet = new HashSet<>();

        for(String line : inputArray) {
            String startStep = line.substring(line.indexOf(" can begin")-1, line.indexOf(" can begin"));
            String finishedStep =  line.substring(5, 6);

            reversedSteps.put(finishedStep, reversedSteps.getOrDefault(finishedStep, "") + startStep);
            stepsWithDependencies.put(startStep, stepsWithDependencies.getOrDefault(startStep, "") + finishedStep);
            unfinishedStepsSet.add(finishedStep);
            unfinishedStepsSet.add(startStep);
        }
        List<String> unfinishedSteps = new ArrayList<>();
        unfinishedSteps.addAll(unfinishedStepsSet);
        unfinishedSteps.sort(String::compareToIgnoreCase);

        // Set up workers.
        Worker[] workers = new Worker[numWorkers];
        for(int i = 0; i < numWorkers; i++) {
            workers[i] = new Worker();
        }

        String stepOrder = "";
        int totalTimeSpent = 0;
        // welke key staat niet in stepsWithDependencies, omdat we alphabetisch gesorteerd hebben is de eerste altijd de juiste.
        while(!unfinishedSteps.isEmpty()) {
            System.out.println();
            System.out.print(totalTimeSpent + " ");
            totalTimeSpent++;
            int index;
            for(index = 0; index < workers.length; index++) {
                if(workers[index].finishedStep()) {
                    doStep(String.valueOf(workers[index].ch), unfinishedSteps, stepsWithDependencies, reversedSteps);
                }

                if(workers[index].isAvailable()) {
                    for (String unfinishedStep : unfinishedSteps) {
                        if (stepsWithDependencies.containsKey(unfinishedStep))
                            continue;
                        workers[index].doStep(unfinishedStep.charAt(0));
                        unfinishedSteps.remove(unfinishedStep);

                        stepOrder += unfinishedStep;
                        break;
                    }
                }
                workers[index].spentTime();
                System.out.print(workers[index].time + String.valueOf(workers[index].ch)  + " ");
            }
        }

        while(finishWork(workers)) {
            totalTimeSpent++;
        }


        System.out.println("answer A: " + stepOrder);
        System.out.println("answer B: " + totalTimeSpent );
    }

    public static boolean finishWork(Worker[] workers) {
        boolean notAllFinished = false;
        for(Worker worker : workers) {
            worker.spentTime();
            if(!worker.isAvailable()) {
                return true;
            }
        }
        return notAllFinished;
    }

    public static class Worker {
        int time = 0;
        int startupTime = 60;
        char ch;
        private boolean finishedStep = false;

        public void doStep(char ch) {
            this.ch = ch;
            time = startupTime + Integer.valueOf(ch - 'A' +1);
        }

        public void spentTime() {
            if(time > 0) {
                time--;
                if(time == 0) {
                    finishedStep = true;
                }
            }
        }

        public boolean isAvailable() {
            return time <= 0;
        }

        public boolean finishedStep() {
            if(finishedStep) {
                finishedStep = false;
                return true;
            }
            return false;
        }
    }

    public static void doStep(String currentStep, List<String> unfinishedSteps, Map<String,String> steps, Map<String,String> reversedSteps) {
        // remove from unfinished list
        String canContinue = reversedSteps.remove(currentStep);
        if(canContinue == null)
            return;
        for(char ch : canContinue.toCharArray()) {
            String step = String.valueOf(ch);
            String dependentOn = steps.get(step);
            dependentOn = dependentOn.replaceAll(currentStep, "");
            if(dependentOn.length() == 0) {
                steps.remove(step);
            } else {
                steps.put(step, dependentOn);
            }

        }


    }

    public static void runB() {
        String input = getTestInput();
//        String input = getInput();

        System.out.println("answer B: ");
    }


    public static String getTestAnswer() {
        return "CABDFE";
    }

    public static String getTestInput() {
        return  "Step C must be finished before step A can begin.\n" +
                "Step C must be finished before step F can begin.\n" +
                "Step A must be finished before step B can begin.\n" +
                "Step A must be finished before step D can begin.\n" +
                "Step B must be finished before step E can begin.\n" +
                "Step D must be finished before step E can begin.\n" +
                "Step F must be finished before step E can begin.";
    }


    public static String getInput() {
        return "Step J must be finished before step E can begin.\n" +
                "Step X must be finished before step G can begin.\n" +
                "Step D must be finished before step A can begin.\n" +
                "Step K must be finished before step M can begin.\n" +
                "Step P must be finished before step Z can begin.\n" +
                "Step F must be finished before step O can begin.\n" +
                "Step B must be finished before step I can begin.\n" +
                "Step U must be finished before step W can begin.\n" +
                "Step A must be finished before step R can begin.\n" +
                "Step E must be finished before step R can begin.\n" +
                "Step H must be finished before step C can begin.\n" +
                "Step O must be finished before step S can begin.\n" +
                "Step Q must be finished before step Y can begin.\n" +
                "Step V must be finished before step W can begin.\n" +
                "Step T must be finished before step N can begin.\n" +
                "Step S must be finished before step I can begin.\n" +
                "Step Y must be finished before step W can begin.\n" +
                "Step Z must be finished before step C can begin.\n" +
                "Step M must be finished before step L can begin.\n" +
                "Step L must be finished before step W can begin.\n" +
                "Step N must be finished before step I can begin.\n" +
                "Step I must be finished before step G can begin.\n" +
                "Step C must be finished before step G can begin.\n" +
                "Step G must be finished before step R can begin.\n" +
                "Step R must be finished before step W can begin.\n" +
                "Step Z must be finished before step R can begin.\n" +
                "Step Z must be finished before step N can begin.\n" +
                "Step G must be finished before step W can begin.\n" +
                "Step L must be finished before step G can begin.\n" +
                "Step Y must be finished before step R can begin.\n" +
                "Step P must be finished before step I can begin.\n" +
                "Step C must be finished before step W can begin.\n" +
                "Step T must be finished before step G can begin.\n" +
                "Step T must be finished before step R can begin.\n" +
                "Step V must be finished before step Z can begin.\n" +
                "Step L must be finished before step C can begin.\n" +
                "Step K must be finished before step I can begin.\n" +
                "Step J must be finished before step I can begin.\n" +
                "Step Q must be finished before step C can begin.\n" +
                "Step F must be finished before step A can begin.\n" +
                "Step H must be finished before step Y can begin.\n" +
                "Step M must be finished before step N can begin.\n" +
                "Step P must be finished before step H can begin.\n" +
                "Step M must be finished before step C can begin.\n" +
                "Step V must be finished before step Y can begin.\n" +
                "Step O must be finished before step V can begin.\n" +
                "Step O must be finished before step Q can begin.\n" +
                "Step A must be finished before step G can begin.\n" +
                "Step T must be finished before step Z can begin.\n" +
                "Step K must be finished before step R can begin.\n" +
                "Step H must be finished before step O can begin.\n" +
                "Step O must be finished before step Y can begin.\n" +
                "Step O must be finished before step C can begin.\n" +
                "Step K must be finished before step P can begin.\n" +
                "Step P must be finished before step F can begin.\n" +
                "Step E must be finished before step M can begin.\n" +
                "Step M must be finished before step I can begin.\n" +
                "Step T must be finished before step W can begin.\n" +
                "Step P must be finished before step L can begin.\n" +
                "Step A must be finished before step O can begin.\n" +
                "Step X must be finished before step V can begin.\n" +
                "Step S must be finished before step G can begin.\n" +
                "Step A must be finished before step Y can begin.\n" +
                "Step J must be finished before step R can begin.\n" +
                "Step K must be finished before step F can begin.\n" +
                "Step J must be finished before step A can begin.\n" +
                "Step P must be finished before step C can begin.\n" +
                "Step E must be finished before step N can begin.\n" +
                "Step F must be finished before step Y can begin.\n" +
                "Step J must be finished before step D can begin.\n" +
                "Step H must be finished before step Z can begin.\n" +
                "Step U must be finished before step H can begin.\n" +
                "Step J must be finished before step T can begin.\n" +
                "Step V must be finished before step G can begin.\n" +
                "Step Z must be finished before step I can begin.\n" +
                "Step H must be finished before step W can begin.\n" +
                "Step B must be finished before step R can begin.\n" +
                "Step F must be finished before step B can begin.\n" +
                "Step X must be finished before step C can begin.\n" +
                "Step L must be finished before step R can begin.\n" +
                "Step F must be finished before step U can begin.\n" +
                "Step D must be finished before step N can begin.\n" +
                "Step P must be finished before step O can begin.\n" +
                "Step B must be finished before step O can begin.\n" +
                "Step F must be finished before step C can begin.\n" +
                "Step H must be finished before step L can begin.\n" +
                "Step O must be finished before step N can begin.\n" +
                "Step J must be finished before step Y can begin.\n" +
                "Step H must be finished before step N can begin.\n" +
                "Step O must be finished before step L can begin.\n" +
                "Step I must be finished before step W can begin.\n" +
                "Step J must be finished before step H can begin.\n" +
                "Step D must be finished before step Z can begin.\n" +
                "Step F must be finished before step W can begin.\n" +
                "Step X must be finished before step W can begin.\n" +
                "Step Y must be finished before step M can begin.\n" +
                "Step T must be finished before step M can begin.\n" +
                "Step U must be finished before step G can begin.\n" +
                "Step L must be finished before step I can begin.\n" +
                "Step N must be finished before step W can begin.\n" +
                "Step E must be finished before step C can begin.";

    }

}
