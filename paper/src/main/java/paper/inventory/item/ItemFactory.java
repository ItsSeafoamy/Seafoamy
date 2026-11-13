package me.seafoam.minecraft.seafoamy.paper.inventory.item;

import com.mojang.authlib.GameProfile;
import java.lang.reflect.Method;
import java.util.*;
import javax.annotation.CheckReturnValue;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import me.seafoam.minecraft.seafoamy.text.Language;
import me.seafoam.minecraft.seafoamy.text.TextMessage;
import me.seafoam.minecraft.seafoamy.paper.util.SkinData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 * Factory class for generating and modifying ItemStacks
 * @author Laxynd
 *
 */
public class ItemFactory {

	private final ItemStack itemStack;
	private final ItemMeta meta;

	private ItemFactory(ItemStack itemStack) {
		this.itemStack = itemStack;
		meta = itemStack.getItemMeta();
	}

	private ItemFactory(Material material, int amount) {
		itemStack = new ItemStack(material, amount);
		meta = itemStack.getItemMeta();
	}

	/**
	 * Start creating a new ItemStack of type <b>material</b>
	 */
	@CheckReturnValue
	public static ItemFactory createItemStack(Material material) {
		return new ItemFactory(material, 1);
	}

	/**
	 * Start creating a new ItemStack of type <b>material</b> and the specified <b>amount</b>
	 */
	@CheckReturnValue
	public static ItemFactory createItemStack(Material material, int amount) {
		return new ItemFactory(material, amount);
	}

	/**
	 * Prepares to modify an existing ItemStack
	 */
	@CheckReturnValue
	public static ItemFactory modifyItemStack(ItemStack itemStack) {
		return new ItemFactory(itemStack);
	}
	
	/**
	 * Clones an item stack and prepares it for modifying
	 * @param itemStack The ItemStack to clone
	 */
	@CheckReturnValue
	public static ItemFactory cloneItemStack(ItemStack itemStack) {
		return new ItemFactory(itemStack.clone());
	}

	/**
	 * Creates a new Player Head belonging to <b>skullOwner<b/>
	 */
	@CheckReturnValue
	public static ItemFactory createSkullItem(String skullOwner) {
		return new ItemFactory(Material.PLAYER_HEAD, 1).setSkullOwner(skullOwner);
	}

	/**
	 * Creates a new Player Head belonging to <b>skullOwner<b/>
	 */
	@CheckReturnValue
	public static ItemFactory createSkullItem(OfflinePlayer skullOwner) {
		return new ItemFactory(Material.PLAYER_HEAD, 1).setSkullOwner(skullOwner);
	}

	/**
	 * Creates a new Player Head belonging to <b>skullOwner<b/>
	 */
	@CheckReturnValue
	public static ItemFactory createSkullItem(SkinData skin) {
		return new ItemFactory(Material.PLAYER_HEAD, 1).setSkullOwner(skin);
	}

	/**
	 * Creates a new Potion with the given <b>PotionData</b>
	 */
	@CheckReturnValue
	public static ItemFactory createPotionItem(PotionData data) {
		return new ItemFactory(Material.POTION, 1).setBasePotionData(data);
	}

	/**
	 * Creates a new Potion of the given <b>PotionType</b><br/>
	 * In order to use custom durations and strengths, or to have multiple effects, use {@link #createPotionItem(PotionEffect...)} instead
	 */
	@CheckReturnValue
	public static ItemFactory createPotionItem(PotionType type) {
		return new ItemFactory(Material.POTION, 1).setBasePotionEffect(type);
	}

	/**
	 * Creates a new Potion
	 * @param type The Potion Effect of this potion
	 * @param extended Whether or not the potion effect should be extended
	 * @param upgraded Whether or not the potion effect should be upgraded
	 */
	@CheckReturnValue
	public static ItemFactory createPotionItem(PotionType type, boolean extended, boolean upgraded) {
		return new ItemFactory(Material.POTION, 1).setBasePotionEffect(type, extended, upgraded);
	}

	/**
	 * Creates a new Potion with custom Potion Effects
	 */
	@CheckReturnValue
	public static ItemFactory createPotionItem(PotionEffect... effects) {
		return new ItemFactory(Material.POTION, 1).addPotionEffects(effects);
	}

	/**
	 * Creates a new Potion with a custom Potion Effect
	 * @param effect The Potion Effect of this potion
	 * @param duration How long the effect should last for
	 * @param amplifier The level of the effect. Note that this value is 1 less than displayed to clients. i.e. Jump Boost II has an amplifier of 1
	 */
	@CheckReturnValue
	public static ItemFactory createPotionItem(PotionEffectType effect, int duration, int amplifier) {
		return new ItemFactory(Material.POTION, 1).addPotionEffect(effect, duration, amplifier);
	}

	/**
	 * Creates a new Splash Potion with the given <b>PotionData</b>
	 */
	@CheckReturnValue
	public static ItemFactory createSplashPotionItem(PotionData data) {
		return new ItemFactory(Material.SPLASH_POTION, 1).setBasePotionData(data);
	}

	/**
	 * Creates a new Splash Potion of the given <b>PotionType</b><br/>
	 * In order to use custom durations and strengths, or to have multiple effects, use {@link #createSplashPotionItem(PotionEffect...)} instead
	 */
	@CheckReturnValue
	public static ItemFactory createSplashPotionItem(PotionType type) {
		return new ItemFactory(Material.SPLASH_POTION, 1).setBasePotionEffect(type);
	}

	/**
	 * Creates a new Splash Potion
	 * @param type The Potion Effect of this splash potion
	 * @param extended Whether or not the potion effect should be extended
	 * @param upgraded Whether or not the potion effect should be upgraded
	 */
	@CheckReturnValue
	public static ItemFactory createSplashPotionItem(PotionType type, boolean extended, boolean upgraded) {
		return new ItemFactory(Material.SPLASH_POTION, 1).setBasePotionEffect(type, extended, upgraded);
	}

	/**
	 * Creates a new Splash Potion with custom Potion Effects
	 */
	@CheckReturnValue
	public static ItemFactory createSplashPotionItem(PotionEffect... effects) {
		return new ItemFactory(Material.SPLASH_POTION, 1).addPotionEffects(effects);
	}

	/**
	 * Creates a new Splash Potion with a custom Potion Effect
	 * @param effect The Potion Effect of this splash potion
	 * @param duration How long the effect should last for
	 * @param amplifier The level of the effect. Note that this value is 1 less than displayed to clients. i.e. Jump Boost II has an amplifier of 1
	 */
	@CheckReturnValue
	public static ItemFactory createSplashPotionItem(PotionEffectType effect, int duration, int amplifier) {
		return new ItemFactory(Material.SPLASH_POTION, 1).addPotionEffect(effect, duration, amplifier);
	}

	/**
	 * Creates a new Lingering Potion with the given <b>PotionData</b>
	 */
	@CheckReturnValue
	public static ItemFactory createLingeringPotionItem(PotionData data) {
		return new ItemFactory(Material.LINGERING_POTION, 1).setBasePotionData(data);
	}

	/**
	 * Creates a new Lingering Potion of the given <b>PotionType</b><br/>
	 * In order to use custom durations and strengths, or to have multiple effects, use {@link #createLingeringPotionItem(PotionEffect...)} instead
	 */
	@CheckReturnValue
	public static ItemFactory createLingeringPotionItem(PotionType type) {
		return new ItemFactory(Material.LINGERING_POTION, 1).setBasePotionEffect(type);
	}

	/**
	 * Creates a new Lingering Potion
	 * @param type The Potion Effect of this lingering potion
	 * @param extended Whether or not the potion effect should be extended
	 * @param upgraded Whether or not the potion effect should be upgraded
	 */
	@CheckReturnValue
	public static ItemFactory createLingeringPotionItem(PotionType type, boolean extended, boolean upgraded) {
		return new ItemFactory(Material.LINGERING_POTION, 1).setBasePotionEffect(type, extended, upgraded);
	}

	/**
	 * Creates a new Lingering Potion with custom Potion Effects
	 */
	@CheckReturnValue
	public static ItemFactory createLingeringPotionItem(PotionEffect... effects) {
		return new ItemFactory(Material.LINGERING_POTION, 1).addPotionEffects(effects);
	}

	/**
	 * Creates a new Lingering Potion with a custom Potion Effect
	 * @param effect The Potion Effect of this lingering potion
	 * @param duration How long the effect should last for
	 * @param amplifier The level of the effect. Note that this value is 1 less than displayed to clients. i.e. Jump Boost II has an amplifier of 1
	 */
	@CheckReturnValue
	public static ItemFactory createLingeringPotionItem(PotionEffectType effect, int duration, int amplifier) {
		return new ItemFactory(Material.LINGERING_POTION, 1).addPotionEffect(effect, duration, amplifier);
	}

	/**
	 * Sets the amount of items in this stack
	 * @param amount The amount of items in this stack
	 */
	@CheckReturnValue
	public ItemFactory setAmount(int amount) {
		itemStack.setAmount(amount);
		return this;
	}

	/**
	 * Sets the name of this ItemStack. To set the lore at the same time, use {@link #setNameAndLore(TextMessage, TextMessage...)}
	 * @param name The new name of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setName(TextMessage name) {
		int id = ItemDictionary.add(name.undecorate(TextDecoration.ITALIC));
		meta.displayName(Component.text("§{i." + id + "}"));
		return this;
	}

	/**
	 * Sets the name of this ItemStack. To set the lore at the same time, use {@link #setNameAndLore(Language, TextMessage, TextMessage...)}
	 * @param name The new name of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setName(Language language, TextMessage name) {
		meta.displayName(name.undecorate(TextDecoration.ITALIC).build(language));
		return this;
	}

	/**
	 * Sets the name of this ItemStack. To set the lore at the same time, use {@link #setNameAndLore(Component, Component...)}
	 * @param name The new name of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setName(Component name) {
		meta.displayName(name);
		return this;
	}

	/**
	 * Sets the lore of this ItemStack. To set the name at the same time, use {@link #setNameAndLore(TextMessage, TextMessage...)}
	 * @param lore The new lore of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setLore(TextMessage... lore) {
		List<Component> loreComponent = new ArrayList<>();

		for (TextMessage line : lore) {
			if (line == null) {
				loreComponent.add(Component.text(""));
			} else {
				int id = ItemDictionary.add(line.undecorate(TextDecoration.ITALIC));
				loreComponent.add(Component.text("§{i." + id + "}"));
			}
		}

		meta.lore(loreComponent);

		return this;
	}

	/**
	 * Sets the lore of this ItemStack. To set the name at the same time, use {@link #setNameAndLore(Language, TextMessage, TextMessage...)}
	 * @param lore The new lore of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setLore(Language language, TextMessage... lore) {
		List<Component> loreComponent = new ArrayList<>();

		for (TextMessage line : lore) {
			if (line == null) {
				loreComponent.add(Component.text(""));
			} else {
				loreComponent.add(line.undecorate(TextDecoration.ITALIC).build(language));
			}
		}

		meta.lore(loreComponent);

		return this;
	}

	/**
	 * Sets the lore of this ItemStack. To set the name at the same time, use {@link #setNameAndLore(Component, Component...)}
	 * @param lore The new lore of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setLore(Component... lore) {
		meta.lore(Arrays.asList(lore));
		return this;
	}

	/**
	 * Sets the name and lore of this ItemStack
	 * @param name The new name of this ItemStack
	 * @param lore The new lore of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setNameAndLore(TextMessage name, TextMessage... lore) {
		return setName(name).setLore(lore);
	}

	/**
	 * Sets the name and lore of this ItemStack
	 * @param name The new name of this ItemStack
	 * @param lore The new lore of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setNameAndLore(Language language, TextMessage name, TextMessage... lore) {
		return setName(language, name).setLore(language, lore);
	}

	/**
	 * Sets the name and lore of this ItemStack
	 * @param name The new name of this ItemStack
	 * @param lore The new lore of this ItemStack
	 */
	@CheckReturnValue
	public ItemFactory setNameAndLore(Component name, Component... lore) {
		return setName(name).setLore(lore);
	}

	/**
	 * Adds a new enchantment to this ItemStack
	 * @param enchantment The enchantment to add
	 * @param level The level of the enchantment
	 */
	@CheckReturnValue
	public ItemFactory addEnchantment(Enchantment enchantment, int level) {
		meta.addEnchant(enchantment, level, true);
		return this;
	}

	/**
	 * Adds new enchantments to this ItemStack
	 * @param enchantments A map of enchantments where the key represents the enchantment and the value represents the level
	 */
	@CheckReturnValue
	public ItemFactory addEnchantments(Map<Enchantment, Integer> enchantments) {
		for (Map.Entry<Enchantment, Integer> enchant : enchantments.entrySet()) {
			meta.addEnchant(enchant.getKey(), enchant.getValue(), true);
		}
		return this;
	}
	
	/**
	 * Removes enchantments from this ItemStack
	 * @param enchantments The enchantments to remove
	 */
	@CheckReturnValue
	public ItemFactory removeEnchantments(Enchantment... enchantments) {
		for (Enchantment enchant : enchantments) {
			meta.removeEnchant(enchant);
		}
		return this;
	}

	/**
	 * Sets whether or not this ItemStack is unbreakable
	 * @param unbreakable True if this ItemStack will not lose any durability, false otherwise
	 */
	@CheckReturnValue
	public ItemFactory setUnbreakable(boolean unbreakable) {
		meta.setUnbreakable(unbreakable);
		return this;
	}

	/**
	 * Sets whether or not this ItemStack is unbreakable, and if the player can see this
	 * @param unbreakable True if this ItemStack will not lose any durability, false otherwise
	 * @param hide True to hide the "Unbreakable" text, false to show it. Will never be visible if <b>unbreakable</b> is false
	 */
	@CheckReturnValue
	public ItemFactory setUnbreakable(boolean unbreakable, boolean hide) {
		meta.setUnbreakable(unbreakable);

		if (hide && unbreakable) meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

		return this;
	}

	/**
	 * Adds an attribute to this ItemStack
	 * @param attribute The attribute to add
	 * @param name The name of the attribute
	 * @param amount The amount 
	 * @param operation The operation to apply
	 * @param slots Which EquipmentSlots this attribute is active on. Set to <b>null</b> to apply to all slots
	 */
	@CheckReturnValue
	public ItemFactory addAttribute(Attribute attribute, String name, double amount, AttributeModifier.Operation operation, EquipmentSlot... slots) {
		for (EquipmentSlot slot : slots) {
			AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), name, amount, operation, slot);
			meta.addAttributeModifier(attribute, modifier);
		}
		return this;
	}

	/**
	 * Adds an attribute to this ItemStack. Will set the name based on the attribute added
	 * @param attribute The attribute to add
	 * @param amount The amount 
	 * @param operation The operation to apply
	 * @param slots Which EquipmentSlots this attribute is active on. Set to <b>null</b> to apply to all slots
	 */
	@CheckReturnValue
	public ItemFactory addAttribute(Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot... slots) {
		return addAttribute(attribute, attribute.name().toLowerCase().replaceFirst("_", "."), amount, operation, slots);
	}

	/**
	 * Adds an attribute to this ItemStack. Will use AttributeModifier.Operation.ADD_NUMBER
	 * @param attribute The attribute to add
	 * @param name The name of the attribute
	 * @param amount The amount 
	 * @param slots Which EquipmentSlots this attribute is active on. Set to <b>null</b> to apply to all slots
	 */
	@CheckReturnValue
	public ItemFactory addAttribute(Attribute attribute, String name, double amount, EquipmentSlot... slots) {
		return addAttribute(attribute, name, amount, AttributeModifier.Operation.ADD_NUMBER, slots);
	}

	/**
	 * Adds an attribute to this ItemStack. Will use AttributeModifier.Operation.ADD_NUMBER, and set the name based on the attribute added
	 * @param attribute The attribute to add
	 * @param amount The amount 
	 * @param slots Which EquipmentSlots this attribute is active on. Set to <b>null</b> to apply to all slots
	 */
	@CheckReturnValue
	public ItemFactory addAttribute(Attribute attribute, double amount, EquipmentSlot... slots) {
		return addAttribute(attribute, amount, AttributeModifier.Operation.ADD_NUMBER, slots);
	}
	
	/**
	 * Removes attributes from this ItemStack
	 * @param attributes The attributes to remove
	 */
	@CheckReturnValue
	public ItemFactory removeAttributes(Attribute... attributes) {
		for (Attribute attribute : attributes) {
			meta.removeAttributeModifier(attribute);
		}
		return this;
	}
	
	/**
	 * Removes attributes from the specified EquipmentSlots
	 * @param slots The slots in which to remove attributes from
	 */
	@CheckReturnValue
	public ItemFactory removeAttributes(EquipmentSlot... slots) {
		for (EquipmentSlot slot : slots) {
			meta.removeAttributeModifier(slot);
		}
		return this;
	}

	/**
	 * Add Item Flags to this ItemStack
	 * @param flags The flags to add
	 */
	@CheckReturnValue
	public ItemFactory addItemFlags(ItemFlag... flags) {
		meta.addItemFlags(flags);
		return this;
	}

	/**
	 * Remove Item Flags from this ItemStack
	 * @param flags The flags to remove
	 */
	@CheckReturnValue
	public ItemFactory removeItemFlags(ItemFlag... flags) {
		meta.removeItemFlags(flags);
		return this;
	}

	/**
	 * Adds an event to this Item that will be called when the player interacts with it
	 * @param listener The listener to add
	 */
	@CheckReturnValue
	public ItemFactory setOnInteract(ItemInteractHandler listener) {
		ItemIdentifier itemId = new ItemIdentifier(itemStack.getType(), meta.displayName(), null);

		ItemInteractListener.instance.addHandler(itemId, listener);

		return this;
	}

	/**
	 * Adds an event to this Item that will be called when the player interacts with it
	 * @param listener The listener to add
	 */
	@CheckReturnValue
	public ItemFactory setOnInteract(OnlinePlayer player, ItemInteractHandler listener) {
		ItemIdentifier itemId = new ItemIdentifier(itemStack.getType(), meta.displayName(), player);

		ItemInteractListener.instance.addHandler(itemId, listener);

		return this;
	}

	@CheckReturnValue
	public ItemFactory setUnlimited(boolean unlimited) {
		ItemIdentifier itemId = new ItemIdentifier(itemStack.getType(), meta.displayName(), null);

		if (unlimited) {
			ItemInteractListener.instance.unlimited.add(itemId);
		} else {
			ItemInteractListener.instance.unlimited.remove(itemId);
		}

		return this;
	}

	/**
	 * Sets the remaining durability on this ItemStack, i.e. 0 = broken
	 * @param durability The remaining durability
	 * @throws IllegalStateException if this item is not damageable
	 */
	@CheckReturnValue
	public ItemFactory setDurability(int durability) {
		if (!(meta instanceof Damageable d)) throw new IllegalStateException("Item is not damageable");

		d.setDamage(itemStack.getType().getMaxDurability() - durability);
		return this;
	}

	/**
	 * Sets the amount of damage this item has received, i.e. 0 = full durability
	 * @param damage The damage amount
	 * @throws IllegalStateException if this item is not damageable
	 */
	@CheckReturnValue
	public ItemFactory setDamage(int damage) {
		if (!(meta instanceof Damageable d)) throw new IllegalStateException("Item is not damageable");

		d.setDamage(damage);
		return this;
	}

	/**
	 * Sets the owner of a Player Head
	 * @param name The name of the player who this head belongs to
	 * @throws IllegalStateException if this item is not a player head
	 */
	@CheckReturnValue
	public ItemFactory setSkullOwner(String name) {
		if (!(meta instanceof SkullMeta)) throw new IllegalStateException("Item is not a player head!");

		return setSkullOwner(Bukkit.getOfflinePlayer(name));
	}

	/**
	 * Sets the owner of a Player Head
	 * @param player The player who this head belongs to
	 * @throws IllegalStateException if this item is not a player head
	 */
	@CheckReturnValue
	public ItemFactory setSkullOwner(OfflinePlayer player) {
		if (!(meta instanceof SkullMeta sm)) throw new IllegalStateException("Item is not a player head!");

		sm.setOwningPlayer(player);
		return this;
	}

	/**
	 * Sets the skin of a player head
	 * @param skin The skin to give this player head
	 * @throws IllegalStateException if this item is not a player head
	 */
	@CheckReturnValue
	public ItemFactory setSkullOwner(SkinData skin) {
		if (!(meta instanceof SkullMeta sm)) throw new IllegalStateException("Item is not a player head!");

		try {
			Method setProfile = sm.getClass().getDeclaredMethod("setProfile", GameProfile.class);
			setProfile.setAccessible(true);

			GameProfile gp = skin.toGameProfile("Player");

			setProfile.invoke(sm, gp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Sets the base PotionData of this item, which will also set the default name and colour.<br/>
	 * In order to use custom durations and strengths, or to have multiple effects, use {@link #addPotionEffects(PotionEffect...)} or {@link #addPotionEffect(PotionEffectType, int, int)}
	 * @param data The base PotionData
	 * @throws IllegalStateException if this item is not a Potion, Splash Potion, or Lingering Potion
	 */
	@CheckReturnValue
	public ItemFactory setBasePotionData(PotionData data) {
		if (!(meta instanceof PotionMeta pm)) throw new IllegalStateException("Item is not a potion!");

		pm.setBasePotionData(data);

		return this;
	}

	/**
	 * Sets the base PotionType of this item, which will also set the default name and colour.<br/>
	 * In order to use custom durations and strengths, or to have multiple effects, use {@link #addPotionEffects(PotionEffect...)} or {@link #addPotionEffect(PotionEffectType, int, int)}
	 * @param type The base PotionType
	 * @throws IllegalStateException if this item is not a Potion, Splash Potion, or Lingering Potion
	 */
	@CheckReturnValue
	public ItemFactory setBasePotionEffect(PotionType type) {
		if (!(meta instanceof PotionMeta pm)) throw new IllegalStateException("Item is not a potion!");

		pm.setBasePotionData(new PotionData(type));

		return this;
	}

	/**
	 * Sets the base PotionData of this item, which will also set the default name and colour.<br/>
	 * In order to use custom durations and strengths, or to have multiple effects, use {@link #addPotionEffects(PotionEffect...)} or {@link #addPotionEffect(PotionEffectType, int, int)}
	 * @param type The base PotionType
	 * @param extended Whether or not this effect is extended
	 * @param upgraded Whether or not this effect is upgraded
	 * @throws IllegalStateException if this item is not a Potion, Splash Potion, or Lingering Potion
	 */
	@CheckReturnValue
	public ItemFactory setBasePotionEffect(PotionType type, boolean extended, boolean upgraded) {
		if (!(meta instanceof PotionMeta pm)) throw new IllegalStateException("Item is not a potion!");

		pm.setBasePotionData(new PotionData(type, extended, upgraded));

		return this;
	}

	/**
	 * Adds custom potion effects to this potion
	 * @param effects The Potion Effects to add to this potion
	 * @throws IllegalStateException if this item is not a Potion, Splash Potion, or Lingering Potion
	 */
	@CheckReturnValue
	public ItemFactory addPotionEffects(PotionEffect... effects) {
		if (!(meta instanceof PotionMeta pm)) throw new IllegalStateException("Item is not a potion!");

		for (PotionEffect effect : effects) {
			pm.addCustomEffect(effect, true);
		}

		return this;
	}

	/**
	 * Adds a custom potion effect to this item
	 * @param effect The type of Potion Effect to add to this potion
	 * @param duration How long the effect should last for
	 * @param amplifier The level of the effect. Note that this value is 1 less than displayed to clients. i.e. Jump Boost II has an amplifier of 1
	 * @throws IllegalStateException if this item is not a Potion, Splash Potion, or Lingering Potion
	 */
	@CheckReturnValue
	public ItemFactory addPotionEffect(PotionEffectType effect, int duration, int amplifier) {
		if (!(meta instanceof PotionMeta pm)) throw new IllegalStateException("Item is not a potion!");

		pm.addCustomEffect(new PotionEffect(effect, duration, amplifier), true);

		return this;
	}

	/**
	 * Sets the potion colour
	 * @param colour The colour to set this potion
	 * @throws IllegalStateException if this item is not a Potion, Splash Potion, or Lingering Potion
	 */
	@CheckReturnValue
	public ItemFactory setPotionColour(Color colour) {
		if (!(meta instanceof PotionMeta pm)) throw new IllegalStateException("Item is not a potion!");

		pm.setColor(colour);

		return this;
	}

	/**
	 * Builds the ItemStack created or modified from this Factory
	 * @return The built ItemStack
	 */
	public ItemStack build() {
		itemStack.setItemMeta(meta);
		return itemStack;
	}
}