package argagaes.plugin.twobetatwotoolsca;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.LightningStrikeEvent;

public class Listeners implements Listener {

	Main main;
	List<String> allowedCommands = Arrays.asList("help", "list", "pl", "plugins", "2b2t", "r", "online", "playerlist", "msg", "w", "tell", "whisper");

	public Listeners(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onLightningStrike(LightningStrikeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		String p = event.getPlayer().getName();
		if (event.getMessage().startsWith(">")) {
			event.setMessage("§a" + event.getMessage());
		} else
		if (event.getMessage().startsWith("<")) {
			if (main.playerRed.contains(p))
				event.setMessage("§c" + event.getMessage());
		} else
		if (event.getMessage().startsWith("^")) {
			if (main.playerBlue.contains(p))
				event.setMessage("§9" + event.getMessage());
		}
	}

	@EventHandler
	public void onServerListPing(ServerListPingEvent e) {
		e.setMotd(main.MOTDs.get(main.rand.nextInt(main.MOTDs.size())));
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		Location l = p.getLocation();
		Location loc = new Location(Bukkit.getWorlds().get(0), l.getX(), l.getY(), l.getZ());
		if (!e.isBedSpawn()) {
			Random rand = new Random();
			int x = rand.nextInt(main.spawnRadius);
			int z = rand.nextInt(main.spawnRadius);
			if (rand.nextBoolean() == true) {
				x = x * -1;
			}
			if (rand.nextBoolean() == true) {
				z = z * -1;
			}
			loc.setX(x + .5);
			loc.setZ(z + .5);
			p.getWorld().loadChunk(p.getWorld().getChunkAt(loc).getX(), p.getWorld().getChunkAt(loc).getZ());
			loc.setY(p.getWorld().getHighestBlockYAt(loc) + 1);
			e.setRespawnLocation(loc);
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(ServerCommandEvent e)
	{
		String command = e.getCommand();
		if (!allowedCommands.stream().anyMatch(str -> command.toLowerCase().startsWith(str)))
		{
			e.setCommand(" ");
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent e)
	{
		Entity en = e.getEntity();
		EntityDamageEvent.DamageCause dmg = null;
		if (en.getLastDamageCause() != null) {
			dmg = en.getLastDamageCause().getCause();
		} else {
			return;
		}
		if ((en instanceof Player))
		{
			Player p = (Player)en;
			String name = p.getDisplayName();
			if (dmg.equals(EntityDamageEvent.DamageCause.SUICIDE))
			{
				List<String> given = Arrays.asList(new String[] { "§4The Packetman consumed §3" + name + "§4.", "§3" + name + " §4gave up.", "§3" + name + " §4decided to start over.", "§3" + name + " §4took the easy way out.", "§3" + name + " §4took the easy way back to spawn." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4got too close to a blast mine.", "§3" + name + " §4had their body internally compressed.", "§3" + name + " §4 was blown to smithereens.", "§3" + name + " §4went everywhere." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.CONTACT))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4jumped into a pile of cacti.", "§3" + name + " §4popped.", "§3" + name + " §4got poked full of holes." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.DROWNING))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4forgot they weren't Aqua Man.", "§3" + name + " §4slept with the fishes.", "§3" + name + " §4drowned.", "§3" + name + " §4forgot to breath.", "§3" + name + " §4forgot to go up for air.", "§3" + name + " §4swam too deep.", "§3" + name + " §4now suffers from hydrophobia.", "§3" + name + " §4sunk." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4came in too close of contact with a creeper.", "§3" + name + " §4blew up.", "§3" + name + " §4can't see the color green.", "§3" + name + " §4was blasted to bits." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.FALL))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4tripped and fell from the top of the staircase.", "§3" + name + " §4forgot they couldn't fly.", "§4Gravity overtook §3" + name + "§4.", "§3" + name + " §4became one with bedrock.", "§3" + name + " §4looked down.", "§3" + name + " §4forgot to look down.", "§3" + name + " §4didn't watch their step; and then missed it.", "§3" + name + " §4misjudged a jump.", "§3" + name + " §4lost their footing.", "§3" + name + " §4fell and shattered.", "§3" + name + " §4rediscovered gravity." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.FIRE))
			{
				List<String> given = Arrays.asList(new String[] { "§4Smokey the Bear couldn't reach §3" + name + " §4in time.", "§3" + name + " §4cremated themselves.", "§3" + name + " §4turned to ashes.", "§3" + name + " §4tried playing with matches.", "§3" + name + " §4built too large of a campfire.", "§3" + name + " §4became biofuel.", "§3" + name + " §4turned into charcoal.", "§3" + name + " §4fried." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.FIRE_TICK))
			{
				List<String> given = Arrays.asList(new String[] { "§4Smokey the Bear couldn't reach §3" + name + " §4in time.", "§3" + name + " §4cremated themselves.", "§3" + name + " §4turned to ashes.", "§3" + name + " §4tried playing with matches.", "§3" + name + " §4built too large of a campfire.", "§3" + name + " §4became biofuel.", "§3" + name + " §4turned into charcoal.", "§3" + name + " §4couldn't find water fast enough and fried." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.LAVA))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4tried to take swimming lessons in lava.", "§3" + name + " §4melted.", "§3" + name + " §4became part of the Earth." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.LIGHTNING))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4became one with Zeus.", "§3" + name + " §4evaporated.", "§3" + name + " §4turned into photons.", "§3" + name + " §4thought they were a pig.", "§3" + name + " §4thought they were a skeleton horse.", "§3" + name + " §4had bad luck.", "§3" + name + " §4got smited.", "§3" + name + " §4played golf in an electrical storm.", "§3" + name + " §4lost a duel with Zeus.", "§3" + name + " §4tried to mimic Ben Franklin." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.MAGIC))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4turned to dust.", "§3" + name + " §4had too many drinks.", "§3" + name + " §4can't read bottle labels.", "§3" + name + " §4drank the wrong potion.", "§3" + name + " §4made enemies with a Witch.", "§3" + name + " §4went poof.", "§3" + name + " §4somehow died." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.POISON))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4somehow died from poison.", "§3" + name + " §4got sick.", "§3" + name + " §4didn't know the difference between venomous and poisonous." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.PROJECTILE))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4was pierced by an arrow of sorts.", "§3" + name + " §4was made the bullseye for archery practice.", "§3" + name + " §4got spliced.", "§3" + name + " §4was outranged." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.STARVATION))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4was consumed by The Zone.", "§4The Zone consumed §3" + name + "§4.", "§3" + name + " §4forgot to eat.", "§3" + name + " §4became lethally anorexic.", "§3" + name + " §4went on a hunger strike and lost.", "§3" + name + " §4got hungry.", "§3" + name + " §4suffered from a famine." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.SUFFOCATION))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4met Medusa.", "§3" + name + " §4became a solid state of matter.", "§3" + name + " §4was compressed into a wall of sorts.", "§3" + name + " §4was compacted.", "§3" + name + " §4turned into a block." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.VOID))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4was consumed by the void.", "§3" + name + " §4ceased to exist." });
				Random ra = new Random();
				String raE = (String)given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK))
			{
				Player killer = p.getKiller();
				Material weapon = killer.getItemInHand().getType();
				String w = weapon.toString();
				String kN = killer.getDisplayName();
				if (kN == null) {
					kN = "Unknown";
				}
				if (weapon == Material.AIR)
				{
					e.setDeathMessage("§3" + kN + " §4smacked the life out of §3" + name + "§4.");
					return;
				}
				if (weapon == null)
				{
					e.setDeathMessage("§3" + kN + " §4smacked the life out of §3" + name + "§4.");
					return;
				}
				if (weapon != Material.AIR) {
					e.setDeathMessage("§3" + kN + " §4ripped apart §3" + name + " §4using §4" + w + "§4.");
				}
			}
			else if (!dmg.equals(EntityDamageEvent.DamageCause.values()))
			{
				e.setDeathMessage("§3" + name + " §4evaporated or something.");
			}
			else if (dmg.equals(EntityDamageEvent.DamageCause.CUSTOM))
			{
				List<String> given = Arrays.asList(new String[] { "§3" + name + " §4was consumed by the void." });
				Random ra = new Random();
				String raE = given.get(ra.nextInt(given.size()));
				e.setDeathMessage(raE);
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e)
	{
		String error = "Unknown command. Type \"/help\" for help";
		String command = e.getMessage();
		if (e.getMessage().toLowerCase().startsWith("/help"))
		{
			e.setCancelled(true);
			e.getPlayer().sendMessage("§6Commands:");
			e.getPlayer().sendMessage("§2/kill - §4combust yourself instantly.");
			e.getPlayer().sendMessage("§2/tell - §4send a private message to another user.");
			e.getPlayer().sendMessage("§2/r - §4reply to a user.");
			e.getPlayer().sendMessage("§2/timetracker -§4TimeTracker plugin help.");
			e.getPlayer().sendMessage("§2/2b2t - §42b2t.ca plugin info.");
		} else if (!allowedCommands.stream().anyMatch(str -> command.toLowerCase().startsWith(str)))
		{
			e.setCancelled(true);
			e.getPlayer().sendMessage(error);
		}
	}
}
