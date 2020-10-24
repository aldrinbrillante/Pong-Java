
/**
 * @author Aldrin Brillante
 * @TeamName siniGANG
 * @version 1.0
 **/

// ball is 50x50
//paddles are 50x10 


import java.awt.Color;

public class Ball1FuntionToCallOnMyBallAndMainSoICanFinallyGoToSleep {
	
	//let's set and declare the x position of the ball1... again
	static int posX = 250;
	//let's set and declare the y position of the ball1... again
	static int posY = 50;
	//Let us declare the x direction of the ball1
	static int directionX = 5;		
	//Let us declare the y direction of the probe this time
	static int directionY = 5;		
	// Stores X Position of paddle1
	static int paddle1X = 500/2;
	
	
	static EZImage ball1Picture;		// Stores picture of ball1
	static EZImage paddle1Picture;		// Stores picture of paddle1
	static EZSound bounceSound;
	//static int bounces = 0; 		// Stores the number of times it bounced off // Leaving this on comment JUST in case we need to declare an int of the ball "bounce"
	
	
	/*  Leaving this Music void on comment in case we don't use it
	public static void coolMusic(){
		saberSound.loop();
	}
	*/
	

	public static void moveBall1(){
		
		//Let us set the position of the ball1 in the game constant
		ball1Picture.translateTo(posX, posY); 							
		posX= posX+directionX;
		posY= posY+directionY;
		
		
		// Now let us set collision points so the ball1 won't leave 
		if (posX > 500 ) {
			directionX = -directionX;
		}
		if (posX < 0) {
			directionX = -directionX;
		}

		
		if (posY > 600 ) {		// if the ball reaches all the way to the bottom reset it to the top
			posY=200;
		}
		if (posY < 0) {
			directionY = -directionY;
		}

	}
		
	
	
	public static void loop() {

	
		while (true) {
			moveBall1();			
			EZ.refreshScreen(); 
		}
	}
}
