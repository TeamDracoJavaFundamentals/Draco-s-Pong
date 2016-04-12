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
    public boolean possible = true;
    public boolean wallVisible = false;
    public Image imgBonus1;
    public Image imgBonus2;
    public Image imgBonus3;

    public Bonus(Pong pong) {
        this.pong = pong;
        this.random = new Random();
        try {
            this.imgBonus1 = ImageIO.read(new File("src/pong/bonus1.png"));
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }

        try {
            this.imgBonus2 = ImageIO.read(new File("src/pong/bonus2.png"));
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }

        try {
            this.imgBonus3 = ImageIO.read(new File("src/pong/bonus3.png"));
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }
    }

    public void update(Ball ball, BonusBall bonusBall, Paddle paddle1, Paddle paddle2) {
        if (ball.amountOfHits % 20 == 6 && possible) {
            visible = true;
            this.spawn();
        }

        if (this.checkCollision(ball) == 1) {
            if (visible) {
                bonusBall.visibleBall = true;
                bonusBall.spawn();
            }

            visible = false;
        } else if (this.checkCollision(ball) == 2) {
            if (visible) {
                paddle1.pVisible = false;
                paddle2.pVisible = false;
                possible = false;
            }

            visible = false;
        } else if (this.checkCollision(ball) == 3) {
            if (visible) {
                wallVisible = true;
            }

            visible = false;
        }
    }

    public void spawn() {
        this.b1x = this.pong.width / 2 - this.width / 2;
        this.b1y = this.pong.height / 2 - this.height / 2;

        this.b2x = this.pong.width / 2 - this.width / 2;
        this.b2y = this.pong.height / 2 - this.pong.height / 6 - this.height / 2;

        this.b3x = this.pong.width / 2 - this.width / 2;
        this.b3y = this.pong.height / 2 + this.pong.height / 6 - this.height / 2;
    }

    public int checkCollision(Ball ball) {
        if (!(this.b1x > (ball.x + ball.width) || (this.b1x + this.width) < ball.x) &&
                !(this.b1y > (ball.y + ball.height) || (this.b1y + this.height) < ball.y)) {
            return 1;
        } else if (!(this.b2x > (ball.x + ball.width) || (this.b2x + this.width) < ball.x) &&
                !(this.b2y > (ball.y + ball.height) || (this.b2y + this.height) < ball.y)) {
            return 2;
        } else if (!(this.b3x > (ball.x + ball.width) || (this.b3x + this.width) < ball.x) &&
                !(this.b3y > (ball.y + ball.height) || (this.b3y + this.height) < ball.y)) {
            return 3;
        } else {
            return 0;
        }
    }

    public void render(Graphics g) {
        g.drawImage(this.imgBonus1, this.b1x, this.b1y, null);
        g.drawImage(this.imgBonus2, this.b2x, this.b2y, null);
        g.drawImage(this.imgBonus3, this.b3x, this.b3y, null);
    }
}
