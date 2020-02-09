package package1;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject{
	public abstract Coordinate getPosition();
	public abstract Vector getVelocity();
	public abstract Vector getAcceleration();
	public abstract void setPosition(Coordinate pos);
	public abstract void setVelocity(Vector vel);
	public abstract void setAcceleration(Vector acc);
	public abstract boolean solid();
	public abstract void draw(Graphics g);
	public abstract void act();
	public abstract Rectangle getHitbox();
	public abstract boolean affectedByGravity();
	public abstract void collidedWith(GameObject obj, boolean collidedHorizonatlly);
	public abstract double getMass();
	
	
	//A number between 0 and 1. 1 will bounce the most and 0 won't bounce.
	public abstract double getElasticity();
	
	public void addForce(Vector force){
		Vector v=new Vector(force.getMagnitude()/getMass(),force.getDirection(),true);
		setAcceleration(getAcceleration().add(v));
	}
	
	//higher=more friction
	public double getFrictionConstant(){
		return 0;
	}
}