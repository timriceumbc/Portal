package package1;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BlackWall extends GameObject{
	private Coordinate position=new Coordinate(0,0);
	private Vector velocity=new Vector(0,0,false);
	private Vector acceleration=new Vector(0,0,false);
	private int height=0;
	private int width=0;
	private Game game;
	
	//Cannot place Portals on this wall.
	public BlackWall(Coordinate pos, int w, int h, Game g){
		game=g;
		position=pos.getCopy();
		height=h;
		width=w;
	}

	@Override
	public Coordinate getPosition() {
		return position.getCopy();
	}

	@Override
	public Vector getVelocity() {
		return velocity.getCopy();
	}

	@Override
	public Vector getAcceleration() {
		return acceleration.getCopy();
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
		g.setColor(Color.black);
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
		Vector v=obj.getVelocity();
		v.setDirection(v.getDirection()+Math.PI);
		v.setMagnitude(v.getMagnitude()*obj.getFrictionConstant());
		obj.addForce(v);
		if(obj instanceof PortalShot)
			game.remove(obj);
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