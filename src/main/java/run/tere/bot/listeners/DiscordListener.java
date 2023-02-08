package run.tere.bot.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import run.tere.bot.Main;
import run.tere.bot.models.Coordinate;
import run.tere.bot.utils.CrossWordUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, 672, 672);
            graphics.setColor(Color.BLACK);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setFont(new Font("Josefin Sans SemiBold", Font.PLAIN, 32));

            int[][] board = CrossWordUtil.generate(4, 4);
            int i = 1;
            HashMap<Coordinate, Integer> wordIndex = new HashMap<>();
            for (int x = 0; x < board.length; x++) {
                int neighborSize = 0;
                int startIndex = 0;
                for (int y = 0; y < board[x].length; y++) {
                    int content = board[x][y];
                    if (content == 1 || y >= board[x].length - 1) {
                        if (y >= board[x].length - 1 && content != 1) neighborSize++;
                        if (neighborSize >= 2) {
                            wordIndex.put(new Coordinate(x, startIndex), i);
                            i++;
                        }
                        neighborSize = 0;
                    } else if (content == 0) {
                        if (neighborSize == 0) {
                            startIndex = y;
                        }
                        neighborSize++;
                    }
                }
            }

            for (int y = 0; y < board[0].length; y++) {
                int neighborSize = 0;
                int startIndex = 0;
                for (int x = 0; x < board.length; x++) {
                    int content = board[x][y];
                    if (content == 1 || x >= board.length - 1) {
                        if (x >= board.length - 1 && content != 1) neighborSize++;
                        if (neighborSize >= 2) {
                            Coordinate coordinate = new Coordinate(startIndex, y);
                            if (!wordIndex.containsKey(coordinate)) {
                                wordIndex.put(coordinate, i);
                                i++;
                            }
                        }
                        neighborSize = 0;
                    } else if (content == 0) {
                        if (neighborSize == 0) {
                            startIndex = x;
                        }
                        neighborSize++;
                    }
                }
            }

            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[x].length; y++) {
                    if (board[x][y] == 0) {
                        graphics.drawRect(16 + 128 * x, 16 + 128 * y, 128, 128);
                    } else if (board[x][y] == 1) {
                        graphics.fillRect(16 + 128 * x, 16 + 128 * y, 128, 128);
                    }
                    Coordinate coordinate = new Coordinate(x, y);
                    if (wordIndex.containsKey(coordinate)) graphics.drawString(String.valueOf(wordIndex.get(coordinate)), 24 + 128 * x, 48 + 128 * y);
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
