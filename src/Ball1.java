import java.awt.Rectangle;

/**
 * @author Aldrin Brillante
 * @TeamName siniGANG
 * @version 1.0
 **/

/*
 * This class creates the ball1, along with the single player option in the menu, creating an AI opponent as another paddle
 */


// ball is 50x50
//paddles 50x10 


public class Ball1 {
	
	
	public static final int WINDOW_HEIGHT = 0; //constant for the AIOpp class

	//use this to call class attributes onto Main
	public void start() { 
	
	
	//Note game graphics are at 500x500
		
		
		EZImage ballPicture = EZ.addImage("ball.png", 0,0);
		EZSound bounceSound = EZ.addSound("bounceBitch.wav");
		EZImage paddle1Picture = EZ.addImage("paddle1.png", 0,0);
		EZImage paddle2Picture = EZ.addImage("paddle2.png", 0,0);


		
		int posX = 250;			// This declares int of the x position of the ball
		int posY = 50;			// This declares int the y position of the ball
		int directionX = 5;		// This declares int the x direction of the ball
		int directionY = 5;		// This declares int the y direction of the ball
		
		int paddle1X = 500/2;		// This declares int X Position of the paddle1 being used
		int paddle2X = 500/2;		// This declares int X Position of the paddle2 being used


		
		int passedYou = 5;		// This declares int the number of times the ball has bounced off the paddle
		int totalAttempts = 5; 
		
		int ballSpeed = 5; 
		
		//make our rextangles for paddle1, paddle2, and ball.
		Rectangle paddle1Rect = new Rectangle (0, 0, 50, 10);
		Rectangle paddle2Rect = new Rectangle (0, 0, 50, 10);
		Rectangle ball1Rect = new Rectangle (0, 0, 50, 50); 

		
		//use the width and height but dont worry about x and y pos because they are going to be translated.
		
		
		while (true) {

			ballPicture.translateTo(posX, posY); // Set the position of the ball.
			paddle1Picture.translateTo(paddle1X, 20);
			paddle2Picture.translateTo(paddle2X, 460);
			
			paddle2X += ballSpeed;
			//if paddle2X is less than ball1X, speed > 0
			
				if (paddle2X < posX) {
					ballSpeed = Math.abs(ballSpeed); // this makes the ballSPeed positive with he if statement, making it move to the right 
				} else { 
					ballSpeed = - Math.abs(ballSpeed);
				}
			
			//if paddleX is greater than ballX, speed < 0
			
			posX= posX+directionX;
			posY= posY+directionY;
			
			//lets update our rectangles! Creates the 'rectangle' above the images of the paddle1 and paddle2
			paddle1Rect.setLocation(paddle1X, 20);
			paddle2Rect.setLocation(paddle2X, 460);
			ball1Rect.setLocation(posX, posY);
			paddle1Rect.translate(-1 * paddle1Picture.getWidth() / 2, -1 * paddle1Picture.getHeight() / 2);
			paddle2Rect.translate(-1 * paddle2Picture.getWidth() / 2,-1 *  paddle2Picture.getHeight() / 2);
			ball1Rect.translate(-1 * ballPicture.getWidth() / 2, -1 * ballPicture.getHeight() / 2);
			
			
			if (EZInteraction.isKeyDown('d')) {
				paddle1X+=10;
				if (paddle1X > 500) {
					paddle1X-=10;
				}
			}
			if (EZInteraction.isKeyDown('a')) {
				paddle1X-=10;
				if (paddle1X <= 0) {
					paddle1X+=10;
				}
			}
			
			//lets do col detection!
			//lets see if our ball hit paddle1
			if (paddle1Rect.intersects(ball1Rect)) {
				//this code will run when the ball is touching the paddle1Rect
				directionY = Math.abs(directionY); //this will make the ball1 flip direction 
				bounceSound.play();
			}
			
			
			if (paddle2Rect.intersects(ball1Rect)) {
				//this code will run when the ball is touching the paddle1Rect
				directionY = - Math.abs(directionY); //this will make the ball1 flip direction 
				bounceSound.play();
			}
			
				
				
			
			if (posX > 500 ) {
				directionX = -directionX;
			}
			if (posX < 0) {
				directionX = -directionX;
			} 
			
			if (posY > 600 ) {		// if the ball reaches all the way to the bottom reset it to the y-axis of 200
				posY=200;		//declares that the Y position of the ball will move the Y-position of 200... around the middle of the screen
				directionY = Math.abs(directionY);
				
			}
			if (posY < -30) {				//declares if Y<0, then the direction of the ball will go opposite 
				directionY = Math.abs(directionY);
				posY = 200;
				totalAttempts = passedYou - 1;
				System.out.println (totalAttempts + " attempts left");
			}
			if (totalAttempts <= 0) {				 
				System.out.println ("GAME OVER BITCH");
				  
			}
			
			int halfPaddle1Width = paddle1Picture.getWidth()/2;
			int halfPaddle2Width = paddle2Picture.getWidth()/2;
	
			
			
			// Make sure to do this or else this whole coding all night was pointless. Got it? Cuz I don't 
			EZ.refreshScreen();
			
		}



    }

	public static Object getBall() {
		// TODO Auto-generated method stub .... 
		return null;
	}
	
	
	
}
