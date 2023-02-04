package run.tere.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {

    private static Main instance;
    private JDA jda;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        instance = this;
        JDABuilder.createDefault()
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