/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author banana
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane panelSimulation;
    @FXML
    private AnchorPane panelControl;
    @FXML
    private Slider sliderTimer;
    @FXML
    private TextField txtMatrix1_Row;
    @FXML
    private TextField txtMatrix1_Col;
    @FXML
    private TextField txtMatrix2_Col;
    @FXML
    private Button btnCreate;
    @FXML
    private ComboBox<String> cbbCalculation;
    @FXML
    private Button btnPause;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnReplay;
    @FXML
    private Label txtTime;
    
    private Summation sum;
    private Subtraction sub;
    private Multiplycation multi;
    private Determinant det;
    private Timeline tlSlider;
    @FXML
    private TextField txtMatrix2_Row;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[]list = {"Cộng ma trận","Trừ ma trận","Nhân ma trận","Tính định thức"};
        cbbCalculation.getItems().addAll(list);
        cbbCalculation.getSelectionModel().select(3);
        cbbCalculation_ItemSelectChanged();
    }
    
    public void cbbCalculation_ItemSelectChanged(){
        cbbCalculation.valueProperty().addListener(new ChangeListener<String>() {
        @Override 
        public void changed(ObservableValue ov, String t, String t1) {
          switch(ov.getValue().toString()){
            case "Cộng ma trận": case "Trừ ma trận":{
                txtMatrix1_Row.setDisable(false);
                txtMatrix1_Col.setDisable(false);               
                txtMatrix2_Row.setDisable(true);
                txtMatrix2_Col.setDisable(true);
                break;
            }
            case "Nhân ma trận":{
                txtMatrix1_Row.setDisable(false);
                txtMatrix1_Col.setDisable(false);                               
                txtMatrix2_Row.setDisable(true);
                txtMatrix2_Col.setDisable(false);
                break;
            }
            case "Tính định thức":{
                txtMatrix1_Row.setDisable(false);
                txtMatrix1_Col.setDisable(true);
                txtMatrix2_Row.setDisable(true);
                txtMatrix2_Col.setDisable(true);
                break;
            }
          }
        }    
    });
    }

    @FXML
    private void btnCreate_MouseClicked(MouseEvent event) {
        cbbCalculation.setDisable(true);
        btnCreate.setDisable(true);
        
        //Lấy giá trị hàng cột của 2 ma trận
        int row1 = Integer.parseInt(txtMatrix1_Row.getText());
        int col1 = Integer.parseInt(txtMatrix1_Col.getText());
        int row2 = Integer.parseInt(txtMatrix2_Row.getText());
        int col2 = Integer.parseInt(txtMatrix2_Col.getText());
        double totalTime = 0;
        
        switch(cbbCalculation.getValue()){
            case "Cộng ma trận":{
                sum = new Summation(panelSimulation,1); 
                sum.create(row1, col1, 10, 30, 50, 50);
                totalTime = sum.getTotalTime();
                break;
            }
            case "Trừ ma trận":{
                sub = new Subtraction(panelSimulation,1);
                sub.create(row1, col1, 10, 30, 50, 50);
                totalTime = sum.getTotalTime();
                break;
            }
            case "Nhân ma trận":{
                multi = new Multiplycation(panelSimulation, 1);
                multi.create(row1, col1, col2, 10, 30, 50, 50);
                totalTime = multi.getTotalTime();
                break;
            }
            case "Tính định thức":{
                det = new Determinant(panelSimulation,1);
                if(row1<2){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Cảnh báo!!!");
                    alert.setContentText("Số hàng (cột) của ma trận vuông phải lớn hơn hoặc bằng 2 (>=2)! Vui lòng nhập lại để tiếp tục");
                    alert.showAndWait();
                    cbbCalculation.setDisable(false);
                    btnCreate.setDisable(false);
                    return;
                }
                det.create(row1, 10, 30, 50, 50);
                totalTime = det.getTotalTime();
                break;
            }
        }
        
        setTimeline_Slider(totalTime);
        txtTime.setText("0.0/"+totalTime);
        sliderTimer.setMax(totalTime);
        sliderTimer.setMin(0);
    }
    
    private double currentTime = 0;
    public void setTimeline_Slider(double totalTime){
        
        tlSlider = new Timeline();
        
        KeyFrame kf = new KeyFrame(Duration.seconds(0.1),new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                currentTime+=0.1;
                sliderTimer.setValue(currentTime);
                currentTime=((double)Math.round(currentTime*10))/10;
                txtTime.setText(currentTime+"/"+totalTime);
                if(currentTime >= totalTime)
                    tlSlider.stop();
            }
            
        });
        tlSlider.getKeyFrames().add(kf);
        tlSlider.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    private void btnPause_MouseClicked(MouseEvent event) {
        btnPause.setDisable(true);
        btnPlay.setDisable(false);
        switch(cbbCalculation.getValue()){
            case "Cộng ma trận":{
                sum.pause();
                break;
            }
            case "Trừ ma trận":{
                sub.pause();
                break;
            }
            case "Nhân ma trận":{
                multi.pause();
                break;
            }
            case "Tính định thức":{
                break;
            }
        }
        tlSlider.pause();
    }

    @FXML
    private void btnStop_MouseClicked(MouseEvent event) {
        btnStop.setDisable(true);
        switch(cbbCalculation.getValue()){
            case "Cộng ma trận":{
                sum.stop();
                break;
            }
            case "Trừ ma trận":{
                sub.stop();
                break;
            }
            case "Nhân ma trận":{
                multi.stop();
                break;
            }
            case "Tính định thức":{
                break;
            }
        }
        tlSlider.stop();
    }

    @FXML
    private void btnPlay_MouseClicked(MouseEvent event) {
        btnPlay.setDisable(true);
        switch(cbbCalculation.getValue()){
            case "Cộng ma trận":{
                sum.play();
                break;
            }
            case "Trừ ma trận":{
                sub.play();
                break;
            }
            case "Nhân ma trận":{
                multi.play();
                break;
            }
            case "Tính định thức":{
                det.play();
                break;
            }
        }
        tlSlider.play();       
    }

    @FXML
    private void btnReplay_MouseClicked(MouseEvent event) {
    }
    
}
