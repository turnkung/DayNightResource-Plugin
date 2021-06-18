package me.turnnyxyz.daynightresource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import uk.co.harieo.seasons.plugin.models.Season;

public class Utilities {
	static ArrayList<Integer> InventoryFrame(int int1) {
		ArrayList<Integer> arrayInt = new ArrayList<>();
		int i;
		for (i = 1; i < 8; i++) {
			arrayInt.add(Integer.valueOf(i));
		}
		for (i = 0; i < int1; i+=9) {
			arrayInt.add(Integer.valueOf(i));
			arrayInt.add(Integer.valueOf(i + 8));
		}
		for (i = int1 - 1; i > int1 - 9; i--) {
			arrayInt.add(Integer.valueOf(i));
		}
		return arrayInt;
	}
	static ArrayList<Integer> InventoryInside(int int1) {
		ArrayList<Integer> arrayInt = new ArrayList<>();
		for (int i = 0; i < int1; i++) {
			if (!InventoryFrame(int1).contains(Integer.valueOf(i))) {
				arrayInt.add(Integer.valueOf(i));
			}
		}
		return arrayInt;
	}
	static Inventory Frame(Inventory inv) {
		ItemStack frameMaterial = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta frameMaterialMeta = frameMaterial.getItemMeta();
		frameMaterialMeta.setDisplayName(" ");
		frameMaterial.setItemMeta(frameMaterialMeta);
		for (Iterator<Integer> iterator = InventoryFrame(inv.getSize()).iterator(); iterator.hasNext();) {
			int i = ((Integer)iterator.next()).intValue();
			inv.setItem(i, frameMaterial);
		}
		ItemStack frameItem0 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		ItemMeta frameItem0Meta = frameItem0.getItemMeta();
		frameItem0Meta.setDisplayName("§2[Day-Night Resource]");
		List<String> list1 = new ArrayList<>();
		list1.add("§aAdapt by : TurnLnwZa");
		frameItem0Meta.setLore(list1);
		frameItem0.setItemMeta(frameItem0Meta);
		inv.setItem(4, frameItem0);
		return inv;
	}
	static Inventory MainMenu() {
		Inventory mainMenu = Bukkit.createInventory(null, 27, "§2Main menu");
		mainMenu = Frame(mainMenu);
		ItemStack item1 = new ItemStack(Material.DIAMOND_ORE);
		ItemMeta item1Meta = item1.getItemMeta();
		item1Meta.setDisplayName("§aEdit a block");
		item1.setItemMeta(item1Meta);
		mainMenu.setItem(13, item1);
		return mainMenu;
	}
	static Inventory BlockSelection(int pageNumber) {
		Inventory blockSelect = Bukkit.createInventory(null, 54, "§2Block selection");
		blockSelect = Frame(blockSelect);
		ItemStack addBlock = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
		ItemMeta addBlockMeta = addBlock.getItemMeta();
		addBlockMeta.setDisplayName("§aAdd new block");
		addBlock.setItemMeta(addBlockMeta);
		blockSelect.setItem(8, addBlock);
		if (Main.editedBlocks != null) {
			if (Main.editedBlocks.size() > 28 * pageNumber) {
				ItemStack nextPageMaterial = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
				ItemMeta nextPageMaterialMeta = nextPageMaterial.getItemMeta();
				nextPageMaterialMeta.setDisplayName("§aNext page");
				nextPageMaterial.setItemMeta(nextPageMaterialMeta);
				blockSelect.setItem(53, nextPageMaterial);
			}
			if (pageNumber > 1) {
				ItemStack previousPageMaterial = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
				ItemMeta previousPageMaterialMeta = previousPageMaterial.getItemMeta();
				previousPageMaterialMeta.setDisplayName("§aPrevious page");
				previousPageMaterial.setItemMeta(previousPageMaterialMeta);
				blockSelect.setItem(51, previousPageMaterial);
			}
			int tempInt1 = 0;
			int tempInt2 = 0;
			for (Material tempBlockMaterial : Main.editedBlocks.keySet()) {
				if (tempInt2 > 27)
					break; 
				if (tempInt1 < 28 * (pageNumber - 1)) {
					tempInt1++;
					continue;
				}
				Material material = tempBlockMaterial;
				if (material.equals(Material.CARROTS)) {
					tempBlockMaterial = Material.CARROT;
					
				} else if (material.equals(Material.BEETROOTS)) {
					tempBlockMaterial = Material.BEETROOT;
				} else if (material.equals(Material.COCOA)) {
					tempBlockMaterial = Material.COCOA_BEANS;
				} else if (material.equals(Material.POTATOES)) {
					tempBlockMaterial = Material.POTATO;
				}
				ItemStack tempBlock = new ItemStack(tempBlockMaterial);
				blockSelect.setItem(((Integer)InventoryInside(54).get(tempInt2)).intValue(), tempBlock);
				tempInt2++;
			}
		}
		return blockSelect;
	}
	static Inventory AddNewBlock(Player player) {
		Inventory chooseDropInv = Bukkit.createInventory(null, 54, "§2Choose a block");
		chooseDropInv = Frame(chooseDropInv);
		ItemStack mainMenuItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		ItemMeta mainMenuItemMeta = mainMenuItem.getItemMeta();
		mainMenuItemMeta.setDisplayName("§aBack to main menu");
		mainMenuItem.setItemMeta(mainMenuItemMeta);
		chooseDropInv.setItem(45, mainMenuItem);
		ItemStack previousPageItem = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		ItemMeta previousPageItemMeta = previousPageItem.getItemMeta();
		previousPageItemMeta.setDisplayName("§aBack to previous menu");
		previousPageItem.setItemMeta(previousPageItemMeta);
		chooseDropInv.setItem(46, previousPageItem);
		PlayerInventory playerInventory = player.getInventory();
		for (int i = 0; i < 36; i++) {
			if (playerInventory.getItem(i) == null) {
				chooseDropInv.setItem(i + 9, null);
			} else {
				chooseDropInv.setItem(i + 9, playerInventory.getItem(i));
			}
		}
		return chooseDropInv;
	}
	static Inventory BlockInfo(CBlock block) {
		Inventory blockInfo = Bukkit.createInventory(null, 27, "§2Edit a block");
		blockInfo = Frame(blockInfo);
		ItemStack mainMenuItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		ItemMeta mainMenuItemMeta = mainMenuItem.getItemMeta();
		mainMenuItemMeta.setDisplayName("§aBack to main menu");
		mainMenuItem.setItemMeta(mainMenuItemMeta);
		blockInfo.setItem(18, mainMenuItem);
		ItemStack previousPageItem = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		ItemMeta previousPageItemMeta = previousPageItem.getItemMeta();
		previousPageItemMeta.setDisplayName("§aBack to previous menu");
		previousPageItem.setItemMeta(previousPageItemMeta);
		blockInfo.setItem(19, previousPageItem);
		ItemStack frameItem25 = new ItemStack(Material.BARRIER);
		ItemMeta frameItem25Meta = frameItem25.getItemMeta();
		frameItem25Meta.setDisplayName("§aDelete block");
		List<String> frameItem25Lore = new ArrayList<>();
		frameItem25Lore.add("§aShift right click to delete this block");
		frameItem25Meta.setLore(frameItem25Lore);
		frameItem25.setItemMeta(frameItem25Meta);
		blockInfo.setItem(26, frameItem25);
		ItemStack frameItem4 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		ItemMeta frameItem4Meta = frameItem4.getItemMeta();
		frameItem4Meta.setDisplayName("§2Editing block: " + block.getBlock().name());
		frameItem4.setItemMeta(frameItem4Meta);
		blockInfo.setItem(0, frameItem4);
		ItemStack invItem1 = new ItemStack(Material.GOLD_INGOT);
		ItemMeta invItem1Meta = invItem1.getItemMeta();
		invItem1Meta.setDisplayName("§aCustom drops");
		invItem1.setItemMeta(invItem1Meta);
		blockInfo.setItem(13, invItem1);
		return blockInfo;
	}
	 static Inventory ListCustomDropsBlock(CBlock block, UUID tempUUID) {
		 Inventory customDropsInv = Bukkit.createInventory(null, 54, "§2Custom drops B");
		 customDropsInv = Frame(customDropsInv);
		 ItemStack mainMenuItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		 ItemMeta mainMenuItemMeta = mainMenuItem.getItemMeta();
		 mainMenuItemMeta.setDisplayName("§aBack to main menu");
		 mainMenuItem.setItemMeta(mainMenuItemMeta);
		 customDropsInv.setItem(45, mainMenuItem);
		 ItemStack frameItem1 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		 ItemMeta frameItem1Meta = frameItem1.getItemMeta();
		 frameItem1Meta.setDisplayName("§aBack to previous menu");
		 frameItem1.setItemMeta(frameItem1Meta);
		 customDropsInv.setItem(46, frameItem1);
		 ItemStack frameItem2 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		 ItemMeta frameItem2Meta = frameItem2.getItemMeta();
		 frameItem2Meta.setDisplayName("§2Editing block: " + block.getBlock().name());
		 frameItem2.setItemMeta(frameItem2Meta);
		 customDropsInv.setItem(0, frameItem2);
		 ItemStack frameItem3 = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		 ItemMeta frameItem3Meta = frameItem3.getItemMeta();
		 frameItem3Meta.setDisplayName("§aAdd new item");
		 frameItem3.setItemMeta(frameItem3Meta);
		 customDropsInv.setItem(8, frameItem3);
		 ItemStack frameItem4 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		 ItemMeta frameItem4Meta = frameItem4.getItemMeta();
		 frameItem4Meta.setDisplayName("§aCurrentt page: " + Main.lastPage.get(tempUUID));
		 frameItem4.setItemMeta(frameItem4Meta);
		 customDropsInv.setItem(52, frameItem4);
		 if (block.getCustomDrops() != null) {
			 if (Main.lastPage.get(tempUUID) == null || block.getCustomDrops().size() > 28 * ((Integer)Main.lastPage.get(tempUUID)).intValue()) {
				 ItemStack nextPageMaterial = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
				 ItemMeta nextPageMaterialMeta = nextPageMaterial.getItemMeta();
				 nextPageMaterialMeta.setDisplayName("§aNext page");
				 nextPageMaterial.setItemMeta(nextPageMaterialMeta);
				 customDropsInv.setItem(53, nextPageMaterial);
			 }
			 if (Main.lastPage.get(tempUUID) != null && ((Integer)Main.lastPage.get(tempUUID)).intValue() > 1) {
				 ItemStack previousPageMaterial = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
				 ItemMeta previousPageMaterialMeta = previousPageMaterial.getItemMeta();
				 previousPageMaterialMeta.setDisplayName("§aPrevious page");
				 previousPageMaterial.setItemMeta(previousPageMaterialMeta);
				 customDropsInv.setItem(51, previousPageMaterial);
			 }
			 int tempInt1 = 0;
			 int tempInt2 = 0;
			 for (CustomDrop customDrop : block.getCustomDrops()) {
				 if (tempInt2 > 27)
					 break;
				 if (Main.lastPage.get(tempUUID) != null && tempInt1 < 28 * (((Integer)Main.lastPage.get(tempUUID)).intValue() - 1)) {
					 tempInt1++;
					 continue;
				 }
				 ItemStack dropItemStack = customDrop.getDrop();
				 customDropsInv.setItem(((Integer)InventoryInside(54).get(tempInt2)).intValue(), dropItemStack);
				 tempInt2++;
			 }
		 }
		 return customDropsInv;
	 }
	 static Inventory AddCustomDropInventoryBlock(Player player, CBlock block) {
		 Inventory chooseDropInv = Bukkit.createInventory(null, 54, "§2Choose an item B");
		 chooseDropInv = Frame(chooseDropInv);
		 ItemStack frameItem0 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		 ItemMeta frameItem0Meta = frameItem0.getItemMeta();
		 frameItem0Meta.setDisplayName("§aBack to main menu");
		 frameItem0.setItemMeta(frameItem0Meta);
		 chooseDropInv.setItem(45, frameItem0);
		 ItemStack frameItem1 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		 ItemMeta frameItem1Meta = frameItem1.getItemMeta();
		 frameItem1Meta.setDisplayName("§aBack to previous menu");
		 frameItem1.setItemMeta(frameItem1Meta);
		 chooseDropInv.setItem(46, frameItem1);
		 ItemStack frameItem2 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		 ItemMeta frameItem2Meta = frameItem2.getItemMeta();
		 frameItem2Meta.setDisplayName("§2Editing block: " + block.getBlock().name());
		 frameItem2.setItemMeta(frameItem2Meta);
		 chooseDropInv.setItem(0, frameItem2);
		 PlayerInventory playerInventory = player.getInventory();
		 for (int i = 0; i < 36; i++) {
			 if (playerInventory.getItem(i) == null) {
				 chooseDropInv.setItem(i + 9, null);
			 } else {
				 chooseDropInv.setItem(i + 9, playerInventory.getItem(i));
			 }
		 }
		 return chooseDropInv;
	 }
	 static Inventory ShowCustomDropInfoBlock(CBlock block, CustomDrop tempDrop, int tempInt) {
		 Inventory customDropInfo = Bukkit.createInventory(null, 45, "§2Drop settings B");
		 customDropInfo = Frame(customDropInfo);
		 ItemStack frameItem0 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		 ItemMeta frameItem0Meta = frameItem0.getItemMeta();
		 frameItem0Meta.setDisplayName("§aBack to main menu");
		 frameItem0.setItemMeta(frameItem0Meta);
		 customDropInfo.setItem(36, frameItem0);
		 ItemStack frameItem1 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		 ItemMeta frameItem1Meta = frameItem1.getItemMeta();
		 frameItem1Meta.setDisplayName("§aBack to previous menu");
		 frameItem1.setItemMeta(frameItem1Meta);
		 customDropInfo.setItem(37, frameItem1);
		 ItemStack frameItem2 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		 ItemMeta frameItem2Meta = frameItem2.getItemMeta();
		 frameItem2Meta.setDisplayName("§2Editing block: " + block.getBlock().name());
		 List<String> tempList = new ArrayList<>();
		 tempList.add("§2Drop number: §a"+ tempInt);
		 frameItem2Meta.setLore(tempList);
		 frameItem2.setItemMeta(frameItem2Meta);
		 customDropInfo.setItem(0, frameItem2);
		 ItemStack frameItem3 = new ItemStack(Material.BARRIER);
		 ItemMeta frameItem3Meta = frameItem0.getItemMeta();
		 frameItem3Meta.setDisplayName("§aDelete drop");
		 List<String> tempList4 = new ArrayList<>();
		 tempList4.add("§aShift right click this to delete this item");
		 frameItem3Meta.setLore(tempList4);
		 frameItem3.setItemMeta(frameItem3Meta);
		 customDropInfo.setItem(44, frameItem3);
		 ItemStack customDrop = tempDrop.getDrop();
		 customDropInfo.setItem(10, customDrop);
		 ItemStack dropSettings = new ItemStack(Material.BOOKSHELF);
		 ItemMeta dropSettingsMeta = dropSettings.getItemMeta();
		 dropSettingsMeta.setDisplayName("§aDrop seasons list");
		 List<String> tempList5 = new ArrayList<>();
		 if (Main.plugin.UseSS == true) {
			 if (tempDrop.getSeasonDrop()) {
				 tempList5.add("§aEnabled, §cclick to disable");
				 tempList5.add("§aShift click to edit seasons");
			 } else {
				 tempList5.add("§cDisabled, §aclick to enable");
			 }
		 } else {
			 tempList5.add("§cDisabled, §cneed §a'Seasons' §cplugin to use this function");
		 }
		 dropSettingsMeta.setLore(tempList5);
		 dropSettings.setItemMeta(dropSettingsMeta);
		 customDropInfo.setItem(11, dropSettings);
		 ItemStack worldSettings = new ItemStack(Material.BOOKSHELF);
		 ItemMeta worldSettingsMeta = worldSettings.getItemMeta();
		 worldSettingsMeta.setDisplayName("§aDrop worlds list");
		 List<String> tempList6 = new ArrayList<>();
		 if (tempDrop.getWorldOverride()) {
			 tempList6.add("§aEnabled, §cclick to disable");
			 tempList6.add("§aShift click to edit seasons");
		 } else {
			 tempList6.add("§cDisabled, §aclick to enable [ Comming soon... ]");
		 }
		 worldSettingsMeta.setLore(tempList6);
		 worldSettings.setItemMeta(worldSettingsMeta);
		 customDropInfo.setItem(20, worldSettings);
		 if (tempDrop.getDropChance() > 0) {
			 ItemStack dropSettings0 = new ItemStack(Material.RED_CONCRETE);
			 ItemMeta dropSettings0Meta = dropSettings0.getItemMeta();
			 dropSettings0Meta.setDisplayName("§cDecrease chance by 0.01%");
			 dropSettings0.setItemMeta(dropSettings0Meta);
			 customDropInfo.setItem(22, dropSettings0);
			 if (tempDrop.getDropChance() > 9) {
				 ItemStack itemStack = new ItemStack(Material.RED_CONCRETE);
				 ItemMeta itemMeta = itemStack.getItemMeta();
				 itemMeta.setDisplayName("§cDecrease chance by 0.1%");
				 itemStack.setItemMeta(itemMeta);
				 customDropInfo.setItem(23, itemStack);
				 if (tempDrop.getDropChance() > 99) {
					 ItemStack itemStack1 = new ItemStack(Material.RED_CONCRETE);
					 ItemMeta itemMeta1 = itemStack1.getItemMeta();
					 itemMeta1.setDisplayName("§cDecrease chance by 1%");
					 itemStack1.setItemMeta(itemMeta1);
					 customDropInfo.setItem(24, itemStack1);
					 if (tempDrop.getDropChance() > 999) {
						 ItemStack dropSettings3 = new ItemStack(Material.RED_CONCRETE);
						 ItemMeta dropSettings3Meta = dropSettings3.getItemMeta();
						 dropSettings3Meta.setDisplayName("§cDecrease chance by 10%");
						 dropSettings3.setItemMeta(dropSettings3Meta);
						 customDropInfo.setItem(25, dropSettings3);
					 }
				 }
			 }
		 }
		 if (tempDrop.getDropChance() < 10000) {
			 ItemStack dropSettings0 = new ItemStack(Material.GREEN_CONCRETE);
			 ItemMeta dropSettings0Meta = dropSettings0.getItemMeta();
			 dropSettings0Meta.setDisplayName("§aIncrease chance by 0.01%");
			 dropSettings0.setItemMeta(dropSettings0Meta);
			 customDropInfo.setItem(13, dropSettings0);
			 if (tempDrop.getDropChance() < 9991) {
				 ItemStack itemStack = new ItemStack(Material.GREEN_CONCRETE);
				 ItemMeta itemMeta = itemStack.getItemMeta();
				 itemMeta.setDisplayName("§aIncrease chance by 0.1%");
				 itemStack.setItemMeta(itemMeta);
				 customDropInfo.setItem(14, itemStack);
				 if (tempDrop.getDropChance() < 9901) {
					 ItemStack itemStack1 = new ItemStack(Material.GREEN_CONCRETE);
					 ItemMeta itemMeta1 = itemStack1.getItemMeta();
					 itemMeta1.setDisplayName("§aIncrease chance by 1%");
					 itemStack1.setItemMeta(itemMeta1);
					 customDropInfo.setItem(15, itemStack1);
					 if (tempDrop.getDropChance() < 9001) {
						 ItemStack dropSettings3 = new ItemStack(Material.GREEN_CONCRETE);
						 ItemMeta dropSettings3Meta = dropSettings3.getItemMeta();
						 dropSettings3Meta.setDisplayName("§aIncrease chance by 10%");
						 dropSettings3.setItemMeta(dropSettings3Meta);
						 customDropInfo.setItem(16, dropSettings3);
					 }
				 }
			 }
		 }
		 ItemStack dropSettings1 = new ItemStack(Material.PAPER);
		 ItemMeta dropSettings1Meta = dropSettings1.getItemMeta();
		 if (tempDrop.getDropChance() % 100 < 10) {
			 dropSettings1Meta.setDisplayName("§2Current chance: §a" + (tempDrop.getDropChance() / 100) + ".0" + (tempDrop.getDropChance() % 100) + "%");
		 } else {
			 dropSettings1Meta.setDisplayName("§2Current chance: §a" + (tempDrop.getDropChance() / 100) + "." + (tempDrop.getDropChance() % 100) + "%");
		 }
		 dropSettings1.setItemMeta(dropSettings1Meta);
		 customDropInfo.setItem(12, dropSettings1);
		 ItemStack greenPane = new ItemStack(Material.LIME_CONCRETE);
		 ItemMeta greenPaneMeta = greenPane.getItemMeta();
		 List<String> tempList1 = new ArrayList<>();
		 tempList1.add("§aAllowed, click to deny");
		 greenPaneMeta.setLore(tempList1);
		 ItemStack redPane = new ItemStack(Material.RED_CONCRETE);
		 ItemMeta redPaneMeta = redPane.getItemMeta();
		 List<String> tempList2 = new ArrayList<>();
		 tempList2.add("§cDenied, click to allow");
		 redPaneMeta.setLore(tempList2);
		 if (tempDrop.getSpawnerDrop()) {
			 greenPaneMeta.setDisplayName("§aPlaced drops");
			 greenPane.setItemMeta(greenPaneMeta);
			 customDropInfo.setItem(28, greenPane);
		 } else {
			 redPaneMeta.setDisplayName("§aPlaced drops");
			 redPane.setItemMeta(redPaneMeta);
			 customDropInfo.setItem(28, redPane);
		 }
		 if (tempDrop.getNaturalDrop()) {
			 greenPaneMeta.setDisplayName("§aNatural drops");
			 greenPane.setItemMeta(greenPaneMeta);
			 customDropInfo.setItem(29, greenPane);
		 } else {
			 redPaneMeta.setDisplayName("§aNatural drops");
			 redPane.setItemMeta(redPaneMeta);
			 customDropInfo.setItem(29, redPane);
		 }
		 /*อันนี้เพิ่มเอง*/
		 //เปิดตอนกลางคืน
		 ItemStack timeDrop = new ItemStack(Material.GRAY_CONCRETE);
		 ItemMeta timeDropMeta = timeDrop.getItemMeta();
		 List<String> timeList1 = new ArrayList<>();
		 timeList1.add("§aEnabled, §7[ Night time ] §aclick to change");
		 timeDropMeta.setLore(timeList1);
		 //เปิดตอนกลางวัน
		 ItemStack timeDrop1 = new ItemStack(Material.YELLOW_CONCRETE);
		 ItemMeta timeDrop1Meta = timeDrop1.getItemMeta();
		 List<String> timeList2 = new ArrayList<>();
		 timeList2.add("§aEnabled, §e[ Day time ] §aclick to change");
		 timeDrop1Meta.setLore(timeList2);
		 //ปิด
		 ItemStack timeDrop2 = new ItemStack(Material.RED_CONCRETE);
		 ItemMeta timeDrop2Meta = timeDrop2.getItemMeta();
		 List<String> timeList3 = new ArrayList<>();
		 timeList3.add("§cDisabled, click to change");
		 timeDrop2Meta.setLore(timeList3);
		 if (tempDrop.getDropTime() < 0) {
			 timeDrop2Meta.setDisplayName("§aDrop time setting");
			 timeDrop2.setItemMeta(timeDrop2Meta);
			 customDropInfo.setItem(31, timeDrop2);
		 } else if (tempDrop.getDropTime() == 0) {
			 timeDrop1Meta.setDisplayName("§aDrop time setting");
			 timeDrop1.setItemMeta(timeDrop1Meta);
			 customDropInfo.setItem(31, timeDrop1);
		 } else if (tempDrop.getDropTime() == 12500) {
			 timeDropMeta.setDisplayName("§aDrop time setting");
			 timeDrop.setItemMeta(timeDropMeta);
			 customDropInfo.setItem(31, timeDrop);
		 }
 		 return customDropInfo;
	 }
	 static Inventory ListDropSeasonsBlock(Player player, CBlock block, CustomDrop tempDrop, int dropInt, int lastPage) {
		 Inventory listWorlds = Bukkit.createInventory(null, 54, "§2Drop seasons B");
		 listWorlds = Frame(listWorlds);
		 ItemStack frameItem0 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		 ItemMeta frameItem0Meta = frameItem0.getItemMeta();
		 frameItem0Meta.setDisplayName("§aBack to main menu");
		 frameItem0.setItemMeta(frameItem0Meta);
		 listWorlds.setItem(45, frameItem0);
		 ItemStack frameItem1 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		 ItemMeta frameItem1Meta = frameItem1.getItemMeta();
		 frameItem1Meta.setDisplayName("§aBack to previous menu");
		 frameItem1.setItemMeta(frameItem1Meta);
		 listWorlds.setItem(46, frameItem1);
		 ItemStack frameItem4 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		 ItemMeta frameItem4Meta = frameItem4.getItemMeta();
		 frameItem4Meta.setDisplayName("§aCurrent page: " + lastPage);
		 frameItem4.setItemMeta(frameItem4Meta);
		 listWorlds.setItem(52, frameItem4);
		 ItemStack frameItem2 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		 ItemMeta frameItem2Meta = frameItem2.getItemMeta();
		 frameItem2Meta.setDisplayName("§2Editing block: " + block.getBlock());
		 List<String> tempList = new ArrayList<>();
		 tempList.add("§2Drop number: §a" + dropInt);
		 frameItem2Meta.setLore(tempList);
		 frameItem2.setItemMeta(frameItem2Meta);
		 listWorlds.setItem(0, frameItem2);
		 if (player.getServer().getWorlds().size() > 28 * lastPage) {
			 ItemStack nextPageMaterial = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
			 ItemMeta nextPageMaterialMeta = nextPageMaterial.getItemMeta();
			 nextPageMaterialMeta.setDisplayName("§aNext page");
			 nextPageMaterial.setItemMeta(nextPageMaterialMeta);
			 listWorlds.setItem(53, nextPageMaterial);
		 }
		 if (lastPage > 1) {
			 ItemStack previousPageMaterial = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
			 ItemMeta previousPageMaterialMeta = previousPageMaterial.getItemMeta();
			 previousPageMaterialMeta.setDisplayName("§aPrevious page");
			 previousPageMaterial.setItemMeta(previousPageMaterialMeta);
			 listWorlds.setItem(51, previousPageMaterial);
		 }
		 int tempInt1 = 0;
		 int tempInt2 = 0;
		 for (String season : Season.getSeasonsList()) {
			 ItemStack seasonItemStack;
			 ItemMeta seasonItemStackMeta;
			 if (tempInt2 > 27)
				 break;
			 if (tempInt1 < 28 * (lastPage - 1)) {
				 tempInt1++;
				 continue;
			 }
			 if (tempDrop.getSeason() != null && tempDrop.getSeason().contains(ChatColor.stripColor(season.toUpperCase()))) {
				 seasonItemStack = new ItemStack(Material.LIME_BANNER);
				 seasonItemStackMeta = seasonItemStack.getItemMeta();
				 seasonItemStackMeta.setDisplayName(season.toUpperCase());
				 List<String> tempList1 = new ArrayList<>();
				 tempList1.add("§aEnabled, click to disable");
				 seasonItemStackMeta.setLore(tempList1);
			 } else {
				 seasonItemStack = new ItemStack(Material.RED_BANNER);
				 seasonItemStackMeta = seasonItemStack.getItemMeta();
				 seasonItemStackMeta.setDisplayName(season.toUpperCase());
				 List<String> tempList1 = new ArrayList<>();
				 tempList1.add("§cDisabled, click to enable");
				 seasonItemStackMeta.setLore(tempList1);
			 }
			 seasonItemStack.setItemMeta(seasonItemStackMeta);
			 listWorlds.setItem(((Integer)InventoryInside(54).get(tempInt2)).intValue(), seasonItemStack);
			 tempInt2++;
		 }
		 return listWorlds;
	 }
}