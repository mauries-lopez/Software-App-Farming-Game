package gameMenu;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import gameMain.*;

/**
 * The MainMenu class is used for the main menu of the game. It contains the Start the Game, How to Play?
 * Quit Game and mute(for the music) buttons.
 */

/**
 *
 * @author Lopez, Mauries
 * 		   Rojo, Kate Lynn
 *
 * @version 12/10/2022
 *
 */
public class MainMenu{

	/**
	 * the window of the main menu.
	 */
	public JFrame frmMainMenu;
	JPanel bgPanel = new JPanel();
	
	Clip clip;

	/**
	 * Launches the Main Menu.
	 * @param args for main method
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmMainMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JButton[] btnChoice = new JButton[3];
		
		String filePath = "src/Wager With Angels - Nathan Moore.wav";
		File musicPath = new File(filePath);
		

		for ( int i = 0 ; i < 3 ; i++ ) 
		{
			btnChoice[i] = new JButton();
			/*
			 * Button Numbers
			 * [0] = Start
			 * [1] = How to play
			 * [2] = Quit
			 */
		}
		
		
		
		//Make the window
		frmMainMenu = new JFrame();
		frmMainMenu.setAutoRequestFocus(false);
		frmMainMenu.setResizable(false);
		frmMainMenu.setTitle("MyFarm | Main Menu |");
		frmMainMenu.setSize(450,300);
		frmMainMenu.setForeground(new Color(0, 0, 0));
		frmMainMenu.getContentPane().setFont(new Font("MOON GET!", Font.PLAIN, 11));
		frmMainMenu.getContentPane().setBackground(new Color(255, 255, 255));
		frmMainMenu.setBackground(new Color(0, 0, 0));
		frmMainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainMenu.getContentPane().setLayout(null);
		frmMainMenu.setLocationRelativeTo(null);
		
		playMusic(musicPath);
		
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-10.0f);
		
		JButton muteButton = new JButton("Mute");
		JButton unmuteButton = new JButton("Unmute");
		
		
		muteButton.setFont(new Font("Tahoma", Font.PLAIN, 7));
		muteButton.setBounds(10, 11, 65, 15);
		muteButton.setFocusable(false);
		muteButton.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e ) 
			{
				unmuteButton.setVisible(true);
				
				if ( unmuteButton.isVisible() == true ) 
				{
					gainControl.setValue(-80.0f);
					muteButton.setVisible(false);
					
				}
				
			}
		});
		bgPanel.add(muteButton);
		
		
		unmuteButton.setFont(new Font("Tahoma", Font.PLAIN, 7));
		unmuteButton.setBounds(10, 11, 65, 15);
		unmuteButton.setFocusable(false);
		unmuteButton.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e ) 
			{
				
				if ( muteButton.isVisible() == false ) 
				{
					gainControl.setValue(-10.0f);
					unmuteButton.setVisible(false);
					muteButton.setVisible(true);
				}

			}
		});
		bgPanel.add(unmuteButton);
		
		//Start Game Button
		btnChoice[0].setText("START GAME");
		btnChoice[0].setToolTipText("Proceed to the game proper");
		btnChoice[0].setBounds(262, 39, 136, 39);
		btnChoice[0].setFont(new Font("Haettenschweiler", Font.PLAIN, 21));
		btnChoice[0].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				stopMusic();
				MyFarm myFarm = new MyFarm();
				myFarm.myFarm.setVisible(true);
				frmMainMenu.dispose();
				
				
			}
			
		});
		frmMainMenu.getContentPane().add(btnChoice[0]);
		
	
		//How To Play Button
		ImageIcon howToPlay = new ImageIcon("src/HowToPlay.png");
		
		btnChoice[1].setText("HOW TO PLAY?");
		btnChoice[1].setToolTipText("Know the rules and basics");
		btnChoice[1].setFont(new Font("Haettenschweiler", Font.PLAIN, 21));
		btnChoice[1].addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e ) 
			{
				JOptionPane.showMessageDialog(null, null, "How To Play", JOptionPane.INFORMATION_MESSAGE, howToPlay);
			}
		});
		btnChoice[1].setBounds(262, 102, 136, 39);
		frmMainMenu.getContentPane().add(btnChoice[1]);

		//Quit Game Button
		btnChoice[2].setText("QUIT GAME");
		btnChoice[2].setToolTipText("Closes the game");
		btnChoice[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int i = JOptionPane.showConfirmDialog(frmMainMenu, "Do wish to quit?", "Exit" , 0);
				if ( i == 0 ) {
					System.exit(0);
				}

			}
		});
		
		btnChoice[2].setBounds(262, 169, 136, 39);
		btnChoice[2].setFont(new Font("Haettenschweiler", Font.PLAIN, 21));
		frmMainMenu.getContentPane().add(btnChoice[2]);

		
		//Orange Left Background
		bgPanel.setBorder(null);
		bgPanel.setBounds(0, 0, 217, 261);
		bgPanel.setBackground(new Color(124, 252, 0, 10));
		frmMainMenu.getContentPane().add(bgPanel);
		bgPanel.setLayout(null);
		
		//MyFarm Title
		JLabel lblMyFarm = new JLabel("MyFarm");
		lblMyFarm.setBackground(new Color(255, 255, 255));
		lblMyFarm.setForeground(new Color(255, 255, 255));
		lblMyFarm.setBounds(29, 70, 167, 72);
		bgPanel.add(lblMyFarm);
		lblMyFarm.setFont(new Font("Haettenschweiler", Font.PLAIN, 64));
		
		//MyFarm Sub-Title
		JLabel lblFarmingSimulatorGame = new JLabel("Farming Simulator Game");
		lblFarmingSimulatorGame.setForeground(new Color(255, 255, 255));
		lblFarmingSimulatorGame.setFont(new Font("Haettenschweiler", Font.PLAIN, 23));
		lblFarmingSimulatorGame.setBounds(29, 116, 173, 72);
		bgPanel.add(lblFarmingSimulatorGame);

		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src/MainMenu.png"));
		lblNewLabel.setBounds(0, 0, 434, 261);
		frmMainMenu.getContentPane().add(lblNewLabel);
			
		
		

	}
	
	private void playMusic(File musicPath) 
	{
		
		
		try 
		{
			
			if ( musicPath.exists() ) 
			{
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				
				this.clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				
				clip.start();
	
			}

		}
		catch ( Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * stops the music
	 */
	
	public void stopMusic() 
	{
		clip.stop();
	}
}
