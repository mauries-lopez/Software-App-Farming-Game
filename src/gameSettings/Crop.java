package gameSettings;

import java.awt.Color;
import java.util.Random;

import gameMain.MyFarm;
import gameMain.Player;

/**
 *	The Crop class displays the information of the crop planted in the tile, and is
 *	tasked in watering,fertilizing, harvesting the crop and checks if the crop is withered.
 *  Also, it is assigned in producing the number of product/s of the crop harvested.
 */

/**
 *
 * @author Lopez, Mauries
 * 		   Rojo, Kate Lynn
 *
 * @version 12/10/2022
 *
 */

/**
 * Consists of variables and global state of the crop.
 */
public class Crop {
	
	Player player = new Player();

	private String seedName;

	/**
	 * the number of days the crop has been planted(Age of the crop)
	 */
	public static int[] cropAge = new int[51];
	private final static Random rand = new Random();
	private String cropType;
	private Boolean isWithered = false;
	private Boolean isHarvestable = false;

	//Harvest Variables
	private boolean isHarvested = false;
	private int harvestDay;

	//Harvest Produced
	private static int minProduced;
	private static int maxProduced;
	private static int producedCrop = 0;

	//Water Variables
	/**
	 * numbers of water used
	 */
	public static int[] water = new int[51];

	/**
	 * the needed amount of water for the crop.
	 */
	private int waterNeeded;
	private static int waterLimit;

	//Fertilizer Variables
	/**
	 * numbers of fertilizer used
	 */
	public static int[] fertilizer = new int[51];
	private int fertilizerNeeded;
	private static int fertilizerLimit;

	/**
	 *	Constructor for Crop class
	 *
	 * @param name The name of the seed
	 * @param type The crop type
	 * @param waterNeeded The water needed for the crop
	 * @param waterLimit The water limit for the crop
	 * @param fertilizerNeeded The fertilizer needed for the crop
	 * @param fertilizerLimit The fertilizer limit for the crop
	 * @param harvestDay The harvest day of the crop
	 * @param minProduced The minimum produced of the crop
	 * @param maxProduced The maximum produced of the crop
	 * @param isWithered  The crop is withered
	 * @param isHarvestable The crop is ready to be harvested
	 */

	public Crop(String name, String type, int waterNeeded, int waterLimit, int fertilizerNeeded, int fertilizerLimit, int harvestDay, int minProduced, int maxProduced, boolean isWithered, boolean isHarvestable) {
		this.seedName = name;
		this.cropType = type;
		this.waterNeeded = waterNeeded;
		this.fertilizerNeeded = fertilizerNeeded;
		Crop.waterLimit = waterLimit;
		Crop.fertilizerLimit = fertilizerLimit;
		this.harvestDay = harvestDay;
		Crop.minProduced = minProduced;
		Crop.maxProduced = maxProduced;
		this.isWithered = isWithered;
		this.setIsHarvestable(isHarvestable);
	}
	
	/**
	 * Gets the name of the seed
	 * 
	 * @return seedName
	 */
	public String getName() {
		return this.seedName;
	}

	/**
	 * Sets the name of the seed
	 *
	 * @param name The name of the seed
	 */
	public void setName(String name) {
		this.seedName = name;
	}

	/**
	 * Gets the number of times the crop has been watered
	 *
	 * @param tileNumber The tile number of the crop
	 * @return tileNumber
	 */
	public static int getWater(int tileNumber) {
		return water[tileNumber];
	}

	/**
	 * Adds water to the crop
	 * @param tileNumber The tile number of the crop
	 */
	public static void addWater(int tileNumber) 
	{
		int temp = getWater(tileNumber);
		
		if ( getWater(tileNumber) >= getWaterLimit() ) 
		{
			water[tileNumber] = temp;
		}
		else 
		{
			water[tileNumber] += 1;
		}
		
	}

	/**
	 * Gets the water needed of the crop
	 * 
	 * @return waterNeeded
	 */

	public int getWaterNeeded() {
		return waterNeeded;
	}

	/**
	 * Sets the water needed of the crop
	 *
	 * @param waterNeeded The water needed of the crop
	 */
	public void setWaterNeeded(int waterNeeded) {
		this.waterNeeded = waterNeeded;
	}

	/**
	 * Gets the fertilizer limit of the crop
	 *
	 * @param tileNumber The tile number of the crop
	 * @return tileNumber
	 */

	public static int getFertilizer(int tileNumber) {
		return fertilizer[tileNumber];
	}

	/**
	 * Adds a fertilizer to the crop
	 *
	 * @param tileNumber The tile number of the crop
	 */
	public static void addFertilizer(int tileNumber) 
	{
		int temp = getFertilizer(tileNumber);
		
		if ( getFertilizer(tileNumber) >= getFertilizerLimit() ) 
		{
			fertilizer[tileNumber] = temp;
		}
		else 
		{
			fertilizer[tileNumber] += 1;
		}
		
		
	}

	/**
	 * Gets the fertilizer needed of the crop
	 *
	 * @return fertilizerNeeded
	 */
	public int getFertilizerNeeded() {
		return fertilizerNeeded;
	}

	/**
	 *	Sets the fertilizer needed of the crop
	 *
	 *  @param fertilizerNeeded The fertilizer needed of the crop
	 */
	public void setFertilizerNeeded(int fertilizerNeeded) {
		this.fertilizerNeeded = fertilizerNeeded;
	}

	/**
	 * Gets the water limit of the crop
	 *
	 * @return Crop.waterLimit + Player.waterBonusLimit
	 */
	public static int getWaterLimit() {
		return Crop.waterLimit + Player.waterBonusLimit;
		//return waterLimit + Player.waterBonusLimit;
	}

	/**
	 *	Gets the fertilizer limit of the crop
	 *
	 * @return fertilizerLimit + Player.fertilizerBonusLimit
	 */
	public static int getFertilizerLimit() {
		return fertilizerLimit + Player.fertilizerBonusLimit;
		//return fertilizerLimit + Player.fertilizerBonusLimit;
	}

	/**
	 * Gets the harvest day of the crop
	 *
	 * @return harvestDay
	 */
	public int getHarvestDay() {
		return harvestDay;
	}

	/**
	 *	Gets the crop age
	 *
	 *	@param tileNumber The tile number of the crop
	 *	@return Crop.cropAge[tileNumber];
	 */
	public int getCropAge(int tileNumber) {
		return Crop.cropAge[tileNumber];
	}

	/**
	 * Sets the crop age
	 * @param cropAge - The crop age
	 * @param tileNumber - The tile number of the crop
	 */
	public void setCropAge(int cropAge, int tileNumber) {
		Crop.cropAge[tileNumber] = cropAge;
	}

	/**
	 *	Grows the crop
	 */
	public static void growCrop() {
		
		int ageTile = 0;
		
		for ( int i = 1; i < MyFarm.plantedSeed.size() ; i++ ) 
		{
			if ( MyFarm.buttonTiles[i].isEnabled() == true && MyFarm.plantedSeed.get(i) != null ) 
			{
				ageTile = i;
				Crop.cropAge[ageTile] += 1;	
				MyFarm.buttonTiles[ageTile].setBackground(new Color(255,192,0));
			}
		}		
		
	}

	/**
	 * Checks if the crop is harvested
	 *
	 * @return isHarvested 
	 */
	public boolean isHarvested() {
		return isHarvested;
	}

	/**
	 *	Sets the crop as harvested
	 *
	 *  @param isHarvested The crop is harvested
	 */
	public void setHarvested(boolean isHarvested) {
		this.isHarvested = isHarvested;
	}

	/**
	 *	Does the random produced of the crop
	 */
	public static void doRandomProduced()
	{
		Crop.producedCrop = (rand.nextInt(maxProduced - minProduced + 1) + minProduced) + Player.bonusProduced;

	}

	/**
	 *	Gets the produced crop
	 *
	 *	@return Crop.producedCrop
	 */
	public static int getProducedCrop()
	{
		return Crop.producedCrop;
	}

	/**
	 * Gets the crop type
	 * 
	 * @return cropType
	 */
	public String getCropType() {
		return cropType;
	}

	/**
	 * Sets the crop type
	 *
	 * @param cropType The crop type
	 */
	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
	
	@Override
	public String toString() 
	{
		return this.getName();
	}

	/**
	 * gets if the crop is withered
	 *
	 * @return isWithered
	 */
	public boolean getIsWithered() 
	{
		return isWithered;
	}

	/**
	 * Sets if the crop is withered
	 *
	 * @param isWithered The crop is withered
	 */
	public void setIsWithered(Boolean isWithered) 
	{
		this.isWithered = isWithered;
	}

	/**
	 * Gets the harvested crop
	 *
	 * @return isHarvestable
	 */

	public Boolean getIsHarvestable() {
		return isHarvestable;
	}

	/**
	 * Sets the harvested crop
	 *
	 * @param isHarvestable - The crop is ready to be harvested
	 */

	public void setIsHarvestable(Boolean isHarvestable) {
		this.isHarvestable = isHarvestable;
	}
	



}
