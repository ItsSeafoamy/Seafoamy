package me.seafoam.minecraft.seafoamy.text;

public class TranslatedMessage extends TextMessage {

	public TranslatedMessage(String message) {
		super(message, true);
	}

	public TranslatedMessage(String message, boolean parseTags) {
		super(message, parseTags);
	}
}