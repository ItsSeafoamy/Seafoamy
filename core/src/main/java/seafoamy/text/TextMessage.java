package me.seafoam.minecraft.seafoamy.text;

import java.util.*;
import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.command.CommandExecutor;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public abstract class TextMessage {

	protected String message;
	protected boolean parseTags;
	protected TextColor colour;
	protected List<TextDecoration> decorations = new ArrayList<>();
	protected List<TextDecoration> undecorations = new ArrayList<>();
	protected ClickEvent clickMessage;
	protected TextMessage hoverMessage;
	protected char center = (char) 0;
	protected TextColor centerColour;
	protected List<TextDecoration> centerFormats = new ArrayList<>();
	protected int centerSize = TextUtils.CHAT_SIZE;
	protected final HashMap<String, TextMessage> variables = new HashMap<>();
	protected final List<TextMessage> appends = new ArrayList<>();

	private static final LegacyComponentSerializer centerSerializer = LegacyComponentSerializer.legacySection().toBuilder().flattener(ComponentFlattener.textOnly()).build();

	public TextMessage(String message, boolean parseTags) {
		this.message = message;
		this.parseTags = parseTags;
	}

	public TextMessage colour(TextColor colour) {
		this.colour = colour;
		return this;
	}

	public TextMessage decorate(TextDecoration... decorations) {
		this.decorations.addAll(Arrays.asList(decorations));
		return this;
	}

	public TextMessage undecorate(TextDecoration... decorations) {
		this.undecorations.addAll(Arrays.asList(decorations));
		return this;
	}

	public TextMessage click(ClickEvent onClick) {
		clickMessage = onClick;
		return this;
	}

	public TextMessage hover(TextMessage onHover) {
		hoverMessage = onHover;
		return this;
	}

	public TextMessage variable(String key, TextMessage value) {
		variables.put(key, value);
		return this;
	}

	public TextMessage variable(String key, String rawText) {
		variables.put(key, new LiteralMessage(rawText));
		return this;
	}

	public TextMessage variable(String key, int value) {
		variables.put(key, new LiteralMessage(Integer.toString(value)));
		return this;
	}

	public TextMessage variable(String key, long value) {
		variables.put(key, new LiteralMessage(Long.toString(value)));
		return this;
	}

	public TextMessage variable(String key, float value) {
		variables.put(key, new LiteralMessage(Float.toString(value)));
		return this;
	}

	public TextMessage variable(String key, double value) {
		variables.put(key, new LiteralMessage(Double.toString(value)));
		return this;
	}

	public TextMessage variable(String key, char value) {
		variables.put(key, new LiteralMessage(Character.toString(value)));
		return this;
	}

	public TextMessage append(TextMessage other) {
		appends.add(other);
		return this;
	}

	public TextMessage center() {
		center = ' ';
		return this;
	}

	public TextMessage center(int size) {
		center = ' ';
		centerSize = size;
		return this;
	}

	public TextMessage center(char padding, TextColor colour, TextDecoration... formats) {
		this.center = padding;
		this.centerColour = colour;
		this.centerFormats.addAll(Arrays.asList(formats));

		return this;
	}

	public TextMessage center(char padding, int size, TextColor colour, TextDecoration... formats) {
		this.center = padding;
		this.centerSize = size;
		this.centerColour = colour;
		this.centerFormats.addAll(Arrays.asList(formats));

		return this;
	}

	public void send(CommandExecutor player) {
		player.sendMessage(this);
	}

	public void send(CommandExecutor[] players) {
		for (CommandExecutor player : players) player.sendMessage(this);
	}

	public void broadcast() {
		Seafoamy.getConsole().sendMessage(this);

		for (OnlinePlayer player : Seafoamy.getPlayerManager().getAllPlayers()) {
			player.sendMessage(this);
		}
	}

	public Component build(Language language) {
		MiniMessage mm = MiniMessage.miniMessage();
		List<TagResolver> placeholders = new ArrayList<>(variables.size());

		for (Map.Entry<String, TextMessage> entry : variables.entrySet()) {
			placeholders.add(Placeholder.component(entry.getKey(), entry.getValue().build(language)));
		}

		placeholders.add(TagResolver.builder().tag("player", (argumentQueue, context) -> {
			String player = argumentQueue.pop().value();

			try {
				UUID uuid = UUID.fromString(player);

				return Tag.selfClosingInserting(Component.object(ObjectContents.playerHead(uuid)));
			} catch (Exception e) {
				return Tag.inserting(Component.object(ObjectContents.playerHead(player)));
			}
		}).build());

		placeholders.add(TagResolver.builder().tag("sprite", (argumentQueue, context) -> {
			String atlas = argumentQueue.pop().value();
			String sprite = argumentQueue.pop().value();

			return Tag.selfClosingInserting(Component.object(ObjectContents.sprite(Key.key(atlas), Key.key(sprite))));
		}).build());

		placeholders.add(TagResolver.builder().tag("translate", (argumentQueue, context) -> {
			String toTranslate = argumentQueue.pop().value();

			return Tag.selfClosingInserting(new TranslatedMessage(toTranslate).build(language));
		}).build());

		String message = this.message;
		if (this instanceof TranslatedMessage) message = language.translate(message);

		// Fix MiniMessage annoyingly not accepting legacy colour codes
		message = ColourFixer.fix(message);

		Component component;
		if (this instanceof VanillaMessage) {
			component = Component.translatable(message).color(colour);
		} else if (this instanceof PlayerHeadMessage m) {
			if (message.equals("name")) {
				component = Component.object(ObjectContents.playerHead(m.name)).color(colour);
			} else if (message.equals("uuid")) {
				component = Component.object(ObjectContents.playerHead(m.uuid)).color(colour);
			} else throw new IllegalStateException();
		} else if (this instanceof SpriteMessage m) {
			component = Component.object(ObjectContents.sprite(Key.key(message), Key.key(m.sprite))).color(colour);
		} else if (parseTags) {
			component = mm.deserialize(message, placeholders.toArray(new TagResolver[0]));
			component = component.colorIfAbsent(colour);
		} else {
			String m = mm.escapeTags(message, placeholders.toArray(new TagResolver[0]));
			component = mm.deserialize(m, placeholders.toArray(new TagResolver[0])).color(colour);
		}

		for (TextDecoration format : undecorations) {
			component = component.decoration(format, false);
		}

		for (TextDecoration format : decorations) {
			component = component.decorate(format);
		}

		if (clickMessage != null) component = component.clickEvent(clickMessage);
		if (hoverMessage != null) component = component.hoverEvent(HoverEvent.showText(hoverMessage.build(language)));

		for (TextMessage append : appends) {
			component = component.append(append.build(language));
		}

		if (center != (char) 0) {
			component = TextUtils.centerMessage(component, center, centerSize, centerColour, centerFormats);
		}

		return component;
	}
}