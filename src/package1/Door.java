package package1;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Door extends GameObject{
	private Coordinate position=new Coordinate(0,0);
	private Vector velocity=new Vector(0,0,false);
	private Vector acceleration=new Vector(0,0,false);
	private int height=0;
	private int width=0;
	private Room room;
	private Game game;

	public Door(Coordinate pos, Room r, Game g){
		position=pos.getCopy();
		height=100;
		width=100;
		room=r;
		game=g;
	}

	@Override
	public Coordinate getPosition() {
		return position;
	}

	@Override
	public Vector getVelocity() {
		return velocity;
	}

	@Override
	public Vector getAcceleration() {
		return acceleration;
	}

	@Override
	public void setPosition(Coordinate pos) {
		position=pos.getCopy();
	}

	@Override
	public void setVelocity(Vector vel) {
		velocity=vel.getCopy();
	}

	@Override
	public void setAcceleration(Vector acc) {
		acceleration=acc.getCopy();
	}

	@Override
	public void addForce(Vector force) {

	}

	@Override
	public boolean solid() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)position.getX(),(int)position.getY(),width,height);
		g.setColor(Color.black);
		g.drawRect((int)position.getX(),(int)position.getY(),width,height);
	}

	@Override
	public void act() {

	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int)position.getX(),(int)position.getY(),width,height);
	}

	@Override
	public boolean affectedByGravity() {
		return false;
	}

	@Override
	public void collidedWith(GameObject obj, boolean collidedHorizonatlly) {
		
		if(obj instanceof Character){
			game.load(room);
			PortalShot.currentBluePortal=null;
			PortalShot.currentRedPortal=null;
		}
	}

	@Override
	public double getMass() {
		return 1;
	}

	@Override
	public double getElasticity() {
		return 0;
	}

}