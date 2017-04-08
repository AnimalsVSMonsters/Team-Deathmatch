package com.animalsvsmonsters.tdm.inventories.utils;

/**
 * A quick enum for getting the amount of slots in an inventory row.
 * Used to prevent illegal arguments when creating an inventory.
 * 
 * @author Justis
 *
 */
public enum Rows {
	
	/** 
	 * Stops at seven because you cannot create inventories larger than seven rows.
	 * Increases by nine because each row has nine slots in it.
	 */
	ONE(9), TWO(18), THREE(27), FOUR(36), FIVE(45), SIX(54), SEVEN(63);

	private int spaces;

	/**
	 * Choose the amount of rows! ;D
	 * @param space
	 */
	private Rows(int space) {
		this.spaces = space;
	}

	/**
	 * @return the number of spaces in this row
	 */
	public int getSpaces() {
		return this.spaces;
	}

	/**
	 * Turns an integer into a row.
	 * Boom magic.
	 * @param i
	 * @return The number of rows you specified in the param
	 * null if you asked for less than 1 or greater than 7
	 */
	public static Rows fromInt(int i) {
		switch (i) {
		case 1:
			return ONE;
		case 2:
			return TWO;
		case 3:
			return THREE;
		case 4:
			return FOUR;
		case 5:
			return FIVE;
		case 6:
			return SIX;
		case 7:
			return SEVEN;
		}
		return null;
	}
}
