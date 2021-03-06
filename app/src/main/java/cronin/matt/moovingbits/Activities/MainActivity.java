package cronin.matt.moovingbits.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cronin.matt.moovingbits.Fragments.SelectionFragment;
import cronin.matt.moovingbits.R;


public class MainActivity extends AppCompatActivity implements SelectionFragment.Callbackable {

    public final static int MAIN_REQUEST_CODE = 1000;
    public final static String MAIN_BUNDLE = "mainBundle";
    public final static String MAIN_REQUEST_ID = "mainRequestId";
    public final static String MAIN_DOMAIN_ID = "mainDomainId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoveToDetail(0,0);
            }
        });
    }


    @Override
    public void MoveToDetail(long domainId, long requestId) {
        Bundle b = new Bundle();
        b.putLong(MAIN_REQUEST_ID, requestId);
        b.putLong(MAIN_DOMAIN_ID, domainId);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MAIN_BUNDLE, b);
        startActivityForResult(intent, MAIN_REQUEST_CODE);
    }
}
