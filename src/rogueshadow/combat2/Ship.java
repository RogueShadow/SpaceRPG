package rogueshadow.combat2;

import org.newdawn.slick.Color;

import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.combat2.AbstractEntity;
import rogueshadow.combat2.Entity;


public class Ship extends AbstractEntity implements Entity {
	public final int DELAY = 0;
	public final int LIFE = 1;
	public final int PIERCE = 2;
	public final int HOMING = 3;
	public final int SIZE = 4;
	public final int MULTI = 5;
	
	int shipLife = 500;
	float angle = 0;
	int fireCounter = 0;
	String names[] = {"Delay","Life","Pierce","Homing","Size","Multi"};
	float stepValue[] = {50f, 500f, 1f, 0.25f, 8f, 1f};
	float minValue[] = {0f,1500f,0f,0f,8f,0f};
	float maxValue[] = {500f,6000f,5f,8f,36f,5f};
	float powerTimerCurrent[] = {0,0,0,0,0,0};
	float powerTimerTickRate[] = {30000,30000,15000,15000,30000,15000};
	float currentValue[] = {0f,1500f,0f,0f,8f,0f};
	
	
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Ship(Vector2f position, Vector2f velocity) {
		super(position, velocity);
		setSize(15);

	}

	@Override
	public void update(EntityManager manager, int delta) {
		Input in = manager.getContainer().getInput();
		fireCounter += delta;
		for (int i = 0; i < names.length; i ++){
			powerTimerCurrent[i] += delta;
			if (powerTimerCurrent[i] > powerTimerTickRate[i]){
				subtractPower(i);
			}
		}
		if (in.isKeyDown(Input.KEY_W) || in.isKeyDown(Input.KEY_UP)){
			getVelocity().add(new Vector2f((getAngle())).scale(delta));
			Vector2f pos = getPosition().copy().add(new Vector2f(getAngle()).scale(-15));
			manager.getGame().getEngine().addBox(pos.getX(), pos.getY());
		}
		if (in.isKeyDown(Input.KEY_S) || in.isKeyDown(Input.KEY_DOWN)){
		//	getVelocity().add(new Vector2f((getAngle())).scale(-delta));
		}
		if (in.isKeyDown(Input.KEY_A) || in.isKeyDown(Input.KEY_LEFT)){
			angle -= 5f;
		}
		if (in.isKeyDown(Input.KEY_D) || in.isKeyDown(Input.KEY_RIGHT)){
			angle += 5f;
		}
		if (in.isKeyDown(Input.KEY_X)){
			Vector2f pos = getPosition().copy();
			pos.add(new Vector2f(getAngle()).scale(30));
			Bullet b =  new Bullet(pos.copy(), new Vector2f(getAngle()).scale(400),maxValue);
			manager.add(b);
		}
		if (in.isKeyDown(Input.KEY_SPACE) && fireCounter > (maxValue[DELAY] - currentValue[DELAY])){
			
			fireWeapon(manager);
			fireCounter = 0;
		}
		getVelocity().scale(0.97f);
		
		for (int i = 0; i < manager.game.bars.size(); i ++){
			manager.game.bars.get(i).setCurrentValue(currentValue[i]);
		}
		for (int i = 0; i < manager.game.tickers.size(); i ++){
			manager.game.tickers.get(i).setCurrentValue(powerTimerTickRate[i]-powerTimerCurrent[i]);
		}
		manager.game.lifebar.setCurrentValue(shipLife);
		
		super.update(delta);
	}

	private void fireWeapon(EntityManager manager) {
		Vector2f pos = getPosition().copy();
		pos.add(new Vector2f(getAngle()).scale(30));
		
		Bullet b;
		float angleStep = 15;
		float startAngle = getAngle() - (int)((currentValue[MULTI] * angleStep)/2f);
		
		for (int i = 0; i < currentValue[MULTI] + 1; i++){
			b =  new Bullet(pos.copy(), new Vector2f(startAngle).scale(300),currentValue);
			manager.add(b);
			startAngle += angleStep;
		}
		manager.getGame().shot.play();
	}

	@Override
	public void render(Graphics g) {

		g.pushTransform();
		g.translate(getX(), getY());
		g.rotate(0, 0, getAngle()+90);
		g.setColor(Color.yellow);
		g.drawLine(0, -20, -10, 10);
		g.drawLine(0, -20, 10, 10);
		g.drawLine(-10, 10, 10, 10);
		g.popTransform();
	}

	@Override
	public void collided(EntityManager manager, Entity other) {
		if (other instanceof Powerup){
			int type = ((Powerup) other).type;
			addPower(type);
		}
		if (other instanceof Rock){
			shipLife--;	
			Vector2f vec = new Vector2f(getCenterX()-other.getCenterX(),getCenterY()-other.getCenterY());
			getVelocity().add(vec);
		}
		
	}
	public void addPower(int type){
		currentValue[type] += stepValue[type];
		if (currentValue[type] > maxValue[type]){
			currentValue[type] = maxValue[type];
		}
		powerTimerCurrent[type] = 0;
	}
	public void subtractPower(int type){
		currentValue[type] -= stepValue[type];
		powerTimerCurrent[type] = 0;
		if (currentValue[type] < minValue[type]){
			currentValue[type] = minValue[type];
		}
	}
}
