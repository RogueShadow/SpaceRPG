package rogueshadow.SpaceRPG;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	static Sound explosion;
	static Sound shot;

	public Sounds() throws SlickException{
		explosion = new Sound("res/blast2.wav");
		shot = new Sound("res/shot2.wav");
	}

	public static void Play(String snd){
		if (snd == "Shoot")shot.play();
		if (snd == "Explode")explosion.play();
	}
}
