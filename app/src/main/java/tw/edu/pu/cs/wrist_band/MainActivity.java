package tw.edu.pu.cs.wrist_band;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final User[] sampleUsers = {
            new User("A123456789", "張小心", "1", Role.Elder, "F", 20, 180, 70),
            new User("B123456789", "時小唐", "2", Role.SocialWorker, "M", 20, 180, 70),
            new User("C123456789", "廖小勛", "3", Role.Elder, "M", 20, 180, 70),
            new User("D123456789", "林小宏", "4", Role.Doctor, "M", 20, 180, 70)
    };
    private UserViewModel mUserViewModel;
    private LiveData<List<User>> users;
    private SharedPreferences aqiValue;
    private EditText edUserid, edPasswd;
    private ConstraintLayout bg;
    private ListView lv;
    private ImageButton imgbt;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //隱藏標題

        edUserid = findViewById(R.id.ed_userid);
        edPasswd = findViewById(R.id.ed_passwd);
        bg = findViewById(R.id.back);
        lv = findViewById(R.id.pm25);
        imgbt = findViewById(R.id.imageButton);
        cardView = findViewById(R.id.cardview1);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        bg.getBackground().setAlpha(130);
        aqiValue = getSharedPreferences("AQI_Value", Context.MODE_PRIVATE);

        for (User user : sampleUsers) {
            mUserViewModel.insert(user);
            webapi.api_UploadUser(
                    user.getPersonalID(),
                    user.getName(),
                    user.getPassword(),
                    user.getGender(),
                    user.getAge() + "",
                    user.getHeight() + "",
                    user.getWeight() + ""
            );
        }
        String url2 = "http://223.200.80.137/webapi/api/rest/datastore/355000000I-000259/?format=json&limit=26&sort=SiteName&token=n8PcukbV2kGguEmuCxNZ/Q";

        GetNetworkJson process = new GetNetworkJson();
        getData(url2);

        users = mUserViewModel.getAllUsers();

        users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.d(TAG, "user list updated");
                if (users == null)
                    return;
                try {
                    MainActivity.this.users = mUserViewModel.getAllUsers();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        });

        // Test for webapi
        // webapi.api_UploadUser("D1234","Kevin","1234","male","56","160","70");
        // webapi.api_UploadSleepSummary("1","D1234","QAZ123","2019-06-13 02:01:09","2019-06-13 02:01:09","2019-06-13 02:01:09","2019-06-13 02:01:09","2019-06-13 02:01:09");
        // webapi.api_UploadMeasureLog("2","D1234","QAZ123","1500","10","150","200","80","2019-06-13 02:01:09","2019-06-13 02:01:09");
        webapi.api_GetDaySteps("A123456789", "PS-100 425");
        webapi.api_GetSleepInterval("A123456789", "PS-100 425");
        webapi.api_GetDeepLightTime("A123456789", "PS-100 425");
        webapi.api_GetExerciseCalories("A123456789", "PS-100 425");
        webapi.api_GetRestCalories("A123456789", "PS-100 425");
        webapi.api_GetTotalCalories("A123456789", "PS-100 425");
        webapi.api_GetHeartrate("A123456789", "PS-100 425");


    }

    private String getData(String urlString) {
        String result = "";

        final String siteName = aqiValue.getString("SiteName", "Null");
        final int aqi = aqiValue.getInt("AQI", -1);
        Log.d(TAG, "Get SharedPreference: SiteName = " + siteName + ", AQI = " + aqi);
        setBackgroundByAQI(siteName, aqi);

        //使用JsonObjectRequest類別要求JSON資料。
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(urlString, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            //Velloy採非同步作業，Response.Listener  監聽回應
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "回傳結果=" + response.toString());
                                try {
                                    parseJSON(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Response.ErrorListener 監聽錯誤
                        Log.e(TAG, "回傳結果 錯誤訊息：" + error.toString());
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
        return result;
    }

    @SuppressLint("ShowToast")
    private void parseJSON(JSONObject jsonObject) throws JSONException {
        JSONArray data = jsonObject.getJSONObject("result").getJSONArray("records");
        for (int i = 0; i < data.length(); i++) {
            JSONObject o = data.getJSONObject(i);
            if (o.getString("SiteName").equals("沙鹿")) {
                int aqi = o.getInt("AQI");
                setBackgroundByAQI(o.getString("SiteName"), aqi);
            }
        }
    }

    private void setBackgroundByAQI(String siteName, int aqi) {

        SharedPreferences.Editor editor = aqiValue.edit();
        editor.putString("SiteName", siteName);
        editor.putInt("AQI", aqi);
        editor.apply();

        if (aqi < 50) {
            cardView.setCardBackgroundColor(Color.GREEN);
            cardView.getBackground().setAlpha(150);
        } else if (aqi < 101) {
            cardView.setCardBackgroundColor(Color.YELLOW);
            cardView.getBackground().setAlpha(150);
        } else if (aqi < 151) {
            cardView.setCardBackgroundColor(Color.parseColor("#FFBB66"));
        } else if (aqi < 201) {
            cardView.setCardBackgroundColor(Color.parseColor("#FF0000"));
        } else if (aqi < 301) {
            cardView.setCardBackgroundColor(Color.parseColor("#FF0000"));
        } else {
            cardView.setCardBackgroundColor(Color.parseColor("#FF0000"));
        }
        String str = siteName + "   AQI: "
                + aqi;
        ArrayList<String> list = new ArrayList<>();
        list.add(str);
        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
    }

    public void login(View v) {

        String uid = edUserid.getText().toString();
        String pw = edPasswd.getText().toString();

        User selectedUser = null;

        List<User> userList;

        try {
            userList = users.getValue();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            new AlertDialog.Builder(this)
                    .setTitle("返老「環」童")
                    .setMessage("資料庫失敗")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        for (User user : userList) {
            Log.d(TAG, user.toString());
            if (user.getPersonalID().equals(uid)) {
                selectedUser = user;
                break;
            }
        }

        if (selectedUser != null) {
            if (pw.equals(selectedUser.getPassword())) {
                switch (selectedUser.getRole()) {
//                    case Role.Manager:
//                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(this, MANAGEMENT.class));
//                        break;
                    case Role.SocialWorker:
                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, socialfun.class));
                        break;
                    case Role.Elder:
                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, ELDER.class));
                        break;
                    case Role.Doctor:
                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, doctorfun.class));
                        break;
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("登入失敗")
                        .setMessage("密碼錯誤")
                        .setPositiveButton(R.string.dialog_positive_button, null)
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("登入失敗")
                    .setMessage("查無此帳號")
                    .setPositiveButton(R.string.dialog_positive_button, null)
                    .show();
        }
    }

    public void touch_surprise(View v) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("AQI簡介")
                .setIcon(getDrawable(R.drawable.icon_use))
                .setMessage("空氣品質指標為依據監測資料將當日空氣中臭氧(O3)、細懸浮微粒(PM2.5)、懸浮微粒(PM10)、一氧化碳(CO)、二氧化硫(SO2)及二氧化氮(NO2)濃度等數值，以其對人體健康的影響程度，分別換算出不同污染物之副指標值，再以當日各副指標之最大值為該測站當日之空氣品質指標值(AQI)。")
                .setPositiveButton("關閉", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}

