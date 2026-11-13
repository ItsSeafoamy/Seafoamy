package me.seafoam.minecraft.seafoamy.paper.util;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SkinData {

	public final UUID uuid;
	public final String texture, signature;

	/**
	 * Creates new SkinData with the specified texture. This constructor may be used for player heads, but <b>must not</b> be used for NPCs, which require signature too. Instead, use {@link #SkinData(String, String)}
	 */
	public SkinData(String texture) {
		this(UUID.randomUUID(), texture, "null");
	}

	/**
	 * Creates new SkinData with the specified texture and signature
	 */
	public SkinData(String texture, String signature) {
		this(UUID.randomUUID(), texture, signature);
	}

	/**
	 * Creates new SkinData with the specified texture and signature
	 */
	public SkinData(UUID uuid, String texture, String signature) {
		this.uuid = uuid;
		this.texture = texture;
		this.signature = signature;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SkinData other) {

			return other.texture.equals(texture) && other.signature.equals(signature);
		}
		return false;
	}

	/**
	 * Creates a new WrappedGameProfile with the specified player name, for use with packets with ProtocolLib
	 * @param name The player name
	 * @return The WrappedGameProfile
	 */
	public WrappedGameProfile toWrappedGameProfile(String name) {
		WrappedGameProfile profile = new WrappedGameProfile(uuid, name);

		profile.getProperties().put("textures", WrappedSignedProperty.fromValues("textures", texture, signature));

		return profile;
	}

	/**
	 * Creates a new GameProfile with the specified player name
	 * @param name The player name
	 * @return The GameProfile
	 */
	public GameProfile toGameProfile(String name) {
		return (GameProfile) toWrappedGameProfile(name).getHandle();
	}

	/**
	 * Gets the skin data from the specified player
	 * @param player The player whose skin data to get
	 * @return The player's skin data
	 */
	public static SkinData getSkinData(Player player) {
		WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);

		for (WrappedSignedProperty property : profile.getProperties().get("textures")) {
			if (property.getName().equalsIgnoreCase("textures")) {
				return new SkinData(profile.getUUID(), property.getValue(), property.getSignature());
			}
		}

		return null;
	}

	/**
	 * Gets the skin data from the specified offline player. This method is discouraged and should only be used when necessary, as repeated calls to this method may trigger Mojang's rate limits<br/>
	 * For online players, {@link #getSkinData(Player)} is preferred<br/>
	 * For static skins, use https://gist.github.com/Laxynd/a98aa6dc30600ea506da70b0c979ac51 to generate skin data from a player. This skin data will then remain with this skin, even if the player updates their skin<br/>
	 * If an up to date skin is required for an offline player, then this method becomes necessary
	 * @param player The player whose skin data to get
	 * @return The offline player's skin data
	 */
	public static SkinData getSkinData(OfflinePlayer player) {
		WrappedGameProfile profile = WrappedGameProfile.fromOfflinePlayer(player);

		for (WrappedSignedProperty property : profile.getProperties().get("textures")) {
			if (property.getName().equalsIgnoreCase("textures")) {
				return new SkinData(profile.getUUID(), property.getValue(), property.getSignature());
			}
		}

		return null;
	}
}