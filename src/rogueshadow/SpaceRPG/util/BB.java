package rogueshadow.SpaceRPG.util;

public class BB {
	public Point min;
	public Point max;
	
	public BB(float minx, float miny, float width, float height){
		super();
		min = new Point(minx, miny);
		max = new Point(minx+width, miny+height);
	}
	
	public boolean intersects(BB bb){
		if (min.x >= bb.max.x || max.x <= bb.min.x){
			return false;
		}
		if (min.y >= bb.max.y || max.y <= bb.min.y){
			return false;
		}
		return true;
	}
	public boolean intersects(Point p){
		if (p.x < min.x && p.x > max.x){
			return false;
		}
		if (p.y < min.y && p.y > max.y){
			return false;
		}
		return true;
	}
	public float getWidth(){
		return max.x - min.x;
	}
	public float getHeight(){
		return max.y - min.y;
	}
	
}
