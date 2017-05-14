package sample;

/**
 * Created by zhangjiazhao on 2017/3/27.
 */

public   class Mp3 {
   protected   String Artist;
   protected   String Title;
   protected   String Album;
   protected   String Year;
   protected   String Genre;
   protected   String Comment;

   public Mp3(String Artist,String Title,String Album,String Year,String Genre,String Comment){
       this.Album=Album;
       this.Artist=Artist;
       this.Title=Title;
       this.Comment=Comment;
       this.Genre=Genre;
       this.Year=Year;

   }

   public String getLyrics(){
       return "";
   }

    public byte[] getImage(){
        return null;
    }











}
