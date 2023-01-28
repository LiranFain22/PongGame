import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PlayState extends GameState {

	boolean active;
	float deltaTimeAverage;
	Ball gameBall;
	Paddle userPaddle;
	Paddle pcPaddle;
	static String WINNER;
	ArrayList<String> userLives;
	ArrayList<String> pcLives;
	
	private int bounceCount;
	
	String message;
	
	public PlayState() {
		gameBall = new Ball(300, 200, 3, 3, 3, Color.YELLOW, 10);
		userPaddle = new Paddle(10, 200, 75, 3, Color.BLUE);
	    pcPaddle = new Paddle(610, 200, 75, 3, Color.RED);
	    initLives();
	}
	
	private void initLives() {
		userLives = new ArrayList<>();
		pcLives = new ArrayList<>();
		
		for(int i = 0; i < 3; i++) {
			userLives.add("♥");
			pcLives.add("♥");
		}
	}

	public void enter(Object memento) {
		active = true;
		deltaTimeAverage = 0;
	}
	
	public void processKeyReleased(int aKeyCode) {
		if (aKeyCode == KeyEvent.VK_ESCAPE)
			System.exit(0);
		
		
		if (aKeyCode == KeyEvent.VK_UP) {
			for(int i = 0; i < 10; i++)
				userPaddle.moveTowards(0);
		}
		
		if (aKeyCode == KeyEvent.VK_DOWN) {
			for(int i = 0; i < 10; i++)
				userPaddle.moveTowards(Game.HEIGHT);
		}
			
	}
	
	public void update(long deltaTime) {
		deltaTimeAverage = deltaTimeAverage* 0.9f + 0.1f*(float)deltaTime;
		gameLogic();
	}
	
	public void gameLogic(){
		
	    gameBall.moveBall();
	    gameBall.bounceOffEdges(0, Game.HEIGHT);
	    pcPaddle.moveTowards(gameBall.getY());
	    
	    //check if someone lost
	    if(gameBall.getX() < 0){
	        //player has lost
	        userLives.remove(0);
	        reset();
	    }
	    else if(gameBall.getX() > Game.WIDTH){
	        //PC has lost
	        pcLives.remove(0);
	        reset();
	    }
	    
	    if(pcPaddle.checkCollision(gameBall)){
	        //reverse ball if they collide
	        gameBall.reverseX();
	        //increase the bounce count
	        bounceCount++;
	        
	    } else if(userPaddle.checkCollision(gameBall)) {
	    	//reverse ball if they collide
	        gameBall.reverseX();
	        //increase the bounce count
	        bounceCount++;
	    } 

	    if (bounceCount == 5){
	        bounceCount = 0;
	        gameBall.increaseSpeed();
	    }
	    
	    if (pcLives.size() == 0) {
	    	WINNER = "YOU WON!";
	    	initLives();
	    	active = false;
	    }
	    
	    if (userLives.size() == 0) {
	    	WINNER = "YOU LOST! TRY AGAIN.";
	    	initLives();
	    	active = false;
	    }
	}
	
	public boolean isActive() { return active; }
	
	public String next() {
		return "GameOver";
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
		
	    userPaddle.paint(g);
	    pcPaddle.paint(g);
	    
	    g.setColor(Color.WHITE);
	    g.drawString("Lives -  " + userLives + " ", 20, 20);
	    g.drawString("Lives -  " + pcLives + " ", 500, 20);
	}

}
