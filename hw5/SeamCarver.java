import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture p;
    private double[][] minCostEnding;

    public SeamCarver(Picture picture) {
        p = picture;
    }

    public Picture picture() {
        return p;
    }                       // current picture

    public int width() {
        return p.width();
    }                         // width of current picture

    public int height() {
        return p.height();
    }                        // height of current picture

    public double energy(int x, int y) {
        if (x >= width() || y >= height() || x < 0 || y < 0) {
            throw new IndexOutOfBoundsException();
        }
        double xGradient;
        double yGradient;
        int r1, r2, b1, b2, g1, g2;
        if (width() > 1) {
            if (x == 0) {
                r1 = p.get(width() - 1, y).getRed();
                r2 = p.get(x + 1, y).getRed();
                b1 = p.get(width() - 1, y).getBlue();
                b2 = p.get(x + 1, y).getBlue();
                g1 = p.get(width() - 1, y).getGreen();
                g2 = p.get(x + 1, y).getGreen();
            } else if (x == width() - 1) {
                r1 = p.get(x - 1, y).getRed();
                r2 = p.get(0, y).getRed();
                b1 = p.get(x - 1, y).getBlue();
                b2 = p.get(0, y).getBlue();
                g1 = p.get(x - 1, y).getGreen();
                g2 = p.get(0, y).getGreen();
            } else {
                r1 = p.get(x - 1, y).getRed();
                r2 = p.get(x + 1, y).getRed();
                b1 = p.get(x - 1, y).getBlue();
                b2 = p.get(x + 1, y).getBlue();
                g1 = p.get(x - 1, y).getGreen();
                g2 = p.get(x + 1, y).getGreen();
            }
            yGradient = (r1 - r2) * (r1 - r2) + (b1 - b2) * (b1 - b2) + (g1 - g2) * (g1 - g2);
        } else yGradient = 0;
        if (height() > 1) {
            if (y == 0) {
                r1 = p.get(x, height() - 1).getRed();
                r2 = p.get(x, y + 1).getRed();
                b1 = p.get(x, height() - 1).getBlue();
                b2 = p.get(x, y + 1).getBlue();
                g1 = p.get(x, height() - 1).getGreen();
                g2 = p.get(x, y + 1).getGreen();
            } else if (y == height() - 1) {
                r1 = p.get(x, y - 1).getRed();
                r2 = p.get(x, 0).getRed();
                b1 = p.get(x, y - 1).getBlue();
                b2 = p.get(x, 0).getBlue();
                g1 = p.get(x, y - 1).getGreen();
                g2 = p.get(x, 0).getGreen();
            } else {
                r1 = p.get(x, y - 1).getRed();
                r2 = p.get(x, y + 1).getRed();
                b1 = p.get(x, y - 1).getBlue();
                b2 = p.get(x, y + 1).getBlue();
                g1 = p.get(x, y - 1).getGreen();
                g2 = p.get(x, y + 1).getGreen();
            }
            xGradient = (r1 - r2) * (r1 - r2) + (b1 - b2) * (b1 - b2) + (g1 - g2) * (g1 - g2);
        } else xGradient = 0;
        return xGradient + yGradient;
    }            // energy of pixel at column x and row y

    public int[] findHorizontalSeam() {
        Picture original = new Picture(p);
        p = new Picture(original.height(), original.width());
        for (int w = 0; w < p.width(); w += 1) {
            for (int h = 0; h < p.height(); h += 1) {
                p.set(w, h, original.get(h, w));
            }
        }
        int[] target = findVerticalSeam();
        p = new Picture(original);
        return target;
    }            // sequence of indices for horizontal seam

    public int[] findVerticalSeam() {
        int[] target = new int[height()];
        minimumCostVertical();
        double min = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < width(); i += 1) {
            if (minCostEnding[height() - 1][i] < min) {
                min = minCostEnding[height() - 1][i];
                minIndex = i;
            }
        }
        for (int i = height() - 1; i >= 0; i -= 1) {
            target[i] = minIndex;
            if (i != 0) {
                if (minIndex == 0) {
                    minIndex = findMinIndex(i - 1, minIndex, minIndex + 1);
                } else if (minIndex == width() - 1) {
                    minIndex = findMinIndex(i - 1, minIndex, minIndex - 1);
                } else {
                    int a = findMinIndex(i - 1, minIndex, minIndex - 1);
                    minIndex = findMinIndex(i - 1, a, minIndex + 1);
                }
            }
        }
        return target;
    }              // sequence of indices for vertical seam

    public void removeHorizontalSeam(int[] seam) {
        p = new Picture(SeamRemover.removeHorizontalSeam(p, seam));
    }   // remove horizontal seam from picture

    public void removeVerticalSeam(int[] seam) {
        p = new Picture(SeamRemover.removeVerticalSeam(p, seam));
    }     // remove vertical seam from picture

    private void minimumCostVertical() {
        minCostEnding = new double[height()][width()];

        for (int i = 0; i < height(); i += 1) {
            for (int j = 0; j < width(); j += 1) {
                if (i == 0) {
                    minCostEnding[i][j] = energy(j, i);
                } else if (j != 0 && j != width() - 1) {
                    minCostEnding[i][j] = energy(j, i) + Math.min(Math.min(minCostEnding[i - 1][j - 1], minCostEnding[i - 1][j]), minCostEnding[i - 1][j + 1]);
                } else if (j == 0) {
                    minCostEnding[i][j] = energy(j, i) + Math.min(minCostEnding[i - 1][j], minCostEnding[i - 1][j + 1]);
                } else if (j == width() - 1) {
                    minCostEnding[i][j] = energy(j, i) + Math.min(minCostEnding[i - 1][j], minCostEnding[i - 1][j - 1]);
                }
            }
        }
    }

    private int findMinIndex(int row, int a, int b) {
        if (minCostEnding[row][a] < minCostEnding[row][b])
            return a;
        else return b;
    }
}