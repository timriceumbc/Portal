package package1;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Block extends GameObject{
	private Coordinate position=new Coordinate(0,0);
	private Vector velocity=new Vector(0,0,false);
	private Vector acceleration=new Vector(0,0,false);
	private int height=0;
	private int width=0;
	private BufferedImage image;
	
	public Block(Coordinate pos, Game g){
		position=pos.getCopy();
		height=50;
		width=50;
		try {
			image=ImageIO.read(g.getFrame().getClass().getResource("/images/companionCube.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public boolean solid() {
		return true;
	}

	@Override
	public void draw(Graphics g) {
		
		try {
			g.drawImage(image,(int)position.getX(),(int)position.getY(),width,height,null);
		} catch (Exception e) {
			g.setColor(Color.black);
			g.fillRect((int)position.getX(),(int)position.getY(),width,height);
			g.setColor(Color.black);
			g.drawRect((int)position.getX(),(int)position.getY(),width,height);
		}

	}

	@Override
	public void act() {
		
	}

	@Override
	public Rectangle getHitbox() {
		//System.out.println(new Rectangle((int)position.getX(),(int)position.getY(),width,height));
		return new Rectangle((int)position.getX(),(int)position.getY(),width,height);
	}

	@Override
	public boolean affectedByGravity() {
		return true;
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
		return 3;
	}

	@Override
	public double getElasticity() {
		return .3;
	}

	public double getFrictionConstant(){
		return .5;
	}

}