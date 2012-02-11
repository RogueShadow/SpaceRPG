package rogueshadow.SpaceRPG.entities;

import java.io.Serializable;

import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Level;


public class PlayerShip extends Ship implements Serializable {

	private static final long serialVersionUID = 5397137534101740438L;
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
