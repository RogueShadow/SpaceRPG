package rogueshadow.SpaceRPG.interfaces;

import rogueshadow.SpaceRPG.util.BB;

public interface Collidable {
	public void collided(Collidable c);
	public BB getBB();
}
