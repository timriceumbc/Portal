package package2;

public class PortalAI {
	
	public final int LEFT = 0;
	public final int RIGHT = 1;
	public final int UP = 2;
	public final int DOWN = 3;
	public final int ANGLE = 4;
	public final int LEFTCLICK = 5;
	public final int RIGHTCLICK = 6;
	
	private int[] inputArray;
	
	PortalAI(){
		inputArray = new int[7];
	}
	
	public int[] getInputs(){
		inputArray[RIGHT] = 1;
		return inputArray;
	}
	
}