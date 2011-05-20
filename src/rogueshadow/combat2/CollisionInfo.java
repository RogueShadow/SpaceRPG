package rogueshadow.combat2;


public class CollisionInfo {
	public boolean collided;
	public boolean isCollided() {
		return collided;
	}
	public float getDistance() {
		return distance;
	}
	public Entity getOther() {
		return other;
	}
	public Entity getEntity() {
		return entity;
	}
	public float distance;
	public Entity other;
	public CollisionInfo(Entity entity, Entity other, boolean collided, float distance) {
		super();
		this.collided = collided;
		this.distance = distance;
		this.other = other;
		this.entity = entity;
	}
	public CollisionInfo() {
		super();
		this.collided = false;
		this.distance = 0;
		this.other = null;
		this.entity = null;
	}
	public Entity entity;
}
