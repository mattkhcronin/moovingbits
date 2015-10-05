package cronin.matt.moovingbits.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cronin.matt.moovingbits.API.CallAPI;
import cronin.matt.moovingbits.API.PostExecutable;
import cronin.matt.moovingbits.Model.Domain;
import cronin.matt.moovingbits.Model.Request;
import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 9/30/15.
 */
public class DetailFragment extends Fragment implements PostExecutable {

    private long domainId = 0;
    private long requestId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.api_detail_fragment, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.response_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

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

    public Domain getDomain(){
        Domain domain = new Domain();
        EditText urlText = (EditText) getActivity().findViewById(R.id.urlText);
        domain.setURL(urlText.getText().toString());
        return domain;
    }

    public Request getRequest(){
        Request request = new Request();
        request.setId(requestId);
        request.setDomainId(domainId);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        request.setType(spinner.getSelectedItem().toString());

        EditText urlText = (EditText) getActivity().findViewById(R.id.urlText);
        request.setRoute(urlText.getText().toString());
        return request;
    }
}
