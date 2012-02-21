package rogueshadow.SpaceRPG.entities;

import java.io.Serializable;


public class PlayerShip extends Ship implements Serializable {

	private static final long serialVersionUID = 5397137534101740438L;
	public PlayerShip(float x, float y) {
		super(x,y);
	}
	
	@Override
	public boolean isActive(){
		return true;
	}
	
	@Override
	public boolean isAlwaysUpdated(){
		return true;
	}

}
