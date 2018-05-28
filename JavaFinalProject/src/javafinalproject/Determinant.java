/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinalproject;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author banana
 */
public class Determinant extends Calculation {

    private Matrix mx;
    private int count = 0;
    private TextField[] arrTemp;
    private Label[] arrSign1;
    private Timeline mainDiagonal, antiDiagonal, cal;

    public Determinant(Pane parentPane, double scale) {
        this.parentPane = parentPane;
        this.scale = scale;
        name = "Determinant";
    }

    public void create(int row, int x, int y, double widthElement, double heightElement) {
        mx = new Matrix("Q", row, row, x, y, widthElement, heightElement, scale);
        mx.randomValue();
        parentPane.getChildren().add(mx);

        double we = mx.getWidthElement();
        double he = mx.getHeightElement();

        //Đặt giá trị cho các biến tạm
        arrTemp = new TextField[mx.getCol() * 2 + 2];
        for (int i = 0; i < arrTemp.length; i++) {
            arrTemp[i] = new TextField();
            arrTemp[i].setLayoutX(30);
            arrTemp[i].setLayoutY(300);
            arrTemp[i].setPrefSize(we, he);
            arrTemp[i].setFont(new Font("Arial", (we < he) ? we * 30 / 100 : he * 30 / 100));
            arrTemp[i].setAlignment(Pos.CENTER);
            arrTemp[i].setOpacity(0);
            //arrTemp[i].setText(i+"");
            parentPane.getChildren().add(arrTemp[i]);
        }

        //Đặt giá trị cho 2 dấu nhỏ
        arrSign = new Label[mx.getCol() - 1];
        for (int i = 0; i < arrSign.length; i++) {
            arrSign[i] = new Label("x");
            arrSign[i].setFont(new Font("Arial", 30 * scale));
            arrSign[i].setLayoutX(105 * scale + we + (we + 15 * scale) * (i + 1) + 45 * scale * i);
            arrSign[i].setLayoutY(302 * scale);
            arrSign[i].setOpacity(0);
            parentPane.getChildren().add(arrSign[i]);
        }

        arrSign1 = new Label[mx.getCol() + 1];
        for (int i = 0; i < arrSign1.length; i++) {
            if (i < arrSign1.length - 2) {
                arrSign1[i] = new Label("+");
            } else {
                arrSign1[i] = new Label("=");
            }
            arrSign1[i].setFont(new Font("Arial", 30 * scale));
            if (i < arrSign1.length - 1) {
                arrSign1[i].setLayoutY(66 * scale + mx.getHeightElement());
                arrSign1[i].setLayoutX(60 * scale + (mx.getWidthElement() + 15 * scale) * (i + 1) + 45 * scale * i);
            } else {
                arrSign1[i].setLayoutY(36 * scale);
                arrSign1[i].setLayoutX(60 * scale + (mx.getWidthElement() + 15 * scale) * i + 45 * scale * (i - 1));
            }
            arrSign1[i].setOpacity(0);
            parentPane.getChildren().add(arrSign1[i]);
        }
    }

    public void fadeOutTemp(int pos, double duration) {
        FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrTemp[pos]);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();
    }

    public void fadeInTemp(int pos, double duration) {
        FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrTemp[pos]);
        fadein.setFromValue(1);
        fadein.setToValue(0);
        fadein.play();
    }

    public void fadeInArrSign1(int pos, double duration) {
        FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrSign1[pos]);
        fadein.setFromValue(1);
        fadein.setToValue(0);
        fadein.play();
    }

    public void fadeOutArrSign1(int pos, double duration) {
        FadeTransition fadein = new FadeTransition(Duration.seconds(duration), arrSign1[pos]);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();
    }

    public void translateTemp(int pos, double duration, int desx, int desy) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(duration), arrTemp[pos]);
        tt.setToX(desx);
        tt.setToY(desy);
        tt.play();
    }

    public void play() {
        //Timeline chuẩn bị vị trí cho các ma trận
        prePare = new Timeline();
        preparePosition(0.2);
        stopPrepare_playMainDiag(3.5);
        prePare.play();

        double time = 0;
        //Timeline chính gồm đường chéo chính và đường chéo phụ
        mainDiagonal = new Timeline();
        antiDiagonal = new Timeline();
    }

    public double getTotalTime() {
        if(mx.getRow()==2)
            return 33.26;
        else{
            int length = mx.getRow();
            return 14.5+(11+0.095*length*length)*2*length;
        }
    }

    public void preparePosition(double start) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                mx.translateMatrix(3, (int) (1000 * scale), 0);
            }
        });
        prePare.getKeyFrames().add(kf);
    }
    
    public void stopPrepare_playMainDiag(double start){
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                prePare.stop();
                Chilren_Play(1, mainDiagonal, 0);
            }
        });
        prePare.getKeyFrames().add(kf);
    }

    public void Chilren_Play(double start, Timeline timeLine, int type) {

        double time = start;
        moveLeft(time, timeLine, type);
        time += 2;
        visibleLabelElement(time, timeLine, type);
        time += 1.5;
        setPosToCalculation(time, timeLine, type);
        time += 2;
        visibleSign(time, timeLine);
        time += 1;
        executeCalculation(time, timeLine, type);
        time += (0.095 * mx.getRow() * mx.getRow() + 1);
        moveTemp(time, timeLine, type);
        time += 0.5;
        returnPos(time, timeLine, type);
        time += 1;
        visibleElement(time, timeLine, type);
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    public void moveLeft(double start, Timeline timeLine, int type) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx.getRow(); k++) {
                    if (type == 0) {
                        mx.translateElement(k, (count + k) % mx.getRow(), 2, (int) (700 * scale), 0);
                        mx.translateLabelElement(k, (count + k) % mx.getRow(), 2, (int) (712 * scale), (int) -(15 * scale));
                    } else {
                        mx.translateElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 2, (int) (700 * scale), 0);
                        mx.translateLabelElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 2, (int) (712 * scale), (int) -(15 * scale));
                    }
                }
            }
        });
        timeLine.getKeyFrames().add(kf);
    }

    public void visibleLabelElement(double start, Timeline timeLine, int type) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx.getRow(); k++) {
                    if (type == 0) {
                        mx.fadeOutLabelElement(k, (count + k) % mx.getRow(), 1);
                    } else {
                        mx.fadeOutLabelElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 1);
                    }
                }
            }
        });
        timeLine.getKeyFrames().add(kf);
    }

    public void setPosToCalculation(double start, Timeline timeLine, int type) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx.getRow(); k++) {
                    if (type == 0) {
                        mx.translateElement(k, (count + k) % mx.getRow(), 2, (int) (100 * scale) + (int) mx.getWidthElement() + (int) (mx.getWidthElement() + 60 * scale) * k - mx.getPosElement_X(k, (count + k) % mx.getRow()), (int) (300 * scale) - mx.getPosElement_Y(k, (count + k) % mx.getRow()));
                        mx.translateLabelElement(k, (count + k) % mx.getRow(), 2, (int) (112 * scale) + (int) mx.getWidthElement() + (int) (mx.getWidthElement() + 60 * scale) * k - mx.getPosElement_X(k, (count + k) % mx.getRow()), (int) (285 * scale) - mx.getPosElement_Y(k, (count + k) % mx.getRow()));
                    } else {
                        mx.translateElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 2, (int) (100 * scale) + (int) mx.getWidthElement() + (int) (mx.getWidthElement() + 60 * scale) * k - mx.getPosElement_X((mx.getRow() - 1 + count - k) % mx.getRow(), k), (int) (300 * scale) - mx.getPosElement_Y((mx.getRow() - 1 + count - k) % mx.getRow(), k));
                        mx.translateLabelElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 2, (int) (112 * scale) + (int) mx.getWidthElement() + (int) (mx.getWidthElement() + 60 * scale) * k - mx.getPosElement_X((mx.getRow() - 1 + count - k) % mx.getRow(), k), (int) (285 * scale) - mx.getPosElement_Y((mx.getRow() - 1 + count - k) % mx.getRow(), k));
                    }
                }
            }
        });
        timeLine.getKeyFrames().add(kf);
    }

    public void visibleSign(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                fadeOutArrSign(1);
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void executeCalculation(double start, Timeline timeLine, int type) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int k, value = 1;
                for (k = 0; k < mx.getRow(); k++) {
                    if (type == 0) {
                        mx.translateElement(k, (count + k) % mx.getRow(), 1, (int) (30 * scale) - mx.getPosElement_X(k, (count + k) % mx.getRow()), (int) (300 * scale) - mx.getPosElement_Y(k, (count + k) % mx.getRow()));
                        mx.translateLabelElement(k, (count + k) % mx.getRow(), 1, (int) (42 * scale) - mx.getPosElement_X(k, (count + k) % mx.getRow()), (int) (285 * scale) - mx.getPosElement_Y(k, (count + k) % mx.getRow()));

                        if (k < mx.getRow() - 1) {
                            fadeInASign(k, 0.6 / (k + 1));
                        }
                        mx.fadeInElement(k, (count + k) % mx.getRow(), 0.095 * mx.getRow() * (k + 1));
                        mx.fadeInLabelElement(k, (count + k) % mx.getRow(), 0.095 * mx.getRow() * (k + 1));
                        value *= mx.getValueElement(k, (count + k) % mx.getRow());
                    } else {
                        mx.translateElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 1, (int) (30 * scale) - mx.getPosElement_X((mx.getRow() - 1 + count - k) % mx.getRow(), k), (int) (300 * scale) - mx.getPosElement_Y((mx.getRow() - 1 + count - k) % mx.getRow(), k));
                        mx.translateLabelElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 1, (int) (42 * scale) - mx.getPosElement_X((mx.getRow() - 1 + count - k) % mx.getRow(), k), (int) (285 * scale) - mx.getPosElement_Y((mx.getRow() - 1 + count - k) % mx.getRow(), k));

                        if (k < mx.getRow() - 1) {
                            fadeInASign(k, 0.095 * mx.getRow() * (k + 1));
                        }
                        mx.fadeInElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 0.095 * mx.getRow() * (k + 1));
                        mx.fadeInLabelElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 0.095 * mx.getRow() * (k + 1));
                        value *= mx.getValueElement((mx.getRow() - 1 + count - k) % mx.getRow(), k);
                    }
                }
                if (type == 0) {
                    arrTemp[count].setText(value + "");
                    fadeOutTemp(count, 0.095 * mx.getRow() * (k + 1));
                } else {
                    arrTemp[count + mx.getRow()].setText(value + "");
                    fadeOutTemp(count + mx.getRow(), 0.095 * mx.getRow() * (k + 1));
                }

            }
        });
        timeLine.getKeyFrames().add(kf);
    }

    public void moveTemp(double start, Timeline timeLine, int type) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (type == 0) {
                    translateTemp(count, 1, (int) (30 * scale + (mx.getWidthElement() + 60 * scale) * count), (int) (30 * scale - arrTemp[count].getLayoutY()));
                } else {
                    translateTemp(count + mx.getRow(), 1, (int) (30 * scale + (mx.getWidthElement() + 60 * scale) * count), (int) (60 * scale + mx.getHeightElement() - arrTemp[count + mx.getRow()].getLayoutY()));
                }
            }
        });
        timeLine.getKeyFrames().add(kf);
    }

    public void returnPos(double start, Timeline timeLine, int type) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx.getRow(); k++) {
                    if (type == 0) {
                        mx.translateElement(k, (count + k) % mx.getRow(), 0.5, (int) (1010 * scale + (count + k) % mx.getRow() * (mx.getWidthElement() + mx.getDis()) - mx.getPosElement_X(k, (count + k) % mx.getRow())), (int) (30 * scale + k * (mx.getHeightElement() + mx.getDis()) - mx.getPosElement_Y(k, (count + k) % mx.getRow())));
                        mx.translateLabelElement(k, (count + k) % mx.getRow(), 0.5, (int) (1022 * scale + (count + k) % mx.getRow() * (mx.getWidthElement() + mx.getDis()) - mx.getPosElement_X(k, (count + k) % mx.getRow())), (int) (30 * scale + k * (mx.getHeightElement() + mx.getDis()) - mx.getPosElement_Y(k, (count + k) % mx.getRow())));
                    } else {
                        mx.translateElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 0.5, (int) (1010 * scale + k * (mx.getWidthElement() + mx.getDis()) - mx.getPosElement_X((mx.getRow() - 1 + count - k) % mx.getRow(), k)), (int) (30 * scale + (mx.getRow() - 1 + count - k) % mx.getRow() * (mx.getHeightElement() + mx.getDis()) - mx.getPosElement_Y((mx.getRow() - 1 + count - k) % mx.getRow(), k)));
                        mx.translateLabelElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 0.5, (int) (1022 * scale + k * (mx.getWidthElement() + mx.getDis()) - mx.getPosElement_X((mx.getRow() - 1 + count - k) % mx.getRow(), k)), (int) (30 * scale + (mx.getRow() - 1 + count - k) % mx.getRow() * (mx.getHeightElement() + mx.getDis()) - mx.getPosElement_Y((mx.getRow() - 1 + count - k) % mx.getRow(), k)));
                    }
                }
            }
        });
        timeLine.getKeyFrames().add(kf);
    }

    public void visibleElement(double start, Timeline timeLine, int type) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < mx.getRow(); k++) {
                    if (type == 0) {
                        mx.fadeOutElement(k, (count + k) % mx.getRow(), 1);
                    } else {
                        mx.fadeOutElement((mx.getRow() - 1 + count - k) % mx.getRow(), k, 1);
                    }
                }
                increaseCount(timeLine, type);
            }
        });
        timeLine.getKeyFrames().add(kf);
    }

    public void increaseCount(Timeline timeLine, int type) {
        count++;
        if (count == mx.getRow() || mx.getRow() == 2) {
            timeLine.stop();
            if (type == 0) {
                Chilren_Play(1, antiDiagonal, 1);
                count = 0;
            } else {
                count = 0;
                Cal_Play();
            }
        }
    }

    public void Cal_Play() {
        cal = new Timeline();
        double time = 1;
        if (mx.getRow() > 2) {
            moveSign();
            visibleSign(time, cal);
            visibleSign1(time, cal);
            time += 1;
            visibleTemp(time, cal);
            time += 1;
            DiagonalCalculated(time, cal);
            time += 1;
            hideAll(time, cal);
            time += 1;
        }
        subTemp(time, cal);
        time += 1;
        scaleCalSub(time, cal);
        time += 3;
        scaleSubResult(time, cal);
        time += 1;
        showResult(time, cal);
        cal.play();
    }

    public void moveSign() {
        for (int k = 0; k < arrSign.length; k++) {
            arrSign[k].setText("+");
            arrSign[k].setLayoutX(60 * scale + (mx.getWidthElement() + 15 * scale) * (k + 1) + 45 * scale * k);
            arrSign[k].setLayoutY(36 * scale);
        }
    }

    public void visibleSign1(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < arrSign1.length; k++) {
                    fadeOutArrSign1(k, 1);
                }
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void visibleTemp(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                arrTemp[arrTemp.length - 2].setLayoutX(60 * scale + (mx.getWidthElement() + 60 * scale) * mx.getRow());
                arrTemp[arrTemp.length - 2].setLayoutY(30 * scale);
                arrTemp[arrTemp.length - 1].setLayoutX(60 * scale + (mx.getWidthElement() + 60 * scale) * mx.getRow());
                arrTemp[arrTemp.length - 1].setLayoutY(60 * scale + mx.getHeightElement());
                fadeOutTemp(arrTemp.length - 1, 1);
                fadeOutTemp(arrTemp.length - 2, 1);
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void DiagonalCalculated(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int value1 = 0, value2 = 0;
                for (int k = 0; k < mx.getRow(); k++) {
                    value1 += (Integer.parseInt(arrTemp[k].getText()));
                    value2 += (Integer.parseInt(arrTemp[k + mx.getRow()].getText()));
                }
                arrTemp[arrTemp.length - 1].setText(value1 + "");
                arrTemp[arrTemp.length - 2].setText(value2 + "");
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void hideAll(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (int k = 0; k < arrSign1.length; k++) {
                    fadeInArrSign1(k, 1);
                }
                for (int k = 0; k < arrTemp.length - 2; k++) {
                    fadeInTemp(k, 1);
                }
                fadeInArrSign(1);
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void subTemp(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                arrSign1[arrSign1.length - 1].setText("-");
                arrSign1[arrSign1.length - 1].setFont(new Font("Arial", 40 * scale));
                if (mx.getRow() == 2) {
                    arrSign1[arrSign1.length - 1].setLayoutX(arrTemp[0].getLayoutX()+50*scale);
                    arrSign1[arrSign1.length - 1].setLayoutY((arrTemp[2].getLayoutY() + arrTemp[0].getLayoutY()) / 2 - 234 * scale);
                } else {
                    arrSign1[arrSign1.length - 1].setLayoutX(78 * scale + (mx.getWidthElement() + 60 * scale) * mx.getRow());
                    arrSign1[arrSign1.length - 1].setLayoutY((arrTemp[arrTemp.length - 2].getLayoutY() + arrTemp[arrTemp.length - 1].getLayoutY()) / 2 - 4 * scale);
                }
               
                fadeOutArrSign1(arrSign1.length - 1, 1);
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void scaleCalSub(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (mx.getRow() == 2) {
                    scaleTemp(0, 0.5, 2);
                    scaleTemp(2, 0.5, 2);
                    fadeInTemp(0, 1);
                    fadeInTemp(2, 1);
                } else {
                    scaleTemp(arrTemp.length - 1, 0.5, 2);
                    scaleTemp(arrTemp.length - 2, 0.5, 2);
                    fadeInTemp(arrTemp.length - 1, 1);
                    fadeInTemp(arrTemp.length - 2, 1);
                }
                fadeInArrSign1(arrSign1.length - 1, 1);
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void scaleSubResult(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (mx.getRow() == 2) {
                    int value = Integer.parseInt(arrTemp[0].getText()) - Integer.parseInt(arrTemp[2].getText());
                    arrTemp[0].setText(value + "");
                    arrTemp[0].setLayoutX(30 * scale + (mx.getWidthElement() + 60 * scale) * mx.getRow());
                    arrTemp[0].setLayoutY((arrTemp[0].getLayoutY() + arrTemp[2].getLayoutY()) / 2);
                    fadeOutTemp(0, 1);
                    scaleTemp(0, 1.5, 2);
                } else {
                    int value = Integer.parseInt(arrTemp[arrTemp.length - 1].getText()) - Integer.parseInt(arrTemp[arrTemp.length - 2].getText());
                    arrTemp[arrTemp.length - 1].setText(value + "");
                    arrTemp[arrTemp.length - 1].setLayoutX(30 * scale + (mx.getWidthElement() + 60 * scale) * mx.getRow());
                    arrTemp[arrTemp.length - 1].setLayoutY((arrTemp[arrTemp.length - 2].getLayoutY() + arrTemp[arrTemp.length - 1].getLayoutY()) / 2);
                    fadeOutTemp(arrTemp.length - 1, 1);
                    scaleTemp(arrTemp.length - 1, 1.5, 2);
                }
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void showResult(double start, Timeline timeLine) {
        KeyFrame kf = new KeyFrame(Duration.seconds(start), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (mx.getRow() == 2) {
                    arrSign1[arrSign1.length - 1].setText("=det(A)");
                    arrSign1[arrSign1.length - 1].setLayoutX(arrTemp[0].getLayoutX() + 150 * scale);
                    arrSign1[arrSign1.length - 1].setLayoutY(arrTemp[0].getLayoutY() - 280 * scale);
                } else {
                    arrSign1[arrSign1.length - 1].setText("det(A)=");
                    arrSign1[arrSign1.length - 1].setLayoutX(arrTemp[arrTemp.length - 1].getLayoutX() - 250 * scale);
                    arrSign1[arrSign1.length - 1].setLayoutY(arrTemp[arrTemp.length - 1].getLayoutY() - 20 * scale);

                }
                arrSign1[arrSign1.length - 1].setFont(new Font("Arial", 70 * scale));
                fadeOutArrSign1(arrSign1.length - 1, 2);
            }
        });

        timeLine.getKeyFrames().add(kf);
    }

    public void scaleTemp(int pos, double ratio, double duration) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(duration), arrTemp[pos]);
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(ratio);
        st.setToY(ratio);
        st.setAutoReverse(false);
        st.play();

    }
}
