
import java.awt.Image;
import javax.swing.ImageIcon;

public class NumberedCell extends Cell{

	
	public NumberedCell(int number) {
		super(32, false, getNumber(number));
		
		
	}
	
	public static Image getNumber (int number) {
		if(number==1) {
			return new ImageIcon("Ones.jpg").getImage();
		}
		else if(number==2) {
			return new ImageIcon("Twos.jpg").getImage();
		}
		else if(number==3) {
			return new ImageIcon("Three.jpg").getImage();
		}
		else if(number ==4 ){
			return new ImageIcon("Four.jpg").getImage();
		}
		else {
			return new ImageIcon("Five.jpg").getImage();
		}
	}

}
