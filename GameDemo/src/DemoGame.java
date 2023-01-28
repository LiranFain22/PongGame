
public class DemoGame extends Game {

	public DemoGame() {
		GameState welcome = new WelcomeState();
		GameState play = new PlayState();
		GameState gameOver = new GameOverState();
		stateMachine.installState("Play", play);
		stateMachine.installState("Welcome", welcome);
		stateMachine.installState("GameOver", gameOver);
		stateMachine.setStartState(welcome);
	}
	
	public static void main( String[] args ) {
	    Game app = new DemoGame();
	    app.setTitle( "Pong Game" );
	    app.setVisible( true );
	    app.run();
	    System.exit( 0 );
	  }
}
