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
public class BodyAdapter extends ArrayAdapter<Body> implements View.OnFocusChangeListener {

    private Context _context;
    private int _layoutResourceId;
    private Body[] _bodies = null;
    private OnFocusChangeListener onFocusChangeListener;

    public BodyAdapter(Context context, int resource, Body[] bodies) {
        super(context, resource, bodies);
        this._context = context;
        this._layoutResourceId = resource;
        this._bodies = bodies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(_layoutResourceId, null);
        }
        EditText editKey = (EditText) convertView.findViewById(R.id.editKey);
        EditText editValue = (EditText) convertView.findViewById(R.id.editValue);

        Body body = _bodies[position];
        editKey.setHint(R.string.body_hint);
        editValue.setHint(R.string.value_hint);

        if (body.getId() != 0) {
            editKey.setText(body.getKey());
            editValue.setText(body.getValue());
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
                Body body = new Body(position, editKey.getText().toString(), editValue.getText().toString(), "", "x-www-form-urlencoded", 0);
                onFocusChangeListener.OnFocusChange(body);
            }
        }
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    public interface OnFocusChangeListener {
        void OnFocusChange(Body body);
    }
}
