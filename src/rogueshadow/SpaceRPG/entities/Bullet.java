package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Renderable;
import rogueshadow.SpaceRPG.Updatable;
import rogueshadow.SpaceRPG.WorldObject;

public class Bullet extends WorldObject implements Updatable, Renderable {
	protected int rotation = 0;
	protected int life = 2000;
	Vector2f velocity;
	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	protected Ship owner;
	float size;

	public Bullet(float x, float y, Vector2f velocity) {
		super(x,y);
		setVelocity(velocity);
		size = 4;

	}

	public void update(int delta){
		x += getVelocity().copy().scale(delta/1000f).x;
		y += getVelocity().copy().scale(delta/1000f).y;
		rotation = (rotation + 3)%180;
		life -= delta;
		if (life < 0){
			getWorld().remove(this);
		}
		
	}
	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.rotate(getCenterX(), getCenterY(), rotation);
		g.setColor(Color.green);
		g.drawRect(getX(),getY(),getSize(),getSize());
		g.popTransform();
	}



	public float getCenterX() {
		return getX() + getSize()/2f;
	}

	public float getCenterY() {
		return getY() + getSize()/2f;
	}

	public float getSize() {
		return size;
	}

	public boolean isAlwaysUpdated() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}

}
