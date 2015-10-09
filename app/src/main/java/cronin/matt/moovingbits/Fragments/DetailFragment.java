package cronin.matt.moovingbits.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cronin.matt.moovingbits.API.CallAPI;
import cronin.matt.moovingbits.Activities.MainActivity;
import cronin.matt.moovingbits.Data.BodyRepository;
import cronin.matt.moovingbits.Data.CRUDHelper;
import cronin.matt.moovingbits.Data.DomainRepository;
import cronin.matt.moovingbits.Data.HeaderRepository;
import cronin.matt.moovingbits.Data.RequestRepository;
import cronin.matt.moovingbits.Model.Body;
import cronin.matt.moovingbits.Model.BodyAdapter;
import cronin.matt.moovingbits.Model.Domain;
import cronin.matt.moovingbits.Model.Header;
import cronin.matt.moovingbits.Model.HeaderAdapter;
import cronin.matt.moovingbits.Model.Request;
import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 9/30/15.
 */
public class DetailFragment extends Fragment implements CallAPI.PostExecutable, CallAPI.ResponseReadable, CallAPI.PreExecutable, HeaderAdapter.OnFocusChangeListener, BodyAdapter.OnFocusChangeListener {

    private CRUDHelper domainRepository;
    private CRUDHelper requestRepository;
    private HeaderRepository headerRepository;
    private BodyRepository bodyRepository;
    private long requestId = 0;
    private long domainId = 0;
    private Domain domain = null;
    private Request request = null;
    private Callbackable activity;
    private int responseCode;
    private String responseMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        domainRepository = new DomainRepository(getActivity());
        requestRepository = new RequestRepository(getActivity());
        headerRepository = new HeaderRepository(getActivity());
        bodyRepository = new BodyRepository(getActivity());
        activity = (Callbackable)getActivity();

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
        headerRepository.deleteByRequest(requestId);
        bodyRepository.deleteByRequest(requestId);
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
        ArrayAdapter<CallAPI.RequestMethod> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,CallAPI.RequestMethod.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int position = 0;
        if (request.getType() != null) {
            position = adapter.getPosition(CallAPI.RequestMethod.valueOf(request.getType()));
        }

        spinner.setAdapter(adapter);
        spinner.setSelection(position);

        EditText routeText = (EditText) getActivity().findViewById(R.id.routeText);
        routeText.setText(request.getRoute());

        EditText urlText = (EditText) getActivity().findViewById(R.id.urlText);
        urlText.setText(domain.getURL());

        List<Header> headers = new ArrayList<>();
        headers.add(new Header());
        HeaderAdapter headerArrayAdapter = new HeaderAdapter(getActivity(), R.layout.key_value_list_item,headers.toArray(new Header[headers.size()]));
        headerArrayAdapter.setOnFocusChangeListener(this);
        ListView headerView = (ListView)getActivity().findViewById(R.id.listHeaders);
        headerView.setAdapter(headerArrayAdapter);

        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body());
        BodyAdapter bodyArrayAdapter = new BodyAdapter(getActivity(), R.layout.key_value_list_item, bodies.toArray(new Body[bodies.size()]));
        bodyArrayAdapter.setOnFocusChangeListener(this);
        ListView bodyView = (ListView)getActivity().findViewById(R.id.listBodies);
        bodyView.setAdapter(bodyArrayAdapter);

    }

    //call api
    public void CallAPIClick(View view) {
        CallAPI callAPI = new CallAPI(domain.getURL(), CallAPI.RequestMethod.valueOf(request.getType()));
        callAPI.setPostExecutable(this);
        callAPI.setResponseReadable(this);
        callAPI.setPreExecutable(this);
        callAPI.execute(request.getRoute());
    }

    //Post execute on calling api
    @Override
    public void PostExecute(String results) {
        //Unlock user inputs
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        activity.MoveToResults(results, responseCode, responseMessage);
    }

    //return response code from call. -1 if an expected error occurs, 0 for unexpected unhandled errors.
    @Override
    public void ReadResponse(int responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    //PreExecute for call async task. Lock user inputs.
    @Override
    public void PreExecute() {
        //lock user inputs while sending request
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //onfocuschange listener for HeaderAdapter
    @Override
    public void OnFocusChange(Header header) {
        header.setRequestId(requestId);
        if (header.getId() == 0){
            headerRepository.add(header);
        } else {
            headerRepository.update(header.getId(), header);
        }
    }

    //onfocuschange listener for BodyAdapter
    @Override
    public void OnFocusChange(Body body) {
        body.setRequestId(requestId);
        if (body.getId() == 0){
            bodyRepository.add(body);
        } else {
            bodyRepository.update(body.getId(), body);
        }
    }

    public interface Callbackable {
        void MoveToResults(String results, int responseCode, String responseMessage);
    }
}
