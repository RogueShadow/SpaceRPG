package rogueshadow.combat2;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Powerup extends AbstractEntity implements Entity {
	public static final int MAX_POWERUPS = 15;
	public final int DELAY = 0;
	public final int LIFE = 1;
	public final int PIERCE = 2;
	public final int HOMING = 3;
	public final int SIZE = 4;
	public final int MULTI = 5;
	int type;
	int life = 30000;
	int rotation = 0;
	String names[] = {"Delay","Life","Pierce","Homing","Size","Multi"};
	
	public Powerup(Vector2f position, Vector2f velocity) {
		super(position, velocity);
		type = (int)(Math.random()*names.length);
		setSize(20);
	}

	@Override
	public void update(EntityManager manager, int delta) {
		if (isDestroyed())return;
		life -= delta;
		if (life <= 0)manager.remove(this);
		rotation -= 3;
		Vector2f vec = new Vector2f(manager.getGame().ship.getCenterX() - getCenterX(),manager.getGame().ship.getCenterY() - getCenterY());
		getVelocity().setTheta(vec.getTheta());
		super.update(delta);
	}

	@Override
	public void render(Graphics g) {
		if (!isVisible())return;
		g.pushTransform();
		g.translate(getX(), getY());
		g.setColor(Color.magenta);
		g.drawString(names[type],-30,-30);
		g.rotate(0, 0, rotation);
		g.fillRect(-10, -10, 20, 20);
		g.popTransform();
	}

	@Override
	public void collided(EntityManager manager, Entity other) {
		if (isDestroyed())return;
		if (other instanceof Ship){
			manager.remove(this);
		}
	}

}
