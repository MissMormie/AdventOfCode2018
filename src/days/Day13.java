package days;

import helpers.DirectionalMovingObject;

import java.util.*;

public class Day13 {

    public static void main(String[] args) {
        new Track(getInput().split("\n"));
    }



    public static class Track {
        Map<String, Character> tracks = new HashMap();
        List<Cart> carts = new ArrayList();
        Set<String> cartCoords = new HashSet();

        public Track(String[] input) {

            setUpTracks(input);

            boolean notCrashed = true;
            while(notCrashed) {
                runCarts();
                if(cartCoords.size() < 2) {
                    notCrashed = false;
                }
            }

            for(String coords: cartCoords) {
                System.out.println("left over cart " + coords);
            }
        }

        private boolean runCarts() {

            boolean notCrashed = true;

            Collections.sort(carts);

            for(Cart cart : carts) {
                if(cart.crashed){
                    continue;
                }
                // remove coords from set
                if(!runCart(cart)) {
                    return false;
                }
                // check if it didn't collide.
            }
//            System.out.println(cartCoords.toString());
            return true;
        }

        private boolean runCart(Cart cart) {
            // remove old location
            cartCoords.remove(cart.getCoords());

            // move cart
            cart.move();

            // check for collision
            if(cartCoords.add(cart.getCoords()) == false) {
                // Crash!
                handleCrash(cart);
                if(carts.size() == 1) {
                    return false;
                }
            }

            // Update direction
            char track = tracks.get(cart.getCoords());
            cart.updateDirection(track);

            return true;
        }

        private void handleCrash(Cart cart) {
            // removes crashed carts.
            cart.setCrashed(true);
            cartCoords.remove(cart.getCoords());
            for(Cart c : carts) {
                if(c.getCoords().equals(cart.getCoords()) && c != cart) {
                    c.setCrashed(true);
                    System.out.println("crash at: " + cart.getCoords() + " carts: " + cart.cartId +  " " + c.cartId);
                    return;
                }
            }
        }

        private void setUpTracks(String[] input) {

            for(int y_coord = 0; y_coord < input.length; y_coord++) { // x-axis
                char[] orders = input[y_coord].toCharArray();
                for(int x_coord = 0; x_coord < orders.length; x_coord++) {
                    String key = "" + x_coord + "," +y_coord;
                    char value = orders[x_coord];
                    if(value == '>' || value == '<') {
                        createCart(value, x_coord, y_coord);
                        value = '-';
                    } else if (value == '^' || value == 'v'){
                        createCart(value, x_coord, y_coord);
                        value = '|';
                    }
                    tracks.put(key, value);
                }

            }
        }

        private Cart createCart(char direction, int x, int y) {
            Cart cart = new Cart(direction, x, y);
            carts.add(cart);
            cartCoords.add(cart.getCoords());
            return null;
        }
    }

    public static class Cart extends DirectionalMovingObject implements  Comparable {

        public static int totalCarts = 0;
        private int cartId = totalCarts++;
        public boolean crashed = false;

        public Cart(char initialDirection, int x, int y) {
            super(x, y, Direction.UP);
            if(initialDirection == '>') {
                direction = Direction.RIGHT;
            } else if (initialDirection == '<') {
                direction = Direction.LEFT;
            } else if (initialDirection == '^') {
                direction = Direction.UP;
            } else if (initialDirection == 'v') {
                direction = Direction.DOWN;
            }
        }

        public String getCoords() {
            return coordinate.getCoords();
        }

        public void setCrashed(boolean crashed) {
            this.crashed = crashed;
        }

        public void updateDirection(char trackPiece) {
            if(trackPiece == '-' || trackPiece == '|') {
                return;
            } else if(trackPiece == '+') {
                changeDirectionAtCrossRoad();
                return;
            } else if(direction == Direction.UP) {
                if(trackPiece == '/') {
                    direction = Direction.RIGHT;
                } else if(trackPiece == '\\') {
                    direction = Direction.LEFT;
                }
            } else if(direction == Direction.DOWN) {
                if(trackPiece == '/') {
                    direction = Direction.LEFT;
                } else if(trackPiece == '\\') {
                    direction = Direction.RIGHT;
                }
            } else if(direction == Direction.LEFT) {
                if(trackPiece == '/') {
                    direction = Direction.DOWN;
                } else if(trackPiece == '\\') {
                    direction = Direction.UP;
                }
            } else if(direction == Direction.RIGHT) {
                if(trackPiece == '/') {
                    direction = Direction.UP;
                } else if(trackPiece == '\\') {
                    direction = Direction.DOWN;
                }
            }
        }

        int nextTurn = 0; // 0 = left, 1 = straight, 2 right;
        private void changeDirectionAtCrossRoad() {
            // first left, then straight, then right;
            if(nextTurn == 0) {
                turnLeft();
            } else if (nextTurn == 2) {
                turnRight();
            }
            nextTurn++;
            nextTurn %=3;
        }

        @Override
        public int compareTo(Object o) {
            int answer = 0;
            Cart otherCart = (Cart) o;
            if(otherCart.coordinate.y == this.coordinate.y) {
                answer = this.coordinate.x - otherCart.coordinate.x;
            } else {
                answer = this.coordinate.y - otherCart.coordinate.y;
            }
            return answer;
        }
    }


    public static String getTestInput() {
//        return "/->-\\        \n" +
//                "|   |  /----\\\n" +
//                "| /-+--+-\\  |\n" +
//                "| | |  | v  |\n" +
//                "\\-+-/  \\-+--/\n" +
//                "  \\------/   ";
//        return "/->-\\        \n" +
//                "|   |  /----\\\n" +
//                "| /-+--+-\\  |\n" +
//                "| | |  | v  |\n" +
//                "\\-+>/  \\-+--/\n" +
//                "  \\------/   ";

        return "/>-<\\  \n" +
                "|   |  \n" +
                "| /<+-\\\n" +
                "| | | v\n" +
                "\\>+</ |\n" +
                "  |   ^\n" +
                "  \\<->/";
    }


    public static String getInput() {
        return "     /----------------------------------------------------------------------------------------------\\                                                 \n" +
                "     |                                                          /-----------------------------------+-------------------------------\\                 \n" +
                "     |      /-------------\\                             /-------+-----------------------------------+----------------\\              |                 \n" +
                "     |      |        /----+-----------------------------+-------+----------------------------------<+\\               |              |                 \n" +
                "     |      |        |    | /-----------------------\\   |       |   /-------------------------------++---------------+\\             |                 \n" +
                "     |      |        |    | |                       |   |      /+---+-----------------\\          /--++---------------++\\            |                 \n" +
                "     |      |        |    | |                       |   |      ||/--+-----------------+----------+\\ ||               |||            |                 \n" +
                "     |      |      /-+----+-+-----------------------+---+-----\\|||  |                 |          || ||          /----+++------------+------------\\    \n" +
                "     |      |      | |    | |                       |   | /---++++--+---------------\\ |          || ||          |    |||            |            |    \n" +
                "     |     /+------+-+----+-+---------------\\       |   |/+---++++--+---------------+-+----------++-++----------+---\\|||           /+------------+--\\ \n" +
                "     |     ||      | |    | |  /------------+-------+---+++---++++--+---------------+-+----------++-++----------+---++++-------\\/--++------\\     |  | \n" +
                "     |     ||      | |    | |  |            |       |   |||   ||||  |               | |          || ||          |   ||||       ||  ||      |     |  | \n" +
                "     \\-----++------+-+----+-+--+------------+-------+---+++---++++--+---------------+-+----------++-/|          |   ||||       ||  ||      |     |  | \n" +
                "           ||   /--+-+----+-+--+---------\\  |       |/--+++---++++--+---------------+-+----------++--+----------+---++++-------++--++------+-----+-\\| \n" +
                "           ||   |  | |    | |  |     /---+--+---\\   ||/-+++---++++--+---------------+-+--------\\ ||  |          |   ||||       ||  ||      |     | || \n" +
                "           ||  /+--+-+---\\| |  |     |   |  |   |/--+++-+++---++++--+---------------+-+--------+-++--+---->-----+---++++-------++--++------+-\\   | || \n" +
                "           ||  ||  | |   || |/-+-----+---+--+---++--+++-+++---++++--+---------\\     | |        | ||/-+----------+---++++-------++--++------+-+---+\\|| \n" +
                "           ||  ||  | |   || || |     | /-+--+---++--+++-+++---++++--+---------+-----+-+--------+-+++-+----------+---++++-------++--++-----\\| |   |||| \n" +
                "           ||  ||  | |   || || |   /-+-+-+--+---++--+++-+++---++++--+---------+-----+-+----\\   | \\++-+----------+---+++/       ||  \\+-----++-+---+++/ \n" +
                "  /--------++--++--+-+---++-++-+---+-+-+-+--+---++--+++-+++---++++--+-\\       |     | |    |   |  || |          |   |||        ||   |     || |   |||  \n" +
                "  |        ||  ||  | |   || || |/--+-+-+-+--+---++--+++-+++---++++--+-+-------+-----+-+----+---+\\ || |          |   |||        ||   |     v| |   |||  \n" +
                "  |        ||  ||  | |   || || ||  | | | |  |   ||  ||| |||   ||||  | |       |     | |    |   || || |          |   |||        ||   |     || |   |||  \n" +
                "  |        ||  ||  | |   || || ||  | | | |  |   ||  ||| |\\+---++++--+-+-------+-----+-+----+---++-++-+----------+---/||        ||   |     || |   |||  \n" +
                "  |     /--++--++--+-+---++-++-++--+-+-+-+--+---++--+++-+-+---++++--+\\|       |     | |    |   || || |          |    ||        ||  /+-----++-+\\  |||  \n" +
                "  |     |  ||  ||  | |   || || \\+--+-+-+-+--+---++--+++-+-+---++++--+++-------+-----+-+----+---++-++-+----------+----++--------/|  ||     || ||  |||  \n" +
                "  |     |  ||  ||  | |/--++-++--+--+-+-+-+\\ |   ||  |\\+-+-+---++++--+++-------+-----+-+----+---++-++-+----------+----++---------+--++-----++-++--++/  \n" +
                "  |     |  ||  ||  | ||  || ||  |  | | | || |   ||  | | | |   ||\\+--+++-------+-----+-+----+---++-++-+----------+----++---------+--+/     || ||  ||   \n" +
                "  |     |  ||  \\+--+-++--/| ||  |  | | | || |   ||  | | | \\---++-+--+++-------+-----/ |    |   || || |          |    ||         |  |      || ||  ||   \n" +
                "  |     |  ||/--+--+>++---+-++--+--+-+-+-++-+---++--+-+-+-----++-+--+++-------+-------+-\\  |   || || |/---------+----++-----\\   |  |  /---++-++-\\||   \n" +
                "  | /---+--+++--+--+-++---+-++--+--+-+-+-++-+-\\ ||  | | |     || |  |||       |       | |  |   || || ||         |    ||     |   |  |  |   || || |||   \n" +
                "  | |   |  |||  |  | ||   | ||  |  | | | || | | ||  | | | /---++-+--+++-------+-------+-+--+---++-++-++---------+----++-----+---+--+--+---++-++-+++-\\ \n" +
                "/-+-+---+--+++--+--+-++---+-++--+--+-+-+-++-+-+-++--+-+-+-+---++-+--+++-------+-------+-+\\ |   || || ||         |    ||     |   |  |  |   || || ||| | \n" +
                "| | |   |/-+++--+--+-++---+-++--+--+-+-+-++-+-+-++--+-+-+-+---++-+--+++-------+-------+-++-+---++\\|| ||         |/---++-----+---+-\\|  |   || || ||| | \n" +
                "| | |   || |||  |  | ||   | ||  |/-+-+-+-++-+-+-++--+-+-+\\|   || |  |||   /---+-------+-++-+---+++++-++---------++---++-----+---+-++--+---++\\|| ||| | \n" +
                "| | |   || |||/-+--+-++---+-++--++-+-+-+-++-+-+-++\\ | \\-+++---++-+--+++---+---+-------+-++-+---/|||| ||         ||   ||   /-+---+-++--+---+++++-+++-+\\\n" +
                "| | |   || |||| |  | ||   | ||  || | | | || | | ||| |   |||   || |  |||   |   |       | || |    |||| ||         ||   ||   | |   | ||  |   ||||| ||| ||\n" +
                "| | |   || |||| |  | ||   | ||/-++-+-+-+-++-+-+-+++-+---+++---++-+--+++---+---+-------+-++-+----++++-++---------++---++---+-+\\  | || /+---+++++-+++\\||\n" +
                "|/+-+---++-++++-+--+-++---+-+++-++-+-+-+-++-+-+-+++-+---+++---++-+--+++---+---+-------+-++-+----++++-++-----\\   ||   ||   | ||  | || ||   ||||| ||||||\n" +
                "||| |   || \\+++-+--+-++---+-+++-++-+-+-+-++-/ | ||| |   |||/--++-+--+++---+---+-------+-++-+----++++-++-----+---++---++---+-++-\\| || ||   ||||| ||||||\n" +
                "||| |   ||  ||| |  | ||   | ||| ||/+-+-+-++---+-+++-+---++++--++-+\\ |||   |   |       | || |/---++++-++-----+---++---++---+-++-++-++-++---+++++\\||||||\n" +
                "||| |   ||  ||| |  | ||   | ||| |||| | |/++---+-+++-+---++++--++-++-+++---+---+-------+-++-++---++++-++-----+-\\ ||   ||   | || || || ||   ||||||||||||\n" +
                "||| |   ||  ||| |  | ||   | ||| |||| \\-++++---+-/|| |   ||||  || || |||   |   |       | || ||   |||| ||     | | ||   ||   | || || || ||   ||||||||||||\n" +
                "||| |/--++--+++-+--+-++---+\\||| ||||   ||||   |  || |   ||||  || || |||   |   |       | || ||   |||| ||     | | ||   ||   | || || || ||   ||||||||||||\n" +
                "||| || /++--+++-+--+-++---+++++-++++---++++---+--++-+---++++--++-++-+++\\  |   |       | || ||   |||| ||     | | ||   ||   | || || || ||   ||||||||||||\n" +
                "||| || |||  ||| |  | ||   ||||| |\\++---++++---+--++-+---+/||  |\\-++-++++--+---+-------/ || ||   |||| ||     | | ||   ||   | || || || ||   ||||||||||||\n" +
                "||| || |||  ||| |  | ||   ||||| | ||   ||||   |  || |   | ||  |  || ||||  | /-+---------++-++---++++-++-----+-+-++---++---+-++\\|| || ||   ||||||||||||\n" +
                "||| || |||  ||| |  | ||   ||||| \\-++---++++---+--++-+---+-++--+--++-++++--+-+-+---------++-++---/||| ||     | | ||   ||   | ||||| || ||   ||||||||||||\n" +
                "||| || ||v  ||| \\--+-++---+++++---++---++/| /-+--++-+---+-++--+--++-++++--+-+-+---------++-++----+++-++-\\   | | ||   ||   | ||||| || ||   ||||||||||||\n" +
                "||| || |||  |||    | ||   |||||   ||   || | |/+--++-+---+-++--+--++-++++--+-+-+---------++-++----+++-++-+---+-+-++---++---+-+++++-++-++--\\||||||||||||\n" +
                "||| || |||  |||    | ||   |||||   ||   || | |||  || |   |/++--+--++-++++--+-+-+--\\      || ||    ||| || |   | | ||   ||   | |^||| || ||  |||||||||||||\n" +
                "||| || |||  |\\+----+-++---+++++---++---++-+-+++--++-+---++++--+--++-++++--+-+-+--+------/| ||    ||| || |   | | ||   ||   | ||||| || ||  |||||||||||||\n" +
                "|||/++-+++--+-+----+-++---+++++---++---++-+-+++--++-+---++++--+--++-++++--+-+-+--+-------+\\||    ||| || |   | | ||   ||   \\-+++++-++-++--++++++++++++/\n" +
                "|||||| |||  | |    | ||   |||||   \\+---++-+-+++--++-+---++++--+--+/ ||||  | | |  |       ||||    ||| || |   | | ||   ||     ||||| || ||  |||||||||||| \n" +
                "|||||| |||  | |    | ||   |||||   /+---++-+-+++--++-+---++++--+--+--++++--+-+-+--+-------++++--\\ ||| |\\-+---+-+-++---++-----/|||| || ||  |||||||||||| \n" +
                "|||||| |||  | |    | ||   |||||   ||   || | |||  || |   ||||  |  |  ||||  | | |  |       ||||  | ||| |  |   | | ||   ||      |||| || ||  |||||||||||| \n" +
                "|||||| ||| /+-+----+-++---+++++---++---++-+-+++--++-+---++++--+--+--++++--+-+-+\\ |       ||||  | ||| |  |   | | ||   ||      |||| || ||  |||||||||||| \n" +
                "|||||| ||| || |    | || /-+++++---++---++-+-+++--++-+---++++--+--+--++++--+-+-++-+-------++++--+-+++-+--+---+-+-++---++--\\   |||| || ||  |||||||||||| \n" +
                "|||||| ||| || |    | || | |||||   || /-++-+-+++--++-+---++++--+--+-\\||||  | | || |       ||||  | ||| |  |   | | ||   ||  |   |||| || ||  |||||||||||| \n" +
                "|||||| ||| || |    | || | |||||/--++-+-++-+-+++--++-+---++++--+--+-+++++--+-+-++-+\\      ||||  | ||| |  |   | | ||   ||  |   |||| || ||  |||||||||||| \n" +
                "|||||| ||| ||/+----+-++-+-++++++--++-+-++-+-+++-\\|| |   |||\\--+--+-+++++--+-+-++-++------++++--+-+++-+--+---+-+-++---++--+---++/| || ||  |||||||||||| \n" +
                "|||||| ||| ||||    | || |/++++++--++-+-++-+-+++-+++-+---+++---+--+-+++++--+-+-++-++------++++--+-+++-+--+---+-+\\||   ||  |   || | || ||  |||||||||||| \n" +
                "|||||| ||| |\\++----+-++-++/|||||  || | || | \\++-+++-+---+++---+--+-+++++--+-+-++-++------++++--+-+++-+--/   | ||||   ||  |   || | || ||  |||||||||||| \n" +
                "|||||| ||| | ||/---+-++-++-+++++--++\\| || |  || ||| |   |||   |  | |||||  | | || ||      ||||  | ||| |  /---+-++++---++--+--\\|| | || ||  |||||||||||| \n" +
                "|||||| ||| | |\\+---+-++-++-+++++--++++-++-+--++-++/ |   |||   |  | |||||  | | || ||      ||||  | ||| |  |   | ||\\+---++--+--+++-+-++-++--++++++++/||| \n" +
                "|||||| ||| | | |   | || || |||||  |||| || |/-++-++--+---+++---+--+-+++++--+-+-++-++------++++--+-+++-+--+---+-++-+---++--+-\\||| | || ||  |||||||| ||| \n" +
                "|||||| ||| | | |   | || ||/+++++--++++-++-++-++-++--+---+++---+--+-+++++--+-+-++-++\\     ||||  | ||| |  |   | || |   ||  | |||| \\-++-++--++/||||| ||| \n" +
                "|||||| ||| | | |   | || ||||||||/-++++-++-++-++-++--+---+++---+--+-+++++--+-+-++-+++-----++++--+-+++-+--+---+-++-+---++--+-++++---++-++\\ || ||||| ||| \n" +
                "|||||| ||| | | |   | || ||||||||| |||| || || || ||  |   |||   |  | |||||  | | || |||     ||||  | ||| |  |   | || |   ||  | ||||   || ||| || ||||| ||| \n" +
                "|||||| ||| | | |   | || |||||\\+++-++++-++-++-++-++--+---+++---+--+-+++++--+-+-/| |||     ||||  | ||| |  |   | || |   ||  | ||||   || ||| || ||||v ||| \n" +
                "||||\\+-+++-+-+-+---+-++-+++++-+++-++++-++-++-+/ || /+---+++---+--+-+++++--+-+--+-+++-----++++--+-+++-+--+---+-++-+---++--+-++++--\\|| ||| || ||||| ||| \n" +
                "|||| | ||| | | |   | || ||||| \\++-++++-++-++-+--++-++---+++---+--+-+++++--+-+--+-+++-----++++--+-+++-+--+---+-++-+---++--+-++/|  ||| ||| || ||||| ||| \n" +
                "|||| |/+++-+-+-+---+-++-+++++--++-++++\\|| || |  || ||   |||   |/-+-+++++--+-+--+-+++-----++++--+-+++-+--+---+-++-+---++--+-++-+--+++-+++-++-+++++\\||| \n" +
                "|||| ||||| | | | /-+-++-+++++--++-+++++++-++-+--++-++---+++---++-+-+++++--+-+--+-+++-----++++-\\| |||/+--+---+-++-+---++--+\\|| |  ||| ||| || ||||||||| \n" +
                "|||| ||||| | | | | |/++-+++++--++-+++++++-++-+-\\|| || /-+++---++-+-+++++--+-+--+-+++-----++++-++-+++++--+---+-++-+---++--++++-+--+++-+++-++-+++++++++\\\n" +
                "|||| ||||| | | | | |||| |||||  || |||||||/++-+-+++-++-+-+++---++-+-+++++--+-+--+-+++-----++++-++-+++++--+---+-++-+---++--++++-+--+++\\||| || ||||||||||\n" +
                "|||| ||||| | | | | |||| |||||  || |||||||||| | ||| || | |||   || | |||||  | |  | |||     |||\\-++-+++++--+---+-++-+---++--++++-+--+++++++-++-+++/||||||\n" +
                "|||| ||||| | | ^ | |||| |||||  || |||||||||| | ||| || | |||   || | |||||  | |  | |||     |||  || |||||  |   | || |   ||  |||| |  ||||||| || ||| ||||||\n" +
                "|||| ||||| | | | | |||| |||||  || |||||||||\\-+-+++-++-+-+++---++-+-+++++--+-+--+-+++-----+++--++-+++++--+---+-++-+---++--++/| |  |||||\\+-++-+++-/|||||\n" +
                "|||| ||||| | | | | |||| |||||  || |||||||||  | ||| || | |||   || | |||||  | |  | |||     |||  || |||||  |   | || |   ||  || | |  ||||| | || |||  |||||\n" +
                "|||| ||||| | | | | ||\\+-+++++--++-+++++++++--+-+++-++-+-+++---++-+-+++++--+-+--+-+++-----+++--++-++++/  |   | || |   ||  || | |  ||||| | || |||  |||||\n" +
                "|||| ||||| | | | | || | ||\\++--++-+++++++++--+-+++-++-+-+++---++-+-+++++--+-+--+-++/     |||  || ||||   |   | || |   ||  || | |  ||||| | || |||  |||||\n" +
                "|||| ||||| | | | | || | || ||  || |||||||||  | ||| || | |||   || | |||||  |/+--+-++------+++--++-++++---+-\\ | || |   ||  || | |  ||||| | || |||  |||||\n" +
                "|||| ||||| | | | | || | |\\-++--++-+++++++++--+-+++-++-+-+++---++-+-+++++--+++--+-++------+++--++-++++---+-+-+-+/ |   ||  || | | /+++++-+-++\\|||  |||||\n" +
                "|||| ||||| | | | | || | |  ||  || |||||||||  | ||| || | |||   |\\-+-+++++--+++--+-++------+++--++-++++---+-+-+-+--+---++--++-+-+-++++++-+-++++++--/||||\n" +
                "|||| ||||| | | | | \\+-+-+--++--++-+++++++++--+-+++-++-+-+++---/  | |||||  |||  | ||      |||  || ||||   | | | |  |   ||  || | | |||||| |/++++++--\\||||\n" +
                "|||| |\\+++-+-+-+-+--+-+-+--++--++-++++/||||  | ||| || | |||      | |||||  |||  | ||      |||  || ||||   | | | |  |   ||  || | | |||||| ||||||||  |||||\n" +
                "|||| | ||| | | | |  | | |/-++--++-++++-++++--+-+++-++-+-+++------+-+++++--+++--+-++-\\    |||  ||/++++---+-+-+-+--+---++--++-+-+-++++++\\||||||||  |||||\n" +
                "||||/+-+++-+-+-+-+--+-+-++-++--++-++++-++++--+-+++-++-+-+++------+-+++++--+++--+-++-+----+++--+++++++---+-+-+-+-\\|   ||  v| | | |||||||||||||||  |||||\n" +
                "|||||| ||| | | | |  | | || ||  || |||| ||||  | ||| || | |||      | |||||  |||  | || |    |||  |||||||   | | | | ||   ||  || | | |||||||||||||||  |||||\n" +
                "|||||| ||| | | | |  | | || ||  \\+-++++-++++--+-+++-++-+-+++------+-+++++--+++--+-+/ |    |||  |||||||   | | | | ||   ||  || | | |||||||||||||||  |||||\n" +
                "|||||| ||| | | | |  | | || ||  /+-++++-++++-\\| ||| || |/+++------+-+++++--+++--+-+--+----+++--+++++++---+-+-+-+-++---++\\ || | | |||||||||||||||  |||||\n" +
                "|||||| ||| | | | |  | | || ||  || |||| |||| || ||| || |||\\+------+-+++++--+++--+-/  |    |||  |||||||   | | | | ||   ||| || | | ||||^||||||||||  |||||\n" +
                "|||||| ||| | | | |  | | || ||  || \\+++-++++-++-+++-++-+++-+------+-+++++--+++--+----+----+++--+/|||||   | | | | ||   ||| || | | |||||||||||||||  |||||\n" +
                "|||||| ||| | | | |  | | || ||  ||  ||| |||| || ||| || ||| |      | |||||  |||  |    |    |||  | |||||   | | | | ||   ||| || | | |||||||||||||||  |||||\n" +
                "|||||| |\\+>+-+-+-+--+-+-++<++--++--+++-++++-++-+++-++-+++-+------+-++/||  |||  |    |    |||  | |||||   | | | | ||   ||| || | | |||||||||||||||  |||||\n" +
                "||||||/+-+-+-+-+-+--+-+-++-++--++--+++-++++-++-+++-++-+++-+------+-++-++--+++--+----+----+++--+-+++++---+-+-+-+\\||   ||| || | | |||||||||||||||  |||||\n" +
                "|||||||| | | | | |  | | \\+-++--++--+++-++++-++-+++-++-+++-+------+-++-++--+++--+----+----+++--+-+++++---+-+-+-++++---+++-/| | | |||||||||||||||  |||||\n" +
                "|||||||| | | | | \\--+-+--+-++--++--+++-++++-++-+++-++-+++-+------+-++-++--+++--+----+----+++--/ |||||   | | | ||||   |||  | | | \\++++++++++/|||  |||||\n" +
                "|||||||| | | | |    | |  | ||  ||  ||| |||| || ||| || ||| |      | || ||  |||  |    |    |||    |||||   | | | ||||   |||  | | |  |||||||||| |||  |||||\n" +
                "|||||||| | | | |   /+-+--+-++--++--+++-++++-++-+++-++-+++-+------+-++-++--+++--+----+----+++----+++++--\\| | | ||||   |||  | | |  |||||||||| |||  |||||\n" +
                "|||||||| | | | |   || |  | ||  ||  ||| |||| || ||| ||/+++-+------+-++-++--+++--+----+\\   |||    |||||  || | | ||||   |||  | | |  |||||||||| |||  |||||\n" +
                "|||||||| | | | |   || |  | ||  ||  ||| |||| || ||| |||||| |      | || ||  |||  |    ||   |||    |||||  || | | ||||   |||  | | |  |||||||||| |||  |||||\n" +
                "|||||||| | | | |   || |  | ||  ||  ||| |||| || ||| |||||| |      | |\\-++--+++--+----++---+++----+++++--++-+-+-++++---+/|  | | |  |||||||||| |||  |||||\n" +
                "|||||||| | | | |   || |  | ||  ||  ||| |||| || ||| |||||| |      | |  ||  |||  |    ||   |||    |||||  || | | ||||   | |  | | |  |||||||\\++-+++--/||||\n" +
                "|||||||| | | | |   || |  | ||  ||  ||| |||| || ||| |||||\\-+------+-+--++--+++--+----++---+++----+++++--++-+-+-++++---/ |  | | |  ||\\++++-++-++/   ||||\n" +
                "|||||||| | | | |   || |  | ||  ||  ||| |||| |\\-+++-+++++--+------+-+--++--+++--+----++---+++----+++++--++-+-+-++++-----+--+-+-+--++-++++-/| ||    ||||\n" +
                "||||\\+++-+-+-+-+---++-+--+-++--++--+++-++++-+--+++-+++++--+------+-+--++--+++--+----++---+++----+++++--++-+-+-++/|     |  | | |  || ||||  | ||    ||||\n" +
                "|||| ||| | | | |   || \\--+-++--++-<+++-+++/ |  ||| |||||  |      | |  ||  ||\\--+----++---+++----+++++--++-+-+-++-+-----+--+-+-/  || ||||  | ||    ||||\n" +
                "|||| ||| | | | |   ||    |/++--++--+++\\|||  |  ||\\-+++++--+------+-+--++--++---+----++---+++----+++++--++-+-+-++-+-----+--+-+----++-++++--+-+/    ||||\n" +
                "|||| ||| | | | |   ||    |||\\--++--+++++++--+--++--+/|||  \\------+-+--++--++---+----++---+++----+++++--++-+-+-++-+-----+--+-+----++-++++--+-+-----++/|\n" +
                "|||| ||| | | | |   ||    |||   ||  |||||\\+--+--++--+-+++---------+-+--++--++---+----++---+++----+++++--++-+-+-/| |     |  | |    || ||||  | |     || |\n" +
                "|||| \\++-+-+-+-+---++----++/   ||  ||||| |  |  ||  | |||         | |  ||  ||   |    ||   |||  /-+++++--++-+-+--+-+-----+--+-+----++-++++-\\| |     || |\n" +
                "||||  || | | | |   ||    ||    ||  ||||| |  |  ||  | |||         \\-+--++--++---+----++---+++--+-++/||  || | |/-+-+-----+--+-+--\\ || |||| || |     || |\n" +
                "||||  || | | | |   ||    ||    ||  ||||| |  |  ||  | \\++-----------+--++--++---+----+/   |||  | || \\+--++-+-++-+-+-----+--+-+--+-++-++++-++-+-----/| |\n" +
                "||||  || \\-+-+-+---++----++----++--+++++-+--+--++--+--++-----------+--++--++---+--->+----+++--+-+/  |  || | || | |     |  | |  | || |||| || |      | |\n" +
                "||||  ||   | | |   ||    ||    ||  ||||\\-+--+--++--+--++-----------+--++--++---+----+----+++--+-+---+--++-+-++-+-+-----+--+-+--+-++-++++-+/ |      | |\n" +
                "||||  |\\---+-+-+---++----++----++--++++--+--+--++--+--++-----------+--+/  ||   |    |    |||  | |   |  || | || | |     |  | |  | || |||| |  |      | |\n" +
                "||||  |    | | |   \\+----++----++--++++--+--+--++--+--++-----------+--+---++---+----+----+++--+-+---+--/| | || | |     |  | |  | || |||| |  |      | |\n" +
                "||||  |    | | |    |    ||    ||  ||||  |  |  ||  |  ||           |  |   \\+---+<---+----+++--+-+---+---+-+-++-+-+-----+--+-+--+-++-++++-+--/      | |\n" +
                "||||  |    | | | /--+----++----++\\ ||||  |  |  ||  |  ||           |  |    |   |    |    |||  | |   |   | | || | |     |  | |  | || |||| |         | |\n" +
                "||||  |    | | | |  |    ||    |\\+-++++--+--+--++--+--++-----------+--+----+---+----+----+++--+-+---+---+-+-++-+-+-----+--+-+--+-++-+++/ |         | |\n" +
                "|\\++--+----+-+-+-+--+----++----+-+-++++--+--+--++--+--++-----------+--+----+---+----+----+++--+-+---+---+-+-/| | |     |  | |  | || |||  |         | |\n" +
                "| \\+--+----+-+-+-+--+----++----+-+-++++--+--+--++--+--++-----------+--/    |   |    |    |||  | |   |   | |  | | |     |  | v  | || |||  |         | |\n" +
                "|  |  |    | | | |  |    ||    | | \\+++--+--+--++--+--++-----------+-------+---+----+----++/  | |   |/--+-+--+-+-+---\\ |  | |  | || |||  |         | |\n" +
                "|  |  |    | | | |  |    ||    | |  |||  |  |  ||  |  |\\-----------+-------+---+----+----++---+-+---++--+-+--+-+-+---+-/  | |  | || |||  |         | |\n" +
                "|  |  |    | | | |  |    ||    | |  |||  |  |  ||  |  |            | /-----+---+----+----++-\\ | |   ||  \\-+--+-+-+---+----+-/  | || |||  |         | |\n" +
                "|  |  |    | | | |  |    ||    | |  |||  |  |  ||  |  |            | |    /+---+----+----++-+-+-+---++----+--+-+-+---+----+----+\\|| |||  |         | |\n" +
                "|  |  |    | | | |  |    || /--+-+--+++--+--+--++--+--+\\           | |    ||   |   /+----++-+-+-+-\\ ||    |  | | |   |    |    |||| |||  |         | |\n" +
                "|  |  |    | | | |  |    || |  | |  |||  |  |  ||  |  \\+-----------+-+----++---+---++----++-+-+-+-+-++----+--+-+-+---+----+----++++-+++--+---------+-/\n" +
                "|  |  |    | | | |  |    || |  | |  |||  |  |  ||  |   |           | |    ||   |   ||    ||/+-+-+-+-++----+--+-+-+---+----+----++++-+++--+--------\\|  \n" +
                "\\--+--+----+-+-+-+--+----++-+--+-+--+++--+--+--++--+---+-----------+-+----++---+---++----/||| | | | ||    |  | | \\---+----+----+++/ |||  |        ||  \n" +
                "   |  |    | | | \\--+----++-+--+-/  |||  \\--+--++--+---+-----------+-+----++---+---++-----+++-+-+-+-++----+--+-+-----+----+----+++--/||  |        ||  \n" +
                "   |  |    | | |    |    || |  |    |||     |  ||  |   |           | |    ||   |   ||     ||| | | | ||    |  \\-+-----+----+----/||   ||  |        ||  \n" +
                "   |  |    | | \\----+----++-+--+----/||     |  ||  \\---+-----------+-+----++---+---++-----+++-+-+-+-++----+----+-----+----+>----+/   ||  |        ||  \n" +
                "   \\--+----+-+------+----++-+--+-----++-----+--++------+-----------+-+----++---+---++-----/|| | | | ||    |    |     |    |     |    ||  |        ||  \n" +
                "      |    | |      |    || |  |     ||     |  ||      |           | |    ||   |   ||      || | | | ||    |    |     |    |     |    ||  |        ||  \n" +
                "      |    \\-+------+----++-+--+-----++-----+--++------+-----------+-+----++---/   \\+------++-+-+-/ \\+----+----+-----+----/     |    ||  |        ||  \n" +
                "      |      |      |    || |  |     ||     |  ||      |           | |    ||        |      || | |    |    |    |     |          |    ||  |        ||  \n" +
                "      |      |      |    || |  |     ||     |  ||      |           | |    ||        |      || | \\----+----+----+-----+----------+----+/  |        ||  \n" +
                "      |      |      |    || |  |     ||     |  ||      |      /----+-+--\\ ||        |      || |      |    |    |     |          |    |   |        ||  \n" +
                "      |      |      |    || |  \\-----++-----/  ||      |      |    | |  | ||        |      \\+-+------+----+----+-----+----------+----+---+--------/|  \n" +
                "      |      |      |    || |        ||        ||      |      |    | |  | ||        |       | |      \\----+----+-----/          |    |   |         |  \n" +
                "      |      |      |    |\\-+--------+/        ||      |      |    | \\--+-++--------+-------/ |           |    |                |    |   |         |  \n" +
                "      |      |      |    |  \\--------+---------++------/      |    |    | \\+--------+---------+-----------+----+----------------/    |   |         |  \n" +
                "      |      |      |    |           |         ||             |    |    |  |        |         |           |    |                     \\---+---------/  \n" +
                "      |      |      |    \\-----------+---------++-------------+----+----+--+--------/         \\-----------+----+-------------------------/            \n" +
                "      |      \\------+----------------+---------+/             |    |    |  \\------------------------------/    |                                      \n" +
                "      |             |                \\---------+--------------+----/    |                                      |                                      \n" +
                "      \\-------------+--------------------------+--------------+---------+--------------------------------------/                                      \n" +
                "                    \\--------------------------/              \\---------/                                                                             \n";
    }
}
