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
    public int width = 25;
    public int height = 25;
    public int motionX;
    public int motionY;
    public Random random;
    private Pong pong;
    public int amount;
    public int amountOfHits;

    public Ball(Pong pong) { //construktor s atribut obekt ot Pong
        this.pong = pong;
        this.random = new Random();
        this.spawn(); // funkciq na klasa za s1zdavane na top4eto v sredata na ekrana
    }                  // i izpra6tane v s1otvetna posoka

    public void update(Paddle paddle1,
                       Paddle paddle2,
                       BonusBall bonusBall,
                       Bonus bonus,
                       BonusWall bonusWall1,
                       BonusWall bonusWall2) {
        byte speed = 2;
        this.x += this.motionX * speed;
        this.y += this.motionY * speed;
        if (this.y + this.height - this.motionY > this.pong.height || this.y + this.motionY < 0) {
            if (this.motionY < 0) {
                this.y = 0;
                this.motionY = 1;          // proverqva dali top4eto
                if (this.motionY == 0) {                         // se e udarilo v gornata ili
                    this.motionY = 1;                           // dolanata stena
                }                                               // i ako da, da se otbl1skva
            } else {                                            // s1otvetno p1rvo za gornata i posle za dolnata stena
                this.motionY = -1;
                this.y = this.pong.height - this.height;
                if (this.motionY == 0) {
                    this.motionY = -1;
                }
            }
        }

        if (this.checkCollision(paddle1) == 1) {                 // a tezi 2te proverqvat za paddle-ite
            if (this.amountOfHits < 20) {
                amount = this.amountOfHits / 5;
            }
            this.motionX = 3 + amount;                          // kato se uveli4ava skorostta na vseki 5 udara
            this.motionY = this.motionY + 1;        // i izpra6ta top4eto v random posoka ot -2 + ot 0 do 4
            if (this.motionY == 0) {                             // maha slu4aq s 0 za6toto da se izbegne to4no
                motionY = -random.nextInt((6 - 3) + 3);                              // prava horizontalna posoka t1i kato se
            }                                                   // polu4avat b1gove ako se udari v nqkoi ot r1bovete
            // na prozoreca
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

        if (this.checkCollision(paddle1) == 2) {                 // v slu4ai 4e tazi funkciq checkCollision v1rne 2
            ++paddle2.score;
            bonusBall.visibleBall = false;
            bonus.wallVisible = false;
            bonus.visible = false;
            bonus.possible = true;
            paddle1.pVisible = true;
            paddle2.pVisible = true;
            this.spawn();
            pong.gameStatus = 4;                                                        // respawnva top4eto po sredata
        } else if (this.checkCollision(paddle2) == 2) {
            ++paddle1.score;
            bonusBall.visibleBall = false;
            bonus.wallVisible = false;
            bonus.visible = false;
            bonus.possible = true;
            paddle1.pVisible = true;
            paddle2.pVisible = true;
            this.spawn();
            pong.gameStatus = 4;
        }

        if (bonus.wallVisible) {
            if (this.checkCollisionWall(bonusWall2) == 1) {
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

    public void spawn() {
        this.amountOfHits = 0;
        this.x = this.pong.width / 2 - this.width / 2;
        this.y = this.pong.height / 2 - this.height / 2;
        this.motionY = (Math.random() <= 0.5) ? 1 : -1;


        if (this.random.nextBoolean()) {
            this.motionX = 3;
        } else {
            this.motionX = -3;
        }

    }

    public int checkCollisionWall(BonusWall bonusWall) {
        return this.x < bonusWall.x + bonusWall.width &&
                this.x + this.width > bonusWall.x &&
                this.y < bonusWall.y + bonusWall.height &&
                this.y + this.height > bonusWall.y ? 1 : 0;
    }

    public int checkCollision(Paddle paddle) {
        return this.x < paddle.x + paddle.width &&
                this.x + this.width > paddle.x &&
                this.y < paddle.y + paddle.height &&
                this.y + this.height > paddle.y ? 1 : ((paddle.x <= this.x || paddle.paddleNumber != 1) &&
                (paddle.x >= this.x - this.width || paddle.paddleNumber != 2) ? 0 : 2);
    }

    public void render(Graphics g) { // iz4ertava top4eto
        g.setColor(Color.WHITE);
        //g.fillOval(this.x, this.y, this.width, this.height);
        try {
            Image imgPaddle = ImageIO.read(new File("src/pong/ball.png"));
            g.drawImage(imgPaddle, this.x, this.y, null);
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }
    }
}