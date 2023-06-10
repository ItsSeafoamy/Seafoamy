package me.seafoam.minecraft.seafoamy.text;

public class TextUtils {

	public final static int CHAT_SIZE = 308;
	public final static int MOTD_SIZE = 256;

	public static String getPaddingForCenter(String message) {
		return getPaddingForCenter(message, (char) 0, CHAT_SIZE);
	}

	public static String getPaddingForCenter(String message, char pad) {
		return getPaddingForCenter(message, pad, CHAT_SIZE);
	}

	public static String getPaddingForCenter(String message, int maxPixels) {
		return getPaddingForCenter(message, (char) 0, maxPixels);
	}

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
}