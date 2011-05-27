package rogueshadow.combat2;

import org.newdawn.slick.Color;

import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.combat2.AbstractEntity;
import rogueshadow.combat2.Entity;


public class Ship extends AbstractEntity implements Entity {
	public static final int DELAY = 0;
	public static final int LIFE = 1;
	public static final int PIERCE = 2;
	public static final int HOMING = 3;
	public static final int SIZE = 4;
	public static final int MULTI = 5;
	
	static int startShipLife = 5;
	int shipLife = startShipLife;
	int invulnerable = 2000;
	float angle = 0;
	int fireCounter = 0;
	static String names[] = {"6)Delay","5)Life","4)Pierce","3)Homing","2)Size","1)Multi"};
	String cursurON = " <ON>";
	String cursurOFF = " <OFF>";
	boolean selected[] = {true,true,true,true,true,true};
	static float stepValue[] = {50f, 500f, 1f, 0.25f, 8f, 1f};
	static float minValue[] = {0f,1500f,0f,0f,8f,0f};
	static float maxValue[] = {500f,6000f,5f,8f,36f,5f};
	float powerTimerCurrent[] = {0,0,0,0,0,0};
	static float powerTimerTickRate[] = {20,20,20,20,20,20};
	static float startValue[] = {0f,1500f,0f,0f,8f,0f};
	float currentValue[] = new float[startValue.length]; 
	
	
	
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Ship(Vector2f position) {
		super(position, new Vector2f(0,0));
		setSize(15);
		System.arraycopy(startValue, 0, currentValue, 0, startValue.length);
	}

	@Override
	public void update(EntityManager manager, int delta) {
		Input in = manager.getContainer().getInput();
		fireCounter += delta;
		if (invulnerable > 0)invulnerable -= delta;
		
		if (in.isKeyPressed(Input.KEY_1))selected[5] = (selected[5]) ? false:true;
		if (in.isKeyPressed(Input.KEY_2))selected[4] = (selected[4]) ? false:true;
		if (in.isKeyPressed(Input.KEY_3))selected[3] = (selected[3]) ? false:true;
		if (in.isKeyPressed(Input.KEY_4))selected[2] = (selected[2]) ? false:true;
		if (in.isKeyPressed(Input.KEY_5))selected[1] = (selected[1]) ? false:true;
		if (in.isKeyPressed(Input.KEY_6))selected[0] = (selected[0]) ? false:true;
		
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
		if (in.isKeyDown(Input.KEY_SPACE)){
			if ((fireCounter > (maxValue[DELAY] - currentValue[DELAY]) && selected[DELAY]) ||
					((fireCounter > maxValue[DELAY] - minValue[DELAY]))){
				fireWeapon(manager);
				fireCounter = 0;
				for (int i = 0; i < names.length; i ++){
					if (!selected[i])continue;
					powerTimerCurrent[i] += 1;
					if (powerTimerCurrent[i] > powerTimerTickRate[i]){
						subtractPower(i);
					}
				}
			}
		}
		getVelocity().scale(0.97f);
		
		for (int i = 0; i < manager.game.bars.size(); i ++){
			manager.game.bars.get(i).setCurrentValue(currentValue[i]);
			if (selected[i])manager.getGame().bars.get(i).setLabel(names[i] + cursurON);else
				manager.getGame().bars.get(i).setLabel(names[i] + cursurOFF);
		}
		for (int i = 0; i < manager.game.tickers.size(); i ++){
			manager.game.tickers.get(i).setCurrentValue(powerTimerTickRate[i]-powerTimerCurrent[i]);
		}
		manager.game.lifebar.setCurrentValue(shipLife);
		
		super.update(delta);
		
		if (shipLife <= 0){
			manager.getGame().score = 0;
			manager.getGame().round = 0;
			manager.resetGame();
			manager.setPaused(true);
		}
		
	}

	private void fireWeapon(EntityManager manager) {
		if (isInvulnerable())return;
		Vector2f pos = getPosition().copy();
		pos.add(new Vector2f(getAngle()).scale(30));
		
		Bullet b;
		if (selected[MULTI]){
			float angleStep = 15;
			float startAngle = getAngle() - (int)((currentValue[MULTI] * angleStep)/2f);
		
			for (int i = 0; i < currentValue[MULTI] + 1; i++){
				b =  new Bullet(pos.copy(), new Vector2f(startAngle).scale(300),getValues());
				manager.add(b);
				startAngle += angleStep;
			}
		}else{
			b = new Bullet(pos.copy(), new Vector2f(getAngle()).scale(300),getValues());
			manager.add(b);
		}
		manager.getGame().playShoot();
	}

	@Override
	public void render(Graphics g) {
		if (isInvulnerable())if ((int)(invulnerable/150f) % 2 == 0)return;
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
		if (isInvulnerable())return;
		if (other instanceof Rock){
			shipLife--;	
			if (getVelocity().length() > 100)getVelocity().negateLocal();
			manager.getGame().playBlast();
			manager.getGame().getEngine().explosion(getCenterX(), getCenterY(), 2);
			invulnerable = 2000;
			for (int i = 0; i < names.length; i++ ){
				subtractPower(i);
			}
		}
		
	}
	public boolean isInvulnerable(){
		return (invulnerable > 0);
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
	public float[] getValues(){
		float values[] = new float[names.length];
		System.arraycopy(startValue, 0, values, 0, names.length);
		for (int i = 0; i < names.length; i++){
			if (selected[i])values[i] = currentValue[i];
		}
		return values;
	}
}
