package rogueshadow.SpaceRPG;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.AbstractEntity;

public class Rock extends AbstractEntity implements Entity {
	float maxVel = 50f;
	float lastCall = 0;
	float thisCall = 0;
	float sizeScaler = 15f;
	int rockSize;
	public int getRockSize() {
		return rockSize;
	}


	float rotate = 0.0f;
	float rotateSpeed = 1.5f;
	Polygon poly = new Polygon();

	
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
	
	public Rock(Vector2f position, Vector2f velocity, int size) {
		super(position, velocity);
		setSize(size*sizeScaler);
		rockSize = size;
		rotateSpeed = -1.5f + (float)Math.random()*3f;
		generatePolygon();
	}

	@Override
	public void update(EntityManager manager, int delta) {
		if (isDestroyed())return;
		super.update(delta);
		rotate += rotateSpeed;
		if (getVelocity().lengthSquared() > (maxVel*maxVel)){
			getVelocity().scale(0.9f);
		}else getVelocity().scale(1.05f);
		if (manager.getContainer().getInput().isKeyPressed(Input.KEY_X)){
			generatePolygon();
		}
	}

	@Override
	public void render(Graphics g) {
		if (!isVisible())return;
		g.pushTransform();
		g.setColor(Color.cyan);
		g.translate(getX(), getY());
		g.drawString(Integer.toString(rockSize), 0, 0);
		g.rotate(0,0, rotate);
		
		g.draw(poly);
		//g.fillOval(-getSize()/2,-getSize()/2, getSize()	,getSize());
		g.popTransform();
	}
	

	@Override
	public void collided(EntityManager manager, Entity other) {
		if (isDestroyed())return;
		if (other instanceof Rock){
			//Vector2f vec = new Vector2f(getCenterX()-other.getCenterX(),getCenterY()-other.getCenterY());
			//getVelocity().add(vec);
		}
		if (other instanceof Ship){



		}else
		if (other instanceof Bullet){

			
		}
	}
	

	

}
