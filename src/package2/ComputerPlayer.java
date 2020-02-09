package package2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import package1.Character;
import package1.Coordinate;
import package1.Game;
import package1.GameObject;


public class ComputerPlayer extends Character{
	
	public final int LEFT = 0;
	public final int RIGHT = 1;
	public final int UP = 2;
	public final int DOWN = 3;
	public final int ANGLE = 4;
	public final int LEFTCLICK = 5;
	public final int RIGHTCLICK = 6;
	
	private int[] prevInputArray;
	private int[] inputArray;
	
	private PortalAI portalAI;

	public ComputerPlayer(Coordinate pos, int h, int w, Game g) {
		super(pos, h, w, g);
		inputArray = new int[7];
		prevInputArray = new int[7];
		portalAI = new PortalAI();
	}
	
	public void act() {
		
		inputArray = portalAI.getInputs();
		
		boolean keyPressed = false;
		boolean keyReleased = false;
		
		for(int i = 0; i < 4; i++){
			if(prevInputArray[i] == 0 && inputArray[i] == 1)
				keyPressed = true;
			if(prevInputArray[i] == 1 && inputArray[i] == 0)
				keyReleased = true;
		}
		
		if(keyPressed)
			keyPressed(inputArray);
		if(keyReleased)
			keyReleased(inputArray);
		
		super.act();
		
		for(int i = 0; i < inputArray.length; i++){
			prevInputArray[i] = inputArray[i];
		}
	}
	
	public void keyPressed(int[] inputs){
		if(inputs[UP] == 1){
			upIsPressed=true;
		}

		if(inputs[RIGHT] == 1){
			rightIsPressed=true;
			facingRight=true;
		}

		if(inputs[LEFT] == 1){
			leftIsPressed=true;
			facingRight=false;
		}

		if(inputs[DOWN] == 1){
			downIsPressed=true;
		}
	}
	
	public void keyReleased(int[] inputs){
		if(inputs[UP] == 0){
			upIsPressed=false;
		}

		if(inputs[RIGHT] == 0){
			rightIsPressed=false;
		}

		if(inputs[LEFT] == 0){
			leftIsPressed=false;
		}

		if(inputs[DOWN] == 0){
			downIsPressed=false;
			if(!blockPickedUp){
				if(block!=null){
					ArrayList<GameObject> intersections=game.getIntersections(block, true);
					if(intersections.size()==0){
						game.add(block);
						block=null;
					}
				}
			}
			blockPickedUp=false;
		}
	}
	
	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		
	}
	
}