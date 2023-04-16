package gameSettings;

import javax.swing.JOptionPane;

import gameMain.MyFarm;
import gameMain.Player;

/**
 *	The ChooseSeed class is used for the player to choose the seed he/she wants to plant.
 */

/**
*
* @author Lopez, Mauries
* 		   Rojo, Kate Lynn
*
* @version 12/10/2022
*
*/


public class ChooseSeed {
	
	Player player = new Player();
	GameStatus createReport = new GameStatus();
    String plantName;
    static int plantPrice;
    static String userChoice;

	/**
	 * Either accepts the player's choice or not.
	 */
    public static int userChoiceInteger;

	/**
	 * Gets the seed the player wants to plant.
	 *
	 * @param tileNumber the tile number the player wants to plant a seed.
	 * @return tileNumber
	 */
    public Crop getSeed(int tileNumber)
    {
        
    	
        String[] seedChoiceList = {"Turnip [5 ObjectCoins]","Carrot [10 ObjectCoins]","Potato [20 ObjectCoins]","Rose [5 ObjectCoins]","Tulips [10 ObjectCoins]","Sunflower [20 ObjectCoins]","Mango [100 ObjectCoins]","Apple [200 ObjectCoins]"};      
       
        if( MyFarm.isCancelledTileNumber == false && MyFarm.isPlowedTiles[tileNumber] == 1 && MyFarm.isWitheredTiles[tileNumber] == 0 ) 
        {
        	 userChoice = (String)JOptionPane.showInputDialog(MyFarm.playerActionPanel, "Select a seed", "Seed Store", JOptionPane.QUESTION_MESSAGE, null, seedChoiceList, seedChoiceList[0]);
        	
             
             if ( ChooseSeed.userChoice != null ) 
             {
             	switch(ChooseSeed.userChoice)
                 {
                     case "Turnip [5 ObjectCoins]":
                    	 	plantPrice = 5 - Player.seedCostReduc;
                    	 	
                    	 	
                    	 	if ( Player.objectCoin >= plantPrice ) 
                    	 	{
                    	 		Player.objectCoin -= plantPrice;
                    	 		
                    	 		Player.initializePlayerStatus();
                    	 		
                    	 		return new Crop("Turnip", "Root crop",1, 2, 0, 1, 2, 1, 2, false, false);
                    	 	}
                    	 	else 
                    	 	{
                    	 		createReport.updateReport("Not Enough Object Coin. Buying Denied!");
                    	 	}
                    	 	
                    	 	break;
                    	 	
                     case "Carrot [10 ObjectCoins]":
                             plantPrice = 10 - Player.seedCostReduc;
                             
                     	 	if ( Player.objectCoin >= plantPrice ) 
                     	 	{
                     	 		Player.objectCoin -= plantPrice;

                                Player.initializePlayerStatus();
               
                             	return new Crop("Carrot", "Root crop",1, 2, 0, 1, 3, 1, 2, false, false);
                     	 	}
                     	 	else 
                    	 	{
                    	 		createReport.updateReport("Not Enough Object Coin. Buying Denied!");
                    	 	}
                            
                     	 	break;
                     	 	
                     case "Potato [20 ObjectCoins]":
                             plantPrice = 20 - Player.seedCostReduc;
                             
                     	 	if ( Player.objectCoin >= plantPrice ) 
                     	 	{
                     	 		 Player.objectCoin -= plantPrice;

                                 Player.initializePlayerStatus();

                             	return new Crop("Potato", "Root crop",3, 4, 1, 2, 5, 1, 10, false, false);
                     	 	}
                     	 	else 
                    	 	{
                    	 		createReport.updateReport("Not Enough Object Coin. Buying Denied!");
                    	 	}
                     	 	
                     	 	break;
                            
                     case "Rose [5 ObjectCoins]":
                             plantPrice = 5 - Player.seedCostReduc;
                             
                     	 	if ( Player.objectCoin >= plantPrice ) 
                     	 	{
                     	 		Player.objectCoin -= plantPrice;

                                Player.initializePlayerStatus();

                            	return new Crop("Rose", "Flower",1, 2, 0, 1, 1, 1, 1, false, false);
                     	 	}
                     	 	else 
                    	 	{
                    	 		createReport.updateReport("Not Enough Object Coin. Buying Denied!");
                    	 	}
                     	 	
                     	 	break;
                             
                     case "Tulips [10 ObjectCoins]":
                             plantPrice = 10 - Player.seedCostReduc;
                             
                     	 	if ( Player.objectCoin >= plantPrice ) 
                     	 	{
                     	 		 Player.objectCoin -= plantPrice;

                                 Player.initializePlayerStatus();

                              	return new Crop("Tulips", "Flower",2, 3, 0, 1, 2, 1, 1, false, false);
                     	 	}
                     	 	else 
                    	 	{
                    	 		createReport.updateReport("Not Enough Object Coin. Buying Denied!");
                    	 	}
                     	 	
                     	 	break;
                            
                     case "Sunflower [20 ObjectCoins]":
                             plantPrice = 20 - Player.seedCostReduc;
                             
                     	 	if ( Player.objectCoin >= plantPrice ) 
                     	 	{
                     	 		Player.objectCoin -= plantPrice;

                                Player.initializePlayerStatus();

                             	return new Crop("Sunflower", "Flower",2, 3, 1, 2, 3, 1, 1, false, false);     
                     	 	}
                     	 	else 
                    	 	{
                    	 		createReport.updateReport("Not Enough Object Coin. Buying Denied!");
                    	 	}
                     	 	
                     	 	break;
                              
                     case "Mango [100 ObjectCoins]":
                             plantPrice = 100 - Player.seedCostReduc;
                             
                     	 	if ( Player.objectCoin >= plantPrice ) 
                     	 	{
                     	 		 Player.objectCoin -= plantPrice;

                                 Player.initializePlayerStatus();
                         	
                              	return new Crop("Mango", "Fruit tree",7, 7, 4, 4, 10, 5, 15, false, false);
                     	 	}
                     	 	else 
                    	 	{
                    	 		createReport.updateReport("Not Enough Object Coin. Buying Denied!");
                    	 	}
                     	 	
                     	 	break;
                            
                     case "Apple [200 ObjectCoins]":
                             plantPrice = 200 - Player.seedCostReduc;
                             
                     	 	if ( Player.objectCoin >= plantPrice ) 
                     	 	{
                     	 		Player.objectCoin -= plantPrice;

                                Player.initializePlayerStatus();
                                
                            	return new Crop("Apple", "Fruit tree",7, 7, 5, 5, 10, 10, 15, false, false);
                     	 	}
                     	 	else 
                    	 	{
                    	 		createReport.updateReport("Not Enough Object Coin. Buying Denied!");
                    	 	}
                     	 	
                     	 	break;                            	
                 }
             	
             	
             }  
        }
        else if ( MyFarm.isPlowedTiles[tileNumber] == 0 ) 
        {
        	createReport.updateReport("Tile not plowed. Buying Denied!");
        }
        else if ( MyFarm.isWitheredTiles[tileNumber] == 1 ) 
        {
        	createReport.updateReport("Tile has withered plant. Buying Denied!");
        }
        
        return null;
    }

}
