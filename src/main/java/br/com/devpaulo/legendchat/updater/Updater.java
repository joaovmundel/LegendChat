package br.com.devpaulo.legendchat.updater;

import java.io.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import br.com.devpaulo.legendchat.api.Legendchat;
import br.com.devpaulo.legendchat.messages.MessageManager;

public class Updater {
	private boolean updConfig = false;
	private Plugin plugin = Bukkit.getPluginManager().getPlugin("Legendchat");
	public boolean updateConfig() {
		InputStream is = plugin.getResource(("config_template.yml"));
		YamlConfiguration c = YamlConfiguration.loadConfiguration(new InputStreamReader(is));
		for(String n : c.getConfigurationSection("").getKeys(true))
			if(!has(n))
				set(n,c.get(n));
		if(updConfig)
			plugin.saveConfig();
		return updConfig;
	}
	
	private boolean has(String s) {
		return plugin.getConfig().contains(s);
	}
	
	private void set(String s,Object obj) {
		plugin.getConfig().set(s, obj);
		updConfig=true;
	}
	
	private boolean updLang = false;
	public boolean updateAndLoadLanguage(String language) {
		File f = new File(plugin.getDataFolder(),"language"+File.separator+"language_"+language+".yml");
		MessageManager m = Legendchat.getMessageManager();
		m.registerLanguageFile(f);
		m.loadMessages(f);
		InputStream is = null;
		if((is = plugin.getResource(("language"+File.separator+"language_"+language+".yml").replace('\\', '/')))==null)
			is=plugin.getResource(("language"+File.separator+"language_en.yml").replace('\\', '/'));
		YamlConfiguration c = YamlConfiguration.loadConfiguration(new InputStreamReader(is));
		for(String n : c.getConfigurationSection("").getKeys(false))
			if(!m.hasMessage(n))
				addMessage(m,n,c.getString(n));
		if(updLang)
			m.loadMessages(f);
		return updLang;
	}
	
	private void addMessage(MessageManager m, String name, String msg) {
		m.addMessageToFile(name, msg);
		updLang=true;
	}
	
	public boolean updateChannels() {
		boolean upd = false;
		for (File channel : new File(Bukkit.getPluginManager().getPlugin("Legendchat").getDataFolder(),"channels").listFiles()) {
			YamlConfiguration channel2 = YamlConfiguration.loadConfiguration(channel);
			if(!channel2.contains("needFocus")) {
				channel2.set("needFocus", false);
				upd=true;
			}
			try {
				channel2.save(channel);
			} catch (IOException e) {e.printStackTrace();}
		}
		return upd;
	}
	
}
