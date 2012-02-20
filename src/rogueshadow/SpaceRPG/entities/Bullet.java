package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.interfaces.Collidable;
import rogueshadow.SpaceRPG.interfaces.Renderable;


public class Bullet extends MovableObject implements Renderable, Collidable{
	protected int rotation = 0;
	protected int life = 2000;
	public Object parent = null;
	protected Ship owner;
	float size;
	private boolean hasMoved = false;

	public void moved() {
		this.hasMoved = true;
	}
	public void reset(){
		this.hasMoved = false;
	}

	public Bullet(float x, float y, Vector2f velocity, Object parent) {
		super(x,y);
		setVelocity(velocity);
		setShape(new Rectangle(-4,-4,8,8));
		this.parent = parent;
		size = 4;

	}

	public void update(int delta){
		super.update(delta);
		if (getVelocity().lengthSquared() != 0)moved();
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

	
	public void collided(Collidable c) {
		// TODO Auto-generated method stub
		
		
	}
	public boolean intersects(Collidable c) {
		return getRect().intersects(c.getRect());
	}

	@Override
	public boolean hasMoved() {
		return hasMoved ;
	}
	@Override
	public Rectangle getRect() {
		float w = Math.max(getShape().getWidth(), getShape().getHeight());
		return new Rectangle(getCenterX(),getCenterY(),w,w);
	}
	@Override
	public void setMoved(boolean value) {
		hasMoved = value;
	}

}
