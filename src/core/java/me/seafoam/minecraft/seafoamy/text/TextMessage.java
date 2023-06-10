package me.seafoam.minecraft.seafoamy.text;

import java.util.*;
import java.util.regex.Pattern;
import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.command.CommandExecutor;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
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
		List<TagResolver.Single> placeholders = new ArrayList<>(variables.size());

		for (Map.Entry<String, TextMessage> entry : variables.entrySet()) {
			placeholders.add(Placeholder.component(entry.getKey(), entry.getValue().build(language)));
		}

		String message = this.message;
		if (this instanceof TranslatedMessage) message = language.translate(message);

		Component component;
		if (parseTags) {
			if (hasColour(message)) {
				component = mm.deserialize(message, placeholders.toArray(new TagResolver.Single[0]));
			} else {
				component = mm.deserialize(message, placeholders.toArray(new TagResolver.Single[0])).color(colour);
			}
		}
		else {
			String m = mm.escapeTags(message);
			component = mm.deserialize(m, placeholders.toArray(new TagResolver.Single[0])).color(colour);
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
			String legacy = LegacyComponentSerializer.legacySection().serialize(component);
			String padding = TextUtils.getPaddingForCenter(legacy, center, centerSize);
			Component pre = component;

			component = Component.text(padding).color(centerColour);

			for (TextDecoration format : centerFormats) {
				component = component.decorate(format);
			}

			component = Component.text("").append(component).append(pre).append(component);
		}

		return component;
	}

	private boolean hasColour(String message) {
		for (NamedTextColor colour : NamedTextColor.NAMES.values()) {
			if (message.toLowerCase().startsWith("<" + colour + ">") && message.toLowerCase().endsWith("</" + colour + ">")) {
				return true;
			}
		}

		return Pattern.matches("^<#[0-9a-zA-Z]{6}>.*<\\/#[0-9a-zA-Z]{6}>$", message);
	}
}