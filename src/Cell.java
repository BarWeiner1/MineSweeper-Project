import java.awt.Image;

import javax.swing.ImageIcon;

public class Cell {
	private Image blank;
	private int sideLength;
	private boolean isMarked;
	private Image image;
	private boolean isFlagged;
	private Image flagged;
	private Image detonated;
	private boolean bombDetonated;
	private boolean revealed;

	public Cell(int size, boolean isClicked, Image picture) {
		blank = new ImageIcon("BlankSquare.jpg").getImage();
		flagged= new ImageIcon("Flag.jpg").getImage();
		detonated = new ImageIcon("Detonated.jpg").getImage();
		sideLength = size;
		isMarked = isClicked;
		image = picture;
		isFlagged = false;
		bombDetonated=false;
		revealed = false;
		
	}
	
	public void setMark(boolean mark){
		isMarked = mark;
	}
	public boolean getMark(){
		return isMarked;
	} 
	public void setImage(Image newIm) {
		image = newIm;
	}
	public void setFlagged(boolean value) {
		if(!getMark()) {
			isFlagged = value;
		}
	}
	public void setDetonated(boolean value) {
		bombDetonated = value;
	}
	public void reveal(boolean value) {
		revealed = value;
	}
	public boolean getFlagged() {
		return isFlagged;
	}
	public Image getImage() {
		if(bombDetonated) {
			return detonated;
		}
		if(isFlagged) {
			return flagged;
		}
		if (isMarked) {
			return image;
		}
		if(revealed) {
			return image;
		}
		return blank;
	}
}
 
