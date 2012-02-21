package rogueshadow.SpaceRPG.interfaces;

import java.util.ArrayList;

import rogueshadow.SpaceRPG.util.BB;
import rogueshadow.SpaceRPG.util.Point;

public interface ISpatialPartition<T> {
	public void remove(T obj);
	public ArrayList<T> get(BB box);
	public ArrayList<T> get(Point p);
	public void add(T obj);
	public int count();
}
