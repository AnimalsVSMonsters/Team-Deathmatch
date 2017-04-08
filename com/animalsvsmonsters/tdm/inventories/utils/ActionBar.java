package com.animalsvsmonsters.tdm.inventories.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 
 * @author Justis
 * Action Bar utility for sending an action bar message.
 * The action bar is right above the hotbar.
 *
 */
public abstract class ActionBar {
	
	/**
	 * Gets the version correct path for a particular NMS class and returns the class.
	 * @param nmsClassName is the NMS class name
	 * @return the version correct class for the class name
	 * @throws ClassNotFoundException if the class asked for does not exist at that path.
	 */
	public static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server."
				+ Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "."
				+ nmsClassName);
	}

	/**
	 * Gets the server version from the package name.
	 * @return Server version.
	 */
	public static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().substring(23);
	}

	/**
	 * Send and action bar message to the given player.
	 * @param p Player to send the action bar message to.
	 * @param msg Message to include in the action bar.
	 */
	public static void sendActionBar(Player p, String msg) {
		try {
			String version = getServerVersion();
			String nmsClass = ((version.startsWith("v1_8_R") || version.startsWith("v1_9_R")) ? "IChatBaseComponent$" : "") + "ChatSerializer";
			Object serializer = getNmsClass(nmsClass).getMethod("a", new Class[] {String.class}).invoke(null,
					new Object[] { "{\"text\": \"" + msg + "\"}" });
			Object packet = getNmsClass("PacketPlayOutChat")
					.getConstructor(new Class[] { getNmsClass("IChatBaseComponent"), Byte.TYPE })
					.newInstance(new Object[] { serializer, (byte) 2 });
			Object handle = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", new Class[] { getNmsClass("Packet") })
					.invoke(playerConnection, new Object[] { packet });
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
