package cronin.matt.moovingbits.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import cronin.matt.moovingbits.API.CallAPI;
import cronin.matt.moovingbits.API.PostExecutable;
import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 9/30/15.
 */
public class DetailFragment extends Fragment implements PostExecutable {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.response_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.api_detail_fragment, container, false);
        return rootView;
    }

    public void button_click(View view) {
        CallAPI callAPI = new CallAPI("http://restfulapp.azurewebsites.net/", CallAPI.RequestMethod.GET, this);
        callAPI.execute("api/cow");
    }

    @Override
    public void Execute(String results) {
        TextView textView = (TextView) getActivity().findViewById(R.id.text);
        textView.setText(results);
    }
}
