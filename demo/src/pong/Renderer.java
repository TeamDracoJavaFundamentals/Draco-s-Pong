package pong;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import pong.Pong;

public class Renderer extends JPanel {
    private static final long serialVersionUID = 1L; // konstanta, ponqkoga java prosto q iziskva ina4e dava gre6ka ne e ne6to ot zna4enie

    public Renderer() { // constructor na klasa
    }

    protected void paintComponent(Graphics g) { // za tova ne znam kak to4no da go obqsnq ama sluji za izrisuvaneto
        super.paintComponent(g);
        Pong.pong.render((Graphics2D)g);
    }
}