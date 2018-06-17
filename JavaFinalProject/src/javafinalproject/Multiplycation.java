/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
public class Multiplycation extends Operator {

    public Multiplycation(Pane parentPane, double scale) {
        super(parentPane, scale);
        name="Multiplycation";
    }

    public void create(int row1, int col1, int col2, int x, int y, double widthElement, double heightElement) {
        mx1 = new Matrix("A", row1, col1, x, y, widthElement, heightElement, scale);
        mx1.randomValue();
        mx2 = new Matrix("B", col1, col2, (int) (x + mx1.get_Width() + 180 * scale), y, widthElement, heightElement, scale);
        mx2.randomValue();
        mxr = new Matrix("R", row1, col2, (int) (mx2.getX() + mx2.get_Width() + 180 * scale), y, widthElement, heightElement, scale);

        parentPane.getChildren().add(mx1);
        parentPane.getChildren().add(mx2);
        parentPane.getChildren().add(mxr);

        //Đặt giá trị cho dấu lớn
        arrMainSign = new Label[2];
        arrMainSign[0] = new Label("x");
        arrMainSign[0].setFont(new Font("Arial", 60 * scale));
        arrMainSign[0].setLayoutX((mx2.getX() + mx1.get_Width() + mx1.getX()) / 2 - 20 * scale);
        arrMainSign[0].setLayoutY(mx2.get_Height() < mx1.get_Height() ? mx1.get_Height() / 2 - 10 : mx2.get_Height() / 2);
        arrMainSign[0].setOpacity(0);
        parentPane.getChildren().add(arrMainSign[0]);

        arrMainSign[1] = new Label("=");
        arrMainSign[1].setFont(new Font("Arial", 60 * scale));
        arrMainSign[1].setLayoutX((mxr.getX() + mx2.get_Width() + mx2.getX()) / 2 - 20 * scale);
        arrMainSign[1].setLayoutY(mx2.get_Height() < mx1.get_Height() ? mx1.get_Height() / 2 - 10 : mx2.get_Height() / 2);
        arrMainSign[1].setOpacity(0);
        parentPane.getChildren().add(arrMainSign[1]);

        //Đặt giá trị cho 2 dấu nhỏ
        arrSign = new Label[mx1.getCol() * 2];
        int i;
        for (i = 0; i < arrSign.length; i++) {
            if (i < arrSign.length - 1) {
                if (i % 2 == 0) {
                    arrSign[i] = new Label("x");
                    arrSign[i].setFont(new Font("Arial", 30 * scale));
                    arrSign[i].setLayoutX(20 * scale + (30 * scale + mx1.getWidthElement()) * (i/2 + 1) + (130 * scale + mx2.getWidthElement()) * i/2);
                    arrSign[i].setLayoutY(mx1.getHeightElement() - 24 * scale);
                    arrSign[i].setOpacity(0);
                    parentPane.getChildren().add(arrSign[i]);
                } else {
                    arrSign[i] = new Label("+");
                    arrSign[i].setFont(new Font("Arial", 30 * scale));
                    arrSign[i].setLayoutX(-6 * scale + (110 * scale + mx1.getWidthElement() + mx2.getWidthElement()) * (i/2 + 1) + 50 * scale * i/2);
                    arrSign[i].setLayoutY(mx1.getHeightElement() - 22 * scale);
                    arrSign[i].setOpacity(0);
                    parentPane.getChildren().add(arrSign[i]);
                }
            } else {
                arrSign[i] = new Label("=");
                arrSign[i].setFont(new Font("Arial", 30 * scale));
                arrSign[i].setLayoutX(-6 * scale + (110 * scale + mx1.getWidthElement() + mx2.getWidthElement()) * (i/2 + 1) + 50*scale * i/2);
                arrSign[i].setLayoutY(mx1.getHeightElement() - 22 * scale);
                arrSign[i].setOpacity(0);
                parentPane.getChildren().add(arrSign[i]);
            }
        }
    }
    
    public double getTotalTime(){
        return 14.5*mx1.getRow()*mx2.getCol();
    }

    public void moveUp(double start) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx1.getCol(); k++) {
                    mx1.translateElement(count1, k, 2, 0, (int) (140 * scale) - mx1.getPosElement_Y(count1, k));
                    mx2.translateElement(k, count2, 2, 0, (int) (140 * scale) - mx2.getPosElement_Y(k, count2) + (int) (mx2.getHeightElement() + mx2.getDis()) * k);
                    mx1.translateLabelElement(count1, k, 2, (int) (14 * scale), (int) (120 * scale) - mx1.getPosElement_Y(count1, k));
                    mx2.translateLabelElement(k, count2, 2, -(int) (28 * scale), (int) (160 * scale) - mx2.getPosElement_Y(k, count2) + (int) (mx2.getHeightElement() + mx2.getDis()) * k);
                }
                mxr.translateElement(count1, count2, 2, 0, (int) (140 * scale) - mxr.getPosElement_Y(count1, count2));
                mxr.translateLabelElement(count1, count2, 2, (int) (14 * scale), (int) (120 * scale) - mxr.getPosElement_Y(count1, count2));
            }
        });

        Play.getKeyFrames().add(kf);
    }

    public void visibleLabelElement(double start) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx1.getCol(); k++) {
                    mx1.fadeOutLabelElement(count1, k, 1);
                    mx2.fadeOutLabelElement(k, count2, 1);
                }
                mxr.fadeOutLabelElement(count1, count2, 1);
            }
        });

        Play.getKeyFrames().add(kf);
    }

    public void setPosToCalculation(double start) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int k;
                for (k = 0; k < mx1.getCol(); k++) {
                    mx1.translateElement(count1, k, 1, (int)(20*scale+(mx1.getWidthElement()+mx2.getWidthElement()+160*scale)*k)-mx1.getPosElement_X(count1, k),(int)(20*scale)-mx1.getPosElement_Y(count1, k));
                    mx1.translateLabelElement(count1, k, 1, (int) (14 * scale) + (int)(20*scale+(mx1.getWidthElement()+mx2.getWidthElement()+160*scale)*k)-mx1.getPosElement_X(count1, k), -mx1.getPosElement_Y(count1, k));
                    
                    mx2.translateElement(k, count2, 1, (int)(20*scale+(80*scale+mx1.getWidthElement())*(k+1)+(mx2.getWidthElement()+80*scale)*k)-mx2.getPosElement_X(k,count2), (int)(20*scale)-mx2.getPosElement_Y(k, count2));
                    mx2.translateLabelElement(k, count2, 1, (int) (14 * scale) + (int)(20*scale+(80*scale+mx1.getWidthElement())*(k+1)+(mx2.getWidthElement()+80*scale)*k)-mx2.getPosElement_X(k,count2), -mx2.getPosElement_Y(k, count2));                   
                }
                mxr.translateElement(count1, count2, 1, (int)(20*scale+(mx1.getWidthElement()+mx2.getWidthElement()+160*scale)*k)-mxr.getPosElement_X(count1, count2),(int)(20*scale)-mxr.getPosElement_Y(count1, count2));
                mxr.translateLabelElement(count1, count2, 1, (int) (14 * scale) + (int)(20*scale+(mx1.getWidthElement()+mx2.getWidthElement()+160*scale)*k)-mxr.getPosElement_X(count1, count2), -mxr.getPosElement_Y(count1, count2));
            }
        });

        Play.getKeyFrames().add(kf);
    }

    public void executeCalculation(double start) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int value = 0;
                for (int k = 0; k < mx1.getCol(); k++) { 
                    value += (mx1.getValueElement(count1, k)* mx2.getValueElement(k, count2));
                }
                mxr.setValueElement(count1,count2,value);
            }
        });

        Play.getKeyFrames().add(kf);
    }

    public void hideBeforeReturnPos(double start) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx1.getCol(); k++) {    
                    mx1.fadeInElement(count1, k, 1);
                    mx2.fadeInElement(k, count2, 1);
                    mx1.fadeInLabelElement(count1, k, 1);
                    mx2.fadeInLabelElement(k, count2, 1);
                }
                fadeInArrSign(1);
            }
        });

        Play.getKeyFrames().add(kf);
    }

    public void returnPos(double start) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                mxr.translateLabelElement(count1, count2, 1.5, mxr.getX()- mxr.getPosElement_X(count1, count2) + (int)(count2*(mxr.getWidthElement()+mxr.getDis())), (int)(330*scale)-mxr.getPosElement_Y(count1, count2)+ count1*(int)(mxr.getWidthElement() + mxr.getDis()));
                for (int k = 0; k < mx1.getCol(); k++) {  
                mx1.translateElement(count1, k, 1.5, mx1.getX() - mx1.getPosElement_X(count1, 0) , (int) (330 * scale)-mx1.getPosElement_Y(count1, k) + count1*(int)(mx1.getWidthElement() + mx1.getDis()));
                mx1.translateLabelElement(count1, k, 1.5, mx1.getX() - mx1.getPosElement_X(count1, 0), (int) (330 * scale)-mx1.getPosElement_Y(count1, k)  + count1*(int)(mx1.getWidthElement() + mx1.getDis()));
                
                mx2.translateElement(k, count2, 1.5, mx2.getX() - mx2.getPosElement_X(k, count2) + (int) (count2 * (mx2.getWidthElement() + mx2.getDis())), (int) (330 * scale)-mx2.getPosElement_Y(k, count2)+k*(int)(mx2.getWidthElement() + mx2.getDis()));                
                mx2.translateLabelElement(k, count2, 1.5, mx2.getX() - mx2.getPosElement_X(k, count2) + (int) (count2 * (mx2.getWidthElement() + mx2.getDis())), (int) (330 * scale)-mx2.getPosElement_Y(k, count2)+k*(int)(mx2.getWidthElement() + mx2.getDis()));
                }
                mxr.translateElement(count1, count2, 1.5, mxr.getX() - mxr.getPosElement_X(count1, count2) + (int) (count2 * (mxr.getWidthElement() + mxr.getDis())), (int) (330 * scale)-mxr.getPosElement_Y(count1, count2)+ count1*(int)(mxr.getWidthElement() + mxr.getDis()));
            }
        });

        Play.getKeyFrames().add(kf);
    }

    public void visibleElement(double start) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx1.getCol(); k++) {
                    mx1.fadeOutElement(count1, k, 1);
                    mx2.fadeOutElement(k, count2, 1);
                }
                increaseCount();
            }
        });

        Play.getKeyFrames().add(kf);
    }
    
    public void increaseCount(){
        count2++;
        if(count2 == mx2.getCol()){
            count2=0;
            count1++;
        }
        if(count1 == mx1.getRow()){
            Play.stop();
        }
    }
}
