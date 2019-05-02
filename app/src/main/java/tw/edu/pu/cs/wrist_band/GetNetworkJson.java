package tw.edu.pu.cs.wrist_band;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetNetworkJson extends AsyncTask<String,Void,String> {
    String data = "";
    InputStream inputStream = null;
    @Override
    protected String doInBackground(String... urlStrings) {
        try {
            URL url = new URL(urlStrings[0]); //初始化
            HttpURLConnection httpURLConnection =
                    (HttpURLConnection) url.openConnection(); //取得連線之物件

            InputStream inputStream = httpURLConnection.getInputStream();
            //對取得的資料進行讀取
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    protected void onPostExecute(String data) {//執行完的結果會傳入這裡
        super.onPostExecute(data);
      //  MainActivity.tv.setText(data);
    }
}
