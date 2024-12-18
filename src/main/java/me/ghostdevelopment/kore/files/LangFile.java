package me.ghostdevelopment.kore.files;

import lombok.SneakyThrows;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.utils.Color;
import me.ghostdevelopment.kore.utils.Console;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class LangFile {

    private static final Kore main = Kore.getInstance();
    private static File file;
    private static FileConfiguration config;

    public static void setUp() {
        Arrays.asList("it", "en", "ru", "es", "fr", main.getConfig().getString("messages")).forEach(lang -> setUp(lang));
    }

    @SneakyThrows
    public static void setUp(String lang) {
        File langFolder = new File(main.getDataFolder().getPath() + "/lang/");
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }

        File file = new File(langFolder, lang + ".yml");
        if (!file.exists()) {
            String resourcePath = "lang/" + lang + ".yml";
            if (main.getResource(resourcePath) == null) {
                file.createNewFile();
            } else {
                main.saveResource(resourcePath, false);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        FileConfiguration defaultConfig = loadDefaultConfig(lang);
        updateFile(defaultConfig, config, file);
    }

    private static FileConfiguration loadDefaultConfig(String lang) {
        String resourcePath = "lang/" + lang + ".yml";
        InputStream resourceInputStream = main.getResource(resourcePath);
        if (resourceInputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(resourceInputStream, StandardCharsets.UTF_8);
            return YamlConfiguration.loadConfiguration(inputStreamReader);
        }
        return null;
    }

    public static void updateFile(FileConfiguration defaultConfig, FileConfiguration config, File file) {
        if (defaultConfig != null) {
            boolean updated = false;
            for (String key : defaultConfig.getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defaultConfig.get(key));
                    updated = true;
                }
            }
            if (updated) {
                try {
                    config.save(file);
                } catch (Exception e) {
                    Console.warning(e.getMessage());
                }
            }
        }
    }

    public static boolean checkFileExists(String lang) {
        File langFolder = new File(main.getDataFolder().getPath() + "/lang/");
        File file = new File(langFolder, lang + ".yml");
        return file.exists();
    }

    public static FileConfiguration getFile() {
        return config;
    }

    public static void reload() {
        setUp();
    }

    public static void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            Console.warning(e.getMessage());
        }
    }

    public static List<String> getAvailableLanguages() {
        List<String> languages = new ArrayList<>();
        File langFolder = new File(main.getDataFolder().getPath() + "/lang/");
        File[] files = langFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".yml")) {
                    languages.add(file.getName().replace(".yml", ""));
                }
            }
        }
        return languages;
    }

    public static String getString(String path) {
        return Color.Color(getFile().getString(path).replaceAll("%prefix%", getFile().getString("prefix")));
    }
}