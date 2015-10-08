package cronin.matt.moovingbits.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cronin.matt.moovingbits.Fragments.ResultFragment;
import cronin.matt.moovingbits.R;

/**
 * Created by mattcronin on 10/8/15.
 */
public class ResultActivity extends AppCompatActivity {

    private ResultFragment resultFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getBundleExtra(DetailActivity.DETAIL_BUNDLE);

        resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .add(R.id.resultContainer, resultFragment)
                .commit();
    }
}
