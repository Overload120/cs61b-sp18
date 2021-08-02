package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40,seed);
        game.startGame();
    }

    public MemoryGame(int width, int height,int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the bottom left is (0,0) and the top right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand=new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String str="";
        int randomNum;
        for (int i=0;i<n;++i){
            randomNum=rand.nextInt(26);
            str+=(""+CHARACTERS[randomNum]);
        }
        return str;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(this.width*0.5,this.height*0.5,s);
        StdDraw.show();
        if(!gameOver){
            StdDraw.text(this.width*0.2,this.height*0.9,"Round:"+round);
            StdDraw.text(this.width*0.5,this.height*0.9,"Watch!");
            StdDraw.text(this.width*0.8,this.height*0.9,ENCOURAGEMENT[rand.nextInt(7)]);
            StdDraw.show();
        }
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters

        for(int i=0;i<letters.length();++i){
            StdDraw.clear();
            StdDraw.pause(500);
            StdDraw.text(this.width*0.5,this.height*0.5,""+letters.charAt(i));
            StdDraw.show();
            StdDraw.pause(1000);
        }
        StdDraw.clear();
        StdDraw.show();
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        int count=0;
        String str="";
        while (count<n){
            if(!playerTurn){
                continue;
            }
            if(StdDraw.hasNextKeyTyped()){
                count+=1;
                char ch=StdDraw.nextKeyTyped();
                str+=""+ch;
                StdDraw.clear();
                StdDraw.text(this.width*0.5,this.height*0.5,str);
                StdDraw.show();
            }
        }
        return str;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round=1;
        gameOver=false;
        playerTurn=false;
        //TODO: Establish Game loop
        while (!gameOver){
            playerTurn=false;
            drawFrame("Round:"+round);
            String str=generateRandomString(round);
            flashSequence(str);
            playerTurn=true;
            String player=solicitNCharsInput(round);
            StdDraw.pause(500);
            if(str.equals(player)){
                round++;
                continue;
            }
            gameOver=true;
            drawFrame("Game Over! You made it to round:"+round);
        }
    }

}
