import java.awt.Point;


public class Tetris {
	public static final int HEIGHT = 22, WIDTH = 10, BLANK = -1;
	int[][] gameBoard;
	protected Tetronimo activePiece;
	protected Point activeLocation;
	protected static final Point ORIGIN = new Point(WIDTH/2, HEIGHT-4);
	protected int clearedRows;
	public Tetris(){
		gameBoard = new int[WIDTH][HEIGHT];
		activePiece = new Tetronimo();
		activeLocation = ORIGIN;
		clearedRows = 0;
		for(int r = 0; r < gameBoard.length; r++)
			for(int c = 0; c < gameBoard[0].length; c++)
				gameBoard[r][c] = BLANK;
	}
	public int getHeight(){
		return HEIGHT;
	}
	public int getWidth(){
		return WIDTH;
	}
	public int getBlock(Point p){
		for(Point pt : activePiece.getBody()){
			pt = (Point) pt.clone();
			pt.translate(activeLocation.x, activeLocation.y);
			if(pt.equals(p))
				return activePiece.getType();
		}
		return gameBoard[p.x][p.y];
	}
	private void setBlock(int type, Point p){
		gameBoard[p.x][p.y] = type;
	}
	private boolean inBounds(Point p){
		boolean result =  (p.x >= 0 && p.x < gameBoard.length 
				&& p.y >= 0 /*&& p.y < gameBoard[0].length*/);
		return result;
	}
	private boolean inBounds(Tetronimo t, Point p){
		Point corner = (Point) p.clone();
		corner.translate(t.getWidth(), t.getHeight());
		return (inBounds(p) && inBounds(corner));
	}
	public void clearRow(int row){
		if(row < 0 || row > gameBoard.length)
			throw new IndexOutOfBoundsException("Illegal row call for getRow: "+ row);
		//copy each above row into the one below
		for(; row < gameBoard.length; row++)
			for(int col = 0; col < gameBoard[0].length; col++)
				if(col + 1 < gameBoard[0].length)
					gameBoard[row][col] = gameBoard[row][col+1];
				else
					gameBoard[row][col] = BLANK;
	}
	//Place the given Tetronimo on the board; the lower left corner of the 
	//Tetronimo is defined by Point p
	private boolean checkPiece(Tetronimo t, Point p){
		if(!inBounds(t,p)){
			return false;
		}	
		for(Point pt : t.getBody()){
			Point dest = (Point) pt.clone();
			dest.translate(p.x, p.y);
			if(gameBoard[dest.x][dest.y] != BLANK)
				return false;
		}
		return true;
	}
	private void placePiece(Tetronimo t, Point p){
		if(!checkPiece(t, p)){
			System.out.println("Fail");
			return;
		}	
		for(Point pt : t.getBody()){
			Point dest = (Point) pt.clone();
			dest.translate(p.x, p.y);	
			setBlock(t.getType(), dest);
		}
	}
	/*Step active piece down by 1 unit
	 * If this is impossible, generate new AP  at origin
	 * */
	public boolean stepDown(){
		Point newPoint = (Point) activeLocation.clone();
		newPoint.translate(0, -1);
		if(checkPiece(activePiece, newPoint)){
			activeLocation = newPoint;
			return true;
		}else{//The Piece has been permanently committed
			commitActive();
			return false;
		}
				
	}
	private void commitActive() {
		System.out.println(activeLocation);
		placePiece(activePiece, activeLocation);
		activePiece = new Tetronimo();
		activeLocation = ORIGIN;
		
	}
	public void shiftRight(){
		Point newPoint = (Point) activeLocation.clone();
		newPoint.translate(1, 0);
		if(checkPiece(activePiece, newPoint)){
			activeLocation = newPoint;
		}
	}
	public void shiftLeft(){
		Point newPoint = (Point) activeLocation.clone();
		newPoint.translate(-1, 0);
		if(checkPiece(activePiece, newPoint)){
			activeLocation = newPoint;
		}
	}
	/*Identifies a clearable (entirely nonempty)
	 * row
	 * */
	public void rotateRight(){
		Tetronimo t = activePiece.clone();
		t.rotateClockwise();
		if(checkPiece(t, activeLocation)){
			activePiece = t;
		}
	}
	public void rotateLeft(){
		Tetronimo t = activePiece.clone();
		t.rotateCClockwise();
		if(checkPiece(t, activeLocation)){
			activePiece = t;
		}
	}
	private boolean rowClearable(int row){
		for(int c = 0; c < WIDTH; c++)
			if(gameBoard[row][c] == BLANK){
				return false;
			} 	
		System.out.printf("Row %d is Clearable\n", row);
		return true;
	}
	public int clearableRows() {
		int count = 0;
		for(int row = 0; row < gameBoard.length; row++)
			if(rowClearable(row))
				count++;
		return count;
	}
	public void clearFullRows() {
		for(int row = HEIGHT - 1; row >=0; row--){
			if(rowClearable(row))
				clearRow(row);
		}
	}
	public void drop() {
		// TODO Auto-generated method stub
		
	}
	
 	
}
