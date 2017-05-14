package sample;


import Bean.Lycbean;
import Util.FileUtil;
import Util.GsonUtil;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;


import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANGJIAZHAO on 2017/3/27.
 */


public class AudioPlayer   {


      public  MediaPlayer mediaPlayer;
      private List<MediaPlayer> mp_list;
      private List<String>lyc_list;
      private List<Mp3> info_list;
      private  ObservableList<String> mp_name;
      private Controller controller;
      private ListView lv_media;
      private  int  cur_position=0;
      private MediaView mediaView;
      private ImageView imageView;




    public  AudioPlayer(Controller controller){
        this.controller=controller;
        this.lv_media=controller.getLv_media();
        this.mediaView=controller.getMedia_view();
        this.imageView=controller.getImg_bg();
       mp_list=new ArrayList<>();
       info_list=new ArrayList<>();
       lyc_list=new ArrayList<>();
       mp_name= FXCollections.observableArrayList();
       lv_media.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               System.out.println((String) newValue);
               for(int i=0;i<mp_name.size();i++){
                   if(mp_name.get(i).compareTo((String) newValue)==0){

                       if(mediaPlayer!=null) {
                           mediaPlayer.stop();
                       }
                       cur_position=i;
                       playOrpaused();
                       System.out.println(mp_name.get(cur_position).concat("mp3"));
                       if (mp_name.get(cur_position).contains(".mp3")){
                           controller.changeImage();
                       }else{
                           mediaView.setMediaPlayer(mediaPlayer);
                       }


                   }
               }
           }
       });


      }





     public  void add(String music){

      mp_list.add(new MediaPlayer(new Media(new File(music).toURI().toString())));

         try {
          if(music.contains(".mp3")){
              String lycdoc=music.replace(".mp3",".lyc");
              String lyc=FileUtil.readFileByBytes(lycdoc);
              if(lyc==null){
                  lyc_list.add("无歌词");
              }else {
                  Lycbean lycbean= GsonUtil.paresrJsontoObject(lyc,Lycbean.class);
                  lyc_list.add(lycbean.lyric);
                  System.out.println(lycbean.lyric);
              }
              mp_name.add(music.substring(music.lastIndexOf("\\")+1));
             Mp3File mp3File=new Mp3File(music);
             if(mp3File.hasId3v1Tag()){
                 Mp3_Id3v1 mp3_id3v1=new Mp3_Id3v1(mp3File.getId3v1Tag());
                 info_list.add(mp3_id3v1);
             }else if(mp3File.hasId3v2Tag()){
                 Mp3_Id3v2 mp3_id3v2=new Mp3_Id3v2(mp3File.getId3v2Tag());
                 info_list.add(mp3_id3v2);
             }else {
                 System.out.println("nothing");
             }
          }else {
              lyc_list.add("");
              Mp3 mp3=new Mp3("无","无","无","无","无","无");
              info_list.add(mp3);
              mp_name.add(music.substring(music.lastIndexOf("\\")+1));
          }

         } catch (IOException e) {
             e.printStackTrace();
         } catch (UnsupportedTagException e) {
             e.printStackTrace();
         } catch (InvalidDataException e) {
             e.printStackTrace();
         }

     lv_media.setItems(mp_name);

     }

     public void playOrpaused(){
         mediaView.setVisible(false);
         imageView.setVisible(false);
        if(mp_list.size()==0){
            return;
        }
         mediaPlayer=mp_list.get(cur_position);


         mediaPlayer.currentTimeProperty().addListener(new MediaListener(lyc_list.get(cur_position),mediaPlayer,controller));

         MediaPlayer.Status status= mediaPlayer.getStatus();

        if(status== MediaPlayer.Status.UNKNOWN||status== MediaPlayer.Status.HALTED){
            System.out.println("ahaahah");
            return;
        }
         if (mp_name.get(cur_position).contains(".mp4")||mp_name.get(cur_position).contains(".flv")){
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setVisible(true);

         }else {
             imageView.setVisible(true);
         }


        if(status==MediaPlayer.Status.PAUSED||status==MediaPlayer.Status.READY||status==MediaPlayer.Status.STOPPED){
                  mediaPlayer.play();
            System.out.println(info_list.get(cur_position).Artist);
        }else{
                  mediaPlayer.pause();
        }

    }

     public void playOrpaused(double value){
         imageView.setVisible(false);
         mediaView.setVisible(false);
         System.out.println("value"+value);
         if(mp_list.size()==0){
             return;
         }
         mediaPlayer=mp_list.get(cur_position);

         Duration duration=mediaPlayer.getMedia().getDuration();

         System.out.println("stoptime"+mediaPlayer.getStopTime().toMillis());
         System.out.println("start"+mediaPlayer.getStartTime().toMillis());
         System.out.println("currenttime"+mediaPlayer.getCurrentTime().toMillis());

         mediaPlayer.seek(duration.multiply(value/10000));

         mediaPlayer.currentTimeProperty().addListener(new MediaListener(lyc_list.get(cur_position),mediaPlayer,controller));


         MediaPlayer.Status status= mediaPlayer.getStatus();
         if (mp_name.get(cur_position).contains(".mp4")||mp_name.get(cur_position).contains(".flv")){
             mediaView.setVisible(true);
             mediaView.setMediaPlayer(mediaPlayer);
         }else {
             imageView.setVisible(true);
         }


         if(status== MediaPlayer.Status.UNKNOWN||status== MediaPlayer.Status.HALTED){

             return;
         }

         mediaPlayer.play();



     }




    public byte[] getImage(){

         return info_list.get(cur_position).getImage();


    }


    public void playnext(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.seek(new Duration(0));

        }



        nextSong();
        System.out.println(cur_position);
        playOrpaused();

    }


    public void playlast(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.seek(new Duration(0));
        }


        lastSong();
        System.out.println(cur_position);
        playOrpaused();
    }





    private boolean isEmpty(){
        return mp_list.isEmpty();
    }

    private void nextSong(){
        if(cur_position+1==mp_list.size()){
            cur_position=0;
        }else{
            cur_position=cur_position+1;
        }
    }

    private void lastSong(){
        if(cur_position==0){
            cur_position=mp_list.size()-1;
        }else{
            cur_position--;
        }
    }

    private boolean setSong(int position){
        if(position>=0&&position<mp_list.size()){
            cur_position=position;
            return  true;
        }else{
            return  false;
        }

    }


public Mp3 returnInfo(){


        return  info_list.get(cur_position);

}




}
