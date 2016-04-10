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

public class Pong implements ActionListener, KeyListener { // tezi 2te sa interface-i vgradeni v java
    public static Pong pong; // obekt ot klasa
    public int width = 1000; // razmeri na prozoreca
    public int height = 700;
    public Renderer renderer; // obekt ot klasa Renderer
    public Paddle player1; // obekti ot klasa Paddle
    public Paddle player2;
    public Ball ball; // obekt ot klasa Ball
    public Bonus bonus;
    public BonusBall bonusBall;
    public BonusBall bonusBall2;
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
    public JFrame jframe; // prozorecyt (vgraden klas 4rez swing library)

    public Pong() { // constructor na klasa
        Timer timer = new Timer(20, this);
        this.random = new Random();
        this.jframe = new JFrame("Draco's Pong");
        this.renderer = new Renderer();
        this.jframe.setSize(this.width + 15, this.height + 35);
        this.jframe.setVisible(true); // ako tova go nqma, nqma da se vizualizira ni6to
        this.jframe.setDefaultCloseOperation(3); // zatvarq se 4rez butona 'x'
        this.jframe.add(this.renderer); // dobavq obekt or Renderer
        this.jframe.addKeyListener(this); // funkciq ot interface-a, a this ozna4ava 4e stava v1pros za s1otvetniq class v koito se namirame v momenta
        this.jframe.addKeyListener(Pong.pong); // - || -
        timer.start();
    }

    public void start() {
        this.gameStatus = 2; // smenq ot menuto k1m igrata
        this.player1 = new Paddle(this, 1); // s1zdava obektite, kato vzema 4rez 'this' definiciqta ot po gore
        this.player2 = new Paddle(this, 2);
        this.ball = new Ball(this);
        this.bonus = new Bonus(this);
        this.bonusBall = new BonusBall(this);
        this.bonusBall2 = new BonusBall(this);
    }

    public void update() {
        if(this.player1.score >= this.scoreLimit) {
            this.playerWon = 1;
            this.gameStatus = 3; // prosorec pri pobeda
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
            if(this.botCooldown > 0) { // ako e pove4e ot 0 go namalq za6toto kato padne na nula po
                --this.botCooldown;    // nadolu ima kod koito  koito go vdiga na opredelena stoinost v zavisimost ot difficultito
                if(this.botCooldown == 0) { // zanulqva dvijeniqta ako izpusne top4eto
                    this.botMoves = 0;
                }
            }

            if(this.botMoves < 10) {
                if(this.player2.y + this.player2.height / 2 < this.ball.y) { // ako y coord + viso4inata na paddle-a sa po malki
                    this.player2.move(false);                                // ot y coord na top4eto se mesti paddle-a nadolu
                    ++this.botMoves;
                }

                if(this.player2.y + this.player2.height / 2 > this.ball.y) { // obratnoto
                    this.player2.move(true);
                    ++this.botMoves;
                }

                if(this.botDifficulty == 0) { // uveli4avaneto za koeto govoreh
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

        if(bonusBall.visibleBall) {
            this.bonusBall.update(this.player1, this.player2, this.ball);
            this.bonusBall2.update(this.player1, this.player2, this.ball);
        }
        if(bonusBall2.visibleBall) {
            this.bonusBall.update(this.player1, this.player2, this.ball);
            this.bonusBall2.update(this.player1, this.player2, this.ball);
        }
        this.bonus.update(this.ball, this.bonusBall, this.bonusBall2);
        this.ball.update(this.player1, this.player2, this.bonusBall, this.bonusBall2); // updatvane na topkata (funkciqta idva ot nego klas t1i kato pi6e this(demek ot tozi klas,
    }                                                 // posle ball(definiciqta ot na4aloto na tozi klas Ball ball) i funkciqta update on klasa Ball
                                                      // koqto ima atributi paddle1 i paddle2
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            Image img = ImageIO.read(new File("src/pong/playGround.jpg")); //izchertavane na background image
            g.drawImage(img, 0, 0, null);
        } catch (IOException ioe){
            System.out.println("image: background not found");
        }
        if(this.gameStatus == 0) { // glavno menu
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("DRACO'S PONG", this.width / 2 - 190, 100);
            if(!this.selectingDifficulty) {
                g.setFont(new Font("Arial", 1, 30));
                g.drawString("Press Space to Play", this.width / 2 - 150, this.height / 2 - 25);
                g.drawString("Press Shift to Play with Draco", this.width / 2 - 200, this.height / 2 + 25);
                g.drawString("<< Score Limit: " + this.scoreLimit + " >>", this.width / 2 - 150, this.height / 2 + 75);
            }
        }

        if(this.selectingDifficulty) { // menu pri natiskane na shift
            String string = this.botDifficulty == 0?"Easy":(this.botDifficulty == 1?"Medium":"Hard");
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("<< Draco's Difficulty: " + string + " >>", this.width / 2 - 180, this.height / 2 - 25);
            g.drawString("Press Space to Play", this.width / 2 - 150, this.height / 2 + 25);
            g.drawString("Press ESC for Menu", this.width / 2 - 150, this.height / 2 + 75);
        }

        if(this.gameStatus == 1) { // pause
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSED", this.width / 2 - 103, this.height / 2 - 25);
        }

        if(this.gameStatus == 4) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("Press Space To Begin", this.width / 2 - 260, this.height / 2 - 25);
        }

        if(this.gameStatus == 1 || this.gameStatus == 2 || this.gameStatus == 4) { // prez igrata
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(5.0F));
            g.drawLine(this.width / 2, 0, this.width / 2, this.height);
            g.setStroke(new BasicStroke(2.0F));
            g.drawOval(this.width / 2 - 150, this.height / 2 - 150, 300, 300);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString(String.valueOf(this.player1.score), this.width / 2 - 90, 50);
            g.drawString(String.valueOf(this.player2.score), this.width / 2 + 65, 50);
            this.player1.render(g); // izpolzva render funkciite ot drugite klasove za da updatva kartinata
            this.player2.render(g);
            this.ball.render(g);
            if (bonus.visible) {
                this.bonus.render(g);
            }
            if (bonusBall.visibleBall) {
                this.bonusBall.render(g);
            } else {
                bonusBall2.visibleBall = false;
            }
            if (bonusBall2.visibleBall) {
                this.bonusBall2.render(g);
            } else {
                bonusBall.visibleBall = false;
            }

        }

        if(this.gameStatus == 3) { // menu na pobeda
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("DRACO'S PONG", this.width / 2 - 190, 100);
            if(this.bot && this.playerWon == 2) {
                g.drawString("Draco Wins!", this.width / 2 - 150, 200);
            } else {
                g.drawString("Player " + this.playerWon + " Wins!", this.width / 2 - 165, 200);
            }

            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Press Space to Play Again", this.width / 2 - 185, this.height / 2 - 25);
            g.drawString("Press ESC for Menu", this.width / 2 - 140, this.height / 2 + 25);
        }

    }

    public void actionPerformed(ActionEvent e) { // funkciq ot interfasite pak (ActionListener)
        if(this.gameStatus == 2) { // da updatva i repaintva dokato se igrae
            this.update();
        }

        this.renderer.repaint();
    }

    public void keyPressed(KeyEvent e) { // vsi4ki funkcii nadolu sa ot interface-a KeyListener
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
            if(this.gameStatus != 0 && this.gameStatus != 3 ) {
                if(this.gameStatus == 1) {
                    this.gameStatus = 2;
                } else if(this.gameStatus == 2) {
                    this.gameStatus = 1;
                }
                if(this.gameStatus == 4){
                    this.gameStatus = 2;
                }
            } else {
                if(!this.selectingDifficulty) {
                    this.bot = false;
                } else {
                    this.selectingDifficulty = false;
                }

                this.start(); // start funkciqta ot na4aloto na klasa
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

    public void keyTyped(KeyEvent e) { // nqma zna4enie 4e e prazna, ako se implementira interface
    }                                   // trqbva da se vkarat vsi4ki negovi funkcii dori i da ne se izpolzvat
}
