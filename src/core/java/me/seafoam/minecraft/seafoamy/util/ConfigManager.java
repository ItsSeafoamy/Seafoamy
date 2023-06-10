package me.seafoam.minecraft.seafoamy.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

	private static final Gson gson = new GsonBuilder().setLenient().create();

	public static <T> T load(File dataFolder, String path, Class<T> clazz) {
		try {
			if (!path.endsWith(".json")) path += ".json";

			Path filePath = dataFolder.toPath().resolve(path);
			if (!Files.exists(filePath)) {
				try {
					URI uri = clazz.getResource("/" + path).toURI();
					Map<String, String> env = new HashMap<>();
					String[] array = uri.toString().split("!");
					FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);

					Files.createDirectories(filePath.getParent());
					Files.copy(fs.getPath(array[1]), filePath);

					fs.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}

			String json = Files.readString(dataFolder.toPath().resolve(path));

			return gson.fromJson(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}