package t13StreamIO.hw131install;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

    static StringBuilder log1 = new StringBuilder();
    static int cnt = 0;


    static void mkFileMySvrl(int point, String pathmain, String kat4newFiles, String[] fileNames) {

        for (int i = 0; i < fileNames.length; i++) {
            File file = new File(pathmain + kat4newFiles + "\\" + fileNames[i]);

            try {
                if (file.createNewFile()) // System.out.println("булин- файл создан");
                    log1.append("пункт " + point + " " + "шаг " + cnt++ + " Файл " + fileNames[i] + " создан\n");
                else
                    log1.append("пункт " + point + " " + "шаг " + cnt++ + " Файл " + fileNames[i] + " НЕ был создан сейчас - возможно ранее - не EXC!\n");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    static void mkDirMySvrl(int point, String pathmain, String katpath, String[] Katname) {

        for (int i = 0; i < Katname.length; i++) {
            File kat = new File(pathmain + katpath + "\\" + Katname[i]);
            if (kat.mkdir()) {
                log1.append("пункт " + point + " " + "шаг " + cnt++ + " Каталог \"" + Katname[i] + "\" создан\n");
            } else {
                log1.append("пункт " + point + " " + "шаг " + cnt++ + " Каталог \"" + Katname[i] + "\" видимо уже есть\n");
            }
        }
    }


    private static void writeTextInFile(int point, String pathMain, String kat4TxtFile, String fileNameToText, String text2rite) {

        log1.append("пункт " + point + " " + "шаг " + cnt++ + " запишем текст из Лога в  файл \n");
        // потерялось???

        try (FileWriter writer = new FileWriter(pathMain + kat4TxtFile + "\\" + fileNameToText, false)) {
            //незабываем что перед файлом надо \\- "указатель директория"!!!
            writer.write(text2rite);
            writer.flush();
            System.out.println("Записали чтото в файл успешно ");
            writer.close(); //не исп изза реср...
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void main(String[] args) {

        String pathMain = "D:\\MOE\\GuglExt\\Coding\\netology\\hwNetCore3StreamIO\\";
        // путь по дефолту/корень рабочего
        String[] catNames;          // перечень назв.каталогов для создания  или   1го но в массиве
        String kat4newCat;          // путь для созд.нов.каталогов от *корня
        String[] fileNames;         // перечень назв.файлов для создания  или   1го но в массиве
        String kat4newFiles;        // путь для созд.нов.файлов от *корня
        int point;                  // номер задачи по порядку в домашке
        String fileNameToText;      // наимен.файла для записи лога


        // TODO  чек ворк гитигнор Games итд?


        // 0 Cоздайте  папку Games
        point = 0;
        catNames = new String[]{"Games"};
        kat4newCat = "";
        mkDirMySvrl(point, pathMain, kat4newCat, catNames);

        // 1 В папке Games создайте несколько директорий: src, res, savegames, temp.
        point = 1;
        catNames = new String[]{"src", "res", "savegames", "temp"};
        kat4newCat = "\\Games";
        mkDirMySvrl(point, pathMain, kat4newCat, catNames);

        // 2 В каталоге src создайте две директории : main, test.
        point = 2;
        catNames = new String[]{"main", "test"};
        kat4newCat = "\\Games\\src";
        mkDirMySvrl(point, pathMain, kat4newCat, catNames);

        // 3 В подкаталоге main создайте два файла: Main.java, Utils.java.
        point = 3;
        fileNames = new String[]{"Main.java", "Utils.java"};
        kat4newFiles = "\\Games\\src";
        mkFileMySvrl(point, pathMain, kat4newFiles, fileNames);

        // 4 В каталог res создайте три директории: drawables, vectors, icons.
        point = 4;
        catNames = new String[]{"drawables", "vectors", "icons"};
        kat4newCat = "\\Games\\res";
        mkDirMySvrl(point, pathMain, kat4newCat, catNames);


        // 5 В директории temp создайте файл temp.txt.
        point = 5;
        fileNames = new String[]{"temp.txt"};
        kat4newFiles = "\\Games\\temp";
        mkFileMySvrl(point, pathMain, kat4newFiles, fileNames);


        // 6 выводим в консоль Лог
        point = 6;
        System.out.println("\n" + "пункт " + point + " " + "шаг " + cnt++ +
                " выводим Лог в консоль: " + "\n\n" +
                log1);

        // 7  запишем текст из Лога в  файл temp.txt с помощью класса FileWriter
        point = 7;
        String kat4TxtFile = "\\Games\\temp";
        fileNameToText = "temp.txt";
        String text2rite = String.valueOf(log1);
        writeTextInFile(point, pathMain, kat4TxtFile, fileNameToText, text2rite);

    }
}