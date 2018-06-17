/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinalproject;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author banana
 */
public class Calculation {
    protected String name;
    protected Label[]arrSign;
    protected Timeline prePare, Play;
    protected Pane parentPane;
    protected double scale;
    
    public Calculation(){}
    
    public void fadeInArrSign(double duration){
        for(int i=0; i<arrSign.length; i++){
            FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrSign[i]);
            fadein.setFromValue(1);
            fadein.setToValue(0);
            fadein.play();
        }
    }
    
    public void fadeOutArrSign(double duration){
        for(int i=0; i<arrSign.length; i++){
            FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrSign[i]);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.play();
        }
    }
    
    public void fadeInASign(int pos, double duration){
        FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrSign[pos]);
        fadein.setFromValue(1);
        fadein.setToValue(0);
        fadein.play();
    }
    
    public String getName() {
        return name;
    }


    public Label[] getArrSign() {
        return arrSign;
    }

    public Timeline getPrePare() {
        return prePare;
    }

    public Timeline getPlay() {
        return Play;
    }

    public Pane getParentPane() {
        return parentPane;
    }

    public double getScale() {
        return scale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArrSign(Label[] arrSign) {
        this.arrSign = arrSign;
    }

    public void setPrePare(Timeline prePare) {
        this.prePare = prePare;
    }

    public void setPlay(Timeline play) {
        Play = play;
    }

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
    
    public void stop(){
        if(prePare != null)
            prePare.stop();
        if(Play != null)
            Play.stop();
    }
    
    public void pause(){
        if(prePare != null)
            prePare.pause();
        if(Play != null)
            Play.pause();
    }
}
