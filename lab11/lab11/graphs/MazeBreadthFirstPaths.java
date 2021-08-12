package lab11.graphs;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int sourceV;
    private int targetV;
    private boolean findFlag;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        findFlag=false;
        sourceV=m.xyTo1D(sourceX,sourceY);
        targetV=m.xyTo1D(targetX,targetY);
        distTo[sourceV]=0;
        edgeTo[sourceV]=sourceV;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        marked[sourceV]=true;
        announce();
        if(sourceV==targetV){
            findFlag=true;
            return;
        }
        Queue<Integer> fringe=new ArrayBlockingQueue<>(maze.V());
        fringe.add(sourceV);
        while (!fringe.isEmpty()){
            int curV=fringe.remove();
            for (int w:maze.adj(curV)){
                if(!marked[w]){
                    fringe.add(w);
                    marked[w]=true;
                    edgeTo[w]=curV;
                    distTo[w]=distTo[curV]+1;
                    announce();
                    if(w==targetV){
                        findFlag=true;
                    }
                    if (findFlag){
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

