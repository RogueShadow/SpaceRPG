package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import rogueshadow.SpaceRPG.Renderable;
import rogueshadow.SpaceRPG.WorldObject;

public class Planet extends WorldObject implements Renderable {
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
