package rogueshadow.SpaceRPG;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	public static Sound explosion;
	public static Sound shot;

	public Sounds() throws SlickException{
		explosion = new Sound("res/blast2.wav");
		shot = new Sound("res/shot2.wav");
	}
}
