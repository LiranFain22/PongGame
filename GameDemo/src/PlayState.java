import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class PlayState extends GameState {

	boolean active;
//	float x;
	float deltaTimeAverage;
	Ball gameBall;
	Paddle userPaddle;
	Paddle pcPaddle;
	String winner;
	
	private int userScore, pcScore, bounceCount;
	
	String message;
	
	public PlayState() {
		gameBall = new Ball(300, 200, 3, 3, 3, Color.YELLOW, 10);
		userPaddle = new Paddle(10, 200, 75, 3, Color.BLUE);
	    pcPaddle = new Paddle(610, 200, 75, 3, Color.RED);
	    userScore = 0; 
	    pcScore = 0;
	}
	

	public void enter(Object memento) {
		active = true;
		deltaTimeAverage = 0;
	}
	
	public void processKeyReleased(int aKeyCode) {
		if (aKeyCode == KeyEvent.VK_ESCAPE)
			System.exit(0);
		
		if (aKeyCode == KeyEvent.VK_Q)
			active = false;
		
		if (aKeyCode == KeyEvent.VK_UP) {
			userPaddle.moveTowards(0);
			userPaddle.moveTowards(0);
			userPaddle.moveTowards(0);
			userPaddle.moveTowards(0);
			userPaddle.moveTowards(0);
			userPaddle.moveTowards(0);
			userPaddle.moveTowards(0);
		}
		
		if (aKeyCode == KeyEvent.VK_DOWN) {
			userPaddle.moveTowards(Game.HEIGHT);
			userPaddle.moveTowards(Game.HEIGHT);
			userPaddle.moveTowards(Game.HEIGHT);
			userPaddle.moveTowards(Game.HEIGHT);
			userPaddle.moveTowards(Game.HEIGHT);
			userPaddle.moveTowards(Game.HEIGHT);
			userPaddle.moveTowards(Game.HEIGHT);
		}
			
	}
	
	public void update(long deltaTime) {
		deltaTimeAverage = deltaTimeAverage* 0.9f + 0.1f*(float)deltaTime;
		gameLogic();
//		x = x + 0.1f*deltaTime;
//		if (x > 640)
//			x = x - 640;
		
	}
	
	public void gameLogic(){

	    //move the ball one frame
	    gameBall.moveBall();
	    
	    //edge check/bounce
	    gameBall.bounceOffEdges(0, Game.HEIGHT);
	    
	    //move the PC paddle towards the ball y position
	    pcPaddle.moveTowards(gameBall.getY());
	    
	  //check if someone lost
	    if(gameBall.getX() < 0){
	        //player has lost
	        pcScore++;
	        reset();
	    }
	    else if(gameBall.getX() > Game.WIDTH){
	        //PC has lost
	        userScore++;
	        reset();
	    }
	    
	  //check if ball collides with either paddle
	    if(pcPaddle.checkCollision(gameBall) || userPaddle.checkCollision(gameBall)){
	        //reverse ball if they collide
	        gameBall.reverseX();
	        //increase the bounce count
	        bounceCount++;
	    }

	    //after 5 bounces
	    if (bounceCount == 5){
	        //reset counter
	        bounceCount = 0;
	        //increase ball speed
	        gameBall.increaseSpeed();
	    }
	    
	    if (userScore == 5) {
	    	winner = "YOU WON!";
	    }
	    
	    if (pcScore == 5) {
	    	winner = "YOU LOST! TRY AGAIN.";
	    }
	}
	
	public boolean isActive() { return active; }
	
	public String next() {
		return "Welcome";
	}
	
	public void reset(){
		//pause for a second
	    try{
	        Thread.sleep(1000);
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
		
	    //reset ball
    gameBall.setX(300);
    gameBall.setY(200);
    gameBall.setCx(3);
    gameBall.setCy(3);
    gameBall.setSpeed(3);
    bounceCount = 0;
}

	public void render(GameFrameBuffer aGameFrameBuffer) {
		Graphics g = aGameFrameBuffer.graphics();
		
		g.setColor(Color.white);
		gameBall.paint(g);
		
		//draw the paddles
	    userPaddle.paint(g);
	    pcPaddle.paint(g);
	    
	  //update score
	    g.setColor(Color.WHITE);
	    //the drawString method needs a String to print, and a location to print it at.
	    g.drawString("Score - User [ " + userScore + " ]   PC [ " + pcScore + " ]", 250, 20   );
	    
//		g.drawOval((int)x, 100, 10, 10);
//		message = "" + (int)deltaTimeAverage;
//		g.drawString(message, 10, 10);

	}

}
