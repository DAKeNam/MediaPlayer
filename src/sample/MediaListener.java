package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjiazhao on 2017/3/27.
 */
public class MediaListener implements ChangeListener<Duration> {

    double[][] lyc_map;
    List<String> lyc_content;
//    double begin;
    MediaPlayer mediaPlayer;
    Slider slider;
    double total;
    Label lyric;
    public MediaListener(String  lyc,MediaPlayer mediaPlayer,Controller controller){
         this.mediaPlayer=mediaPlayer;
         this.lyric=controller.getLyric();
        this.slider=controller.getSlider_progress();
        this.total=mediaPlayer.getStopTime().toMillis();

        if(lyc.compareTo("无歌词")!=0&&lyc.compareTo("")!=0) {
            String[] lyc_arry = lyc.split("\n");
            lyc_map = new double[lyc_arry.length][2];
            lyc_content = new ArrayList<>();
            for (int i = 0; i < lyc_arry.length; i++) {
                String[] content = lyc_arry[i].split("]");
                lyc_map[i][0] = toDoubletime(content[0]);
                lyc_map[i][1]=i;
                lyc_content.add(content[1]);
            }
        }else{
            lyc_map=new double[1][2];
            lyc_map[0][0]=0;
            lyc_map[0][1]=0;
            lyc_content=new ArrayList<>();
            lyc_content.add(lyc);
        }



    }



//
//    @Override
//    public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
//
//        //begin+=duration*1000;
//        double begin=mediaPlayer.getCurrentTime().toMillis();
//        slider.setValue((begin/total)*10000);
//
//        timestamp=begin/1000;
//
//        System.out.println("current time"+begin);
//        for(int i=0;i<lyc_map.length;i++){
//            if( i !=lyc_map.length-1&&(lyc_map[i+1][0])>timestamp&&(lyc_map[i][0])<=timestamp){
//                //System.out.println(lyc_map[i][0]);
//            //   System.out.println(lyc_content.get((int)lyc_map[i][1]));
//            }else if(i==lyc_map.length-1&&lyc_map[i][0]<=timestamp){
//             //   System.out.println(lyc_content.get((int)lyc_map[lyc_map.length-1][1]));
//            }
//        }
//
//
//
//    }

     private double toDoubletime(String str){
               str=str.replace("[","");
               String[] str_s=str.split(":");
               double min=Double.valueOf(str_s[0]);
               double second=Double.valueOf(str_s[1]);
               return  min*60+second;
     }


    @Override
    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {


        slider.setValue((newValue.toMillis()/total)*10000);

       double timestamp=newValue.toMillis()/1000;

       // System.out.println("timestamp"+timestamp);
        for(int i=0;i<lyc_map.length;i++){
            if( i !=lyc_map.length-1&&(lyc_map[i+1][0])>timestamp&&(lyc_map[i][0])<=timestamp){
           //        System.out.println(lyc_map[i][0]);
               //    System.out.println(lyc_content.get((int)lyc_map[i][1]));
                   lyric.setText(lyc_content.get((int)lyc_map[i][1]));
            }else if(i==lyc_map.length-1&&lyc_map[i][0]<=timestamp){
              //    System.out.println(lyc_content.get((int)lyc_map[lyc_map.length-1][1]));
                  lyric.setText(lyc_content.get((int)lyc_map[lyc_map.length-1][1]));
            }
        }

    }
}
