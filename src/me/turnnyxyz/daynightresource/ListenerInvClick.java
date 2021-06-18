package me.turnnyxyz.daynightresource;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import uk.co.harieo.seasons.plugin.models.Season;

public class ListenerInvClick implements Listener {
	ArrayList<CustomDrop> arrayList2 = new ArrayList<>();
	ArrayList<CustomDrop> arrayList3 = new ArrayList<>();
	@EventHandler
	public void onInventoryClick(InventoryClickEvent click) {
		Player player = (Player) click.getWhoClicked();
		if (click.getCurrentItem() == null || click.getCurrentItem().getAmount() == 0)
			return;
		switch (click.getView().getTitle()) {
		  case "§2Main menu":
			  click.setCancelled(true);
			  switch (click.getCurrentItem().getItemMeta().getDisplayName()) {
			    case "§aEdit a block":
			    	player.openInventory(Utilities.BlockSelection(1));
			    	break;
			  }
			  break;
		  case "§2Block selection":
			  click.setCancelled(true);
			  switch (click.getCurrentItem().getItemMeta().getDisplayName()) {
			    case "§aAdd new block":
			    	player.openInventory(Utilities.AddNewBlock(player));
			    	break;
			  }
			  if (click.getSlot() > 9 && click.getSlot() < 44 && click.getSlot() % 9 > 0 && click.getSlot() % 9 < 8) {
				  if (Main.editedBlocks.keySet().contains(click.getCurrentItem().getType())) {
					  CBlock block = Main.editedBlocks.get(click.getCurrentItem().getType());
					  player.openInventory(Utilities.BlockInfo(block));
					  break;
				  }
			         Material material5 = click.getCurrentItem().getType();
			         if (material5.equals(Material.CARROT)) {
			             material5 = Material.CARROTS;
		             } else if (material5.equals(Material.BEETROOT)) {
		                 material5 = Material.BEETROOTS;
		             } else if (material5.equals(Material.COCOA_BEANS)) {
			             material5 = Material.COCOA;
		             } else if (material5.equals(Material.POTATO)) {
			             material5 = Material.POTATOES;
			         }
			         if (Main.editedBlocks.keySet().contains(material5)) {
			             CBlock cMP2Block = Main.editedBlocks.get(material5);
			             player.openInventory(Utilities.BlockInfo(cMP2Block));
			         }
			  }
			  break;
		  case "§2Choose a block":
			  click.setCancelled(true);
			  switch (click.getCurrentItem().getItemMeta().getDisplayName()) {
			    case "§aBack to main menu":
			    	player.performCommand("dnrcdrops");
			    	break;
			    case "§aBack to previous menu":
		            Inventory inventory2 = Utilities.BlockSelection(1);
		            player.openInventory(inventory2);
		            break;
			  }
			  if (click.getSlot() > 8 && click.getSlot() < 45) {
				  Material material5 = click.getCurrentItem().getType();
				  boolean weirdMaterial = false;
				  Material tempMaterial = null;
				  if (!material5.isBlock() || Main.editedBlocks.keySet().contains(material5))
					  if (material5.equals(Material.CARROT)) {
						  tempMaterial = Material.CARROTS;
						  weirdMaterial = true;
					  } else if (material5.equals(Material.BEETROOT)) {
						  tempMaterial = Material.BEETROOTS;
			              weirdMaterial = true;
					  }  else if (material5.equals(Material.COCOA_BEANS)) {
						  tempMaterial = Material.COCOA;
						  weirdMaterial = true;
					  } else if (material5.equals(Material.POTATO)) {
						  tempMaterial = Material.POTATOES;
						  weirdMaterial = true;
					  } else {
						  break;
					  }
				  CBlock Block = new CBlock();
				  if (weirdMaterial) {
					  Block.setBlock(tempMaterial);
				  } else {
					  Block.setBlock(material5);
				  }
				  Block.setCustomDrops(null);
				  Block.setVanillaDrops(null);
				  if (weirdMaterial) {
					  Main.editedBlocks.put(tempMaterial, Block);
				  } else {
					  Main.editedBlocks.put(material5, Block);
				  }
				  player.openInventory(Utilities.BlockSelection(1));
			  }
			  break;
		  case "§2Edit a block":
			  click.setCancelled(true);
			  for (Material material5 : Main.editedBlocks.keySet()) {
				  if (material5.name().equals(click.getInventory().getItem(0).getItemMeta().getDisplayName().substring(17))) {
					  Inventory customDropsInv;
					  CBlock block = Main.editedBlocks.get(material5);
					  switch (click.getCurrentItem().getItemMeta().getDisplayName()) {
					    case "§aBack to main menu":
					    	player.performCommand("dnrcdrops");
					    	break;
					    case "§aBack to previous menu":
					    	player.openInventory(Utilities.BlockSelection(1));
					    	break;
					    case "§aDelete block":
					    	if (click.isRightClick() && click.isShiftClick()) {
					    		Main.editedBlocks.remove(block.getBlock());
					    		Inventory blockSelection1 = Utilities.BlockSelection(1);
					    		player.openInventory(blockSelection1);
					    	} else {
					    		break;
					    	}
					    	break;
					    case "§aCustom drops":
					    	customDropsInv = Utilities.ListCustomDropsBlock(block, player.getUniqueId());
			                player.openInventory(customDropsInv);
					  }  
				  }
			  }
			  break;
		  case "§2Custom drops B":
			  click.setCancelled(true);
			  Material material1 = Material.getMaterial(click.getInventory().getItem(0).getItemMeta().getDisplayName().substring(17));
			  CBlock block1 = Main.editedBlocks.get(material1);
			  switch (click.getCurrentItem().getItemMeta().getDisplayName()) {
			    case "§aBack to main menu":
			    	player.performCommand("dnrcdrops");
			    	break;
			    case "§aBack to previous menu":
			    	player.openInventory(Utilities.BlockSelection(1));
			    	break;
			    case "§aNext page":
			    	Main.lastPage.put(player.getUniqueId(), Integer.valueOf(((Integer)Main.lastPage.get(player.getUniqueId())).intValue() + 1));
			    	Inventory inventory5 = Utilities.ListCustomDropsBlock(block1, player.getUniqueId());
			    	player.openInventory(inventory5);
			    	break;
			    case "§aPrevious page":
			    	Main.lastPage.put(player.getUniqueId(), Integer.valueOf(((Integer)Main.lastPage.get(player.getUniqueId())).intValue() - 1));
			    	Inventory inventory8 = Utilities.ListCustomDropsBlock(block1, player.getUniqueId());
			    	player.openInventory(inventory8);
			    	break;
			    case "§aAdd new item":
		            Inventory inventory10 = Utilities.AddCustomDropInventoryBlock(player, block1);
		            player.openInventory(inventory10);
		            break;
			  }
			  if (click.getSlot() > 8 && click.getSlot() < 45 && click.getCurrentItem().getAmount() != 0) {
				  CustomDrop tempDrop10 = null;
				  int tempInt = 0;
				  for (int i7 = 0; i7 < 28; i7++) {
					  if (click.getSlot() == ((Integer)Utilities.InventoryInside(54).get(i7)).intValue()) {
						  if (Main.lastPage.get(player.getUniqueId()) == null)
							  Main.lastPage.put(player.getUniqueId(), Integer.valueOf(1));
						  if (i7 + 28 * (((Integer)Main.lastPage.get(player.getUniqueId())).intValue() - 1) < block1.getCustomDrops().size()) {
							  tempDrop10 = block1.getCustomDrops().get(i7 + 28 * (((Integer)Main.lastPage.get(player.getUniqueId())).intValue() - 1));
							  tempInt = i7;
							  break;
						  }
					  }
				  }
				  if (tempDrop10 == null)
					  return;
				  Inventory inventory = Utilities.ShowCustomDropInfoBlock(block1, tempDrop10, tempInt + 28 * (((Integer)Main.lastPage.get(player.getUniqueId())).intValue() - 1));
				  player.openInventory(inventory);
			  }
			  break;
		  case "§2Choose an item B":
			  click.setCancelled(true);
			  Material material2 = Material.getMaterial(click.getInventory().getItem(0).getItemMeta().getDisplayName().substring(17));
			  CBlock block2 = Main.editedBlocks.get(material2);
			  switch (click.getCurrentItem().getItemMeta().getDisplayName()) {
			    case "§aBack to main menu":
			    	player.performCommand("dnrcdrops");
			    	break;
			    case "§aBack to previous menu":
			    	Inventory inventory7 = Utilities.ListCustomDropsBlock(block2, player.getUniqueId());
			    	player.openInventory(inventory7);
			    	break;
			  }
			  if (click.getSlot() > 8 && click.getSlot() < 45) {
				  CustomDrop tempDrop11 = new CustomDrop();
				  tempDrop11.setDrop(click.getCurrentItem());
				  tempDrop11.setDropChance(10);
				  tempDrop11.setEffect(true);
				  tempDrop11.setEffectColor(Color.LIME);
				  tempDrop11.setSpawnerDrop(false);
				  tempDrop11.setEggDrop(false);
				  tempDrop11.setNaturalDrop(true);
				  tempDrop11.setBredDrop(false);
				  tempDrop11.setWorldOverride(false);
				  tempDrop11.setDropTime(-1);
				  if (block2.getCustomDrops() == null) {
					  ArrayList<CustomDrop> tempArr = new ArrayList<>();
					  tempArr.add(tempDrop11);
					  block2.setCustomDrops(tempArr);
				  } else {
					  ArrayList<CustomDrop> tempArr = block2.getCustomDrops();
					  tempArr.add(tempDrop11);
					  block2.setCustomDrops(tempArr);
				  }
				  Main.editedBlocks.put(block2.getBlock(), block2);
				  Main.lastPage.put(player.getUniqueId(), Integer.valueOf(1));
				  Inventory inventory = Utilities.ListCustomDropsBlock(block2, player.getUniqueId());
				  player.openInventory(inventory);
			  }
			  break;
		  case "§2Drop settings B":
			  click.setCancelled(true);
			  Material material3 = Material.getMaterial(click.getInventory().getItem(0).getItemMeta().getDisplayName().substring(17));
			  CBlock block3 = Main.editedBlocks.get(material3);
			  arrayList2 = block3.getCustomDrops();
			  String str7 = ((String)click.getInventory().getItem(0).getItemMeta().getLore().get(0)).substring(17);
			  int i2 = Integer.parseInt(str7);
			  CustomDrop CustomDrop2 = block3.getCustomDrops().get(i2);
			  switch (click.getCurrentItem().getItemMeta().getDisplayName()) {
			    case "§aBack to main menu":
			    	player.performCommand("dnrcdrops");
			    	break;
			    case "§aBack to previous menu":
			    	Inventory inventory14 = Utilities.ListCustomDropsBlock(block3, player.getUniqueId());
			    	player.openInventory(inventory14);
			    	break;
			    case "§aDrop seasons list":
			    	if (click.isShiftClick() && ((String)click.getCurrentItem().getItemMeta().getLore().get(0)).equals("§aEnabled, §cclick to disable")) {
			    		Inventory listWorlds = Utilities.ListDropSeasonsBlock(player, block3, CustomDrop2, i2, 1);
			    		player.openInventory(listWorlds);
			    		break;
			    	}
			    	if (((String)click.getCurrentItem().getItemMeta().getLore().get(0)).equals("§cDisabled, §aclick to enable")) {
			    		CustomDrop2.setSeasonDrop(true);
			    	} else {
			    		CustomDrop2.setSeasonDrop(false);
			    	}
			    	arrayList2.set(i2, CustomDrop2);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory refresh1 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(refresh1);
			    	break;
			    case "§aDelete drop":
			    	if (click.isRightClick() && click.isShiftClick()) {
			    		arrayList2.remove(i2);
			    		block3.setCustomDrops(arrayList2);
			    		Main.editedBlocks.put(block3.getBlock(), block3);
			    		Inventory inventory18 = Utilities.ListCustomDropsBlock(block3, player.getUniqueId());
			    		player.openInventory(inventory18);
			    	}
			    	break;
			    case "§cDecrease chance by 0.01%":
			    	((CustomDrop)arrayList2.get(i2)).setDropChance(CustomDrop2.getDropChance() - 1);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory17 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory17);
			    	break;
			    case "§cDecrease chance by 0.1%":
			    	((CustomDrop)arrayList2.get(i2)).setDropChance(CustomDrop2.getDropChance() - 10);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory19 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory19);
			    	break;
			    case "§cDecrease chance by 1%":
			    	((CustomDrop)arrayList2.get(i2)).setDropChance(CustomDrop2.getDropChance() - 100);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory20 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory20);
			    	break;
			    case "§cDecrease chance by 10%":
			    	((CustomDrop)arrayList2.get(i2)).setDropChance(CustomDrop2.getDropChance() - 1000);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory22 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory22);
			    	break;
			    case "§aIncrease chance by 0.01%":
			    	((CustomDrop)arrayList2.get(i2)).setDropChance(CustomDrop2.getDropChance() + 1);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory23 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory23);
			    	break;
			    case "§aIncrease chance by 0.1%":
			    	((CustomDrop)arrayList2.get(i2)).setDropChance(CustomDrop2.getDropChance() + 10);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory24 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory24);
			    	break;
			    case "§aIncrease chance by 1%":
			    	 ((CustomDrop)arrayList2.get(i2)).setDropChance(CustomDrop2.getDropChance() + 100);
			    	 block3.setCustomDrops(arrayList2);
			    	 Main.editedBlocks.put(block3.getBlock(), block3);
			    	 Inventory inventory26 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	 player.openInventory(inventory26);
			    	break;
			    case "§aIncrease chance by 10%":
			    	((CustomDrop)arrayList2.get(i2)).setDropChance(CustomDrop2.getDropChance() + 1000);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory27 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory27);
			    	break;
			    case "§aPlaced drops":
			    	if (click.getCurrentItem().getType().equals(Material.LIME_CONCRETE))
			    		CustomDrop2.setSpawnerDrop(false);
			    	if (click.getCurrentItem().getType().equals(Material.RED_CONCRETE))
			    		CustomDrop2.setSpawnerDrop(true);
			    	arrayList2.set(i2, CustomDrop2);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory28 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory28);
			    	break;
			    case "§aNatural drops":
			    	if (click.getCurrentItem().getType().equals(Material.LIME_CONCRETE))
			    		CustomDrop2.setNaturalDrop(false); 
			    	if (click.getCurrentItem().getType().equals(Material.RED_CONCRETE))
			    		CustomDrop2.setNaturalDrop(true); 
			    	arrayList2.set(i2, CustomDrop2);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory30 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory30);
			    	break;
			    case "§aDrop time setting":
			    	if (click.getCurrentItem().getType().equals(Material.RED_CONCRETE))
			    		CustomDrop2.setDropTime(0);
			    	if (click.getCurrentItem().getType().equals(Material.YELLOW_CONCRETE))
			    		CustomDrop2.setDropTime(12500);
			    	if (click.getCurrentItem().getType().equals(Material.GRAY_CONCRETE))
			    		CustomDrop2.setDropTime(-1);
			    	arrayList2.set(i2, CustomDrop2);
			    	block3.setCustomDrops(arrayList2);
			    	Main.editedBlocks.put(block3.getBlock(), block3);
			    	Inventory inventory31 = Utilities.ShowCustomDropInfoBlock(block3, CustomDrop2, i2);
			    	player.openInventory(inventory31);
			    	break;
			  }
			  break;
		    case "§2Drop seasons B":
		    	click.setCancelled(true);
		    	Material material4 = Material.getMaterial(click.getInventory().getItem(0).getItemMeta().getDisplayName().substring(17));
		    	CBlock block4 = Main.editedBlocks.get(material4);
		    	arrayList3 = block4.getCustomDrops();
		    	String str9 = ((String)click.getInventory().getItem(0).getItemMeta().getLore().get(0)).substring(17);
		    	int i4 = Integer.parseInt(str9);
		    	CustomDrop CustomDrop3 = block4.getCustomDrops().get(i4);
		    	switch (click.getCurrentItem().getItemMeta().getDisplayName()) {
		    	  case "§aBack to main menu":
		    		  player.performCommand("dnrcdrops");
		    		  break;
		    	  case "§aBack to previous menu":
		    		  Inventory inventory21 = Utilities.ShowCustomDropInfoBlock(block4, CustomDrop3, i4);
		    		  player.openInventory(inventory21);
		    		  break;
		    	}
		    	if (click.getCurrentItem().getType().equals(Material.LIME_BANNER) || click.getCurrentItem().getType().equals(Material.RED_BANNER)) {
		    		ArrayList<String> tempArr;
		    		if (CustomDrop3.getSeason() != null) {
		    			tempArr = CustomDrop3.getSeason();
		    		} else {
		    			tempArr = new ArrayList<>();
		    		}
		    		Season season = Season.fromName(ChatColor.stripColor(click.getCurrentItem().getItemMeta().getDisplayName()));
		    		if (click.getCurrentItem().getType().equals(Material.LIME_BANNER)) {
		    			tempArr.remove(season.getRawName().toUpperCase());
		    		} else {
		    			tempArr.add(season.getRawName().toUpperCase());
		    		}
		    		CustomDrop3.setSeason(tempArr);
		    		arrayList3.set(i4, CustomDrop3);
		    		block4.setCustomDrops(arrayList3);
		    		Main.editedBlocks.put(block4.getBlock(), block4);
		    		String str = click.getInventory().getItem(52).getItemMeta().getDisplayName().substring(16);
		    		int i7 = Integer.parseInt(str);
		    		Inventory globalWorlds2 = Utilities.ListDropSeasonsBlock(player, block4, CustomDrop3, i4, i7);
		    		player.openInventory(globalWorlds2);
		    	}
		    	break;
		}
	}
}
