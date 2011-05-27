/**
 * 
 */
package rogueshadow.combat2;

import org.newdawn.slick.geom.Vector2f;


/**
 * @author Adam
 *
 */
public abstract class AbstractEntity implements Entity {
	protected final int WIDTH = Combat2.WORLD_WIDTH;
	protected final int HEIGHT = Combat2.WORLD_HEIGHT;
	protected Vector2f position = new Vector2f();
	protected Vector2f velocity = new Vector2f();
	protected float size = 0;
	protected boolean destroyed = false;
	protected Camera cam;
	
	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public CollisionInfo collides(Entity other){
		float distance = getPosition().distanceSquared(other.getPosition());
		float range = getSize()/2f + other.getSize()/2f;
		
		range *= range;
		if (distance < range){
			return new CollisionInfo(this, other, true, distance);
		}else{
			return new CollisionInfo(this, other, false, distance);
		}
	}
	
	public void update(int delta){
		position.add(velocity.copy().scale((delta/1000f)));
		if (position.getX() < -size/2)position.x = WIDTH + size/2;
		if (position.getX() > WIDTH + size/2)position.x = -size/2;
		if (position.getY() < -size/2)position.y = HEIGHT +size/2;
		if (position.getY() > HEIGHT + size/2)position.y = -size/2;
		
	}
	
	public float getX(){
		return position.getX();
	}
	
	public float getY(){
		return position.getY();
	}
	public float getCenterX(){
		return position.getX()+getSize()/2f;
	}
	
	public float getCenterY(){
		return position.getY()+getSize()/2f;
	}
	
	public float getSize(){
		return size;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public AbstractEntity(Vector2f position, Vector2f velocity) {
		super();
		this.position = position;
		this.velocity = velocity;
		this.size = 0;
	}
	public void setSize(float size){
		this.size = size;
	}
	
}
