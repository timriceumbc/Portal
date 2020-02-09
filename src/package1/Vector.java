package package1;

public class Vector {
	private double magnitude;
	private double direction;
	private double xComponent;
	private double yComponent;

	public Vector(double magOrX, double dirOrY, boolean magAndDir){
		if(magAndDir){
			magnitude=magOrX;
			direction=dirOrY;
			updateComponents();
		}else{
			xComponent=magOrX;
			yComponent=dirOrY;
			updateMagnitudeAndDirection();
		}
	}
	
	public Vector getCopy(){
		return new Vector(xComponent, yComponent,false);
	}
	
	public Vector(Vector v){
		xComponent=v.getXComponent();
		setYComponent(v.getYComponent());
	}

	public Vector add(Vector vector){
		return new Vector(xComponent+vector.getXComponent(),yComponent+vector.getYComponent(),false);
	}

	public void setMagnitude(double mag){
		magnitude=mag;
		updateComponents();
	}

	public void setDirection(double dir){
		direction=dir;
		updateComponents();
	}

	public void setXComponent(double x){
		xComponent=x;
		updateMagnitudeAndDirection();
	}

	public void setYComponent(double y){
		yComponent=y;
		updateMagnitudeAndDirection();
	}

	public double getMagnitude(){
		return magnitude;
	}

	public double getDirection(){
		return direction;
	}

	public double getXComponent(){
		return xComponent;
	}

	public double getYComponent(){
		return yComponent;
	}

	private void updateComponents(){
		xComponent=magnitude*Math.cos(direction);
		yComponent=magnitude*Math.sin(direction);
		yComponent*=-1;
	}

	private void updateMagnitudeAndDirection(){
		magnitude=Math.sqrt((xComponent*xComponent)+(yComponent*yComponent));
		if(xComponent==0&&yComponent<=0)
			direction=Math.PI/2;
		else if(xComponent==0&&yComponent>0)
			direction=3*(Math.PI/2);
		else
			direction=Math.atan(-yComponent/xComponent);
		if(yComponent<0&&xComponent<0)
			direction+=Math.PI;
		else if(xComponent<0)
			direction+=Math.PI;
	}

	public String toString(){
		return "Magnitude: "+magnitude+"\nDirection: "+direction/Math.PI*180+"\nComponents: <"+xComponent+","+yComponent+">";
	}

}