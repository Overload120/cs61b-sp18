package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


import java.util.Random;

public class WorldFormer {
    private static class Room{
        int xRange;
        int yRange;
        Position p;
        int whichWall;
        int timesConnect;
        Room(int x,int y,Position pos){
            xRange=x;
            yRange=y;
            p=new Position(pos.getX(),pos.getY());
            whichWall=0;
            timesConnect=0;
        }
        static boolean isOverlap(Room r1,Room r2){
            boolean OverlapX=!((r1.p.getX()+r1.xRange<r2.p.getX())||(r1.p.getX()>r2.p.getX()+r2.xRange));
            boolean OverlapY=!((r1.p.getY()+r1.yRange<r2.p.getY())||(r1.p.getY()>r2.p.getY()+r2.yRange));
            return OverlapX&&OverlapY;
        }
        static boolean isOverEdge(Room r,TETile[][] world){
            return (r.p.getX()+r.xRange)>=world.length || (r.p.getY()+r.yRange)>=world[0].length ||r.p.getX()<0||r.p.getY()<0;
        }
        static boolean isOver(int pos,Room[] arr,TETile[][] world){
            if(isOverEdge(arr[pos],world)){
                return true;
            }
            if(pos==0){
                return false;
            }
            for(int j=0;j<pos;j+=1){
                if(isOverlap(arr[pos],arr[j])){
                    return true;
                }
            }

            return false;
        }

        /**     0
         *    3   1
         *      2
         * @param
         * @return
         */
        Position formRandomPosition(Random random){
            whichWall=RandomUtils.uniform(random,4);
            Position whichPos;
            switch (whichWall){
                case 0:
                    whichPos=new Position(p.getX()+RandomUtils.uniform(random,1,xRange-1),p.getY()+yRange-1);break;
                case 1:
                    whichPos=new Position(p.getX()+xRange-1,p.getY()+RandomUtils.uniform(random,1,yRange-1));break;
                case 2:
                    whichPos=new Position(p.getX()+RandomUtils.uniform(random,1,xRange-1),p.getY());break;
                default:
                    whichPos=new Position(p.getX(),p.getY()+RandomUtils.uniform(random,1,yRange-1));
            }
            return whichPos;
        }
    }

    private static void formRoom(Room r, TETile index, TETile[][] world){
        for(int i=r.p.getY(); i<r.p.getY()+r.yRange;i+=1){
            for(int j=r.p.getX();j<r.p.getX()+r.xRange;j+=1){
                world[j][i]= index;
            }
        }
    }
    private static void formRoomWithEdge(Room r, TETile[][] world){
        if(r==null){
            return;
        }
        Position p1=new Position(r.p.getX()+1,r.p.getY()+1);
        Room newRoom =new Room(r.xRange-2,r.yRange-2,p1);
        formRoom(r,Tileset.WALL,world);
        formRoom(newRoom,Tileset.FLOOR,world);
    }
    private static void connect(Random random, Room r1, Room r2, TETile[][] world){
        Position p1;
        do{
            p1=r1.formRandomPosition(random);
        }while (p1.isOnEdge(world));
        Position p2;
        do{
            p2=r2.formRandomPosition(random);
        }while (p2.isOnEdge(world));
        formHallway(p1,p2,world);
    }
    private static void formHallway(Position p1, Position p2, TETile[][] world){
        if(p1.getX()==p2.getX()){
            for(int i=Math.min(p1.getY(),p2.getY());i<=Math.max(p1.getY(),p2.getY());++i){
                world[p1.getX()][i]=Tileset.FLOOR;
            }
            return;
        }
        if(p1.getY()==p2.getY()){
            for(int i=Math.min(p1.getX(),p2.getX());i<=Math.max(p1.getX(),p2.getX());++i){
                world[i][p1.getY()]=Tileset.FLOOR;
            }
            return;
        }
        for (int i = Math.min(p1.getY(), p2.getY()); i <= Math.max(p1.getY(), p2.getY()); ++i) {
            world[p1.getX()][i] = Tileset.FLOOR;
        }
        for (int i = Math.min(p1.getX(), p2.getX()); i <= Math.max(p1.getX(), p2.getX()); ++i) {
            world[i][p2.getY()] = Tileset.FLOOR;
        }
        }
    private static Room[] RandomRooms(Random random, TETile[][] world){
        int randomX;
        int randomY;
        Position randomP;
        int randomXrange;
        int randomYrange;
        int randomSize=RandomUtils.uniform(random,20,25);
        Room[] arr=new Room[randomSize];
        for(int i=0;i<randomSize;i+=1){
            do{
                randomX=RandomUtils.uniform(random,0,world.length);
                randomY=RandomUtils.uniform(random,0,world[0].length-1);
                randomP=new Position(randomX,randomY);
                randomXrange=RandomUtils.uniform(random,4,11);
                randomYrange=RandomUtils.uniform(random,4,11);
                arr[i]=new Room(randomXrange,randomYrange,randomP);
            }while (Room.isOver(i,arr,world));
        }

        return arr;
    }
    private static void formRandomRooms(Random random,TETile[][] world){
        Room[] arr=RandomRooms(random,world);
        int randomNum;
        for(int i=0;i<arr.length;i+=1){
            formRoomWithEdge(arr[i],world);
            if(arr[i].timesConnect>3){
                continue;
            }
            randomNum=RandomUtils.uniform(random,i,arr.length);
            connect(random,arr[i],arr[randomNum],world);
            arr[i].timesConnect+=1;
            arr[randomNum].timesConnect+=1;
        }
        for(int i=arr.length-1;i>=0;i-=1){
            if(arr[i].timesConnect>3){
                continue;
            }
            randomNum=-1*RandomUtils.uniform(random,-1*i,1);
            connect(random,arr[i],arr[randomNum],world);
            arr[i].timesConnect+=1;
            arr[randomNum].timesConnect+=1;
        }

    }
    private static void fillWalls(TETile[][] world){
        Position currentP;
        for(int i=0;i<world.length;++i){
            for(int j=0;j<world[0].length;++j){
                if(world[i][j]!=Tileset.NOTHING){
                    continue;
                }
                currentP=new Position(i,j);
                if(currentP.isExistAround(Tileset.FLOOR,world)){
                    world[i][j]=Tileset.WALL;
                }
            }
        }
    }
    private static void clearDeadEnds(TETile[][] world){
        boolean flag=false;
        while (!flag){
            for(int i=0;i<world.length;++i){
                for(int j=0;j<world[0].length;++j){
                    if(!new Position(i,j).isDeadEnd(world)){
                        flag=true;
                        continue;
                    }
                    world[i][j]=Tileset.WALL;
                    flag=false;
                }
            }
        }

    }
    private static void clearInnerWalls(TETile[][] world){
        for(int i=0;i<world.length;++i){
            for(int j=0;j<world[0].length;++j){
                if(world[i][j]!=Tileset.WALL){
                    continue;
                }
                if(!new Position(i,j).isExistAround(Tileset.FLOOR,world)){
                    world[i][j]=Tileset.NOTHING;
                }
            }
        }
    }
    private static void formRandomPlayer(Random random,TETile[][] world){
        int X;
        int Y;
        do{
            X=RandomUtils.uniform(random,world.length);
            Y=RandomUtils.uniform(random,world[0].length);
        }while (world[X][Y]!=Tileset.FLOOR);
        world[X][Y]=Tileset.PLAYER;
    }
    private static void formRandomDoor(Random random,TETile[][] world){
        int X;
        int Y;
        do{
            X=RandomUtils.uniform(random,world.length);
            Y=RandomUtils.uniform(random,world[0].length);
        }while (world[X][Y]!=Tileset.WALL);
        world[X][Y]=Tileset.LOCKED_DOOR;
    }
    public static TETile[][] createWorld(int width,int height,Random random){
        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        formRandomRooms(random,world);
        fillWalls(world);
        clearDeadEnds(world);
        clearInnerWalls(world);
        formRandomPlayer(random,world);
        formRandomDoor(random,world);
        return world;
    }
}

