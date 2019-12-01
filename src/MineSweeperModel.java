import java.awt.Image;
import javax.swing.*;
import java.awt.*;

public class MineSweeperModel  {
	
	private int numBombs;
	private int numFlags;
	public final int cellHeight=32;
	//instance variables represent the state of the panel
	private Cell[][] Cells;
	private int cellDimensions;
	private boolean loss = false;

	public int getFlags() {
		return numBombs-numFlags;
	}
	public MineSweeperModel() {}
	
	public int getCellDimensions() {
		return cellDimensions;
	}
	public void setCellDimensions(int size) {
		cellDimensions=size;
		Cells = new Cell[size][size];
	}
	public void setNumBombs(int value) {
		numBombs= value;
	}
	public void runGame() {
		loss= false;
		for(int i=0; i<Cells.length;i++) {
			for(int j=0; j<Cells[0].length;j++) {
				Cells[i][j]= new Cell(32, false, new ImageIcon("BlankSquare.jpg").getImage());
			}
		}
		placeMines(Cells.length, Cells[0].length, numBombs);
		numFlags=0;
		for(int i=0; i<Cells.length;i++) {
			for(int j=0; j<Cells[0].length;j++) {
				setValues(i, j);
			}
		}
	}
	public void restart() {
		runGame();

	}
	public int numRows() {
		return Cells.length;
	}
	
	public int numCols() {
		return Cells[0].length;
	}
	public void flag(int row, int col) {
		if (row < 0 || col < 0 || row >= numRows() || col >= numCols())
			throw new IllegalArgumentException();
		if(Cells[row][col].getFlagged()) {
			Cells[row][col].setFlagged(false);
			numFlags--;
		}
		else if(numFlags < numBombs && Cells[row][col].getMark()==false) {
			Cells[row][col].setFlagged(true);
			numFlags++;
		}
		if(checkWin()) {
			MineSweeperView.stopTime();
			revealBombs(row, col);
			JOptionPane.showMessageDialog(MineSweeperMain.window,"You win!");
			MineSweeperView.restartTime();
			restart();
		}
		
	}
	public void mark(int row, int col){
		if (row < 0 || col < 0 || row >= numRows() || col >= numCols())
			throw new IllegalArgumentException();
		if(Cells[row][col].getFlagged() == false && Cells[row][col].getMark() == false) {
			
			if(Cells[row][col] instanceof BombCell ) {
				Cells[row][col].setDetonated(true);
				loss = true;
				revealBombs(row, col);
				MineSweeperView.stopTime();
	
			}
			if(Cells[row][col] instanceof BlankCell) {
				findEmpty(row, col, null);
			}
			else {
				Cells[row][col].setMark(true);
			}
		}
		if(!loss & checkWin()) {
			MineSweeperView.stopTime();
			revealBombs(row, col);
			JOptionPane.showMessageDialog(MineSweeperMain.window,"You win!");
			MineSweeperView.restartTime();
			restart();
		}
	}
	public boolean checkWin() {
		for(int i=0; i<Cells.length; i++) {
			for(int j=0; j<Cells[0].length; j++) {
				if(!(Cells[i][j].getMark()) & !(Cells[i][j].getFlagged())) {
					return false;
				}
			}
		}
		return true;
	}
	public void findEmpty(int row, int column, Route course) {
		if(Cells[row][column].getFlagged()) {
			return;
		}
		else if(Cells[row][column].getMark()) {
			return;
		}
		
		else if(!(Cells[row][column] instanceof BlankCell) ) {
			Cells[row][column].setMark(true);
			return;
		}
		if(row!= 0 && course !=Route.DOWN) {
			Cells[row][column].setMark(true);
			findEmpty(row-1, column, Route.UP);
		}
		if(row!= Cells.length-1  && course !=Route.UP) {
			Cells[row][column].setMark(true);
			findEmpty(row+1, column, Route.DOWN);
		}
		if(column!=Cells[0].length-1  && course != Route.LEFT) {
			Cells[row][column].setMark(true);
			findEmpty(row, column+1, Route.RIGHT);
		}
		if(column!=0 && course != Route.RIGHT) {
			Cells[row][column].setMark(true);
			findEmpty(row, column-1, Route.LEFT);
		}
		if(row != Cells.length-1 && column<Cells[0].length-1 && course != Route.UpLeft) {
			Cells[row][column].setMark(true);
			findEmpty(row+1, column+1, Route.DownRight);
		}
		if(row!= Cells.length-1 && column != 0 && course !=Route.UpRight) {
			Cells[row][column].setMark(true);
			findEmpty(row+1, column-1, Route.DownLeft);
		}
		if(column!=Cells[0].length-1  && row != 0 && course != Route.DownLeft) {
			Cells[row][column].setMark(true);
			findEmpty(row-1, column+1, Route.UpRight);
		}
		if(column!=0 && row !=0 && course != Route.DownRight) {
			Cells[row][column].setMark(true);
			findEmpty(row-1, column-1, Route.UpLeft);
		}
	}
	public void revealBombs(int row, int column) {
		for(int i=0; i<Cells.length;i++) {
			for(int j=0; j<Cells[0].length;j++) {
				if(Cells[i][j].getMark()== false) {
					if(Cells[i][j] instanceof BombCell && (i!=row || j!= column)) {
						Cells[i][j].reveal(true);
						Cells[i][j].setMark(true);
					}
					else if(Cells[i][j].getFlagged() && !(Cells[i][j] instanceof BombCell)) {
						Cells[i][j].setImage(new ImageIcon("Incorrect.jpg").getImage());
						Cells[i][j].setFlagged(false);
						Cells[i][j].setMark(true);
					}
					else{ 
						Cells[i][j].setImage(new ImageIcon("BlankSquare.jpg").getImage());
						Cells[i][j].setMark(true);
					}
				}
			}
		}
		
		
	}
	public void setValues(int row, int column) {
		if(5>0) {
			if(!(Cells[row][column] instanceof BombCell)) {
				int label = getMineCount(row, column);
				if (label > 0) {
					Cells[row][column] = new NumberedCell(label);
				}
				else {
					Cells[row][column]= new BlankCell();
				}
			}
		}
	}
		
	public int getMineCount(int row, int column) {
		int count=0;
		if(row!= Cells.length-1 && Cells[row+1][column] instanceof BombCell) {//One down
			count ++;
		}
		if(column!=Cells[0].length-1 && Cells[row][column+1] instanceof BombCell) {//One right
			count ++;
		}
		if(row < Cells.length-1 && column<Cells[0].length-1 && Cells[row+1][column+1] instanceof BombCell) {//Right and Down
			count ++;
		}
		if(row!= 0 && Cells[row-1][column] instanceof BombCell) {//Up One
			count ++;
		}
		if(row!= 0 && column!=0 && Cells[row-1][column-1] instanceof BombCell) {//Up Left
			count ++;
		}
		if(column!=0 && Cells[row][column-1] instanceof BombCell) {//One left
			count ++;
		}
		if(row != Cells.length-1 && column>0 && Cells[row+1][column-1] instanceof BombCell) {//Down left
			count ++;
		}
		if(row!= 0 && column != Cells[0].length-1 && Cells[row-1][column+1] instanceof BombCell) {
			count ++;
		}
		return count;
	}
	public void placeMines(int row, int col, int numMines) {
		while(numMines>0) {
			int randRow = (int)(Math.random()*row);
			int randCol = (int)(Math.random()*col);
			if(Cells[randRow][randCol] instanceof BombCell) {
				continue;
			}
			else {
				Cells[randRow][randCol] = new BombCell(cellHeight, false, new ImageIcon("Mine.jpg").getImage());
			}
			numMines--;
		}
	}
	public Cell[][] getCells(){
		return Cells;
	}
	public int getHeight() {
		return Cells.length*cellHeight;
	}
	public int getWidth() {
		return Cells[0].length * cellHeight;
	}
	
}












