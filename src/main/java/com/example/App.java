package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

public class App 
{
    Gson gson = new Gson();
    HashMap<String, ArrayList<Word>> listadictionare = new HashMap<String, ArrayList<Word>>();

    App(File folder) {

        //File folder = new File("resources/");
        String[] listafisiere = folder.list();

        for(String numefisier : listafisiere) {
            
        // Cautam doar fisierele cu formatul .json
        if(numefisier.endsWith(".json"))
        try (FileReader input = new FileReader("resources/" + numefisier);) {

            // Prefixul este limba dictionarelor (ro, fr) 
            // si va fi stocata ca index in hashmap
            String prefix = numefisier.substring(0, 2);

            // Extragem informatiile din fisier
            Type lista = new TypeToken<ArrayList<Word>>(){}.getType();
            ArrayList<Word> dictionar = gson.fromJson(input, lista); 
            
            // Introducem datele in hashmap
            listadictionare.put(prefix, dictionar);

        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    }

    boolean addWord(Word word, String language) {

        ArrayList<Word> cuvinte = listadictionare.get(language);

        // Cautam cuvantul in dictionar
        for(Word cuvant : cuvinte)
        if(cuvant.equals(word))
        return false;

        // Daca cuvantul nu este gasit, il adaugam in arraylist
        cuvinte.add(word);
        return true;
    }

    boolean removeWord(String word, String language) {

        ArrayList<Word> cuvinte = listadictionare.get(language);

        // Cautam cuvantul in dictionar
        for(Word cuvant : cuvinte)
        if(cuvant.word.equals(word)) {
        cuvinte.remove(cuvant);
        return true;
        }

        // Daca cuvantul nu este gasit, returnam false
        return false;
    }

    boolean addDefinitionForWord(String word, String language, Definition definition) {

        ArrayList<Word> cuvinte = listadictionare.get(language);

        // Gasim cuvantul 
        for(Word cuvant : cuvinte)
        if(cuvant.word.equals(word)) {

        ArrayList<Definition> definitii = cuvant.definitions;

        // Cautam daca exista deja definitia
        for(Definition definitie : definitii)
        if(definitie.equals(definition))
        return false;

        // Daca nu exista, o adaugam 
        cuvant.definitions.add(definition);
        return true;
        }

        return false;
    }

    boolean removeDefinition(String word, String language, String dictionary) {

        ArrayList<Word> cuvinte = listadictionare.get(language);

        // Gasim cuvantul 
        for(Word cuvant : cuvinte)
        if(cuvant.word.equals(word)) {

        ArrayList<Definition> definitii = cuvant.definitions;

        // Daca gasim definitia o stergem si returnam true
        for(Definition definitie : definitii)
        if(definitie.dict.equals(dictionary)) {
            definitii.remove(definitie);
            return true;
        }
        }

        // Daca nu este gasita, returnam false
        return false;
    }

    String translateWord(String word, String fromLanguage, String toLanguage) {

        ArrayList<Word> fromCuvinte = listadictionare.get(fromLanguage);
        ArrayList<Word> toCuvinte = listadictionare.get(toLanguage);
        String wordEN = "";
        String cuvantTradus = "";

        // Cautam cuvantul care trebuie tradus 
        for(Word cuvant : fromCuvinte)
        if(cuvant.word.equals(word))
        wordEN = cuvant.word_en;

        // Ne folosim de cuvantul tradus in engleza pentru a-l cauta 
        // al doilea dictionar 
        for(Word cuvant : toCuvinte)
        if(cuvant.word_en.equals(wordEN))
        cuvantTradus = cuvant.word;

        // Daca cuvantul este gasit, returnam traducerea acestuia
        return cuvantTradus;

    }

    String translateSentence(String sentence, String fromLanguage, String toLanguage) {

        ArrayList<Word> fromCuvinte = listadictionare.get(fromLanguage);
        ArrayList<Word> toCuvinte = listadictionare.get(toLanguage);
        String propozitieTradusa = "";

        // Separam propozitia si o traducem cuvant cu cuvant
        for(String cuvantCurent: sentence.split(" ")) {

            String wordEN = "";
            String cuvantTradus = "";

            // Gasim cuvantul in primul dictionar si extragem
            // traducerea in engleza
            for(Word cuvant : fromCuvinte)
            if(cuvant.word.equals(cuvantCurent))
            wordEN = cuvant.word_en;

            // Cautam cuvantul in engleza in cel de-al doilea
            // dictionar si stocam traducerea acestuia
            for(Word cuvant : toCuvinte)
            if(cuvant.word_en.equals(wordEN))
            cuvantTradus = cuvant.word;

            // Verificam daca exista cuvantul in dictionar
            if(wordEN == "")
            return null;

            // Adaugam fiecare cuvant tradus in propozitia finala
            propozitieTradusa = propozitieTradusa + cuvantTradus;

        }

        return propozitieTradusa;
    }

    ArrayList<Definition> getDefinitionsForWord(String word, String language) {

        ArrayList<Word> cuvinte = listadictionare.get(language);
        
        // Verificam daca exista cuvantul in dictionar
        // si returnam definitiile acestuia
        for(Word cuvant : cuvinte) 
        if(cuvant.word.equals(word))
        return cuvant.definitions;
        
        return null;
    }

    void Afisare() {

        // Ne afiseaza toate dictionarele si cuvintele
        for(String language : listadictionare.keySet()) {

            ArrayList<Word> cuvinte = listadictionare.get(language);

            for(Word cuvant : cuvinte)
            System.out.println(cuvant);
        }
    }

}
