package br.com.devpaulo.legendchat.updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import br.com.devpaulo.legendchat.Main;
import br.com.devpaulo.legendchat.api.Legendchat;

public class UpdateChecker {
	public static final String REPOSITORY = "joaovmundel/LegendChat";
	private static final String API_URL = "https://api.github.com/repos/"+REPOSITORY+"/releases/latest";
	private static final String RELEASES_URL = "https://github.com/"+REPOSITORY+"/releases/latest";
	// api.github.com answers 403 to any request without an User-Agent
	private static final String USER_AGENT = "Legendchat";
	private static final Pattern TAG_NAME = Pattern.compile("\"tag_name\"\\s*:\\s*\"([^\"]*)\"");
	private static final Pattern VERSION = Pattern.compile("[0-9]+(?:\\.[0-9]+)*");
	private static final String[] STAFF_PERMISSIONS = {
		"legendchat.admin",
		"legendchat.admin.channel",
		"legendchat.admin.spy",
		"legendchat.admin.hide",
		"legendchat.admin.mute",
		"legendchat.admin.unmute",
		"legendchat.admin.muteall",
		"legendchat.admin.unmuteall",
		"legendchat.admin.reload"
	};

	private String version = "";
	public UpdateChecker(String v) {
		version=v;
	}

	// Version of the latest release when it is above the running one, null otherwise
	public String getNewVersion() throws Exception {
		String tag = readLatestTag();
		if(tag==null)
			return null;
		Matcher m = VERSION.matcher(tag);
		// a tag carrying no number (like "plugin") is nothing we can compare against
		if(!m.find())
			return null;
		String latest = m.group();
		return isNewer(latest,version)?latest:null;
	}

	private String readLatestTag() throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
		conn.setRequestProperty("Accept", "application/vnd.github+json");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		// 404 while the repository has no release, 403 when the api rate limit is hit
		if(conn.getResponseCode()!=200) {
			conn.disconnect();
			return null;
		}
		StringBuilder body = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		try {
			String line;
			while((line=reader.readLine())!=null)
				body.append(line);
		}
		finally {
			try {reader.close();} catch(Exception e) {}
			conn.disconnect();
		}
		Matcher m = TAG_NAME.matcher(body.toString());
		return m.find()?m.group(1):null;
	}

	// Compares dot separated numbers, so 1.1.17 is newer than 1.1.7
	static boolean isNewer(String latest, String current) {
		String[] a = latest.split("\\.");
		String[] b = current.split("\\.");
		int size = (a.length>b.length?a.length:b.length);
		for(int i=0;i<size;i++) {
			int na = (i<a.length?toInt(a[i]):0);
			int nb = (i<b.length?toInt(b[i]):0);
			if(na!=nb)
				return na>nb;
		}
		return false;
	}

	private static int toInt(String s) {
		try {
			return Integer.parseInt(s.trim());
		}
		catch(NumberFormatException e) {
			return 0;
		}
	}

	public static String getReleasesUrl() {
		return RELEASES_URL;
	}

	// Staff notice on join, delayed so it is not buried by the join messages. The check
	// runs asynchronously, so the result may only arrive after the player is already in
	public static void notifyIfOutdated(final Player p) {
		if(!isStaff(p))
			return;
		Bukkit.getServer().getScheduler().runTaskLater(Legendchat.getPlugin(), new Runnable() {
			public void run() {
				if(Main.need_update==null)
					return;
				p.sendMessage(ChatColor.GOLD+"[Legendchat] "+ChatColor.WHITE+"New update available: "+ChatColor.AQUA+"V"+Main.need_update+"!");
				p.sendMessage(ChatColor.GOLD+"Download: "+ChatColor.WHITE+RELEASES_URL);
			}
		}, 60L);
	}

	private static boolean isStaff(Player p) {
		for(String permission : STAFF_PERMISSIONS)
			if(p.hasPermission(permission))
				return true;
		return false;
	}
}
