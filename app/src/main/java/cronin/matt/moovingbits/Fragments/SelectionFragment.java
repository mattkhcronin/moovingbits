package cronin.matt.moovingbits.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cronin.matt.moovingbits.Data.DomainRepository;
import cronin.matt.moovingbits.Data.RequestRepository;
import cronin.matt.moovingbits.Model.Domain;
import cronin.matt.moovingbits.Model.DomainListAdapter;
import cronin.matt.moovingbits.Model.Request;
import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 9/30/15.
 */
public class SelectionFragment extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupExpandListener {

    private Callbackable activity;
    private List<Domain> domains;
    private HashMap<Domain, List<Request>> domainRequestMap;
    private int lastExpandedPosition = -1;
    private ExpandableListView expandableListView;

    public SelectionFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Callbackable)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.selection_list_fragment, container, false);
        return rootView;
    }

    public void refreshView(){
        setDomains();
        setDomainRequestMap();
        expandableListView = (ExpandableListView) getActivity().findViewById(R.id.domainlist);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupExpandListener(this);
        DomainListAdapter domainListAdapter = new DomainListAdapter(getActivity(), domains, domainRequestMap);
        expandableListView.setAdapter(domainListAdapter);
    }

    private void setDomains(){
        DomainRepository domainRepository = new DomainRepository(getActivity());
        domains = domainRepository.getAll();
    }

    private List<Request> getRequests(long domainId) {
        RequestRepository requestRepository = new RequestRepository(getActivity());
        return requestRepository.getAllByDomain(domainId);
    }

    private void setDomainRequestMap() {
        domainRequestMap = new HashMap<>();
        for (Domain domain : domains) {
            List<Request> requests = new ArrayList<>();
            requests.add(new Request(0, null, null, domain.getId()));
            requests.addAll(getRequests(domain.getId()));
            domainRequestMap.put(domain, requests);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        ExpandableListView expandableListView = (ExpandableListView) getActivity().findViewById(R.id.domainlist);
        long domainId = expandableListView.getExpandableListAdapter().getGroupId(groupPosition);
        long requestId = expandableListView.getExpandableListAdapter().getChildId(groupPosition, childPosition);
        activity.MoveToDetail(domainId, requestId);
        return true;
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
            expandableListView.collapseGroup(lastExpandedPosition);
        }
        lastExpandedPosition = groupPosition;
    }

    public interface Callbackable {
        void MoveToDetail(long domainId, long requestId);
    }
}
