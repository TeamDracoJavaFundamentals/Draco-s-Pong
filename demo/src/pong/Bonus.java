package pong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Bonus {
    public int b1x, b1y;
    public int b2x, b2y;
    public int b3x, b3y;
    public int width = 50;
    public int height = 50;
    public Random random;
    public boolean visible = false;
    private Pong pong;
    private BonusBall bonusBall;

    public Bonus(Pong pong) {
        this.pong = pong;
        this.random = new Random();
    }

    public void update(Ball ball, BonusBall bonusBall) {
        if (ball.amountOfHits % 20 == 15) {
            visible = true;
            this.spawn();
        }

        if (this.checkCollision(ball) == 1) {
            if (visible) {
                bonusBall.visibleBall = true;
                bonusBall.spawn();
            }
            visible = false;
        }
    }

    public void spawn() {
        this.b1x = this.pong.width / 2 - this.width / 2;
        this.b1y = this.pong.height / 2 - this.height / 2;
    }

    public int checkCollision(Ball ball) {
        if (!(this.b1x > (ball.x + ball.width) || (this.b1x + this.width) < ball.x) &&
                !(this.b1y > (ball.y + ball.height) || (this.b1y + this.height) < ball.y)) {
            return 1;
        } else {
            return 0;
        }
    }

    public void render(Graphics g) {
        Color color = new Color(0, 0, 0, 0x33);
        g.setColor(color);
        //g.fillRect(this.b1x, this.b1y, this.width, this.height);
        try {
            Image imgPaddle = ImageIO.read(new File("src/pong/bonus1.png"));
            g.drawImage(imgPaddle, this.b1x, this.b1y, null);
        } catch (IOException ioe){
            System.out.println("image: ball not found");
        }
    }
}
