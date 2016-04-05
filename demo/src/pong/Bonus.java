package pong;

import java.awt.*;
import java.util.Random;

public class Bonus {
    public int x;
    public int y;
    public int width = 25;
    public int height = 25;
    public Random random;
    private Pong pong;

    public Bonus(Pong pong) {
        this.pong = pong;
        this.random = new Random();
        this.spawn();
    }

    public void update(Ball ball) {

    }

    public void spawn() {
        this.x = 100;//this.random.nextInt(4);
//        if (this.x == 0 || this.x < 150) {
//            this.x = 150;
//        }
        this.y = 100;//this.random.nextInt(4);
    }

//    public int checkCollision(Ball ball) {
//        return this.x < ball.x + ball.width &&
//                this.x + this.width > ball.x &&
//                this.y < ball.y + ball.height &&
//                this.y + this.height > ball.y?1:((ball.x <= this.x || ball.paddleNumber != 1) &&
//                (ball.x >= this.x - this.width || ball.paddleNumber != 2)?0:2);
//    }

    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(this.x, this.y, this.width, this.height);
    }
}
