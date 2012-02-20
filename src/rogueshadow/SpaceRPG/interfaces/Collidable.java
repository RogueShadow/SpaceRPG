package rogueshadow.SpaceRPG.interfaces;

import org.newdawn.slick.geom.Rectangle;

public interface Collidable {
	public void collided(Collidable c);
	public Rectangle getRect();
	public boolean hasMoved();
	public void setMoved(boolean value);
}
