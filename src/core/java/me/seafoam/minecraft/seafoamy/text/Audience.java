package me.seafoam.minecraft.seafoamy.text;

import me.seafoam.minecraft.seafoamy.util.Location;
import net.kyori.adventure.sound.Sound;

public interface Audience {

	void sendMessage();
	void sendMessage(TextMessage message);
	void sendMessage(TextMessage... messages);

	void sendTitle(TextMessage title);
	void sendSubtitle(TextMessage subtitle);
	void sendTitleAndSubtitle(TextMessage title, TextMessage subtitle);
	void clearTitles();
	void sendActionBar(TextMessage actionBar);

	void playSound(Sound sound);
	void playSound(Sound sound, Location location);
	void playSound(Sound sound, Sound.Emitter emitter);
}