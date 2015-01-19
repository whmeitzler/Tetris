import java.awt.Point;
import java.util.ArrayList;

/*This class determines a single tetronimo from a pretetermined set*/
public class Tetronimo {
	public static int SQUARE  = 0, LINE = 1, S = 2, Z = 3, L = 4, J = 5, T = 6;
	public static String[] typeName = {"Square", "Line", "S", "Z", "L", "J", "T"};
	public static String[] orientationName = {"North", "East", "South", "West"};
    private static final Point[][] sq = {
        {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1)},
        {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1)},
        {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1)},
        {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1)},
};
    private static final Point[][] ln = {
        {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3)},
        {new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0)},
        {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3)},
        {new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0)},
};
    private static final Point[][] s = {
        {new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1)},
        {new Point(2,0), new Point(1,0), new Point(1,1), new Point(0,1)},
        {new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1)},
        {new Point(2,0), new Point(1,0), new Point(1,1), new Point(0,1)},
};
	private static final Point[][] z = {
        {new Point(0,1), new Point(1,1), new Point(1,0), new Point(2,0)},
        {new Point(1,0), new Point(1,1), new Point(0,1), new Point(0,2)},
        {new Point(0,1), new Point(1,1), new Point(1,0), new Point(2,0)},
        {new Point(1,0), new Point(1,1), new Point(0,1), new Point(0,2)},
};
	private static final Point[][] l = {
        {new Point(0,0), new Point(0,1), new Point(0,2), new Point(1,0)},
        {new Point(0,0), new Point(1,0), new Point(2,0), new Point(2,1)},
        {new Point(0,2), new Point(1,2), new Point(1,1), new Point(1,0)},
        {new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1)},
};
	private static final Point[][] j = {
        {new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1)},
        {new Point(0,1), new Point(1,1), new Point(2,1), new Point(2,0)},
        {new Point(0,0), new Point(0,1), new Point(0,2), new Point(1,2)},
        {new Point(0,0), new Point(0,1), new Point(1,0), new Point(2,0)},
};
	private static final Point[][] t= {
	    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(1,1)},
	    {new Point(0,1), new Point(1,1), new Point(1,0), new Point(1,2)},
	    {new Point(0,1), new Point(1,1), new Point(2,1), new Point(1,0)},
	    {new Point(0,0), new Point(0,1), new Point(0,2), new Point(1,1)}
	};
	private static final Point[][][] allBodies = {sq,ln,s,z,l,j,t};
	private static final Point[][] sqS = new Point[][]{
		    {new Point(0, 0), new Point(1, 0)}, 
		    {new Point(0, 0), new Point(1, 0)}, 
		    {new Point(0, 0), new Point(1, 0)}, 
		    {new Point(0, 0), new Point(1, 0)}}; 
	private static Point[][] lnS = new Point[][]{
		    {new Point(0, 0)},
		    {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)},
		    {new Point(0, 0) },
		    {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)}};
	private static Point[][] sS = {
		    {new Point(0, 0), new Point(1, 0), new Point(2, 1)},
		    {new Point(0, 1), new Point(1, 0), new Point(2, 0)},
		    {new Point(0, 0), new Point(1, 0), new Point(2, 1)},
		    {new Point(0, 1), new Point(1, 0), new Point(2, 0)}};
	private static Point[][] zS = new Point[][]{
		    {new Point(0, 1), new Point(1, 0), new Point(2, 0)},
		    {new Point(0, 1), new Point(1, 0)},
		    {new Point(0, 1), new Point(1, 0), new Point(2, 0)},
		    {new Point(0, 1), new Point(1, 0)}};
	private static Point[][] lS = new Point[][]{
		    {new Point(0, 0), new Point(1, 0)},
		    {new Point(0, 0), new Point(1, 0), new Point(2, 0)},
		    {new Point(0, 2), new Point(1, 0)},
		    {new Point(0, 0), new Point(1, 1), new Point(2, 1)}};
	private static Point[][] jS = new Point[][]{
		    {new Point(0, 0), new Point(1, 0), new Point(2, 1)},
		    {new Point(0, 1), new Point(1, 1), new Point(2, 0)},
		    {new Point(0, 0), new Point(1, 2)},
		    {new Point(0, 0), new Point(1, 0), new Point(2, 0)}};
	private static Point[][] tS = new Point[][]{
		    {new Point(0, 0), new Point(1, 0), new Point(2, 0)},
		    {new Point(0, 1), new Point(1, 0)},
		    {new Point(0, 1), new Point(1, 0), new Point(2, 1)},
		    {new Point(0, 0), new Point(1, 1)}};
	private static Point[][][] allSkirts = {sqS, lnS, sS, zS, lS, jS, tS};
	public int type, rotation;


	Tetronimo(int type){
		if(type < 0 || type > 6)
			this.type = 0;
		else this.type = type;
	}
	Tetronimo(){
		this.type = SQUARE;
		//this.type = (int) (Math.random()*6);
	}
	
	public Tetronimo clone(){
		Tetronimo t = new Tetronimo(this.type);
		t.rotation = this.rotation;
		return t;
	}
	public int getType(){
		return type;
	}
	public int getHeight(){
		//Get the largest Y value in the form
		int maxY = 0;
		for(Point p : getBody())
			if(p.y > maxY)
				maxY = p.y;
		return maxY;
	}
	public int getWidth(){
		//Get the largest X value in the form
		int maxX = 0;
		for(Point p : getBody())
			if(p.x > maxX)
				maxX = p.x;
		return maxX;
	}
	public Point[] getSkirt(){
		return allSkirts[type][rotation];
	}
	private static Point[] getSkirt(Point[] body){
		ArrayList<Point> skirt = new ArrayList<Point>();
		for(int i = 0; i < 4; i++){
			int min = Integer.MAX_VALUE;
			for(Point p : body){
				if(p.x == i && p.y < min)
					min = p.y;
			}
			if(min < 5){
				skirt.add(new Point(i,min));
			}
		}
		skirt.trimToSize();
		Point[] result = new Point[skirt.size()];
		for(int i = 0; i < result.length; i++)
			result[i] = skirt.get(i);
		return result;
	}
	@SuppressWarnings("unused")
	private static void printSkirts(){
		for(int type = 0; type < allBodies.length; type++){
			System.out.println(typeName[type]);
			for(int orientation = 0; orientation < allBodies[0].length; orientation++){
				for(Point p : getSkirt(allBodies[type][orientation])){
					System.out.print("(" + p.x + ", " + p.y + ") ");
				}
				System.out.println();
			}
		}
	}
	public Point[] getBody(){
		return allBodies[type][rotation];
	}
	public void rotateCClockwise(){
		//Rotate counterclockwise by reflecting across y=x
		rotation++;
		if(rotation >= allBodies[0].length)
			rotation = 0;
	}
	public void rotateClockwise(){
		rotation--;
		if(rotation < 0)
			rotation = allBodies[0].length - 1;
	}
	public String toString(){
		return String.format("Tetronimo \"%s\" \"%s\"", typeName[type], orientationName[rotation]);
	}
}
