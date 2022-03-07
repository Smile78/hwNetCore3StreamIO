package t13StreamIO.hw133load;

import  t13StreamIO.hw132save.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    static int cnt = 0;

    public static <FileInputStreamfis> void main(String[] args) {


        String pathRoot = "D:\\MOE\\GuglExt\\Coding\\netology\\javaCore\\src\\netology\\Core\\t13StreamIO\\hw131install\\" +
                "Games";
        String pathArch = "\\savegames";
        String archName = "zip_name.zip";

        List<String> fileLinkList = new ArrayList<>();
        List<GameProgress> objList = new ArrayList<>();

        //распакуем архив
        fileLinkList = unzip(pathRoot, pathArch, pathArch, archName);


        System.out.println("Вывод в консоль списка разархв файлов:");
        for (String str : fileLinkList) {
            System.out.println(str);
        }


        System.out.println("\nВывод в консоль списка  десериализованных файлов  установленного типа:");
        objList = restoreListSavedGames(fileLinkList);

        for (GameProgress gP : objList) {
            System.out.println(gP);
        }


        System.out.println("\nПочистим каталожик");
        for (String str : fileLinkList) {
            File newFile = new File(str);
            if (newFile.delete()) System.out.println("Файл " + regFileName(newFile.getName())  + " удален");
            // можно и без гетНейм
            // такто регулярка может и весь путь исколючить из названия
        }
    }


    //регулярочка - пока для сокращения удаляемого имени файла - потом конечно можно и для переименования итд
    static String regFileName(String str) {
        String shortName = "fileX.x";

        Pattern pattern = Pattern.compile("save.\\.dat");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            shortName =  str.substring(matcher.start(), matcher.end());
        }

        return shortName;
    }


    private static List<GameProgress> restoreListSavedGames(List<String> fileLinkList) {

        List<GameProgress> objList = new ArrayList<>();
        for (String str : fileLinkList) {
            try (FileInputStream fis = new FileInputStream(str); //откроемвходнойпотокдлячтенияфайла
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                objList.add((GameProgress) ois.readObject());             //десериализуем объекти скастимеговкласс
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return objList;
    }


    private static List<String> unzip(String pathRoot, String pathArch, String pathUnArch, String archName) {

        List<String> fileLinkList = new ArrayList<>();

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(
                pathRoot + pathArch + "\\" + archName))) {
            ZipEntry entry;
            String name;

            while ((entry = zin.getNextEntry()) != null) {  //по всем файлам в архиве
                name = entry.getName(); //получимназваниефайла

                // распаковка
                FileOutputStream fout = new FileOutputStream(pathRoot + pathUnArch + "\\" + "uzipped_" + name);
                fileLinkList.add(pathRoot + pathUnArch + "\\" + "uzipped_" + name);

                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return fileLinkList;

    }

}
