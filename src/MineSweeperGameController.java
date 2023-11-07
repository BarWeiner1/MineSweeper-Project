
import java.awt.event.*;

import javax.swing.JOptionPane;

public class MineSweeperGameController extends MouseAdapter implements ActionListener {
	private MineSweeperModel panel;
	private MineSweeperView view;
	
	public MineSweeperGameController(MineSweeperModel panel, MineSweeperView view) {
		this.panel=panel;
		this.view =view;
		view.addMouseListener(this);
		
	}
	
	
	@Override
	public void mousePressed(MouseEvent ev) {
		view.repaint();
		int col = ev.getY() / 32;
		int row = ev.getX() / 32;
		
		if(ev.isMetaDown()) { 
			panel.flag(row,col);
		}
		else {
			panel.mark(row,col);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s== null) {
			view.incrementTime();
		}
		else if("Restart".equals(s)) {
			MineSweeperView.restartTime();
			panel.restart();
			
			
		}
		else if("Minesweeper Rules".equals(s)) {
			JOptionPane.showMessageDialog(MineSweeperMain.window, "You are presented with a board of squares. Some squares contain mines (bombs), others don't. If you click on a square containing a bomb, you lose. If you manage to click all the squares (without clicking on any bombs) you win.\n" + 
					"Clicking a square which doesn't have a bomb reveals the number of neighbouring squares containing bombs. Use this information plus some guess work to avoid the bombs.\n" + 
					"To open a square, point at the square and click on it. To mark a square you think is a bomb, point and command-click .");
		}
		else if("About the Founder".equals(s)) {
			JOptionPane.showMessageDialog(MineSweeperMain.window, "Bar Weiner is a 10th Grade Student at Los Altos High School, he recieved inspiration for his final project from classic game MineSweeper.");
		}
		else if("Easy".equals(s)) {
			panel.setCellDimensions(8);
			panel.setNumBombs(10);
			panel.runGame();
			MineSweeperMain.setFrame(panel.cellHeight*panel.getCellDimensions(), panel.cellHeight*panel.getCellDimensions());
			view.nextCard("GamePanel");
			MineSweeperView.restartTime();
		}
		else if("Medium".equals(s)) {
			panel.setCellDimensions(16);
			panel.setNumBombs(20);
			panel.runGame();
			MineSweeperMain.setFrame(panel.cellHeight*panel.getCellDimensions(), panel.cellHeight*panel.getCellDimensions());
			view.nextCard("GamePanel");
			MineSweeperView.restartTime();
		}
		else if("Hard".equals(s)) {
			panel.setCellDimensions(24);
			panel.setNumBombs(99);
			panel.runGame();
			MineSweeperMain.setFrame(panel.cellHeight*panel.getCellDimensions(), panel.cellHeight*panel.getCellDimensions());
			view.nextCard("GamePanel");
			MineSweeperView.restartTime();
		}
		view.requestFocusInWindow();
		view.nextCard("mainPanel");
		view.repaint();
		
	}
	
	
}
