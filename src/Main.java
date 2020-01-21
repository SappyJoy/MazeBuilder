import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JPanel {
    private static int n;
    private static Labirint lab;
    private static Labirint preLab;
    private static final int PX = 10;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!lab.square[i][j]) {
                    g.setColor(Color.GREEN);
                    g.fillRect(i*PX, j*PX, PX, PX);
                    g.setColor(Color.BLACK);
                    // g.drawRect(i*PX, j*PX, PX, PX);
                } else {
                    g.setColor(Color.BLACK);
                    // g.drawRect(i*PX, j*PX, PX, PX);
                }
            }
        }
        g.setColor(Color.RED);
        g.drawString("MIN PATH: " + lab.minPath, 10, 10);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PX*n, PX*n);
    }

    private static void createAndShowGui() {
        Main mainPanel = new Main();

        JFrame f = new JFrame("Labirint3000");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(mainPanel);
        f.pack();
        f.setLocationByPlatform(true);
        f.setVisible(true);
    }


    public static void main(String[] args) throws CloneNotSupportedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter even n, m and numHoles: ");
        n = sc.nextInt();
        int m = sc.nextInt();
        int numHoles = sc.nextInt();
        System.out.println();
        Main labs = new Main();
        //int m = 14;
        //int numHoles = 10;
        //n = 50;
        lab = new Labirint(n, m);
        while (lab.minPath == Integer.MAX_VALUE) {
            lab.init();
            lab.randLab(numHoles);
            lab = doDFS(n, m, numHoles);
        }


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }


    public static Labirint doDFS(int n, int m, int numHoles) {
        lab.show();
        ArrayList<Integer> pathLens = lab.DFSForAll();
        int min = Integer.MAX_VALUE;
        for (int l : pathLens) {
            min = Integer.min(min, l);
        }
            /*if (min == Integer.MAX_VALUE) {
                n -= 2;
                continue;
            }*/
        System.out.println("n=" + n + ", m=" + m +", numHoles=" + numHoles);
        System.out.println("min=" + min);
        return lab;
    }
}
