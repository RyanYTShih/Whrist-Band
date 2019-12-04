package tw.edu.pu.cs.wrist_band;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MPAndroidChart extends AppCompatActivity {

    //PieChart pieChart;
    BarChart barChart;
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("q", "11/4", "11/5", "11/6", "11/7", "11/8", "11/9", "11/10"));
    int[] colorarray = new int[]{Color.GREEN, Color.BLUE, Color.DKGRAY, Color.RED, Color.LTGRAY};
    View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chart);
        getSupportActionBar().hide();

        background = findViewById(R.id.back);
        background.getBackground().setAlpha(150);
        String heartrate = webapi.heartrate;
        // Toast.makeText(this,heartrate,Toast.LENGTH_LONG).show();

/*
        pieChart=findViewById(R.id.ppie);
        pieChart.setDrawHoleEnabled(false);
        PieDataSet pieDataSet=new PieDataSet(dataValuese(),"");
        pieDataSet.setColors(colorarray);
        PieData pieData=new PieData(pieDataSet);
        pieData.setValueTextSize(80f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setData(pieData);
        pieChart.invalidate();*/


        String step = webapi.step;
        //  Toast.makeText(this,step,Toast.LENGTH_LONG).show();
        List<BarEntry> incomeEntries = getStep();
        barChart = findViewById(R.id.chart1);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setDrawGridBackground(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        BarDataSet barDataSet = new BarDataSet(incomeEntries, "步數");
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        barDataSet.setColors(Color.rgb(0, 191, 255));


        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
        barChart.getAxisLeft().removeAllLimitLines();
        barChart.getLegend().setTextSize(14);
        barChart.getAxisLeft().setAxisMinimum(1000);
        barChart.getAxisRight().setAxisMinimum(0);

        barChart.setData(data);
        barChart.invalidate();


    }

    private List<BarEntry> getStep() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 1100f));
        barEntries.add(new BarEntry(2, 1200f));
        barEntries.add(new BarEntry(3, 1100f));
        barEntries.add(new BarEntry(4, 1150f));
        barEntries.add(new BarEntry(5, 1175f));
        barEntries.add(new BarEntry(6, 1135f));
        barEntries.add(new BarEntry(7, 1125f));
        return barEntries.subList(0, 7);
    }


    /*private ArrayList<PieEntry> dataValuese(){
        ArrayList<PieEntry> dataVals = new ArrayList<>();
        webapi.api_GetHeartrate("A123456789","PS-100 425");
        dataVals.add(new PieEntry(85,"HeartRate"));

        return dataVals;
    }*/


}
