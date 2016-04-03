package pong;
import java.awt.Color;
import java.awt.Graphics;
import pong.Pong;

public class Paddle {
    public int paddleNumber;
    public int x;
    public int y;
    public int width = 50;
    public int height = 250;
    public int score;

    public Paddle(Pong pong, int paddleNumber) {
        this.paddleNumber = paddleNumber;
        if(paddleNumber == 1) {
            this.x = 0;
        }

        if(paddleNumber == 2) {
            this.x = pong.width - this.width;
        }

        this.y = pong.height / 2 - this.height / 2;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

    public void move(boolean up) {
        byte speed = 25;
        if(up) {
            if(this.y - speed > 0) {
                this.y -= speed;
            } else {
                this.y = 0;
            }
        } else if(this.y + this.height + speed < Pong.pong.height) {
            this.y += speed;
        } else {
            this.y = Pong.pong.height - this.height;
        }

    }
}

