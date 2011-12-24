/**
 * 
 */
package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Camera;
import rogueshadow.SpaceRPG.CollisionInfo;

/**
 * @author Adam
 *
 */
public interface Entity {

	public void update(int delta);
	
	public void render(Graphics g);
	
	public Float getSize();
	
	public CollisionInfo collides(Entity other);
	
	public void collided(Entity other);
	
	public float getX();
	
	public float getY();
	
	public float getCenterX();
	
	public float getCenterY();
	
	public Vector2f getPosition();
	
	public Vector2f getVelocity();
	
	public boolean isDestroyed();
	
	public void setDestroyed(boolean destroyed);
	
}
