package me.seafoam.minecraft.seafoamy.text;

public class LiteralMessage extends TextMessage {

	public LiteralMessage(String message) {
		super(message, true);
	}

	public LiteralMessage(String message, boolean parseTags) {
		super(message, parseTags);
	}

	public LiteralMessage(int value) {
		super(Integer.toString(value), false);
	}

	public LiteralMessage(long value) {
		super(Long.toString(value), false);
	}

	public LiteralMessage(float value) {
		super(Float.toString(value), false);
	}

	public LiteralMessage(double value) {
		super(Double.toString(value), false);
	}

	public LiteralMessage(char value) {
		super(Character.toString(value), false);
	}

	public LiteralMessage() {
		super("", false);
	}
}