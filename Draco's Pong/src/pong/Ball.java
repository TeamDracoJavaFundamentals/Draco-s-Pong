package pong;

import java.awt.*;
import java.util.Random;
import java.io.File;
import java.io.IOException;

import pong.BackgroundPanel;

import javax.imageio.ImageIO;

import pong.Paddle;
import pong.Pong;

public class Ball {
    public int x;
    public int y;
    public int width = 25; // width of ball
    public int height = 25; // height of ball
    public int motionX;
    public int motionY;
    public Random random;
    private Pong pong;
    public int amount;
    public int amountOfHits; // amount of hits by the paddles
    public Image imgPaddle;

    public Ball(Pong pong) { //constructor
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
                       BonusBall bonusBall,
                       Bonus bonus,
                       BonusWall bonusWall1,
                       BonusWall bonusWall2) {  // function for updating the movement of the ball
        byte speed = 2;
        this.x += this.motionX * speed;  // setting the movement speed
        this.y += this.motionY * speed;
        if (this.y + this.height - this.motionY > this.pong.height || this.y + this.motionY < 0) {  // checking collision
            if (this.motionY < 0) {                                                                 // of the ball with
                this.y = 0;                                                                         // the bottom and
                this.motionY = 1;                                                                   // top borders
                if (this.motionY == 0) {                                                            // of the window
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

        if (this.checkCollision(paddle1) == 1) {    // checking the collision of the ball with the paddles
            if (this.amountOfHits < 20) {
                amount = this.amountOfHits / 5;
            }
            this.motionX = 3 + amount;              // the speed grows with every 5 hits
            this.motionY = this.motionY + 1;
            if (this.motionY == 0) {
                motionY = -random.nextInt((6 - 3) + 3);
            }

            ++this.amountOfHits;
        } else if (this.checkCollision(paddle2) == 1) {
            if (this.amountOfHits < 20) {
                amount = this.amountOfHits / 5;
            }
            this.motionX = -3 - amount;
            this.motionY = this.motionY - 1;
            if (this.motionY == 0) {
                motionY = random.nextInt((6 - 3) + 3);
            }

            ++this.amountOfHits;
        }

        if (this.checkCollision(paddle1) == 2) {             // in case the ball goes beyond the paddles
            ++paddle2.score;                    // the score grows
            bonusBall.visibleBall = false;      // the bonusball is not going to be repainted
            bonus.wallVisible = false;          // the bonusWall is not going to be repainted
            bonus.visible = false;              // the bonus will not be repainted
            bonus.possible = true;              // it is possible for the bonuses to show up
            paddle1.pVisible = true;            // paddle1 is visible
            paddle2.pVisible = true;            // paddle2 is visible
            this.spawn();                       // the ball respawns in the middle of the window
            pong.gameStatus = 4;                // waits for pressing space to continue
        } else if (this.checkCollision(paddle2) == 2) {
            ++paddle1.score;                    // the score grows
            bonusBall.visibleBall = false;      // the bonusball is not going to be repainted
            bonus.wallVisible = false;          // the bonusWall is not going to be repainted
            bonus.visible = false;              // the bonus will not be repainted
            bonus.possible = true;              // it is possible for the bonuses to show up
            paddle1.pVisible = true;            // paddle1 is visible
            paddle2.pVisible = true;            // paddle2 is visible
            this.spawn();                       // the ball respawns in the middle of the window
            pong.gameStatus = 4;                // waits for pressing space to continue
        }

        if (bonus.wallVisible) {                            // in case the bonusWall is visible
            if (this.checkCollisionWall(bonusWall2) == 1) { // checks the collision with the ball
                if (this.amountOfHits < 20) {
                    amount = this.amountOfHits / 5;
                }
                this.motionX = 3 + amount;
                this.motionY = this.motionY + 1;
                if (this.motionY == 0) {
                    motionY = -random.nextInt((6 - 3) + 3);
                }

            } else if (this.checkCollisionWall(bonusWall1) == 1) {
                if (this.amountOfHits < 20) {
                    amount = this.amountOfHits / 5;
                }
                this.motionX = -3 - amount;
                this.motionY = this.motionY - 1;
                if (this.motionY == 0) {
                    motionY = random.nextInt((6 - 3) + 3);
                }

            }
        }

    }

    public void spawn() {                                // sets the beginning positions of the ball
        this.amountOfHits = 0;                           // and it's starting movement
        this.x = this.pong.width / 2 - this.width / 2;
        this.y = this.pong.height / 2 - this.height / 2;
        this.motionY = (Math.random() <= 0.5) ? 1 : -1;


        if (this.random.nextBoolean()) {
            this.motionX = 3;
        } else {
            this.motionX = -3;
        }

    }

    public int checkCollisionWall(BonusWall bonusWall) { // checks the collision of the ball with the bonusWall
        return this.x < bonusWall.x + bonusWall.width &&
                this.x + this.width > bonusWall.x &&
                this.y < bonusWall.y + bonusWall.height &&
                this.y + this.height > bonusWall.y ? 1 : 0;
    }

    public int checkCollision(Paddle paddle) { // checks the collision of the ball with the paddles
        return this.x < paddle.x + paddle.width &&
                this.x + this.width > paddle.x &&
                this.y < paddle.y + paddle.height &&
                this.y + this.height > paddle.y ? 1 : ((paddle.x <= this.x || paddle.paddleNumber != 1) &&
                (paddle.x >= this.x - this.width || paddle.paddleNumber != 2) ? 0 : 2);
    }

    public void render(Graphics g) { // renders the ball
        g.setColor(Color.WHITE);
        //g.fillOval(this.x, this.y, this.width, this.height);
        g.drawImage(this.imgPaddle, this.x, this.y, null);
    }
}