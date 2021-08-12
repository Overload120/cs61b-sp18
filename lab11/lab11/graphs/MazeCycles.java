package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] parents;

    public MazeCycles(Maze m) {
        super(m);
        parents=new int[m.V()];
        distTo[0]=0;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        announce();
        dfs(0);
    }

    // Helper methods go here
    private void dfs(int v){
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                parents[w] = v;
                distTo[w] = distTo[v] + 1;
                dfs(w);
            }
            else {
                if(parents[v]!=w){
                    edgeTo[w]=v;
                    int temp=v;
                    while (temp!=w){
                        edgeTo[temp]=parents[temp];
                        temp=parents[temp];
                    }
                    return;
                }
            }
        }
    }
}

