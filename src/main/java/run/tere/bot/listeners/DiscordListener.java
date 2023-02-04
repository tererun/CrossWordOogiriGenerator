package run.tere.bot.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import run.tere.bot.Main;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent e) {
        Main.getInstance().setJDA(e.getJDA());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        String label = e.getName();
        if (label.equalsIgnoreCase("cwgen")) {

        }
    }

}
