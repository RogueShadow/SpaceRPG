package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.geom.Vector2f;


public class MovableObject extends WorldObject  {
	private Vector2f velocity;


	public MovableObject(float x, float y) {
		super(x, y);

		velocity = new Vector2f(0,0);
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}


	@Override
	public void update(int delta) {
		getPosition().add(getVelocity().copy().scale(delta/1000f));
		checkPosition();
		super.update(delta);
	}


}