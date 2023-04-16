package gameSettings;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gameMain.MyFarm;
import gameMain.Player;

/**
 *
 * @author Lopez, Mauries
 * 		   Rojo, Kate Lynn
 *
 * @version 12/10/2022
 *
 */

/**
 * The Tile class displays the status of the current tile.
 */
public class Tile{
	
	static Player player = new Player();
	ChooseSeed seedStore = new ChooseSeed();
	static GameStatus createReport = new GameStatus();
	String userChoice;

	/**
	 * Checks if the tile has a plant using an integer value
	 */
	public static int[] hasPlants = new int[51];

	/**
	 * Checks the status of the plant
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	public void checkPlantStatus( int tileNumber ) 
	{
		
		double playerTempCoin = Player.objectCoin;
		
		Crop boughtSeed = null;
		
		if ( Player.objectCoin >= 5 ) 
		{
			boughtSeed = seedStore.getSeed(tileNumber);	
		}
		else 
		{
			createReport.updateReport("Not Enough Object Coin. Buying Denied!");
			JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Not Enough Object Coin. Buying Denied!", "Player Action", JOptionPane.INFORMATION_MESSAGE);
		}
			
		//Checks if the user inputted a valid tile number and
		//Checks if the chosen button tile of the user is not null
		if ( tileNumber >= 0 && MyFarm.buttonTiles[tileNumber] != null && boughtSeed != null ) 
		{
			
			MyFarm.seedList.add(boughtSeed);	
			userChoice = boughtSeed.getName();
	
			//Check if it has rocks
			if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && MyFarm.isRockedTiles[tileNumber] == 1 && MyFarm.isPlowedTiles[tileNumber] == 0 ) 
			{
				createReport.updateReport("Tile has rocks!");
			}
			
			//Checks if the tile is plowed or not
			else if ( (MyFarm.buttonTiles[tileNumber].isEnabled() == false || MyFarm.buttonTiles[tileNumber].isEnabled() == true) && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 0 ) 
			{
				createReport.updateReport("Tile not plowed. Planting Denied!");
				//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Tile not plowed. Planting Denied!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
				
			}
			
			//Accept Action, if the tile is plowed and ready to be planted
			else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == true && MyFarm.plantedSeed.get(tileNumber) == null  && MyFarm.isPlowedTiles[tileNumber] == 1 && ChooseSeed.userChoiceInteger == 0 ) 
			{
				int i;
				for ( i = 0; i < MyFarm.seedList.size(); i++ ) 
				{
					// Gets the seed in the array list by picking the very FIRST same name in the seedList
					if ( MyFarm.seedList.get(i).getName().equals(userChoice) ) 
					{
						int index = i;
									
						// Checks if the tile is not yet planted
						if ( MyFarm.plantedSeed.get(tileNumber) == null )  
						{
							
							if ( !MyFarm.seedList.get(index).getCropType().equals("Fruit tree") ) 
							{

								plantSeed(tileNumber,index);
								
							}
							else 
							{
	
								if ( checkSurroundings(tileNumber) == true ) 
								{
									plantSeed(tileNumber,index);
								}
								else 
								{
									
									double tempCoin = playerTempCoin - Player.objectCoin;
									
									Player.objectCoin += tempCoin;
									
									
									Player.initializePlayerStatus();
									
									createReport.updateReport("Plant 1 tile away from a planted tile/withered tile/rocked tile/corner of the farm lot/far sides of a farm lot. Planting Denied!");
								}
								
							}
			
									
						}
											
					}
				}
				
			}
			// Deny Action, if the tile is not free
			else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && MyFarm.plantedSeed.get(tileNumber) != null && MyFarm.isPlowedTiles[tileNumber] == 0 )
			{
				createReport.updateReport("Tile has plants. Planting Denied!");
				//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Tile has plants. Planting Denied!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
			}
			else if ( MyFarm.plantedSeed.get(tileNumber) != null && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 1 ) 
			{
				createReport.updateReport("Tile has a plant. Planting Denied!");
				//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Tile has a plant. Planting Denied!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
				
			}	
			
			
		}

		
	}
	
	
	@SuppressWarnings("static-access")
	private static void plantSeed(int tileNumber, int index) 
	{
		Player.updateXPAndLevel();
		Player.levelUpEligible(MyFarm.promoteNotifCount);
		
		//Plant the seed
		MyFarm.plantedSeed.set(tileNumber, MyFarm.seedList.get(index));
				
		//Shows information of the planted seed of a tile when CLICKED
		MyFarm.buttonTiles[tileNumber].addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				MyFarm.plantedSeed.get(tileNumber);
				MyFarm.plantedSeed.get(tileNumber);
				//Show a pop-up when clicked
				JOptionPane.showMessageDialog(MyFarm.playerActionPanel, 
											  "Plant Status\nName: "+MyFarm.plantedSeed.get(tileNumber).getName()+
											  "\nAge: "+MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber)+
											  "\nWater: ["+Crop.getWater(tileNumber)+
											  "/"+MyFarm.plantedSeed.get(tileNumber).getWaterNeeded()+"]"+
											  "\nFertilizer: ["+Crop.getFertilizer(tileNumber)+
											  "/"+MyFarm.plantedSeed.get(tileNumber).getFertilizerNeeded()+"]", "Tile #["+tileNumber+"]", JOptionPane.INFORMATION_MESSAGE);
				}
					
			});
		
		
		if ( MyFarm.plantedSeed.get(tileNumber) != null ) 
		{
			MyFarm.buttonTiles[tileNumber].setToolTipText( "Plant Status: [Name: "+MyFarm.plantedSeed.get(tileNumber).getName()+"]"); 
		}
					
		//Shows message that the seed has been successfully planted
		createReport.updateReport("["+MyFarm.plantedSeed.get(tileNumber).getName()+"] planted successfully!");
		JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "["+MyFarm.plantedSeed.get(tileNumber).getName()+"] planted successfully!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
		
		//Put a plant in the tile
		hasPlants[tileNumber] = 1;
		
		//Ask if to water or fertilize the planted plant
		int chosenAction = 0;
		String[] plantedAction = {"Watering Can", "Fertilizer", "Cancel"};
		
		chosenAction = JOptionPane.showOptionDialog(MyFarm.playerActionPanel, "Choose an action", "Player Action", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, plantedAction, plantedAction[0]);
		
		switch ( chosenAction ) 
		{
			case 0:
				checkWaterStatus(tileNumber);
				break;
			case 1:
				checkFertilizeStatus(tileNumber);
				break;
			default:
				break;
		}	
		
		
	}


	/**
	 * Checks if the tile has been plowed or not
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	public void checkPlowStatus( int tileNumber ) 
	{
		
		if ( tileNumber >= 0 && MyFarm.buttonTiles[tileNumber] != null ) 
		{
			
			if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && MyFarm.isRockedTiles[tileNumber] == 1 ) 
			{
				createReport.updateReport("Tile has rocks!");
			}
			
			//Check if it has rocks
			else if ( (MyFarm.buttonTiles[tileNumber].isEnabled() == false || MyFarm.buttonTiles[tileNumber].isEnabled() == true ) && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 0 ) 
			{
				createReport.updateReport("Tile plowed successfully!");
				//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Tile plowed successfully", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
				MyFarm.buttonTiles[tileNumber].setEnabled(true);
				MyFarm.buttonTiles[tileNumber].setBackground(new Color(193,154,107));
				Player.curPlayerXP += 0.5;
				MyFarm.isPlowedTiles[tileNumber] = 1;
				
				MyFarm.buttonTiles[tileNumber].setToolTipText("Tile plowed");
				
				//Update Player Status
				Player.updateXPAndLevel();
				Player.levelUpEligible(MyFarm.promoteNotifCount);
				Player.initializePlayerStatus();
				
			}
			else if ( MyFarm.isPlowedTiles[tileNumber] == 1 )//Check if plowed already
			{
				createReport.updateReport("Tile already plowed");
				//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Tile already plowed", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
			}	
			
			
		}

		
		
	}

	/**
	 * Checks water status of the planted crop in the tile.
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	@SuppressWarnings("static-access")

	public static void checkWaterStatus( int tileNumber ) 
	{
		
	
		if ( tileNumber >= 0 && MyFarm.buttonTiles[tileNumber] != null && MyFarm.plantedSeed.get(tileNumber) != null ) 
		{
			
			//Check if it has rocks
			if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && MyFarm.isRockedTiles[tileNumber] == 1 && MyFarm.isPlowedTiles[tileNumber] == 0) 
			{
				createReport.updateReport("Tile has rocks!");
			}
			
			//If plowed and has plants and not withered
			else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == true && MyFarm.plantedSeed.get(tileNumber).getIsWithered() == false && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 1)
			{
				Crop.addWater(tileNumber);
				
				//Add Exp
				Player.curPlayerXP += 0.5;
				
				//Display success water in the game status
				createReport.updateReport("Crop Watered Successfully!");
				
				//Update Player Status
				Player.updateXPAndLevel();
				Player.levelUpEligible(MyFarm.promoteNotifCount);
				Player.initializePlayerStatus();
			}
			
			//Check if the tile has no planted seed
			else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == true && MyFarm.plantedSeed.get(tileNumber) == null && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 1 ) 
			{
				createReport.updateReport("No seed is planted. Watering Denied!");
				//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "No seed is planted", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
			} else {
				MyFarm.plantedSeed.get(tileNumber);
				MyFarm.plantedSeed.get(tileNumber);
				if ( ( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) > MyFarm.plantedSeed.get(tileNumber).getHarvestDay() ) || 
						( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) == MyFarm.plantedSeed.get(tileNumber).getHarvestDay() && 
						( Crop.getWater(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getWaterNeeded() || 
						  Crop.getFertilizer(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getFertilizerNeeded() ) ) )
				{
					createReport.updateReport("Plant withered. Watering Denied!");
					//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Plant is withered. Watering Denied!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
				}
				
				else if ( (MyFarm.buttonTiles[tileNumber].isEnabled() == false || MyFarm.buttonTiles[tileNumber].isEnabled() == true ) && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 0 ) 
				{
					createReport.updateReport("Tile not plowed. Watering Denied!");
					//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Tile not plowed. Watering Denied!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
				}
			}
				
		}
		else
		{
			createReport.updateReport("Tile has no planted seed / has rocks / not plowed. Watering Denied!");
		}
		
	}
	/**
	 * Checks if the plant in tile can be fertilized.
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	@SuppressWarnings("static-access")

	public static void checkFertilizeStatus( int tileNumber ) 
	{
		
		if ( tileNumber >= 0 && MyFarm.buttonTiles[tileNumber] != null && MyFarm.plantedSeed.get(tileNumber) != null ) 
		{

			//Check if it has rocks
			if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && MyFarm.isRockedTiles[tileNumber] == 1 && MyFarm.isPlowedTiles[tileNumber] == 0 ) 
			{
				createReport.updateReport("Tile has rocks!");
			}
			
			//If plowed and has plants and not withered
			else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == true && MyFarm.plantedSeed.get(tileNumber).getIsWithered() == false && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 1 && Player.objectCoin >= 10 )
			{
				Crop.addFertilizer(tileNumber);
				
				Player.curPlayerXP += 4;
				Player.objectCoin -= 10;
				
				//Display success water in the game status
				createReport.updateReport("Crop Fertilized Successfully!");
				
				//Update Player Status
				Player.updateXPAndLevel();
				Player.levelUpEligible(MyFarm.promoteNotifCount);
				Player.initializePlayerStatus();
			}
			else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == true && MyFarm.plantedSeed.get(tileNumber).getIsWithered() == false && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 1 && Player.objectCoin < 10 ) 
			{
				createReport.updateReport("Not Enough ObjectCoins. Fertilizing Denied!");
			}
			//Check if the tile has no planted seed
			else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == true && MyFarm.plantedSeed.get(tileNumber) == null && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 1  ) 
			{
				createReport.updateReport("No seed is planted. Fertilizing Denied!");
				//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "No seed is planted. Fertilizing Denied!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
			} else {
				MyFarm.plantedSeed.get(tileNumber);
				MyFarm.plantedSeed.get(tileNumber);
				if ( ( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) > MyFarm.plantedSeed.get(tileNumber).getHarvestDay() ) || 
						( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) == MyFarm.plantedSeed.get(tileNumber).getHarvestDay() && 
						( Crop.getWater(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getWaterNeeded() || 
						  Crop.getFertilizer(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getFertilizerNeeded() ) ) )
				{
					createReport.updateReport("Plant withered. Fertilizing Denied!");
					//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Plant is withered. Fertilizing Denied!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
				}
				
				//Tile is unplowed. Allow watering
				else if ( ( MyFarm.buttonTiles[tileNumber].isEnabled() == false || MyFarm.buttonTiles[tileNumber].isEnabled() == true ) && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 0 ) 
				{
					createReport.updateReport("Tile not plowed. Fertilizing Denied!");
					//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Tile not plowed. Fertilizing Denied!", "Tile Status", JOptionPane.INFORMATION_MESSAGE);
				}
			}	
		}	
		else
		{
			createReport.updateReport("Tile has no planted seed / has rocks / not plowed. Fertilizing Denied!");
		}

		
	}

	/**
	 * Check if the tile has a rock.
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	public void checkRemoveRockStatus( int tileNumber ) 
	{
		if ( MyFarm.buttonTiles[tileNumber] != null ) 
		{
			if ( MyFarm.isRockedTiles[tileNumber] == 1 && Player.objectCoin >= 50 ) 
			{
				MyFarm.isRockedTiles[tileNumber] = 0;
				MyFarm.buttonTiles[tileNumber].setBackground(new Color(65,36,11));
				MyFarm.buttonTiles[tileNumber].setVisible(true);
				MyFarm.buttonTiles[tileNumber].setFocusable(false);
				
				Player.objectCoin -= 50;
				Player.curPlayerXP += 15;
				
				Player.updateXPAndLevel();
				Player.levelUpEligible(MyFarm.promoteNotifCount);
				Player.initializePlayerStatus();
				
				createReport.updateReport("Rock removed successfully!");
			}
			else if ( MyFarm.isRockedTiles[tileNumber] == 1 && Player.objectCoin < 50  )
			{
				createReport.updateReport("Not Enough ObjectCoins. Removing Denied!");
			}
			else 
			{
				createReport.updateReport("Tile has no rocks. Removing Denied!");
			}
		}
		
	}

	/**
	 * Check if the status of the crop in the tile can be harvested.
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	@SuppressWarnings("static-access")

	public void checkHarvestStatus( int tileNumber ) 
	{
		if ( tileNumber >= 0 && MyFarm.buttonTiles[tileNumber] != null && MyFarm.plantedSeed.get(tileNumber) != null ) 
		{
			//Check if it has rocks
			if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && MyFarm.isRockedTiles[tileNumber] == 1 && MyFarm.isPlowedTiles[tileNumber] == 0 ) 
			{
				createReport.updateReport("Tile has rocks!");
			} else {
				MyFarm.plantedSeed.get(tileNumber);
				MyFarm.plantedSeed.get(tileNumber);
				if ( 
							MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) == MyFarm.plantedSeed.get(tileNumber).getHarvestDay() && 
							MyFarm.plantedSeed.get(tileNumber).getIsWithered() == false &&
							Crop.getWater(tileNumber) >= MyFarm.plantedSeed.get(tileNumber).getWaterNeeded() &&
							Crop.getFertilizer(tileNumber) >= MyFarm.plantedSeed.get(tileNumber).getFertilizerNeeded()
						) 
				{
					
					JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "["+MyFarm.plantedSeed.get(tileNumber).getName()+"] Harvested Successfully!");
					MyFarm.buttonTiles[tileNumber].setEnabled(false);
					MyFarm.isPlowedTiles[tileNumber] = 0;
					
					//Reset the age, water, and fertilizer of the crop
					Crop.cropAge[tileNumber] = 0;
					Crop.water[tileNumber] = 0;
					Crop.fertilizer[tileNumber] = 0;
					
					//How many produced crop product/s after harvest
					Player.updateXPAndLevel();
					updateCoinAndXP(tileNumber);
					createReport.updateReport("["+ Crop.getProducedCrop() + "x "+MyFarm.plantedSeed.get(tileNumber).getName()+"] harvested Succesfully!");
					
					//Update Player Status
					Player.levelUpEligible(MyFarm.promoteNotifCount);
					Player.initializePlayerStatus();
					
					//Remove the seed from being planted
					MyFarm.plantedSeed.set(tileNumber, null);
					
					//Remove tooltip for the plant
					MyFarm.buttonTiles[tileNumber].setToolTipText(null);
					
					MyFarm.buttonTiles[tileNumber].setBackground(new Color(65,36,11));
					
					
					
					//Removes ActionListener for the tile so that it won't repeat the pop-up because of the new actionListener
					for ( ActionListener d : MyFarm.buttonTiles[tileNumber].getActionListeners() ) 
					{
						MyFarm.buttonTiles[tileNumber].removeActionListener(d);
					}
					
				} else {
					MyFarm.plantedSeed.get(tileNumber);
					MyFarm.plantedSeed.get(tileNumber);
					if ( ( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) > MyFarm.plantedSeed.get(tileNumber).getHarvestDay() ) || 
							( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) == MyFarm.plantedSeed.get(tileNumber).getHarvestDay() && 
							( Crop.getWater(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getWaterNeeded() || 
							  Crop.getFertilizer(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getFertilizerNeeded() ) ) )
					{
						createReport.updateReport("Plant Withered. Not Enough Water/Fertilizer. Harvesting Denied!");
					}
					
					else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 0  ) 
					{
						createReport.updateReport("Tile not plowed. Harvesting Denied!");
						//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Tile not plowed. Harvesting Denied!");
					}

					//Deny Action, if it is not yet at the right age
					else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == true && MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getHarvestDay()  && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 1  )
					{
						createReport.updateReport("Plant is still growing. Harvesting Denied!");
						//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Plant is still growing. Harvesting Denied!");
					}
					
					//Deny Action, if the crop is withered
					else if ( MyFarm.plantedSeed.get(tileNumber).getIsWithered() == true && MyFarm.isRockedTiles[tileNumber] == 0 && MyFarm.isPlowedTiles[tileNumber] == 1  )
					{
						createReport.updateReport("Plant Withered. Harvesting Denied!");
						//JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "Plant Withered. Harvesting Denied!");
					}
				}
			}
					
		}
		else 
		{
			createReport.updateReport("Tile has no planted seed / has rocks / not plowed. Harvesting Denied!");
		}

		
	}
	/**
	 * Check if the status of the crop in the tile is withered.
	 */
	@SuppressWarnings("static-access")

	public void checkWitherStatus() 
	{
			
		for ( int i = 0; i < MyFarm.plantedSeed.size(); i++ ) 
		{
			
			//Check all the planted tiles if it got withered
			if ( MyFarm.plantedSeed.get(i) != null ) 
			{
				MyFarm.plantedSeed.get(i);
				MyFarm.plantedSeed.get(i);
				//If the planted crop is older than its harvest age. Change properties
				if( ( MyFarm.plantedSeed.get(i).getCropAge(i) > MyFarm.plantedSeed.get(i).getHarvestDay() ) || ( MyFarm.plantedSeed.get(i).getCropAge(i) == MyFarm.plantedSeed.get(i).getHarvestDay() && ( Crop.getWater(i) < MyFarm.plantedSeed.get(i).getWaterNeeded() || Crop.getFertilizer(i) < MyFarm.plantedSeed.get(i).getFertilizerNeeded() ) ) )
				{
					MyFarm.buttonTiles[i].setToolTipText("Plant got withered");
					MyFarm.buttonTiles[i].setBackground(new Color(10,10,10));
					MyFarm.buttonTiles[i].setEnabled(false);
					
					MyFarm.isWitheredTiles[i] = 1;
					createReport.updateReport("A Plant Withered!");
				}
				
			}
		}

	}

	/**
	 *  Check if the status of the crop in the tile is ready to be harvested.
	 */
	public void checkHarvestableStatus() 
	{
		for ( int i = 0; i < MyFarm.plantedSeed.size(); i++ ) 
		{
			//Check all the planted tiles if it got withered
			if ( MyFarm.plantedSeed.get(i) != null ) 
			{
				//If the planted crop is older than its harvest age. Change properties
				if( MyFarm.plantedSeed.get(i).getCropAge(i) == MyFarm.plantedSeed.get(i).getHarvestDay() ) 
				{
					MyFarm.buttonTiles[i].setToolTipText("Plant is harvestable");
					MyFarm.buttonTiles[i].setBackground(new Color(30, 152, 26));
					MyFarm.plantedSeed.get(i).setIsHarvestable(true);	
				}
				
			}
	
		}
	}

	/**
	 * Check if the tile requires to use a shovel.
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	@SuppressWarnings("static-access")

	public void checkRemoveWitherStatus(int tileNumber) 
	{
		
		if ( MyFarm.buttonTiles[tileNumber] != null && MyFarm.plantedSeed.get(tileNumber) != null ) 
		{
			MyFarm.plantedSeed.get(tileNumber);
			MyFarm.plantedSeed.get(tileNumber);
			if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && 
			   ( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) > MyFarm.plantedSeed.get(tileNumber).getHarvestDay() ) || 
			   ( ( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) == MyFarm.plantedSeed.get(tileNumber).getHarvestDay()  ) && 
				  Crop.getWater(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getWaterNeeded() ||
				  Crop.getFertilizer(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getFertilizerNeeded() ) &&
			     Player.objectCoin >= 7 ) 
			{
				
				JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "[Withered "+MyFarm.plantedSeed.get(tileNumber).getName()+"] Removed Successfully!");
				MyFarm.buttonTiles[tileNumber].setBackground(new Color(65,36,11));
				MyFarm.plantedSeed.get(tileNumber).setIsHarvestable(false);
				MyFarm.plantedSeed.get(tileNumber).setIsWithered(false);
				MyFarm.plantedSeed.set(tileNumber, null);
				
				MyFarm.isPlowedTiles[tileNumber] = 0;
				Crop.cropAge[tileNumber] = 0;
				Crop.water[tileNumber] = 0;
				Crop.fertilizer[tileNumber] = 0;
				
				hasPlants[tileNumber] = 0;
				MyFarm.isWitheredTiles[tileNumber] = 0;
				
				MyFarm.buttonTiles[tileNumber].setToolTipText(null);
				
				Player.objectCoin -= 7;
				Player.curPlayerXP += 2;
				
				//Update Player Status
				Player.updateXPAndLevel();
				Player.levelUpEligible(MyFarm.promoteNotifCount);
				Player.initializePlayerStatus();
				
				//Removes ActionListener for the tile so that it won't repeat the pop-up because of the new actionListener
				for ( ActionListener d : MyFarm.buttonTiles[tileNumber].getActionListeners() ) 
				{
					MyFarm.buttonTiles[tileNumber].removeActionListener(d);
				}
			} else {
				MyFarm.plantedSeed.get(tileNumber);
				MyFarm.plantedSeed.get(tileNumber);
				if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false && 
						( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) > MyFarm.plantedSeed.get(tileNumber).getHarvestDay() ) || 
						( ( MyFarm.plantedSeed.get(tileNumber).getCropAge(tileNumber) == MyFarm.plantedSeed.get(tileNumber).getHarvestDay()  ) && 
						  Crop.getWater(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getWaterNeeded() ||
						  Crop.getFertilizer(tileNumber) < MyFarm.plantedSeed.get(tileNumber).getFertilizerNeeded() ) &&
						  Player.objectCoin < 7 )
				{
					createReport.updateReport("Not Enough ObjectCoins. Shovel tool usage Denied!");
				}
				else 
				{
					
					JOptionPane.showMessageDialog(MyFarm.playerActionPanel, "["+MyFarm.plantedSeed.get(tileNumber).getName()+"] removed successfully w/ a shovel!");
					
					MyFarm.buttonTiles[tileNumber].setBackground(new Color(65,36,11));
					MyFarm.buttonTiles[tileNumber].setEnabled(false);
					MyFarm.plantedSeed.set(tileNumber, null);
					MyFarm.isPlowedTiles[tileNumber] = 0;
					Crop.cropAge[tileNumber] = 0;
					Crop.water[tileNumber] = 0;
					Crop.fertilizer[tileNumber] = 0;
					
					MyFarm.buttonTiles[tileNumber].setToolTipText(null);
					
					Player.objectCoin -= 7;
					
					//Update Player Status
					Player.updateXPAndLevel();
					Player.levelUpEligible(MyFarm.promoteNotifCount);
					Player.initializePlayerStatus();
					
					for ( ActionListener d : MyFarm.buttonTiles[tileNumber].getActionListeners() ) 
					{
						MyFarm.buttonTiles[tileNumber].removeActionListener(d);
					}
					
				}
			}
		}
		else if ( MyFarm.buttonTiles[tileNumber].isEnabled() == false || MyFarm.isRockedTiles[tileNumber] == 1)
		{
			Player.objectCoin -= 7;
			
			Player.initializePlayerStatus();
			Player.updateXPAndLevel();
			Player.levelUpEligible(MyFarm.promoteNotifCount);
			
			createReport.updateReport("Shovel Tool Used!");
		}

	}
	
	/**
	 * Sets the coin and XP of the player
	 *
	 * @param cropSellCoin the coin of the crop
	 * @param cropSellXP the XP of the crop
	 */
	@SuppressWarnings("static-access")
	private static void setCoinAndXP(int cropSellCoin, double cropSellXP, int tileNumber )
	{
		double waterBonusCoin = 0.0, fertilizerBonusCoin = 0.0, tempHarvestCoin = 0.0, producedCropQuantity = Crop.getProducedCrop(), finalHarvestPrice = 0.0;

		tempHarvestCoin = producedCropQuantity * ( cropSellCoin + Player.bonusProduced ) ;
		MyFarm.plantedSeed.get(tileNumber);
		waterBonusCoin = tempHarvestCoin * 0.2 * ( Crop.getWater(tileNumber) - 1);
		MyFarm.plantedSeed.get(tileNumber);
		fertilizerBonusCoin = tempHarvestCoin * 0.5 * Crop.getFertilizer(tileNumber);
		finalHarvestPrice  = tempHarvestCoin + waterBonusCoin + fertilizerBonusCoin;
		Player.curPlayerXP = Player.curPlayerXP + ( cropSellXP * producedCropQuantity );

		if ( MyFarm.plantedSeed.get(tileNumber).getCropType().equals("Flower") )
		{
			finalHarvestPrice = finalHarvestPrice * 1.1;
		}

		Player.objectCoin = Player.objectCoin + finalHarvestPrice;
		
		Player.updateXPAndLevel();
		Player.initializePlayerStatus();
		Player.levelUpEligible(MyFarm.promoteNotifCount);

	}
	
	/**
	 * Updates the coin and XP of the player
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	private static void updateCoinAndXP( int tileNumber )
	{
		Crop.doRandomProduced();

		switch(MyFarm.plantedSeed.get(tileNumber).getName())
		{
			case "Turnip":
				setCoinAndXP(6, 5, tileNumber);
				break;
			case "Carrot":
				setCoinAndXP(9, 7.5, tileNumber);
				break;
			case "Potato":
				setCoinAndXP(3, 12.5, tileNumber);
				break;
			case "Rose":
				setCoinAndXP(5, 2.5, tileNumber);
				break;
			case "Tulips":
				setCoinAndXP(9, 5, tileNumber);
				break;
			case "Sunflower":
				setCoinAndXP(19, 7.5, tileNumber);
				break;
			case "Mango":
				setCoinAndXP(8, 25, tileNumber);
				break;
			case "Apple":
				setCoinAndXP(5, 25, tileNumber);
				break;
		}
	}

	/**
	 * Checks the surroundings of the tile
	 *
	 * @param tileNumber the chosen tile number of the player
	 */
	private static Boolean checkSurroundings( int tileNumber ) 
	{
		int[] surroundings = new int[8];
		int[][] tileEdgeX = {{1,2,3,4,5}, {46,47,48,49,50}};
		int[][] tileEdgeY = {{10,15,20,25,30,35,40,45}, {6,11,16,21,26,31,36,41}};
		
		Boolean isFree = true;
		int countFail = 0;
		int x, y;
	
			
		//UP
		surroundings[0] = Math.abs(tileNumber - 5);
		
		//DOWN
		surroundings[1] = Math.abs(tileNumber + 5);
		
		//RIGHT
		surroundings[2] = Math.abs(tileNumber + 1);
		
		//LEFT
		surroundings[3] = Math.abs(tileNumber - 1);
		
		//LEFT-UP
		surroundings[4] = Math.abs(tileNumber - 6);
		
		//LEFT-DOWN
		surroundings[5] = Math.abs(tileNumber + 4);
		
		//RIGHT-UP
		surroundings[6] = Math.abs(tileNumber - 4);
		
		//RIGHT-DOWN
		surroundings[7] = Math.abs(tileNumber + 6);
		
		//Check if the crop to planted is in the X edges
		for ( x = 0; x < 2; x++ ) 
		{
			for ( y = 0; y < 5; y++ ) 
			{
				if ( tileEdgeX[x][y] == tileNumber ) 
				{
					isFree = false;
					return isFree;
				}
				else 
				{
					isFree = true;
				}
			}
		}

		//Check if the crop to planted is in the Y edges
		for ( x = 0 ; x < 2 ; x++ ) 
		{
			for ( y = 0 ; y < 8 ; y++ ) 
			{
				if ( tileEdgeY[x][y] == tileNumber ) 
				{
					isFree = false;
					return isFree;
				}
				else 
				{
					isFree = true;
				}
			}	
		}
		

		for( int i = 0; i < 8; i++ ) 
		{
			
			//Detects plants and Rocks
			if ( (hasPlants[surroundings[i]] > 0 || MyFarm.isRockedTiles[surroundings[i]] > 0 ) && isFree == true ) 
			{
				//For the edges
				if ( hasPlants[surroundings[i]] == 0 ) 
				{						
					countFail += 1;
					
				}		
				
				if ( countFail < 8 ) 
				{
					//Test all the surroundings if all goes successful. If not, false
					isFree = false;
				}
			
				
				isFree = false;
				
			}
			
		}
		
		
		return isFree;
	}

}
