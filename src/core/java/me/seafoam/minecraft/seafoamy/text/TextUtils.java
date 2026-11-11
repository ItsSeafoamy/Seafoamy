package me.seafoam.minecraft.seafoamy.text;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ObjectComponent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class TextUtils {

	public final static int CHAT_SIZE = 308;
	public final static int MOTD_SIZE = 256;

	@Deprecated
	public static String getPaddingForCenter(String message) {
		return getPaddingForCenter(message, (char) 0, CHAT_SIZE);
	}

	@Deprecated
	public static String getPaddingForCenter(String message, char pad) {
		return getPaddingForCenter(message, pad, CHAT_SIZE);
	}

	@Deprecated
	public static String getPaddingForCenter(String message, int maxPixels) {
		return getPaddingForCenter(message, (char) 0, maxPixels);
	}

	@Deprecated
	public static String getPaddingForCenter(String message, char pad, int maxPixels) {
		String toCheck = message.replaceAll("§#[0-9a-fA-F]{6}", "").replaceAll("§x[0-9a-fA-F]{6}", "");
		double messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : toCheck.toCharArray()) {
			if (c == '§') {
				previousCode = true;
				continue;
			} else if (previousCode) {
				previousCode = false;
				if (c == 'l' || c == 'L') {
					isBold = true;
					continue;
				} else isBold = false;
			} else {
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;

			}
			if (messagePxSize >= maxPixels) {
				break;
			}
		}

		double halvedMessageSize = messagePxSize / 2.0;
		double toCompensate = (maxPixels / 2.0) - halvedMessageSize;
		double spaceLength = DefaultFontInfo.getDefaultFontInfo(pad == 0 ? ' ' : pad).getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();

		while (compensated < toCompensate) {
			sb.append(pad == 0 ? ' ' : pad);
			compensated += spaceLength;
		}

		return sb.toString();
	}

	public static Component centerMessage(Component toCenter, char pad, int maxWidth, TextColor colour, List<TextDecoration> decorations) {
		int width = getComponentWidth(toCenter, false);

		if (width >= maxWidth) return toCenter;

		int toCompensate = (maxWidth - width) / 2;
		int padLength = DefaultFontInfo.getDefaultFontInfo(pad).getLength();

		int compensated = 0;
		StringBuilder sb = new StringBuilder();

		while (compensated + padLength < toCompensate) {
			sb.append(pad);
			compensated += padLength;
		}

		Component padComponent = Component.text(sb.toString());

		if (colour != null) padComponent = padComponent.color(colour);
		if (decorations != null) {
			for (TextDecoration format : decorations) {
				padComponent = padComponent.decorate(format);
			}
		}

		return Component.empty().append(padComponent).append(toCenter).append(padComponent);
	}

	public static int getComponentWidth(Component component, boolean isParentBold) {
		int width = 0;

		boolean bold = switch (component.decoration(TextDecoration.BOLD)) {
			case TRUE -> true;
			case FALSE -> false;
			case NOT_SET -> isParentBold;
		};

		if (component instanceof TextComponent text) {
			String content = text.content();

			for (char c : content.toCharArray()) {
				DefaultFontInfo dfi = DefaultFontInfo.getDefaultFontInfo(c);
				if (bold) width += dfi.getBoldLength();
				else width += dfi.getLength();
			}
		} else if (component instanceof ObjectComponent) {
			width += 8;
		}

		for (Component child : component.children()) {
			width += getComponentWidth(child, bold);
		}

		return width;
	}

	public static TextMessage join(TextMessage on, TextMessage... messages) {
		if (messages.length == 0) return null;

		TextMessage result = messages[0];
		for (int i = 1; i < messages.length; i++) {
			result.append(on);
			result.append(messages[i]);
		}

		return result;
	}

	public static TextMessage join(TextMessage on, List<TextMessage> messages) {
		if (messages.isEmpty()) return null;

		TextMessage result = messages.getFirst();
		for (int i = 1; i < messages.size(); i++) {
			result.append(on);
			result.append(messages.get(i));
		}

		return result;
	}
}
