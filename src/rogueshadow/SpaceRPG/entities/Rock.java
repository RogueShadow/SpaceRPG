package rogueshadow.SpaceRPG.entities;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Engine;
import rogueshadow.SpaceRPG.interfaces.Collidable;
import rogueshadow.SpaceRPG.interfaces.Renderable;
import rogueshadow.SpaceRPG.interfaces.Updatable;


public class Rock extends MovableObject implements Updatable, Renderable, Collidable {
	float maxVel = 50f;
	float sizeScaler = 15f;
	int rockSize;
	float rotate = 0.0f;
	float rotateSpeed = 1.5f;
	float size;

	public int getRockSize() {
		return rockSize;
	}
	protected void generatePolygon(){
		Polygon poly = new Polygon();
		int size = (int)(getSize()/2.5f);
		int distortion = (int)(size*0.8);
		int points = 16;
		float step = (float)(6.28)/points;
		float dist;
		for (float i = 0; i < 6.28; i += step){
			dist = (float)Math.random()*distortion;
			poly.addPoint((float)Math.sin(i)*(size + dist),(float)Math.cos(i)*(size +dist));
		}
		poly.setCenterX(0);
		poly.setCenterY(0);
		setShape(poly);
	}
	
	public Rock(float x, float y, int size) {
		super(x,y);
		this.size = (size*sizeScaler);
		rockSize = size;
		rotateSpeed = -0.5f + (float)Math.random()*1f;
		generatePolygon();
	}


	public void update(int delta) {

		rotate += rotateSpeed;
	}


	public void render(Graphics g) {
		g.pushTransform();
		g.setColor(new Color(0x8B4513));
		g.translate(getX(), getY());
		g.rotate(0,0, rotate);
		
		g.draw(getShape());

		g.popTransform();
	}
	



	public float getSize() {
		return size;
	}


	public float getCenterX() {
		return getX() + getSize()/2f;
	}

	public float getCenterY() {
		return getY() + getSize()/2f;
	}

	public boolean isActive() {
		return true;
	}
	@Override
	public boolean isAlwaysUpdated() {
		return false;
	}
	
	public void collided(Collidable c) {
		// TODO Auto-generated method stub
		if (c instanceof Bullet){
			Vector2f a = ((Bullet) c).getVelocity();
			getVelocity().add(a.copy().scale(0.1f));
			((Bullet) c).getWorld().remove((WorldObject)c);
			Engine.getEngine().explosion(((Bullet) c).getX(), ((Bullet) c).getY(), 2);
			
		}
	}
	
	public boolean intersects(Collidable c) {
		Circle a = getCollisionShape();
		Circle b = c.getCollisionShape();
		return a.intersects(b);
	}

	
	public Circle getCollisionShape() {
		return new Circle(getCenterX(), getCenterY(), getShape().getBoundingCircleRadius());
	}

	

}
