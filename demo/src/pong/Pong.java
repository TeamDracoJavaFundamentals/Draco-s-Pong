package pong;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import pong.Ball;
import pong.Paddle;
import pong.Renderer;

public class Pong implements ActionListener, KeyListener {
    public static Pong pong;
    public int width = 1000;
    public int height = 700;
    public Renderer renderer;
    public Paddle player1;
    public Paddle player2;
    public Ball ball;
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

    public Pong() {
        Timer timer = new Timer(20, this);
        this.random = new Random();
        this.jframe = new JFrame("Pong");
        this.renderer = new Renderer();
        this.jframe.setSize(this.width + 15, this.height + 35);
        this.jframe.setVisible(true);
        this.jframe.setDefaultCloseOperation(3);
        this.jframe.add(this.renderer);
        this.jframe.addKeyListener(this);
        this.jframe.addKeyListener(Pong.pong);
        timer.start();
    }

    public void start() {
        this.gameStatus = 2;
        this.player1 = new Paddle(this, 1);
        this.player2 = new Paddle(this, 2);
        this.ball = new Ball(this);
    }

    public void update() {
        if(this.player1.score >= this.scoreLimit) {
            this.playerWon = 1;
            this.gameStatus = 3;
        }

        if(this.player2.score >= this.scoreLimit) {
            this.gameStatus = 3;
            this.playerWon = 2;
        }

        if(this.w) {
            this.player1.move(true);
        }

        if(this.s) {
            this.player1.move(false);
        }

        if(!this.bot) {
            if(this.up) {
                this.player2.move(true);
            }

            if(this.down) {
                this.player2.move(false);
            }
        } else {
            if(this.botCooldown > 0) {
                --this.botCooldown;
                if(this.botCooldown == 0) {
                    this.botMoves = 0;
                }
            }

            if(this.botMoves < 10) {
                if(this.player2.y + this.player2.height / 2 < this.ball.y) {
                    this.player2.move(false);
                    ++this.botMoves;
                }

                if(this.player2.y + this.player2.height / 2 > this.ball.y) {
                    this.player2.move(true);
                    ++this.botMoves;
                }

                if(this.botDifficulty == 0) {
                    this.botCooldown = 20;
                }

                if(this.botDifficulty == 1) {
                    this.botCooldown = 15;
                }

                if(this.botDifficulty == 2) {
                    this.botCooldown = 10;
                }
            }
        }

        this.ball.update(this.player1, this.player2);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(this.gameStatus == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PONG", this.width / 2 - 75, 50);
            if(!this.selectingDifficulty) {
                g.setFont(new Font("Arial", 1, 30));
                g.drawString("Press Space to Play", this.width / 2 - 150, this.height / 2 - 25);
                g.drawString("Press Shift to Play with Bot", this.width / 2 - 200, this.height / 2 + 25);
                g.drawString("<< Score Limit: " + this.scoreLimit + " >>", this.width / 2 - 150, this.height / 2 + 75);
            }
        }

        if(this.selectingDifficulty) {
            String string = this.botDifficulty == 0?"Easy":(this.botDifficulty == 1?"Medium":"Hard");
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("<< Bot Difficulty: " + string + " >>", this.width / 2 - 180, this.height / 2 - 25);
            g.drawString("Press Space to Play", this.width / 2 - 150, this.height / 2 + 25);
            g.drawString("Press ESC for Menu", this.width / 2 - 150, this.height / 2 + 75);
        }

        if(this.gameStatus == 1) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSED", this.width / 2 - 103, this.height / 2 - 25);
        }

        if(this.gameStatus == 1 || this.gameStatus == 2) {
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(5.0F));
            g.drawLine(this.width / 2, 0, this.width / 2, this.height);
            g.setStroke(new BasicStroke(2.0F));
            g.drawOval(this.width / 2 - 150, this.height / 2 - 150, 300, 300);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString(String.valueOf(this.player1.score), this.width / 2 - 90, 50);
            g.drawString(String.valueOf(this.player2.score), this.width / 2 + 65, 50);
            this.player1.render(g);
            this.player2.render(g);
            this.ball.render(g);
        }

        if(this.gameStatus == 3) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PONG", this.width / 2 - 75, 50);
            if(this.bot && this.playerWon == 2) {
                g.drawString("The Bot Wins!", this.width / 2 - 170, 200);
            } else {
                g.drawString("Player " + this.playerWon + " Wins!", this.width / 2 - 165, 200);
            }

            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Press Space to Play Again", this.width / 2 - 185, this.height / 2 - 25);
            g.drawString("Press ESC for Menu", this.width / 2 - 140, this.height / 2 + 25);
        }

    }

    public void actionPerformed(ActionEvent e) {
        if(this.gameStatus == 2) {
            this.update();
        }

        this.renderer.repaint();
    }

    public void keyPressed(KeyEvent e) {
        int id = e.getKeyCode();
        if(id == KeyEvent.VK_W) { // w
            this.w = true;
        } else if(id == KeyEvent.VK_S) { // s
            this.s = true;
        } else if(id == KeyEvent.VK_UP) { // up arrow
            this.up = true;
        } else if(id == KeyEvent.VK_DOWN) { // down arrow
            this.down = true;
        } else if(id == KeyEvent.VK_RIGHT) { // right arrow
            if(this.selectingDifficulty) {
                if(this.botDifficulty < 2) {
                    ++this.botDifficulty;
                } else {
                    this.botDifficulty = 0;
                }
            } else if(this.gameStatus == 0) {
                ++this.scoreLimit;
            }
        } else if(id == KeyEvent.VK_LEFT) { // left arrow
            if(this.selectingDifficulty) {
                if(this.botDifficulty > 0) {
                    --this.botDifficulty;
                } else {
                    this.botDifficulty = 2;
                }
            } else if(this.gameStatus == 0 && this.scoreLimit > 1) {
                --this.scoreLimit;
            }
        } else if(id == KeyEvent.VK_ESCAPE) { // esc
            if(this.gameStatus == 2 || this.gameStatus == 3) {
                this.gameStatus = 0;
            } else {
                    if(!this.selectingDifficulty) {
                        this.bot = false;
                    } else {
                        this.selectingDifficulty = false;
                    }
                    this.gameStatus = 0;
            }
        } else if(id == KeyEvent.VK_SHIFT && this.gameStatus == 0) { // shift
            this.bot = true;
            this.selectingDifficulty = true;
        } else if(id == KeyEvent.VK_SPACE) { // space
            if(this.gameStatus != 0 && this.gameStatus != 3) {
                if(this.gameStatus == 1) {
                    this.gameStatus = 2;
                } else if(this.gameStatus == 2) {
                    this.gameStatus = 1;
                }
            } else {
                if(!this.selectingDifficulty) {
                    this.bot = false;
                } else {
                    this.selectingDifficulty = false;
                }

                this.start();
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int id = e.getKeyCode();
        if(id == KeyEvent.VK_W) {
            this.w = false;
        } else if(id == KeyEvent.VK_S) {
            this.s = false;
        } else if(id == KeyEvent.VK_UP) {
            this.up = false;
        } else if(id == KeyEvent.VK_DOWN) {
            this.down = false;
        }

    }

    public void keyTyped(KeyEvent e) {
    }
}
