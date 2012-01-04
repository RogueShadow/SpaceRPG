package rogueshadow.SpaceRPG.entities;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Level;

public class Rock extends AbstractEntity implements Entity {
	float maxVel = 50f;
	float sizeScaler = 15f;
	int rockSize;
	float rotate = 0.0f;
	float rotateSpeed = 1.5f;
	Polygon poly = new Polygon();

	public int getRockSize() {
		return rockSize;
	}
	protected void generatePolygon(){
		this.poly = new Polygon();
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
	}
	
	public Rock(Level lvl, Vector2f position, Vector2f velocity, int size) {
		super(lvl,position, velocity);
		setSize(size*sizeScaler);
		rockSize = size;
		rotateSpeed = -0.5f + (float)Math.random()*1f;
		generatePolygon();
	}

	@Override
	public void update(int delta) {
		if (isDestroyed())return;
		super.update(delta);
		rotate += rotateSpeed;
	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.setColor(Color.cyan);
		g.translate(getX(), getY());
		g.rotate(0,0, rotate);
		
		g.draw(poly);

		g.popTransform();
	}
	

	@Override
	public void collided(Entity other) {
		if (isDestroyed())return;
		if (other instanceof Bullet){
			//TODO remove test code from here.
			getLevel().remove(this);
			other.getVelocity().negateLocal();
		}


	}
	

	

}
