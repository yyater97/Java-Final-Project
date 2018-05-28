/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinalproject;

import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author banana
 */
public class Matrix extends Pane{
    private String name;
    private double dis = 2;
    private int row, col;
    private double widthElement, heightElement;
    private double width, height;
    private double scale;
    private int x, y;
    private TextField[][] arrElement;
    private Label[][] arrLaElement;
    
    public Matrix(String name, int row, int col, int x, int y, double widthElement, double heightElement, double scale) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        this.widthElement = widthElement;
        this.heightElement = heightElement;
        width = (widthElement+dis)*col-dis;
        height = (heightElement+dis)*row-dis;
        this.scale = scale;
        
        arrElement = new TextField[row][col];
        arrLaElement = new Label[row][col];
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //Đặt thuộc tính cho từng phần tử ma trận
                arrElement[i][j] = new TextField();
                arrElement[i][j].setLayoutX(x+(widthElement+dis)*j);
                arrElement[i][j].setLayoutY(y+(heightElement+dis)*i);
                arrElement[i][j].setPrefSize(widthElement, heightElement);
                arrElement[i][j].setFont(new Font("Arial",(widthElement<heightElement)?widthElement*30/100:heightElement*30/100));
                arrElement[i][j].setAlignment(Pos.CENTER);
                this.getChildren().add(arrElement[i][j]);
                
                //Đặt thuộc tính cho từng địa chỉ phần tử
                arrLaElement[i][j] = new Label(name+i+j);
                arrLaElement[i][j].setLayoutX(x+(widthElement+dis)*j);
                arrLaElement[i][j].setLayoutY(y+(heightElement+dis)*i);
                arrLaElement[i][j].setFont(new Font("Arial",(widthElement<heightElement)?widthElement*25/100:heightElement*25/100));
                arrLaElement[i][j].setTextFill(Color.RED);
                arrLaElement[i][j].setOpacity(0);
                this.getChildren().add(arrLaElement[i][j]);
            }
        }
    }
    
    public void randomValue(){
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                Random rd = new Random();
                arrElement[i][j].setText(rd.nextInt(50)+"");
            }
        }
    }
    
    public void fadeInElement(int row, int col, double duration){
        FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrElement[row][col]);
        fadein.setFromValue(1);
        fadein.setToValue(0);
        fadein.play();
    }
    
    public void fadeOutElement(int row, int col, double duration){
        FadeTransition fadeout = new FadeTransition(Duration.seconds(duration), arrElement[row][col]);
        fadeout.setFromValue(0);
        fadeout.setToValue(1);
        fadeout.play();
    }
    
    public void fadeInLabelElement(int row, int col, double duration){
        FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrLaElement[row][col]);
        fadein.setFromValue(1);
        fadein.setToValue(0);
        fadein.play();
    }
    
    public void fadeOutLabelElement(int row, int col, double duration){
        FadeTransition fadeout = new FadeTransition(Duration.seconds(duration), arrLaElement[row][col]);
        fadeout.setFromValue(0);
        fadeout.setToValue(1);
        fadeout.play();
    }
    
    public void translateElement(int row, int col, double duration, int desx, int desy){
        TranslateTransition tt = new TranslateTransition(Duration.seconds(duration), arrElement[row][col]);
        tt.setToX(desx);        
        tt.setToY(desy);
        tt.play();
    }

    public void translateLabelElement(int row, int col, double duration, int desx, int desy){
        TranslateTransition tt = new TranslateTransition(Duration.seconds(duration), arrLaElement[row][col]);
        tt.setToX(desx);
        tt.setToY(desy);
        tt.play();
    }
    
    public void translateMatrix(double duration, int desx, int desy){
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                translateElement(i, j, duration, desx, desy);
                translateLabelElement(i, j, duration, desx, desy);
            }
        }
    }
    
    public int getValueElement(int row, int col){
        return Integer.parseInt(arrElement[row][col].getText());
    }
    
    public void setValueElement(int row, int col, int value){
        arrElement[row][col].setText(value+"");
    }
    
    public int getPosElement_X(int row, int col){
        return (int)(arrElement[row][col].getLayoutX());
    }
    
    public int getPosElement_Y(int row, int col){
        return (int)(arrElement[row][col].getLayoutY());
    }
            
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getWidthElement() {
        return widthElement;
    }

    public double getHeightElement() {
        return heightElement;
    }

    public double getScale() {
        return scale;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setWidthElement(double widthElement) {
        this.widthElement = widthElement;
    }

    public void setHeightElement(double heightElement) {
        this.heightElement = heightElement;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public double get_Width() {
        return width;
    }

    public double get_Height() {
        return height;
    }
    
    public double getDis(){
        return dis;
    }
}
