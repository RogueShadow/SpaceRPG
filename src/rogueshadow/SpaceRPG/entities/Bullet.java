package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.util.BB;


public class Bullet extends MovableObject {
	protected int rotation = 0;
	protected int life = 2000000;
	public Object parent = null;
	protected Ship owner;
	float size;


	public Bullet(float x, float y, Vector2f velocity, Object parent) {
		super(x,y);
		setVelocity(velocity);
		setShape(new Rectangle(-4,-4,8,8));
		getShape().setCenterX(0);
		getShape().setCenterY(0);
		this.parent = parent;
		size = 4;

	}

	public void update(int delta){
		super.update(delta);
		rotation = (rotation + 3)%180;
		life -= delta;
		if (life < 0){
			getWorld().remove(this);
		}
		
	}
	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.translate(getX(), getY());
		g.rotate(0, 0, rotation); 
		g.setColor(new Color(0xFF0000));
		g.draw(getShape());

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

	
	public void collided(WorldObject c) {
		// TODO Auto-generated method stub
		
		
	}
	public boolean intersects(WorldObject c) {
		return getBB().intersects(c.getBB());
	}

	@Override
	public BB getBB() {
		float w = Math.max(getShape().getWidth(), getShape().getHeight());
		return new BB(getX()-w/2,getY()-w/2,w,w);
	}
	
	@Override
	public boolean isAlwaysUpdated(){
		return true;
	}



}
