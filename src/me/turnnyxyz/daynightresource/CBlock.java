package me.turnnyxyz.daynightresource;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.World;

public class CBlock {
	private Material block;
	
	private ArrayList<World> VanillaDrops;
	
	private ArrayList<CustomDrop> CustomDrops;
	
	public Material getBlock() {
		return this.block;
	}
	
	public void setBlock(Material temp) {
		this.block = temp;
	}
	
	public ArrayList<World> getVanillaDrops() {
		return this.VanillaDrops;
	}
	
	public void setVanillaDrops(ArrayList<World> temp) {
		this.VanillaDrops = temp;
	}
	
	public ArrayList<CustomDrop> getCustomDrops() {
		return this.CustomDrops;
	}
	
	public void setCustomDrops(ArrayList<CustomDrop> temp) {
		this.CustomDrops = temp;
	}
}
