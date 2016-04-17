package pong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import pong.Ball;
import pong.Paddle;
import pong.Renderer;
import pong.BackgroundPanel;

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener, KeyListener {  // contains the game design
    public static Pong pong;
    public int width = 1000;
    public int height = 700;
    public Renderer renderer; // declares the Renderer
    public Paddle player1; // declares the Paddle1
    public Paddle player2; // declares the Paddle2
    public Ball ball; // declares the Ball
    public Bonus bonus; // declares the Bonus
    public BonusBall bonusBall; // declares the BonusBall
    public BonusWall bonusWall1; // declares the BonusWall
    public BonusWall bonusWall2;
    public boolean bot = false;
    public boolean selectingDifficulty;
    public boolean w;
    public boolean s;
    public boolean up;
    public boolean down;
    public int gameStatus = 0;
    public int scoreLimit = 7;
    public int playerWon;
    public int botDifficulty;
    public int botMoves;
    public int botCooldown = 0;
    public Random random;
    public JFrame jframe;
    public Image img;
    public Image imgBlue;
    public Image imgRed;

    public Pong() { // creates the window frame and names it and also contains the game design
        try {
            this.img = ImageIO.read(new File("src/img/playGround.jpg")); //initialize img playGround.jpg
        } catch (IOException ioe) {
            System.out.println("image: background not found");
        }

        try {
            this.imgBlue = ImageIO.read(new File("src/img/blue.jpg")); //initialize img blue.jpg
        } catch (IOException ioe) {
            System.out.println("image: background not found");
        }

        try {
            this.imgRed = ImageIO.read(new File("src/img/red.jpg")); //initialize img red.jpg
        } catch (IOException ioe) {
            System.out.println("image: background not found");
        }


        Timer timer = new Timer(20, this);
        this.random = new Random();
        this.jframe = new JFrame("Draco's Pong");
        this.renderer = new Renderer();
        this.jframe.setSize(this.width + 15, this.height + 47); // set size of window
        this.jframe.setVisible(true); // you can see the window
        this.jframe.setDefaultCloseOperation(3); // exit mechanism
        this.jframe.add(this.renderer); // adds the renderer class
        this.jframe.addKeyListener(this); // adds keyboard use
        this.jframe.addKeyListener(Pong.pong); // - || -
        timer.start();

    }

    public void start() { // initialize game
        this.gameStatus = 2;
        this.player1 = new Paddle(this, 1);
        this.player2 = new Paddle(this, 2);
        this.ball = new Ball(this);
        this.bonus = new Bonus(this);
        this.bonusBall = new BonusBall(this);
        this.bonusWall1 = new BonusWall(this, 1);
        this.bonusWall2 = new BonusWall(this, 2);
    }

    public void update() { // updates/repaints the game
        if (this.player1.score >= this.scoreLimit) {
            this.playerWon = 1;
            this.gameStatus = 3; // changes game status when player1 wins
        }

        if (this.player2.score >= this.scoreLimit) {
            this.gameStatus = 3;
            this.playerWon = 2; // changes game status when player2 wins
        }

        if (this.w) {
            this.player1.move(true);
        }

        if (this.s) {
            this.player1.move(false);
        }

        if (!this.bot) { // if bot is not active
            if (this.up) {
                this.player2.move(true);
            }

            if (this.down) {
                this.player2.move(false);
            }
        } else { // if the bot is active
            if (this.botCooldown > 0) {
                --this.botCooldown;
                if (this.botCooldown == 0) {
                    this.botMoves = 0;
                }
            }

            if (this.botMoves < 10) {
                if (this.player2.y + this.player2.height / 2 < this.ball.y) {
                    this.player2.move(false);
                    ++this.botMoves;
                }

                if (this.player2.y + this.player2.height / 2 > this.ball.y) {
                    this.player2.move(true);
                    ++this.botMoves;
                }

                if (this.botDifficulty == 0) {
                    this.botCooldown = 20;
                }

                if (this.botDifficulty == 1) {
                    this.botCooldown = 15;
                }

                if (this.botDifficulty == 2) {
                    this.botCooldown = 10;
                }
            }
        }

        if(bonusBall.visibleBall) { // if the bonus is hit updates the BonusBall
            this.bonusBall.update(this.player1,
                    this.player2,
                    this.ball,
                    this.bonus);
        }
        if(bonus.wallVisible) { // if the bonus is hit updates the BonusWall
            this.bonusWall1.update(this.bonus);
            this.bonusWall2.update(this.bonus);
        }

        this.bonus.update(this.ball,
                this.bonusBall,
                this.player1,
                this.player2); // if the bonus is hit updates the Bonus

        this.ball.update(this.player1,
                this.player2,
                this.bonusBall,
                this.bonus,
                this.bonusWall1,
                this.bonusWall2); // // updates the Ball
    }

    public void render(Graphics2D g) { // function for painting and displaying
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(this.img, 0, 0, null);


        if (this.gameStatus == 0) { // main menu
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("DRACO'S PONG", this.width / 2 - 190, 100);
            if (!this.selectingDifficulty) {
                g.setFont(new Font("Arial", 1, 30));
                g.drawString("Press Space to Play", this.width / 2 - 150, this.height / 2 - 25);
                g.drawString("Press Shift to Play with Draco", this.width / 2 - 220, this.height / 2 + 25);
                g.drawString("Press I for Instructions", this.width / 2 - 165, this.height / 2 + 75);
                g.drawString("<< Score Limit: " + this.scoreLimit + " >>", this.width / 2 - 150, this.height / 2 + 125);
            }
        }

        if (this.selectingDifficulty) { // menu for choosing bot difficulty
            String string = this.botDifficulty == 0 ? "Easy" : (this.botDifficulty == 1 ? "Medium" : "Hard");
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("<< Draco's Difficulty: " + string + " >>", this.width / 2 - 220, this.height / 2 - 25);
            g.drawString("Press Space to Play", this.width / 2 - 150, this.height / 2 + 25);
            g.drawString("Press ESC for Menu", this.width / 2 - 150, this.height / 2 + 75);
        }

        if (this.gameStatus == 1) { // pause
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSED", this.width / 2 - 103, this.height / 2 - 25);
        }

        if (this.gameStatus == 4) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("Press Space To Begin", this.width / 2 - 260, this.height / 2 - 25);
        }

        if (this.gameStatus == 1 || this.gameStatus == 2 || this.gameStatus == 4) { // the actual game window
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(5.0F));
            g.drawLine(this.width / 2, 0, this.width / 2, this.height);
            g.setStroke(new BasicStroke(2.0F));
            g.drawOval(this.width / 2 - 150, this.height / 2 - 150, 300, 300);
            g.setFont(new Font("Arial", 1, 50)); // sets the font
            g.drawString(String.valueOf(this.player1.score), this.width / 2 - 90, 50); // draws the score of player1
            g.drawString(String.valueOf(this.player2.score), this.width / 2 + 65, 50); // draws the score of player2

            this.player1.render(g); // draws paddle1
            this.player2.render(g); // draws paddle2
            this.ball.render(g);    // draws the ball
            if (bonus.visible) {    // if amountOfHits is 6, draw the bonuses
                this.bonus.render(g);
            }
            if (bonusBall.visibleBall) { // if the bonusball bonus is hit, draw the bonusball
                this.bonusBall.render(g);
                bonus.possible = false;
            }
            if(bonus.wallVisible) { // if the bonuswall bonus is hit, draw the bonuswall
                this.bonusWall1.render(g);
                this.bonusWall2.render(g);
                bonus.possible = false;
            }


        }

        if (this.gameStatus == 3) { // victory menu
            if (this.playerWon == 1){
                g.drawImage(this.imgBlue, 0, 0, null); // if player1 wins, draw img blue.jpg
            } else if (this.playerWon == 2 || this.bot){
                g.drawImage(this.imgRed, 0, 0, null); // if player2 or bot wins, draw img red.jpg
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("DRACO'S PONG", this.width / 2 - 190, 100);
            if (this.bot && this.playerWon == 2) {
                g.drawString("Draco Wins!", this.width / 2 - 150, 200);

            } else {
                g.drawString("Player " + this.playerWon + " Wins!", this.width / 2 - 165, 200);
            }

            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Press Space to Play Again", this.width / 2 - 185, this.height / 2 - 25);
            g.drawString("Press ESC for Menu", this.width / 2 - 140, this.height / 2 + 25);
        }

        if (this.gameStatus == 5) { //instructions menu
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("Instructions", this.width / 2 - 140, this.height / 2 - 175);
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Player 1: W, S - move up and down", this.width / 2 - 250, this.height / 2 - 125);
            g.drawString("Player 2: Up, Down - move up and down", this.width / 2 - 250, this.height / 2 - 75);
            g.drawString("Esc - Main menu", this.width / 2 - 250, this.height / 2 - 25);
            g.drawString("Space - Pause", this.width / 2 - 250, this.height / 2 + 25);
            g.drawString("Press I Again to exit Instructions", this.width / 2 - 250, this.height / 2 + 75);


        }


    }

    public void actionPerformed(ActionEvent e) {
        if (this.gameStatus == 2) { // updates while in game
            this.update();
        }

        this.renderer.repaint();
    }

    public void keyPressed(KeyEvent e) { // KeyListener function
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_W) { // w
            this.w = true;
        } else if (id == KeyEvent.VK_S) { // s
            this.s = true;
        } else if (id == KeyEvent.VK_UP) { // up arrow
            this.up = true;
        } else if (id == KeyEvent.VK_DOWN) { // down arrow
            this.down = true;
        } else if (id == KeyEvent.VK_RIGHT) { // right arrow // choosing limit for the score ot bot difficulty
            if (this.selectingDifficulty) {
                if (this.botDifficulty < 2) {
                    ++this.botDifficulty;
                } else {
                    this.botDifficulty = 0;
                }
            } else if (this.gameStatus == 0) {
                ++this.scoreLimit;
            }
        } else if (id == KeyEvent.VK_LEFT) { // left arrow // choosing limit for the score ot bot difficulty
            if (this.selectingDifficulty) {
                if (this.botDifficulty > 0) {
                    --this.botDifficulty;
                } else {
                    this.botDifficulty = 2;
                }
            } else if (this.gameStatus == 0 && this.scoreLimit > 1) {
                --this.scoreLimit;
            }
        } else if (id == KeyEvent.VK_ESCAPE) { // esc // exit to main menu
            if (this.gameStatus == 2 || this.gameStatus == 3) {
                this.gameStatus = 0;
            } else {
                if (!this.selectingDifficulty) {
                    this.bot = false;
                } else {
                    this.selectingDifficulty = false;
                }
                this.gameStatus = 0;
            }
        } else if (id == KeyEvent.VK_SHIFT && this.gameStatus == 0) { // shift // going to selecting bot's difficulty
            this.bot = true;
            this.selectingDifficulty = true;
        } else if (id == KeyEvent.VK_SPACE) { // space
            if (this.gameStatus != 0 && this.gameStatus != 3) { //switching between game and pause
                if (this.gameStatus == 1) {
                    this.gameStatus = 2;
                } else if (this.gameStatus == 2) {
                    this.gameStatus = 1;
                }
                if (this.gameStatus == 4) {
                    this.gameStatus = 2;
                }
            } else {
                if (!this.selectingDifficulty) {
                    this.bot = false;
                } else {
                    this.selectingDifficulty = false;
                }

                this.start(); // start the game
            }
        } else if (id == KeyEvent.VK_I) { // open and close instructions
            if (this.gameStatus == 0) {
                this.gameStatus = 5;
            } else if (this.gameStatus == 5) {
                this.gameStatus = 0;
            }

        }
    }

    public void keyReleased(KeyEvent e) {
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_W) {
            this.w = false;
        } else if (id == KeyEvent.VK_S) {
            this.s = false;
        } else if (id == KeyEvent.VK_UP) {
            this.up = false;
        } else if (id == KeyEvent.VK_DOWN) {
            this.down = false;
        }

    }

    public void keyTyped(KeyEvent e) {
    }
}