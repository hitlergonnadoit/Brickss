package brickbreaker;

import brickbreaker.AudioPlayback;
import brickbreaker.Settings;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    private final int PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 500;
    private final Base base;
    private final Ball ball;
    ArrayList<Brick> bricks = new ArrayList();
    private final BackgroundImageLabel backgroundImageLabel;
    private final Timer timer;
    private final Settings gameSettings;
    private int level;
    private long lastCollisionTime = 0;
    private final long COLLISION_COOLDOWN = 100;

    public GamePanel() {
        super.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        super.setLayout(new BorderLayout());

        backgroundImageLabel = new BackgroundImageLabel();
        base = new Base();
        ball = new Ball();
        backgroundImageLabel.add(ball);
        backgroundImageLabel.add(base);
        level = 1;
        for (Brick brick : createBrickPattern()) {
            bricks.add(brick);
            backgroundImageLabel.add(brick);
        }
        super.add(backgroundImageLabel, BorderLayout.NORTH);

        // Game settings
        gameSettings = Settings.getInstance();

        // Start gameloop
        timer = new Timer(16, this);
        timer.start();
    }

    /**
     * Main game-loop method. It repaints the game after every 16 milliseconds
     * (60 FPS)
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.repaint();

        base.move();
        ball.moveBall();
        checkCollisionsWithBase(base, ball);
        Brick collidedBrick = checkCollisionsWithBricks(bricks, ball);
        if (collidedBrick != null) {
            if (gameSettings.isSoundEffectsOn()) {
                AudioPlayback.playSoundEffect();
            }
            backgroundImageLabel.remove(collidedBrick);            
        }
        
        if (bricks.size()== 0){
            level++;
            
            for (Brick brick : createBrickPattern()) {
                bricks.add(brick);
                backgroundImageLabel.add(brick);
            }
        }

        if (ball.y >= 500) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Gameover");
        }
    }
    
    private ArrayList<Brick> createBrickPattern(){
        if (level == 1){
            return createLevelOneBrickPattern();
        }
        
        if (level == 2){
            return createLevelTwoBrickPattern();
        }
        
        if (level == 3){
            return createLevelThreeBrickPattern();
        }
        
        if (level == 4){
            return createLevelFourBrickPattern();
        }
        
        return createLevelFiveBrickPattern();
    }

    private ArrayList<Brick> createLevelOneBrickPattern() {
        ArrayList<Brick> bricks = new ArrayList();
        int xPos = 0;
        int yPos = 50;
        for (int i = 0; i < 28; i++) {
            Brick brick = new Brick(null);
            if (i % 7 == 0) {
                xPos = 0;
                yPos = yPos + brick.getBRICK_HEIGHT() + 20;
            }
            brick.setBounds(xPos, yPos, brick.getBRICK_WIDTH(), brick.getBRICK_HEIGHT());
            bricks.add(brick);
            xPos = xPos + brick.getBRICK_WIDTH() + 20;
        }
        return bricks;
    }
    
    private ArrayList<Brick> createLevelTwoBrickPattern() {
        ArrayList<Brick> bricks = new ArrayList();
        int xPos = 70;
        int yPos = 50;
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 7; j++) {
                // For every even row
                if (i % 2 == 0) {
                    Brick brick = new Brick(null);
                    // If the column number is even, only then create a brick
                    if (j % 2 == 0) {
                        brick.setBounds(xPos, yPos, brick.getBRICK_WIDTH(), brick.getBRICK_HEIGHT());
                        bricks.add(brick);
                        xPos = xPos + brick.getBRICK_WIDTH();
                        continue;
                    }
                    xPos = xPos + brick.getBRICK_WIDTH();
                } else {
                    Brick brick = new Brick(null);
                    // For every odd row, ignore the even columns
                    if (j % 2 == 0) {
                        xPos = xPos + brick.getBRICK_WIDTH();
                        continue;
                    }
                    // Add brick to array
                    brick.setBounds(xPos, yPos, brick.getBRICK_WIDTH(), brick.getBRICK_HEIGHT());
                    bricks.add(brick);
                    xPos = xPos + brick.getBRICK_WIDTH();
                }
            }
            // After creating bricks for a row
            xPos = 70; // Reset X position
            yPos = yPos + 30; // Update Y position
        }
        return bricks;
    }
    
    private ArrayList<Brick> createLevelThreeBrickPattern() {
        ArrayList<Brick> bricks = new ArrayList();
        // Box 1
        int xPos1 = 30;
        int yPos1 = 20;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                Brick brick = new Brick(null);
                brick.setBounds(xPos1, yPos1, brick.getBRICK_WIDTH(), brick.getBRICK_HEIGHT());
                bricks.add(brick);
                xPos1 += brick.getBRICK_WIDTH();
            }
            xPos1 = 30;
            yPos1 += 30;
        }
        // Box 2
        int xPos2 = 400;
        int yPos2 = 20;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                Brick brick = new Brick(null);
                brick.setBounds(xPos2, yPos2, brick.getBRICK_WIDTH(), brick.getBRICK_HEIGHT());
                bricks.add(brick);
                xPos2 += brick.getBRICK_WIDTH();
            }
            xPos2 = 400;
            yPos2 += 30;
        }
        return bricks;
    }
    
    private static ArrayList<Brick> createLevelFourBrickPattern() {
        ArrayList<Brick> bricks = new ArrayList();
        int xPos = 120;
        int yPos = 20;
        int nextXPos = 120;
        int bricksCount = 8;
        while (bricksCount != 0) {
            for (int i = 0; i < bricksCount; i++) {
                Brick brick = new Brick(null);
                brick.setBounds(xPos, yPos, brick.getBRICK_WIDTH(), brick.getBRICK_HEIGHT());
                bricks.add(brick);
                xPos += brick.getBRICK_WIDTH();
            }
            bricksCount -= 1;
            nextXPos += 35;
            xPos = nextXPos;
            yPos += 30;
        }
        return bricks;
    }
    
    private ArrayList<Brick> createLevelFiveBrickPattern() {
        ArrayList<Brick> bricks = new ArrayList();
        // Triangle one
        int xPos = 150;
        int yPos = 50;
        int nextXPos = 150;
        int bricksCount = 1;
        while (bricksCount <= 5) {
            for (int i = 0; i < bricksCount; i++) {
                Brick brick = new Brick(null);
                brick.setBounds(xPos, yPos, brick.getBRICK_WIDTH(), brick.getBRICK_HEIGHT());
                bricks.add(brick);
                xPos += brick.getBRICK_WIDTH();
            }
            bricksCount += 1;
            nextXPos -= 35;
            xPos = nextXPos;
            yPos += 30;
        }
        // Triangle two
        int xPos2 = 550;
        int yPos2 = 50;
        int nextXPos2 = 550;
        int bricksCount2 = 1;
        while (bricksCount2 <= 5) {
            for (int i = 0; i < bricksCount2; i++) {
                Brick brick = new Brick(null);
                brick.setBounds(xPos2, yPos2, brick.getBRICK_WIDTH(), brick.getBRICK_HEIGHT());
                bricks.add(brick);
                xPos2 += brick.getBRICK_WIDTH();
            }
            bricksCount2 += 1;
            nextXPos2 -= 35;
            xPos2 = nextXPos2;
            yPos2 += 30;
        }
        return bricks;
    }

    private void checkCollisionsWithBase(Base base, Ball ball) {
        // Using time base collision checks to avoid some collision glitches
        // like, multiple collision detections at a time
        long currentTime = System.currentTimeMillis();
        // CHECK COLLISION WITH TOP SIDE OF THE BASE
        if ((currentTime - lastCollisionTime) > COLLISION_COOLDOWN) {
            if (ball.x >= base.x && ball.x <= base.x + base.getBASE_WIDTH() && ball.y + ball.getBALL_HEIGHT() >= base.y && ball.y + ball.getBALL_HEIGHT() <= base.y + base.getBASE_HEIGHT()) {
                ball.yVelocity *= -1;
                if (ball.x > 400) {
                    ball.xVelocity *= -1;
                }
                lastCollisionTime = currentTime;
            } // CHECK COLLISIONS WITH LEFT SIDE OF THE BASE
            else if (ball.x + ball.getBALL_WIDTH() >= base.x && ball.x + ball.getBALL_WIDTH() <= base.x + base.getBASE_WIDTH() / 4 && ball.y >= base.y && ball.y <= base.y + base.getBASE_HEIGHT()) {
                ball.xVelocity *= -1;
                ball.yVelocity *= -1;
                lastCollisionTime = currentTime;
            } // CHECK COLLISIONS WITH RIGHT SIDE OF THE BASE (!!! Not tested yet !!!)
            else if (ball.x <= base.x + base.getBASE_WIDTH() && ball.x >= base.x + base.getBASE_WIDTH() - base.getBASE_WIDTH() / 4 && ball.y + ball.getBALL_HEIGHT() >= base.y && ball.y + ball.getBALL_HEIGHT() <= base.y + base.getBASE_HEIGHT()) {
                ball.xVelocity *= -1;
                ball.yVelocity *= -1;
                lastCollisionTime = currentTime;
            }
        }
    }

    private Brick checkCollisionsWithBricks(ArrayList<Brick> bricks, Ball ball) {
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            Rectangle bounds = brick.getBounds();
            if (ball.x >= bounds.x && ball.x <= bounds.x + bounds.width && ball.y <= bounds.y + bounds.height && ball.y >= bounds.y) {
                System.out.println("Brick bottom collided");
                iterator.remove();
                ball.yVelocity *= -1;
                return brick;
            } // BALL'S RIGHT SIDE --> BRICK'S LEFT SIDE
            else if (ball.x + ball.getBALL_WIDTH() >= bounds.x && ball.x + ball.getBALL_WIDTH() <= bounds.x + 5 && ball.y > bounds.y && ball.y < bounds.y + bounds.height) {
                System.out.println("Brick left collided");
                iterator.remove();
                ball.xVelocity *= -1;
                return brick;
            } // BALL'S LEFT SIDE --> BRICK'S RIGHT SIDE
            else if (ball.x <= bounds.x + bounds.width && ball.y >= bounds.y && ball.x >= bounds.x && ball.y < bounds.y + bounds.height) {
                System.out.println("Brick right collided");
                iterator.remove();
                ball.xVelocity *= -1;
                return brick;
            } // BALL'S BOTTOM SIDE --> BRICK'S TOP SIDE
            else if (ball.x >= bounds.x && ball.x <= bounds.x + bounds.width && ball.y + ball.getHeight() >= bounds.y && ball.y + ball.getHeight() <= bounds.y + bounds.height) {
                System.out.println("Brick top collided");
                iterator.remove();
                ball.yVelocity *= -1;
                return brick;
            }
        }
        return null;
    }

}
