/* Felix Hu
 * Thanks for playing Containment: The Game! The instructions are simple: The game is for two players.
 * You play as either Communism (Red) or Democracy (Blue). Your partner is whatever character you aren't.
 * Both of you are trying to convert as much land as possible into you sphere of influence.
 * Communism starts out in the bottom-right corner. WASD controls Communism's movement.
 * Democracy starts in the top-left corner. Arrow keys control Democracy's movement.
 * 
 * The board starts out all white, signaling neutrality. Wherever you step, the board turns into your color.
 * Convert as much land into your color as possible within 30 seconds.
 * 
 * Power-ups: 
 * - The star-shaped power-up, called motivation, gives you a speed boost for 3 seconds.
 * - The bomb-shaped power-up, called nuclear bomb, drops a nuke on the other player, making
 * the area around him/her that is in the shape of a circle completely converted to your color.
 * 
 * And of course, press 'space' to begin the game.
 * 
 * Width = 900
 * Height = 800
 */


import ucigame.*;
import java.util.*;

public class area extends Ucigame{

	//has the game started or ended
	boolean begun = false;
	boolean end = false;
		
	//clocks
	GameClock c, star1, star2; //c is the game clock, the star clocks manage the star power-ups time length
		
	//players
	Sprite p1, p2;
	
	//player speed
	double p1sp = 1.5, p2sp = 1.5;
	
	//player directions
	int dir1 = 1, dir2 = 1;
	
	//the board
	Sprite tiles[][] = new Sprite[40][40]; //keeps track of the sprites
	int col[][] = new int[40][40]; //keeps track of the sprite colors
	int numBlue = 0, numRed = 0; //number of red and blue tiles
	
	//used to produce the on-screen text for both splash screen and victory screen
	String condition = "";
	Sprite splashScreen;
	Sprite t1,t2;
	Sprite infoButton;
	
	//the nuclear bomb power-up
	Sprite bo; 
	int timeBomb = Integer.MIN_VALUE; //when to show the bomb
	int bx, by; //bomb location
	boolean bomb = false; //has bomb been shown?
	
	//ArrayList to hold the star sprites for the motivation power-up
	ArrayList<Sprite> powerStar = new ArrayList<Sprite>();
	
	
	/*
	 * (non-Javadoc)
	 * @see ucigame.Ucigame#setup()
	 * Initial Setup
	 */
	public void setup(){
		
		 window.size(900, 800);
		 window.title("Containment");
		 framerate(20);
		 window.showFPS();
		 canvas.background(230,230,230);
		 doLocalSetup();
	}
	
	/*
	 * Temporary setup in case you want the game to be restart-ble, which I didn't implement
	 */
	public void doLocalSetup(){
		begun = false; //game doesn't start until someone hits the 'space' key
		
		//set up the sprites for players 1 and 2
		p1 = new Sprite(getImage("sprite.jpg",255,255,255));
		p2 = new Sprite(getImage("sprite.jpg",255,255,255));
		p1.position(45 ,40);
		p2.position(725,740);
		
		//board set up, everything start out white
		for(int i = 0; i < 40; i++){
			for(int j = 0; j < 40; j++){
				tiles[i][j] = new Sprite(getImage("white.png"),20,20);
			}
		}
		
		//set up clocks
		star1 = new GameClock(3);
		star2 = new GameClock(3);
		c = new GameClock(30);
		
		//set up the bomb beforehand.
		bo = new Sprite(getImage("bomb.jpg",255,252,253));
		bo.hide();
		bo.position(-100, 100);
		bomb = false;
		Random r = new Random();
		timeBomb =(int)(r.nextDouble()*27000)+3000; //determine the time when the bomb will show up
		bx = (int)(r.nextDouble()*800);
		by = (int)(r.nextDouble()*800);
		
		/*
		 * The next lines of code set up t1, splashScreen, t2, and infoButton
		 * in order to produce the right text and picture on-screen.
		 */
		t1 = makeSprite(getImage("transparent.png",255,255,255),100,800);
		t1.position(800, 0);
		t1.font("Arial", BOLD, 13);
		t1.hide();
		
		splashScreen = new Sprite(getImage("splash.png"));
		splashScreen.position(0, 0);
		splashScreen.show();
		
		t2 = makeSprite(getImage("transparent.png",255,255,255),800,900);
		t2.position(0, 0);
		t2.font("Arial", BOLD, 22);
		
		infoButton = makeButton("info", getImage("button.jpg"),200,30);
		infoButton.position(550, 725);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ucigame.Ucigame#draw()
	 * Draw everything, includes game logic
	 */
	public void draw(){
		canvas.clear(); //reset canvas every time
		
		//if the game clock has passed timeBomb, show the bomb
		if(c.millisRemaining() < timeBomb && !bomb){ 
			bo.show();
			bo.position(bx, by);
			bomb = true;
		}
		
		//if the clock has reached 0, end the game, count the area of each color, determine the victor
		if(c.millisRemaining() <0){
			if(!end){
			count(0,0);
			c.pause();
			end = true;
			begun = false;
			if(numRed==numBlue){
				System.out.println("TIE");
				condition = "TIE";
			}else if(numRed> numBlue){
				condition = "COMMUNISM";
				System.out.println("p2 wins");
			} else{
				condition = "DEMOCRACY";
				System.out.println("p1 wins");
			}
		}
		}
		
		/*
		 * everytime draw() is called, check what player is on each sprite,
		 * and change the sprite to the player's respective color.
		 */
		for(int i = 0; i <40; i++){
			for(int j = 0; j < 40; j++){
				
				tiles[i][j].checkIfCollidesWith(p1);
				if(tiles[i][j].collided()){
					tiles[i][j] = new Sprite(getImage("blue.png"),20,20);
					col[i][j] = 1;
				} else {
					tiles[i][j].checkIfCollidesWith(p2);
				if(tiles[i][j].collided()){
					tiles[i][j] = new Sprite(getImage("red.png"),20,20);
					col[i][j] = 2;
				}
				}
				tiles[i][j].position(i*20, j*20);
				tiles[i][j].draw();
				
			}
		}
		
		//draw the players' directions correctly, don't let them go out-of-bounds.
		p1.pauseIfCollidesWith(BOTTOMEDGE,TOPEDGE,LEFTEDGE);
		if(p1.x()+p1.width()>800){
			p1.position(800-p1.width(), p1.y());
		}
		p2.pauseIfCollidesWith(BOTTOMEDGE,TOPEDGE,LEFTEDGE);
		if(p2.x()+p2.width()>800){
			p2.position(800-p2.width(), p2.y());
		}
		switch(dir1){
		case 1:
			p1.draw();
			break;
		case 3: 
			p1.flipVertical();
			p1.draw();
			break;
		case 2: 
			p1.rotate(90);
			p1.draw();
			p1.rotate(-90);
			break;
		case 4:
			p1.rotate(-90);
			p1.draw();
			p1.rotate(90);
			break;
		}
		switch(dir2){
		case 1:
			p2.draw();
			break;
		case 3: 
			p2.flipVertical();
			p2.draw();
			break;
		case 2: 
			p2.rotate(90);
			p2.draw();
			p2.rotate(-90);
			break;
		case 4:
			p2.rotate(-90);
			p2.draw();
			p2.rotate(90);
			break;
		}
		
		//randomizing the motivation power-up drops
		if(begun){
		Random r = new Random();
		if(r.nextFloat() <0.011){
			Sprite temp = new Sprite(getImage("star.png",255,255,255));
			temp.position((r.nextDouble()*790), ( r.nextDouble()*790));
			powerStar.add(temp);
			}
		}
		
		//check if players have collided with any motivation power-ups. If so, start the clock for speed boost.
		for(int i = 0; i < powerStar.size(); i++){
			
			powerStar.get(i).checkIfCollidesWith(p1);
			if(powerStar.get(i).collided()){
				powerStar.get(i).hide();
				powerStar.get(i).position(-20, -20);
				
				//p1 gets powerup
				
				star1 = new GameClock(3);
				star1.start();
			}
			
			powerStar.get(i).checkIfCollidesWith(p2);
			if(powerStar.get(i).collided()){
				powerStar.get(i).hide();
				powerStar.get(i).position(-20, -20);
			
				//p2 gets powerup
				
				star2 = new GameClock(3);
				star2.start();
			}
			powerStar.get(i).draw();
		}
		
		//check if a player has collided with the bomb power-up. If so, draw the bomb animation.
		bo.checkIfCollidesWith(p1);
		if(bo.collided()){
			bo.hide();
			bo.position(-100, -100);
			blueB(p2.xPixel()+p2.width()/2, p2.yPixel()+p2.height()/2, 8);
		}
		bo.checkIfCollidesWith(p2);
		if(bo.collided()){
			bo.hide();
			bo.position(-100, -100);
			redB(p1.xPixel()+p1.width()/2, p1.yPixel()+p1.height()/2, 8);
		}
		
		/*
		 * The motivation power-up effects on players. 
		 * If a player has motivation, increase their speed from 1.5 to 3.
		 * If not, keep speed at the default 1.5.
		 */
		if(star1 !=null&& star1.millisRemaining() >0&&star1.timerTicking){
			p1sp = 3;
		} else{
			p1sp = 1.5;
			star1.pause();	
		}
		if(star2!=null&&star2.millisRemaining() >0 &&star2.timerTicking){
			p2sp = 3;
		} else{
			p2sp = 1.5;
			star2.pause();
		}
		
		
		//draw the timer
		canvas.font("Arial", BOLD, 22);
		canvas.putText(c.millisRemaining()/1000+ " sec", 820, 100);
		
		//draw splash screen and end screen
		if(end){
			t1.show();
			t1.putText("VICTOR:", 5, 350);
			t1.putText(condition, 5, 400);
			t1.putText(numBlue + "--" + numRed, 5, 450);
			t1.draw();
		}
		if(begun){
			splashScreen.hide();
			infoButton.hide();
		}
		splashScreen.draw();
		infoButton.draw();
		t2.putText("WASD to move Communism. Arrow keys to move Democracy.", 40, 700);
		t2.putText("Convert as much land as possible.", 40, 750);
		if(begun){
			t2.hide();	
		}
		t2.draw();
		
		//draw the bomb on top of everything.
		bo.draw();
	}
	
	public void onKeyPress(){
		
		//begin the game
		if(keyboard.key() == keyboard.SPACE){
			c.start();
			begun = true;
		}
		
		if(begun){
		//movement for p1
		if(keyboard.isDown(keyboard.RIGHT)){
			p1.nextX(p1.x()+(5*Math.sqrt(p1sp))); //uses modified square root function to control player speed.
			dir1 = 2;
		}
		if(keyboard.isDown(keyboard.LEFT)){
			p1.nextX(p1.x()-(5*Math.sqrt(p1sp)));
			dir1 = 4;
		}
		if(keyboard.isDown(keyboard.UP)){
			p1.nextY(p1.y()-(5*Math.sqrt(p1sp)));
			dir1 = 1;
		}
		if(keyboard.isDown(keyboard.DOWN)){
			p1.nextY(p1.y()+(5*Math.sqrt(p1sp)));
			dir1 = 3;
		}
		
		//movement for p2
		if(keyboard.isDown(keyboard.D)){
			p2.nextX(p2.x()+(5*Math.sqrt(p2sp)));
			dir2 = 2;
		}
		if(keyboard.isDown(keyboard.A)){
			p2.nextX(p2.x()-(5*Math.sqrt(p2sp)));
			dir2 = 4;
		}
		if(keyboard.isDown(keyboard.W)){
			p2.nextY(p2.y()-(5*Math.sqrt(p2sp)));
			dir2 = 1;
		}
		if(keyboard.isDown(keyboard.S)){
			p2.nextY(p2.y()+(5*Math.sqrt(p2sp)));
			dir2 = 3;
		}
		}
		
	}
	
	/*
	 * The function that handles interaction with the instance variable infoButton.
	 * If the button is clicked, this method is called, which hides the button and
	 * shows the info splash page.
	 */
	public void onClickinfo(){
		splashScreen = new Sprite(getImage("Splash2.png"));
		splashScreen.position(0, 0);
		t2.hide();
		infoButton.hide();
	}
	
	/*
	 * This count method counts the number of blue and red tiles non-recursively.
	 * This method is not used in the game.
	 */
	public void count(){
		for(int i = 0; i <40; i++){
			for(int j = 0; j < 40; j++){
				if(col[i][j] == 1){
					numBlue++;
				}
				if(col[i][j] == 2){
					numRed++;
				}
			}
			}
		System.out.println(numBlue + " "+ numRed);
	}
	
	/*
	*This count method uses recursion to check each individual element of the col array.
	*It counts how many red and blue tiles there are.
	*As it counts, it increments numBlue and numRed if it comes across a blue or red tile, respectively.
	*/
	public void count(int i, int j){
	//base case
	if(i == 39 && j == 39){
		return;
	}
		if(col[i][j]==1){
		numBlue++;
		} else if(col[i][j]==2){
		numRed++;
	}	
		if(j<39){
		count(i,j+1);
		} else{
		count(i+1,0);
		}
	}
	
	/*
	 * Bomb Powerup Red, animates the bomb power-up for communism
	 * Parameters: takes in the xy coordinates of the center of the bomb and r, its radius.
	 */
	public void redB(int x, int y, int r){
		int a = (y/20)-r;
		int b = (y/20)+r;
		int c = (x/20)-r;
		int d =(x/20)+r;
		if(a <0){
			a=0;
		}
		if(b>39){
			b=39;
		}
		if(c<0){
			c=0;
		}
		if(d>39){
			d=39;
		}
		
		for(int i = a; i <= b; i++){
			for(int j = c ; j <= d; j++){
				if((Math.pow((20*i)-y, 2)+Math.pow((20*j)-x, 2)) < Math.pow(r*20, 2)){
					tiles[j][i] = new Sprite(getImage("red.png"),20,20);
					tiles[j][i].position(i*20,j *20);
					col[j][i] = 2;
				}
			}
		}
	}
	/*
	 * Bomb Powerup Blue, animates the bomb powerup for democracy
	 * Parameters: takes in the xy coordinates of the center of the bomb and r, its radius.
	 */
	public void blueB(int x, int y, int r){
		int a = (y/20)-r;
		int b = (y/20)+r;
		int c = (x/20)-r;
		int d =(x/20)+r;
		if(a <0){
			a=0;
		}
		if(b>39){
			b=39;
		}
		if(c<0){
			c=0;
		}
		if(d>39){
			d=39;
		}
		
		for(int i = a; i <=b; i++){
			for(int j = c ; j <= d; j++){
				if((Math.pow((20*i)-y, 2)+Math.pow((20*j)-x, 2)) < Math.pow(r*20, 2)){
					tiles[j][i] = new Sprite(getImage("blue.png"),20,20);
					tiles[j][i].position(i*20,j *20);
					col[j][i] = 1;
				}
			}
		}
	}
	
}
