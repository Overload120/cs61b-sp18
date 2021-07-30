package byog.Core;


import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Position {
    private int x;
    private int y;
    public Position(int xPos,int yPos){
        x=xPos;
        y=yPos;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Position getLeft(){
        return new Position(x-1,y);
    }
    public Position getRight(){
        return new Position(x+1,y);
    }
    public Position getUp(){
        return new Position(x,y+1);
    }
    public Position getDown(){
        return new Position(x,y-1);
    }
    public boolean isExistAround(TETile t,TETile[][] world){
        boolean xRange=x>0&&x<world.length-1;
        boolean yRange=y>0&&y<world[0].length-1;
        if(x==world.length-1 && y==world[0].length-1){
            return world[x-1][y]==t || world[x][y-1]==t;
        }
        else if(x==world.length-1&&yRange){
            return world[x-1][y]==t || world[x][y+1]==t || world[x][y-1]==t;
        }
        else if(y==world[0].length-1&&xRange){
            return world[x+1][y]==t || world[x-1][y]==t || world[x][y-1]==t;
        }
        if(x==0&&y==0){
            return world[x+1][y]==t ||world[x][y+1]==t;
        }
        else if(x==0&&yRange){
            return world[x+1][y]==t || world[x][y+1]==t || world[x][y-1]==t;
        }
        else if(y==0&&xRange){
            return world[x+1][y]==t || world[x-1][y]==t || world[x][y+1]==t;
        }
        if(xRange&&yRange){
            return world[x+1][y]==t || world[x-1][y]==t || world[x][y+1]==t || world[x][y-1]==t;
        }
            return false;
    }
    public int wallsCount(TETile[][] world){
        if(world[x][y]!= Tileset.FLOOR){
            return -1;
        }
        int count=0;
        if(world[x-1][y]==Tileset.WALL){
            count+=1;
        }
        if(world[x+1][y]==Tileset.WALL){
            count+=1;
        }
        if(world[x][y+1]==Tileset.WALL){
            count+=1;
        }
        if(world[x][y-1]==Tileset.WALL){
            count+=1;
        }
        return count;
    }
    public boolean isOnEdge(TETile[][] world){
        return x==world.length-1 || x==0 ||y==world[0].length-1 ||y==0;
    }
    public boolean isDeadEnd(TETile[][] world){
        return wallsCount(world)>=3;
    }
}
