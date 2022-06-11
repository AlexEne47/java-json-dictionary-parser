package com.example;

import java.io.File;

public class Main {
    
    public static void main(String[] args) {

        File folder = new File("resources/");
        App app = new App(folder);

        //ArrayList<Word> temp = app.listadictionare.get("ro");
        //Word test = temp.get(0);

        //System.out.println(test.word);

        //System.out.println(app.addWord(test, "fr"));
        //System.out.println(app.removeWord("merge", "fr"));
        //System.out.println(app.translateWord("pisica", "ro", "fr"));
        //System.out.println(app.getDefinitionsForWord("merge", "ro"));
        app.Afisare();
    }
}
