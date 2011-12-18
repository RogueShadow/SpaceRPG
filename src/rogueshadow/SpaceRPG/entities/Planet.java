package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.EntityManager;

public class Planet extends AbstractEntity implements Entity {
	String name;

	public Planet(Vector2f position) {
		super(position, new Vector2f(0,0));
		setSize(50);
	}

	@Override
	public void update(EntityManager manager, int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.blue);
		g.drawOval(getCenterX(), getCenterY(), getSize(), getSize());
	}

	@Override
	public void collided(EntityManager manager, Entity other) {
		// TODO Auto-generated method stub

	}

}
