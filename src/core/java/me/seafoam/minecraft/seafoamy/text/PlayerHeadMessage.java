package me.seafoam.minecraft.seafoamy.text;

import java.util.UUID;

public class PlayerHeadMessage extends TextMessage {

	protected String name;
	protected UUID uuid;

	public PlayerHeadMessage(String name) {
		super("name", false);

		this.name = name;
	}

	public PlayerHeadMessage(UUID uuid) {
		super("uuid", false);

		this.uuid = uuid;
	}
}
