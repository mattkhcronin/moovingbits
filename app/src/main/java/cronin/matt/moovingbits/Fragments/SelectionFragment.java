package cronin.matt.moovingbits.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import cronin.matt.moovingbits.Data.RequestRepository;
import cronin.matt.moovingbits.Model.Request;
import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 9/30/15.
 */
public class SelectionFragment extends ListFragment {

    public SelectionFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.selection_list_fragment, container, false);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    public void refreshView(){
        RequestRepository requestRepository = new RequestRepository(getActivity());
        List<Request> requests = requestRepository.getAll();
        ArrayAdapter<Request> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_layout, requests);
        setListAdapter(adapter);
    }
}
