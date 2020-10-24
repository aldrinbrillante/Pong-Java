/**
 * @author Lauryn Lim
 * @version 1.0
 * @date 11/26/18
 **/


import com.sun.glass.events.KeyEvent;

public class Multiplayer {

		EZImage ballPicture;
		EZImage paddle1;
		EZImage paddle2;
		
	Multiplayer(){
                ballPicture = EZ.addImage("ball.jpg", 0, 0);
                paddle1 = EZ.addImage("paddle.png", 0, 0);
                paddle2= EZ.addImage("paddle.png", 0, 0);
	}
	
	void move() {
		int posX = 0;
		int posY = 200;
		int directionX = 5;
		int directionY = 5;
		int rotationAngle = 0;
		int paddle1X = 700/2;
		int paddle2X = 700/2;

		int bounces = 0;
		while (true) {
			ballPicture.translateTo(posX,posY);
			ballPicture.rotateTo(rotationAngle);

			posX =  posX+directionX;
			posY = posY+directionY;

			rotationAngle+=directionX;

			if (posX > 500) {
				directionX = -directionX;
			}
			if (posX <0) {
				directionX = -directionX;
			}
			if ((posX > 500)|| (posX <0)) {
				directionX = -directionX;
			}
			if (posY >500) {
				posY=0;
			}
			if (posY<0) {
				directionY = -directionY;
			}

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
			if (EZInteraction.isKeyDown(KeyEvent.VK_RIGHT)) {
				paddle2X+=10;
				if (paddle2X >500) {
					paddle2X-=10;
				}
			}
			if (EZInteraction.isKeyDown(KeyEvent.VK_LEFT)) {
				paddle2X-=10;
				if(paddle2X <= 500) {
					paddle2X+=10;
				}
			}
			EZ.refreshScreen();
		}
	}
}

