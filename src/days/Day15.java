package days;

import helpers.Coordinate;
import helpers.dijkstra.DijkstraAlgorithm;
import helpers.dijkstra.model.Edge;
import helpers.dijkstra.model.Graph;
import helpers.dijkstra.model.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 {
    static String ELF = "elf";
    static String GOBLIN = "goblin";

    static List<Vertex> vertexList = new ArrayList<>();
    static Map<String, Vertex> vertexMap = new HashMap<>();

    static List<Unit> goblins = new ArrayList<>();
    static List<Unit> elves = new ArrayList<>();
    static List<Edge> edges = new ArrayList<>();


    public static void main(String[] args) {
        createMap(getInput());
        int count = 0;
        drawMap();
        while(round()) {
            if(count >23) {
                int i = 1 ;
            }
            System.out.println(count);
            drawMap();
            count++;
        }

        List<Unit> winners = elves.size() > 0 ? elves : goblins;
        int sum = winners.stream().mapToInt(Unit::getHitPoints).sum();

        System.out.println("answer A: rounds " + count + " hitpoints left: " + sum + " result: " + (count * sum));
    }

    public static boolean spotTaken(LinkedList<Vertex> vertexList) {
        long count = 0;
        for(Vertex vertex: vertexList) {
            Coordinate coord = new Coordinate(vertex.x, vertex.y);
            count += goblins.stream().filter(unit -> unit.equals(coord)).count();
            count += elves.stream().filter(unit -> unit.equals(coord)).count();
        }
        if(count > 2) { // first and last place are always taken
            return true;
        }
        return false;
    }

    public static void drawMap() {
        String input = getInput();
        String[] inputArray = input.split("\n");
        List<Unit> allUnits = new ArrayList<Unit>(goblins);
        allUnits.addAll(elves);

        for(int y = 0; y < inputArray.length; y++) {
            char[] chars = inputArray[y].toCharArray();
            for (int x = 0; x < chars.length; x++) {
                if (chars[x] == '#') { // don't save this, it's a wall, we're ignoring it for paths.
                    System.out.print("#");
                } else {
                    Coordinate coord = new Coordinate(x, y);
                    Optional<Unit> optionalUnit = allUnits.stream().filter(unit -> unit.equals(coord)).findAny();
                    if(optionalUnit.isPresent()) {
                        if(optionalUnit.get().type.equals(ELF)) {
                            System.out.print("E");
                        } else {
                            System.out.print("G");
                        }
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println(" ");
        }
    }

    public static void createMap(String input) {
        String[] inputArray = input.split("\n");


        for(int y = 0; y < inputArray.length; y++) {
            char[] chars = inputArray[y].toCharArray();
            for(int x = 0; x < chars.length; x++) {
                if(chars[x] == '#') { // don't save this, it's a wall, we're ignoring it for paths.
                    continue;
                }
                String coords = Coordinate.makeCoordString(x,y);
                Vertex vertex = new Vertex("id", coords, x, y);

                vertexMap.put(coords, vertex);
                vertexList.add(vertex);

                switch(chars[x]) {
                    case 'G': goblins.add(new Unit(x,y, GOBLIN)); break;
                    case 'E': elves.add(new Unit(x,y, ELF));break;
                    default : break;
                }

            }
        }

        for(int y = 0; y < inputArray.length; y++) {
            char[] chars = inputArray[y].toCharArray();
            for(int x = 0; x < chars.length; x++) {
                createEdges(edges, vertexMap, x, y, vertexMap.get(Coordinate.makeCoordString(x, y)));
            }
        }

        setPassable();

        Graph graph = new Graph(vertexList, edges);
    }

    public static void setPassable() {
        List<Unit> allUnits = new ArrayList<Unit>(goblins);
        allUnits.addAll(elves);

        for(Unit unit : allUnits) {
            updateEdge(unit.getCoords(), false);
        }
    }

    public static void createEdges(List<Edge> edges, Map<String, Vertex> vertexMap, int x ,int y, Vertex vertex) {
        if(vertex == null) {
            return;
        }

        // Get coord above current.
        createEdge(Coordinate.makeCoordString(x, y -1), edges, vertexMap, vertex);

        // Get coord to the left
        createEdge(Coordinate.makeCoordString(x - 1, y), edges, vertexMap, vertex);

        // get coord to the right
        createEdge(Coordinate.makeCoordString(x + 1, y), edges, vertexMap, vertex);

        // get coord below current
        createEdge(Coordinate.makeCoordString(x, y +1), edges, vertexMap, vertex);
    }

    public static void createEdge(String coordinate, List<Edge> edges, Map<String, Vertex> vertexMap, Vertex sourceVertex) {
        if(vertexMap.containsKey(coordinate)) {
            edges.add(new Edge("id", sourceVertex, vertexMap.get(coordinate), 1));
        }
    }

    /**
     * Combat proceeds in rounds; in each round, each unit that is still alive takes a turn,
     * resolving all of its actions before the next unit's turn begins.
     *
     * For instance, the order in which units take their turns within a round is the reading order of their starting
     * positions in that round, regardless of the type of unit or whether other units have moved after the round started.
     * @return false when a unit cannot take a turn, so the battle is over.
     */
    public static boolean round () {
        List<Unit> allUnits = new ArrayList<Unit>(goblins);
        allUnits.addAll(elves);
        Collections.sort(allUnits);

        for(Unit unit : allUnits) {
            if(!unit.isAlive()) {
                continue;
            }

            List<Unit> targets = unit.type.equals(ELF) ? goblins : elves;

            String oldCoord = unit.getCoords();
            if(!unit.turn(targets, new Graph(vertexList, edges))) {
                return false; // done
            }

            updateEdges(oldCoord, unit.getCoords());
        }
        return true;
    }

    public static void updateEdges(String oldCoord, String newCoord) {
        if(oldCoord.equals(newCoord))
            return;
        updateEdge(oldCoord, true);
        updateEdge(newCoord, false);
    }

    public static void updateEdge(String coord, boolean passable) {
        final Vertex destinationVertex = vertexMap.get(coord);
        List<Edge> collection = edges.stream().filter(edge -> edge.getDestination() == destinationVertex).collect(Collectors.toList());
        for(Edge edge : collection) {
            edge.setPassable(passable);
        }
        //.forEach(edge -> edge.setPassable(passable));
    }

    public static class Unit extends Coordinate {
        // Each unit, either Goblin or Elf, has 3 attack power and starts with 200 hit points.
        String type;
        final int attackPower = 3;
        public int hitPoints = 200;

        public Unit(int x, int y, String type) {
            super(x, y);
            this.type = type;
        }

        public int getHitPoints() {
            return hitPoints;
        }

        /**
         * Each unit begins its turn by identifying all possible targets (enemy units). If no targets remain, combat ends.
         * On each unit's turn, it tries to move into range of an enemy (if it isn't already) and then attack (if it is in range).
         * @return false if no turn can be taken;
         */
        public boolean turn(List<Unit> targets, Graph graph) {
            if(targets == null || targets.size() == 0)
                return false;

            if(!isAlive()) {
                return true;
            }

            Unit target = identifyTargetAndMove(targets, graph);

            attack(targets);
            return true;
        }

        /**
         * Each unit begins its turn by identifying all possible targets (enemy units). If no targets remain, combat ends.
         * Then, the unit identifies all of the open squares (.) that are in range of each target; these are the squares
         * which are adjacent (immediately up, down, left, or right) to any target and which aren't already occupied by a
         * wall or another unit. Alternatively, the unit might already be in range of a target. If the unit is not already
         * in range of a target, and there are no open squares which are in range of a target, the unit ends its turn.
         * Units never move or attack diagonally, as doing so would be dishonorable. When multiple choices are equally
         * valid, ties are broken in reading order: top-to-bottom, then left-to-right.
         *
         * If the unit is already in range of a target, it does not move, but continues its turn
         * Otherwise, since it is not in range of a target, it moves.
         *
         * To move, the unit first considers the squares that are in range and determines which of those squares it
         * could reach in the fewest steps. A step is a single movement to any adjacent (immediately up, down, left, or
         * right) open (.) square. Units cannot move into walls or other units. The unit does this while considering the
         * current positions of units and does not do any prediction about where units will be later. If the unit cannot
         * reach (find an open path to) any of the squares that are in range, it ends its turn. If multiple squares are
         * in range and tied for being reachable in the fewest steps, the square which is first in reading order is chosen.
         *
         * The unit then takes a single step toward the chosen square along the shortest path to that square. If multiple
         * steps would put the unit equally closer to its destination, the unit chooses the step which is first in reading
         * order. (This requires knowing when there is more than one shortest path so that you can consider the first
         * step of each such path.)
         * @return Unit null if there are no targets left
         */
        public Unit identifyTargetAndMove(List<Unit> targets, Graph graph) {
            if(targets.size() == 0) {
                return null;
            }

            // If we're already adjacent a target we don't need to figure that out.
            Optional<Unit> optionalTarget= getAdjacentTarget(targets);
            if(optionalTarget.isPresent()) {
                return optionalTarget.get();
            }

            // identify target
            LinkedList<Vertex> shortestPath = null;
            Unit target = null;
            for(Unit unit : targets) {
                Vertex source = vertexMap.get(getCoords());
                Vertex destination = vertexMap.get(unit.getCoords());
                DijkstraAlgorithm algorithm = new DijkstraAlgorithm(graph);
                algorithm.execute(source);
                LinkedList<Vertex> path = algorithm.getPath(destination);
                if(shortestPath == null || path.size() < shortestPath.size()) {
                    shortestPath = path;
                    target = unit;
                }
            }


            if(spotTaken(shortestPath)) {
                return null;
            }

            // move along the shortest path
            if(shortestPath.size() > 2) {
                updateEdge(getCoords(), true);
                Vertex vertex = shortestPath.get(1);
                x = vertex.x;
                y = vertex.y;
                updateEdge(getCoords(), true);
            }

            return target;
        }

        public Optional<Unit> getAdjacentTarget(List<Unit> targets) {
            return targets.stream().filter(Unit::isAlive).filter(u -> u.getManhattanDistance(this) == 1).sorted().findFirst();

        }

        /**
         * To attack, the unit first determines all of the targets that are in range of it by being immediately adjacent to it.
         * If there are no such targets, the unit ends its turn. Otherwise, the adjacent target with the fewest hit
         * points is selected; in a tie, the adjacent target with the fewest hit points which is first in reading order is selected.
         */
        public void attack(List<Unit> targets) {
            // Target that has been moved to may not be only adjacent target, so we'll find all possible matches.
            // find above
            Optional<Unit> target = getAdjacentTarget(targets);
            if(target.isPresent()) {
                dealDamage(target.get());
            }
        }

        /**
         * The unit deals damage equal to its attack power to the selected target, reducing its hit points by that amount.
         */
        public void dealDamage(Unit target) {
            target.receiveDamage(attackPower);
        }

        /**
         * If this reduces its hit points to 0 or fewer, the selected target dies: its square becomes . and it takes no
         * further turns.
         */
        public void receiveDamage(int damage) {
            hitPoints -= damage;
            if(!isAlive()) {
                if(type == ELF) {
                    elves.remove(this);
                } else {
                    goblins.remove(this);
                }
                updateEdge(getCoords(), true);
            }
        }

        public boolean isAlive() {
            return hitPoints > 0;
        }
    }

    public static String getInput() {
        return "################################\n" +
                "##########..........############\n" +
                "########G..................#####\n" +
                "#######..G.GG...............####\n" +
                "#######....G.......#......######\n" +
                "########.G.G...............#E..#\n" +
                "#######G.................#.....#\n" +
                "########.......................#\n" +
                "########G.....G....#.....##....#\n" +
                "########.....#....G.........####\n" +
                "#########..........##....E.E#.##\n" +
                "##########G..G..........#####.##\n" +
                "##########....#####G....####E.##\n" +
                "######....G..#######.....#.....#\n" +
                "###....#....#########......#####\n" +
                "####........#########..E...#####\n" +
                "###.........#########......#####\n" +
                "####G....G..#########......#####\n" +
                "####..#.....#########....#######\n" +
                "######.......#######...E.#######\n" +
                "###.G.....E.G.#####.....########\n" +
                "#.....G........E.......#########\n" +
                "#......#..#..####....#.#########\n" +
                "#...#.........###.#..###########\n" +
                "##............###..#############\n" +
                "######.....E####..##############\n" +
                "######...........###############\n" +
                "#######....E....################\n" +
                "######...####...################\n" +
                "######...###....################\n" +
                "###.....###..##..###############\n" +
                "################################";
    }


    public static String getTestInput() {
//        return  "#######\n" +
//                "#.....#\n" +
//                "#....G#\n" +
//                "#.#.#G#\n" +
//                "#...#E#\n" +
//                "#.....#\n" +
//                "#######";

        return  "#######\n" +
                "#.G...#\n" +
                "#...EG#\n" +
                "#.#.#G#\n" +
                "#..G#E#\n" +
                "#.....#\n" +
                "#######";
    }
}
