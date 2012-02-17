package rogueshadow.SpaceRPG;

import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.entities.PlayerShip;
import rogueshadow.particles.PHelper;
import rogueshadow.utility.KeyBind;

/**
 * @author Adam
 *
 */
public class Engine implements Game {
	public Engine() {
		super();
	}

	public String title;
	public static Integer WIDTH;
	public static Integer HEIGHT;
	public static Integer WORLD_WIDTH;
	public static Integer WORLD_HEIGHT;
	public static String name;
	
	public static Sounds snd;
	
	Input input;
	

	public static World world;
	public static PHelper particles;
	
	public static Minimap map;
	public static Vector2f camPos;
	public static PlayerShip playerShip;
	
	KeyBind keyBinds;

	public void init(GameContainer container) throws SlickException {
		
		name = "SpaceRPG Prototype";
		
		WIDTH = container.getWidth();
		HEIGHT = container.getHeight();
		
		world = new World();
		
		map = new Minimap(5, 500,200,200);
		camPos = new Vector2f(0,0);
		
		
		// Initialize key binding class
		keyBinds = new KeyBind();
		// set key binds, .bind(full_name, friendly name, int key code) or .bind(name, int key code)
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
		particles = new PHelper();
		LevelLoader.loadLevel(world,"map");
		
		particles.initDust(getPlayer());
		map.setTracking(getPlayer().getPosition());
		
		getPlayer().setEngineStrength(100);
		getPlayer().setThrusterStrength(10);
		
		//TODO May need some kind of configuration loader, .ini file perhaps. Saving configs.
		//TODO Some kind of level file format, to handle loading various entities, NPCs, etc.

		
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {

		getWorld().getCamera().translateIn(g);
		getWorld().render(g);
		particles.render(g);
		
		// world border
		int bwidth = 128;
		g.setColor(Color.red);
		g.drawRect(0, 0, WORLD_WIDTH-1, WORLD_HEIGHT-1);
		g.setColor(Color.magenta);
		g.drawRect(bwidth/2, bwidth/2, WORLD_WIDTH-bwidth, WORLD_HEIGHT-bwidth);
		
		getWorld().getCamera().translateOut(g);
		
		particles.renderDust(g);
		map.render(g);
		
		g.setColor(Color.white);
		
		g.drawString("Ship x/y : " + coor(getPlayer().getPosition().getX()) + " / " + coor(getPlayer().getY()), 100, 120);
		g.drawString("Entities: " + world.objects.size(), 100, 140);

	}

	public static PlayerShip getPlayer() {
		return playerShip;
	}

	public static void setPlayer(PlayerShip ship) {
		playerShip = ship;
	}

	public static Integer coor(float x){
		return (Math.round((x/256f)));
	}



	public void update(GameContainer container, int delta)
			throws SlickException {
		getWorld().update(delta);
		particles.update(delta);
		particles.updateDust(delta, getPlayer());
		getPlayer().resetControls();
		if (isKD("Thrust")){
			getPlayer().setEngineActive(true);

		}
		if (isKD("Left"))getPlayer().setLeftThrusterActive(true);
		if (isKD("Right"))getPlayer().setRightThrusterActive(true);
		if (isKD("Brake"))getPlayer().setSpaceBrake(true);
		if (isKD("Shoot"))getPlayer().ShootPrimaryWeapon();
		
		if (isKD("Exit")){
			Log.debug("Engine", "User exited, (pressed ESC)");
			container.exit();
		}
		
	}

	public static PHelper getEngine() {
		return particles;
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
	
	public static World getWorld(){
		return world;
	}

	@Override
	public boolean closeRequested() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getTitle() {
		return name;
	}

}
