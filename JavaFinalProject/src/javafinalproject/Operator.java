/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinalproject;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author banana
 */
public class Operator extends Calculation{
    
    protected Matrix mx1, mx2, mxr;
    protected int count1 = 0, count2=0;
    protected Label[] arrMainSign;
    
    public Operator(Pane parentPane, double scale){
        this.parentPane = parentPane;
        this.scale = scale;
    }
    
    public void create(int row, int col, int x, int y, double widthElement, double heightElement){
        mx1 = new Matrix("A",row, col, x, y, widthElement, heightElement, scale);
        mx1.randomValue();
        mx2 = new Matrix("B",row, col, (int)(x+mx1.get_Width()+90*scale), y, widthElement, heightElement, scale);
        mx2.randomValue();
        mxr = new Matrix("R",row, col, (int)(mx2.getX()+mx2.get_Width()+90*scale), y, widthElement, heightElement, scale);
        
        parentPane.getChildren().add(mx1);
        parentPane.getChildren().add(mx2);
        parentPane.getChildren().add(mxr);
        
        //Đặt giá trị cho dấu lớn
        arrMainSign = new Label[2];
        arrMainSign[0] = new Label("+");
        arrMainSign[0].setFont(new Font("Arial",50*scale));
        arrMainSign[0].setLayoutX((mx2.getX() + mx1.get_Width()+ mx1.getX())/2-15*scale);
        arrMainSign[0].setLayoutY(mx2.get_Height()<mx1.get_Height()?mx1.get_Height()/2-10:mx2.get_Height()/2);
        arrMainSign[0].setOpacity(0);
        parentPane.getChildren().add(arrMainSign[0]);
        
        arrMainSign[1] = new Label("=");
        arrMainSign[1].setFont(new Font("Arial",50*scale));
        arrMainSign[1].setLayoutX((mxr.getX() + mx2.get_Width()+mx2.getX())/2-15*scale);
        arrMainSign[1].setLayoutY(mx2.get_Height()<mx1.get_Height()?mx1.get_Height()/2-10:mx2.get_Height()/2);
        arrMainSign[1].setOpacity(0);
        parentPane.getChildren().add(arrMainSign[1]);
        
        //Đặt giá trị cho 2 dấu nhỏ
        arrSign = new Label[2];
        arrSign[0] = new Label("+");
        arrSign[0].setFont(new Font("Arial",30*scale));
        arrSign[0].setLayoutX(30*scale+mx1.getWidthElement());
        arrSign[0].setLayoutY(mx1.getHeightElement()-15*scale);
        arrSign[0].setOpacity(0);
        parentPane.getChildren().add(arrSign[0]);
        
        arrSign[1] = new Label("=");
        arrSign[1].setFont(new Font("Arial",30*scale));
        arrSign[1].setLayoutX(90*scale+mx1.getWidthElement()+mx2.getWidthElement());
        arrSign[1].setLayoutY(mx1.getHeightElement()-15*scale);
        arrSign[1].setOpacity(0);
        parentPane.getChildren().add(arrSign[1]);
    }
    
    public void fadeInMainSign(double duration){
        for(int i=0; i<arrMainSign.length; i++){
            FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrMainSign[i]);
            fadein.setFromValue(1);
            fadein.setToValue(0);
            fadein.play();
        }
    }
    
    public void fadeOutMainSign(double duration){
        for(int i=0; i<arrMainSign.length; i++){
            FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrMainSign[i]);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.play();
        }
    }
    
    public void translateMainSign(double duration, int desx, int desy){
        for(int i=0; i<arrMainSign.length; i++){
            TranslateTransition tt = new TranslateTransition(Duration.seconds(duration), arrMainSign[i]);
            tt.setToX(desx);
            tt.setToY(desy);
            tt.play();
        }
    }
    
    public void play(){
        //Timeline chuẩn bị vị trí cho các ma trận
        prePare = new Timeline();
        preparePosition(0.2);
        Timeline_fadeOutMainSign(2);     
        prePare.play();
        
        //Timeline chính
        Play = new Timeline();
        moveUp(3);
        visibleLabelElement(3.5);
        setPosToCalculation(5.5);
        visibleSign(7);
        executeCalculation(8.5);
        hideBeforeReturnPos(10);
        hideLabelResultMatrix(11);
        returnPos(12);
        visibleElement(13.5);
        Play.setCycleCount(Timeline.INDEFINITE);
        Play.play();
    }
    
    public double getTotalTime(){return 0;}
     
    
    public void preparePosition(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mx1.translateMatrix(1.5, 0,300);
                mx2.translateMatrix(1.5, 0,300);
                mxr.translateMatrix(1.5, 0,300);
                translateMainSign(1.5,0,300);
                
            }     
        });
        
        prePare.getKeyFrames().add(kf);
    }
    
    public void Timeline_fadeOutMainSign(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                fadeOutMainSign(1);              
            }     
        });
        
        prePare.getKeyFrames().add(kf);    
    }
    
    public void moveUp(double start){}
    
    public void visibleLabelElement(double start){}
    
    public void setPosToCalculation(double start){}
    
    public void visibleSign(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                fadeOutArrSign(1);
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void executeCalculation(double start){}
    
    public void hideBeforeReturnPos(double start){}
    
    public void hideLabelResultMatrix(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mxr.fadeInLabelElement(count1, count2, 1);
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void returnPos(double start){}
    
    public void visibleElement(double start){}
    
    public void increaseCount(){}
    
}
