package brickbreaker;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Brick extends JButton {

    private final int BRICK_WIDTH = 70;
    private final int BRICK_HEIGHT = 30;

    public Brick(ImageIcon brickImage) {
        super.setPreferredSize(new Dimension(BRICK_WIDTH, BRICK_HEIGHT));
        super.setBorderPainted(true);
        super.setFocusable(false);
        
        if (brickImage != null) {
            super.setIcon(brickImage);
        } else {
            super.setBackground(Color.red);
        }
    }

    public int getBRICK_WIDTH() {
        return BRICK_WIDTH;
    }

    public int getBRICK_HEIGHT() {
        return BRICK_HEIGHT;
    }
    
    
    
}
