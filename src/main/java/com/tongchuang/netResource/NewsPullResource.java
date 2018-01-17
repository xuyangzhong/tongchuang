package com.tongchuang.netResource;

import com.google.gson.*;
import com.google.gson.JsonParser;
import com.tongchuang.utils.MD5;
import com.tongchuang.model.NewsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Description: 拉取新闻的资源类
 * @Author: Yeliang
 * @Date: Create in 5:36 2018/1/10
 */
public class NewsPullResource {
    private static final Logger log = LoggerFactory.getLogger(NewsPullResource.class);

    private static volatile NewsPullResource newsPullResource = new NewsPullResource();

    //上一次API调用的第一个ID。本次返回仅会给出比此ID更新的新闻。若不设置，从第一条数据开始获取。
    private String first_id = "127257193096414213";

    //上一次API调用的最后一个ID。本次返回仅会给出此ID后面的新闻。若不设置，从第一条数据开始获取。
    //当同时指定first_id及last_id时会抛出异常
    private String last_id;

    //请求的时间戳。GMT当前时间戳，13位数字，精确到毫秒。与服务器时间偏差5分钟内有效。
    private long timestamp;

    //颁发给用户的身份识别表示
    private static String ACCESS_KEY = "qSbIBZiX1Z75HuNK";

    private static String URLSTR = "https://api.xinwen.cn/news/all?" + "access_key=" + ACCESS_KEY;

    private static String SECRET_KEY = "17d3e48583ab475194586bb9fa269b3a";

    /**
     * 签名结果串
     * "SecretKeyValue"+timestamp+"AccessKeyValue"
     * 32位MD5值
     *
     * @see <a href="https://fenfa.shuwen.com/docs/api_signature?spm=fenfa.0.0.1.k3NzX7">签名的计算方法</>
     */
    private String signature;

    private BufferedReader in = null;

    private NewsPullResource() {

    }

    public static NewsPullResource getInstance() {
        return newsPullResource;
    }

    public ArrayList<NewsModel> pullNews() {
        String realUrl = initUrl();
        String result = sendGet(realUrl);
        return parseResult(result);
    }

    private ArrayList<NewsModel> parseResult(String result) {
        log.info("pull news result :" + result);
        try {
            JsonParser parser = new JsonParser();
            JsonObject resultJson = (JsonObject) parser.parse(result);
            boolean success = resultJson.get("success").getAsBoolean();
            if (success == false) {
                log.error(String.format("Pull news fail : errorcode = %s and errormsg = %s",
                        resultJson.get("code").getAsString(), resultJson.get("msg").getAsString()));
                return null;
            }
            JsonObject dataJson = resultJson.get("data").getAsJsonObject();
            first_id = dataJson.get("first_id").getAsString();
            last_id = dataJson.get("last_id").getAsString();
            int count = dataJson.get("count").getAsInt();
            if (count == 0) {
                log.info("there is no news to update");
                return null;
            }
            JsonArray newsArray = dataJson.get("news").getAsJsonArray();
            ArrayList<NewsModel> newslist = new ArrayList<NewsModel>();
            NewsModel newsModel;
            for (int i = 0; i < newsArray.size(); i++) {
                newsModel = new NewsModel();
                JsonObject news = newsArray.get(i).getAsJsonObject();
                log.info(String.format("one piece of news adds to DB : title = %s ,source = %s ,gmt_publish = %d,url = %s"
                        , news.get("title").getAsString(), news.get("source").getAsString(),
                        news.get("gmt_publish").getAsInt(), news.get("url").getAsString()));
                newsModel.setNews_id(news.get("news_id").getAsString());
                newsModel.setTitle(news.get("title").getAsString());
                newsModel.setSource(news.get("source").getAsString());
                newsModel.setGmt_publish(new Timestamp(news.get("gmt_publish").getAsLong()));
                newsModel.setUrl(news.get("url").getAsString());
                newslist.add(newsModel);
            }

            log.info(String.format("total %d pieces of news add to DB", newsArray.size()));
            return newslist;
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String sendGet(String realUrl) {
        String result = "";
        try {
            URL url = new URL(realUrl);
            // 打开和URL之间的连接
            URLConnection conn = url.openConnection();
            // 建立实际的连接
            conn.connect();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error(String.format("发送Get请求出错,Url = %s", realUrl), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private String initUrl() {
        timestamp = new Timestamp(new Date().getTime()).getTime();
        signature = getSignature(timestamp);
        String realUrl = URLSTR;
        if (first_id != null) {
            realUrl = realUrl + "&" + "first_id=" + first_id;
        }
//        if (last_id != null) {
//            realUrl = realUrl + "&" + "last_id=" + last_id;
//        }
        return realUrl + "&" + "timestamp=" + timestamp + "&" + "signature=" + signature;

    }

    private String getSignature(long timestamp) {
        String md5Str = null;
        try {
            md5Str = MD5.getMD5Str(SECRET_KEY + timestamp + ACCESS_KEY);
        } catch (Exception e) {
            log.error(String.format("MD5加密签名出错,timestamp = %s", timestamp), e);
        }
        return md5Str;
    }
}
