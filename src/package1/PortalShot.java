package package1;
import java.awt.Graphics;

import java.awt.Polygon;

import java.awt.Graphics2D;

import java.awt.Image;

import java.awt.Rectangle;

import java.awt.geom.AffineTransform;

import java.awt.geom.PathIterator;

import java.awt.image.AffineTransformOp;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Arrays;

import javax.imageio.ImageIO;



public class PortalShot extends GameObject{

   private Coordinate currentPosition=new Coordinate(0,0);

   private Vector velocity=new Vector(0,0,false);

   private Vector acceleration=new Vector(0,0,false);

   BufferedImage PortShotImage;

   double directionAngle;

   Boolean color;

   private File file;

   private Game game;

   public static Portal currentRedPortal;

   public static Portal currentBluePortal;

   String mapDirection;

   PortalShot(Boolean PortalColor,double angle,Game passGame){    

       color=PortalColor;

       setVelocity(new Vector(20,angle+Math.PI,true));

       try {

           if(color){

               PortShotImage= ImageIO.read(passGame.getFrame().getClass().getResource("/images/redPortalShot.gif"));

           }else{

               PortShotImage= ImageIO.read(passGame.getFrame().getClass().getResource("/images/bluePortalShot.gif"));

           }

       } catch (IOException e) {

       }

       directionAngle=angle;

       game=passGame;

   }

   public void act() {

   }

   public boolean affectedByGravity() {

       return false;

   }



   public void draw(Graphics g) {

       Graphics2D g2 = (Graphics2D) g;

       AffineTransform turn = new AffineTransform();

       double rotationRequired = directionAngle*-1;

       double locationX = PortShotImage.getWidth() / 2;

       double locationY = PortShotImage.getHeight() / 2;

       AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);

       AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

       // Drawing the rotated image at the required drawing locations

       if(!color){

           g2.drawImage(op.filter(PortShotImage, null), (int)getPosition().getX()-40, (int)getPosition().getY()+0, null);

       }else{

           g2.drawImage(op.filter(PortShotImage, null), (int)getPosition().getX()-40, (int)getPosition().getY()+20, null);

       }

   }

   public Rectangle getHitbox() {

       Rectangle r=new Rectangle((int)getPosition().getX()+10,(int)getPosition().getY()+10,15,15);

       

       return r;

   }

   public double getMass() {

       return 1;

   }

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

   public boolean solid() {

       return false;

   }

   public static Portal getRedPortal(){

       return currentRedPortal;

   }

   public static Portal getBluePortal(){

       return currentBluePortal;

   }

   public Boolean getOverlap(Portal putPortal,Boolean PortalColor,Game game){

       try{

           ArrayList<GameObject> a=game.getIntersections(putPortal,game.getPortals());

           for(int i=0;i<a.size();i++){

               Portal p=(Portal)a.get(i);

               if(p.getPortalColor()==PortalColor){

                   a.remove(i);

               }

           }

           if(a.size()>0){

               return true;

               

           }

       }catch(Exception ex){

       }

       return false;

   }

   public void collidedWith(GameObject obj, boolean collidedHorizontally) {

       if(obj instanceof Wall){

           if(collidedHorizontally ){

               if(((Wall) obj).getHeight()>60){

                   if(getVelocity().getXComponent()>0){

                       mapDirection="west";

                   }else if(getVelocity().getXComponent()<0){

                       mapDirection="east";

                   }

                   if(color){

                       Portal testPortal=new Portal(true,true,mapDirection,game,(Wall) obj);

                       //                  while((obj.getPosition().getY()+((Wall) obj).getHeight()/2-getPosition().getY()+60)<=0){

                       //                      setPosition

                       //                  }

                       testPortal.setPosition(new Coordinate(getPosition().getX(),getPosition().getY()));

                       testPortal.setVelocity(new Vector(0,0,true));

                       if(getOverlap(testPortal,color,game)){

                           game.remove(testPortal);

                           game.remove(this);

                       }else{

                           game.remove(currentRedPortal);

                           currentRedPortal=new Portal(true,true,mapDirection,game,(Wall) obj);

                           game.add(currentRedPortal);

                           currentRedPortal.setPosition(new Coordinate(getPosition().getX(),getPosition().getY()));

                           currentRedPortal.setVelocity(new Vector(0,0,true));

                           game.remove(this);

                       }

                   }else{

                       Portal testPortal=new Portal(false,true,mapDirection,game,(Wall) obj);

                       testPortal.setPosition(new Coordinate(getPosition().getX(),getPosition().getY()));

                       testPortal.setVelocity(new Vector(0,0,true));

                       if(getOverlap(testPortal,color,game)){

                           game.remove(testPortal);

                           game.remove(this);

                       }else{

                           game.remove(currentBluePortal);

                           currentBluePortal=new Portal(false,true,mapDirection,game,(Wall) obj);

                           game.add(currentBluePortal);

                           currentBluePortal.setPosition(new Coordinate(getPosition().getX(),getPosition().getY()));

                           currentBluePortal.setVelocity(new Vector(0,0,true));

                           game.remove(this);

                       }

                   }

               }else{

                   game.remove(this);

               }

           }else{

               if(((Wall) obj).getWidth()>60){

                   if(getVelocity().getYComponent()<0){

                       mapDirection="south";

                   }else if(getVelocity().getYComponent()>0){

                       mapDirection="north";

                   }

                   if(color){

                       Portal testPortal=new Portal(true,false,mapDirection,game,(Wall) obj);

                       testPortal.setPosition(new Coordinate(getPosition().getX(),getPosition().getY()));

                       testPortal.setVelocity(new Vector(0,0,true));

                       if(getOverlap(testPortal,color,game)){

                           game.remove(testPortal);

                           game.remove(this);

                       }else{

                           game.remove(currentRedPortal);

                           currentRedPortal=new Portal(true,false,mapDirection,game,(Wall) obj);

                           game.add(currentRedPortal);

                           currentRedPortal.setPosition(new Coordinate(getPosition().getX(),getPosition().getY()));

                           currentRedPortal.setVelocity(new Vector(0,0,true));

                           game.remove(this);

                       }

                   }else{

                       Portal testPortal=new Portal(false,false,mapDirection,game,(Wall) obj);

                       testPortal.setPosition(new Coordinate(getPosition().getX(),getPosition().getY()));

                       testPortal.setVelocity(new Vector(0,0,true));

                       if(getOverlap(testPortal,color,game)){

                           game.remove(testPortal);

                           game.remove(this);

                       }else{

                           game.remove(currentBluePortal);

                           currentBluePortal=new Portal(false,false,mapDirection,game,(Wall) obj);

                           game.add(currentBluePortal);

                           currentBluePortal.setPosition(new Coordinate(getPosition().getX(),getPosition().getY()));

                           currentBluePortal.setVelocity(new Vector(0,0,true));

                           game.remove(this);

                       }

                   }

               }else{

                   game.remove(this);

               }

           }

       }

   }

   @Override

   public double getElasticity() {

       return 0;

   }

}