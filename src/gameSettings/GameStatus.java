package gameSettings;

import java.awt.Font;
import javax.swing.JTextArea;

import gameMain.MyFarm;

/**
 *
 * @author Lopez, Mauries
 * 		   Rojo, Kate Lynn
 *
 * @version 12/10/2022
 *
 */

/**
 * The GameStatus class is used for the status of the game
 */
public class GameStatus {

	/**
	 * Used to store the reports of the game
	 */
	public static JTextArea reportList = new JTextArea();

	/**
	 * Used to update and report the actions of the player
	 *
	 * @param gameStatus - the status of the game
	 */
	public void updateReport( String gameStatus ) 
	{
		//MyFarm.seedInventory.add(gameStatus);
		
		reportList.setEditable(false);
		reportList.setFont(new Font("Calibri", Font.BOLD, 10));
		reportList.setBounds(0, 0, 135, 739);
		reportList.insert(gameStatus+"\n", 0);
		reportList.setWrapStyleWord(true);
		reportList.setLineWrap(true);
		
		MyFarm.reportStatusPanel.add(reportList);
		MyFarm.reportStatusPanel.repaint();
		
		
	}

}
