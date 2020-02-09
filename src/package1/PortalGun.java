package package1;
import java.awt.Frame;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Image;

import java.awt.MouseInfo;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.awt.event.MouseMotionListener;

import java.awt.geom.AffineTransform;

import java.awt.image.AffineTransformOp;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JFrame;

import javax.swing.text.Position;


public class PortalGun extends GameObject implements MouseMotionListener,MouseListener{

   private Coordinate currentPosition=new Coordinate(0,0);

   private Vector velocity=new Vector(0,0,false);

   private Vector acceleration=new Vector(0,0,false);

   private Character person;

   private BufferedImage PortGunImage;

   private Vector gunVect=new Vector(0,0,false);

   private int height=0;

   private int width=0;

   private double angle;

   private double x=0, y=0;

   private Game game;

   PortalGun(Character man, Game g){

       game=g;

       Vector gunVector=new Vector((int)(MouseInfo.getPointerInfo().getLocation().getX()/game.scale()-getPosition().getX()),
    		   (int)(MouseInfo.getPointerInfo().getLocation().getY()/game.scale()-getPosition().getY()),false);

       gunVect=gunVector;

       person=man;

       currentPosition=man.getPosition();

       try {

           PortGunImage=ImageIO.read(g.getFrame().getClass().getResource("/images/portalGun.gif"));

       } catch (IOException e) {

           e.printStackTrace();

       }

   }

   public void act(){

       gunVect=new Vector((int)(x-getPosition().getX()),(int)(y-getPosition().getY()),false);
       
       //gunVect=game.updateForTranslation(gunVect);

       angle= gunVect.getDirection()+Math.PI;

   }

   @Override

   public void mouseDragged(MouseEvent arg0) {

       // TODO Auto-generated method stub

   }

   public void mouseMoved(MouseEvent arg0) {

       x=arg0.getX()/game.scale();

       y=arg0.getY()/game.scale();

       gunVect=new Vector((int)(x-getPosition().getX()),y-getPosition().getY(),false);

       angle= gunVect.getDirection()+Math.PI;

   }

   public void mouseClicked(MouseEvent arg0) {


   }

   public void mouseEntered(MouseEvent arg0) {

   }

   @Override

   public void mouseExited(MouseEvent arg0) {

   }

   @Override

   public void mousePressed(MouseEvent arg0) {

       if(arg0.isMetaDown()){

           Coordinate pos =getPosition().getCopy();

           pos.update(new Vector(-15,-40,false));

           PortalShot shot=new PortalShot(true,angle,game);

           shot.setPosition(pos);

           game.add(shot);

       }else{

           PortalShot shot=new PortalShot(false,angle,game);

           shot.setPosition(getPosition());

           game.add(shot);

       }

   }

   @Override

   public void mouseReleased(MouseEvent arg0) {

   }

   @Override

   public boolean affectedByGravity() {

       // TODO Auto-generated method stub

       return false;

   }

   @Override

   public void draw(Graphics g) {

       Graphics2D g2 = (Graphics2D) g;

       AffineTransform turn = new AffineTransform();

       double rotationRequired = angle*-1;

       double locationX = PortGunImage.getWidth() / 2;

       double locationY = PortGunImage.getHeight() / 2;

       AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);

       AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

       // Drawing the rotated image at the required drawing locations

       g2.drawImage(op.filter(PortGunImage, null), (int)getPosition().getX(), (int)getPosition().getY(),(int)(PortGunImage.getWidth()*.75),(int)(PortGunImage.getHeight()*.75), null);

   }

   @Override

   public Rectangle getHitbox() {

       width=PortGunImage.getWidth();

       height=PortGunImage.getHeight();

       return new Rectangle((int)currentPosition.getX(),(int)currentPosition.getY(),width,height);

   }

   @Override

   public double getMass() {

       return 1;

   }

   @Override

   public Coordinate getPosition() {

       return currentPosition.getCopy();

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

       //System.out.println(currentPosition);

       currentPosition=pos.getCopy();

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

       // TODO Auto-generated method stub

       return false;

   }

   @Override

   public void collidedWith(GameObject obj, boolean collidedHorizonatlly) {

       // TODO Auto-generated method stub

   }

   @Override

   public double getElasticity() {

       return 0;

   }

}