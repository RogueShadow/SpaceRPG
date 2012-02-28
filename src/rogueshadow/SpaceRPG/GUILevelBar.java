package rogueshadow.SpaceRPG;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class GUILevelBar {
	String label;
	float minValue;
	float maxValue;
	float currentValue;
	float x;
	float y;
	float width;
	float height = 10;
	public GUILevelBar(String label, float minValue, float maxValue,
			float currentValue, float x, float y, float width) {
		super();
		this.label = label;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.currentValue = currentValue;
		this.x = x;
		this.y = y;
		this.width = width;;
	}
	
	public void render(Graphics g){
		float pad = 1f;
		g.pushTransform();
		g.translate(x, y);
		g.setColor(Color.yellow);
		g.drawRect(-pad, -pad, getWidth()+pad, getHeight()+pad);
		g.setColor(Color.cyan);
		g.fillRect(0, 0, getBarLength(), getHeight());
		g.setColor(Color.white);
		g.drawString(label.toString(), width+8,-5);
		g.popTransform();
	}
	
	private float getHeight() {
		return height;
	}

	private float getWidth() {
		return width;
	}

	public float getBarLength(){
		float length = (currentValue/maxValue);
		return length*getWidth();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public float getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(float currentValue) {
		this.currentValue = currentValue;
	}

	public void setHeight(int i) {
		this.height = i;
	}


}
