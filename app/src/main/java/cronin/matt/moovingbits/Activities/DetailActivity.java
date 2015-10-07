package cronin.matt.moovingbits.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import cronin.matt.moovingbits.Fragments.DetailFragment;
import cronin.matt.moovingbits.R;

public class DetailActivity extends AppCompatActivity {

    public final static int DETAIL_REQUEST_CODE = 1001;
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

}
