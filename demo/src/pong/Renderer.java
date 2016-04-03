package pong;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import pong.Pong;

public class Renderer extends JPanel {
    private static final long serialVersionUID = 1L;

    public Renderer() {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Pong.pong.render((Graphics2D)g);
    }
}