package gameMain;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import gameSettings.Crop;
import gameSettings.GameStatus;
import gameSettings.Tile;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BoxLayout;
/**
 *
 * The MyFarm class is used to display the main game. It also contains the main method
 *
 */

/**
 * 
 * @author Lopez, Mauries 
 * 		   Rojo, Kate Lynn
 * 
 * @version 12/10/2022
 *
 */

public class MyFarm implements ActionListener 
{

	Tile tile = new Tile(); //Tile Class
	GameStatus createReport = new GameStatus(); //GameStatus Class
	static Player player = new Player("Farmer", 100, 0, 0, 0, 0, 0, 0); //Player Class

	/**
	 * The main frame of the game.
	 */
	public JFrame myFarm;
	
	static JPanel rightPanel = new JPanel(); //Game Status Reports [Gray Color]
	JPanel lowerPanel = new JPanel(); //Player Status HUD

	/**
	 * The whole 10x5 Tiles of the farm lot.
	 */
	public static JButton[] buttonTiles = new JButton[51];

	/**
	 * a Panel used to put the tiles
	 */
	public static JPanel buttonPanelTiles = new JPanel();

	/**
	 * a Panel that is a white rectangle to put the buttons of the player's actions
	 */
	public static JPanel playerActionPanel = new JPanel();
	JButton playerBtnGameAction = new JButton("Game Action"); //Button for Game Action
	JButton playerBtnPlayerAction = new JButton("Player Action"); //Button for Player Action

	/**
	 * Player Status Panel
	 */
	public static JPanel playerStatusPanel = new JPanel();

	/**
	 * Game Report Panel
	 */
	public static JPanel gameReportPanel = new JPanel();

	/**
	 * Report Status Display
	 */
	public static JPanel reportStatusPanel = new JPanel();

	/**
	 * an ArrayList for the list of seeds the player can buy and plant.
	 */
	public static List<Crop> seedList = new ArrayList<>();
	/**
	 * an ArrayList for the name of the seed bought by the player.
	 */
	public static List<String> boughtSeedListName = new ArrayList<>();

	/**
	 * an ArrayList for the planted seeds.
	 */
	public static List<Crop> plantedSeed = new ArrayList<Crop>(Arrays.asList());

	/**
	 * User choice of Tile Number
	 */
	public static String tileStringNumber = null;

	/**
	 * tileStringNumber as integer storage
	 */
	public static int tileNumber;
	
	//Game Day
	JLabel lblGameDay = new JLabel("Day [ 1 ]");

	/**
	 * Used to set the game day.
	 */
	public static int gameDay = 1; 
	
	//Tile Rocked / Plowed Status/ Withered Status/

	/**
	 * Used to set the rocked tile status.
	 */
	public static int[] isRockedTiles = new int[51];

	/**
	 * Used to set the plowed tile status.
	 */
	public static int[] isPlowedTiles = new int[51];

	/**
	 * Used to set the withered tile status.
	 */
	public static int[] isWitheredTiles = new int[51];

	/**
	 * Used to cancel the tile number.
	 */
	public static boolean isCancelledTileNumber = false; 

	/**
	 * Used to display the current farmer type of the player.
	 */
	public static JLabel playerTypeLabel = new JLabel();

	/**
	 * Used to display the current level of the player.
	 */
	public static JLabel playerLevelLabel = new JLabel();

	/**
	 * Used to display the current XP of the player.
	 */
	public static JLabel playerXPLabel = new JLabel();

	/**
	 * Used to display the current ObjectCoins of the player.
	 */
	public static JLabel playerObjectCoinLabel = new JLabel();

	/**
	 * Used to count the number of times the player has been promoted.
	 */
	public static int promoteNotifCount = 0;
	
	
	JPanel panelBackground = new JPanel();
	JLabel labelLowerHUD = new JLabel();
	
	Clip clip;
	
	/**
	 * Launches the application.
	 * @param args - for main method
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MyFarm window = new MyFarm();
					window.myFarm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyFarm() {
		initialize(); //Main Window
		initializeGameTiles(); //Farm Tiles
		initializePlayerChoice(); //Player Choices (Game or Player Action)
		initializeImages(); //Images
		
		
		//Assuming all tiles already has a plant but NULL
		while( plantedSeed.size() < 51 ) 
		{
			plantedSeed.add(null);
		}
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//Music
		String filePath = "src/Girasol - Quincas Moreira.wav";
		File musicPath = new File(filePath);
		
		//Whole Window
		myFarm = new JFrame();
		myFarm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		myFarm.setResizable(false);
		myFarm.setTitle("MyFarm | Game |");
		myFarm.setSize(1000,800);
		myFarm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFarm.setLocationRelativeTo(null);
		myFarm.getContentPane().setLayout(null);
		
		//Play Music
		playMusic(musicPath);
		
		//Music Gain Control
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-10.0f);
		
		//Place where Game Reports and Inventory is stored
		rightPanel.setBackground(Color.LIGHT_GRAY);
		rightPanel.setBounds(831, 0, 153, 761);
		myFarm.getContentPane().add(rightPanel);
		rightPanel.setLayout(null);
		
		//Game Report Panel
		gameReportPanel.setBackground(new Color(0, 0, 0));
		gameReportPanel.setBounds(0, 0, 155, 761);
		rightPanel.add(gameReportPanel);
		gameReportPanel.setLayout(null);
		reportStatusPanel.setBackground(new Color(255, 255, 255));
		
		//Report Status Panel
		reportStatusPanel.setBounds(10, 11, 135, 739);
		gameReportPanel.add(reportStatusPanel);
		reportStatusPanel.setLayout(null);
		
		//Only a separator (DONT TOUCH)
		JSeparator rightPanelSep = new JSeparator();
		rightPanelSep.setBounds(10, 438, -19, 2);
		rightPanel.add(rightPanelSep);
		
	
		//Option Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 47, 26);
		myFarm.getContentPane().add(menuBar);
		
		//Inside Option Bar
		JMenu menuBarOption = new JMenu("Option");
		menuBarOption.setBackground(new Color(255, 255, 255));
		menuBarOption.setHorizontalAlignment(SwingConstants.CENTER);
		menuBarOption.setBounds(0,0,50,19);
		menuBarOption.setForeground(new Color(0, 0, 0));
		menuBar.add(menuBarOption);
		
		JPanel menuBarPanel = new JPanel();
		menuBarPanel.setBorder(null);
		menuBarOption.add(menuBarPanel);
		
		
		JButton btnRestartGame = new JButton("Restart Game");
		btnRestartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				String[] choices = {"Reset", "Cancel"};
				int userInput;
				
				userInput = JOptionPane.showOptionDialog(playerActionPanel, "Are you sure?", "Reset Game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
				
				if ( userInput == 0 ) 
				{
					try {
						generateRockTiles(true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		menuBarPanel.setLayout(new BoxLayout(menuBarPanel, BoxLayout.Y_AXIS));
		menuBarPanel.add(btnRestartGame);
		
		JButton btnHowToPlay = new JButton("How To Play");
		menuBarPanel.add(btnHowToPlay);
		btnHowToPlay.addActionListener( new ActionListener() 
		{
			ImageIcon howToPlay = new ImageIcon("src/HowToPlay.png");
			
			public void actionPerformed( ActionEvent e ) 
			{
				JOptionPane.showMessageDialog(null, null, "How To Play", JOptionPane.INFORMATION_MESSAGE, howToPlay);
			}
		});

		
		JButton muteButton = new JButton("Mute");
		JButton unmuteButton = new JButton("Unmute");
		
		unmuteButton.setVisible(false);
		
		muteButton.setFocusable(false);
		muteButton.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e ) 
			{
				if ( unmuteButton.isVisible() == false ) 
				{
					gainControl.setValue(-80.0f);	
					unmuteButton.setVisible(true);
					muteButton.setVisible(false);
				}
					
			}
		});
			

		unmuteButton.setFocusable(false);
		unmuteButton.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e ) 
			{
				if ( muteButton.isVisible() == false ) 
				{
					gainControl.setValue(-10.0f);
					muteButton.setVisible(true);
					unmuteButton.setVisible(false);
				}
				
			}
		});
		
		menuBarPanel.add(muteButton);
		menuBarPanel.add(unmuteButton);
		
		JButton btnQuitGame = new JButton("Quit");
		btnQuitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(myFarm, "Do wish to quit?", "Exit" , 0);
				if ( i == 0 ) {
					System.exit(0);
				}
			}
		});
		menuBarPanel.add(btnQuitGame);
		menuBarPanel.repaint();
	
		
		lblGameDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameDay.setFont(new Font("Tahoma", Font.BOLD, 21));
		
		lblGameDay.setBounds(345, 30, 151, 35);
		myFarm.getContentPane().add(lblGameDay);
		
		
		
		//Place where the Player HUD is
		lowerPanel.setLayout(null);
		lowerPanel.setBackground(new Color(65,36,11));
		lowerPanel.setBounds(0, 639, 984, 126);
		myFarm.getContentPane().add(lowerPanel);
		
		
		//Only a separator (DONT TOUCH)
		JSeparator lowerPanelSep = new JSeparator();
		lowerPanelSep.setBounds(170, 0, 1, 2);
		lowerPanel.add(lowerPanelSep);
		

		
		
	}
	
	private void initializeImages() 
	{
		
		//Farmer Picture
		JLabel labelFarmerPic = new JLabel();
		labelFarmerPic.setBounds(18, 2, 153, 126);
		labelFarmerPic.setIcon(new ImageIcon("src/Farmer.png"));
		lowerPanel.add(labelFarmerPic);
		labelFarmerPic.validate();
		
		panelBackground.setBounds(0, 0, 832, 640);
		myFarm.getContentPane().add(panelBackground);
		panelBackground.setLayout(null);
		
		JLabel labelBackgroundPic = new JLabel();
		labelBackgroundPic.setBounds(0, 0, 836, 640);
		labelBackgroundPic.setIcon(new ImageIcon("src/Background.png"));
		labelBackgroundPic.validate();
		panelBackground.add(labelBackgroundPic);
		
		
	}
	
	private void initializePlayerChoice() 
	{
		
		//Place where the buttons is stored
		playerActionPanel.setBackground(new Color(255, 255, 255));
		playerActionPanel.setBounds(301, 9, 525, 105);
		lowerPanel.add(playerActionPanel);
		playerBtnGameAction.setBounds(0, 0, 262, 105);
		
		playerBtnGameAction.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				playerBtnGameAction.setVisible(false);
				playerBtnPlayerAction.setVisible(false);
				playerActionPanel.setVisible(false);
				initializeGameAction();
			}
		});
		playerActionPanel.setLayout(null);
		
		playerBtnGameAction.setFocusable(false);
		playerActionPanel.add(playerBtnGameAction);
		playerBtnPlayerAction.setBounds(262, 0, 265, 105);
		
		
		playerBtnPlayerAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				initializePlayerAction();
			}
		});
		
		playerBtnPlayerAction.setFocusable(false);
		playerActionPanel.add(playerBtnPlayerAction);
		
		
		//Place where Player Status will be stored
		playerStatusPanel.setBackground(new Color(255, 255, 255));
		playerStatusPanel.setBounds(148, 9, 145, 105);
		lowerPanel.add(playerStatusPanel);
		playerStatusPanel.setLayout(null);
		
		Player.initializePlayerStatus();
		
	}
	
	private void initializeGameTiles() 
	{
		
		//Whole 10x5 Farm Tile 
		buttonPanelTiles.setBounds(108, 89, 623, 436);
		myFarm.getContentPane().add(buttonPanelTiles);	
		buttonPanelTiles.setLayout(new GridLayout(10,5));
		buttonPanelTiles.setBackground(new Color(150,150,150));
		
		
		//Generate The Tiles
		for ( int i = 1; i < 51; i++ ) 
		{
			buttonTiles[i] = new JButton();
			buttonPanelTiles.add(buttonTiles[i]);
			buttonTiles[i].setText(""+i);
			buttonTiles[i].setBackground(new Color(65,36,11));
			buttonTiles[i].setFont(new Font("MV Boli", Font.BOLD, 21));
			buttonTiles[i].setFocusable(false);
			buttonTiles[i].addActionListener(this);
			buttonTiles[i].setVisible(true);
			buttonTiles[i].setEnabled(false);	
			buttonTiles[i].setToolTipText("Tile unplowed");
		}
		
		try {
			generateRockTiles(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initializePlayerAction() 
	{
		do
		{							
								
			tileStringNumber = JOptionPane.showInputDialog(playerActionPanel,"Choose a Tile Number", "Tile", JOptionPane.QUESTION_MESSAGE);
								
							
			if ( tileStringNumber != null && !tileStringNumber.equals("")) 
			{
				tileNumber = Integer.parseInt(tileStringNumber);
				isCancelledTileNumber = false;
								
				//Ask for a tool
				playerChoice(tileNumber);
			}
			else
			{
				isCancelledTileNumber = true;
				tileStringNumber = null;
				tileNumber = 0;
			}

			if ( (tileNumber < 1 || tileNumber > 50 ) && isCancelledTileNumber == false )
			{

				createReport.updateReport("Please enter an INTEGER from [1 to 50]");
				//JOptionPane.showMessageDialog(playerActionPanel, "Please enter an INTEGER from [1 to 50]");
									
			}
					
		}while ( (tileNumber < 1 || tileNumber > 50 ) && isCancelledTileNumber == false );

	}
	
	private void initializeGameAction() 
	{	
		JButton[] btnTools = new JButton[3];
		
		/*
		 * [0] Next Day
		 * [1] Register Promotion
		 * [2] Seed Store
		 * [3] Back
		 */
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(301, 10, 524, 104);
		buttonPanel.setLayout(new GridLayout(2,1));
		lowerPanel.add(buttonPanel);
		buttonPanel.repaint();
		buttonPanel.revalidate();

		
		for ( int i = 0; i < 3; i++ ) 
		{
			
			btnTools[i] = new JButton();
			buttonPanel.add(btnTools[i]);
			btnTools[i].setFocusable(false);
			//btnTools[i].setFont(new Font("MV Boli", Font.BOLD, 10));
			btnTools[i].addActionListener(this);
			btnTools[i].setVisible(true);
			btnTools[i].setEnabled(true);
			
		}
		
		btnTools[0].setText("Next Day");
		btnTools[0].addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createReport.updateReport("Proceeds to next day");
				
				//Check if game over
				checkGameOver();
				
				//Check if eligible to level up
				Player.levelUpEligible(promoteNotifCount);
				
				//Grow every crop planted
				Crop.growCrop();
				
				//Check if a plant is already harvestable
				tile.checkHarvestableStatus();
				
				//Check if may nabubulok na plant
				tile.checkWitherStatus();
				
				//Proceed to next day
				gameDay += 1;
				lblGameDay.setText("Day [ "+gameDay+" ]");
				
			}
		});
		
		btnTools[1].setText("Register For Promotion");
		btnTools[1].addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e ) 
			{
				Player.registerLevelUp();
			}
		});
				
		btnTools[2].setText("< Back >");
		btnTools[2].addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				buttonPanel.setVisible(false);
				playerActionPanel.setVisible(true);
				playerBtnPlayerAction.setVisible(true);
				playerBtnGameAction.setVisible(true);
			}
		});

		
	}

	/**
	 * Choices of the player for the seeds,tools,and actions.
	 * @param tileNumber - The tile number chosen by the player.
	 */
	public void playerChoice(int tileNumber) 
	{
		Object chosenAction = null;
		
		String[] listOfActions1 = {"Plow Tool [0 ObjectCoins]", "Shovel Tool [7 ObjectCoins]", "Pickaxe Tool [50 ObjectCoins]", "Harvest Plant", "Plant Seed", "Cancel"};
		String[] listOfActions2 = {"Watering Can [0 ObjectCoins]", "Fertilizer [10 ObjectCoins]","Plow Tool [0 ObjectCoins]", "Shovel Tool [7 ObjectCoins]", "Pickaxe Tool [50 ObjectCoins]", "Harvest Plant", "Plant Seed", "Cancel"};
		
		
		if( buttonTiles[tileNumber].isEnabled() == true && isPlowedTiles[tileNumber] == 1 ) 
		{
			chosenAction = JOptionPane.showInputDialog(playerActionPanel, "Choose tool/action", "Player Action", JOptionPane.QUESTION_MESSAGE, null, listOfActions2, listOfActions2[0]);
		
			if ( chosenAction != null ) 
			{
				switch( chosenAction.toString() )
				{
					case "Watering Can [0 ObjectCoins]":
						Tile.checkWaterStatus(tileNumber);
						break;
					case "Fertilizer [10 ObjectCoins]":
						Tile.checkFertilizeStatus(tileNumber);
						break;
					case "Plow Tool [0 ObjectCoins]":
						tile.checkPlowStatus(tileNumber);
						break;
					case "Shovel Tool [7 ObjectCoins]":
						tile.checkRemoveWitherStatus(tileNumber);
						break;
					case "Pickaxe Tool [50 ObjectCoins]":
						tile.checkRemoveRockStatus(tileNumber);
						break;
					case "Harvest Plant":
						tile.checkHarvestStatus(tileNumber);
						break;
					case "Plant Seed":
						tile.checkPlantStatus(tileNumber);
						break;
					default:
						break;
				}		
			}

			
		}
		else 
		{
			chosenAction = JOptionPane.showInputDialog(playerActionPanel, "Choose tool/action", "Player Action", JOptionPane.QUESTION_MESSAGE,  null, listOfActions1, listOfActions1[0]);
			
			if ( chosenAction != null ) 
			{
				switch( chosenAction.toString() )
				{
					case "Plow Tool [0 ObjectCoins]":
						tile.checkPlowStatus(tileNumber);
						break;
					case "Shovel Tool [7 ObjectCoins]":
						tile.checkRemoveWitherStatus(tileNumber);
						break;
					case "Pickaxe Tool [50 ObjectCoins]":
						tile.checkRemoveRockStatus(tileNumber);
						break;
					case "Harvest Plant":
						tile.checkHarvestStatus(tileNumber);
						break;
					case "Plant Seed":
						tile.checkPlantStatus(tileNumber);
						break;
					default:
						break;
				}				
			}

		}
		
	}
	
	private void generateRockTiles(boolean isReset) throws IOException 
	{
		
		if ( isReset == false ) 
		{
			File file = new File("src/RockedTile.txt");
			
			
			try 
			{
				file.createNewFile();
				
			} catch ( Exception e) {
				
				e.printStackTrace();
			}
		
			
			FileWriter writer = new FileWriter("src/RockedTile.txt");

			Random rand = new Random();
			
		
			//Generate how many rocked tiles will be there
			int rockedTiles;
			
			do 
			{
				rockedTiles  = rand.nextInt(30 - 10 + 1) + 10;
			}
			while ( rockedTiles <= 10 );
			
			int tempLocationRockedTiles = 0;
			
			//Generate Random Location of the rocked tiles
			for ( int x = 0; x <= rockedTiles; x++) 
			{
				//Generating random rocked tiles to 0 to 50 tiles
				tempLocationRockedTiles = (int)((Math.random()*50) + 1);
				
				writer.write(String.valueOf(tempLocationRockedTiles)+"\n");
			}		
			
			writer.close();
			
			try (BufferedReader reader = new BufferedReader(new FileReader("src/RockedTile.txt"))) {
				String fileContent = null;
				int locationRockedTiles = 0;
				
				while ( (fileContent = reader.readLine()) != null ) 
				{
					locationRockedTiles = Integer.parseInt(fileContent);
					buttonTiles[locationRockedTiles].setFont(new Font("MV Boli", Font.BOLD, 21));
					buttonTiles[locationRockedTiles].setBackground(new Color(169,169,169));
					isRockedTiles[locationRockedTiles] = 1;
					isPlowedTiles[locationRockedTiles] = 0;
					MyFarm.buttonTiles[locationRockedTiles].setToolTipText("Tile has rock/s");
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else 
		{
		
			new Player("Farmer", 100, 0, 0, 0, 0, 0, 0);
			
			gameDay = 1;
			lblGameDay.setText("Day [ "+gameDay+" ]");
			
			GameStatus.reportList.setText("");
			
			reportStatusPanel.repaint();
		
			for ( int i = 0; i < 51; i++ ) 
			{
				if ( isRockedTiles[i] == 1 || 
					 isPlowedTiles[i] == 1 || 
					 Tile.hasPlants[i] == 1 ) 
				{
					buttonTiles[i].setBackground(new Color(65,36,11));
					isRockedTiles[i] = 0;
					isPlowedTiles[i] = 0;
					buttonTiles[i].setEnabled(false);
					
					Crop.cropAge[i] = 0;
					Crop.water[i] = 0;
					Crop.fertilizer[i] = 0;
					plantedSeed.set(i, null);
					buttonTiles[i].setToolTipText(null);
					
					//Remove ActionListener for the tile para hindi mag repeat ung pop up dahil sa panibagong actionListener
					for ( ActionListener d : MyFarm.buttonTiles[i].getActionListeners() ) 
					{
						MyFarm.buttonTiles[i].removeActionListener(d);
					}
				}
			}
			
			FileWriter writer = new FileWriter("src/RockedTile.txt");

			Random rand = new Random();
			
		
			//Generate how many rocked tiles will be there
			int rockedTiles;
			do 
			{
				rockedTiles  = rand.nextInt(30 - 10 + 1) + 10;
			}
			while ( rockedTiles <= 10 );
			
			int tempLocationRockedTiles = 0;
			
			//Generate Random Location of the rocked tiles
			for ( int x = 0; x < rockedTiles; x++) 
			{
				//Generating random rocked tiles to 0 to 50 tiles
				tempLocationRockedTiles = (int)((Math.random()*50) + 1);
				
				writer.write(String.valueOf(tempLocationRockedTiles)+"\n");
			}		
			
			writer.close();
			
			try (BufferedReader reader = new BufferedReader(new FileReader("src/RockedTile.txt"))) {
				String fileContent = null;
				int locationRockedTiles = 0;
				
				while ( (fileContent = reader.readLine()) != null ) 
				{
					locationRockedTiles = Integer.parseInt(fileContent);
					buttonTiles[locationRockedTiles].setFont(new Font("MV Boli", Font.BOLD, 21));
					buttonTiles[locationRockedTiles].setBackground(new Color(169,169,169));
					isRockedTiles[locationRockedTiles] = 1;
					isPlowedTiles[locationRockedTiles] = 0;
					MyFarm.buttonTiles[locationRockedTiles].setToolTipText("Tile has rock/s");
					
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Player.initializePlayerStatus();
		}

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
	
	private void checkGameOver() 
	{
		String[] option = {"New Game", "Exit Game"};
		int userChoice;
		
		int countWitheredPlants = 0;
		int countPlantedPlants = 0;
		
		for ( int i = 0 ; i < 51 ; i++ ) 
		{
			if ( isWitheredTiles[i] > 0 ) 
			{
				countWitheredPlants += 1;
			}
			
			if ( Tile.hasPlants[i] > 0 ) 
			{
				countPlantedPlants += 1;
			}
		}

		if ( ( Player.objectCoin < 5 && countPlantedPlants == 0 ) || (Player.objectCoin < 7 && countWitheredPlants >= 50 )  ) 
		{
			
			ImageIcon howToPlay = new ImageIcon("src/HowToPlay.png");
			
			userChoice = JOptionPane.showOptionDialog(null, null, "MyFarm | Game Over |", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, howToPlay, option, option[0]);
			
			switch( userChoice ) 
			{
				case 0:
					try {
						generateRockTiles(true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				default:
					myFarm.dispose();
			}
		}

		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	
}

