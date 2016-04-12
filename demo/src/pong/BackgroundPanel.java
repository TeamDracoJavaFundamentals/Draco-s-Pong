package pong;

import java.awt.*;


class BackgroundPanel extends Panel {
    // The Image to store the background image in.
    Image img;
    Image imgPaddle;
    Image imgBall;
    Image imgBonus1;
    Image imgBonus2;
    Image imgBonus3;

    public BackgroundPanel() {
        // Loads the background image and stores in img object.
        img = Toolkit.getDefaultToolkit().createImage("playGround.jpg");
        imgPaddle = Toolkit.getDefaultToolkit().createImage("paddle.jpg");
        imgBall = Toolkit.getDefaultToolkit().createImage("ball.png");
        imgBonus1 = Toolkit.getDefaultToolkit().createImage("bonus1.png");
        imgBonus2 = Toolkit.getDefaultToolkit().createImage("bonus2.png");
        imgBonus3 = Toolkit.getDefaultToolkit().createImage("bonus3.png");
    }

    public void paint(Graphics g) {
        // Draws the img to the BackgroundPanel.
        g.drawImage(img, 0, 0, null);
        g.drawImage(imgPaddle, 0, 0, null);
        g.drawImage(imgBall, 0, 0, null);
        g.drawImage(imgBonus1, 0, 0, null);
        g.drawImage(imgBonus2, 0, 0, null);
        g.drawImage(imgBonus3, 0, 0, null);
    }
}
