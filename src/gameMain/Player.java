package gameMain;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gameSettings.GameStatus;

/**
 * The Player class is used to initialize the status of the player, updates player's XP and level and if the player wants to level up from his/her current level status
 */

/**
 * 
 * @author Lopez, Mauries 
 * 		   Rojo, Kate Lynn
 * 
 * @version 12/10/2022
 *
 */

public class Player {

	// Player's statistics

	/**
	 * the number of coins the player has from the start of the game.
	 */
	public static double objectCoin = 100;

	/**
	 * current player's XP.
	 */
	public static double curPlayerXP = 0;

	/**
	 * the farmer type of the player.
	 */
	public static String playerType;

	/**
	 * the level of the player.
	 */
	private static int playerLevel = 0;

	/**
	 * the bonus produced by the player.
	 */
	public static int bonusProduced;
	/**
	 * the cost of the seed reduced by the player.
	 */
	public static int seedCostReduc;

	/**
	 * the water bonus limit.
	 */
	public static int waterBonusLimit;

	/**
	 * the fertilizer bonus limit.
	 */
	public static int fertilizerBonusLimit;

	/**
	 * the registration fee.
	 */
	public static int regFee;

	/**
	 * the registration choice.
	 */
	private static int regChoice;

	/**
	 * 
	 * This is the constructor of the Player class.
	 *
	 * @param playerType the farmer type of the player.
	 * @param objectCoin the number of coins the player has.
	 * @param playerXP the player's XP.
	 * @param bonusProduced the bonus produced by the player.
	 * @param seedCostReduc the cost of the seed reduced by the player.
	 * @param waterBonusLimit the water bonus limit.
	 * @param fertilizerBonusLimit the fertilizer bonus limit.
	 * @param regFee the registration fee.
	 */

	public Player( String playerType, int objectCoin, double playerXP, int bonusProduced, int seedCostReduc, int waterBonusLimit, int fertilizerBonusLimit, int regFee) {
		Player.objectCoin = objectCoin;
		Player.curPlayerXP = playerXP;
		Player.playerType = playerType;
		Player.bonusProduced = bonusProduced;
		Player.seedCostReduc = seedCostReduc;
		Player.waterBonusLimit = waterBonusLimit;
		Player.fertilizerBonusLimit = fertilizerBonusLimit;
		Player.regFee = regFee;
		Player.playerLevel = (int) getLevel(curPlayerXP);
	}

	/**
	 * This is the default constructor of the Player class.
	 */
	public Player() 
	{
		
	}

	JLabel[] player = new JLabel[4];

	/**
	 * This method is used to initialize the player's status.
	 */
	
	public static void initializePlayerStatus() 
	{
		/*
		 * Farmer Type
		 * Level
		 * XP
		 * Object Coin
		 */
		MyFarm.playerTypeLabel.setText("Type: ["+playerType+"]");
		MyFarm.playerTypeLabel.setBounds(10,19,125,14);
		MyFarm.playerStatusPanel.add(MyFarm.playerTypeLabel);
			
		MyFarm.playerLevelLabel.setText("Level: ["+playerLevel+"]");
		MyFarm.playerLevelLabel.setBounds(10,34, 125,14);
		MyFarm.playerStatusPanel.add(MyFarm.playerLevelLabel);
			
		MyFarm.playerXPLabel.setText("XP: ["+curPlayerXP+"]");
		MyFarm.playerXPLabel.setBounds(10,50,125,14);
		MyFarm.playerStatusPanel.add(MyFarm.playerXPLabel);
			
		MyFarm.playerObjectCoinLabel.setText("ObjectCoin: ["+objectCoin+"]");
		MyFarm.playerObjectCoinLabel.setBounds(10,66,125,14);
		MyFarm.playerStatusPanel.add(MyFarm.playerObjectCoinLabel);

	}

	/**
	 * This method is used to update the player's XP and level.
	 */
	public static void updateXPAndLevel() 
	{
		playerLevel = (int) Player.getLevel(curPlayerXP);
	}
	
	/**
	 * checks if player is in the right level(s) to be promoted for next farmer type
	 */
	public static void checkLevelUp()
	{

		updateXPAndLevel();
		
		if ( playerLevel >= 5  && Player.playerType.equals("Farmer") )
		{
			setPlayerStats("Registered Farmer", 1, 1, 0, 0);
		}
		else if ( playerLevel >= 10 && Player.playerType.equals("Registered Farmer") )
		{
			setPlayerStats("Distinction Farmer", 2, 2, 1, 0);
		}
		else if ( playerLevel >= 15 && Player.playerType.equals("Distinction Farmer"))
		{
			setPlayerStats("Legendary Farmer", 4, 3, 2, 1);
		}


	}

	/**
	 * Used if the player wants to level up from his/her current level status.
	 */
	public static void registerLevelUp()
	{

		int reqLevelPromote = 0;

		if ( Player.playerType.equals("Farmer") )
		{
			reqLevelPromote = 5;
			Player.regFee = 200;
		}
		else if ( Player.playerType.equals("Registered Farmer") )
		{
			reqLevelPromote = 10;
			Player.regFee = 300;
		}
		else if ( Player.playerType.equals("Distinction Farmer") )
		{
			reqLevelPromote = 15;
			Player.regFee = 400;
		}
		else
		{
			Player.regFee = 0;
		}
		
		String[] choices = {"Promote", "Cancel"};
		
		if ( Player.playerType != "Legendary Farmer") 
		{
			regChoice = JOptionPane.showOptionDialog(MyFarm.playerActionPanel, "Promotion from [" +Player.playerType+ "] requires [" +Player.regFee+"] Object coins to be promoted", "Registration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		}
		else 
		{
			GameStatus createReport = new GameStatus();
			createReport.updateReport("Reached Max Level. Promotion Denied!");
		}
		

		if ( regChoice == 0 && Player.objectCoin >= Player.regFee && Player.playerLevel >= reqLevelPromote )
		{
			Player.objectCoin -= Player.regFee;
			initializePlayerStatus();
			checkLevelUp();

		}
		else if ( regChoice == 0 && Player.objectCoin < Player.regFee )
		{
			JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Required Object Coin: ["+ Player.regFee+"]\nCurrent Object Coin: ["+Player.objectCoin+"]\nYou need ["+ (Player.regFee - Player.objectCoin)+ "] more Object coins to register", "Registration", JOptionPane.INFORMATION_MESSAGE);
		}
		else if ( regChoice == 0 && Player.playerLevel < reqLevelPromote )
		{
			JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Required Level: ["+ reqLevelPromote +"]\nCurrent Level: ["+Player.playerLevel+"]\nYou need ["+ (reqLevelPromote - Player.playerLevel)+ "] more level/s to register", "Registration", JOptionPane.INFORMATION_MESSAGE);
		}
		
		

	}

	/**
	 * Notify the player if he/she is eligible to be promoted
	 *
	 * @param promoteNotifCount if what type of farmer is the player eligible to be promoted to
	 *
	 */
	public static void levelUpEligible(int promoteNotifCount)
	{
		if ( playerLevel == 5 && Player.playerType.equals("Farmer") && promoteNotifCount == 0 )
		{
			JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Your level is now eligible for promotion as a [Regsitered Farmer]", "Registration Notice", JOptionPane.INFORMATION_MESSAGE);
			MyFarm.promoteNotifCount = 1;
		}
		else if ( playerLevel == 10 && ( Player.playerType.equals("Registered Farmer") || Player.playerType.equals("Farmer")) && promoteNotifCount == 1 )
		{
			JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Your level is now eligible for promotion as a [Distinction Farmer]", "Registration Notice", JOptionPane.INFORMATION_MESSAGE);
			MyFarm.promoteNotifCount = 2;
		}
		else if ( playerLevel == 15 && ( Player.playerType.equals("Distinction Farmer") || Player.playerType.equals("Registered Farmer") || Player.playerType.equals("Farmer") ) && promoteNotifCount == 2 )
		{
			JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Your level is now eligible for promotion as a [Legendary Farmer]", "Registration Notice", JOptionPane.INFORMATION_MESSAGE);
			MyFarm.promoteNotifCount = 3;
		}

	}

	/**
	 * Notifies the player of the promotion and see the given bonuses
	 *
	 * @param farmerType Promotion Farmer Type
	 * @param bonusProduced Bonus produced for each crop harvested
	 * @param seedCostReduc Seed cost reduction when buying seeds
	 * @param waterBonusLimit Bonus water limit
	 * @param fertilizerBonusLimit Bonus fertilizer limit
	 */
	private static void setPlayerStats(String farmerType, int bonusProduced, int seedCostReduc, int waterBonusLimit, int fertilizerBonusLimit )
	{
		Player.playerType = farmerType;
		Player.bonusProduced = bonusProduced;
		Player.seedCostReduc = seedCostReduc;
		Player.waterBonusLimit = waterBonusLimit;
		Player.fertilizerBonusLimit = fertilizerBonusLimit;

		JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Congratulations!\nYou are now a ["+farmerType+"]\n\nBonuses:\nBonus Produced: ["+bonusProduced+"]\nSeed Cost Reduction: ["+seedCostReduc+"]\nWater Bonus Limit: ["+waterBonusLimit+"]\nFertilizer Bonus Limit: ["+fertilizerBonusLimit+"]", "Registration", JOptionPane.INFORMATION_MESSAGE);
		
		initializePlayerStatus();
	}


	/**
	 * Returns the player's level
	 * 
	 * @param curPlayerXP2 Current player's XP
	 * @return curPlayerXP2(double)
	 */
	public static double getLevel(double curPlayerXP2)
	{
		return curPlayerXP2 / 100;
	}

	public String toString() {
		return "[" + Player.playerType + " | Objectcoin: " + Player.objectCoin + " | Player Level: " + playerLevel + " | Player XP: " + Player.curPlayerXP + "]";
	}

}
