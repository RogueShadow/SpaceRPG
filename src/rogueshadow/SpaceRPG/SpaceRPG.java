package rogueshadow.SpaceRPG;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.particles.ParticleEngine;

public class SpaceRPG extends BasicGame{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int WORLD_WIDTH = 100000;
	public static final int WORLD_HEIGHT = 100000;
	
	Input input;
	public Camera cam = new Camera();
	public Ship ship;

	EntityManager manager;
	ParticleEngine engine;
	KeyBind keyBinds = new KeyBind();

	Sound explosion;
	Sound shot;

	DecimalFormat f = new DecimalFormat("00000.00");

	public SpaceRPG(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		AppGameContainer container = new AppGameContainer(new SpaceRPG("SpaceRPG Prototype!"), 800,600,false);
		container.setTargetFrameRate(60);
		container.setVSync(true);
		container.start();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		cam.translateIn(g);
		
		manager.render(g);
		engine.render(g);
		
		g.setColor(Color.red);
		g.drawRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
		g.setColor(Color.magenta);
		g.drawRect(16, 16, WORLD_WIDTH-32, WORLD_HEIGHT-32);
		
		cam.translateOut(g);
		

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
		
		keyBinds.bind("Thrust",Input.KEY_W);
		keyBinds.bind("Left",Input.KEY_A);
		keyBinds.bind("Right",Input.KEY_D);
		keyBinds.bind("Shoot",Input.KEY_SPACE);
		keyBinds.bind("Pause", Input.KEY_P);
		keyBinds.bind("SkipRound", "Skip", Input.KEY_L);
		keyBinds.bind("Exit", Input.KEY_ESCAPE);
		keyBinds.bind("Cheat", Input.KEY_X);
		keyBinds.bind("Brake", Input.KEY_S);
		
		explosion = new Sound("res/blast2.wav");
		shot = new Sound("res/shot2.wav");
		
		input = container.getInput();
		
		engine = new ParticleEngine();
		manager = new EntityManager(container, this);
		
		ship = new Ship(new Vector2f(170,150));

		cam.setFollowing(ship.getPosition());
		manager.add(ship);

	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (isKP("Pause"))manager.togglePaused();
		manager.update(delta);
		engine.update(delta);

		ship.resetControls();
		if (isKD("Thrust"))ship.setEngineActive(true);
		if (isKD("Left"))ship.setLeftThrusterActive(true);
		if (isKD("Right"))ship.setRightThrusterActive(true);
		if (isKD("Brake"))ship.setSpaceBrake(true);
		if (isKD("Exit"))container.exit();
		
	}

	public ParticleEngine getEngine() {
		return engine;
	}
	
	public void playBlast(){
		explosion.play(0.3f + (float)Math.random()*0.7f,1f);
	}
	public void playShoot(){
		shot.play();
	}
	
	public boolean isKD(String key){
		return input.isKeyDown(keyBinds.getKey(key));
	}
	public boolean isKP(String key){
		return input.isKeyPressed(keyBinds.getKey(key));
	}

}
