package me.turnnyxyz.daynightresource;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ListenerBlockPlace implements Listener {
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {
		for (Material material : Main.editedBlocks.keySet()) {
			if (event.getBlock().getType().equals(material)) {
				CBlock block = Main.editedBlocks.get(material);
				if (block.getCustomDrops() != null) {
					Main.spawnerBlocks.add(event.getBlock());
				}
			}
		}
	}
}
