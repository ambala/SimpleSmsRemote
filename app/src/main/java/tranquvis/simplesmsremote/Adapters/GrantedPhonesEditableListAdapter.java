package tranquvis.simplesmsremote.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tranquvis.simplesmsremote.R;

/**
 * Created by Andreas Kaltenleitner on 30.08.2016.
 */
public class GrantedPhonesEditableListAdapter extends ArrayAdapter<String>
{
    public static final int LAYOUT_RES = R.layout.listview_item_granted_phones_editable;

    private List<String> phones;
    private ListView listView;
    private int itemHeight = 0;

    public GrantedPhonesEditableListAdapter(Context context, List<String> phones, ListView listView)
    {
        super(context, LAYOUT_RES, phones);
        this.phones = phones;
        this.listView = listView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(LAYOUT_RES, parent, false);

            if(itemHeight == 0)
            {
                convertView.measure(0, 0);
                itemHeight = convertView.getMeasuredHeight();
            }
        }

        String phone = phones.get(position);

        final EditText phoneEditText = (EditText) convertView.findViewById(R.id.editText);
        ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.imageButton_delete);

        phoneEditText.setText(phone);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                removePhone(position);
            }
        });

        return convertView;
    }

    public void removePhone(int position)
    {
        updateData();
        phones.remove(position);
        notifyDataSetChanged();
    }

    public void addPhone(String phone)
    {
        updateData();
        phones.add(phone);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = (itemHeight + listView.getDividerHeight()) * phones.size();
    }

    public void updateData()
    {
        for(int i = 0; i < listView.getChildCount(); i++) {
            View view = listView.getChildAt(i);
            EditText editText = (EditText) view.findViewById(R.id.editText);
            phones.set(i, editText.getText().toString());
        }
    }
}