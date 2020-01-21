import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Labirint {
    /*
        Class for square mazes
     */
    boolean[][] square;
    private int n, m;
    int minPath = Integer.MAX_VALUE;


    public Labirint(int n, int m) {
        square = new boolean[n][n];
        this.n = n;
        this.m = m;
        init();
    }

    public void randLab(int numExits) {
        // Generate random points. Similar points can't stay together
        Random rand = new Random();
        for (int j = 0; j < numExits; j++) {
            int line = n;
            for (int i = 0; i <= (n - m) / 2; i += 2) {
                // Deleting points from rows
                int r = (int) (rand.nextDouble()*(line*4-4));
                if (r < line - 1) {
                    if (checkField(i, r) && square[i][r])
                        square[i][r] = false;
                    else {
                        line += 2;
                        i -= 2;
                    }
                } else if (r < line * 2 - 1) {
                    if (checkField(r-line+1, n-1-i) && square[r - line + 1][n - 1 - i])
                        square[r - line + 1][n - 1 - i] = false;
                    else {
                        line += 2;
                        i -= 2;
                    }
                } else if (r < line * 3 - 2) {
                    if (checkField(n-1-i, r-2*line+1) && square[n - 1 - i][r - 2 * line + 1])
                        square[n - 1 - i][r - 2 * line + 1] = false;
                    else {
                        line += 2;
                        i -= 2;
                    }
                } else {
                    if (checkField(r-3*line+2, i) && square[r - 3 * line + 3][i])
                        square[r - 3 * line + 2][i] = false;
                    else {
                        line += 2;
                        i -= 2;
                    }
                }
                line -= 2;
            }

            line = n - 2;
            for (int i = 1; i < (n-m)/2; i += 2) {
                // Add random points into rows
                int r = (int) (rand.nextDouble()*(line*4-4));
                if (r < line - 1) {
                    if (checkField(i, r) && !square[i][r])
                        square[i][r] = true;
                    else {
                        line += 2;
                        i -= 2;
                    }
                } else if (r < line * 2 - 1) {
                    if (checkField(r-line+1, n-1-i) && !square[r - line + 1][n - 1 - i])
                        square[r - line + 1][n - 1 - i] = true;
                    else {
                        line += 2;
                        i -= 2;
                    }
                } else if (r < line * 3 - 2) {
                    if (checkField(n-1-i, r-2*line+1) && !square[n - 1 - i][r - 2 * line + 1])
                        square[n - 1 - i][r - 2 * line + 1] = true;
                    else {
                        line += 2;
                        i -= 2;
                    }
                } else {
                    if (checkField(r-3*line+2, i) && !square[r - 3 * line + 3][i])
                        square[r - 3 * line + 2][i] = true;
                    else {
                        line += 2;
                        i -= 2;
                    }
                }
                line -= 2;
            }
        }
    }

    public boolean checkField(int x, int y) {
        // check if here too many similar points together
        boolean sign = !square[x][y];
        int numSim = 0;
        if (x > 0) {
            if (square[x - 1][y] == sign)
                numSim++;
        } else
            numSim++;
        if (x < n-1) {
            if (square[x + 1][y] == sign)
                numSim++;
        } else
            numSim++;
        if (y > 0) {
            if (square[x][y - 1] == sign)
                numSim++;
        } else
            numSim++;
        if (y < n-1) {
            if (square[x][y + 1] == sign)
                numSim++;
        } else
            numSim++;

        return numSim <= 2;
    }

    public void init() {
        // Initialize maze
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((((i < (n-m)/2+1)||(i > (n+m)/2-2)) ||
                        ((j < (n-m)/2+1)||(j > (n+m)/2-2))))
                    if (((i % 2 == 0) && ((j >= i) && (j <= n-i-1)) || ((i % 2 == 1) && ((j >= n-i-1) && (j <= i)))) ||
                            ((j % 2 == 0) && ((i >= j) && (i <= n-j-1)) || ((j % 2 == 1) && ((i >= n-j-1) && (i <= j)))))
                        square[i][j] = true;
                    else
                        square[i][j] = false;
            }
        }
    }

    public void show() {
        // Describe in text form
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (square[i][j])
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void makeAdditionCircle(boolean ch) {
        // Make one circle into centre of maze to optimize DFS
        for (int i = 0; i < (m-2); i++) {
            square[(n - m) / 2 + 1][(n - m) / 2 + 1 + i] = ch;
            square[n - (n - m) / 2 - 2][(n - m) / 2 + 1 + i] = ch;
        }
        for (int i = 0; i < (m-2); i++) {
            square[(n - m) / 2 + 1 + i][(n - m) / 2 + 1] = ch;
            square[(n - m) / 2 + 1 + i][n - (n - m) / 2 - 2] = ch;
        }
    }

    public ArrayList<Integer> DFSForAll() {
        // Starts DFS from every entree
        ArrayList<Integer> Len = new ArrayList<>();

        makeAdditionCircle(true);

        for (int i = 0; i < (m-2); i++) {
            int x = (n-m)/2;
            int y = (n-m)/2+1+i;
            if (!square[x][y])
                Len.add(DFS(x, y, 0, square));
            if (!square[x+m-1][y])
                Len.add(DFS(x+m-1, y, 0, square));
        }
        for (int i = 0; i < (m-2); i++) {
            int x = (n-m)/2+1+i;
            int y = (n-m)/2;
            if (!square[x][y]) {
                Len.add(DFS(x, y, 0, square));
            }
            if (!square[x][y+m-1])
                Len.add(DFS(x, y+m-1, 0, square));
        }

        makeAdditionCircle(false);

        return Len;
    }

    public int DFS(int x, int y, int lenPath, boolean[][] mySquare) {
        //Just DFS
        if (lenPath >= minPath)
            return lenPath;
        if ((x == 0) || (y == 0) || (x == n-1) || (y == n-1)) {
            minPath = lenPath;
            return lenPath;
        }

        int ans = Integer.MAX_VALUE;
        mySquare[x][y] = true;


        if (!mySquare[x-1][y])
            ans = Integer.min(ans, DFS(x-1, y, lenPath+1, mySquare));
        if (!mySquare[x+1][y])
            ans = Integer.min(ans, DFS(x+1, y, lenPath+1, mySquare));
        if (!mySquare[x][y-1])
            ans = Integer.min(ans, DFS(x, y-1, lenPath+1, mySquare));
        if (!mySquare[x][y+1])
            ans = Integer.min(ans, DFS(x, y+1, lenPath+1, mySquare));

        mySquare[x][y] = false;

        return ans;
    }
}
