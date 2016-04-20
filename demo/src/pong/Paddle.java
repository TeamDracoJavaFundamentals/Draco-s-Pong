package pong;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import pong.Pong;
import pong.BackgroundPanel;

import javax.imageio.ImageIO;


public class Paddle {
    public int paddleNumber;
    public int x;
    public int y;
    public int width = 50;
    public int height = 250;
    public int score;
    public boolean pVisible = true;
    public Image imgPaddle;

    public Paddle(Pong pong, int paddleNumber) { // constructor // sets the begging positions of the paddles
        this.paddleNumber = paddleNumber;
        if (paddleNumber == 1) {
            this.x = 0;
        }

        if (paddleNumber == 2) {
            this.x = pong.width - this.width; // nai vdqsno
        }

        try {
            this.imgPaddle = ImageIO.read(new File("src/img/paddle.jpg"));
        } catch (IOException ioe) {
            System.out.println("image: paddle not found");
        }

        this.y = pong.height / 2 - this.height / 2; // b1y coord e ednakva i za dvete
    }

    public void render(Graphics g) { // renders the paddles
        if (pVisible) {
            g.drawImage(this.imgPaddle, this.x, this.y, null);

        } else {
            Color color = new Color(0, 0, 0, 0x10);
            g.setColor(color);
            g.fillRect(this.x, this.y, this.width, this.height);
        }


    }

    public void move(boolean up) {              // sets the movement speed of the paddles
        byte speed = 35;
        if (up) {
            if (this.y - speed > 0) {
                this.y -= speed;
            } else {
                this.y = 0;
            }
        } else if (this.y + this.height + speed < Pong.pong.height) {
            this.y += speed;
        } else {
            this.y = Pong.pong.height - this.height;
        }

    }
}