package rogueshadow.combat2;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import rogueshadow.particles.ParticleEngine;

public class Combat2 extends BasicGame{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	EntityManager manager;
	ParticleEngine engine;
	ArrayList<GUILevelBar> bars = new ArrayList<GUILevelBar>();
	ArrayList<GUILevelBar> tickers = new ArrayList<GUILevelBar>();
	GUILevelBar lifebar;
	Sound explosion;
	Sound shot;
	int round = 0;
	int score = 0;
	int highscore = 0;

	public Combat2(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		AppGameContainer container = new AppGameContainer(new Combat2("Combat Version 2.0!"), 800,600,false);
		container.setTargetFrameRate(60);
		container.setVSync(true);
		container.start();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		manager.render(g);
		for (GUILevelBar b: bars)b.render(g);
		for (GUILevelBar b: tickers)b.render(g);
		lifebar.render(g);
		if (manager.isPaused()){
			g.pushTransform();
			g.setColor(Color.yellow);
			g.scale(4, 4);
			g.drawString("'p')PAUSED", 70, 70);
			g.popTransform();
		}
		engine.render(g);
		g.setColor(Color.white);
		g.drawString("Score: " + Integer.toString(score), 10, 50);
		g.drawString("High Score: " + Integer.toString(highscore), 10, 30);
		g.drawString("Round: " + Integer.toString(round), 10, 70);
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		explosion = new Sound("res/blast2.wav");
		shot = new Sound("res/shot2.wav");
		for (int i = 0; i < Ship.names.length; i++){
			GUILevelBar b;
			b = new GUILevelBar(Ship.names[i],Ship.minValue[i],Ship.maxValue[i],Ship.startValue[i],10,HEIGHT-50-(i*20),100);
			bars.add(b);
		}
		for (int i = 0; i < Ship.names.length; i++){
			GUILevelBar b;
			b = new GUILevelBar("",0,Ship.powerTimerTickRate[i],0,10,HEIGHT-38-(i*20),100);
			b.setHeight(4);
			tickers.add(b);
		}
		lifebar = new GUILevelBar("Life",0, Ship.startShipLife,Ship.startShipLife ,300,10,150);
		engine = new ParticleEngine();
		manager = new EntityManager(container, this);
		clearedRound();
	}


	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_P)){
			if (manager.isPaused()){
				manager.setPaused(false);
			}else manager.setPaused(true);
		}
		manager.update(delta);
		engine.update(delta);
		if (container.getInput().isKeyPressed(Input.KEY_L)){
			round += 5;
			clearedRound();
		}
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))container.exit();
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

	public void clearedRound() {
		manager.generateRocks(++round);
		manager.setPaused(true);
		
	}
}
