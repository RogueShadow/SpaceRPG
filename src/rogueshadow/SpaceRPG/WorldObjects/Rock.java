package rogueshadow.SpaceRPG.WorldObjects;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import rogueshadow.SpaceRPG.Art;
import rogueshadow.SpaceRPG.Engine;
import rogueshadow.SpaceRPG.Sounds;
import rogueshadow.utility.BB;


public class Rock extends MovableObject {
	float maxVel = 50f;
	float sizeScaler = 15f;
	int rockSize;
	float rotate = 0.0f;
	float rotateSpeed = 1.5f;
	float size;
	public boolean hasMoved;
	
	public void moved(){
		this.hasMoved = true;
	}

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
		
		super.update(delta);
	}


	public void render(Graphics g) {
		g.pushTransform();
		g.setColor(new Color(0x8B4513));
		g.translate(getX(), getY());
		g.rotate(0,0, rotate);
		
		g.texture(getShape(), Art.rock, true);
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
	
	public void collided(WorldObject c) {
		// TODO Auto-generated method stub
		if (c instanceof Bullet){
			((Bullet) c).getWorld().remove((WorldObject)c);

			getWorld().remove(this);
			if (getWorld().getCamera().getBB().copy().grow(700).intersects(getBB())){
				Engine.getEngine().explosion(((Bullet) c).getX(), ((Bullet) c).getY(), 2);
				Sounds.explosion.play();	
			}
		}
	}
	
	public boolean intersects(WorldObject c) {
		return getBB().intersects(c.getBB());
	}


	@Override
	public BB getBB() {
		float w = Math.max(getShape().getWidth(), getShape().getHeight());
		return new BB(getX()-w/2,getY()-w/2,w,w);
	}

	

}
