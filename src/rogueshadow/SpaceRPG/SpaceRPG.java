package rogueshadow.SpaceRPG;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public class SpaceRPG {

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args){
		try {
			AppGameContainer app = new AppGameContainer(new Engine(), 1024, 768, false);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
