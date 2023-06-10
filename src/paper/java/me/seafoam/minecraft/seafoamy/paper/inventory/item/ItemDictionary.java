package me.seafoam.minecraft.seafoamy.paper.inventory.item;

import java.util.ArrayList;
import java.util.List;
import me.seafoam.minecraft.seafoamy.text.TextMessage;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ItemDictionary {

	private static final List<TextMessage> dictionary = new ArrayList<>();

	public static TextMessage get(int id) {
		if (id < 0 || id >= dictionary.size()) {
			return null;
		}

		return dictionary.get(id);
	}

	public static int add(TextMessage message) {
		dictionary.add(message);

		return dictionary.size() - 1;
	}
}