package com.animalsvsmonsters.tdm.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.animalsvsmonsters.tdm.data.files.DataFile;

/**
 * 
 * @author Justis
 * Updater class for updating this plugin from wherever you want to update it from...
 * I was using it for my database thingy, which also contained a txt file with the latest version numbers.
 */
public class Updater {
	
	// Link to jar file to check for
	private final String dlLink = "http://justisroot.com/AVM.jar";
	// Link to txt file containing the version numbers. :P
	private final String versionLink = "http://justisroot.com/Versions.txt";
	// Current version
	private final String version;

	/**
	 * Initialize the updater
	 * @param version that we're currently on.
	 */
	public Updater(String version) {
		this.version = version;
	}

	/**
	 * For an update of the plugin to the file at the dLink, regardless of whether or not the version is old or new or equal.
	 */
	public void update() {
		update(true);
	}

	/**
	 * Update only if the version of the dLink file is newer than the version currently running.
	 */
	public void checkForUpdates() {
		update(false);
	}

	/**
	 * Update the plugin if the Dlink version is new or the force boolean is true
	 * @param force the plugin to update even if the version is not new? 
	 */
	public void update(boolean force) {
		try {
			Version oldVersion = new Version(this.version);
			String path = getFilePath();
			URL url = new URL(versionLink);
			DataFile file = new DataFile("", "PluginVersions", ".txt");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			InputStream input = con.getInputStream();
			file.copyDefaults(input, true);
			input.close();
			con.disconnect();
			String fileVersion = file.getString("AVM");
			Version newVersion = new Version(fileVersion);
			file.delete();
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Newest plugin version: v" + fileVersion);
			if ((force) || (newVersion.compareTo(oldVersion) > 0)) {
				url = new URL(dlLink);
				con = (HttpURLConnection) url.openConnection();
				input = con.getInputStream();
				FileOutputStream out = new FileOutputStream(path);
				byte[] buffer = new byte[1024];
				int size = 0;
				while ((size = input.read(buffer)) != -1) {
					out.write(buffer, 0, size);
				}

				out.close();
				input.close();
				con.disconnect();
				Bukkit.getConsoleSender()
						.sendMessage(ChatColor.GREEN + "Succesfully updated plugin to v" + fileVersion);
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No updates neccessaary!");
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Warning] Failed to auto-update " + e.getMessage());
		}
	}

	/** 
	 * Get the path to the jar file.
	 * @return
	 */
	private String getFilePath() {
		String[] folder = null;
		try {
			folder = URLDecoder
					.decode(Updater.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")
					.split(File.separator);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "plugins" + File.separator + folder[(folder.length - 1)];
	}

	@SuppressWarnings("unused")
	/**
	 * Convert a string version into a int value.
	 * @param from string to conver
	 * @return int value of the string version
	 */
	private int getVersionFromString(String from) {
		String result = "";
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(from);

		while (matcher.find()) {
			result = result + matcher.group();
		}

		return result.isEmpty() ? 0 : Integer.parseInt(result);
	}

	/** 
	 * 
	 * Little util for managing versions and stuff.
	 *
	 */
	public class Version implements Comparable<Version> {
		private String version;

		public final String get() {
			return this.version;
		}

		public Version(String version) {
			if (version == null)
				throw new IllegalArgumentException("Version can not be null");
			if (!version.matches("[0-9]+(\\.[0-9]+)*"))
				throw new IllegalArgumentException("Invalid version format");
			this.version = version;
		}

		public int compareTo(Version that) {
			if (that == null)
				return 1;
			String[] thisParts = get().split("\\.");
			String[] thatParts = that.get().split("\\.");
			int length = Math.max(thisParts.length, thatParts.length);
			for (int i = 0; i < length; i++) {
				int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
				int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
				if (thisPart < thatPart)
					return -1;
				if (thisPart > thatPart)
					return 1;
			}
			return 0;
		}

		public boolean equals(Object that) {
			if (this == that)
				return true;
			if (that == null)
				return false;
			if (getClass() != that.getClass())
				return false;
			return compareTo((Version) that) == 0;
		}
	}
}