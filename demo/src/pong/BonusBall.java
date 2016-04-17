package pong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import pong.BackgroundPanel;

import java.util.Random;

public class BonusBall {
    public int x;
    public int y;
    public int width = 25; // width of the bonusball
    public int height = 25; // height of the bonusball
    public int motionX;
    public int motionY;
    public int amount;
    public Random random;
    private Pong pong;
    public boolean visibleBall;
    public Image imgPaddle;

    public BonusBall(Pong pong) { //constructor
        this.pong = pong;
        this.random = new Random();
        this.spawn();
        try {
            this.imgPaddle = ImageIO.read(new File("src/img/ball.png")); // initialize the img
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }
    }

    public void update(Paddle paddle1,
                       Paddle paddle2,
                       Ball ball,
                       Bonus bonus) { // function for updating the movement of the bonusball
        byte speed = 2;
        this.x += this.motionX * speed; // setting the movement speed
        this.y += this.motionY * speed;
        if (this.y + this.height - this.motionY > this.pong.height || this.y + this.motionY < 0) { // checking collision
            if (this.motionY < 0) {                                                                // of the ball with
                this.y = 0;                                                                        // the bottom and
                this.motionY = 1;                                                                  // top borders
                if (this.motionY == 0) {                                                           // of the window
                    this.motionY = 1;
                }
            } else {
                this.motionY = -1;
                this.y = this.pong.height - this.height;
                if (this.motionY == 0) {
                    this.motionY = -1;
                }
            }
        }

        if (this.checkCollision(paddle1) == 1) { // checking the collision of the ball with the paddles
            if (ball.amountOfHits < 20) {
                amount = ball.amountOfHits / 5;
            }
            this.motionX = 3 + amount;           // the speed grows with every 5 hits
            this.motionY = this.motionY + 1;
            if (this.motionY == 0) {
                motionY = -random.nextInt((6 - 3) + 3);
            }

            ++ball.amountOfHits;
        } else if (this.checkCollision(paddle2) == 1) {
            if (ball.amountOfHits < 20) {
                amount = ball.amountOfHits / 5;
            }
            this.motionX = -3 - amount;
            this.motionY = this.motionY - 1;
            if (this.motionY == 0) {
                motionY = random.nextInt((6 - 3) + 3);
            }

            ++ball.amountOfHits;
        }

        if (this.checkCollision(paddle1) == 2) {                 // in case the ball goes beyond the paddles
            ++paddle2.score;              // the score grows
            this.spawn();
            this.visibleBall = false;     // the bonusball is not going to be repainted
            bonus.visible = false;        // the bonus will not be repainted
            bonus.possible = true;        // it is possible for the bonuses to show up
            paddle1.pVisible = true;      // paddle1 is visible
            paddle2.pVisible = true;      // paddle2 is visible
            ball.spawn();                 // the ball respawns in the middle of the window
            pong.gameStatus = 4;          // waits for pressing space to continue
        } else if (this.checkCollision(paddle2) == 2) {
            ++paddle1.score;              // the score grows
            this.spawn();
            this.visibleBall = false;     // the bonusball is not going to be repainted
            bonus.visible = false;        // the bonus will not be repainted
            bonus.possible = true;        // it is possible for the bonuses to show up
            paddle1.pVisible = true;      // paddle1 is visible
            paddle2.pVisible = true;      // paddle2 is visible
            ball.spawn();                 // the ball respawns in the middle of the window
            pong.gameStatus = 4;          // waits for pressing space to continue
        }

    }

    public void spawn() {                                   // sets the beginning positions of the bonusball
        this.x = this.pong.width / 2 - this.width / 2;      // and it's starting movement
        this.y = this.pong.height / 2 - this.height / 2;
        this.motionY = (Math.random() <= 0.5) ? 1 : -1;


        if (this.random.nextBoolean()) {
            this.motionX = 3;
        } else {
            this.motionX = -3;
        }

    }

    public int checkCollision(Paddle paddle) { // checks the collision of the bonusball with the paddles
        return this.x < paddle.x + paddle.width &&
                this.x + this.width > paddle.x &&
                this.y < paddle.y + paddle.height &&
                this.y + this.height > paddle.y ? 1 : ((paddle.x <= this.x || paddle.paddleNumber != 1) &&
                (paddle.x >= this.x - this.width || paddle.paddleNumber != 2) ? 0 : 2);
    }

    public void render(Graphics g) { // renders the bonusball
        g.setColor(Color.WHITE);
        //g.fillOval(this.x, this.y, this.width, this.height);
        g.drawImage(imgPaddle, this.x, this.y, null);
    }
}