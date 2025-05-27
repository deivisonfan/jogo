import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JPanel implements ActionListener, KeyListener {
    Timer timer;
    int playerX = 250;
    int playerY = 500;
    ArrayList<Integer> bulletsX = new ArrayList<>();
    ArrayList<Integer> bulletsY = new ArrayList<>();
    ArrayList<Integer> enemiesX = new ArrayList<>();
    ArrayList<Integer> enemiesY = new ArrayList<>();

    public Game() {
        timer = new Timer(15, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);

        // Gerar inimigos
        for (int i = 0; i < 5; i++) {
            enemiesX.add(50 + i * 100);
            enemiesY.add(0);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fundo
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);

        // Jogador
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, 40, 20);

        // Tiros
        g.setColor(Color.YELLOW);
        for (int i = 0; i < bulletsY.size(); i++) {
            g.fillRect(bulletsX.get(i), bulletsY.get(i), 5, 10);
        }

        // Inimigos
        g.setColor(Color.RED);
        for (int i = 0; i < enemiesX.size(); i++) {
            g.fillRect(enemiesX.get(i), enemiesY.get(i), 40, 20);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Mover tiros
        for (int i = 0; i < bulletsY.size(); i++) {
            bulletsY.set(i, bulletsY.get(i) - 5);
        }

        // Mover inimigos
        for (int i = 0; i < enemiesY.size(); i++) {
            enemiesY.set(i, enemiesY.get(i) + 1);
        }

        // Colisões
        for (int i = 0; i < bulletsY.size(); i++) {
            Rectangle bullet = new Rectangle(bulletsX.get(i), bulletsY.get(i), 5, 10);
            for (int j = 0; j < enemiesX.size(); j++) {
                Rectangle enemy = new Rectangle(enemiesX.get(j), enemiesY.get(j), 40, 20);
                if (bullet.intersects(enemy)) {
                    enemiesX.remove(j);
                    enemiesY.remove(j);
                    bulletsX.remove(i);
                    bulletsY.remove(i);
                    break;
                }
            }
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT && playerX > 0) {
            playerX -= 10;
        }
        if (k == KeyEvent.VK_RIGHT && playerX < 560) {
            playerX += 10;
        }
        if (k == KeyEvent.VK_SPACE) {
            bulletsX.add(playerX + 18);
            bulletsY.add(playerY);
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Nave Espacial");
        Game game = new Game();
        frame.add(game);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
