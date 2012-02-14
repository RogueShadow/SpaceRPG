package rogueshadow.SpaceRPG;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.entities.Planet;
import rogueshadow.SpaceRPG.entities.PlayerShip;
import rogueshadow.SpaceRPG.entities.Rock;
import rogueshadow.SpaceRPG.entities.Ship;
import rogueshadow.SpaceRPG.entities.Star;
import rogueshadow.SpaceRPG.entities.Entity;

public class LevelLoader {
	

		//figure out how to include the metadata, such as planet names,
		//quest info, and the like. :D
	public static void loadLevel(Level lvl, String world) {
		try {
			BufferedImage map = ImageIO.read(Engine.class.getResource("/res/" + world + ".png"));
			int w = map.getWidth();
			int h = map.getHeight();
			
			int scale = 64;
			
			Engine.WORLD_WIDTH = w*scale;
			Engine.WORLD_HEIGHT = h*scale;
			
			int x = 0;
			int y = 0;
			int[] pixels = new int[w*h];
			map.getRGB(0, 0, w, h, pixels, 0, w);
			int color = 0;
			for (x = 0; x < w; x++){
				for (y = 0; y < h; y++){
					color = pixels[x + y * w] & 0xffffff;
					
					if (color == 0xffff00){
						lvl.add((Entity)new Star(lvl, new Vector2f(x*scale,y*scale)) );
					}else
					if (color == 0xff00ff){
						PlayerShip s = new PlayerShip(lvl, new Vector2f(x*scale,y*scale));
						lvl.setPlayer(s);
						lvl.getCamera().setFollowing(lvl.getPlayer().getPosition());
						lvl.add(lvl.getPlayer());
					}else
					if (color == 0x825d07){
						lvl.add(new Rock(lvl,new Vector2f(x*scale,y*scale), new Vector2f(0,0), 2));
					}else
					if (color == 0x008e00){
						lvl.add(new Planet(lvl, new Vector2f(x*scale,y*scale)));
					}else
					if (color == 0xff0000){
						lvl.add(new Ship(lvl, new Vector2f(x*scale,y*scale)));
					}
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
