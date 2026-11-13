package me.seafoam.minecraft.seafoamy.player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PlayerWrapper {

	private static final List<Class<? extends WrappedPlayer>> classes = new ArrayList<>();

	public static void registerWrapper(Class<? extends WrappedPlayer> clazz) {
		classes.add(clazz);
	}

	public static void unregisterWrapper(Class<? extends WrappedPlayer> clazz) {
		classes.remove(clazz);
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends WrappedPlayer>[] getWrappers() {
		return classes.toArray((Class<? extends WrappedPlayer>[]) Array.newInstance(Class.class, classes.size()));
	}
}