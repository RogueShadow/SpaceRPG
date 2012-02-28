package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.World;
import rogueshadow.SpaceRPG.util.BB;
import rogueshadow.SpaceRPG.util.GridObject;
import rogueshadow.SpaceRPG.util.Point;

public class WorldObject extends GridObject  {
	public World world;
	public Shape shape;
	Vector2f position = null;
	
	public WorldObject(float x, float y) {
		super();
		this.world = null;
		this.position = new Vector2f(x,y);
	}
	public World getWorld() {
		return world;
	}
	public void update(int delta){
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
	}
	
	public BB getBB() {
		return new BB(getX(), getY(), 1,1);
	}
	public void collided(WorldObject obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Point getPoint() {
		return new Point(getPosition().x,getPosition().y);
	}


}
