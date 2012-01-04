package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Level;

public class Bullet extends AbstractEntity implements Entity {
	protected int rotation = 0;
	protected int life = 2000;
	protected Ship owner;

	public Bullet(Level level, Ship owner, Vector2f position, Vector2f velocity) {
		super(level, position, velocity);
		this.owner = owner;
		setSize(4);
		// TODO Auto-generated constructor stub
	}

	public void update(int delta){
		super.update(delta);
		rotation = (rotation + 3)%180;
		life -= delta;
		if (life < 0){
			getLevel().remove(this);
		}
		
	}
	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.rotate(getCenterX(), getCenterY(), rotation);
		g.setColor(Color.green);
		g.drawRect(getX(),getY(),getSize(),getSize());
		g.popTransform();
	}

	@Override
	public void collided(Entity other) {
		// TODO Auto-generated method stub

	}

}
