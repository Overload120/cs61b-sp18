package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private class Node implements Comparable<Node>{
        int curV;
        int priority;
        Node parent;
        Node(int v,Node p){
            curV=v;
            parent=p;
            priority=distTo[v]+h(v);
        }
        @Override
        public int compareTo(Node o) {
            return this.priority-o.priority;
        }
        @Override
        public boolean equals(Object o){
            if(o==this)return true;
            if(o==null||this.getClass()!=o.getClass()){
                return false;
            }
            return this.curV==((Node)o).curV;
        }
    }
    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v)-maze.toX(t))+Math.abs(maze.toY(v)-maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        marked[s]=true;
        announce();
        Queue<Node> fringe=new PriorityQueue<>();
        Node temp=new Node(s,null);
        fringe.add(temp);
        while (temp.curV!=t&&!fringe.isEmpty()){
            temp=fringe.poll();
            for(int w:maze.adj(temp.curV)){
                if(temp.parent!=null&&w==temp.parent.curV) continue;
                distTo[w]=distTo[temp.curV]+1;
                Node cur=new Node(w,temp);
                if(!fringe.contains(cur)){
                    fringe.add(cur);
                }
                else {
                    fringe.remove(cur);
                    fringe.add(cur);
                }
            }
        }
        Stack<Node> a=new Stack<>();
        while (temp.curV!=s){
            a.push(temp);
            temp=temp.parent;
        }
        while (!a.isEmpty()){
            temp=a.pop();
            marked[temp.curV]=true;
            edgeTo[temp.curV]=temp.parent.curV;
            announce();
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

