package me.seafoam.minecraft.seafoamy.text;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

public class Language {

	private static final HashMap<String, Language> languages = new HashMap<>();
	@Getter @Setter private static Language defaultLanguage;

	@Getter private final String code;

	private final HashMap<String, String> map = new HashMap<>();

	private Language(String code) {
		this.code = code;
	}

	public static Language loadLanguage(String code, String namespace, String json) {
		Language lang = languages.computeIfAbsent(code, Language::new);

		Map<?, ?> jsonMap = new Gson().fromJson(json, Map.class);

		for (Map.Entry<?, ?> entry : jsonMap.entrySet()) {
			if (entry.getValue() instanceof String s) lang.map.put(namespace + ":" + entry.getKey(), s);
			else if (entry.getValue() instanceof LinkedTreeMap<?, ?> tree) lang.next(namespace + ":" + entry.getKey(), tree);
		}

		return lang;
	}

	public static Language loadLanguage(String code, String namespace, Object plugin) {
		try {
			URI uri = plugin.getClass().getClassLoader().getResource("lang/" + code + ".json").toURI();
			Map<String, String> env = new HashMap<>();
			String[] array = uri.toString().split("!");
			FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);

			String jsonString = Files.readString(fs.getPath(array[1]));
			fs.close();

			return loadLanguage(code, namespace, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void loadAllLanguages(Object plugin, String namespace) {
		try {
			URI uri = plugin.getClass().getClassLoader().getResource("lang").toURI();
			Map<String, String> env = new HashMap<>();
			String[] array = uri.toString().split("!");
			FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);

			Path path = fs.getPath(array[1]);

			try (Stream<Path> files = Files.list(path)) {
				files.forEach(p -> {
					try {
						String jsonString = Files.readString(p);
						String code = p.getFileName().toString().replace(".json", "");
						loadLanguage(code, namespace, jsonString);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}

			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void next(String key, LinkedTreeMap<?, ?> tree) {
		for (Map.Entry<?, ?> entry : tree.entrySet()) {
			String fullKey = key + "." + entry.getKey();

			if (entry.getValue() instanceof String s) map.put(fullKey, s);
			else if (entry.getValue() instanceof LinkedTreeMap<?, ?> nextTree) next(fullKey, nextTree);
		}
	}

	public String translate(String key) {
		return map.getOrDefault(key, key);
	}

	public static Language getLanguage(String code) {
		return languages.get(code);
	}
}