package Util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.io.StringReader;

/**
 * Created by zhangjiazhao on 2017/4/27.
 */
public class GsonUtil {

public static <T> T paresrJsontoObject(String json,Class<T> type){
    Gson gson=new Gson();
    JsonReader jsonReader=new JsonReader(new StringReader(json));
    jsonReader.setLenient(true);
    T result=gson.fromJson(jsonReader,type);
    return  result;

}


}
