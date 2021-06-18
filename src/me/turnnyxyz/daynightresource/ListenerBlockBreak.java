package me.turnnyxyz.daynightresource;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.harieo.seasons.plugin.Seasons;
import uk.co.harieo.seasons.plugin.models.Cycle;

public class ListenerBlockBreak implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled())
			return;
		Block brokenBlock = event.getBlock();
		if (Main.spawnerBlocks.contains(brokenBlock)) {
			Main.spawnerBlocks.remove(brokenBlock);
		}
		long worldTicks = Bukkit.getWorld(brokenBlock.getWorld().getName()).getTime();
		ArrayList<ItemStack> drops = new ArrayList<>();
		Seasons seasons = Seasons.getInstance();
		Cycle cycle = seasons.getWorldCycle(event.getBlock().getWorld());
		for (Material material : Main.editedBlocks.keySet()) {
			if (brokenBlock.getType().equals(material)) {
				CBlock block = Main.editedBlocks.get(material);
				if (block.getCustomDrops() != null) {
					for (CustomDrop tempDrop : block.getCustomDrops()) {
						if (tempDrop.getDropTime() > -1 && tempDrop.getDropTime() < 12500 && worldTicks >= 12500) {
							return;
						}
						if (tempDrop.getDropTime() > -1 && tempDrop.getDropTime() >= 12500 && worldTicks < 12500) {
							return;
						}
						if (Main.plugin.UseSS == true) {
							if (tempDrop.getSeasonDrop() == true && tempDrop.getSeason() != null && !tempDrop.getSeason().contains(cycle.getSeason().getRawName().toUpperCase()))
								return;
						}
						if (!tempDrop.getSpawnerDrop() && Main.spawnerBlocks.contains(brokenBlock))
							continue;
						if (!tempDrop.getNaturalDrop() && !Main.spawnerBlocks.contains(brokenBlock))
							continue;
						double tempDouble = Math.random();
						double chance = tempDrop.getDropChance();
						if (tempDouble * 10000.0D < chance) {
							if (!tempDrop.getDrop().getType().equals(Material.BARRIER))
								drops.add(tempDrop.getDrop());
						}
					}
				}
			}
		}
		for (ItemStack item : drops) {
			brokenBlock.getWorld().dropItemNaturally(brokenBlock.getLocation(), item);
		}
	}
}
