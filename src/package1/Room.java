package package1;
import java.util.ArrayList;


public class Room {

    private ArrayList<GameObject> gameObjects=new ArrayList<GameObject>();

    private Coordinate charStart=new Coordinate(0,0);

    private Coordinate bottomRightCorner;

    public Room(Coordinate btmRgtCnr){

        bottomRightCorner=btmRgtCnr;

    }

   

    public Coordinate getBottomRightCorner(){

        return bottomRightCorner;

    }

    public void setCharacterStart(Coordinate start){

        charStart=start.getCopy();

    }

    public Coordinate getCharacterStart(){

        return charStart.getCopy();

    }

    public void add(GameObject obj){

        if(obj instanceof Character)

            setCharacterStart(obj.getPosition());

        gameObjects.add(obj);

    }

    public void remove(GameObject obj){

        gameObjects.remove(obj);

    }

    public ArrayList<GameObject> getGameObjects(){

        return gameObjects;

    }

}