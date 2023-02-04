package run.tere.bot.config;

import run.tere.bot.utils.FileUtil;
import run.tere.bot.utils.JsonUtil;

import java.io.File;

public class ConfigHandler {

    private String discordBotToken;

    public ConfigHandler() {
        this.discordBotToken = "";
    }

    public String getDiscordBotToken() {
        return discordBotToken;
    }

    public void save() {
        JsonUtil.toJson(getConfigFile(), this);
    }

    public static ConfigHandler load() {
        ConfigHandler configHandler = (ConfigHandler) JsonUtil.fromJson(getConfigFile(), ConfigHandler.class);
        if (configHandler == null) {
            configHandler = new ConfigHandler();
            configHandler.save();
        }
        return configHandler;
    }

    public static File getConfigFile() {
        return new File(FileUtil.getParentDirectory(), "configHandler.json");
    }

}
