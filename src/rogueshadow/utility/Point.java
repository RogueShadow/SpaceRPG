package rogueshadow.utility;

public class Point {
	public double x;
	public double y;
	
	public Point(double x, double y){
		super();
		this.x = x;
		this.y = y;
	}
	public Point(Point p){
		this.x = p.x;
		this.y = p.y;
	} 
	
	@Override
	public String toString(){
		return "("+x+","+y+")";
	}
	
}
