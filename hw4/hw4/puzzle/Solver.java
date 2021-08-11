package hw4.puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private static class SearchNode implements Comparable<SearchNode>{
        WorldState currentState;
        int numOfMoves;
        SearchNode previous;
        SearchNode(WorldState ws,int n,SearchNode sn){
            currentState=ws;
            numOfMoves=n;
            previous=sn;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.numOfMoves+this.currentState.estimatedDistanceToGoal()- (o.numOfMoves+o.currentState.estimatedDistanceToGoal());
        }
    }
    private List<WorldState> bestPath;
    private int move;

    public Solver(WorldState initial){
        SearchNode initialNode=new SearchNode(initial,0,null);
        bestPath=new ArrayList<>();
        MinPQ<SearchNode> minPQ=new MinPQ<>();
        minPQ.insert(initialNode);
        SearchNode temp=initialNode;
        int curMoves;
        while (!temp.currentState.isGoal()){
            temp=minPQ.delMin();
            for (WorldState cur:temp.currentState.neighbors()){
                if(temp.previous!=null&&cur.equals(temp.previous.currentState)){
                    continue;
                }
                curMoves=temp.numOfMoves+1;
                SearchNode newNode=new SearchNode(cur,curMoves,temp);
                minPQ.insert(newNode);
            }
        }
        move=temp.numOfMoves;
        Stack<WorldState> cur=new Stack<>();
        while (temp!=null){
            cur.push(temp.currentState);
            temp=temp.previous;
        }
        while (!cur.isEmpty()){
            bestPath.add(cur.pop());
        }
    }
    public int moves(){
        return move;
    }
    public Iterable<WorldState> solution(){
        return bestPath;
    }

}
