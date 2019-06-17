package tw.edu.pu.cs.wrist_band;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;

public class webapi {


    static void api_UploadUser(final String PersonalID,final String Name,final String Password,final String Gender,final String Age,final String Height,final String Weight){

        Runnable exec = new Runnable() {
            @Override
            public void run() {
                String url = "http://120.110.112.151:8080/UploadUser";
                DefaultHttpClient client = new DefaultHttpClient();
                try {
                    // 利用get method 取得laravel csrf token;
                    HttpGet get = new HttpGet(url);
                    HttpResponse response = client.execute(get);
                    HttpEntity resEntity = response.getEntity();
                    String result = EntityUtils.toString(resEntity);
                    int pos = result.indexOf("_token");
                    String substr = result.substring(pos);
                    String token = substr.substring(15,substr.length()-11);
                    Log.e("Label", "----------------");
                    Log.d("----substr",substr);
                    Log.d("----token",token);


                    //利用post mothod 傳入web api;
                    HttpPost post = new HttpPost(url);
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("_token",token));
                    params.add(new BasicNameValuePair("PersonalID",PersonalID ));
                    params.add(new BasicNameValuePair("Name", Name));
                    params.add(new BasicNameValuePair("Password", Password));
                    params.add(new BasicNameValuePair("Gender", Gender));
                    params.add(new BasicNameValuePair("Age", Age));
                    params.add(new BasicNameValuePair("Height", Height));
                    params.add(new BasicNameValuePair("Weight", Weight));
                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    response = client.execute(post);
                    int status = response.getStatusLine().getStatusCode();
                    Log.d("api status", String.valueOf(status));

                }
                catch (Exception e) {
                    Log.e("Label", "----------------");
                    e.printStackTrace();
                    //Log.d("error1:", e.getMessage());
                }

            }
        };

        (new Thread(exec)).start();

    }
    static void api_UploadSleepSummary(final String SerialID, final String PersonalID, final String BandID, final String SleepStartTime, final String SleepStopTime, final String DeepSleepStartTime, final String LightSleepStartTime, final String OtherSleepStartTime){

        Runnable exec = new Runnable() {
            @Override
            public void run() {
                String url = "http://120.110.112.151:8080/UploadSleepSummary";
                DefaultHttpClient client2 = new DefaultHttpClient();
                try {
                    // 利用get method 取得laravel csrf token;
                    HttpGet get = new HttpGet(url);
                    HttpResponse response = client2.execute(get);
                    HttpEntity resEntity = response.getEntity();
                    String result = EntityUtils.toString(resEntity);
                    int pos = result.indexOf("_token");
                    String substr = result.substring(pos);
                    String token = substr.substring(15,substr.length()-11);
                    Log.e("Label", "----------------");
                    Log.d("----substr",substr);
                    Log.d("----token",token);


                    //利用post mothod 傳入web api;
                    HttpPost post = new HttpPost(url);
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("_token",token));
                    params.add(new BasicNameValuePair("SerialID", SerialID));
                    params.add(new BasicNameValuePair("PersonalID", PersonalID));
                    params.add(new BasicNameValuePair("BandID", BandID));
                    params.add(new BasicNameValuePair("SleepStartTime", SleepStartTime));
                    params.add(new BasicNameValuePair("SleepStopTime", SleepStopTime));
                    params.add(new BasicNameValuePair("DeepSleepStartTime", DeepSleepStartTime));
                    params.add(new BasicNameValuePair("LightSleepStartTime", LightSleepStartTime));
                    params.add(new BasicNameValuePair("OtherSleepStartTime", OtherSleepStartTime));
                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    response = client2.execute(post);
                    int status = response.getStatusLine().getStatusCode();
                    Log.d("api status", String.valueOf(status));

                }
                catch (Exception e) {
                    Log.e("Label", "----------------");
                    e.printStackTrace();
                    //Log.d("error1:", e.getMessage());
                }

            }
        };

        (new Thread(exec)).start();

    }
    static void api_UploadMeasureLog(final String SerialID, final String PersonalID, final String BandID, final String Steps, final String Distance, final String ExerciseCalories, final String RestCalories, final String Heartrate, final String StartTime, final String StopTime){

        Runnable exec = new Runnable() {
            @Override
            public void run() {
                String url = "http://120.110.112.151:8080/UploadMeasureLog";
                DefaultHttpClient client3 = new DefaultHttpClient();
                try {
                    // 利用get method 取得laravel csrf token;
                    HttpGet get = new HttpGet(url);
                    HttpResponse response = client3.execute(get);
                    HttpEntity resEntity = response.getEntity();
                    String result = EntityUtils.toString(resEntity);
                    int pos = result.indexOf("_token");
                    String substr = result.substring(pos);
                    String token = substr.substring(15,substr.length()-11);
                    Log.e("Label", "----------------");
                    Log.d("----substr",substr);
                    Log.d("----token",token);


                    //利用post mothod 傳入web api;
                    HttpPost post = new HttpPost(url);
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("_token",token));
                    params.add(new BasicNameValuePair("SerialID", SerialID));
                    params.add(new BasicNameValuePair("PersonalID", PersonalID));
                    params.add(new BasicNameValuePair("BandID", BandID));
                    params.add(new BasicNameValuePair("Steps", Steps));
                    params.add(new BasicNameValuePair("Distance", Distance));
                    params.add(new BasicNameValuePair("ExerciseCalories", ExerciseCalories));
                    params.add(new BasicNameValuePair("RestCalories", RestCalories));
                    params.add(new BasicNameValuePair("Heartrate", Heartrate));
                    params.add(new BasicNameValuePair("StartTime", StartTime));
                    params.add(new BasicNameValuePair("StopTime", StopTime));
                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    response = client3.execute(post);
                    int status = response.getStatusLine().getStatusCode();
                    Log.d("api status", String.valueOf(status));

                }
                catch (Exception e) {
                    Log.e("Label", "----------------");
                    e.printStackTrace();
                    //Log.d("error1:", e.getMessage());
                }

            }
        };

        (new Thread(exec)).start();

    }

}
