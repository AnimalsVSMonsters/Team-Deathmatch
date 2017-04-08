package com.animalsvsmonsters.tdm.skills;

/**
 * Skills that implement this interface will have their "equip" and "unequip" methods updated when the player equips and unequips the slot their skill is located in.
 * 
 * Allows for additional functionality only possible by knowing when the player has equipt/unequipt the skill item.
 * @author Justis
 *
 */
public abstract interface Sensitive {
	
	public abstract void equip();

	public abstract void unequip();
}
