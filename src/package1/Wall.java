package package1;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends GameObject{
	private Coordinate position=new Coordinate(0,0);
	private Vector velocity=new Vector(0,0,false);
	private Vector acceleration=new Vector(0,0,false);
	private int height=0;
	private int width=0;

	public Wall(Coordinate pos, int w, int h){
		position=pos.getCopy();
		height=h;
		width=w;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
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
		return true;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.gray);
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
		Vector Fr=obj.getVelocity();
		Fr.setMagnitude(obj.getMass()*obj.getFrictionConstant());
		Fr.setDirection(obj.getVelocity().getDirection()+Math.PI);
		obj.addForce(Fr);
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