package cronin.matt.moovingbits.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import cronin.matt.moovingbits.Data.RequestRepository;
import cronin.matt.moovingbits.Fragments.SelectionFragment;
import cronin.matt.moovingbits.R;


public class MainActivity extends AppCompatActivity {

    public final static int MAIN_REQUEST_CODE = 1000;
    public final static String MAIN_BUNDLE = "mainBundle";
    public final static String MAIN_REQUEST_ID = "mainRequestId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            addItem();
        }

        return super.onOptionsItemSelected(item);
    }

    private void addItem(){
        Bundle b = new Bundle();
        b.putString(MAIN_BUNDLE, "0");

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MAIN_BUNDLE, b);
        startActivityForResult(intent, MAIN_REQUEST_CODE);
    }

    private void deleteItem(){
        RequestRepository requestRepository = new RequestRepository(this);
        requestRepository.deleteAll();
        refreshFragment();
    }

    private void refreshFragment() {
        SelectionFragment fragment = (SelectionFragment) getFragmentManager().findFragmentById(R.id.selectionFragment);
        fragment.refreshView();
    }
}
