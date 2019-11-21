package tw.edu.pu.cs.wrist_band;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class TextToSpeech {

    private static final String TAG = "TextToSpeech";
    private static final String subscription_key = "8094781bb1ed4b0a8e3ac7c4baee2ba3";
    private static final String fetch_token_url = "https://southeastasia.api.cognitive.microsoft.com/sts/v1.0/issueToken";
    private static final String tts_url = "https://southeastasia.tts.speech.microsoft.com/cognitiveservices/v1";
    private static final String voice_name = "Microsoft Server Speech Text to Speech Voice (zh-TW, Yating, Apollo)";
//    private static final String voice_name = "Microsoft Server Speech Text to Speech Voice (zh-CN, XiaoxiaoNeural)";

    private static RequestQueue mQueue;
    private Context mContext;
    private String tts;
    private String access_token;

    TextToSpeech(Context context, String tts) {
        mContext = context;
        this.tts = tts.replace("\n", "");
        access_token = "";
        mQueue = Volley.newRequestQueue(mContext);
        get_token();
    }

    private void get_token() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, fetch_token_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                access_token = response;
                get_audio();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("Ocp-Apim-Subscription-Key", subscription_key);
                map.put("Content-type", "application/x-www-form-urlencoded");
                map.put("Content-Length", "0");
                return map;
            }
        };

        Log.d(TAG, stringRequest.toString());
        mQueue.add(stringRequest);
    }

    private void get_audio() {

        String xml_body = XmlDom.createDom(voice_name, tts.substring(0, tts.length() > 1000 ? 1000 : tts.length()));

        byte[] xml = null;

        try {
            xml = xml_body.getBytes("utf-8");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        final byte[] body = xml;

        BinaryRequest audioRequest = new BinaryRequest(Request.Method.POST, tts_url, new Response.Listener<byte[]>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(byte[] response) {
                try {
                    Log.d(TAG, "isExternalStorageWritable: " + isExternalStorageWritable());
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "tts.wav");

                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(response);
                    fos.close();

                    FileInputStream fis = new FileInputStream(file);

                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(fis.getFD());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + access_token);
                map.put("Content-Type", "application/ssml+xml");
                map.put("X-Microsoft-OutputFormat", "riff-24khz-16bit-mono-pcm");
                map.put("User-Agent", "YOUR_RESOURCE_NAME");
                return map;
            }

            @Override
            public byte[] getBody() {
                return body;
            }
        };

        try {
            Log.d(TAG, audioRequest.getHeaders().toString() + new String(audioRequest.getBody(), "utf-8"));
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        mQueue.add(audioRequest);

    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }


}
