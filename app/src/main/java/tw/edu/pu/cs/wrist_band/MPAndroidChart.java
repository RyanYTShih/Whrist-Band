package tw.edu.pu.cs.wrist_band;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MPAndroidChart extends AppCompatActivity {

    PieChart pieChart;
    BarChart barChart;
    int[] colorarray=new int[]{Color.GREEN,Color.BLUE,Color.DKGRAY,Color.RED,Color.LTGRAY};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chart);

        String heartrate = webapi.heartrate;
       // Toast.makeText(this,heartrate,Toast.LENGTH_LONG).show();

        pieChart=findViewById(R.id.ppie);
        pieChart.setDrawHoleEnabled(false);
        PieDataSet pieDataSet=new PieDataSet(dataValuese(),"");
        pieDataSet.setColors(colorarray);
        PieData pieData=new PieData(pieDataSet);
        pieData.setValueTextSize(80f);
        pieChart.setData(pieData);
        pieChart.invalidate();


        String step = webapi.step;
      //  Toast.makeText(this,step,Toast.LENGTH_LONG).show();

        barChart = findViewById(R.id.chart1);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,1100f));
        barEntries.add(new BarEntry(2,1200f));
        barEntries.add(new BarEntry(3,1100f));
        barEntries.add(new BarEntry(4,1150f));
        barEntries.add(new BarEntry(5,1175f));
        barEntries.add(new BarEntry(6,1135f));
        barEntries.add(new BarEntry(7,1125f));

        BarDataSet barDataSet = new BarDataSet(barEntries,"Date Set1");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        barChart.setData(data);
    }
    private ArrayList<PieEntry> dataValuese(){
        ArrayList<PieEntry> dataVals = new ArrayList<>();
        webapi.api_GetHeartrate("A123456789","PS-100 425");
        dataVals.add(new PieEntry(85,"Sun"));

        return dataVals;
    }


}
