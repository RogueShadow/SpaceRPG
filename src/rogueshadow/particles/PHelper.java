package rogueshadow.particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class PHelper extends ParticleEngine {
	int starDustMax = 500;
	int starDustCount = 0;
	
	public void addStarDust(Vector2f position, Vector2f velocity){
		if (velocity.lengthSquared() < 1)return;
		Vector2f pos = new Vector2f();
		pos = position.copy();
		pos.add(new Vector2f(Math.random()*360).scale(1500));
		add(new StarDust(pos));
		if (starDustCount < starDustMax){
			starDustCount++;
			return;
		}
		removeStarDust();
	}
	public void removeStarDust(){
		for (Particle p: getParticles()){
			if (p instanceof StarDust){
				remove(p);
				return;
			}
		}
	}

	public void addBox(float x, float y){
		BoxParticle b = new BoxParticle(new Vector2f(x,y),new Vector2f(Math.random()*360).scale(16), 1000, 4);
		b.setRotationSpeed(5f);
		add(b);
	}
	public void explosion(float x, float y, int size){
		for (int i = 0; i < 6*size; i ++){
			BoxParticle b = new BoxParticle(new Vector2f(x,y),new Vector2f(Math.random()*360).scale((float)Math.random()*66*size), 2000*(size/2f), (float)Math.random()*28*(size/2f));
			b.setRotationSpeed((float)-4 + (float)Math.random()*8);
			b.setColor(new Color(184,134,11));
			add(b);
		}
	}
}
