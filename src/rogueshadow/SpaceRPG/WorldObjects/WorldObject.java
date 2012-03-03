package rogueshadow.SpaceRPG.WorldObjects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Engine;
import rogueshadow.SpaceRPG.World;
import rogueshadow.utility.BB;
import rogueshadow.utility.Point;

public class WorldObject {
	public World world;
	public Shape shape;
	Vector2f position = null;
	public Point oldPos;

	public boolean removed = false;
	
	public WorldObject(float x, float y) {
		this.world = null;
		this.position = new Vector2f(x,y);
		oldPos = new Point(x,y);
	}
	public World getWorld() {
		return world;
	}
	public void update(int delta){
		if (!Engine.getBB().contains(getBB())){
			getWorld().remove(this);
		}
	}
	public boolean isAlwaysUpdated() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public void setShape(Shape shape){
		this.shape = shape;
	}
	public void render(Graphics g){
	}
	public Shape getShape() {
		return this.shape;
	}
	public float getX() {
		return getPosition().x;
	}
	public void setX(float x) {
		getPosition().x = x;
	}
	public float getY() {
		return getPosition().y;
	}
	public void setY(float y) {
		getPosition().y = y;
	}
	public Vector2f getPosition(){
		return this.position;
	}
	public void setPosition(float x, float y){
		setPosition(new Vector2f(x,y));
	}
	public void setPosition(Vector2f position){
		this.position = position;
		checkPosition();
	}
	
	public BB getBB() {
		return new BB(getX(), getY(), 1,1);
	}
	public void collided(WorldObject obj) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkPosition(){ // returns true if moved.

		if (getPosition().x != oldPos.x || getPosition().y != oldPos.y){
			getWorld().hasMoved(new Point(oldPos), this);
			oldPos.x = getPosition().x;
			oldPos.y = getPosition().y;
			return true;
		}

		return false;
	}
	
	public Point getPoint() {
		return new Point(getPosition().x,getPosition().y);
	}


}
