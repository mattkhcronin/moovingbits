package cronin.matt.moovingbits.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 10/9/15.
 */
public class HeaderAdapter extends ArrayAdapter<Header> implements View.OnFocusChangeListener {

    private Context _context;
    private int _layoutResourceId;
    private Header[] _headers = null;
    private OnFocusChangeListener onFocusChangeListener;

    public HeaderAdapter(Context context, int resource, Header[] headers) {
        super(context, resource, headers);
        this._context = context;
        this._layoutResourceId = resource;
        this._headers = headers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(_layoutResourceId, null);
        }
        EditText editKey = (EditText) convertView.findViewById(R.id.editKey);
        EditText editValue = (EditText) convertView.findViewById(R.id.editValue);

        Header header = _headers[position];
        editKey.setHint(R.string.header_hint);
        editValue.setHint(R.string.value_hint);

        if (header.getId() != 0) {
            convertView.setId((int)header.getId());
            editKey.setText(header.getKey());
            editValue.setText(header.getValue());
        } else {
            convertView.setId(0);
        }
        //editKey.setOnFocusChangeListener(this);
        //editValue.setOnFocusChangeListener(this);

        return convertView;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            if (onFocusChangeListener != null) {
                int position = view.getId();
                EditText editKey = (EditText) view.findViewById(R.id.editKey);
                EditText editValue = (EditText) view.findViewById(R.id.editValue);
                Header header = new Header(position, editKey.getText().toString(), editValue.getText().toString(), 0);
                onFocusChangeListener.OnFocusChange(header);
            }
        }
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    public interface OnFocusChangeListener {
        void OnFocusChange(Header header);
    }
}
