package pong;

import java.awt.*;
import java.util.Random;

public class Bonus {
    public int x;
    public int y;
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

    public void update(Ball ball, BonusBall bonusBall, BonusBall bonusBall2) {
        if (ball.amountOfHits % 20 == 15) {
            visible = true;
            this.spawn();
        }

        if (this.checkCollision(ball) == 1) {
            if (visible) {
                bonusBall.visibleBall = true;
                bonusBall2.visibleBall = true;
                bonusBall.spawn();
            }
            visible = false;
        }
    }

    public void spawn() {
        this.x = 400;//this.random.nextInt(4);
//        if (this.x == 0 || this.x < 150) {
//            this.x = 150;
//        }
        this.y = 400;//this.random.nextInt(4);
    }

    public int checkCollision(Ball ball) {
        if (!(this.x > (ball.x + ball.width) || (this.x + this.width) < ball.x) &&
                !(this.y > (ball.y + ball.height) || (this.y + this.height) < ball.y)) {
            return 1;
        } else {
            return 0;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(this.x, this.y, this.width, this.height);
    }
}
