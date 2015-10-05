package cronin.matt.moovingbits.API;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mattcronin on 9/29/15.
 */
public class CallAPI extends AsyncTask<String, String, String> {

    public String getApiUrl() {
        return apiUrl;
    }

    private String apiUrl = "";

    public void setExecutable(PostExecutable executable) {
        this.executable = executable;
    }

    private PostExecutable executable;

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    private RequestMethod requestMethod;

    public CallAPI(String apiUrlBase, RequestMethod requestMethod){
        this(apiUrlBase, requestMethod, null);
    }

    public CallAPI(String apiUrlBase, RequestMethod requestMethod, PostExecutable executable){
        this.apiUrl = apiUrlBase;
        this.requestMethod = requestMethod;
        this.executable = executable;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            URL url = new URL(apiUrl + params[0]);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            result = readStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String readStream(InputStream inputStream) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (executable != null){
            executable.Execute(result);
        }
    }

    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }
}
