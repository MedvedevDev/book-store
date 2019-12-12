package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class GenreUtility {
    private Scanner in = new Scanner(System.in);
    private Map<Integer, String> listGenre = new HashMap<>();
    private Gson gson = new Gson();
    private String file_location = "D:/java/book-store/genres.txt";

    public GenreUtility() {
    }

    public String getNameOfGenre(int key){
        return listGenre.get(key);
    }

    public Map<Integer, String> addGenre(){
        boolean isExsist = false;
        boolean continueLoop = true;
        while (continueLoop) {

            System.out.println("Введите id: ");
            int newId = validateInt();

            System.out.println("Введите название жанра: ");
            String newGenre = "";
            if (in.hasNextLine()) {
                newGenre = in.nextLine();
            }
//         for (Iterator<String> it = listGenre.iterator(); it.hasNext();){
//             String s = it.next();
//             if (s.replace(" ", "").equalsIgnoreCase(newGenre.replace(" ", ""))) {
//                 isExsist = true;
//                 break;
//             }
//         }
            for (Map.Entry me : listGenre.entrySet()) {
                if (me.getKey().equals(newId) || newGenre.replace(" ", "").equalsIgnoreCase(me.getValue().toString().replace(" ", ""))) {
                    isExsist = true;
                    break;
             }
            }

         if (!isExsist) {
             listGenre.put(newId, newGenre.toLowerCase().replace(" ", ""));
             System.out.println("Жаер " + newGenre + " успешно добавлен");
         } else {
             System.out.println("Жанр с таким названием или ID уже существует");
         }

            continueLoop = continueMethod("**  Добавить еще жанр? ");
        }
        saveGenre(listGenre);
        return listGenre;
    }

    public Map<Integer, String> deleteGenre(){
        String userInput="";
        listGenres();

        System.out.println("**** Введите ID жанра, который хотите удалить: ");
        int userChoose = validateInt();

            if (listGenre.get(userChoose) != null) {
                System.out.println("Вы точно хотите удалить жанр " + listGenre.get(userChoose) + "? Для подтверждения введите названия жанра, для отмены введите 'not'");
                if (in.hasNextLine()) {
                    userInput = in.nextLine();
                }
                if (userInput.toLowerCase().equals(listGenre.get(userChoose))) {
                    listGenre.remove(userChoose);
                    System.out.println("Жанр удален");
                } else  if (userInput.equals("not")) {
                    System.out.println("Отмена");
                    return listGenre;
                } else {
                    System.out.println("Вы ввели некорректное название жанра");
                }
            } else {
                System.out.println("Жанра с таким айди нет");
            }
//        try {
//            if (!listGenre.isEmpty()) {
//                for (Iterator<String> it = listGenre.iterator(); it.hasNext(); ) {
//                    String s = it.next();
//                    System.out.println(s);
//                    if (s.contains(newGenre)) {
//                        it.remove();
//                        System.out.println("Жаер " + s + " удален");
//                        isDeleted = true;
//                    }
//                }
//            }
//        }catch (NoSuchElementException e){
//            System.out.println("Нет такого жанра");
//        }
        saveGenre(listGenre);
        return listGenre;
    }

    public void listGenres(){
        if (!listGenre.isEmpty()) {
            for (Map.Entry me : listGenre.entrySet()) {
                System.out.println("ID: "+me.getKey() + " & ЖАНР: " + me.getValue());
            }
        } else {
            System.out.println("No genres");
        }
    }

    public Map<Integer, String> editGenre() {
        String userInput = "";
        listGenres();

        System.out.println("**** Введите номер жанра, который хотите отредактировать: ");
        int userChoose = validateInt();
        if (listGenre.get(userChoose) != null) {
                System.out.println("Введите новое название жанра или 'not' для отмены");
                if (in.hasNextLine()) {
                    userInput = in.nextLine();
                }
                if (userInput.equals("not")) {
                    System.out.println("Отмена");
                    return listGenre;
                } else {
                    listGenre.put(userChoose, userInput);
                }
        } else {
            System.out.println("Жанра с таким id нет");
        }
        saveGenre(listGenre);
        return listGenre;
    }


    public  void saveGenre(Map<Integer, String> genres) {
        saveGenresToFile(gson.toJson(genres));
    }

    public  Map<Integer, String> loadGenres() {
        File crunchifyFile = new File(file_location);
        if (!crunchifyFile.exists())
            log("File doesn't exist");

        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(crunchifyFile), "UTF-8");

            JsonReader myReader = new JsonReader(isReader);
            listGenre = gson.fromJson(myReader, new TypeToken<Map<Integer, String>>() {
            }.getType());

        } catch (Exception e) {
            log("error load cache from file " + e.toString());
        }
        if (listGenre == null) {
            listGenre = new HashMap<Integer, String>();
        }
        log("\nGenres loaded successfully from file " + file_location);

        return listGenre;
    }

    public void saveGenresToFile(String myData) {
        File crunchifyFile = new File(file_location);
        if (!crunchifyFile.exists()) {
            try {
                File directory = new File(crunchifyFile.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                crunchifyFile.createNewFile();
            } catch (IOException e) {
                log("Excepton Occured: " + e.toString());
            }
        }

        try {
            FileWriter crunchifyWriter;
            crunchifyWriter = new FileWriter(crunchifyFile.getAbsoluteFile());

            BufferedWriter bufferWriter = new BufferedWriter(crunchifyWriter);
            bufferWriter.write(myData.toString());
            bufferWriter.close();

            log("Genre saved at file location: " + file_location + "\n");
        } catch (IOException e) {
            log("Hmm.. Got an error while saving genre to file " + e.toString());
        }
    }

    private void log(String string) {
        System.out.println(string);
    }

    public boolean continueMethod(String str) {
        boolean continueLoop = true;
        String userinput = "";
        System.out.println(str + " y/n");

        boolean choiceIsOK = false;
        do{
            if (in.hasNextLine()) {
                userinput = in.nextLine();
            }

            char choice = userinput.toLowerCase().charAt(0);
            switch(choice){
                case 'y':
                    continueLoop = true;
                    choiceIsOK = true;

                    break;
                case 'n':
                    continueLoop = false;
                    choiceIsOK = true;
                    break;
                default:
                    System.out.println("Type Y or N to respectively continue or quit");
                    break;
            }
        }while(!choiceIsOK);

        return continueLoop;
    }

    private int validateInt() {
        int number;
        String mas = "";
        do {
            System.out.print(mas);
            while (!in.hasNextInt()) {
                String input = in.next();
                System.out.printf("\"%s\" - не является числом, попробуйсте снова: ", input);
            }
            number = in.nextInt();
            mas = "Вы ввели отрицательное число, попробуйсте снова: ";
            in.nextLine();
        } while (number < 0);

        return number;
    }
}
