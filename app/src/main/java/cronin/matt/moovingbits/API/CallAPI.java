package cronin.matt.moovingbits.API;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mattcronin on 9/29/15.
 */
public class CallAPI extends AsyncTask<String, String, String> {

    public String getApiUrl() {
        return apiUrl;
    }

    private String apiUrl = "";

    private Map<String, String> headers;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body = "";

    public void addHeader(String field, String value) {
        headers.put(field, value);
    }

    public void setPostExecutable(PostExecutable executable) {
        this.postExecutable = executable;
    }

    private PostExecutable postExecutable;

    public void setPreExecutable(PreExecutable preExecutable) {
        this.preExecutable = preExecutable;
    }

    private PreExecutable preExecutable;

    public void setResponseReadable(ResponseReadable responseReadable) {
        this.responseReadable = responseReadable;
    }

    private ResponseReadable responseReadable;

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    private RequestMethod requestMethod;

    public CallAPI(String apiUrlBase, RequestMethod requestMethod) {
        this.apiUrl = apiUrlBase;
        this.requestMethod = requestMethod;
        headers = new HashMap<>();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        String responseMessage = "";
        int responseCode = 0;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(apiUrl + params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(requestMethod.toString());
            //Set headers
            if (!headers.isEmpty()) {
                setUrlHeaders(urlConnection);
            }

            //send output stream if applicable
            if (requestMethod == RequestMethod.POST || requestMethod == RequestMethod.PUT){
                sendData(urlConnection);
            }

            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            responseCode = urlConnection.getResponseCode();
            responseMessage = urlConnection.getResponseMessage();
            result = readStream(inputStream);
        } catch (MalformedURLException e) {
            responseCode = -1;
            responseMessage = "The requested URL was not in the correct format.";
        } catch (UnknownHostException e) {
            responseCode = -1;
            responseMessage = e.getMessage();
        } catch (IOException e) {
            try {
                // Set response code if possible.
                if (urlConnection != null) {
                    responseCode = urlConnection.getResponseCode();
                    responseMessage = urlConnection.getResponseMessage();
                }
            } catch (IOException e2) {
                e2.getStackTrace();
                responseCode = -1;
                responseMessage = "An unhandled error occured.";
            }
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        if (responseReadable != null) {
            responseReadable.ReadResponse(responseCode, responseMessage);
        }
        return result;
    }

    private void setUrlHeaders(HttpURLConnection urlConnection){
        Iterator iterator = headers.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry)iterator.next();
            urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private void sendData(HttpURLConnection urlConnection){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(body);
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    protected void onPreExecute() {
        if (preExecutable != null) {
            preExecutable.PreExecute();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (postExecutable != null) {
            postExecutable.PostExecute(result);
        }
    }

    public interface PostExecutable {
        void PostExecute(String results);
    }

    public interface PreExecutable {
        void PreExecute();
    }

    public interface ResponseReadable {
        void ReadResponse(int responseCode, String responseMessage);
    }

    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }
}
