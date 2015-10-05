package cronin.matt.moovingbits.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import cronin.matt.moovingbits.Data.CRUDHelper;
import cronin.matt.moovingbits.Data.DomainRepository;
import cronin.matt.moovingbits.Data.RequestRepository;
import cronin.matt.moovingbits.Fragments.DetailFragment;
import cronin.matt.moovingbits.Model.Domain;
import cronin.matt.moovingbits.Model.Request;
import cronin.matt.moovingbits.R;

public class DetailActivity extends AppCompatActivity {

    public final static int DETAIL_REQUEST_CODE = 1001;
    private CRUDHelper domainRepository;
    private CRUDHelper requestRepository;
    private long requestId = 0;
    private long domainId = 0;
    private Request request = null;
    private Domain domain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getBundleExtra(MainActivity.MAIN_BUNDLE);
        requestId = b.getLong(MainActivity.MAIN_REQUEST_ID);

        domainRepository = new DomainRepository(this);
        requestRepository = new RequestRepository(this);

        if (requestId == 0){
            request = new Request();
            domain = new Domain();
        } else{
            request = (Request) requestRepository.getItem(requestId);
            domainId = request.getDomainId();
            domain = (Domain)domainRepository.getItem(domainId);
        }
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

        if (id == R.id.action_add) {
            return true;
        } else if (id == R.id.action_delete){
            DeleteItem();
        } else if (id == R.id.action_save){
            SaveItem();
        } else if (id == R.id.home){
            SaveItem();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveItem(){
        if (requestId == 0){
            domain = getDomain();
            domainId = domainRepository.add(domain);
            domain.setId(domainId);
            request = getRequest();
            requestId = requestRepository.add(request);
        } else {
            domain = getDomain();
            domainRepository.update(domainId, domain);

            request = getRequest();
            requestRepository.update(requestId, request);
        }
    }

    private void DeleteItem(){
        requestRepository.delete(requestId);
        domainRepository.delete(domainId);
        finish();
    }

    private Domain getDomain(){
        DetailFragment detailFragment = (DetailFragment)getFragmentManager().findFragmentById(R.id.detailFragment);
        return detailFragment.getDomain();
    }

    private Request getRequest(){
        DetailFragment detailFragment = (DetailFragment)getFragmentManager().findFragmentById(R.id.detailFragment);
        return detailFragment.getRequest();
    }
}
