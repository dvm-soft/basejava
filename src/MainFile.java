import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Data\\Project\\java\\basejava\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error file", e);
        }

        File dir = new File("./src");
        System.out.println(dir.isDirectory());

        String[] list = dir.list();
        if (list != null) {
            for (String name : dir.list()) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        printDirectoryDeeply(new File("C:\\Data\\Project\\java\\JavaRush\\basejava\\src\\"), 0);

    }

    public static void printDirectoryDeeply(File dir, int deep) throws IOException {
        if (dir.isFile()) {
            System.out.println(String.join("", Collections.nCopies(deep, "  ")) +
                    dir.getName());
            return;
        }
        System.out.println(String.join("", Collections.nCopies(deep, "  ")) +
                dir.getName());
        for (File item : dir.listFiles()) {
            printDirectoryDeeply(item, deep + 1);
        }
    }
}
