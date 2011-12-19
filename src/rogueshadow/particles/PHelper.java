package rogueshadow.particles;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.SpaceRPG;
import rogueshadow.SpaceRPG.entities.Ship;

public class PHelper extends ParticleEngine {
	int updateDistance = 200;
	float depth = 3f;
	Vector2f lastPos = new Vector2f(0,0);
	ArrayList<Particle> starDust = new ArrayList<Particle>();
	ArrayList<Particle> removeList = new ArrayList<Particle>();
	
	
	public void initDust(Ship s){
		float x,y;
		for (int i = 0; i < 50; i++){
			x = (float)Math.random()*SpaceRPG.WIDTH;
			y = (float)Math.random()*SpaceRPG.HEIGHT;
			BoxParticle p = new BoxParticle(new Vector2f(x,y), new Vector2f(0,0),Integer.MAX_VALUE,(float)Math.random()*4);
			p.setRotation((float)Math.random()*360);
			p.scale = (float)(Math.random()*depth);
			starDust.add(p);
		}
	}
	
	public Integer getStarCount(){
		return starDust.size();
	}
	
	public void renderDust(Graphics g){
		for (Particle p: starDust)p.render(g);
	}
	public void updateDust(int delta, Ship s){
		float dist = s.getPosition().distanceSquared(lastPos);
		if (dist < updateDistance){
			
		}else{
			lastPos = s.getPosition().copy();
			float x = 0;
			float y = 0;
			
			if (s.getVelocity().getX() > 0)x = SpaceRPG.WIDTH;
			if (s.getVelocity().getY() > 0)y = SpaceRPG.HEIGHT;
			
			if (Math.random() < 0.5d)x = (float)Math.random()*SpaceRPG.WIDTH;
			else y = (float)Math.random()*SpaceRPG.HEIGHT;
			
			BoxParticle p = new BoxParticle(new Vector2f(x,y), new Vector2f(0,0),Integer.MAX_VALUE,(float)Math.random()*4);
			p.setRotation((float)Math.random()*360);
			p.scale = (float)(Math.random()*depth);
			starDust.add(p);
		}
		
		for (Particle p: starDust){
			p.setVelocity(new Vector2f(s.getVelocity().copy().negate().scale((float)((BoxParticle)p).scale)));
			p.update(new ParticleEngine(), delta);
			if (((BoxParticle)p).getPosition().x < 0 || ((BoxParticle)p).getPosition().x > SpaceRPG.WIDTH ||
			((BoxParticle)p).getPosition().y < 0 || (((BoxParticle)p).getPosition().y > SpaceRPG.HEIGHT))removeList.add(p);
		}
		
		starDust.removeAll(removeList);
		removeList.clear();
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
