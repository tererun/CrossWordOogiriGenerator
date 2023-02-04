package run.tere.bot.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static File getParentDirectory() {
        Path path = Paths.get("");
        return path.toAbsolutePath().toFile();
    }

}
