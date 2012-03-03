package rogueshadow.SpaceRPG;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Art   {
	
	public static final String imgDir = "/res/";
	
	public static Image ship;
	public static Image star;
	public static Image bullet;
	public static Image rock;
	public static Image debri;

	public static void loadThings() throws SlickException {
		ship = load("spaceship.png");
		star = load("star1.png");
		bullet = load("bullet.png");
		rock = load("rockTex.png");
		debri = load("debri.png");
	}
	
	public static Image load(String file) throws SlickException{
		return new Image(Engine.class.getResourceAsStream(imgDir + file), file, false);
	}

}
