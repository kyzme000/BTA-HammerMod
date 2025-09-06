package kuzme.hammermod;

import kuzme.hammermod.item.ItemHammer;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.util.ItemInitEntrypoint;

import static kuzme.hammermod.HammerMod.MOD_ID;

public class HammerItems implements ItemInitEntrypoint {

	public static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeItems();
		}
	}

	public static int itemID = HammerConfig.itemIDs;

	public static int itemID(String itemName) {
		try {
			return HammerConfig.cfg.getInt(HammerConfig.itemIDs + "." + itemName);
		} catch (NullPointerException e) {
			HammerConfig.properties.addEntry(HammerConfig.itemIDs + "." + itemName, itemID);
			return itemID++;
		}
	}

	public static Item HAMMER;

	public static void initializeItems() {
		HAMMER = new ItemBuilder(MOD_ID)
			.build(new ItemHammer("hammer", "hammermod:hammer", itemID("HAMMER")));
	}

	@Override
	public void afterItemInit() {

	}

}
