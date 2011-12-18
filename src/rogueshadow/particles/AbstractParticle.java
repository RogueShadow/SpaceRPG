package rogueshadow.particles;

import org.newdawn.slick.geom.Vector2f;

public abstract class AbstractParticle implements Particle {
	public Vector2f position = new Vector2f(0,0);
	public Vector2f velocity = new Vector2f(0,0);
	public Vector2f gravity = new Vector2f(0,0);
	public float life = 0;
	public float maxLife = 1;
	public float rotation = 0;
	public float rotationSpeed = 0;
	public boolean immortal = false;
	
	public AbstractParticle(Vector2f position, Vector2f velocity, float life){
		this.position = position;
		this.velocity = velocity;
		this.maxLife = life;
	}
	
	public void update(ParticleEngine engine, int delta){
		if (!isImmortal())life += delta;
		if (life < maxLife){
			rotation += rotationSpeed;
			velocity.add(gravity.copy().scale(delta/1000f));
			position.add(velocity.copy().scale(delta/1000f));
		}else{
			engine.remove(this);
		}
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public Vector2f getGravity() {
		return gravity;
	}

	public void setGravity(Vector2f gravity) {
		this.gravity = gravity;
	}

	public float getLife() {
		return life;
	}

	public void setLife(float life) {
		this.life = life;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public boolean isImmortal() {
		return immortal;
	}

	public void setImmortal(boolean immortal) {
		this.immortal = immortal;
	}

}
