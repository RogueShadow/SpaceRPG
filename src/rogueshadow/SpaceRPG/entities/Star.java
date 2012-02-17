package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import rogueshadow.SpaceRPG.Renderable;
import rogueshadow.SpaceRPG.WorldObject;

public class Star extends WorldObject implements Renderable {

	public float size;
	
	public Star(float x, float y, float size) {
		super(x, y);
		setSize(size);
		setShape(new Circle(0, 0, size/2f));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.yellow);
		g.pushTransform();
		g.translate(getCenterX(), getCenterY());
		g.draw(getShape());
		g.popTransform();
	}

	public float getCenterY() {
		return getX()+getSize()/2f;
	}

	public float getCenterX() {
		return getY()+getSize()/2f;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}
	
	

}
