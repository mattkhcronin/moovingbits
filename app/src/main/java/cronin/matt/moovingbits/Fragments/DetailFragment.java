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
import cronin.matt.moovingbits.Activities.MainActivity;
import cronin.matt.moovingbits.Data.CRUDHelper;
import cronin.matt.moovingbits.Data.DomainRepository;
import cronin.matt.moovingbits.Data.RequestRepository;
import cronin.matt.moovingbits.Model.Domain;
import cronin.matt.moovingbits.Model.Request;
import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 9/30/15.
 */
public class DetailFragment extends Fragment implements PostExecutable {

    private CRUDHelper domainRepository;
    private CRUDHelper requestRepository;
    private long requestId = 0;
    private long domainId = 0;
    private Domain domain = null;
    private Request request = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        domainRepository = new DomainRepository(getActivity());
        requestRepository = new RequestRepository(getActivity());

        Bundle bundle = this.getArguments();

        //Set up current request that is being handled. 0 for new
        requestId = bundle.getLong(MainActivity.MAIN_REQUEST_ID);
        domainId = bundle.getLong(MainActivity.MAIN_DOMAIN_ID);

        // create empty request and domain or grab existing
        if (requestId == 0){
            request = new Request();
        } else{
            request = (Request) requestRepository.getItem(requestId);
        }

        if (domainId == 0) {
            domain = new Domain();
        } else {
            domain = (Domain) domainRepository.getItem(domainId);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    public void DeleteItem(){
        //remove domain and request from database.
        requestRepository.delete(requestId);
        domainRepository.delete(domainId);
    }

    public void SaveItem(){
        if (domainId == 0){
            // add domain to database
            domain = getDomain();
            domainId = domainRepository.add(domain);
            domain.setId(domainId);
        } else {
            //update domain objects
            domain = getDomain();
            domainRepository.update(domainId, domain);
        }

        if (requestId == 0){
            // add domain to database
            request = getRequest();
            request.setDomainId(domainId);
            requestId = requestRepository.add(request);
            request.setId(requestId);
        } else {
            request = getRequest();
            requestRepository.update(requestId, request);
        }
    }

    //create domain object based on current data selected
    private Domain getDomain(){
        Domain domain = new Domain();
        EditText urlText = (EditText) getActivity().findViewById(R.id.urlText);
        domain.setURL(urlText.getText().toString());
        return domain;
    }

    //create request object based on current data selected
    private Request getRequest(){
        Request request = new Request();
        request.setId(requestId);
        request.setDomainId(domainId);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        request.setType(spinner.getSelectedItem().toString());

        EditText routeText = (EditText) getActivity().findViewById(R.id.routeText);
        request.setRoute(routeText.getText().toString());
        return request;
    }

    private void refreshView(){
        //Set up spinner with defaults: Get, Post, Put, Delete
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.response_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int position = 0;
        if (request.getType() != null) {
            position = adapter.getPosition(request.getType());
        }

        spinner.setAdapter(adapter);
        spinner.setSelection(position);

        EditText routeText = (EditText) getActivity().findViewById(R.id.routeText);
        routeText.setText(request.getRoute());

        EditText urlText = (EditText) getActivity().findViewById(R.id.urlText);
        urlText.setText(domain.getURL());

    }

    //call api
    public void button_click(View view) {
        CallAPI callAPI = new CallAPI("http://restfulapp.azurewebsites.net/", CallAPI.RequestMethod.GET, this);
        callAPI.execute("api/cow");
    }

    //Post execute on calling api
    @Override
    public void Execute(String results) {
        TextView textView = (TextView) getActivity().findViewById(R.id.text);
        textView.setText(results);
    }
}
