package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicControl {
    private static Music backGroundMusic;
    private static Music gamePlayMusic;
    private static Music scoreMusic;
    private static Music successMusic;

    static void playBackgroundMusic(){
        if(backGroundMusic == null){
            backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainSong.mp3"));
            backGroundMusic.setLooping(true);
        }
        if(!backGroundMusic.isPlaying()){
            backGroundMusic.play();
        }
    }
    static void stopBackgroundMusic(){
        if(backGroundMusic != null && backGroundMusic.isPlaying()){
            backGroundMusic.stop();
        }
    }

    static void playGameplayMusic(){
        if (gamePlayMusic == null) {
            gamePlayMusic = Gdx.audio.newMusic(Gdx.files.internal("gameSound.wav"));
            gamePlayMusic.setLooping(true);
        }
        if (!gamePlayMusic.isPlaying()) {
            gamePlayMusic.play();
        }
    }
    static void stopGameplayMusic(){
        if(gamePlayMusic != null && gamePlayMusic.isPlaying()){
            gamePlayMusic.stop();
        }
    }

    static void playSuccessMusic(){
        if (successMusic == null) {
            successMusic = Gdx.audio.newMusic(Gdx.files.internal("levelUp.mp3"));
            successMusic.setLooping(true);
        }
        if (!successMusic.isPlaying()) {
            successMusic.play();
        }
    }
    static void stopSuccessMusic(){
        if(successMusic != null && successMusic.isPlaying()){
            successMusic.stop();
        }
    }

    static void playScoreMusic(){
        if (scoreMusic == null) {
            scoreMusic = Gdx.audio.newMusic(Gdx.files.internal("fail.wav"));
            scoreMusic.setLooping(true);
        }
        if (!scoreMusic.isPlaying()) {
            scoreMusic.play();
        }
    }
    static void stopScoreMusic(){
        if(scoreMusic != null && scoreMusic.isPlaying()){
            scoreMusic.stop();
        }
    }

    public static void dispose() {
        MusicControl.dispose();
    }
}
