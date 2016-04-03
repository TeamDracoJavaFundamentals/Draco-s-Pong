package pong;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import pong.Paddle;
import pong.Pong;

public class Ball {
    public int x;
    public int y;
    public int width = 25;
    public int height = 25;
    public int motionX;
    public int motionY;
    public Random random;
    private Pong pong;
    public int amountOfHits;

    public Ball(Pong pong) {
        this.pong = pong;
        this.random = new Random();
        this.spawn();
    }

    public void update(Paddle paddle1, Paddle paddle2) {
        byte speed = 5;
        this.x += this.motionX * speed;
        this.y += this.motionY * speed;
        if(this.y + this.height - this.motionY > this.pong.height || this.y + this.motionY < 0) {
            if(this.motionY < 0) {
                this.y = 0;
                this.motionY = this.random.nextInt(4);
                if(this.motionY == 0) {
                    this.motionY = 1;
                }
            } else {
                this.motionY = -this.random.nextInt(4);
                this.y = this.pong.height - this.height;
                if(this.motionY == 0) {
                    this.motionY = -1;
                }
            }
        }

        if(this.checkCollision(paddle1) == 1) {
            this.motionX = 1 + this.amountOfHits / 5;
            this.motionY = -2 + this.random.nextInt(4);
            if(this.motionY == 0) {
                this.motionY = 1;
            }

            ++this.amountOfHits;
        } else if(this.checkCollision(paddle2) == 1) {
            this.motionX = -1 - this.amountOfHits / 5;
            this.motionY = -2 + this.random.nextInt(4);
            if(this.motionY == 0) {
                this.motionY = 1;
            }

            ++this.amountOfHits;
        }

        if(this.checkCollision(paddle1) == 2) {
            ++paddle2.score;
            this.spawn();
        } else if(this.checkCollision(paddle2) == 2) {
            ++paddle1.score;
            this.spawn();
        }

    }

    public void spawn() {
        this.amountOfHits = 0;
        this.x = this.pong.width / 2 - this.width / 2;
        this.y = this.pong.height / 2 - this.height / 2;
        this.motionY = -2 + this.random.nextInt(4);
        if(this.motionY == 0) {
            this.motionY = 1;
        }

        if(this.random.nextBoolean()) {
            this.motionX = 1;
        } else {
            this.motionX = -1;
        }

    }

    public int checkCollision(Paddle paddle) {
        return this.x < paddle.x + paddle.width &&
                this.x + this.width > paddle.x &&
                this.y < paddle.y + paddle.height &&
                this.y + this.height > paddle.y?1:((paddle.x <= this.x || paddle.paddleNumber != 1) &&
                                                   (paddle.x >= this.x - this.width || paddle.paddleNumber != 2)?0:2);
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(this.x, this.y, this.width, this.height);
    }
}