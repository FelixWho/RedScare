import ucigame.*;
import java.util.*;

public class main extends Ucigame{

//Players
	Sprite p1, p2;
	
//projectiles and # of bounces
	Sprite ball1, ball2;
	int numB1 = 0, numB2 = 0;
//Border
	Sprite l,u,r,d;
	
//walls
	ArrayList<organ> vertWalls;
	ArrayList<organ> horiWalls;
	
//centers
	final int p1CenterX = 20;
	final int p1CenterY = 40;
	final int p2CenterX = 20;
	final int p2CenterY = 40;
	
//rotation keeps track of degrees rotated, make player2 start out facing downwards
	int rotation1 = 0;
	int rotation2 = 180;
	
//sprite for text on right side
	Sprite t1;
	
	boolean slPressed = false, shPressed = false;
	
	 public void setup(){
		 //Initial Setup
		 window.size(900, 800);
		 window.title("Boom Boom");
		 framerate(60);
		 canvas.background(255,255,255);
		 
		 //Extra Setup for Restart Purposes
		 doLocalSetup();
		 
	 }
	 
	 /*
	  * Local Setup to make restarting the game easier. Loads all temporary sprites, regenerates the maze.
	  */
	 public void doLocalSetup(){
		 //Map with border = 3
		 AMazing map = new AMazing(3);
		 map.drawMaze();
		 
		 //Init Walls
		 vertWalls = new ArrayList<organ>();
		 horiWalls = new ArrayList<organ>();
		 
		 // add walls to the proper arrayLists
		 for(int i = 1; i <= 3; i++){
			 for(int j = 1; j <= 3; j++){
				 if(map.cellSouthWall[i][j]){
					 horiWalls.add(new organ(makeSprite(getImage("wallH.png"),220,20), i,j,3));
				 }
				 if(map.cellNorthWall[i][j]){
					 horiWalls.add(new organ(makeSprite(getImage("wallH.png"),220, 20), i,j,1));
				 }
				 if(map.cellEastWall[i][j]){
					 vertWalls.add(new organ(makeSprite(getImage("wallV.png"), 20, 220), i,j,2));
				 }
				 if(map.cellWestWall[i][j]){
					 vertWalls.add(new organ(makeSprite(getImage("wallV.png"),20,220), i,j,4));
				 }
			 }
		 }
		 System.err.println(vertWalls);
		 System.err.println(horiWalls);
		 
		 //Init window border sprites
		 l = new Sprite(getImage("borderV.png"));
		 u = new Sprite(getImage("borderH.png"));
		 d = new Sprite(getImage("borderH.png"));
		 r = new Sprite(getImage("borderV.png"));
		 l.position(-10, 0);
		 r.position(790, 0);
		 u.position(0, -10);
		 d.position(0, 790);
		 
		 //setup up player sprites
		 p1 = new Sprite(getImage("player1.png", 255,255,255));
		 p2 = new Sprite(getImage("player2.png", 255,255,255));
		 p1.position(90, 70);
		 p2.position(680, 650);
		 rotation1 = 0;
		 rotation2 = 180;
		 
		 //set up text
		 t1 = makeSprite(getImage("white.png"),100,800);
		 t1.position(800, 0);
		 t1.font("Arial", BOLD, 17);
		 
		 //set up projectiles
		 ball1 = new Sprite(getImage("pokeball.png",255,255,255));
		 ball2 = new Sprite(getImage("pokeball.png",255,255,255));
		 ball1.position(p1.x(), p1.y());
		 ball2.position(p2.x(), p2.y());
		 ball1.hide();
		 ball2.hide();
		 
	 } 
	 
	 public void draw(){
		 canvas.clear();
		/*
		 for(organ o: vertWalls){
			 int x = (o.x())*220;
			 int y = (o.y())*220;
			 if(o.dir == 4){
				 x = (o.x()-1)*220;
			 }
			 
			 o.spr.position(x, y);
			 o.spr.draw();
		 }
		 for(organ o: horiWalls){
			 int x = (o.x())*220;
			 int y = (o.y())*220;
			 if(o.dir == 3){
				 y = (o.y()-1)*220;
			 }
			 
			 o.spr.position(x, y);
			 o.spr.draw();
		 }
		 */
		
	
		 //draw the borders
		 l.draw();
		 u.draw();
		 r.draw();
		 d.draw();
		 
		 //draw players -- do this last so that they stay on top of the layer
		 p1.stopIfCollidesWith(l,u,r,d,LEFTEDGE,RIGHTEDGE,BOTTOMEDGE,TOPEDGE,p2, PIXELPERFECT);
		 p2.stopIfCollidesWith(l,r,u,d,LEFTEDGE,RIGHTEDGE,BOTTOMEDGE,TOPEDGE, p1, PIXELPERFECT);
		 p1.rotate(rotation1, p1CenterX, p1CenterY);
		 p2.rotate(rotation2,p2CenterX, p2CenterY); 
		 p1.draw();
		 p2.draw();
		 
		 //draw side text
		 t1.draw();
		 t1.putText("Player 2", 10, 50);
		 t1.putText("Power-Ups", 7, 90);
		 t1.putText("Player 1", 10, 550);
		 t1.putText("Power-Ups", 7, 590);
		 
		 //draw projectiles
		 ball1.bounceIfCollidesWith(l,u,r,d, p1, p2 , PIXELPERFECT);
		 ball1.move();
		 ball2.bounceIfCollidesWith(l,u,r,d, p1, p2 , PIXELPERFECT);
		 if(ball1.collided()){
			 numB1++;
		 }
		 if(numB1==3){
			 numB1 =0;
			 ball1.hide();
			 ball1.position(-20, -20);
		 }
		 ball2.move();
		 ball1.draw();
		 ball2.draw();
	 }
	 
	 public void onKeyPress(){
		 
		 //restart
		 if(keyboard.key() == keyboard.R){
			 doLocalSetup();
		 }
		 
		 /*
		  * movement for player1
		  */
		 
		// left rotates left.
			if (keyboard.isDown(keyboard.LEFT)){
				rotation1 -= 4;
				
			}
			// right rotates right.
			if (keyboard.isDown(keyboard.RIGHT)){
				rotation1 += 4;
			}
			//move forward
			if (keyboard.isDown(keyboard.UP)){
				double thetaRad = 3.14159 * (double)(rotation1 +90)/ 180.0;
				
				
				p1.nextX(p1.x()+(2*Math.cos(thetaRad)));
				p1.nextY(p1.y()+(2*Math.sin(thetaRad)));
						
			}
			if (rotation1 < 0)
				rotation1 += 360;
			else if (rotation1 > 359)
				rotation1 -= 360;
			//fire
			if(keyboard.key() == keyboard.SLASH){
				slPressed = true;
			}
			
		 /*
		  * movement for player 2
		  */
			
			//a rotates left
		if (keyboard.isDown(keyboard.A)){
			rotation2 -= 4;
			
		}
			// d rotates right.
		if (keyboard.isDown( keyboard.D)){
			rotation2 += 4;
		}
		if (keyboard.isDown(keyboard.W)){
			double thetaRad = 3.14159 * (double)(rotation2 +90)/ 180.0;
			p2.nextX(p2.x()+(2*Math.cos(thetaRad)));
			p2.nextY(p2.y()+(2*Math.sin(thetaRad)));
		}
		if (rotation2 < 0)
			rotation2 += 360;
		else if (rotation2 > 359)
			rotation2 -= 360;
		
		if(keyboard.key() == keyboard.SHIFT){
			shPressed = true;
		}
	 }
	 
	 public void onKeyRelease(){
		 if(slPressed && !keyboard.isDown(keyboard.SLASH)){
			 
			 double thetaRad = 3.14159 * (double)(rotation1+90) / 180.0;
				numB1 = 0;
				int x = p1.xPixel() + p1CenterX;
				int y = p1.yPixel() + p1CenterY;

				x += (int)(44 * Math.cos(thetaRad));
				y += (int)(44 * Math.sin(thetaRad));

				x -= 8;
				y -= 5;
				ball1.position(x,y);
				ball1.show();
				
				ball1.motion(5*Math.cos(thetaRad), 5*Math.sin(thetaRad));
				slPressed = false;
		 }
		 if(shPressed && !keyboard.isDown(keyboard.SHIFT)){
			 double thetaRad = 3.14159 * (double)(rotation2+90) / 180.0;
				numB2 = 0;
				int x = p2.xPixel() + p2CenterX;
				int y = p2.yPixel() + p2CenterY;

				x += (int)(44 * Math.cos(thetaRad));
				y += (int)(44 * Math.sin(thetaRad));

				x -= 8;
				y -= 5;
				ball2.position(x,y);
				ball2.show();
				
				ball2.motion(5*Math.cos(thetaRad), 5*Math.sin(thetaRad));
				shPressed = false;
		 }
	 }
	
}

//this class will carry a sprite and its position
class organ{
	
	Sprite spr;
	int locX, locY;
	int dir; // 1 = up, 2 = right, 3 = down, 4 = left
	public organ(Sprite s, int x, int y, int dir){
		spr = s;
		locX = x;
		locY = y;
		this.dir = dir;
	}
	public int x(){
		return locX;
	}
	public int y(){
		return locY;
	}
	public int dir(){
		return dir;
	}
	public String toString(){
		return "("+locX+", "+locY +", " + dir+")";
	}
}
