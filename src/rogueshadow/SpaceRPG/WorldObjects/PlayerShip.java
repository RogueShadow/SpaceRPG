package rogueshadow.SpaceRPG.WorldObjects;

import java.io.Serializable;

import rogueshadow.SpaceRPG.Engine;


public class PlayerShip extends Ship implements Serializable {

	private static final long serialVersionUID = 5397137534101740438L;
	public PlayerShip(float x, float y) {
		super(x,y);
	}
	
	@Override
	public boolean isAlwaysUpdated(){
		return true;
	}
	
	@Override
	public void collided(WorldObject other){
		if (other instanceof Rock){
			getWorld().remove(other);
			Engine.getEngine().explosion(getX(), getY(), 5);
		}
	}

}
