package rogueshadow.SpaceRPG;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.entities.Planet;
import rogueshadow.SpaceRPG.entities.PlayerShip;
import rogueshadow.SpaceRPG.entities.Rock;
import rogueshadow.SpaceRPG.entities.Ship;
import rogueshadow.SpaceRPG.entities.Star;
import rogueshadow.particles.PHelper;
import rogueshadow.utility.KeyBind;

/**
 * @author Adam
 *
 */
public class SpaceRPG extends BasicGame{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static Integer WORLD_WIDTH = 1000000;
	public static Integer WORLD_HEIGHT = 1000000;
	public static String name = "SpaceRPG Prototype";
	
	public static AppGameContainer con;
	
	public static Sounds snd;
	
	Input input;
	

	Level lvl = new Level();
	public static PHelper engine;
	
	public Vector2f camPos = new Vector2f(0,0);
	
	KeyBind keyBinds;

	public SpaceRPG(String title) {
		super(title);

	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {

		AppGameContainer container = new AppGameContainer(new SpaceRPG(name), WIDTH,HEIGHT,false);
		//container.setTargetFrameRate(60);
		//container.setVSync(true);
		//container.setFullscreen(true);
		con = container;
		container.start();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		lvl.getCamera().translateIn(g);
		lvl.render(g);
		engine.render(g);
		
		// world border
		int bwidth = 128;
		g.setColor(Color.red);
		g.drawRect(0, 0, WORLD_WIDTH-1, WORLD_HEIGHT-1);
		g.setColor(Color.magenta);
		g.drawRect(bwidth/2, bwidth/2, WORLD_WIDTH-bwidth, WORLD_HEIGHT-bwidth);
		
		lvl.getCamera().translateOut(g);

		engine.renderDust(g);

		g.setColor(Color.white);
		
		g.drawString("starDustCount: " + engine.getStarCount().toString(), 100, 100);
		g.drawString("Ship x/y : " + coor(lvl.getPlayer().getPosition().getX()) + " / " + coor(lvl.getPlayer().getY()), 100, 120);
		g.drawString("Entities: " + lvl.entities.size(), 100, 140);
		
		if (isPaused()){
			g.pushTransform();
			g.setColor(Color.yellow);
			g.scale(2, 2);
			g.drawString("(p)PAUSED", 70, 70);
			g.popTransform();
		}
	}

	public String coor(float x){
		return Integer.toString(Math.round((x/256f)));
	}

	private boolean isPaused() {
		// TODO Auto-generated method stub
		return false;
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

		
		snd = new Sounds();
		
		input = container.getInput();
		engine = new PHelper();
		lvl.loadLevel("map");
		
		
		lvl.getPlayer().setThrusterStrength(1);
		lvl.getPlayer().setEngineStrength(10);
		
		engine.initDust(lvl.getPlayer());
		
		//lvl.getCamera().setFollowing(camPos);
		//TODO May need some kind of configuration loader, .ini file perhaps. Saving configs.
		//TODO Some kind of level file format, to handle loading various entities, NPCs, etc.
		
	}


	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		lvl.update(delta);
		engine.update(delta);
		engine.updateDust(delta, lvl.getPlayer());
		lvl.getPlayer().resetControls();
		if (isKD("Thrust")){
			lvl.getPlayer().setEngineActive(true);

		}
		if (isKD("Left"))lvl.getPlayer().setLeftThrusterActive(true);
		if (isKD("Right"))lvl.getPlayer().setRightThrusterActive(true);
		if (isKD("Brake"))lvl.getPlayer().setSpaceBrake(true);
		if (isKP("Shoot"))lvl.getPlayer().ShootPrimaryWeapon();
		
		if (isKD("Exit"))container.exit();
		
	}

	public static PHelper getEngine() {
		return engine;
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

	public GameContainer getContainer() {
		return con;
	}

}
