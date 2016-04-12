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
    public int width = 10;
    public int height = 450;
    private Pong pong;
    private int wallNumber;

    public BonusWall(Pong pong, int wallNumber) {
        this.pong = pong;
        this.wallNumber = wallNumber;
    }

    public void update(Bonus bonus) {
        if (bonus.wallVisible) {
            this.spawn();
        }
    }

    public void spawn() {
        if(wallNumber == 1) {
            this.x = this.pong.width / 2 - this.width;
        }

        if(wallNumber == 2) {
            this.x = this.pong.width / 2;
        }

        this.y = this.pong.height / 2 - this.height / 2;
    }

    public void render(Graphics g) { // iz4ertava top4eto
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

}
