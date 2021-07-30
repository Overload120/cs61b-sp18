package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static String save;
    private static String filepath="F:/code/cs61b-sp18/proj2/save.txt";
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
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
}
