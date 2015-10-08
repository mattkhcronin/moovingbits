package cronin.matt.moovingbits.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cronin.matt.moovingbits.Fragments.DetailFragment;
import cronin.matt.moovingbits.R;

public class DetailActivity extends AppCompatActivity implements DetailFragment.Callbackable {

    public final static int DETAIL_REQUEST_CODE = 1001;
    public final static String DETAIL_RESULTS = "detailResults";
    public final static String DETAIL_RESPONSE_CODE = "detailResponseCode";
    public final static String DETAIL_RESPONSE_MESSAGE = "detailResponseMessage";
    public final static String DETAIL_BUNDLE = "detailBundle";
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getBundleExtra(MainActivity.MAIN_BUNDLE);

        detailFragment = new DetailFragment();

        detailFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .add(R.id.detailContainer, detailFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    public void CallAPIClick(View view) {
        detailFragment.CallAPIClick(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_copy) {
            return true;
        } else if (id == R.id.action_delete){
            detailFragment.DeleteItem();
        } else if (id == R.id.action_save){
            detailFragment.SaveItem();
        } else if (id == R.id.home){
            detailFragment.SaveItem();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void MoveToResults(String results, int responseCode, String responseMessage) {
        Bundle bundle = new Bundle();
        bundle.putString(DETAIL_RESULTS, results);
        bundle.putInt(DETAIL_RESPONSE_CODE, responseCode);
        bundle.putString(DETAIL_RESPONSE_MESSAGE, responseMessage);

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(DETAIL_BUNDLE, bundle);
        startActivity(intent);
    }
}
