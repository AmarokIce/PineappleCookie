package club.someoneice.cookie.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class FileUtil {
    private FileUtil() {}

    public static void tryCreateFile(File file) throws IOException {
        File dir = new File(file.getPath().replace(file.getName(), ""));
        if (!dir.mkdir()) {
            throw new IOException("Cannot create dir for file: " + file.toPath());
        }

        if (!file.createNewFile()) {
            throw new IOException("Cannot create file: " + file.toPath());
        }
    }
}
