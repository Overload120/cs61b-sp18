package byog.lab5;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {


    private static TETile randomTile() {
        Random RANDOM=new Random(StdRandom.uniform(2000000000));
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WATER;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.GRASS;
            default: return Tileset.SAND;
        }
    }

    /**
     * Draws the bottom half of the hexagon.
     */
    private static void addInvert(int size, int xPos, int yPos, TETile[][] world,TETile t) {
        int currentX = xPos;
        int currentY = yPos;
        int i = 0;
        int j = 0;
        for (i = 0; i < size; i += 1) {
            for (j = 0; j < size + 2 * i; j += 1) {
                world[currentX + j][currentY] = t;
            }
            currentX -= 1;
            currentY += 1;
        }

    }

    /**
     * Draws the top half of the hexagon.
     */
    private static void addRight(int size, int xPos, int yPos, TETile[][] world,TETile t) {
        int currentX = xPos;
        int currentY = yPos;
        int i = 0;
        int j = 0;
        for (i = size - 1; i >= 0; i -= 1) {
            for (j = 0; j < size + 2 * i; j += 1) {
                world[currentX + j][currentY] = t;
            }
            currentX += 1;
            currentY += 1;
        }
    }

    private static void addHexagon(int size, int xPos, int yPos, TETile[][] world, TETile t) {
        if (size < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        addInvert(size, xPos, yPos, world,t);
        addRight(size, xPos - size + 1, yPos + size, world,t);
    }

    private static void addHexRow(int size, int xPos, int yPos, TETile[][] world){
        int xRange=world.length;
        int yRange=world[0].length;
        int currentX=xPos;
        int currentY=yPos;
        boolean flagX=currentX>size+1 && currentX<xRange-2*size-1;
        boolean flagY=currentY>=0 && currentY<yRange-2*size-1;
        while (flagX && flagY){
            TETile currentT=randomTile();
            addHexagon(size,currentX,currentY,world,currentT);
            currentX=currentX-2*size+1;
            currentY+=size;
            flagX=currentX>size+1 && currentX<xRange-2*size-1;
            flagY=currentY>=0 && currentY<yRange-2*size-1;
        }
    }

    public static void addHexWorld(int size, int xPos, int yPos, TETile[][] world){
        int xRange=world.length;
        int yRange=world[0].length;
        int currentX=xPos;
        int currentY=yPos;
        boolean flagX=currentX>=size && currentX<xRange-size-1;
        boolean flagY=currentY>=0 && currentY<yRange-2*size;
        while (flagX && flagY){
            addHexRow(size,currentX,currentY,world);
            currentX=currentX+2*size-1;
            currentY+=size;
            flagX=currentX>size+1 && currentX<xRange-2*size-1;
            flagY=currentY>=0 && currentY<yRange-2*size-1;
        }
        if(!flagX){
            currentX=currentX-2*size+1;
            currentY+=size;
            while(flagY){
                addHexRow(size,currentX,currentY,world);
                currentY+=2*size;
                flagY=currentY>=0 && currentY<yRange-2*size-1;
            }
        }

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(40, 40);

        // initialize tiles
        TETile[][] world = new TETile[40][40];
        for (int x = 0; x < 40; x += 1) {
            for (int y = 0; y < 40; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addHexWorld(3,20,0,world);
        ter.renderFrame(world);
    }
}
