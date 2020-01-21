import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JPanel {
    private static int n;
    private static int m;
    private static int numHoles;
    private static Labirint lab;
    private static final int PX = 8;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!lab.square[i][j]) {
                    g.setColor(Color.BLACK);
                    g.fillRect(i*PX, j*PX, PX, PX);
                    g.setColor(Color.BLACK);
                    // g.drawRect(i*PX, j*PX, PX, PX);
                } else {
                    g.setColor(Color.MAGENTA);
                    // g.drawRect(i*PX, j*PX, PX, PX);
                    g.fillRect(i*PX, j*PX, PX, PX);
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

        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    lab = new Labirint(n, m);
                    while (lab.minPath == Integer.MAX_VALUE) {
                        makeNewMaze(n, m, numHoles);
                    }
                    f.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter even n, m and numHoles: ");
        n = sc.nextInt();
        m = sc.nextInt();
        numHoles = sc.nextInt();
        System.out.println();
        lab = new Labirint(n, m);
        while (lab.minPath == Integer.MAX_VALUE) {
            makeNewMaze(n, m, numHoles);
        }


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }

    private static void makeNewMaze(int n, int m, int numHoles) {
        lab.init();
        lab.randLab(numHoles);
        lab = doDFS(n, m, numHoles);
    }


    private static Labirint doDFS(int n, int m, int numHoles) {
        lab.show();
        ArrayList<Integer> pathLens = lab.DFSForAll();
        int min = Integer.MAX_VALUE;
        for (int l : pathLens) {
            min = Integer.min(min, l);
        }
        System.out.println("n=" + n + ", m=" + m +", numHoles=" + numHoles);
        System.out.println("min=" + min);
        return lab;
    }
}
