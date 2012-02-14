package rogueshadow.SpaceRPG;

public class WorldObject {
	public World world;
	public float x;
	public float y;
	
	public WorldObject(float x, float y) {
		super();
		this.world = null;
		this.x = x;
		this.y = y;
	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
