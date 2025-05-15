package com.games.toufoulati.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.*;

public class WordDatabase {

    private final Map<String, Set<String>> wordsByCategory = new HashMap<>();

    public WordDatabase() {
        loadFile("assets/files/pays.csv", "Pays", ",");
        loadFile("assets/files/animaux.tsv", "Animal", "\t");
        loadFile("assets/files/Fruit.tsv", "Fruit", "\t");
        loadFile("assets/files/Couleur.tsv", "Couleur", "\t");
        loadFile("assets/files/Objet.tsv", "Objet", "\t");
        loadFile("assets/files/males_en.csv", "Prenom", ",");
        loadFile("assets/files/females_en.csv", "Prenom", ",");
    }

    private void loadFile(String path, String category, String separator) {
        FileHandle file = Gdx.files.internal(path);
        String[] lines = file.readString("UTF-8").split("\n");

        Set<String> words = wordsByCategory.computeIfAbsent(category, k -> new HashSet<>());

        for (String line : lines) {
            String word = line.split(separator)[0].trim().toUpperCase();
            if (!word.isEmpty()) {
                words.add(word);
            }
        }
    }

    public boolean isValid(String category, String word, String requiredLetter) {
        if (!wordsByCategory.containsKey(category)) return false;
        return word != null
            && word.toUpperCase().startsWith(requiredLetter)
            && wordsByCategory.get(category).contains(word.toUpperCase());
    }

    public String getRandomWord(String category, String startingLetter) {
        Set<String> words = wordsByCategory.get(category);
        if (words == null) return "";

        List<String> validWords = new ArrayList<>();
        for (String word : words) {
            if (word.startsWith(startingLetter.toUpperCase())) {
                validWords.add(word);
            }
        }

        if (validWords.isEmpty()) return ""; // aucun mot
        return validWords.get(new Random().nextInt(validWords.size()));
    }

}

