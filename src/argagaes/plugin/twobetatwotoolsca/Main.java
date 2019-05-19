package argagaes.plugin.twobetatwotoolsca;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Main
extends JavaPlugin
{
	public String version = "1.2.5 Community Edition";
	File configFile;
	FileConfiguration config;
	Listeners listener;
	public static Main plugin;
	HashMap<Player, Player> replyMap = new HashMap();
	List<String> MOTDs = null;
	List<String> playerRed = null;
	List<String> playerBlue = null;
	int spawnRadius = 1000;
	Random rand = new Random();

	public void onDisable()
	{
		getServer().getLogger().info("2b2t.ca " + version + " disabled! Made by Argagaes");
	}

	private void firstRun() throws Exception {
		if(!configFile.exists()){
			configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), configFile);
		}
	}
	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while((len=in.read(buf))>0){
				out.write(buf,0,len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void loadYamls() {
		try {
			config.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onEnable()
	{
		configFile = new File(getDataFolder(), "config.yml");
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		config = new YamlConfiguration();
		loadYamls();

		plugin = this;
		this.listener = new Listeners(this);
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		spawnRadius = config.getInt("SpawnRadius");
		MOTDs = config.getStringList("Random MOTDs");
		playerRed = config.getStringList("Red chat players");
		playerBlue = config.getStringList( "Blue chat players");
		getServer().getLogger().info("2b2t.ca " + version + " enabled! Made by Argagaes & others!");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equals("msg"))
		{
			String message = "";
			if (args.length < 1)
			{
				sender.sendMessage(ChatColor.RED + "Please specify a playername!");
				return true;
			}
			if (args.length >= 2) {
				for (int i = 1; i < args.length; i++) {
					message = message + args[i] + " ";
				}
			}
			Player p = getServer().getPlayer(args[0]);
			if (p == null)
			{
				sender.sendMessage(ChatColor.RED + "Player not found.");
				return true;
			}
			p.sendMessage("\247d" + ((sender instanceof Player) ? ((Player)sender).getName() : "Console") + " whispers: " + message);
			sender.sendMessage("\247dTo " + p.getName() + ": " + message);
			if ((sender instanceof Player)) {
				this.replyMap.put(p, (Player)sender);
			}
			return true;
		}
		if (command.getName().equals("r"))
		{
			if (!this.replyMap.containsKey(sender))
			{
				sender.sendMessage("Sorry, but you have no one whom you can reply to!");
				return true;
			}
			String message = "";
			if (args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					message = message + args[i] + " ";
				}
			}
			Player replyTo = this.replyMap.get(sender);
			replyTo.sendMessage("\247d" + ((sender instanceof Player) ? ((Player)sender).getName() : "Console") + " whispers: " + message);
			sender.sendMessage("\247dTo " + replyTo.getName() + ": " + message);
			if ((sender instanceof Player)) {
				this.replyMap.put(replyTo, (Player)sender);
			}
			return true;
		}
		if (command.getName().equals("list"))
		{
			String list = "";
			Player[] arrayOfPlayer;
			int j = (arrayOfPlayer = getServer().getOnlinePlayers()).length;
			for (int i = 0; i < j; i++)
			{
				Player p = arrayOfPlayer[i];
				list = list + p.getName() + ", ";
			}
			list = list.substring(0, list.length());
			sender.sendMessage("Players online: \2477(" + getServer().getOnlinePlayers().length + "/" + getServer().getMaxPlayers() + ")");
			sender.sendMessage(list);
			return true;
		}
		if (command.getName().equals("2b2t"))
		{
			if (args.length == 0) {
				sender.sendMessage("\247a2b2t.ca plugin made by Argagaes §a(Community Edition)");
				sender.sendMessage("\2479Current Version: 1.0.0");
				sender.sendMessage("\2479For Minecraft: " + version);
				return true;
			}
			return true;
		}
		sender.sendMessage("Woops! Apparently something went horribly wrong here, since you shouldn't be able to see this. Please inform Argagaes about the command you tried.");

		return true;
	}
}