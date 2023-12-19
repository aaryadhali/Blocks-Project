import javax.swing.*;
import java.awt.*;

public class Block {
    private int x;
    private int y;
    private int width;
    private int height;
    private int dx;
    private int dy;
    private Color color;


    public Block(int x, int y, int width, int height,int velocityx, int velocityy,Color color) {

        this.x = x;

        this.y = y;

        this.width = width;

        this.height = height;

        this.dx = velocityx;

        this.dy = velocityy;

        this.color = color;
    }


    public Block(int x, int y, int width, int height,Color color) {
        this.x = x;

        this.y = y;

        this.width = width;

        this.height = height;

        this.color = color;
    }


    public void move() {
        x += dx;
        y += dy;
        if (x < 0 || x > 480) {
            dx = -dx;
        }
        if (y < 0 || y > 480) {
            dy = -dy;
        }
    }

    public void resetPosition(){
        x=0;
        y=0;
        width = 0;
        height = 0;
        color = color.BLACK;
    }


    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, width, height);

    }
    public boolean contains(int x, int y){
        return x >= this.x && x <= this.x + width &&  y >= this.y && y <= this.y + height;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
