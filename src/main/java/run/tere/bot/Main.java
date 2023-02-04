package run.tere.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import run.tere.bot.config.ConfigHandler;
import run.tere.bot.listeners.DiscordListener;
import run.tere.bot.utils.CrossWordUtil;

public class Main {

    private static Main instance;

    private ConfigHandler configHandler;
    private JDA jda;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        instance = this;
        configHandler = ConfigHandler.load();

        JDABuilder
                .createDefault(configHandler.getDiscordBotToken())
                .addEventListeners(new DiscordListener())
                .build();
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public JDA getJDA() {
        return jda;
    }

    public void setJDA(JDA jda) {
        this.jda = jda;
    }

    public static Main getInstance() {
        return instance;
    }

}