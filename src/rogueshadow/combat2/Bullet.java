package rogueshadow.combat2;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Bullet extends AbstractEntity implements Entity {
	public final int DELAY = 0;
	public final int LIFE = 1;
	public final int PIERCE = 2;
	public final int HOMING = 3;
	public final int SIZE = 4;
	public final int MULTI = 5;
	
	protected int life = 1500;
	
	protected int pierce;
	protected float homing;



	public Bullet(Vector2f position, Vector2f velocity, float currentValue[]) {
		super(position, velocity);
		setLife((int)currentValue[LIFE]);
		setPierce((int)currentValue[PIERCE]);
		setHoming(currentValue[HOMING]);
		setSize(currentValue[SIZE]);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getPierce() {
		return pierce;
	}

	public void setPierce(int pierce) {
		this.pierce = pierce;
	}

	public float getHoming() {
		return homing;
	}

	public void setHoming(float homing) {
		this.homing = homing;
	}

	public float getRotate() {
		return rotate;
	}

	public void setRotate(float rotate) {
		this.rotate = rotate;
	}

	protected float rotate = 0;
	Entity closest = null;
	float closest_dist = 100000;
	
	@Override
	public void update(EntityManager manager, int delta) {
		if (isDestroyed())return;
		life -= delta;
		rotate += 10;
		if (life <= 0){
			manager.getGame().getEngine().explosion(getX(), getY(), 1);
			manager.remove(this);
		}
		if (getHoming() > 0 && closest != null){
			Vector2f targetVector = new Vector2f(getCenterX()-closest.getCenterX(),getCenterY()-closest.getCenterY());
			targetVector.negateLocal();
			float angleSpeed = getHoming();
			float angle1 = (float)targetVector.getTheta();
			float angle2 = (float)getVelocity().getTheta();
			float d = angle1 - angle2;
			if (angleSpeed > Math.abs(d))angleSpeed = Math.abs(d);
			if (angle1 > angle2){
				getVelocity().setTheta(getVelocity().getTheta()+angleSpeed);
			}else{
				getVelocity().setTheta(getVelocity().getTheta()-angleSpeed);	
			}
		}
		
		
		closest_dist = 1000000;
		closest = null;
		
		super.update(delta);
		
	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.translate(getX(), getY());
		g.rotate(0, 0, rotate);
		if (getHoming() > 0){
			g.setColor(Color.red);
		}else{
			g.setColor(Color.green);
		}
		g.drawRect(-getSize()/2, -getSize()/2, getSize(), getSize());
		g.popTransform();
	}

	@Override
	public void collided(EntityManager manager, Entity other) {
		if (isDestroyed())return;
		if (other instanceof Rock){
			if (getPierce() >= 1){
				setPierce(getPierce() - 1);
			}else{
				setDestroyed(true);
				manager.remove(this);
			}
			manager.getGame().score += 1;
			if (manager.getGame().score > manager.getGame().highscore){
				manager.getGame().highscore = manager.getGame().score;
			}
			//manager.getGame().explosion.play(0.4f,0.7f);
			manager.getGame().playBlast();
			manager.getGame().getEngine().explosion(getCenterX(), getCenterY(), 1);
		}
		
	}


}
