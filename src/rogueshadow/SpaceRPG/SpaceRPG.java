package rogueshadow.SpaceRPG;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.text.html.parser.Entity;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.ImageData;

import rogueshadow.SpaceRPG.entities.Planet;
import rogueshadow.SpaceRPG.entities.Rock;
import rogueshadow.SpaceRPG.entities.Ship;
import rogueshadow.SpaceRPG.entities.Star;
import rogueshadow.particles.PHelper;
import rogueshadow.particles.ParticleEngine;
import rogueshadow.utility.KeyBind;

/**
 * @author Adam
 *
 */
public class SpaceRPG extends BasicGame{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static int WORLD_WIDTH = 1000000;
	public static int WORLD_HEIGHT = 1000000;
	
	BufferedImage map;
	
	Input input;
	public Camera cam;
	public Ship ship;

	EntityManager manager;
	PHelper engine;
	KeyBind keyBinds;

	Sound explosion;
	Sound shot;

	public SpaceRPG(String title) {
		super(title);

	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {

		AppGameContainer container = new AppGameContainer(new SpaceRPG("SpaceRPG Prototype!"), WIDTH,HEIGHT,false);
		container.setTargetFrameRate(60);
		container.setVSync(true);
		//container.setFullscreen(true);
		container.start();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		cam.translateIn(g);
		
		manager.render(g);
		engine.render(g);
		
		// world border :D
		g.setColor(Color.red);
		g.drawRect(0, 0, WORLD_WIDTH-1, WORLD_HEIGHT-1);
		g.setColor(Color.magenta);
		g.drawRect(16, 16, WORLD_WIDTH-32, WORLD_HEIGHT-32);
		
		cam.translateOut(g);

		engine.renderDust(g);

		g.setColor(Color.white);

		if (manager.isPaused()){
			g.pushTransform();
			g.setColor(Color.yellow);
			g.scale(2, 2);
			g.drawString("(p)PAUSED", 70, 70);
			g.popTransform();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
		// Initialize key binding class
		keyBinds = new KeyBind();
		// set key binds, .bind(name, abbreviation, int key code) or .bind(name, int key code)
		keyBinds.bind("Thrust",Input.KEY_W);
		keyBinds.bind("Left",Input.KEY_A);
		keyBinds.bind("Right",Input.KEY_D);
		keyBinds.bind("Shoot",Input.KEY_SPACE);
		keyBinds.bind("Pause", Input.KEY_P);
		keyBinds.bind("SkipRound", "Skip", Input.KEY_L);
		keyBinds.bind("Exit", Input.KEY_ESCAPE);
		keyBinds.bind("Cheat", Input.KEY_X);
		keyBinds.bind("Brake", Input.KEY_S);
		
		//load sound
		explosion = new Sound("res/blast2.wav");
		shot = new Sound("res/shot2.wav");
		


		input = container.getInput();
		engine = new PHelper();
		manager = new EntityManager(container, this);
		
		
		
		ship = new Ship(new Vector2f(0,0));
		ship.setThrusterStrength(10);
		ship.setEngineStrength(10);
		
		//load game entities from map file
		cam = new Camera();
		loadEntities("map", manager, ship);
		cam.WORLD_HEIGHT = WORLD_HEIGHT;
		cam.WORLD_WIDTH = WORLD_WIDTH;
		manager.add(ship);
		
		
		cam.setFollowing(ship.getPosition());
		
		
		//TODO May need some kind of configuration loader, .ini file perhaps. Saving configs.
		//TODO Some kind of level file format, to handle loading various entities, NPCs, etc.
		
	}


	private void loadEntities(String world, EntityManager manager, Ship ship) {
		//TODO make a level class, and level loading. figure out how to include the metadata, such as planet names,
		//quest info, and the like. :D
		try {
			map = ImageIO.read(SpaceRPG.class.getResource("/res/" + world + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int scale = 64;
		int w = map.getWidth();
		int h = map.getHeight();
		WORLD_WIDTH = w*scale;
		WORLD_HEIGHT = h*scale;
		int x = 0;
		int y = 0;
		int[] pixels = new int[w*h];
		map.getRGB(0, 0, w, h, pixels, 0, w);
		int color = 0;
		for (x = 0; x < w; x++){
			for (y = 0; y < h; y++){
				color = pixels[x + y * w] & 0xffffff;
				
				if (color == 0xffff00)manager.add(new Star(new Vector2f(x*scale,y*scale)) );
				if (color == 0xff00ff)ship.setPosition(new Vector2f(x*scale,y*scale));
				if (color == 0x825d07)manager.add(new Rock(new Vector2f(x*scale,y*scale), new Vector2f(0,0), 2));
				if (color == 0x008e00)manager.add(new Planet(new Vector2f(x*scale,y*scale)));
				if (color == 0xff0000)manager.add(new Ship(new Vector2f(x*scale,y*scale)));
				
			}
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (isKP("Pause"))manager.togglePaused();
		manager.update(delta);
		engine.update(delta);
		engine.updateDust(delta, ship.getVelocity());
		ship.resetControls();
		if (isKD("Thrust")){
			ship.setEngineActive(true);
			
		}
		if (isKD("Left"))ship.setLeftThrusterActive(true);
		if (isKD("Right"))ship.setRightThrusterActive(true);
		if (isKD("Brake"))ship.setSpaceBrake(true);
		if (isKD("Exit"))container.exit();
		
	}

	public PHelper getEngine() {
		return engine;
	}
	
	//TODO put sounds in thier own class
	public void playBlast(){
		explosion.play(0.3f + (float)Math.random()*0.7f,1f);
	}
	public void playShoot(){
		shot.play();
	}
	
	
	/**
	 * @param key
	 * @return
	 */
	public boolean isKD(String key){
		return input.isKeyDown(keyBinds.getKey(key));
	}
	/**
	 * @param key
	 * @return
	 */
	public boolean isKP(String key){
		return input.isKeyPressed(keyBinds.getKey(key));
	}

}
