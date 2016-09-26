package newsday.zxsc.com.newsday.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lenovo on 2016/9/13.
 */
public class GetURL{
    //todo 维护URL的优化类
    public static final String BAIDUGETURL="http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    public static final String APIKEY="7953ce4c523638a25a8f7fa917c0179c";
    public static final String QIWEN_HOT="http://apis.baidu.com/txapi/qiwen/qiwen";
    //todo 拼接URL的方法
    public static String findNewsName(String newName,int page){
        return BAIDUGETURL+"?channelName="+toUTF_8(newName+"焦点")+"&page="+page;
//        return BAIDUGETURL+"?channelName="+toUTF_8(newName+"最新")+"&page="+page;
    }
    public static String findNewsHot(String newName,int page){
        return QIWEN_HOT+"?channelName="+toUTF_8(newName+"奇闻")+"&page="+page;
    }
    private static String toUTF_8(String newName){
        try {
            newName=URLEncoder.encode(newName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return newName;
    }
}
