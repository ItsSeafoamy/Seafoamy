package me.seafoam.minecraft.seafoamy.text;

public class SpriteMessage extends TextMessage {

	protected String sprite;

	public SpriteMessage(String atlas, String sprite) {
		super(atlas.contains(":") ? atlas : "minecraft:" + atlas, false);

		this.sprite = sprite;
	}
}
