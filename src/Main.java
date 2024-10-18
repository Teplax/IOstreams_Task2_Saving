import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        GameProgress gp1 = new GameProgress(75, 12, 6, 74.3);
        GameProgress gp2 = new GameProgress(64, 26, 7, 115.3);
        GameProgress gp3 = new GameProgress(97, 7, 3, 12.6);

        String path1 = "C:/Users/tepla/Games/savegames/save1.dat";
        String path2 = "C:/Users/tepla/Games/savegames/save2.dat";
        String path3 = "C:/Users/tepla/Games/savegames/save3.dat";
        saveGame(path1, gp1);
        saveGame(path2, gp2);
        saveGame(path3, gp3);

        List<String> files = List.of(path1, path2, path3);

        zipFiles("C:/Users/tepla/Games/savegames/Zip_saved.zip", files);

        for (String file : files) {
            File toDelete = new File(file);
            if (toDelete.delete()) System.out.println("Файл " + toDelete.getName() + " удалён.");
        }

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // запишем экземпляр класса в файл
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> packagingFiles) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path))) {
            int i = 1;
            for (String file : packagingFiles) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry("packed_save" + i + ".dat");
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zos.write(buffer);
                    // закрываем текущую запись для новой записи
                    zos.closeEntry();
                    i++;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}