package rogueshadow.SpaceRPG.WorldObjects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Engine;
import rogueshadow.SpaceRPG.Sounds;

public class Baddy extends Ship {
	public String name;
	int shotTimer = 2000;
	int count = 0;

	public Baddy(float x, float y) {
		super(x, y);
		name = "Evil Baddy";
	}
	
	@Override
	public boolean isAlwaysUpdated(){
		return true;
	}
	
	@Override
	public void update(int delta){
		if (count < shotTimer){
			count +=( delta + (5*Math.random()));
		}else{
			ShootPrimaryWeapon();
			count = 0;
		}
		Vector2f dist = new Vector2f((Engine.getPlayer().getX()-getX()),(Engine.getPlayer().getY()-getY()));
		
		setAngle((float)dist.getTheta());
		
		if (dist.lengthSquared() > 160*160){
			getVelocity().add(dist.normalise());

		}else{
			setVelocity(new Vector2f(0,0));
		}
		
		super.update(delta);
	}
	
	@Override
	public void render(Graphics g){
		g.drawString(name, getX()-5, getY()-10);
		super.render(g);
	}

	@Override
	public void collided(WorldObject other){
		if (other instanceof Bullet){
			getWorld().remove(this);
			getWorld().remove(other);
			Sounds.explosion.play();
			Engine.getEngine().explosion(getX(), getY(), 7);
		}
	}
}
