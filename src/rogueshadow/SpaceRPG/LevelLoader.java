package rogueshadow.SpaceRPG;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import rogueshadow.SpaceRPG.entities.Planet;
import rogueshadow.SpaceRPG.entities.PlayerShip;
import rogueshadow.SpaceRPG.entities.Rock;
import rogueshadow.SpaceRPG.entities.Ship;
import rogueshadow.SpaceRPG.entities.Star;

public class LevelLoader {
	

		//figure out how to include the metadata, such as planet names,
		//quest info, and the like. :D
	public static void loadLevel(World world, String filename) {
		try {
			BufferedImage map = ImageIO.read(Engine.class.getResource("/res/" + filename + ".png"));
			int w = map.getWidth();
			int h = map.getHeight();
			
			int scale = 64;
			
			Engine.WORLD_WIDTH = w*scale;
			Engine.WORLD_HEIGHT = h*scale;
			
			world.init();// initialize anything that needs a world size before loading objects into world.
			
			int x = 0;
			int y = 0;
			int[] counts = {0,0,0,0,0};
			int[] pixels = new int[w*h];
			map.getRGB(0, 0, w, h, pixels, 0, w);
			int color = 0;
			for (x = 0; x < w; x++){
				for (y = 0; y < h; y++){
					color = pixels[x + y * w] & 0xffffff;
					
					if (color == 0xffff00){
						world.add(new Star(x*scale,y*scale, 200));
						counts[0]++;
					}else
					if (color == 0xff00ff){
						Engine.setPlayer(new PlayerShip(x*scale,y*scale));
						world.getCamera().setFollowing(Engine.getPlayer().getPosition());
						world.add(Engine.getPlayer());
					}else
					if (color == 0x825d07){
						world.add(new Rock(x*scale,y*scale, 2));
						counts[1]++;
					}else
					if (color == 0x008e00){
						world.add(new Planet(x*scale,y*scale));
						counts[2]++;
					}else
					if (color == 0xff0000){
						world.add(new Ship(x*scale,y*scale));
						counts[3]++;
					}
					
				}
			}
			Log.debug("LevelLoader", "Loaded level objects from image.");
			Log.info("LevelLoader", "Stars: " + counts[0] + " Rocks: " + counts[1] + " Planets: "+ counts[2] + " Ships: "+ counts[3]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.error("LevelLoader", "Could not load the file: " + filename + ".png");
			e.printStackTrace();
		}
	}

}
