package rogueshadow.utility;

import org.newdawn.slick.Graphics;


public class BB {
	public Point min;
	public Point max;
	
	public BB(double minx, double miny, double width, double height){
		this( new Point(minx, miny) , new Point(minx+width, miny+height) );
	}

	public BB(Point min,  Point max){
		super();
		this.min = min;
		this.max = max;
	}
	public BB(BB box){
		this(new Point(box.min), new Point(box.max));
	}
	
	public BB grow(float value){
		min.x -= value;
		min.y -= value;
		max.x += value;
		max.y += value;
		return this;
	}
	
	public void render(Graphics g){
		g.drawRect((float)min.x,(float)min.y,(float)getWidth(),(float)getHeight());
	}
	
	public BB copy(){
		return new BB(this);
	}
	
	public boolean intersects(BB bb){
		if (min.x >= bb.max.x || max.x < bb.min.x){
			return false;
		}
		if (min.y >= bb.max.y || max.y < bb.min.y){
			return false;
		}
		return true;
	}
	
	public boolean contains(BB box){
		if (min.x >= box.min.x)return false;
		if (max.x < box.max.x)return false;
		if (min.y >= box.min.y)return false;
		if (max.y < box.max.y)return false;
		return true;
	}
	
	public boolean contains(Point p){
		if (p.x >= min.x && p.x < max.x){
			if (p.y >= min.y && p.y < max.y){
				return true;
			}
		}
		return false;
	}
	public double getWidth(){
		return max.x - min.x;
	}
	public double getHeight(){
		return max.y - min.y;
	}
	
	@Override	
	public String toString(){
		return "Min"+min.toString() + " Max" + max.toString();
		
	}
	
}
