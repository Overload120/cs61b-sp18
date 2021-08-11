package hw4.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Board implements WorldState{
    private int[][] board;
    private int[][] goal;
    private int size;
    public Board(int[][] tiles){
        size=tiles.length;
        board=new int[size][size];
        goal=new int[size][size];
        for (int i=0;i<size;i+=1){
            System.arraycopy(tiles[i], 0, board[i], 0, size);
        }
        int n=1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goal[i][j]=n;
                n++;
            }
        }
        goal[size-1][size-1]=0;
    }
    public int tileAt(int i, int j){
        if(!(i>=0&&i<size&&j>=0&&j<size)){
            throw new IndexOutOfBoundsException();
        }
        return board[i][j];
    }
    public int size(){
        return size;
    }
    public Iterable<WorldState> neighbors(){
        boolean isYout;
        boolean isXout;
        boolean flag=false;
        int i,j=0;
        List<WorldState> list=new ArrayList<>();
        int[][] newBoard=new int[size][size];
        for (i=0;i<size;i+=1){
            for (j=0;j<size;j+=1){
                flag=board[i][j]==0;
                if(flag)break;
            }
            if(flag)break;
        }
        for (int k=0;k<2;k+=1){
            int cur=k*2-1;
            isXout=cur+i<0||cur+i>=size;
            if(!isXout){
                for (int l=0;l<size;l+=1){
                    System.arraycopy(board[l], 0, newBoard[l], 0, size);
                }
                newBoard[i][j]=newBoard[cur+i][j];
                newBoard[cur+i][j]=0;
                list.add(new Board(newBoard));
            }
        }
        for (int k=0;k<2;k+=1){
            int cur=k*2-1;
            isYout=cur+j<0||cur+j>=size;
            if(!isYout){
                for (int l=0;l<size;l+=1){
                    System.arraycopy(board[l], 0, newBoard[l], 0, size);
                }
                newBoard[i][j]=newBoard[i][cur+j];
                newBoard[i][cur+j]=0;
                list.add(new Board(newBoard));
            }
        }
        return list;
    }
    public int hamming(){
        int count=0;
        for (int i=0;i<size;i+=1){
            for (int j=0;j<size;j+=1){
                if(board[i][j]!=goal[i][j]&&board[i][j]!=0)
                    count+=1;
            }
        }
        return count;
    }
    public int manhattan(){
        int count=0;
        for (int i=0;i<size;i+=1){
            for (int j=0;j<size;j+=1){
                if(board[i][j]!=goal[i][j]&&board[i][j]!=0){
                    int k=i-(board[i][j]-1)/size;
                    int l=j-(board[i][j]-1)%size;
                    count+=(Math.abs(k)+Math.abs(l));
                }
            }
        }
        return count;
    }

    public int estimatedDistanceToGoal(){
        return manhattan();
    }
    public boolean equals(Object y){
        if(y==this)return true;
        if(y==null||this.getClass()!=y.getClass()){
            return false;
        }

        if(this.size!=((Board)y).size) return false;
        for (int i=0;i<size;i+=1){
            for (int j=0;j<size;j+=1){
                if(board[i][j]!=((Board)y).board[i][j]) return false;
            }
        }
        return true;
    }


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] arr={{0,1,3},{4,2,5},{7,8,6}};
        Board b=new Board(arr);
        System.out.println(b);
        System.out.println(b.hamming());
        System.out.println(b.estimatedDistanceToGoal());
    }
}
