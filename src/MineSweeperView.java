
import java.awt.Graphics;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;



import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;



public class MineSweeperView extends JPanel{
	private MineSweeperModel model;
	private MineSweeperGameController listener;
	private JLabel timeDisplay;
	private static int time;
	private JLabel flagsLeft;
	private JButton restart;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private JPanel gamePanel;
	private int flags;
	private JButton[] difficultyOptions;
	private Clip clip;
	private static Timer timer;
	
	public MineSweeperView(MineSweeperModel model) {
		playMusic("MineSweeperMusic.wav");
		this.model=model;
		flags = model.getFlags();
		listener = new MineSweeperGameController(model,this);
		gamePanel = new JPanel(new BorderLayout());
		cardLayout= new CardLayout();
		mainPanel= new JPanel(cardLayout);
		openingPanel opening = new openingPanel();
		mainPanel.add(opening, "OpeningPanel");
		mainPanel.add(gamePanel, "GamePanel");
		JPanel buttonPanel = new JPanel();
		opening.add(buttonPanel);
		buttonPanel.add(new JLabel("Choose your level of difficulty"));
		String[] buttonLabels = {"Easy", "Medium", "Hard"};
		difficultyOptions = new JButton[buttonLabels.length];
		for (int i = 0; i < buttonLabels.length; i++) {
			difficultyOptions[i] = new JButton(buttonLabels[i]);
			difficultyOptions[i].addActionListener(listener);
			buttonPanel.add(difficultyOptions[i]);
		}
		JPanel info = new JPanel();
		info.setLayout(new GridLayout(1,3));	
		timer= new Timer(1000, listener);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu difficultyBar = new JMenu("Difficulty");
		JMenu about = new JMenu("About");
		JMenuItem rules= new JMenuItem("Minesweeper Rules");
		rules.addActionListener(listener);
		about.add(rules);
		JMenuItem founder= new JMenuItem("About the Founder");
		founder.addActionListener(listener);
		about.add(founder);
		JMenuItem easy = new JMenuItem("Easy");//action event
	    JMenuItem medium = new JMenuItem("Medium");
	    JMenuItem hard = new JMenuItem("Hard");
	    easy.addActionListener(listener);
        medium.addActionListener(listener);
        hard.addActionListener(listener);
        difficultyBar.add(easy);
        difficultyBar.add(medium);
        difficultyBar.add(hard);
        menuBar.add(difficultyBar);
        menuBar.add(about);
        MineSweeperMain.window.setJMenuBar(menuBar);
        
		timeDisplay = new JLabel("Time " + time);
		flagsLeft= new JLabel("FlagsLeft " + flags);
		restart = new JButton("Restart");
		restart.addActionListener(listener);
		info.add(flagsLeft);
		info.add(restart);
		info.add(timeDisplay);
		
		gamePanel.add(info, BorderLayout.NORTH);
		gamePanel.add(this, BorderLayout.CENTER);
	
	}
	public static void stopTime() {
		timer.stop();
		
	}
	public void incrementTime() {
		time++;
		timeDisplay.setText("Time " + time);
		flagsLeft.setText("FlagsLeft " + model.getFlags());
		repaint();
	}
	public static void restartTime() {
		timer.start();
		time =-1;
	}
	public void nextCard(String panelName) {
		cardLayout.show(mainPanel, panelName);
		timer.start();
		
	}
	public void QueueOpeningCard() {
		cardLayout.show(mainPanel, "OpeningPanel");
	}
	public void playMusic(String fileName) {
		// Original MineSweeper Game Boy Music!!! https://www.youtube.com/watch?v=JRPA0cL6LmM&t=34s
		try {
			File soundFile = new File(fileName); 
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip(); 
			clip.open(audioIn); 
			clip.start();
			clip.loop(clip.LOOP_CONTINUOUSLY);
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		} 
	}
	public JPanel getPanel() {
		return mainPanel;
	}
	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < model.numRows(); i++) {
			for (int j = 0; j < model.numCols(); j++) {
				g.drawImage(model.getCells()[i][j].getImage(), i*32, j*32, model.cellHeight, model.cellHeight, null);
			} 
		}
	}
    private class openingPanel extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(255,121,255));
			g.setFont(new Font("Courier", Font.BOLD, 64));
			g.drawString("MineSweeper", 
					(getWidth() - g.getFontMetrics().stringWidth("MineSweeper!")) / 2, 
					getHeight() / 2);
		}
	} 
}
