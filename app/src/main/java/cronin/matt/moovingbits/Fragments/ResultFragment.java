package cronin.matt.moovingbits.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cronin.matt.moovingbits.Activities.DetailActivity;
import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 10/8/15.
 */
public class ResultFragment extends Fragment {

    private String resultValue;
    private int responseCode;
    private String responseMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        resultValue = bundle.getString(DetailActivity.DETAIL_RESULTS);
        responseCode = bundle.getInt(DetailActivity.DETAIL_RESPONSE_CODE);
        responseMessage = bundle.getString(DetailActivity.DETAIL_RESPONSE_MESSAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);
        TextView resultText = (TextView) rootView.findViewById(R.id.resultText);
        resultText.setText(resultValue);

        TextView responseCodeText = (TextView) rootView.findViewById(R.id.resultCode);
        responseCodeText.setText(Integer.toString(responseCode));

        TextView responseMessageText = (TextView) rootView.findViewById(R.id.resultMessage);
        responseMessageText.setText(responseMessage);

        return rootView;
    }
}
