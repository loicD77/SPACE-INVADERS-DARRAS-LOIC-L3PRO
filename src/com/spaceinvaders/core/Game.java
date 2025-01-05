package com.spaceinvaders.core;

import com.spaceinvaders.entities.*;
import com.spaceinvaders.render.Renderer;
import com.spaceinvaders.sound.SoundPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private Player player;
    private AlienManager alienManager;
    private List<Projectile> playerBullets;
    private List<Shield> shields;
    private MysteryShip mysteryShip;
    private boolean moveLeft, moveRight, shooting;
    private int score = 0;
    private int lives = 3;
    private boolean isGameOver = false;
    private float shootCooldown = 0.5f; // Temps entre deux tirs (en secondes)
    private float shootTimer = 0;       // Timer pour suivre le temps écoulé depuis le dernier tir
    private float mysteryShipCooldown = 20.0f;
    private int credits = 0; // Nombre de crédits affichés

    private float mysteryShipTimer = mysteryShipCooldown;
    private boolean mysteryShipDirectionLeftToRight = true;

    private Random random = new Random(); // Pour générer le score du Mystery Ship

    public Game() {
        initializeGame();
    }

    private void initializeGame() {
        player = new Player(0.0f, -0.8f, 0.1f, 0.1f, new float[]{0.0f, 0.0f, 1.0f});
        alienManager = new AlienManager();
        playerBullets = new ArrayList<>();
        shields = initializeShields();
        mysteryShip = new MysteryShip(-1.2f, 0.8f, 0.2f, 0.1f, 0.5f);
    }

    private List<Shield> initializeShields() {
        List<Shield> shields = new ArrayList<>();
        shields.add(new Shield(-0.75f, -0.6f, 0.2f, 0.1f, 5, 10));
        shields.add(new Shield(-0.25f, -0.6f, 0.2f, 0.1f, 5, 10));
        shields.add(new Shield(0.25f, -0.6f, 0.2f, 0.1f, 5, 10));
        shields.add(new Shield(0.75f, -0.6f, 0.2f, 0.1f, 5, 10));
        return shields;
    }

    public void update(float delta) {
        if (isGameOver) {
            System.out.println("=================================");
            System.out.println("GAME OVER");
            System.out.println("FINAL SCORE: " + score);
            System.out.println("=================================");
            return;
        }

        // Mise à jour du timer pour le tir
        if (shootTimer > 0) {
            shootTimer -= delta;
        }

        player.update(moveLeft, moveRight, delta);

        handlePlayerShooting();
        alienManager.update(delta, shields);
        handlePlayerBullets(delta);
        handleAlienBullets();

        if (alienManager.getAliens().isEmpty()) {
            alienManager.resetAliens();
        }

        if (alienManager.areAliensAtPlayerLevel(player.getY())) {
            isGameOver = true;
        }

        shields.removeIf(Shield::isFullyDestroyed);
        handleMysteryShip(delta);

        displayScoreInConsole();
    }


    private void handlePlayerShooting() {
        if (shooting && shootTimer <= 0) { // Vérifie si le joueur peut tirer
            float bulletX = player.getX();
            float bulletY = player.getY() + player.getHeight() / 2;
            playerBullets.add(new Projectile(bulletX, bulletY, 0.5f, new float[]{1.0f, 1.0f, 1.0f}));
            SoundPlayer.playSoundAsync("res/sounds/laser.wav");
            shooting = false;
            shootTimer = shootCooldown; // Réinitialise le cooldown
        }
    }

    private void handlePlayerBullets(float delta) {
        for (Projectile bullet : new ArrayList<>(playerBullets)) {
            bullet.update(delta);

            if (bullet.isOffScreen()) {
                playerBullets.remove(bullet);
            } else {
                if (checkBulletShieldCollision(bullet)) {
                    playerBullets.remove(bullet);
                    continue;
                }
                if (checkBulletMysteryShipCollision(bullet)) {
                    playerBullets.remove(bullet);
                    continue;
                }
                checkBulletAlienCollision(bullet);
            }
        }
    }

    private boolean checkBulletShieldCollision(Projectile bullet) {
        for (Shield shield : shields) {
            if (shield.checkCollision(bullet.getX(), bullet.getY())) {
                return true; // Collision détectée, le projectile doit être supprimé
            }
        }
        return false;
    }

    private boolean checkBulletMysteryShipCollision(Projectile bullet) {
        if (mysteryShip.isActive() && mysteryShip.checkCollision(bullet.getX(), bullet.getY())) {
            mysteryShip.deactivate();

            // Génère un score aléatoire pour le MysteryShip
            int mysteryShipPoints = generateMysteryShipScore();
            score += mysteryShipPoints;

            // Affiche le message dans la console
            System.out.println("En plein dans le 1000 !! Vous avez gagné " + mysteryShipPoints + " points !");

            return true; // Collision détectée
        }
        return false; // Pas de collision
    }



    private int generateMysteryShipScore() {
        // Scores aléatoires typiques dans Space Invaders : 50, 100, ou 150 points
        int[] possibleScores = {50, 100, 150};
        return possibleScores[random.nextInt(possibleScores.length)];
    }

    private void checkBulletAlienCollision(Projectile bullet) {
        for (Alien alien : new ArrayList<>(alienManager.getAliens())) {
            if (bullet.collidesWith(alien)) {
                playerBullets.remove(bullet);
                alienManager.getAliens().remove(alien);
                score += alien.getScore();
                SoundPlayer.playSoundAsync("res/sounds/explosion.wav");
                return;
            }
        }
    }

    private void handleAlienBullets() {
        for (Projectile alienBullet : new ArrayList<>(alienManager.getAlienBullets())) {
            if (alienBullet.getY() <= -0.91f) {
                alienManager.getAlienBullets().remove(alienBullet);
                SoundPlayer.playSoundAsync("res/sounds/impact.wav");
                continue;
            }

            if (alienBullet.collidesWith(player)) {
                alienManager.getAlienBullets().remove(alienBullet);
                lives--;
                if (lives <= 0) isGameOver = true;
                return;
            }

            for (Shield shield : shields) {
                if (shield.checkCollision(alienBullet.getX(), alienBullet.getY())) {
                    alienManager.getAlienBullets().remove(alienBullet);
                    SoundPlayer.playSoundAsync("res/sounds/impact.wav");
                    return;
                }
            }
        }
    }

    private void handleMysteryShip(float delta) {
        if (!mysteryShip.isActive()) {
            mysteryShipTimer -= delta;
            if (mysteryShipTimer <= 0) {
                float spawnPosition = mysteryShipDirectionLeftToRight ? -1.2f : 1.2f;
                float directionSpeed = mysteryShipDirectionLeftToRight ? 0.5f : -0.5f;
                mysteryShip.spawn(spawnPosition, directionSpeed);

                mysteryShipTimer = mysteryShipCooldown;
                mysteryShipDirectionLeftToRight = !mysteryShipDirectionLeftToRight;
            }
        }
        mysteryShip.update(delta);
    }

    public void render(Renderer renderer) {
        if (isGameOver) {
            renderGameOver(renderer);
        } else {
            renderGame(renderer);
        }
    }

    private void renderGameOver(Renderer renderer) {
        renderer.drawBitmapText("GAME OVER", -0.4f, 0.0f, 0.05f, new float[]{1.0f, 0.0f, 0.0f});
        renderer.drawBitmapText("SCORE: " + score, -0.3f, -0.2f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
    }

    private void displayScoreInConsole() {
        System.out.println("=================================");
        System.out.println("SCORE: " + score);
        System.out.println("LIVES: " + lives);
        System.out.println("=================================");
    }

    private void renderGame(Renderer renderer) {
        // Dessine le sol
        renderer.drawGround(-0.95f, new float[]{0.0f, 1.0f, 0.0f});

        // Dessine le joueur et les autres entités
        player.render(renderer);
        alienManager.render(renderer);

        for (Projectile bullet : playerBullets) {
            bullet.render(renderer);
        }

        for (Shield shield : shields) {
            shield.render(renderer);
        }

        mysteryShip.render(renderer);

        // Affichage des scores
        renderer.drawBitmapText("SCORE<1>", -0.9f, 0.85f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
        renderer.drawBitmapText("HI-SCORE", -0.1f, 0.85f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
        renderer.drawBitmapText("SCORE<2>", 0.6f, 0.85f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});

        // Scores sous les titres
        renderer.drawBitmapText(String.valueOf(score), -0.8f, 0.75f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
        renderer.drawBitmapText("0", -0.0f, 0.75f, 0.05f, new float[]{1.0f, 1.0f, 1.0f}); // HI-SCORE initialisé à 0
        renderer.drawBitmapText("0", 0.7f, 0.75f, 0.05f, new float[]{1.0f, 1.0f, 1.0f}); // SCORE<2> initialisé à 0

        // Affichage des vies
        StringBuilder livesDisplay = new StringBuilder();
        for (int i = 0; i < lives; i++) {
            livesDisplay.append("V ");
        }
        renderer.drawBitmapText(livesDisplay.toString().trim(), -0.9f, -0.8f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});

        // Affichage des crédits
        renderer.drawBitmapText("CREDIT 00", 0.6f, -0.9f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
}
