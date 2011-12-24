package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Level;

public class Planet extends AbstractEntity implements Entity {
	String name;

	public Planet(Level level, Vector2f position) {
		super(level, position, new Vector2f(0,0));
		setSize(50);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.blue);
		g.drawOval(getCenterX(), getCenterY(), getSize(), getSize());
	}

	@Override
	public void collided(Entity other) {
		// TODO Auto-generated method stub
		
	}

}
