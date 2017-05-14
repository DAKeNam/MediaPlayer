package sample;

import com.mpatric.mp3agic.ID3v1;

/**
 * Created by zhangjiazhao on 2017/3/27.
 */
public class Mp3_Id3v1 extends Mp3 {

    public Mp3_Id3v1(ID3v1 id3v1Tag){
        super(id3v1Tag.getArtist(),id3v1Tag.getTitle(),id3v1Tag.getAlbum(),id3v1Tag.getYear(),""+id3v1Tag.getGenre(),id3v1Tag.getComment());
        System.out.println("id3v1");
    }

}
