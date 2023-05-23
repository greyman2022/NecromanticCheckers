import java.util.*;
//Special version of checkers.
//Has a points system: taking awards one, king awards 3.
//Spend 3 points to return the most recently dead piece to the board where it died, killing any piece already there (and skipping the turn of whatever color it is)

public class App {
    static Stack<Trio<Integer, Integer, String>> dead = new Stack<Trio<Integer, Integer, String>>();
    static final String KINGS = "BR";
    // // Really stupid but it satisfies the 2 special data structures thingy...
    // Locations are a 2 lengh int, ie 11 is position (1,1) and 21 is position (2,1)
    // We can find those locations by doing a simple integer division and stuff
    // Pair<Integer, Integer> move = new Pair<Integer, Integer>(); <K, V> - Key is
    // starting location, value is ending location

    // Creates and initializes board with - at all places
    static String[][] board = new String[8][8];
    //initializes board
    static {
        for (int i = 0; i < board[0].length; i++) {
            boolean place = i % 2 == 0;
            for (int j = 0; j < board.length; j++) {

                if (place) {
                    if (i < 3) {
                        board[j][i] = "b";
                    } else if (i > 4) {
                        board[j][i] = "r";
                    } else {
                        board[j][i] = "-";
                    }
                    place = false;
                }

                else {
                    board[j][i] = " ";
                    place = true;
                }

            }

        }

    }
    //redTurn stores turn status.
    public static boolean redTurn = true;
    //True if the game is going.
    public static boolean running = true;
    //Points for piece revival
    public static int redPoints = 4;
    public static int blackPoints = 4;
    //Stores the number of pieces that each person has in order to determine the total quantity of pieces 
    public static int redPieces = 12;
    public static int blackPieces = 12;
    //Input Scanner
    public static final Scanner input = new Scanner(System.in);
    public static final String environment = "Normal";

    public static void main(String[] args) throws Exception {
        if(environment.equals("Normal")){

        }
        else if(environment.equals("Double Jumping")){
            //Double Jumping
            board[3][7] = "-";
            board[1][1] = "-";
            board[4][4] = "B";
        }
        else if(environment.equals("Kinging")){
            //Kinging
            board[5][5] = "b";
            board[3][3] = "b";
            board[2][2] = "-";
            board[0][0] = "-";
        }
        System.out.println("Weclome to Checkers+!");
        System.out.println("You will earn points as you play the game - 1 for killing and 3 for kinging.");
        System.out.println("You can spend 3 points at the end of your turn to revive the most recently killed piece - even your enemy's.");
        System.out.println("Other than that, it's normal checkers - and takes are not required.");
        print2dArray(board);
        while (running) {
            if (redPieces == 0 || blackPieces == 0)
                break;
            System.out.println("It is " + (redTurn ? "Red\'s " : "Black\'s ") + "turn.");
            Trio<Pair<Integer, Integer>, Pair<Integer, Integer>, String> move = getMove();
            move.getKey();
            print2dArray(board);
            if(!dead.empty())
                openShop();
            redTurn = !redTurn;
            if(blackPieces == 0 || redPieces == 0){
                break;
            }
            //System.out.println("\n".repeat(26));
        }
        System.out.println("The end!");
        if(blackPieces == 0 || redPieces == 0){
            System.out.println(((blackPieces==0)? "Red " : "Black ") + "won!");
        }
        // Prompts turn
        // Checks for move validity
        // Makes move
        // Check for
        // Prompt again if there is
        // Prompts for point shop
        // Offer revival of piece (3 points)
        // ends turn

        // Hashmap to store the locations of each thing (like where it is and such)
        // ie HashMap.add((x,y),(color/king))
        // Red and black, "red", "black", "kred", "kblack"
        // Stack stores dead pieces, BOTH SIDES
    }
    //Piece Revival
    private static void openShop() {
        while(true){
            System.out.println("Would you like to spend 3 points to revive the most recently dead?(You have " + (redTurn? redPoints : blackPoints) + " points). Answer in y/n.");
            String result = input.nextLine();
            if(result.length()!=1)
                continue;
            if(!"yn".contains(result))
                continue;
            if(result.equalsIgnoreCase("n"))
                break;
            if((redTurn && redPoints<3) || (!redTurn && blackPoints<3)){
                System.out.println("Not enough points!");
                break;
            }

            //aliving as in the verb, not a living
            Trio<Integer, Integer, String> alivingPiece = dead.pop();
            int x = alivingPiece.getKey();
            int y = alivingPiece.getValue();
            kill(x, y);
            board[x][y] = alivingPiece.getName();
            if(redTurn)
                redPoints   -= 3;
            else
                blackPoints -=3;
            break;
        }
    }
    //Trio stores 
    public static Trio<Pair<Integer, Integer>, Pair<Integer, Integer>, String> getMove() {
        Trio<Pair<Integer, Integer>, Pair<Integer, Integer>, String> out = null;
        Pair<Integer, Integer> pieceToMove = null;
        Pair<Integer, Integer> locationToMove = null;
        String color;
        while(true){
            while (true) { // Gets piece to move
                System.out.println("What piece would you like to move? Format as xy");
                int moveX = input.nextInt();
                int moveY = (moveX % 10);
                moveX /= 10;


                if (moveX < 0 || moveX > 7)
                    continue;
                if (moveY < 0 || moveY > 7)
                    continue;
                String location = board[moveX][moveY];
                if (location.equalsIgnoreCase(" "))
                    continue;
                if (location.equalsIgnoreCase("-"))
                    continue;
                if (location.equalsIgnoreCase("R") && !redTurn)
                    continue;
                if (location.equalsIgnoreCase("B") && redTurn)
                    continue;

                color = location;
                pieceToMove = new Pair<Integer, Integer>(moveX, moveY);
                break;
            }
            int direction = 0;
            if(color.equalsIgnoreCase("B")){
                direction = 1;
            }
            else{
                direction = -1;
            }

            boolean cont = false;
            boolean anotherJump = false;

            while (true) {
                if(!anotherJump)
                    System.out.println("Where would you like to move it? Format as xy. Input 99 if you want to change your start piece");
                else{
                    System.out.println("Where would you like to jump with the piece at " + pieceToMove.getKey() + ", " + pieceToMove.getValue() + "?");
                }
                int moveX = input.nextInt();
                if (!anotherJump && moveX == 99){
                    cont = true;
                    break;
                }
                else if(moveX == 99)
                    continue;
                if(moveX>77)
                    continue;

                int moveY = (moveX % 10);
                moveX /= 10;

                int originX = pieceToMove.getKey();
                int originY = pieceToMove.getValue();
                //System.out.println("origin is " + originX + ", " + originY);
                int distanceX = Math.abs(originX - moveX);
                int distanceY = Math.abs(moveY - originY);
                // System.out.println("The distance is " + distanceX + ", " + distanceY);

                if (moveX < 0 || moveX > 7)
                    continue;
                if (moveY < 0 || moveY > 7)
                    continue;
                String location = board[moveX][moveY];
                if(!KINGS.contains(color) && Math.abs(moveY-originY+direction)<2)
                    continue;
                if (location.equalsIgnoreCase(" "))
                    continue;
                if (!location.equalsIgnoreCase("-"))
                    continue;
                if (distanceX > 2 || distanceY > 2)
                    continue;
                if (distanceX != distanceY)
                    continue;

                if (distanceX == 1) {
                    //System.out.println("Distance is 1");
                    if (!board[moveX][moveY].equalsIgnoreCase("-"))
                        continue;
                    locationToMove = new Pair<Integer, Integer>(moveX, moveY);
                    board[originX][originY] = "-";
                    board[moveX][moveY] = color;
                    break;
                }
                //System.out.println("Distance is 2");

                int x = (moveX + originX) / 2;
                int y = (moveY + originY) / 2;

                if (board[x][y].equalsIgnoreCase("-"))
                    continue;
                if (board[x][y].equalsIgnoreCase(color))
                    continue;

                locationToMove = new Pair<Integer, Integer>(moveX, moveY);
                kill(x,y);
                board[originX][originY] = "-";
                board[moveX][moveY] = color;
                if(anotherJump){
                    print2dArray(board);
                }
                anotherJump = false;
                
                if(isLegalMove(locationToMove, new Pair<Integer, Integer>(moveX - 2,moveY + 2*direction)))
                    anotherJump = true;
                if(isLegalMove(locationToMove, new Pair<Integer, Integer>(moveX + 2,moveY + 2*direction)))
                    anotherJump = true;

                if(!anotherJump)
                    break;

                System.out.println("You have another jump! It is still " + (redTurn? "Red\'s " : "Black\'s ") + "turn!");

                pieceToMove.setKey(locationToMove.getKey());
                pieceToMove.setValue(locationToMove.getValue());
            }
            if(cont)
                continue;
            break;
        }
        out = new Trio<Pair<Integer, Integer>, Pair<Integer, Integer>, String>(pieceToMove, locationToMove, color);
        String lower = board[locationToMove.getKey()][locationToMove.getValue()];

        //Kinging
        if((locationToMove.getValue() == 0 && lower.equals("r")) || (locationToMove.getValue() == 7 && lower.equals("b"))){
            board[locationToMove.getKey()][locationToMove.getValue()] = lower.toUpperCase();
            if(lower.equals("r"))
                redPoints += 3;
            else
                blackPoints += 3;
            dead.pop();
        }
        
        return out;
        
    }
    //Checks if a move is legal.
    public static boolean isLegalMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to){
        int moveFromX = from.getKey();
        int moveFromY = from.getValue();
        int moveX = to.getKey();
        int moveY = to.getValue();
        //System.out.println("x,y of the from and to: " + moveFromX + ", " + moveFromY + " and " + moveX + ", " + moveY);
        if (moveFromX < 0 || moveFromX > 7)
            return false;
        if (moveFromY < 0 || moveFromY > 7)
            return false;
        String location = board[moveFromX][moveFromY];
        if (location.equalsIgnoreCase(" "))
            return false;
        if (location.equalsIgnoreCase("-"))
            return false;
        if (location.equalsIgnoreCase("R") && !redTurn)
            return false;
        if (location.equalsIgnoreCase("B") && redTurn)
            return false;
        String color = location;

        int direction = 0;
        if(color.equalsIgnoreCase("B")){
            direction = 1;
        }
        else{
            direction = -1;
        }
    
        int distanceX = Math.abs(moveFromX - moveX);
        int distanceY = Math.abs(moveY - moveFromY);
        // System.out.println("The distance is " + distanceX + ", " + distanceY);

        if (moveX < 0 || moveX > 7)
            return false;
        if (moveY < 0 || moveY > 7)
            return false;
        String locationToMove = board[moveX][moveY];
        if(!KINGS.contains(color) && Math.abs(moveY-moveFromY+direction)<2)
            return false;
        if (locationToMove.equalsIgnoreCase(" "))
            return false;
        if (!locationToMove.equalsIgnoreCase("-"))
            return false;
        if (distanceX > 2 || distanceY > 2)
            return false;
        if (distanceX != distanceY)
            return false;

        if (distanceX == 1) {
            if (!board[moveX][moveY].equalsIgnoreCase("-"))
                return false;
            // board[moveFromX][moveFromY] = "-";
            // board[moveX][moveY] = color;
            return true;
        }

        int x = (moveX + moveFromX) / 2;
        int y = (moveY + moveFromY) / 2;

        if (board[x][y].equalsIgnoreCase("-"))
            return false;
        if (board[x][y].equalsIgnoreCase(color))
            return false;
        
        return true;
    }
    
    public static void kill(int x, int y){
        String color = board[x][y];
        if(color.equals("-"))
            return;
        if(color.equalsIgnoreCase("B")) {
            redPoints++;
            blackPieces--;
        }
        else {
            redPoints++;
            blackPieces--;
        }
        Trio<Integer, Integer, String> dyingPiece = new Trio<Integer, Integer, String>(x,y,color);
        dead.add(dyingPiece);
        board[x][y]="-";
    }
    //Prints the board.
    public static void print2dArray(String[][] inputArray) {
        System.out.println("   0  1  2  3  4  5  6  7");

        int rowCounter = 0;
        for (int i = 0; i < inputArray[0].length; i++) {
            System.out.print(rowCounter + "  ");
            for (int j = 0; j < inputArray.length; j++) {
                System.out.print(inputArray[j][i] + "  ");
            }
            rowCounter++;
            System.out.println();
        }
    }

}