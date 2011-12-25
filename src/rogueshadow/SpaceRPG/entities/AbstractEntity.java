/**
 * 
 */
package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Camera;
import rogueshadow.SpaceRPG.CollisionInfo;
import rogueshadow.SpaceRPG.Level;
import rogueshadow.SpaceRPG.SpaceRPG;


/**
 * @author Adam
 *
 */
public abstract class AbstractEntity implements Entity {
	protected Integer WIDTH = SpaceRPG.WORLD_WIDTH;
	protected Integer HEIGHT = SpaceRPG.WORLD_HEIGHT;
	protected Vector2f position = new Vector2f(0,0);
	protected Vector2f velocity = new Vector2f(0,0);
	protected float size = 0;
	protected boolean destroyed = false;
	protected Level level;
	protected boolean active = true;
	protected boolean persistent = false;
	
	public void setPersistent(boolean persistent){
		persistent = false;
	}
	public boolean isPersistent(){
		return persistent;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public boolean isActive()	{
		return active;
	}
	
	public void setActive(boolean value){
		active = value;
	}

	public Level getLevel(){
		return level;
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
		getPosition().add(getVelocity().copy().scale((delta/1000f)));
		if (getX() < -getSize()/2)getPosition().x = WIDTH + getSize()/2;
		if (getX() > WIDTH + getSize()/2)getPosition().x = -getSize()/2;
		if (getY() < -getSize()/2)getPosition().y = HEIGHT +getSize()/2;
		if (getY() > HEIGHT + getSize()/2)getPosition().y = -getSize()/2;
		
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
	
	public Float getSize(){
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

	public AbstractEntity(Level level, Vector2f position, Vector2f velocity) {
		super();
		this.level = level;
		this.position = position;
		this.velocity = velocity;
		this.size = 0;
	}
	public void setSize(float size){
		this.size = size;
	}
	
}
