package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import com.sun.prism.shader.Texture_ImagePattern_AlphaTest_Loader;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static String save;
    private static int savecount;
    private static String filepath="F:/code/cs61b-sp18/proj2/save.txt";
    boolean winflag=false;
    boolean saveflag1 = false;
    boolean saveflag2 = false;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        String string = "";
        String numstring="";
        int strPos = 0;
        int numcount = 0;
        int[] nums;
        long seed = 0;
        initialize();
        StdDraw.text(200,350,"The Game");
        StdDraw.text(200,200,"New Game(N)");
        StdDraw.text(200,150,"Load Game(L)");
        StdDraw.text(200,100,"Quit(Q)");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char ch = StdDraw.nextKeyTyped();
                if ((ch == 'N' || ch == 'n') && strPos == 0) {
                    string += "" + ch;
                    strPos += 1;
                    displayMessage(200,200,"Please input your seed:");
                    continue;
                }
                if (ch >= '0' && ch <= '9') {
                    string += "" + ch;
                    numstring+=""+ch;
                    StdDraw.clear();
                    StdDraw.text(200,200,"Please input your seed:");
                    StdDraw.text(200,150,numstring);
                    StdDraw.show();
                    numcount += 1;
                }
                if (((ch == 's' || ch == 'S') && strPos != 0) || ((ch == 'l' || ch == 'L') && strPos == 0)) {
                    string += "" + ch;
                    strPos += 1;
                    break;
                }
            }
        }
        boolean loadflag=false;
        if(string.charAt(0) == 'l' || string.charAt(0) == 'L'){
            loadflag=true;
            string="";
            try{
                FileReader fr=new FileReader(filepath);
                int i;
                while ((i=fr.read())!=-1){
                    string+=(""+(char)i);
                }
                fr.close();
            }catch (Exception e){
            }
        }
        if (string.charAt(0) == 'n' || string.charAt(0) == 'N') {
            if(loadflag){
                numcount=string.charAt(string.length()-1)-'0';
            }
            nums = new int[numcount];
            for (int i = 0; i < numcount; ++i) {
                nums[i] = string.charAt(i + 1) - '0';
                seed += nums[i] * Math.pow(10, numcount - i - 1);
            }
            Random random = new Random(seed);
            TETile[][] world = WorldFormer.createWorld(WIDTH, HEIGHT, random);
            ter.initialize(WIDTH,HEIGHT);
            ter.renderFrame(world);
            Position pos = WorldFormer.getCurrentPosOfPlayer();
            movementDisplay(pos,world,string+(""+numcount));
            if(winflag){
                displayMessage(200,200,"You win!");
            }
            if(saveflag2){
                displayMessage(200,200,"The game is saved!");
            }
        }
    }

    private void movementDisplay(Position pos,TETile[][] world,String string){
        while (!winflag&&!saveflag2) {
            if (StdDraw.hasNextKeyTyped()) {
                char ch = StdDraw.nextKeyTyped();
                switch (ch) {
                    case 'W':
                    case 'w': {
                        if (world[pos.getX()][pos.getY() + 1] != Tileset.WALL) {
                            world[pos.getX()][pos.getY()] = Tileset.FLOOR;
                            pos = pos.getUp();
                            if(world[pos.getX()][pos.getY()] == Tileset.LOCKED_DOOR){
                                winflag=true;
                            }
                            world[pos.getX()][pos.getY()] = Tileset.PLAYER;

                        }
                        break;
                    }
                    case 'A':
                    case 'a': {
                        if (world[pos.getX() - 1][pos.getY()] != Tileset.WALL) {
                            world[pos.getX()][pos.getY()] = Tileset.FLOOR;
                            pos = pos.getLeft();
                            if(world[pos.getX()][pos.getY()] == Tileset.LOCKED_DOOR){
                                winflag=true;
                            }
                            world[pos.getX()][pos.getY()] = Tileset.PLAYER;
                        }
                        break;
                    }
                    case 'S':
                    case 's': {
                        if (world[pos.getX()][pos.getY() - 1] != Tileset.WALL) {
                            world[pos.getX()][pos.getY()] = Tileset.FLOOR;
                            pos = pos.getDown();
                            if(world[pos.getX()][pos.getY()] == Tileset.LOCKED_DOOR){
                                winflag=true;
                            }
                            world[pos.getX()][pos.getY()] = Tileset.PLAYER;

                        }
                        break;
                    }
                    case 'D':
                    case 'd': {
                        if (world[pos.getX() + 1][pos.getY()] != Tileset.WALL) {
                            world[pos.getX()][pos.getY()] = Tileset.FLOOR;
                            pos = pos.getRight();
                            if(world[pos.getX()][pos.getY()] == Tileset.LOCKED_DOOR){
                                winflag=true;
                            }
                            world[pos.getX()][pos.getY()] = Tileset.PLAYER;

                        }
                        break;
                    }
                    case ':': {
                        saveflag1 = true;
                        break;
                    }
                    case 'Q':
                    case 'q': {
                        if (saveflag1) {
                            saveflag2=true;
                            save=string;
                            File f=new File(filepath);
                            try{
                                boolean x=f.createNewFile();
                                FileWriter f1=new FileWriter(f,false);
                                f1.write(save);
                                f1.flush();
                                f1.close();
                            }catch (Exception e){
                            }
                        }
                        break;
                    }
                }

            }
            ter.renderFrame(world);
        }
    }
    private void initialize(){
        StdDraw.clear();
        StdDraw.setCanvasSize(400,400);
        StdDraw.setXscale(0,400);
        StdDraw.setYscale(0,400);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.enableDoubleBuffering();
    }
    private void displayMessage(double x,double y,String s){
        initialize();
        StdDraw.clear();
        StdDraw.text(x,y,s);
        StdDraw.show();
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        if(input.charAt(0)=='l'||input.charAt(0)=='L'){
            input="";
            try{
                FileReader fr=new FileReader(filepath);
                int i;
                while ((i=fr.read())!=-1){
                    input+=(""+(char)i);
                }
                fr.close();
            }catch (Exception e){

            }
        }
        int length = input.length();
        int[] arr = new int[length];
        int digitLength = 0;
        long seed = 0;
        boolean flagLetter = false;
        boolean flagNumber = false;
        for (int i = 0; i < length; i += 1) {
            if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                flagLetter = true;
                break;
            }
            if (input.charAt(i) <= '9' && input.charAt(i) >= '0') {
                flagNumber = true;
                arr[digitLength] = input.charAt(i) - '0';
                digitLength += 1;
            }
        }
        if (!flagLetter && !flagNumber) {
            return null;
        }

        for (int i = 0; i < digitLength; i += 1) {
            seed += arr[i] * Math.pow(10, digitLength - 1 - i);
        }
        if (input.charAt(0) == 'n' || input.charAt(0) == 'N') {
            Random random = new Random(seed);
            TETile[][] finalWorldFrame = WorldFormer.createWorld(WIDTH, HEIGHT, random);
            if(input.charAt(length-2)==':'&&(input.charAt(length-1)=='Q'||input.charAt(length-1)=='q')){
                save=input;
                File f=new File(filepath);
                try{
                    boolean x=f.createNewFile();
                    FileWriter f1=new FileWriter(f,false);
                    f1.write(save);
                    f1.flush();
                    f1.close();
                }catch (Exception e){
                }
            }
            return finalWorldFrame;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("The Game\n\n\nNew Game(N)\nLoad Game(L)\nQuit(Q)");
    }
}
