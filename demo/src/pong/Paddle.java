package pong;
import java.awt.Color;
import java.awt.Graphics;
import pong.Pong;

public class Paddle {
    public int paddleNumber;
    public int x;
    public int y;
    public int width = 50;
    public int height = 250;
    public int score;

    public Paddle(Pong pong, int paddleNumber) { // constructor na klasa s atributi Pong obekt i nomer na paddle-a
        this.paddleNumber = paddleNumber;
        if(paddleNumber == 1) { // zadava poziciqta na paddlite
            this.x = 0;            // nai vlqvo
        }

        if(paddleNumber == 2) {
            this.x = pong.width - this.width; // nai vdqsno
        }

        this.y = pong.height / 2 - this.height / 2; // y coord e ednakva i za dvete
    }

    public void render(Graphics g) { // iz4ertava gi
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

    public void move(boolean up) {              // ako e istina i ako y coord na paddle-a - 25 dostiga nad
        byte speed = 25;                        // nai gornata to4ka na prozoreca, da napravi y=0 t1i kato v
        if(up) {                                // tezi igri x=0 i y=0 v nai gorniq lqv agal i y raste nadolu
            if(this.y - speed > 0) {            // i s1otvetno posle za nadolu
                this.y -= speed;
            } else {
                this.y = 0;
            }
        } else if(this.y + this.height + speed < Pong.pong.height) {
            this.y += speed;
        } else {
            this.y = Pong.pong.height - this.height;
        }

    }
}

