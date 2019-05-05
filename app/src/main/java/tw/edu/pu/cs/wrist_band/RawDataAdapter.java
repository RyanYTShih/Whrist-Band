package tw.edu.pu.cs.wrist_band;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.epson.pulsenseapi.model.MeasureLogModel;
import com.epson.pulsenseapi.model.SimpleDate;
import com.epson.pulsenseapi.model.SimpleTime;

import java.util.Locale;

public class RawDataAdapter extends ArrayAdapter<Rawdata> {
    public RawDataAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Rawdata rawdata) {
        super.add(rawdata);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rawdata_adapter, parent, false);
        }

        Rawdata rawdata = getItem(position);
        MeasureLogModel measureLogModel = rawdata.getMeasureLogModel();

        TextView textName = convertView.findViewById(R.id.name);
        TextView textUUID = convertView.findViewById(R.id.bandUUID);
        TextView textHeartrate = convertView.findViewById(R.id.heartrate);
        TextView textSteps = convertView.findViewById(R.id.steps);
        TextView textStartTime = convertView.findViewById(R.id.startTime);
        TextView textStopTime = convertView.findViewById(R.id.stopTime);

        String s = "";

        int heartrate[] = measureLogModel.getHeartrate();
        int sum = 0, count = 0;

        for (int h : heartrate) {
            s += h + ", ";
            if (h > 0) {
                sum += h;
                count++;
            }
        }

        int avg = count > 0 ? sum / count : 0;

        SimpleDate startDate = measureLogModel.getMeasureStartDate();
        SimpleTime startTime = measureLogModel.getMeasureStartTime();
        SimpleDate stopDate = measureLogModel.getMeasureStopDate();
        SimpleTime stopTime = measureLogModel.getMeasureStopTime();

        textName.setText(rawdata.getPersonal_id());
        textUUID.setText(rawdata.getBand_id());
        textHeartrate.setText(avg + "");
        textSteps.setText(measureLogModel.getSteps() + "");
        textStartTime.setText(String.format(Locale.TAIWAN, "%04d/%02d/%02d %02d:%02d:%02d",
                startDate.getYear(),
                startDate.getMonth(),
                startDate.getDay(),
                startTime.getHour(),
                startTime.getMinute(),
                startTime.getSecond()));
        textStopTime.setText(String.format(Locale.TAIWAN, "%04d/%02d/%02d %02d:%02d:%02d",
                stopDate.getYear(),
                stopDate.getMonth(),
                stopDate.getDay(),
                stopTime.getHour(),
                stopTime.getMinute(),
                stopTime.getSecond()));

        return convertView;
    }
}
