package rogueshadow.SpaceRPG;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class NpcShip extends AbstractEntity implements Entity {
	String name = "";

	public NpcShip(Vector2f position, Vector2f velocity) {
		super(position, velocity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(EntityManager manager, int delta) {
		// TODO Auto-generated method stub
		
		float dist = 0;
		dist = manager.getGame().ship.getPosition().distance(getPosition());

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void collided(EntityManager manager, Entity other) {
		// TODO Auto-generated method stub

	}

}
