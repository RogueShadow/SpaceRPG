package rogueshadow.SpaceRPG.WorldObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class Planet extends WorldObject {
	String name;
	float size;

	public Planet(float x, float y) {
		super(x,y);
		this.size = 50;
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.blue);
		g.drawOval(getCenterX(), getCenterY(), getSize(), getSize());

	}

	public float getCenterX() {
		return getX() + getSize()/2f;
	}

	public float getCenterY() {
		return getX() + getSize()/2f;
	}

	public float getSize() {
		return size;
	}

}
