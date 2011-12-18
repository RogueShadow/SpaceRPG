package rogueshadow.utility;
import java.util.ArrayList;


public class KeyBind {
	public  ArrayList<Bind> binds = new ArrayList<Bind>();
	
	public  void bind(Bind bind){
		binds.add(bind);
	}
	public  void bind(String name, String abbreviation, int value){
		binds.add(new Bind(name, abbreviation, value));
	}
	public  void bind(String name, int value){
		binds.add(new Bind(name, "", value));
	}
	public  Bind getBind(String name){
		for (Bind bind: binds){
			if (bind.getName().equals(name)){
				return bind;
			}
		}
		for (Bind bind: binds){
			if (bind.getAbbrev().equals(name)){
				return bind;
			}
		}
		return new Bind();
	}
	public int getKey(String name){
		Bind bind = getBind(name);
		return bind.getKey();
	}
}
