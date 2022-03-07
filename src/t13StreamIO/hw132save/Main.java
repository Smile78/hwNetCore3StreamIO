
package t13StreamIO.hw132save;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {


    private static void saveGame(String savePath, List<String> fileNameList, List<GameProgress> objList) {

        int cnt = 0;
        for (GameProgress obj : objList) {
            String nameFile = "save" + cnt++ + ".dat";

            fileNameList.add(nameFile);

            try (FileOutputStream fos = new FileOutputStream(savePath +"\\"+ nameFile);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                //запишемэкземплярклассавфайл
                oos.writeObject(obj);

                fos.close();// есть в ресурсах
                oos.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }


    private static void zipFiles(String savePath, List<String> fileNameList) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(savePath + "\\" + "zip_name.zip"));
        ) {
            for (String str : fileNameList) {
                FileInputStream fis = new FileInputStream(savePath + "\\" + str);
                ZipEntry entry = new ZipEntry("newIntZipName_" + str);

                zout.putNextEntry(entry);                        // считываем содержимое файлавмассив
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);                                // добавляем содержимое кархиву
                zout.write(buffer);                              // закрываем текущуюзапись дляновойзаписи

                fis.close(); // тыдынь потомучто не в ресурсах!!
            }
            zout.closeEntry();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    private static void readfromFiles(String savePath, List<String> fileNameList) {

        for (String str : fileNameList) {
            GameProgress gameProgress = null;
            try (FileInputStream fis = new FileInputStream(savePath + "\\" + str);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                //десериализуемобъектискастимеговкласс
                gameProgress = (GameProgress) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(gameProgress);
        }
    }


    public static void main(String[] args) {

        String savePath =
                "D:\\MOE\\GuglExt\\Coding\\netology\\javaCore\\src\\netology\\Core\\t13StreamIO\\hw131install\\Games\\savegames";


        List<GameProgress> objList = new ArrayList();
        List<String> fileNameList = new ArrayList();         // для упрощения запищу названия файлов  такто коненчо же я бы список файлов с каталога вытащил :) просто завал


        for (int i = 0; i < 4; i++) {
            objList.add(new GameProgress(
                    new Random().nextInt(240),
                    new Random().nextInt(12),
                    new Random().nextInt(99),
                    (double) Math.round(new Random().nextDouble() * 100) / 10));
        }


        //запишем/сохраним/сериализуем "параметры объектов" в файлы из перечняЭкзмпОбъектов
        saveGame(savePath, fileNameList, objList);


        //прочитаем из файлов
        readfromFiles(savePath, fileNameList);


        //запишем в архив
        zipFiles(savePath, fileNameList);


        //удаляем файлы
        //TODO
        // чз итератор?
        for (String str : fileNameList) {
            File newFile = new File(savePath +"\\"+ str);
            if (newFile.delete()) System.out.println("Файл " + str + " удален");
        }

        //почистим список имен файлов
        fileNameList.clear();
        System.out.println("size "+fileNameList.size());


    }



}
