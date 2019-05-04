package tw.edu.pu.cs.wrist_band;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class healthreport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("健康報告");
        setContentView(R.layout.activity_healthreport);

        PieChart pie = findViewById(R.id.piechart);
        BarChart bar = findViewById(R.id.barchart);

        ArrayList<PieEntry> calorie = new ArrayList<>();
        calorie.add(new PieEntry(2500f, 0));
        PieDataSet dataSet = new PieDataSet(calorie, "今日攝取總熱量");
        PieData piedata = new PieData(dataSet);
        pie.setData(piedata);
        pie.animateXY(1000, 1000);

        ArrayList<BarEntry> step = new ArrayList();
        step.add(new BarEntry(1f, 5000f));
        step.add(new BarEntry(2f, 3200f));
        step.add(new BarEntry(3f, 1500f));
        step.add(new BarEntry(4f, 2900f));
        step.add(new BarEntry(5f, 250f));
        step.add(new BarEntry(6f, 4230f));
        step.add(new BarEntry(7f, 2900f));
        BarDataSet set = new BarDataSet(step, "過去七天的步數");
        BarData bardata = new BarData(set);
        bardata.setBarWidth(0.9f); // set custom bar width

        bar.setData(bardata);
        bar.setFitBars(true); // make the x-axis fit exactly all bars
        bar.invalidate(); // refresh
        bar.setData(bardata);
        bar.animateXY(1000, 1000);
        XAxis xAxis = bar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
    }
}
