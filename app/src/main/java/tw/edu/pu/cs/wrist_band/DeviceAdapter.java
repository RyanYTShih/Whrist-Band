package tw.edu.pu.cs.wrist_band;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.epson.pulsenseview.wellnesscommunication.bluetooth.Peripheral;

public class DeviceAdapter extends ArrayAdapter<Peripheral> {

    private ScanActivity mContext;

    public DeviceAdapter(@NonNull ScanActivity context, int resource) {
        super(context, resource);
        mContext = context;
    }

    @Override
    public void add(@Nullable Peripheral peripheral) {
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).getName().equals(peripheral.getName()))
                return;
        }
        super.add(peripheral);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.device_adapter, parent, false);
        }

        final Peripheral peripheral = getItem(position);

        TextView deviceName = convertView.findViewById(R.id.deviceName);
        TextView deviceAddress = convertView.findViewById(R.id.deviceAddress);
        Button register = convertView.findViewById(R.id.register);

        deviceName.setText(peripheral.getName());
        deviceAddress.setText(peripheral.getUuid());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.registerDevice(peripheral);
            }
        });

        return convertView;
    }
}
