package me.seafoam.minecraft.seafoamy.command;

import me.seafoam.minecraft.seafoamy.text.Audience;
import me.seafoam.minecraft.seafoamy.text.Language;
import me.seafoam.minecraft.seafoamy.text.TextMessage;
import org.jetbrains.annotations.NotNull;

public interface CommandExecutor extends Audience {

	@NotNull String getName();
	@NotNull TextMessage getChatName();

	@NotNull Language getLanguage();
	void setLanguage(@NotNull Language language);

	boolean hasPermission(String permission);
}