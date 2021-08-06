package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
    private int[] arrOfThreshold;

    public PercolationStats(int N, int T, PercolationFactory pf){
    Percolation test=pf.make(N);
    arrOfThreshold=new int[T];
    int randomRow;
    int randomCol;
    int threshold;
    for (int i=0;i<T;i+=1){
        threshold=0;
        while (!test.percolates()){
            randomRow= StdRandom.uniform(N);
            randomCol=StdRandom.uniform(N);
            test.open(randomRow,randomCol);
            threshold+=1;
        }
        arrOfThreshold[i]=threshold;
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