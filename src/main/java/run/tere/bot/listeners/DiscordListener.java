package run.tere.bot.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import run.tere.bot.Main;
import run.tere.bot.utils.CrossWordUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent e) {
        JDA jda = e.getJDA();
        Main.getInstance().setJDA(jda);
        jda.upsertCommand("cwgen", "クロスワード大喜利のシートを生成する").queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        String label = e.getName();
        if (label.equalsIgnoreCase("cwgen")) {

            BufferedImage bufferedImage = new BufferedImage(672, 672, BufferedImage.TYPE_3BYTE_BGR);
            Graphics graphics = bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, 672, 672);
            graphics.setColor(Color.BLACK);

            int[][] board = CrossWordUtil.generate(5, 5);
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[x].length; y++) {
                    if (board[x][y] == 0) {
                        graphics.drawRect(16 + 128 * x, 16 + 128 * y, 128, 128);
                    } else if (board[x][y] == 1) {
                        graphics.fillRect(16 + 128 * x, 16 + 128 * y, 128, 128);
                    }
                }
            }

            graphics.dispose();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                e.replyFiles(FileUpload.fromData(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), "result.png")).queue();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
