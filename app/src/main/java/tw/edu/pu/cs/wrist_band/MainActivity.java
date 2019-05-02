package tw.edu.pu.cs.wrist_band;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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

    private UserViewModel mUserViewModel;

    private LiveData<List<User>> users;
    private static final User[] sampleUsers = {
            new User("A123456789", "張小心", "1", Role.Manager),
            new User("B123456789", "時小唐", "2", Role.SocialWorker),
            new User("C123456789", "廖小勛", "3", Role.Elder),
            new User("D123456789", "林小宏", "4", Role.Doctor)
    };

    private EditText edUserid, edPasswd;
    public static ConstraintLayout bg;
    public static ListView lv;
    public static ImageButton imgbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //隱藏標題
        edUserid = findViewById(R.id.ed_userid);
        edPasswd = findViewById(R.id.ed_passwd);
        bg=findViewById(R.id.back);
        lv=findViewById(R.id.pm25);
        imgbt=findViewById(R.id.imageButton);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        for (User user: sampleUsers) {
            mUserViewModel.insert(user);
        }
        String url2 ="http://223.200.80.137/webapi/api/rest/datastore/355000000I-000259/?format=json&limit=26&sort=SiteName&token=+T2Appnb4kmEXBhOwSLuLw";

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
    }
    private String getData(String urlString) {
        String result = "";
        //使用JsonObjectRequest類別要求JSON資料。
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(urlString, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            //Velloy採非同步作業，Response.Listener  監聽回應
                            public void onResponse(JSONObject response) {
                                Log.d("回傳結果", "結果=" + response.toString());
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
                        Log.e("回傳結果", "錯誤訊息：" + error.toString());
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
        return result;
    }
    @SuppressLint("ShowToast")
    private void parseJSON(JSONObject jsonObject) throws JSONException{
        ArrayList<String> list = new ArrayList();
        JSONArray data = jsonObject.getJSONObject("result").getJSONArray("records");
        for  (int i = 0 ; i < data.length(); i++){
            JSONObject o = data.getJSONObject(i);
            if(o.getString("SiteName").equals("沙鹿")) {
                int aqi=o.getInt("AQI");
                if(aqi<50) {
                    bg.setBackgroundColor(Color.GREEN);
                }else if(aqi<101){
                    bg.setBackgroundColor(Color.YELLOW);
                }else if(aqi<151){
                    bg.setBackgroundColor(Color.parseColor("#FFBB66"));
                }else if(aqi<201){
                    bg.setBackgroundColor(Color.parseColor(	"#FF0000"));
                }else if(aqi<301){
                    bg.setBackgroundColor(Color.parseColor(	"#FF0000"));
                }else{
                    bg.setBackgroundColor(Color.parseColor(	"#FF0000"));
                }
                String str =  o.getString("SiteName")+"   AQI:"
                        + o.getString("AQI") + "\n";
                list.add(str);

            }
        }
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
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

        for (User user: userList) {
            Log.d(TAG, user.toString());
            if (user.getId().equals(uid)) {
                selectedUser = user;
                break;
            }
        }

        if (selectedUser != null) {
            if (pw.equals(selectedUser.getPasswd())) {
                switch (selectedUser.getRole()) {
                    case Role.Manager:
                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MANAGEMENT.class));
                        break;
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

