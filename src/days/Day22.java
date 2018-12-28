package days;

import helpers.*;
import helpers.dijkstra.model.Edge;
import helpers.dijkstra.model.Graph;
import helpers.dijkstra.model.Vertex;

import java.util.*;
public class Day22 {
//     puzzle input
    public static int DEPTH = 11817;
    public static int TARGET_X = 9;
    public static int TARGET_Y = 751;


//    public static int DEPTH = 510;
//    public static int TARGET_X = 10;
//    public static int TARGET_Y = 10;

    public enum Tool {
        TORCH, CLIMBING, NEITHER;
    }

    public static void main(String[] args) {
        runA();
        runB();
    }

    public static void runA() {
        String input = getTestInput();
        Map<String, Region> regionMap = new HashMap<>();
        for(int y = 0; y <= TARGET_Y  + 10; y++) {
            for(int x = 0; x <= TARGET_X + 10; x++) {
                Region region = new Region(x, y, regionMap);
                regionMap.put(region.getCoords(), region);
            }
        }

        int riskLevel = 0;
        for(Region region : regionMap.values()) {
            riskLevel += region.getRiskLevel();
        }

        System.out.println("answer A: " + riskLevel);
    }


    public static void runB() {
        String input = getTestInput();
        Map<String, Region> regionMap = new HashMap<>();
        List<Vertex> vertexList = new ArrayList<>();
        Map<String, Vertex> vertexMap = new HashMap<>();
        for(int y = 0; y <= TARGET_Y  + 10; y++) {
            for(int x = 0; x <= TARGET_X + 10; x++) {
                Region region = new Region(x, y, regionMap);
                regionMap.put(region.getCoords(), region);
                Vertex vertex = new Vertex(region.getCoords(), "name", x, y);
                vertexList.add(vertex);
            }
        }

        // Create Edges
        List<Edge> edgeList = new ArrayList<>();
        for(int y = 0; y <= TARGET_Y  + 10; y++) {
            for (int x = 0; x <= TARGET_X + 10; x++) {
                if(x > 0 ) {
                    String coordinate = Coordinate.makeCoordString(x,y);
                    Vertex source = vertexMap.get(coordinate);
                    String destinationCoordinate = Coordinate.makeCoordString(x - 1, y);
                    Vertex destination = vertexMap.get(destinationCoordinate);
                    int weight = calculateWeight(coordinate, destinationCoordinate, regionMap);
                    new Edge("id", source, destination, weight);
                }

                if(y > 0) {

                }
            }
        }

        Graph graph = new Graph(vertexList, edgeList);




        System.out.println("answer B: ");
    }

    public static int calculateWeight(String coordinate, String destinationCoordinate, Map<String, Region> regionMap) {
        return 0;
    }

    public static void createGraph(Map<String, Region> regionMap){

    }

    public static class Region extends Coordinate {
        public enum RegionType {
            ROCKY, WET, NARROW;
        }

        public boolean canUseTool(Tool tool) {
            switch(type) {
                case ROCKY: return (tool == Tool.TORCH || tool == Tool.CLIMBING);
                case WET: return (tool == Tool.CLIMBING || tool == Tool.NEITHER);
                case NARROW: return (tool == Tool.TORCH || tool == Tool.NEITHER);
            }
            return false;
        }

        int erosionLevel;
        RegionType type;

        public Region(int x, int y, Map<String, Region> regionMap) {
            super(x,y);
            determineErosionLevel(regionMap);
        }

        public int getRiskLevel(){
            return type.ordinal();
        }

        private void determineErosionLevel(Map<String, Region> regionMap) {
            int geoIndex = calculateGeoIndex(regionMap);
            erosionLevel = (geoIndex + DEPTH) % 20183;
            switch(erosionLevel % 3) {
                case 0: type = RegionType.ROCKY; break;
                case 1: type = RegionType.WET; break;
                case 2: type = RegionType.NARROW; break;
            }

        }

        private int calculateGeoIndex(Map<String, Region> regionMap) {
            int geoIndex;

            if(x == 0 && y ==0 ) {
                geoIndex = 0;
            } else if(x == TARGET_X && y == TARGET_Y) {
                geoIndex = 0;
            } else if(y == 0 ) {
                geoIndex = 16807 * x;
            } else if(x == 0) {
                geoIndex = 48271 * y;
            } else {
                String coord1  = Util.makeCoordString(x - 1, y);
                String coord2 = Util.makeCoordString(x, y - 1);
                geoIndex = regionMap.get(coord1).erosionLevel * regionMap.get(coord2).erosionLevel;
            }
            return geoIndex;
        }
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
}
