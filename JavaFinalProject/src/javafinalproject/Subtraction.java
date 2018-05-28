/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinalproject;

import javafx.animation.KeyFrame;
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
public class Subtraction extends Summation{

    public Subtraction(Pane parentPane, double scale) {
        super(parentPane, scale);
        name="Subtraction";
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
        arrMainSign[0] = new Label("-");
        arrMainSign[0].setFont(new Font("Arial",50*scale));
        arrMainSign[0].setLayoutX((mx2.getX() + mx1.get_Width()+ mx1.getX())/2-10*scale);
        arrMainSign[0].setLayoutY(mx2.get_Height()<mx1.get_Height()?mx1.get_Height()/2-10:mx2.get_Height()/2);
        arrMainSign[0].setOpacity(0);
        parentPane.getChildren().add(arrMainSign[0]);
        
        arrMainSign[1] = new Label("=");
        arrMainSign[1].setFont(new Font("Arial",50*scale));
        arrMainSign[1].setLayoutX((mxr.getX() + mx2.get_Width()+mx2.getX())/2-10*scale);
        arrMainSign[1].setLayoutY(mx2.get_Height()<mx1.get_Height()?mx1.get_Height()/2-10:mx2.get_Height()/2);
        arrMainSign[1].setOpacity(0);
        parentPane.getChildren().add(arrMainSign[1]);
        
        //Đặt giá trị cho 2 dấu nhỏ
        arrSign = new Label[2];
        arrSign[0] = new Label("-");
        arrSign[0].setFont(new Font("Arial",30*scale));
        arrSign[0].setLayoutX(35*scale+mx1.getWidthElement());
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
    
    public void executeCalculation(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                mxr.setValueElement(count1, count2, mx1.getValueElement(count1, count2)-mx2.getValueElement(count1, count2));
            }     
        });
        
        Play.getKeyFrames().add(kf);
    }
}
