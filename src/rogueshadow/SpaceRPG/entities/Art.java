package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import rogueshadow.SpaceRPG.Engine;

public class Art   {
	
	public static Image ship;
	public static Image star;

	public static void loadThings() throws SlickException {
		ship = new Image(Engine.class.getResourceAsStream("/res/ship.png"), "Ship", false);
		star = new Image(Engine.class.getResourceAsStream("/res/star.png"), "Star", false);
	}

}
