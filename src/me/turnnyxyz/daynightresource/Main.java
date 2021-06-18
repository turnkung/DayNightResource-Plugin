package me.turnnyxyz.daynightresource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.harieo.seasons.plugin.models.Season;

public class Main extends JavaPlugin {
	public static Main plugin;
	
	static HashMap<Material, CBlock> editedBlocks = new HashMap<>();
	
	static HashMap<UUID, Integer> lastPage = new HashMap<>();
	
	static ArrayList<Block> spawnerBlocks = new ArrayList<>();
	
	public boolean UseSS = false;
	FileConfiguration config = getConfig();
	
	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new ListenerInvClick(), this);
		getServer().getPluginManager().registerEvents(new ListenerBlockBreak(), this);
		getServer().getPluginManager().registerEvents(new ListenerBlockPlace(), this);
		SeasonsAPICheck();
		if (config.getConfigurationSection("CustomDrops.editedBlocks") != null)
			for (int i = 0; i < config.getConfigurationSection("CustomDrops.editedBlocks.key").getKeys(false).size(); i++) {
				CBlock tempBlock = new CBlock();
				ArrayList<CustomDrop> tempArr = new ArrayList<>();
				String temp1 = config.getString("CustomDrops.editedBlocks.get." + i + ".Block");
				if (config.getConfigurationSection("CustomDrops.editedBlocks.get." + i + ".CustomDrops") != null)
					for (int j = 0; j < config.getConfigurationSection("CustomDrops.editedBlocks.get." + i + ".CustomDrops").getKeys(false).size(); j++) {
						CustomDrop tempDrop = new CustomDrop();
						ItemStack temp3 = config.getItemStack("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".Drop");
						boolean temp4 = config.getBoolean("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".SpawnerDrop");
						boolean temp6 = config.getBoolean("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".NaturalDrop");
						boolean temp7 = config.getBoolean("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".Effect");
						Color temp8 = config.getColor("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".EffectColor");
						int temp9 = config.getInt("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".DropChance");
						boolean temp13 = config.getBoolean("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".WorldOverride");
						long temp14 = config.getLong("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".DropTime");
						if (config.getConfigurationSection("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".DropSeasons") != null) {
							ArrayList<String> temp10 = new ArrayList<>();
							for (int k = 0; k < config.getConfigurationSection("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".DropSeasons").getKeys(false).size(); k++) {
								String temp11 = config.getString("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".DropSeasons." + k).toUpperCase();
								String season = Season.fromName(temp11).getRawName().toUpperCase();
								temp10.add(season);
							}
							tempDrop.setSeason(temp10);
						}
						boolean temp15 = config.getBoolean("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".SeasonDrop");
						tempDrop.setDrop(temp3);
						tempDrop.setSpawnerDrop(temp4);
						tempDrop.setNaturalDrop(temp6);
						tempDrop.setEffect(temp7);
						tempDrop.setEffectColor(temp8);
						tempDrop.setDropChance(temp9);
						tempDrop.setWorldOverride(temp13);
						tempDrop.setDropTime(temp14);
						tempDrop.setSeasonDrop(temp15);
						tempArr.add(tempDrop);
					}
				tempBlock.setBlock(Material.getMaterial(temp1));
				tempBlock.setCustomDrops(tempArr);
				editedBlocks.put(tempBlock.getBlock(), tempBlock);
			}
	}
	
	public void onDisable() {
		config.set("CustomDrops", null);
		if (editedBlocks != null && editedBlocks.keySet().size() > 0) {
			int i = 0;
			for (Material temp : editedBlocks.keySet()) {
				config.set("CustomDrops.editedBlocks.key." + i, temp.name());
				config.set("CustomDrops.editedBlocks.get." + i + ".Block", ((CBlock)editedBlocks.get(temp)).getBlock().name());
				int j = 0;
				j = 0;
				if (((CBlock)editedBlocks.get(temp)).getCustomDrops() != null && ((CBlock)editedBlocks.get(temp)).getCustomDrops().size() != 0)
					for (CustomDrop tempDrop : ((CBlock)editedBlocks.get(temp)).getCustomDrops()) {
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".Drop", tempDrop.getDrop());
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".SpawnerDrop", Boolean.valueOf(tempDrop.getSpawnerDrop()));
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".NaturalDrop", Boolean.valueOf(tempDrop.getNaturalDrop()));
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".Effect", Boolean.valueOf(tempDrop.getEffect()));
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".EffectColor", tempDrop.getEffectColor());
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".DropChance", Integer.valueOf(tempDrop.getDropChance()));
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".WorldOverride", Boolean.valueOf(tempDrop.getWorldOverride()));
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".DropTime", Long.valueOf(tempDrop.getDropTime()));
						config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".SeasonDrop", Boolean.valueOf(tempDrop.getSeasonDrop()));
						if (tempDrop.getSeason() != null) {
							int k = 0;
							for (String season : tempDrop.getSeason()) {
								config.set("CustomDrops.editedBlocks.get." + i + ".CustomDrops." + j + ".DropSeasons." + k, season);
								k++;
							}
						}
						j++;
					}
				i++;
			}
		}
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cThis command can only excute by player.");
			return true;
		}
		Player player = (Player) sender;
		if (!player.isOp()) {
			player.sendMessage("§cYou don't have permission.");
			return true;
		}
		player.openInventory(Utilities.MainMenu());
		return true;
	}
	
	public void SeasonsAPICheck() {
		if(getServer().getPluginManager().getPlugin("Seasons") != null) {
			getServer().getScheduler().scheduleSyncDelayedTask(this,new Runnable(){
				@Override
				public void run() {
					if(getServer().getPluginManager().getPlugin("Seasons").isEnabled()) {
						System.out.println("[DayNightResource] Seasons found! Usilizing SS Api.");
						UseSS = true;
					}
				}
            },30L);
		}
	}
}
