package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.InputStream;


public class Controller {

@FXML private Button btn_play;
@FXML private Button btn_last;
@FXML private Button btn_next;
@FXML private ImageView img_bg;
@FXML private Slider slider_progress;
@FXML private ListView lv_media;
@FXML private MediaView media_view;
@FXML private Label lyric;
private int change;
    AudioPlayer audioPlayer;




    public void initialize() {
       audioPlayer =new AudioPlayer(this);
        initSlider();
        System.out.println(lv_media);


    }

    public Label getLyric() {
        return lyric;
    }

    public MediaView getMedia_view() {
        return media_view;
    }

    public  Slider getSlider_progress() {
        return slider_progress;
    }

    public ListView getLv_media() {
        return lv_media;
    }

    public ImageView getImg_bg() {
        return img_bg;
    }

    private  void initSlider(){
        System.out.println(slider_progress);
        slider_progress.setMax(10000);
        slider_progress.setMin(0);
        slider_progress.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() - oldValue.doubleValue() > 100 || newValue.doubleValue() - oldValue.doubleValue() < -100) {
                    System.out.println("new value" + newValue.doubleValue());
                    audioPlayer.playOrpaused(newValue.doubleValue());
                }
            }
        });
    }




    @FXML protected void handle_play(ActionEvent event) {
        System.out.println(btn_play);


            audioPlayer.playOrpaused();







    }



    @FXML protected void handle_last(ActionEvent event) {
        audioPlayer.playlast();
         changeImage();
    }

    @FXML protected void handle_next(ActionEvent event) {
       audioPlayer.playnext();
   changeImage();}


    public boolean changeImage(){
        if(audioPlayer.getImage()!=null){
            InputStream inputStream= new ByteArrayInputStream(audioPlayer.getImage());
            javafx.scene.image.Image image=new javafx.scene.image.Image(inputStream);
            img_bg.setImage(image);
            return  true;
        }else {
            return false;
        }
    }


    public void handle_add(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("选择要给MP3或者视频文件");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3","*.mp3"),
                new FileChooser.ExtensionFilter("MP4","*.mp4"),
                new FileChooser.ExtensionFilter("FLV","*.flv")
        );
        Stage s=new Stage();
        File file=fileChooser.showOpenDialog(s);
        audioPlayer.add(file.getPath());
    }

    public void handle_info(ActionEvent actionEvent) {
        Mp3 mp3=audioPlayer.returnInfo();
        Alert information = new Alert(Alert.AlertType.INFORMATION,"曲名"+
        mp3.Title+"\n作者"+mp3.Artist+"\n专辑"+mp3.Album);
       information.show();


    }
}
