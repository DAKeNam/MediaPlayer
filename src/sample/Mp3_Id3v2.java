package sample;

import com.mpatric.mp3agic.ID3v2;

/**
 * Created by zhangjiazhao on 2017/3/27.
 */
public class Mp3_Id3v2 extends Mp3 {
      String Lyrics;
      String Composer;
      String Publisher;
      String Original_artist;
      String Album_artist;
      String Copyright;
      String URL;
      String Encoder;
     byte[] Image;

    public Mp3_Id3v2(ID3v2 id3v2Tag) {
        super(id3v2Tag.getArtist(),id3v2Tag.getTitle(),id3v2Tag.getAlbum(),id3v2Tag.getYear(),""+id3v2Tag.getGenre(),id3v2Tag.getComment());
        Lyrics=id3v2Tag.getLyrics();
        Composer=id3v2Tag.getComposer();
        Publisher=id3v2Tag.getPublisher();
        Original_artist=id3v2Tag.getOriginalArtist();
        Album_artist=id3v2Tag.getAlbumArtist();
        Copyright=id3v2Tag.getCopyright();
        URL=id3v2Tag.getUrl();
        Encoder=id3v2Tag.getEncoder();
        Image=id3v2Tag.getAlbumImage();
        System.out.println("id3v2");
    }

    public String getLyrics(){
        return Lyrics;
    }


    public byte[] getImage(){
        return Image;

    }






}
