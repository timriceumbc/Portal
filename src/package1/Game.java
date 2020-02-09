package package1;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import package2.ComputerPlayer;

public class Game extends JPanel implements ActionListener{
    private JFrame frame;
    private Timer timer;
    private ArrayList<GameObject> gameObjects=new ArrayList<GameObject>();
    private ArrayList<GameObject> portals=new ArrayList<GameObject>();
    private ArrayList<GameObject> portalShots=new ArrayList<GameObject>();
    private double g=.5;
    private Room loadedRoom;
    private double scale=1;
    private PortalGun gun;
    private Character character;

    public Game(){
        gameObjects=new ArrayList<GameObject>();
        frame=new JFrame("Portal");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,1000,700);
        frame.setVisible(true);
    }
    
    public JFrame getFrame(){
        return frame;
    }

    public double g(){
        return g;
    }

    public double scale(){
        return scale;
    }

    public ArrayList<GameObject> getPortals(){
        return portals;
    }

    public void clear(){
        gameObjects.clear();
        portals.clear();
        portalShots.clear();
    }

    public void remove(GameObject obj){
        if(obj instanceof Portal)
            portals.remove(obj);
        if(obj instanceof PortalShot)
            portalShots.remove(obj);
        if(!(obj instanceof Portal))
            gameObjects.remove(obj);
    }

    public void add(GameObject obj){
        if(obj instanceof Portal)
            portals.add(obj);
        else if(obj instanceof PortalShot)
            portalShots.add(obj);
        if(!(obj instanceof Portal))
            gameObjects.add(obj);
    }

    public ArrayList<GameObject> getAllOtherObjects(GameObject current){
        ArrayList<GameObject> objects=new ArrayList<GameObject>();
        for(GameObject obj:gameObjects){
            if(obj!=current)
                objects.add(obj);
        }
        return objects;

    }

    public ArrayList<GameObject> getIntersections(GameObject current,ArrayList<GameObject> objects){
        ArrayList<GameObject> intersections=new ArrayList<GameObject>();
        for(GameObject obj:objects){
            if(obj!=current&&obj.getHitbox().intersects(current.getHitbox())){
                intersections.add(obj);
            }
        }
        return intersections;
    }

    public ArrayList<GameObject> getIntersections(GameObject current, boolean withSolidsOnly){
        ArrayList<GameObject> intersections=new ArrayList<GameObject>();
        for(GameObject obj:gameObjects){
            if(obj!=current&&obj.getHitbox().intersects(current.getHitbox())){
                if(withSolidsOnly){
                    if(obj.solid())
                        intersections.add(obj);
                }else{
                    intersections.add(obj);
                }
            }
        }
        return intersections;
    }

    private void move(GameObject obj){
        //speed limit
        if(obj.getVelocity().getMagnitude()>50)
            obj.setVelocity((new Vector(50,obj.getVelocity().getDirection(),true)));
        if(obj.solid()){
            obj.setVelocity(obj.getVelocity().add(obj.getAcceleration()));
            obj.setAcceleration(new Vector(0,0,false));

            // check in x direction
            Coordinate prevPos=obj.getPosition();
            Coordinate pos=obj.getPosition();
            pos.updateX(obj.getVelocity());
            obj.setPosition(pos);
            ArrayList<GameObject> intersections=getIntersections(obj,portals);
            if(intersections.size()>0){
                for(int i=0;i<intersections.size();i++){
                    obj.collidedWith(intersections.get(i), true);
                    intersections.get(i).collidedWith(obj, true);
                }
            }

            intersections=getIntersections(obj,true);
            if(intersections.size()>0){
                for(int i=0;i<intersections.size();i++){
                    obj.collidedWith(intersections.get(i), true);
                    intersections.get(i).collidedWith(obj, true);
                }
                obj.setPosition(prevPos);
                Vector vel=obj.getVelocity();
                vel.setXComponent(-obj.getVelocity().getXComponent()*obj.getElasticity());
                obj.setVelocity(vel);
            }

            // check in y direction

            prevPos=obj.getPosition();
            pos=obj.getPosition();
            pos.updateY(obj.getVelocity());
            obj.setPosition(pos);
            intersections=getIntersections(obj,portals);
            if(intersections.size()>0){
                for(int i=0;i<intersections.size();i++){
                    obj.collidedWith(intersections.get(i), false);
                    intersections.get(i).collidedWith(obj, false);
                }
            }

            intersections=getIntersections(obj,true);
            if(intersections.size()>0){
                for(int i=0;i<intersections.size();i++){
                    obj.collidedWith(intersections.get(i), false);
                    intersections.get(i).collidedWith(obj, false);
                }
                obj.setPosition(prevPos);
                Vector vel=obj.getVelocity();
                vel.setYComponent(-obj.getVelocity().getYComponent()*obj.getElasticity());
                obj.setVelocity(vel);
            }
        }else{
            // if not solid
            obj.setVelocity(obj.getVelocity().add(obj.getAcceleration()));
            obj.setAcceleration(new Vector(0,0,false));
            Coordinate pos=obj.getPosition();
            pos.updateX(obj.getVelocity());
            obj.setPosition(pos);
            ArrayList<GameObject> intersections=getIntersections(obj,true);
            if(intersections.size()>0){
                for(int i=0;i<intersections.size();i++){
                    obj.collidedWith(intersections.get(i), true);
                    intersections.get(i).collidedWith(obj, true);
                }
            }
            pos.updateY(obj.getVelocity());
            obj.setPosition(pos);
            ArrayList<GameObject> intersections2=getIntersections(obj,false);
            for(int i=0; i<intersections.size();i++){
                intersections2.remove(intersections.get(i));
            }
            if(intersections2.size()>0){
                for(int i=0;i<intersections2.size();i++){
                    obj.collidedWith(intersections2.get(i), false);
                    intersections2.get(i).collidedWith(obj, false);
                }
            }
        }
    }

    private void handlePhysics(){
        for(int j=0;j<5;j++){
            for(int i=0;i<portalShots.size();i++){
                GameObject obj=portalShots.get(i);
                Vector gravity=new Vector(g*obj.getMass(),Math.PI*3/2,true);
                if(obj.affectedByGravity())
                    obj.addForce(gravity);
                move(obj);
            }
        }

        for(int i=0;i<gameObjects.size();i++){
            GameObject obj=gameObjects.get(i);
            Vector gravity=new Vector(g*obj.getMass(),Math.PI*3/2,true);
            if(obj.affectedByGravity())
                obj.addForce(gravity);
            move(obj);
        }
    }

    private void iterate(){       
        handlePhysics();
        for(int i=0;i<gameObjects.size();i++){
            GameObject obj=gameObjects.get(i);
            obj.act();
            if(obj.getPosition().getY()*scale>frame.getHeight()&&obj instanceof Character)
                load(loadedRoom);
        }

        for(int i=0;i<portals.size();i++){
            GameObject obj=portals.get(i);
            obj.act();
        }

        for(int i=0;i<gameObjects.size();i++){
            GameObject obj=gameObjects.get(i);
            if(obj.getPosition().getY()*scale>frame.getHeight()+2000&&obj.solid())
                remove(obj);
            if((obj.getPosition().getY()*scale>frame.getHeight()+2000
            ||obj.getPosition().getX()*scale>frame.getWidth()+2000
            ||obj.getPosition().getY()<-2000
            ||obj.getPosition().getX()<-2000)&&!obj.solid())
                remove(obj);
        }
        repaint();
    }

    public void startGame(){
        timer=new Timer(1000/60,this);
        Room room=new Room(new Coordinate(550,550));
        Room room2=new Room(new Coordinate(1600,900));
        Room room3=new Room(new Coordinate(3000,2000));
        Room room4=new Room(new Coordinate(4000,3000));
        room.add(new Door(new Coordinate(450,400),room2,this));
        room.add((new Wall(new Coordinate(50,500),500,50)));
        room2.add(new Door(new Coordinate(900,300),room3,this));
        room2.add(new Wall(new Coordinate(50,500),50,500));
        room2.add(new Wall(new Coordinate(50,50),500,40));
        room2.add(new Wall(new Coordinate(300,50),50,300));
        room2.add(new Wall(new Coordinate(500,400),500,50));
        room2.add(new Wall(new Coordinate(700,200),50,500));
        room2.add(new Wall(new Coordinate(200,500),500,50));
        room2.add(new Wall(new Coordinate(100,700),500,50));
        room2.add(new Block(new Coordinate(400,650),this));
        room2.add(new Block(new Coordinate(600,350),this));

        room3.add(new Door(new Coordinate(100,800),room4,this));
        room3.add(new Wall(new Coordinate(500,200),200,1600));
        //
        room3.add(new Wall(new Coordinate(1700,1000),500,100));
        room3.add(new Wall(new Coordinate(1700,1100),100,400));
        room3.add(new Wall(new Coordinate(1500,500),500,200));
        room3.add(new Wall(new Coordinate(700,1500),1100,300));
        room3.add(new Wall(new Coordinate(2000,200),100,500));
        room3.add(new Wall(new Coordinate(0,900),500,100));
        room3.add(new Wall(new Coordinate(1800,1700),1000,100));
        room3.add(new Wall(new Coordinate(2800,200),100,1600));
        room3.add(new Block(new Coordinate(2000,950),this));
        room3.add(new Block(new Coordinate(1400,1450),this));
        room3.add(new Block(new Coordinate(2500,1650),this));
        room3.add(new Block(new Coordinate(1600,450),this));
       
        room4.add(new Wall(new Coordinate(3200,1500),100,500));
        room4.add(new Door(new Coordinate(3200,2100),room,this));
        //T
        room4.add(new Wall(new Coordinate(500,500),800,100));
        room4.add(new Wall(new Coordinate(850,600),100,600));
        //H
        room4.add(new Wall(new Coordinate(1550,500),100,700));
        room4.add(new Wall(new Coordinate(1650,800),500,100));
        room4.add(new Wall(new Coordinate(2150,500),100,700));
        //E
        room4.add(new Wall(new Coordinate(2500,500),100,700));
        room4.add(new Wall(new Coordinate(2600,500),500,100));
        room4.add(new Wall(new Coordinate(2600,800),500,100));
        room4.add(new Wall(new Coordinate(2600,1100),500,100));
        //E
        room4.add(new Wall(new Coordinate(600,1500),100,700));
        room4.add(new Wall(new Coordinate(700,1500),500,100));
        room4.add(new Wall(new Coordinate(700,1800),500,100));
        room4.add(new Wall(new Coordinate(700,2100),500,100));
        //N
        room4.add(new Wall(new Coordinate(1550,1500),100,700));
        room4.add(new Wall(new Coordinate(1650,1600),100,100));
        room4.add(new Wall(new Coordinate(1750,1700),100,100));
        room4.add(new Wall(new Coordinate(1850,1800),100,100));
        room4.add(new Wall(new Coordinate(1950,1900),100,100));
        room4.add(new Wall(new Coordinate(2050,2000),100,100));
        room4.add(new Wall(new Coordinate(2150,1500),100,700));
        //D
        room4.add(new Wall(new Coordinate(2500,1500),100,700));
        room4.add(new Wall(new Coordinate(2600,1500),100,100));
        room4.add(new Wall(new Coordinate(2700,1550),100,100));
        room4.add(new Wall(new Coordinate(2800,1600),100,100));
        room4.add(new Wall(new Coordinate(2900,1650),100,100));
        room4.add(new Wall(new Coordinate(3000,1720),100,100));
        room4.add(new Wall(new Coordinate(2600,2100),100,100));
        room4.add(new Wall(new Coordinate(2700,2040),100,100));
        room4.add(new Wall(new Coordinate(2800,1960),100,100));
        room4.add(new Wall(new Coordinate(2900,1890),100,100));
        room4.add(new Wall(new Coordinate(3000,1820),100,100));
      
        room4.add(new Wall(new Coordinate(0,0),100,2900));
        room4.add(new Wall(new Coordinate(100,0),3800,100));
        room4.add(new Wall(new Coordinate(3900,0),100,2900));
        room4.add(new Wall(new Coordinate(100,2800),3800,100));
       
        Character c=new Character(new Coordinate(250,400),50,100,this);
        gun=new PortalGun(c,this);
        this.frame.addMouseListener(gun);
        this.frame.addMouseMotionListener(gun);
        room.add(c);
        room2.add(c);
        room3.add(c);
        room3.setCharacterStart(new Coordinate(1000,1400));
        room4.add(c);
        room4.setCharacterStart(new Coordinate(1000,1400));
        load(room);
        timer.start();
    }

    public void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g;
        g.clearRect(0, 0, getWidth(), getHeight());
        g2.scale(scale,scale);
        for(int i=0;i<gameObjects.size();i++){
            GameObject obj=gameObjects.get(i);
            obj.draw(g);
        }

        for(int i=0;i<portals.size();i++){
            GameObject obj=portals.get(i);
            obj.draw(g);
        }
    }

    public void load(Room room){
        loadedRoom=room;
        clear();
        ArrayList<GameObject> objs=room.getGameObjects();
        for(int i=0;i<objs.size();i++){
            if(objs.get(i) instanceof Character){
                frame.removeKeyListener(character);
                character=new Character(room.getCharacterStart(),100,50,this);
                character.setPortalGun(gun);
                frame.addKeyListener(character);
                add(character);
            }else if(objs.get(i) instanceof Block){
                add(new Block(objs.get(i).getPosition(),this));
            }else
                add(objs.get(i));
        }
        scale=frame.getWidth()/room.getBottomRightCorner().getX();
        if(frame.getHeight()/room.getBottomRightCorner().getY()<scale)
            scale=frame.getHeight()/room.getBottomRightCorner().getY();
    }
    
    public void actionPerformed(ActionEvent arg0) {
        iterate();
    }
    
    public static void main(String[] args) {
        Game game=new Game();
        game.startGame();
    }
}