package package1;
 import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Portal extends GameObject{
   Vector velocity;
   Coordinate currentPosition;
   BufferedImage PortImage;
   Boolean PortColor;
   private Boolean vertical=false;
   String PortalFacing;
   Boolean doSomething=true;
   Game game;
   Wall wall;

   public Boolean checkWallPosition(){
       if(PortalFacing.equals("east")||PortalFacing.equals("west")){
           if(getPosition().getY()-0.25*wall.getHeight()>(wall.getPosition().getY()+(wall.getHeight()/2))){
               setPosition(new Coordinate(getPosition().getX(),getPosition().getY()-1));
               return false;
           }else if(getPosition().getY()<(wall.getPosition().getY()-(wall.getHeight()/2))){
               setPosition(new Coordinate(getPosition().getX(),getPosition().getY()+1));
               return false;            
           }
       }else{
           if(getPosition().getX()-300>(wall.getPosition().getX()+(wall.getWidth()/2))){
               setPosition(new Coordinate(getPosition().getX()-1,getPosition().getY()));
               return false;
           }else if(getPosition().getX()-300<(wall.getPosition().getX()-(wall.getWidth()/2))){
               setPosition(new Coordinate(getPosition().getX()+1,getPosition().getY()));
               return false;
           }
       }
       return true;
   }

   Portal(Boolean PortalColor, Boolean horizontal, String mapDirection,Game passGame,Wall collidedWall){
       game=passGame;
       wall=collidedWall;
       PortalFacing=mapDirection;
       try {
           if(PortalColor){
               PortImage= ImageIO.read(passGame.getFrame().getClass().getResource("/images/redPortal.gif"));
           }else{
               PortImage= ImageIO.read(passGame.getFrame().getClass().getResource("/images/bluePortal.gif"));
           }
       } catch (IOException e) {
       }
       if(!horizontal){
           vertical=true;
       }else{
           vertical=false;
       }
       PortColor=PortalColor;
   }

   public void setDoSomething(Boolean a){

       doSomething=a;

   }

   public Boolean getPortalColor(){

       return PortColor;

   }

   public void act() {

   }

   public boolean affectedByGravity() {

       return false;

   }

   public void draw(Graphics g) {

       while(!checkWallPosition()){

       }

       if(vertical){

           Graphics2D g2 = (Graphics2D) g;

           //            AffineTransform turn = new AffineTransform();

           //            double rotationRequired = Math.PI/2;

           //            double locationX = PortImage.getWidth() / 2;

           //            double locationY = (PortImage.getHeight() / 2)-40;

           //            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);

           //            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

           // Drawing the rotated image at the required drawing locations

           g2.rotate(Math.PI/2,getPosition().getX(),(int)getPosition().getY());

           g2.drawImage(PortImage, (int)getPosition().getX(), (int)getPosition().getY()-40, null);

           g2.rotate(-Math.PI/2,getPosition().getX(),(int)getPosition().getY());

       }else{

           g.drawImage(PortImage,(int)getPosition().getX(),(int)getPosition().getY(), null);

       }

       

   }

   public Vector getAcceleration() {

       return new Vector(0,0,true);

   }

   public Rectangle getHitbox() {

       try{

           if(PortColor){

               if(vertical){

                   return new Rectangle((int)getPosition().getX()-65,(int)getPosition().getY(),PortImage.getHeight(),PortImage.getWidth());

               }else if(PortalFacing.equals("east")){

                   return new Rectangle((int)getPosition().getX(),(int)getPosition().getY(),PortImage.getWidth(),PortImage.getHeight());

               }else{

                   return new Rectangle((int)getPosition().getX(),(int)getPosition().getY(),PortImage.getWidth(),PortImage.getHeight());

               }

           }else{

               if(vertical){

                   return new Rectangle((int)getPosition().getX()-65,(int)getPosition().getY()+5,PortImage.getHeight(),PortImage.getWidth()-5);

               }else if(PortalFacing.equals("east")){

                   return new Rectangle((int)getPosition().getX(),(int)getPosition().getY(),PortImage.getWidth(),PortImage.getHeight());

               }else{

                   return new Rectangle((int)getPosition().getX(),(int)getPosition().getY(),PortImage.getWidth(),PortImage.getHeight());

               }

           }

       }catch(Exception ex){

           return new Rectangle((int)getPosition().getX(),(int)getPosition().getY(),PortImage.getHeight(),PortImage.getWidth());

       }

   }

   public double getMass() {

       return 0;

   }

   public Coordinate getPosition() {

       return currentPosition.getCopy();

   }

   public Vector getVelocity() {

       return velocity.getCopy();

   }

   public void setAcceleration(Vector acc) {

   }

   public void setPosition(Coordinate pos) {

       currentPosition= pos;

   }

   public void setVelocity(Vector vel) {

       velocity=vel.getCopy();

   }


   public boolean solid() {

       return false;

   }

   public void collidedWith(GameObject obj, boolean collidedHorizontally) {

       if(!(obj instanceof Wall)&&!(obj instanceof PortalShot)){

           try{

               if(PortColor){

                   if(PortalShot.getBluePortal().PortalFacing.equals("east")){

                       obj.setPosition(new Coordinate(PortalShot.getBluePortal().getPosition().getX()+65,PortalShot.getBluePortal().getPosition().getY()));

                       obj.setVelocity(new Vector(obj.getVelocity().getMagnitude(),0,true));

                   }else if(PortalShot.getBluePortal().PortalFacing.equals("west")){

                       obj.setPosition(new Coordinate(PortalShot.getBluePortal().getPosition().getX()-50,PortalShot.getBluePortal().getPosition().getY()));

                       obj.setVelocity(new Vector(obj.getVelocity().getMagnitude(),(double)(Math.PI),true));

                   }else if(PortalShot.getBluePortal().PortalFacing.equals("north")){

                       obj.setPosition(new Coordinate(PortalShot.getBluePortal().getPosition().getX(),PortalShot.getBluePortal().getPosition().getY()-100));

                       obj.setVelocity(new Vector(obj.getVelocity().getMagnitude(),(double)(Math.PI)/2,true));

                   }else if(PortalShot.getBluePortal().PortalFacing.equals("south")){

                       obj.setPosition(new Coordinate(PortalShot.getBluePortal().getPosition().getX(),PortalShot.getBluePortal().getPosition().getY()+60));

                       obj.setVelocity(new Vector(obj.getVelocity().getMagnitude(),(double)(3*Math.PI)/2,true));

                   }

               }else if(!PortColor){

                   if(PortalShot.getRedPortal().PortalFacing.equals("east")){

                       obj.setPosition(new Coordinate(PortalShot.getRedPortal().getPosition().getX()+65,PortalShot.getRedPortal().getPosition().getY()));

                       obj.setVelocity(new Vector(obj.getVelocity().getMagnitude(),0,true));

                   }else if(PortalShot.getRedPortal().PortalFacing.equals("west")){

                       obj.setPosition(new Coordinate(PortalShot.getRedPortal().getPosition().getX()-50,PortalShot.getRedPortal().getPosition().getY()));

                       obj.setVelocity(new Vector(obj.getVelocity().getMagnitude(),(double)(Math.PI),true));

                   }else if(PortalShot.getRedPortal().PortalFacing.equals("north")){

                       obj.setPosition(new Coordinate(PortalShot.getRedPortal().getPosition().getX(),PortalShot.getRedPortal().getPosition().getY()-100));

                       obj.setVelocity(new Vector(obj.getVelocity().getMagnitude(),(double)(Math.PI)/2,true));

                   }else if(PortalShot.getRedPortal().PortalFacing.equals("south")){

                       obj.setPosition(new Coordinate(PortalShot.getRedPortal().getPosition().getX(),PortalShot.getRedPortal().getPosition().getY()+60));

                       obj.setVelocity(new Vector(obj.getVelocity().getMagnitude(),(double)(3*Math.PI)/2,true));

                   }

               }

           }catch(Exception ex){

           }

       }

   }

   @Override

   public double getElasticity() {

       return 0;

   }

}