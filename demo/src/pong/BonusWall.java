package pong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alec on 11.04.2016 г..
 */
public class BonusWall {
    public int x, y;
    public int width = 10; // width of bonusWall
    public int height = 300; // height of bonusWall
    private Pong pong;
    private int wallNumber;

    public BonusWall(Pong pong, int wallNumber) {  // constructor
        this.pong = pong;
        this.wallNumber = wallNumber;
    }

    public void update(Bonus bonus) {   // function for updating the visibility of the bonuswall
        if (bonus.wallVisible) {
            this.spawn();
        }
    }

    public void spawn() {  // sets the beginning positions of the bonuswall
        if(wallNumber == 1) {
            this.x = this.pong.width / 2 - this.width;
        }

        if(wallNumber == 2) {
            this.x = this.pong.width / 2;
        }

        this.y = this.pong.height / 2 - this.height / 2;
    }

    public void render(Graphics g) {        // renders the bonuswall
        Color color = new Color(0, 0, 0, 0x80);
        g.setColor(color);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

}
