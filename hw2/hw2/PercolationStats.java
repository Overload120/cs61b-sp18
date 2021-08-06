package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
    private int[] arrOfThreshold;

    public PercolationStats(int N, int T, PercolationFactory pf){

    arrOfThreshold=new int[T];
    int randomRow;
    int randomCol;
    for (int i=0;i<T;i+=1){
        Percolation test=pf.make(N);
        while (!test.percolates()){
            randomRow= StdRandom.uniform(N);
            randomCol=StdRandom.uniform(N);
            test.open(randomRow,randomCol);
        }
        arrOfThreshold[i]=test.numberOfOpenSites()/(N*N);
    }
    }   // perform T independent experiments on an N-by-N grid
    public double mean(){
        return StdStats.mean(arrOfThreshold);
    }                                // sample mean of percolation threshold
    public double stddev(){
        return StdStats.stddev(arrOfThreshold);
    }                                         // sample standard deviation of percolation threshold
    public double confidenceLow(){
        return mean()-1.96*stddev()/Math.sqrt(arrOfThreshold.length);
    }                                  // low endpoint of 95% confidence interval
    public double confidenceHigh(){
        return mean()+1.96*stddev()/Math.sqrt(arrOfThreshold.length);
    }                                 // high endpoint of 95% confidence interval
}