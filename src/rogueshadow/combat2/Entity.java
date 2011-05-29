/**
 * 
 */
package rogueshadow.combat2;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author Adam
 *
 */
public interface Entity {

	public void update(EntityManager manager, int delta);
	
	public void render(Graphics g);
	
	public float getSize();
	
	public CollisionInfo collides(Entity other);
	
	public void collided(EntityManager manager, Entity other);
	
	public float getX();
	
	public float getY();
	
	public float getCenterX();
	
	public float getCenterY();
	
	public Vector2f getPosition();
	
	public Vector2f getVelocity();
	
	public boolean isDestroyed();
	
	public void setCam(Camera cam);
	
	public void setDestroyed(boolean destroyed);
	
}
