package tw.edu.pu.cs.wrist_band;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CardAdapter extends ArrayAdapter<Book> {

    public CardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_adapter, parent, false);
        }

        Book book = getItem(position);

        TextView title = convertView.findViewById(R.id.title);
        TextView text = convertView.findViewById(R.id.description);

        title.setText(book.title);
//        text.setText(book.text.substring(0, 50).replace("\n", "") + "...");

        text.setText(book.text);
        return convertView;
    }
}
