package pong;

import java.awt.*;


class BackgroundPanel extends Panel
{
    // The Image to store the background image in.
    Image img;
    Image imgPaddle;
    Image imgBall;
    public BackgroundPanel()
    {
        // Loads the background image and stores in img object.
        img = Toolkit.getDefaultToolkit().createImage("playGround.jpg");
        imgPaddle = Toolkit.getDefaultToolkit().createImage("paddle.jpg");
        imgBall = Toolkit.getDefaultToolkit().createImage("ball.png");
    }

    public void paint(Graphics g)
    {
        // Draws the img to the BackgroundPanel.
        g.drawImage(img, 0, 0, null);
        g.drawImage(imgPaddle, 0, 0, null);
        g.drawImage(imgBall, 0, 0, null);
    }
}
