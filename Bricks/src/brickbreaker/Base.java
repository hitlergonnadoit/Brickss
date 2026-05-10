
package brickbreaker;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Base extends JButton implements KeyListener {
    private final int BASE_WIDTH = 130;
    private final int BASE_HEIGHT = 15;
    public int x = 325;
    public int y = 400;
    public int velocity = 0;
    private boolean isRightKeyPressed = false;
    private boolean isLeftKeyPressed = false;
    
    public Base() {
        super.setPreferredSize(new Dimension(BASE_WIDTH, BASE_HEIGHT));
        super.setBounds(x, y, BASE_WIDTH, BASE_HEIGHT);
        super.setBorderPainted(false);
        super.setFocusable(true);
        super.addKeyListener(this);
        
        // Load base image
        Image baseImage;
        try {
            baseImage = ImageIO.read(new File("assets/base2.jpg")).getScaledInstance(BASE_WIDTH, BASE_HEIGHT, Image.SCALE_SMOOTH);
            super.setIcon(new ImageIcon(baseImage));
        } catch (IOException ex) {
            System.out.println("[ERROR] Unable to load base image");
        }
    }
    
    public void move() {
        if (isRightKeyPressed && this.x + this.getBASE_WIDTH() < 780) {
            this.x += 5;
        } else if (isLeftKeyPressed && this.x >= 5) {
            this.x -= 5;
        }
        
        this.setBounds(this.x, this.y, this.BASE_WIDTH, this.BASE_HEIGHT);
        
        if (this.x <= 5 || (this.x + this.BASE_WIDTH) > 775)
            this.velocity = 0;
    }

    public int getBASE_WIDTH() {
        return BASE_WIDTH;
    }

    public int getBASE_HEIGHT() {
        return BASE_HEIGHT;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_LEFT) {
            isLeftKeyPressed = true;
        }
        
        if (code == KeyEvent.VK_RIGHT) {
            isRightKeyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_LEFT) {
            isLeftKeyPressed = false;
        }
        
        if (code == KeyEvent.VK_RIGHT) {
            isRightKeyPressed = false;
        }
    }
    
}
