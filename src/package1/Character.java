package package1;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Character extends GameObject implements KeyListener{
	private Coordinate position=new Coordinate(0,0);
	private Vector velocity=new Vector(0,0,false);
	private Vector acceleration=new Vector(0,0,false);
	private int height=0;
	private int width=0;
	protected boolean upIsPressed=false;
	protected boolean downIsPressed=false, leftIsPressed=false, rightIsPressed=false;
	private boolean airborn=false;
	private boolean collidedWithGroundLastTime=false;
	protected Block block;
	protected PortalGun gun;
	protected Game game;
	protected boolean blockPickedUp=false, blockBox=false, facingRight=true;
	private BufferedImage characterImageR, characterImageL;
	
	public Character(Coordinate pos, int h, int w, Game g){
		game=g;
		position=pos.getCopy();
		height=h;
		width=w;
		try {
			characterImageR=ImageIO.read(game.getFrame().getClass().getResource("/images/characterNew.gif"));
			AffineTransform transform=AffineTransform.getScaleInstance(-1, 1);
			transform.translate(-characterImageR.getWidth(), 0);
			AffineTransformOp opTransform = new AffineTransformOp(transform,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			characterImageL = opTransform.filter(characterImageR, null);
		} catch (IOException e) {
		}
	}

	public void setPortalGun(PortalGun newGun){
		gun=newGun;
	}

	public PortalGun getPortalGun(){
		return gun;
	}

	public Coordinate getPosition() {
		return position.getCopy();
	}

	public Vector getVelocity() {
		return velocity.getCopy();
	}

	public Vector getAcceleration() {
		return acceleration.getCopy();
	}

	public void setPosition(Coordinate pos) {
		position=pos.getCopy();
	}

	public void setVelocity(Vector vel) {
		velocity=vel.getCopy();
	}

	public void setAcceleration(Vector acc) {
		acceleration=acc.getCopy();
	}

	public boolean solid() {
		return true;
	}

	public void draw(Graphics g) {
		try {
			if(facingRight)
				g.drawImage(characterImageR,(int)position.getX(),(int)position.getY(),width,height,null);
			else{
				g.drawImage(characterImageL,(int)position.getX(),(int)position.getY(),width,height,null);
			}
		}catch (Exception e) {
			g.setColor(Color.blue);
			g.fillRect((int)position.getX(),(int)position.getY(),width,height);
			g.setColor(Color.black);
			g.drawRect((int)position.getX(),(int)position.getY(),width,height);
		}
		if(gun!=null){
			gun.draw(g);
		}
		if(block!=null){
			block.draw(g);
		}
	}

	private void accelerateXToward(double n){
		double change=.4;
		if(airborn){
			change=.2;
		}
		if(velocity.getXComponent()>=n-change&&velocity.getXComponent()<=n+change){
			velocity.setXComponent(n);
			return;
		}
		if(velocity.getXComponent()>n)
			velocity.setXComponent(velocity.getXComponent()-change);
		else
			velocity.setXComponent(velocity.getXComponent()+change);
	}

	public void act() {
		if(upIsPressed&&!airborn){
			velocity.setYComponent(0);
			addForce(new Vector(10,Math.PI/2,true));
		}

		if(rightIsPressed&&velocity.getXComponent()<5){
			accelerateXToward(5);
		}

		if(leftIsPressed&&velocity.getXComponent()>-5){
			accelerateXToward(-5);
		}

		if(downIsPressed){
			
		}

		if(!rightIsPressed&&!leftIsPressed){
			accelerateXToward(0);
		}

		if(!collidedWithGroundLastTime){
			airborn=true;
		}

		collidedWithGroundLastTime=false;
		Coordinate pos =position.getCopy();
		if(gun!=null){
			if(facingRight)
				pos.update(new Vector(15,40,false));
			else
				pos.update(new Vector(0,40,false));
			gun.setVelocity(velocity);
			gun.setPosition(pos);
			gun.act();
		}

		if(block!=null){
			if(facingRight)
				pos.update(new Vector(35,-20,false));
			else
				pos.update(new Vector(-65,-20,false));
			block.setVelocity(velocity);
			block.setPosition(pos);
			block.act();
		}
		blockBox=true;
		ArrayList<GameObject> intersections=game.getIntersections(this, true);
		for(int i=0; i<intersections.size();i++){
			if(intersections.get(i) instanceof Block&&downIsPressed&&block==null){
				block=(Block)intersections.get(i);
				game.remove(block);
				blockPickedUp=true;
			}
		}
		blockBox=false;
	}

	public Rectangle getHitbox() {
		if(blockBox)
			return new Rectangle((int)position.getX()-30,(int)position.getY()-30,width+60,height+60);
		return new Rectangle((int)position.getX(),(int)position.getY(),width,height);
	}

	public boolean affectedByGravity() {
		return true;
	}

	public void collidedWith(GameObject obj, boolean collidedHorizonatlly) {
		if(!collidedHorizonatlly&&obj.getPosition().getY()>position.getY()&&obj.solid()){
			airborn=false;
			collidedWithGroundLastTime=true;
		}

		if(obj instanceof Portal)
			airborn=true;

	}

	public double getMass() {
		return 1;
	}

	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
			upIsPressed=true;
		}

		if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
			rightIsPressed=true;
			facingRight=true;
		}

		if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
			leftIsPressed=true;
			facingRight=false;
		}

		if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
			downIsPressed=true;
		}

	}

	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
			upIsPressed=false;
		}

		if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
			rightIsPressed=false;
		}

		if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
			leftIsPressed=false;
		}

		if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
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

	public void keyTyped(KeyEvent e) {
		
	}
	
	public double getElasticity() {
		return 0;
	}

}