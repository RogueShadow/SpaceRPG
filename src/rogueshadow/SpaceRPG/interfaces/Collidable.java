package rogueshadow.SpaceRPG.interfaces;

import org.newdawn.slick.geom.Circle;

public interface Collidable {
	public void collided(Collidable c);
	public boolean intersects(Collidable c);
	public Circle getCollisionShape();
}
