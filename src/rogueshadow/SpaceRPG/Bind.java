package rogueshadow.SpaceRPG;

import org.newdawn.slick.Input;

public class Bind {
	String name;
	String abbreviation;
	int value;
	
	public Bind(){
	}
	
	public Bind(String name, String abbreviation, int value){
		this.name = name;
		this.abbreviation = abbreviation;
		this.value = value;
	}
	
	public Bind(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAbbrev(){
		if (abbreviation == null){
			return name;
		}else{
			return abbreviation;
		}
	}
	
	public int getKey(){
		return value;
	}
	
	public String getKeyName(){
		return Input.getKeyName(value);
	}
	
}
