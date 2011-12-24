package rogueshadow.SpaceRPG;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.entities.Entity;
import rogueshadow.SpaceRPG.entities.Planet;
import rogueshadow.SpaceRPG.entities.PlayerShip;
import rogueshadow.SpaceRPG.entities.Rock;
import rogueshadow.SpaceRPG.entities.Ship;
import rogueshadow.SpaceRPG.entities.Star;

public class Level {
	protected boolean paused = false;
	protected int worldWidth = 100000;
	protected int worldHeight = 100000;
	protected Camera cam = new Camera();
	protected PlayerShip playerShip;
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	protected ArrayList<Entity> removeList = new ArrayList<Entity>();
	protected ArrayList<Entity> addList = new ArrayList<Entity>();
	
	public String debug = "";
	
	public void setPlayer(PlayerShip ship){
		playerShip = ship;
	}
	
	public boolean isVisible(Entity entity){
		
		
		return true;
	}
	
	public Level(){
		super();
	}
	
	public int getWidth(){
		return worldWidth;
	}
	public int getHeight(){
		return worldHeight;
	}
	
	public void update(int delta){

		for (int i = 0; i < entities.size(); i++ ){
			Entity e = (Entity) entities.get(i);
			e.update(delta);
		}
		
		entities.removeAll(removeList);
		entities.addAll(addList);
		removeList.clear();
		addList.clear();
		
	}

	public void render(Graphics g){
		for (int i = 0; i < entities.size(); i++ ){
			Entity e = (Entity) entities.get(i);
			if (isVisible(e))e.render(g);
		}
		
	}
	
	public void remove(Entity other){
		other.setDestroyed(true);
		removeList.add(other);
	}
	public void add(Entity other){
		addList.add(other);
	}

	public Camera getCamera() {
		return cam;
	}

	public PlayerShip getPlayer() {
		return playerShip;
	}
	
	public void loadLevel(String world) {

		//figure out how to include the metadata, such as planet names,
		//quest info, and the like. :D
	
		try {
			BufferedImage map = ImageIO.read(SpaceRPG.class.getResource("/res/" + world + ".png"));
			int w = map.getWidth();
			int h = map.getHeight();
			
			getCamera().WORLD_WIDTH = w;
			getCamera().WORLD_HEIGHT = h;
			
			int scale = 64;
			worldWidth = w*scale;
			worldHeight = h*scale;
			int x = 0;
			int y = 0;
			int[] pixels = new int[w*h];
			map.getRGB(0, 0, w, h, pixels, 0, w);
			int color = 0;
			for (x = 0; x < w; x++){
				for (y = 0; y < h; y++){
					color = pixels[x + y * w] & 0xffffff;
					
					if (color == 0xffff00)add(new Star(this, new Vector2f(x*scale,y*scale)) );
					if (color == 0xff00ff){
						PlayerShip s = new PlayerShip(this, new Vector2f(x*scale,y*scale));
						setPlayer(s);
						getCamera().setFollowing(getPlayer().getPosition());
						add(getPlayer());
					}
					if (color == 0x825d07)add(new Rock(this,new Vector2f(x*scale,y*scale), new Vector2f(0,0), 2));
					if (color == 0x008e00)add(new Planet(this, new Vector2f(x*scale,y*scale)));
					if (color == 0xff0000)add(new Ship(this, new Vector2f(x*scale,y*scale)));
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	



	}
}
