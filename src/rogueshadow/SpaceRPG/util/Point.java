package rogueshadow.SpaceRPG.util;

public class Point {
	public float x;
	public float y;
	
	public Point(float x, float y){
		super();
		this.x = x;
		this.y = y;
	}
	public Point(Point p){
		this.x = p.x;
		this.y = p.y;
	}
}
