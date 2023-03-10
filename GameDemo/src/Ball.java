import java.awt.Color;
import java.awt.Graphics;

public class Ball {

    private int x, y, cx, cy, speed, size;
    private Color color;
    
    static final int MAX_SPEED = 7;

    public Ball(int x, int y, int cx, int cy, int speed, Color color, int size) {
        this.x = x;
        this.y = y;
        this.cx = cx;
        this.cy = cy;
        this.speed = speed;
        this.color = color;
        this.size = size;
    }
    
    public void moveBall(){
        x += cx;
        y += cy;
    }
    
    /**
     * Detect collision with screen borders and reverse direction
     * @param top - the y value of the top of the screen
     * @param bottom - the y value of the bottom of the screen
     */
    public void bounceOffEdges(int top, int bottom){

        //if the y value is at the bottom of the screen
        if (y > bottom - size){
            reverseY();
        }
        //if y value is at top of screen
        else if(y < top){
            reverseY();
        }

    }

    /**
     * Reverse's the ball's change in x value
     */
    public void reverseX(){
        cx *= -1;
    }

    /**
     * Reverse's the ball's change in y value
     */
    public void reverseY(){
        cy *= -1;
    }
    
    public void increaseSpeed(){

        if(speed < MAX_SPEED){
            speed++;

            if(cx > 0){
                cx = speed;
            }
            else if(cx < 0){
                cx = speed * -1;
            }
            if(cy > 0){
                cy = speed;
            }
            else if(cy < 0){
                cy = speed * -1;
            }
        }
    }
    
  	public int getY(){
          return y;
      }
  	
  	public int getX(){
  	    return x;
  	}
  	
  	public int getSize() {
  		return size;
  	}
    
    public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setCx(int cx) {
		this.cx = cx;
	}

	public void setCy(int cy) {
		this.cy = cy;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, size, size);

    }
}
