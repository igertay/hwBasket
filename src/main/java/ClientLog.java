import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientLog {
    private String log = "";

    public void log(int productNum, int amount) {
        log += String.format("%d,%d%n", productNum, amount);
    }


    public void exportAsCSV(File txtFile) {
        if (!txtFile.exists()) {
            log = "productNum, amout\n" + log;
        }

        try (FileWriter writer = new FileWriter(txtFile, true)) {
            writer.write(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
