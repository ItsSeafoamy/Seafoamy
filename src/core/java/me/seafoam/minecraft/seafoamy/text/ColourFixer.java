package me.seafoam.minecraft.seafoamy.text;

import java.util.HashMap;

/**
 * This class replaces Minecraft's colour codes with MiniMessage's colour codes.
 * For some reason MiniMessage throws an exception when it encounters Minecraft's colour codes.
 * Legacy colour codes shouldn't be used where possible, but this fixes the cases where they are used (like by the anti-cheat).
 */
public class ColourFixer {

	public static final HashMap<String, String> colourFixes = new HashMap<>();

	static {
		colourFixes.put("§0", "<black>");
		colourFixes.put("§1", "<dark_blue>");
		colourFixes.put("§2", "<dark_green>");
		colourFixes.put("§3", "<dark_aqua>");
		colourFixes.put("§4", "<dark_red>");
		colourFixes.put("§5", "<dark_purple>");
		colourFixes.put("§6", "<gold>");
		colourFixes.put("§7", "<gray>");
		colourFixes.put("§8", "<dark_gray>");
		colourFixes.put("§9", "<blue>");
		colourFixes.put("§a", "<green>");
		colourFixes.put("§b", "<aqua>");
		colourFixes.put("§c", "<red>");
		colourFixes.put("§d", "<light_purple>");
		colourFixes.put("§e", "<yellow>");
		colourFixes.put("§f", "<white>");
		colourFixes.put("§k", "<obfuscated>");
		colourFixes.put("§l", "<bold>");
		colourFixes.put("§m", "<strikethrough>");
		colourFixes.put("§n", "<underline>");
		colourFixes.put("§o", "<italic>");
		colourFixes.put("§r", "<reset>");
	}

	public static String fix(String message) {
		for (String colour : colourFixes.keySet()) {
			message = message.replace(colour, colourFixes.get(colour));
		}
		return message;
	}
}
