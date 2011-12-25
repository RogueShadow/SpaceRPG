package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Level;


public class PlayerShip extends Ship {

	public PlayerShip(Level level, Vector2f position) {
		super(level, position);

	}
	
	public boolean isActive(){
		return true;
	}
	public boolean isPersistent(){
		return true;
	}

}
