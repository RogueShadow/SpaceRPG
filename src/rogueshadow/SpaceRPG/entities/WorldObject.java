package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.World;
import rogueshadow.SpaceRPG.interfaces.NodeElement;
import rogueshadow.SpaceRPG.util.BB;
import rogueshadow.SpaceRPG.util.QuadTree;
import rogueshadow.SpaceRPG.util.QuadTree.Node;

public class WorldObject implements NodeElement {
	public World world;
	public Shape shape;
	private Vector2f lastPos = null;
	public QuadTree.Node leaf = null;
	Vector2f position = null;
	
	public WorldObject(float x, float y) {
		super();
		this.world = null;
		this.position = new Vector2f(x,y);
		lastPos = new Vector2f(position);
	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public void setShape(Shape shape){
		this.shape = shape;
	}
	public Shape getShape() {
		return this.shape;
	}
	public float getX() {
		return getPosition().x;
	}
	public void setX(float x) {
		getPosition().x = x;
		checkPosition();
	}
	public float getY() {
		return getPosition().y;
	}
	public void setY(float y) {
		getPosition().y = y;
		checkPosition();
	}
	public Vector2f getPosition(){
		return this.position;
	}
	public void setPosition(float x, float y){
		setPosition(new Vector2f(x,y));
		checkPosition();
	}
	public void checkPosition(){
		if (leaf != null){
			if (!getPosition().equals(lastPos)){
				lastPos = new Vector2f(getPosition());
				leaf.hasMoved(this);
			}
		}else{
			//System.err.println(this.toString() + ": does not reference a leaf!");
		}
	}
	public void setPosition(Vector2f position){
		this.position = position;
		checkPosition();
	}
	@Override
	public BB getBB() {
		return new BB(getX(), getY(),1,1);
	}
	

	@Override
	public void setLeaf(Node leaf){
		if (leaf != null){
		leaf.m(this, "set new parent leaf.");
		}else{
			System.err.println(this.toString() + ": has no parent leaf.");
		}
		this.leaf = leaf;
	}


}
