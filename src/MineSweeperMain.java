
import java.awt.Dimension; 
import java.awt.Toolkit;

import javax.swing.JFrame;


public class MineSweeperMain { 
		public static final JFrame window = new JFrame("MineSweeperGame");
	public static void main(String[] args) {
		
		MineSweeperModel gridPanel = new MineSweeperModel();
		MineSweeperView painter = new MineSweeperView(gridPanel);
		 
		 
		window.setContentPane(painter.getPanel());
	    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize(600, 400);
		   window.setLocation( (screensize.width - window.getWidth())/2,
	                (screensize.height - window.getHeight())/2 -150);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true); 
	}
	
	public static void setFrame(int width, int length) {
		window.setSize(width, length + 50 + 23);
	}
	

}
