package com.games.toufoulati.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.games.toufoulati.utils.WordDatabase;
import com.badlogic.gdx.graphics.Color;


import java.awt.*;

public class BaccalaureatScreen implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private WordDatabase wordDatabase;

    private String randomLetter;
    private int score = 0;
    private int timeLeft = 60;
    private int currentRound = 1;
    private final int totalRounds = 3;
    private boolean answersValidated = false;

    // UI elements
    private Label letterLabel, timerLabel, scoreLabel, roundLabel, resultLabel;

    private TextField countryField, animalField, nameField, objectField, colorField, fruitsField;
    private Label botCountry, botAnimal, botName, botObject, botColor, botFruit;

    private TextButton validateButton;
    private Texture backgroundTexture;
    private Image backgroundImage;


    public BaccalaureatScreen(Game game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("assets/ui/uiskin.json"));
        this.wordDatabase = new WordDatabase();
        this.randomLetter = getRandomLetter();

        // UI setup
        letterLabel = new Label("Lettre : " + randomLetter, skin);
        letterLabel.setFontScale(2f);
        letterLabel.setColor(Color.BLACK);

        timerLabel = new Label("Temps restant : " + timeLeft, skin);
        timerLabel.setColor(Color.BLACK);
        scoreLabel = new Label("Score : " + score, skin);
        scoreLabel.setColor(Color.BLACK);
        roundLabel = new Label("Manche : " + currentRound + " / " + totalRounds, skin);
        roundLabel.setColor(Color.BLACK);
        resultLabel = new Label("", skin);
        resultLabel.setColor(Color.DARK_GRAY);
        backgroundTexture = new Texture(Gdx.files.internal("assets/backgrounds/baccalaureat_bg.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage); // 🔽 doit être le premier acteur pour rester en arrière-plan

        setupPlayerTable();
        setupBotTable();
        setupMainLayout();

        startTimer();


    }

    private void setupPlayerTable() {
        countryField = new TextField("", skin);
        animalField = new TextField("", skin);
        nameField = new TextField("", skin);
        objectField = new TextField("", skin);
        colorField = new TextField("", skin);
        fruitsField = new TextField("", skin);

        countryField.setMessageText("Pays");
        animalField.setMessageText("Animal");
        nameField.setMessageText("Prénom");
        objectField.setMessageText("Objet");
        colorField.setMessageText("Couleur");
        fruitsField.setMessageText("Fruit");
    }

    private void setupBotTable() {
        botCountry = new Label("", skin);
        botCountry.setColor(Color.DARK_GRAY);
        botAnimal = new Label("", skin);
        botAnimal.setColor(Color.DARK_GRAY);
        botName = new Label("", skin);
        botName.setColor(Color.DARK_GRAY);
        botObject = new Label("", skin);
        botObject.setColor(Color.DARK_GRAY);
        botColor = new Label("", skin);
        botColor.setColor(Color.DARK_GRAY);
        botFruit = new Label("", skin);
        botFruit.setColor(Color.DARK_GRAY);
    }

    private void setupMainLayout() {
        Table root = new Table();
        root.setFillParent(true);
        root.top().padTop(30);
        root.add(roundLabel).colspan(2).padBottom(10).row();
        root.add(letterLabel).colspan(2).padBottom(10).row();
        root.add(timerLabel).colspan(2).padBottom(10).row();
        root.add(scoreLabel).colspan(2).padBottom(10).row();
        root.add(resultLabel).colspan(2).padBottom(20).row();

        // Tableau joueur
        Table playerTable = new Table();
        playerTable.add(new Label("👦 Joueur", skin)).padBottom(10).row();
        playerTable.add(countryField).width(250).pad(5).row();
        playerTable.add(animalField).width(250).pad(5).row();
        playerTable.add(nameField).width(250).pad(5).row();
        playerTable.add(objectField).width(250).pad(5).row();
        playerTable.add(colorField).width(250).pad(5).row();
        playerTable.add(fruitsField).width(250).pad(5).row();

        // Tableau bot
        Table botTable = new Table();
        botTable.add(new Label("🤖 Bot", skin)).padBottom(10).row();
        botTable.add(botCountry).width(250).pad(5).row();
        botTable.add(botAnimal).width(250).pad(5).row();
        botTable.add(botName).width(250).pad(5).row();
        botTable.add(botObject).width(250).pad(5).row();
        botTable.add(botColor).width(250).pad(5).row();
        botTable.add(botFruit).width(250).pad(5).row();

        root.add(playerTable).padRight(80);
        root.add(botTable);

        // Bouton valider
        validateButton = new TextButton("Valider", skin);
        validateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                validateAnswers();
            }
        });

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.bottom().padBottom(20);
        buttonTable.add(validateButton).width(200).height(50);

        stage.addActor(root);
        stage.addActor(buttonTable);
    }

    private void startTimer() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (timeLeft > 0 && !answersValidated) {
                    timeLeft--;
                    timerLabel.setText("Temps restant : " + timeLeft);
                } else if (!answersValidated) {
                    validateAnswers();
                }
            }
        }, 1, 1);
    }

    private void validateAnswers() {
        if (answersValidated) return;

        int points = 0;
        String l = randomLetter;

        if (wordDatabase.isValid("Pays", countryField.getText(), l)) points += 10;
        if (wordDatabase.isValid("Animal", animalField.getText(), l)) points += 10;
        if (wordDatabase.isValid("Prenom", nameField.getText(), l)) points += 10;
        if (wordDatabase.isValid("Objet", objectField.getText(), l)) points += 10;
        if (wordDatabase.isValid("Couleur", colorField.getText(), l)) points += 10;
        if (wordDatabase.isValid("Fruit", fruitsField.getText(), l)) points += 10;

        score += points;
        scoreLabel.setText("Score : " + score);
        answersValidated = true;

        // Remplissage bot
        botCountry.setText(wordDatabase.getRandomWord("Pays", l));
        botAnimal.setText(wordDatabase.getRandomWord("Animal", l));
        botName.setText(wordDatabase.getRandomWord("Prenom", l));
        botObject.setText(wordDatabase.getRandomWord("Objet", l));
        botColor.setText(wordDatabase.getRandomWord("Couleur", l));
        botFruit.setText(wordDatabase.getRandomWord("Fruit", l));

        // Calcul score bot
        int botScore = 0;
        if (!botCountry.getText().isEmpty()) botScore += 10;
        if (!botAnimal.getText().isEmpty()) botScore += 10;
        if (!botName.getText().isEmpty()) botScore += 10;
        if (!botObject.getText().isEmpty()) botScore += 10;
        if (!botColor.getText().isEmpty()) botScore += 10;
        if (!botFruit.getText().isEmpty()) botScore += 10;

        // Affichage résultat
        if (points > botScore) {
            resultLabel.setText("✅ Tu as gagné la manche !");
        } else if (points < botScore) {
            resultLabel.setText("🤖 Le bot a gagné la manche !");
        } else {
            resultLabel.setText("😮 Égalité !");
        }

        // Prochaine manche
        if (currentRound < totalRounds) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    nextRound();
                }
            }, 3);
        } else {
            showEndScreen();
        }
    }

    private void nextRound() {
        currentRound++;
        roundLabel.setText("Manche : " + currentRound + " / " + totalRounds);
        randomLetter = getRandomLetter();
        letterLabel.setText("Lettre : " + randomLetter);
        timerLabel.setText("Temps restant : 60");
        resultLabel.setText("");
        timeLeft = 60;
        answersValidated = false;

        countryField.setText(""); countryField.setDisabled(false);
        animalField.setText(""); animalField.setDisabled(false);
        nameField.setText(""); nameField.setDisabled(false);
        objectField.setText(""); objectField.setDisabled(false);
        colorField.setText(""); colorField.setDisabled(false);
        fruitsField.setText(""); fruitsField.setDisabled(false);

        botCountry.setText("");
        botAnimal.setText("");
        botName.setText("");
        botObject.setText("");
        botColor.setText("");
        botFruit.setText("");

        validateButton.setDisabled(false);
    }

    private String getRandomLetter() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return String.valueOf(alphabet.charAt((int)(Math.random() * alphabet.length())));
    }

    private void showEndScreen() {
        stage.clear(); // Nettoie l’ancien affichage

        Label endLabel = new Label("🎉 Partie terminée !", skin);
        endLabel.setFontScale(2f);
        endLabel.setColor(Color.BLACK);

        Label finalScore = new Label("Score final : " + score, skin);
        finalScore.setColor(Color.DARK_GRAY);

        TextButton backButton = new TextButton("Retour au menu", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameMenuScreen(game)); // retour au menu principal
            }
        });

        Table endTable = new Table();
        endTable.setFillParent(true);
        endTable.center();
        endTable.add(endLabel).padBottom(20).row();
        endTable.add(finalScore).padBottom(20).row();
        endTable.add(backButton).width(200).height(50);

        stage.addActor(endTable);
    }


    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();

    }
}

