import ucigame.*;
import java.util.*;
public class colors extends Ucigame{

	GameClock c;
	
	Sprite player;
	boolean faceDown = false;
	boolean faceLeft = false;
	
	int dir = 1;
	
	//colors
	boolean blue = false;
	boolean red = false;
	boolean purple = false;
	boolean pink = false;
	boolean black = false;
	boolean green = false;
	boolean yellow = false;
	boolean white = false;
	Image images[] = {getImage("white.png"),getImage("black.png"),getImage("pink.png"),getImage("purple.png"),getImage("red.png"),getImage("blue.png"),getImage("green.png"),getImage("yellow.png")};
	
	boolean intermission = false;
	int time = 5000;
	
	int level = 1;
	
	Sprite tiles[][] = new Sprite[6][6];
	Sprite t1, im; // for side text and image
	
	public void setup(){
		 window.size(904, 804);
		 window.title("Colors");
		 framerate(60);
		 canvas.background(230,230,230);
		doLocalSetup();
	}
	public void doLocalSetup(){
		//clock
		c = new GameClock(3);
		c.start();
		
		player = new Sprite(getImage("sprite.jpg",255,255,255));
		player.position(402-(player.width()/2), 402-(player.height()/2));
		
		level = 1;
		//set up tiles
		blue = false;
		red = false;
		purple = false;
		pink = false;
		black = false;
		green = false;
		yellow = false;
		white = false;
		
		Random r = new Random();
		while(!blue ||!red ||!purple ||!pink ||!yellow ||!green ||!white ||!black){ //make sure it has all colors
			blue = false;
			red = false;
			purple = false;
			pink = false;
			black = false;
			green = false;
			yellow = false;
			white = false;
		for(int i = 0; i <6; i++){
			for(int j = 0; j<6; j++){
				int n = ((int)(r.nextDouble()*8))%8;
				if(n==0){
					tiles[i][j] = new Sprite(getImage("white.png"),134,134);
					white = true;
				} else if(n==1){
					tiles[i][j] = new Sprite(getImage("blue.png"),134,134);
					blue = true;
				}else if(n==2){
					tiles[i][j] = new Sprite(getImage("pink.png"),134,134);
					pink = true;
				}else if(n==3){
					tiles[i][j] = new Sprite(getImage("red.png"),134,134);
					red = true;
				}else if(n==4){
					tiles[i][j] = new Sprite(getImage("black.png"),134,134);
					black = true;
				}else if(n==5){
					tiles[i][j] = new Sprite(getImage("green.png"),134,134);
					green = true;
				}else if(n==6){
					tiles[i][j] = new Sprite(getImage("yellow.png"),134,134);
					yellow = true;
				}else if(n==7){
					tiles[i][j] = new Sprite(getImage("purple.png"),134,134);
					purple = true;
				}
			}
		}
		}
		for(Sprite temp[] : tiles){
			System.out.println(Arrays.toString(temp));	
		}
		
		//text label
		t1 = makeSprite(getImage("transparent.png",255,255,255),100,800);
		t1.position(804, 0);
		t1.font("Arial", BOLD, 17);
		
		im = new Sprite(getImage("transparent.png",255,255,255),60,60);
		im.position(824, 372);
		
		
	}
	
	
	public void draw(){
		canvas.clear();
		
		while(c.millisRemaining() <0){
			c.pause();
		}
		
		
		for(int i = 0; i <6; i++){
			for(int j = 0; j<6; j++){
				tiles[i][j].position(i*134, j*134);
				tiles[i][j].draw();
			}
		}
		
		player.pauseIfCollidesWith(BOTTOMEDGE,TOPEDGE,LEFTEDGE);
		if(player.x()+player.width()>804){
			player.position(804-player.width(), player.y());
		}
		
		//toggle player facing direction
		switch(dir){
		case 1:
			player.draw();
			break;
		case 3: 
			player.flipVertical();
			player.draw();
			break;
		case 2: 
			player.rotate(90);
			player.draw();
			player.rotate(-90);
			break;
		case 4:
			player.rotate(-90);
			player.draw();
			player.rotate(90);
			break;
		
		}
	
		
		t1.draw();
		t1.putText("Current", 10, 50);
		t1.putText("Color Is :", 11, 90);
		
		//draw the color
		im.draw();
		
	}
	public void onKeyPress(){
		if(keyboard.key() == keyboard.R){
			doLocalSetup();
		}
		if(keyboard.key()==(keyboard.RIGHT)){
			player.nextX(player.x()+(4*Math.sqrt(level*1.5)));
			dir = 2;
		}
		if(keyboard.key()==(keyboard.LEFT)){
			player.nextX(player.x()-(4*Math.sqrt(level*1.5)));
			dir = 4;
		}
		if(keyboard.key()==(keyboard.UP)){
			player.nextY(player.y()-(4*Math.sqrt(level*1.5)));
			dir = 1;
		}
		if(keyboard.key()==(keyboard.DOWN)){
			player.nextY(player.y()+(4*Math.sqrt(level*1.5)));
			dir = 3;
		}
	}
	
	public void intermission(){
		int x = im.xPixel();
		int y = im.yPixel();
		im = new Sprite(getImage("transparent.png",255,255,255),60,60);
		im.position(x, y);
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		intermission = true;
		while(end-start < 3500){
			end = System.currentTimeMillis();
		}
		System.err.println("intermission ended");
		intermission = false;
	}
	
}
