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
public class Summation extends Operator{
    
    protected Matrix mx1, mx2, mxr;
    
    public Summation(Pane parentPane, double scale){
        super(parentPane, scale);
        name = "Summation";
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
    
    public double getTotalTime(){
        return 14.5*mx1.getRow()*mx1.getCol();
    }
        
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
    
    public void moveUp(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mx1.translateElement(count1, count2, 2, -count2*(int)(mx1.getWidthElement()+mx1.getDis()), (int)(100*scale)-count1*(int)(mx1.getHeightElement()+mx1.getDis()));
                mx2.translateElement(count1, count2, 2, -count2*(int)(mx2.getWidthElement()+mx2.getDis()), (int)(100*scale)-count1*(int)(mx2.getHeightElement()+mx2.getDis()));
                mxr.translateElement(count1, count2, 2, -count2*(int)(mxr.getWidthElement()+mxr.getDis()), (int)(100*scale)-count1*(int)(mxr.getHeightElement()+mxr.getDis()));                           
                mx1.translateLabelElement(count1, count2, 2, (int)(12*scale)-count2*(int)(mx1.getWidthElement()+mx1.getDis()), (int)(80*scale)-count1*(int)(mx1.getHeightElement()+mx1.getDis()));
                mx2.translateLabelElement(count1, count2, 2, (int)(12*scale)-count2*(int)(mx2.getWidthElement()+mx2.getDis()), (int)(80*scale)-count1*(int)(mx2.getHeightElement()+mx2.getDis()));
                mxr.translateLabelElement(count1, count2, 2, (int)(12*scale)-count2*(int)(mxr.getWidthElement()+mxr.getDis()), (int)(80*scale)-count1*(int)(mxr.getHeightElement()+mxr.getDis()));
            }     
        });
        
        Play.getKeyFrames().add(kf);  
    }
    
    public void visibleLabelElement(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mx1.fadeOutLabelElement(count1, count2, 1);
                mx2.fadeOutLabelElement(count1, count2, 1);
                mxr.fadeOutLabelElement(count1, count2, 1);
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void setPosToCalculation(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mx1.translateElement(count1, count2, 1, -count2*(int)(mx1.getWidthElement()+mx1.getDis()), -count1*(int)(mx1.getHeightElement()+mx1.getDis()));
                mx2.translateElement(count1, count2, 1, (int)(70*scale+mx1.getWidthElement()-mx2.getX())-count2*(int)(mx2.getWidthElement()+mx2.getDis()), -count1*(int)(mx2.getHeightElement()+mx2.getDis()));
                mxr.translateElement(count1, count2, 1, (int)(130*scale+mx1.getWidthElement()+mx2.getWidthElement()-mxr.getX())-count2*(int)(mxr.getWidthElement()+mxr.getDis()), -count1*(int)(mxr.getHeightElement()+mxr.getDis()));                           
                mx1.translateLabelElement(count1, count2, 1, (int)(12*scale)-count2*(int)(mx1.getWidthElement()+mx1.getDis()), -(int)(20*scale)-count1*(int)(mx1.getHeightElement()+mx1.getDis()));
                mx2.translateLabelElement(count1, count2, 1, (int)(70*scale+mx1.getWidthElement()-mx2.getX())+(int)(12*scale)-count2*(int)(mx2.getWidthElement()+mx2.getDis()), -(int)(20*scale)-count1*(int)(mx2.getHeightElement()+mx2.getDis()));
                mxr.translateLabelElement(count1, count2, 1, (int)(130*scale+mx1.getWidthElement()+mx2.getWidthElement()-mxr.getX())+(int)(12*scale)-count2*(int)(mxr.getWidthElement()+mxr.getDis()), -(int)(20*scale)-count1*(int)(mxr.getHeightElement()+mxr.getDis()));
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void executeCalculation(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mxr.setValueElement(count1, count2, mx1.getValueElement(count1, count2)+mx2.getValueElement(count1, count2));
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void hideBeforeReturnPos(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mx1.fadeInElement(count1, count2, 1);
                mx2.fadeInElement(count1, count2, 1);
                mx1.fadeInLabelElement(count1, count2, 1);
                mx2.fadeInLabelElement(count1, count2, 1);
                fadeInArrSign(1);
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void hideLabelResultMatrix(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mxr.fadeInLabelElement(count1, count2, 1);
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void returnPos(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mxr.translateLabelElement(count1, count2, 1.5, mxr.getX()- mxr.getPosElement_X(count1, count2) + (int)(count2*(mxr.getWidthElement()+mxr.getDis())), (int)(300*scale));
                mx1.translateElement(count1, count2, 1.5, mx1.getX()- mx1.getPosElement_X(count1, count2)+ (int)(count2*(mx1.getWidthElement()+mx1.getDis())), (int)(300*scale));
                mx2.translateElement(count1, count2, 1.5, mx2.getX()- mx2.getPosElement_X(count1, count2)+ (int)(count2*(mx2.getWidthElement()+mx2.getDis())), (int)(300*scale));
                mx1.translateLabelElement(count1, count2, 1.5, mx1.getX()- mx1.getPosElement_X(count1, count2)+ (int)(count2*(mx1.getWidthElement()+mx1.getDis())), (int)(300*scale));
                mx2.translateLabelElement(count1, count2, 1.5, mx2.getX()- mx2.getPosElement_X(count1, count2)+ (int)(count2*(mx2.getWidthElement()+mx2.getDis())), (int)(300*scale));
                mxr.translateElement(count1, count2, 1.5, mxr.getX()- mxr.getPosElement_X(count1, count2) + (int)(count2*(mxr.getWidthElement()+mxr.getDis())), (int)(300*scale));
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void visibleElement(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mx1.fadeOutElement(count1, count2, 1);
                mx2.fadeOutElement(count1, count2, 1);
                increaseCount();
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
    
    public void increaseCount(){
        count2++;
        if(count2 == mx1.getCol()){
            count2=0;
            count1++;
        }
        if(count1 == mx1.getRow()){
            Play.stop();
        }
    }
}
