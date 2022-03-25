package ru.netology.core6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<GameProgress> progressList = new ArrayList<>();
        progressList.add(new GameProgress(57, 3, 4, 2.6));
        progressList.add(new GameProgress(89, 4, 9, 5.4));
        progressList.add(new GameProgress(76, 2, 15, 14.2));

        String[] saveList = new String[progressList.size()];
        for (int i = 0; i < progressList.size(); i++) {
            saveList[i] = "\\Users\\sosko\\Games\\savegames\\save" + (i + 1) + ".dat";
            saveGames(saveList[i], progressList.get(i));
        }

        zipFiles("\\Users\\sosko\\Games\\savegames\\zip_SG.zip", saveList);

        File dir = new File("\\Users\\sosko\\Games\\savegames");

        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.getName().contains(".dat")) {
                    item.delete();
                }
            }
        }
    }

    public static void saveGames(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream
                (new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void zipFiles(String path, String[] saveList) {
        int saveNumber = 0;
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path))) {
            for (String save : saveList) {
                try (FileInputStream fis = new FileInputStream(save)) {
                    ZipEntry zipEntry = new ZipEntry("save" + (++saveNumber) + ".txt");
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
