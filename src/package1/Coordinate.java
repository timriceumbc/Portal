package package1;

public class Coordinate {
	private double X;
	private double Y;

	public Coordinate(double x, double y){
		X=x;
		Y=y;
	}

	public double getX(){
		return X;
	}

	public double getY(){
		return Y;
	}

	public Coordinate getCopy(){
		return new Coordinate(X,Y);
	}

	public void update(Vector vector){
		X+=vector.getXComponent();
		Y+=vector.getYComponent();
	}
	
	public void updateX(Vector vector){
		X+=vector.getXComponent();
	}
	
	public void updateY(Vector vector){
		Y+=vector.getYComponent();
	}

	public String toString(){
		return "("+X+","+Y+")";
	}

}
