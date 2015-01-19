import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.BorderUIResource.BevelBorderUIResource;


public class GameView extends JFrame implements Runnable{
	/**
	 * 
	 */
	static final int HEIGHTVIEW = 20, WIDTHVIEW = 10;
	private long lastRun, refreshRate = 1 * 1000;
	private static final long serialVersionUID = 0xDEADBEEFL;
	protected JPanel board;
	protected Tetris tetris;
	protected Tetronimo active;
	protected InputHandler input; 
	protected static final Point origin = new Point(HEIGHTVIEW, WIDTHVIEW / 2);
	final int BLOCK = 10;
	private final Color[] blockColors = {Color.ORANGE, Color.CYAN, Color.PINK, 
							Color.GREEN, Color.BLUE, Color.MAGENTA, Color.RED};
	public GameView(){
		tetris = new Tetris();
		input = new InputHandler();
		this.addKeyListener(input);
		this.setTitle("Tetris");
		this.setSize(300,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLayout(new GridLayout(1, 1));
		this.setJMenuBar(buildMenu());
		board = buildBoard();
		this.add(board);
		this.pack();
		this.setVisible(true);
	}
	public void run(){
		while(true){
			if(System.currentTimeMillis() - lastRun >= refreshRate){
				step();
				lastRun = System.currentTimeMillis();
			}
		}
	}
	private void step(){
		tetris.stepDown();
		drawBoard();
		System.out.println("Clearable Rows: " + tetris.clearableRows());
		if(tetris.clearableRows() > 0){
			tetris.clearFullRows();//Clear filled rows
			drawBoard();//Show cleared rows
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tetris.clearFullRows();
			drawBoard();//Show new play surface
		}	
		
	}
	private JPanel buildBoard(){
        final int SQUARE = 25;
        Dimension boardDimensions = new Dimension(WIDTHVIEW * SQUARE,HEIGHTVIEW * SQUARE);
        JPanel board = new JPanel();
        board.setPreferredSize(boardDimensions);
        board.setLayout(new GridLayout(HEIGHTVIEW, WIDTHVIEW));
        for(int col = HEIGHTVIEW -1; col >= 0  ; col--)
            for(int row = 0; row < WIDTHVIEW; row++ ){
                JPanel b = new JPanel();
                b.setBounds(new Rectangle(SQUARE, SQUARE));
                String location = String.format("%d %d",row, col); 
                b.setName(location);
                b.setBackground(Color.green);
                b.setBorder(new BevelBorder(BevelBorderUIResource.RAISED, Color.lightGray, Color.darkGray));
                b.setSize(BLOCK, BLOCK);
                b.setToolTipText(location);
                board.add(b);
            }
            return board;
    }
	private void drawBoard(){
		for(Component cmp : board.getComponents()){
			JPanel block = (JPanel) cmp;
			int x = Integer.parseInt(block.getName().split(" ", 2)[0]);
			int y = Integer.parseInt(block.getName().split(" ", 2)[1]);
			int c = tetris.getBlock(new Point(x,y));
			if(c < 0)
				block.setBackground(Color.lightGray);
			else block.setBackground(blockColors[c]);	
		}	
	}
	private JMenuBar buildMenu(){
		JMenuBar menu = new JMenuBar();
		JMenu gameItem = new JMenu("Game");
		JMenuItem startGame = new JMenuItem("Start Game");
		gameItem.add(startGame);
		menu.add(gameItem);
		return menu;
	}
	public static void main(String[] args){
		GameView gv = new GameView();
		gv.run();
	}
	class InputHandler implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT: tetris.shiftLeft(); step(); break;
			case KeyEvent.VK_RIGHT: tetris.shiftRight(); step(); break;
			case KeyEvent.VK_UP: tetris.rotateLeft(); step(); break;
			case KeyEvent.VK_DOWN: tetris.rotateRight(); step(); break;
			case KeyEvent.VK_SPACE: tetris.drop(); step(); break;
			default: System.out.println(e.getKeyChar()); step(); break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
