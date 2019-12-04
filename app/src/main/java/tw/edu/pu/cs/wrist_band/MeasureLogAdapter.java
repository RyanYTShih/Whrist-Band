package tw.edu.pu.cs.wrist_band;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MeasureLogAdapter extends ArrayAdapter<MeasureLog> {

    public MeasureLogAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable MeasureLog measureLog) {
        super.add(measureLog);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.measure_log_adapter, parent, false);
        }

        MeasureLog measureLog = getItem(position);
//        MeasureLogModel measureLogModel = measureLog.getMeasureLogModel();

        TextView textPID = convertView.findViewById(R.id.p_id);
        TextView textBID = convertView.findViewById(R.id.b_id);
        TextView textHeartrate = convertView.findViewById(R.id.heartrate);
        TextView textSteps = convertView.findViewById(R.id.steps);
        TextView textDistance = convertView.findViewById(R.id.distance);
        TextView textExerciseCalories = convertView.findViewById(R.id.exercise);
        TextView textRestCalories = convertView.findViewById(R.id.rest);
        TextView textStartTime = convertView.findViewById(R.id.start_time);
        TextView textStopTime = convertView.findViewById(R.id.stop_time);

        String s = "";

//        int heartrate[] = measureLogModel.getHeartrate();
//        int sum = 0, count = 0;
//
//        for (int h : heartrate) {
//            s += h + ", ";
//            if (h > 0) {
//                sum += h;
//                count++;
//            }
//        }

//        int avg = count > 0 ? sum / count : 0;

//        SimpleDate startDate = measureLogModel.getMeasureStartDate();
//        SimpleTime startTime = measureLogModel.getMeasureStartTime();
//        SimpleDate stopDate = measureLogModel.getMeasureStopDate();
//        SimpleTime stopTime = measureLogModel.getMeasureStopTime();

        textPID.setText(measureLog.getPersonalID());
        textBID.setText(measureLog.getBandID());
        textHeartrate.setText(measureLog.getHeartrate());
        textSteps.setText(measureLog.getSteps() + "");
        textDistance.setText(measureLog.getDistance() + "");
        textExerciseCalories.setText(measureLog.getExerciseCalories() + "");
        textRestCalories.setText(measureLog.getRestCalories() + "");
        textStartTime.setText(measureLog.getStartTime() + "");
        textStopTime.setText(measureLog.getStopTime() + "");
//        textStartTime.setText(String.format(Locale.TAIWAN, "%04d/%02d/%02d %02d:%02d:%02d",
//                startDate.getYear(),
//                startDate.getMonth(),
//                startDate.getDay(),
//                startTime.getHour(),
//                startTime.getMinute(),
//                startTime.getSecond()));
//        textStopTime.setText(String.format(Locale.TAIWAN, "%04d/%02d/%02d %02d:%02d:%02d",
//                stopDate.getYear(),
//                stopDate.getMonth(),
//                stopDate.getDay(),
//                stopTime.getHour(),
//                stopTime.getMinute(),
//                stopTime.getSecond()));

        return convertView;
    }
}
