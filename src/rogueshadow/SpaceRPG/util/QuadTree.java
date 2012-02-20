package rogueshadow.SpaceRPG.util;

import org.newdawn.slick.geom.Rectangle;

public class QuadTree extends QuadTreeNode {
	public static final int maxElementsPerNode = 2;
	public static final int minWidth = 25;

	public QuadTree(Rectangle rect) {
		super(null, rect);
	}

	@Override
	public QuadTreeNode getParent(){
		return null;
	}


}
