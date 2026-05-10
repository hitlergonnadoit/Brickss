package brickbreaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Ball extends JButton {

    private final int BALL_WIDTH = 20;
    private final int BALL_HEIGHT = 15;
    public int xVelocity = 4;
    public int yVelocity = -4;
    public int x = 325;
    public int y = 350;

    public Ball() {
        super.setPreferredSize(new Dimension(BALL_WIDTH, BALL_HEIGHT));
        super.setBounds(x, y, BALL_WIDTH, BALL_HEIGHT);
        super.setBackground(new Color(255, 255, 255, 0));
        super.setBorderPainted(false);
        super.setFocusable(false);

        // Load ball image
        try {
            Image baseImage = ImageIO.read(new File("assets/ball.png")).getScaledInstance(BALL_WIDTH, BALL_HEIGHT, Image.SCALE_SMOOTH);
            super.setIcon(new ImageIcon(baseImage));
        } catch (IOException ex) {
            System.out.println("[ERROR] Unable to load ball image");
        }
    }

    public void moveBall() {
        if (this.x < 0 || (this.x + this.getBALL_WIDTH()) > 790) {
            this.xVelocity *= -1;
        }
        if (this.y < 0) {
            this.yVelocity *= -1;
        }

        this.x += this.xVelocity;
        this.y += this.yVelocity;
        this.setBounds(this.x, this.y, this.BALL_WIDTH, this.BALL_HEIGHT);

    }


    public int getBALL_WIDTH() {
        return BALL_WIDTH;
    }

    public int getBALL_HEIGHT() {
        return BALL_HEIGHT;
    }

}
