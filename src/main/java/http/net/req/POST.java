package http.net.req;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class POST extends AsyncTask<byte[], String, byte[]> {


    private static byte[] ERROR = "ERROR".getBytes();
    private ResponseHandler responseHandler;
    private String url;
    private byte data[];
    private HashMap<String,String> properties;


    public POST(@NonNull String url, HashMap<String,String> requestProperties, byte[] data,@NonNull ResponseHandler responseHandler){
        this.responseHandler = responseHandler;
        this.url = url;
        this.data = data;
        properties = requestProperties;
    }

    public void execute(){

        this.execute(url.getBytes(),data);
    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }


    @Override
    protected byte[] doInBackground(byte[]... params) {

        String urlString = new String(params[0]); // URL to call
        byte[] dat = params[1];

        InputStream in;
        HttpURLConnection connection;
        URL url;
        try {

            if(urlString.startsWith("http"))
                url = new URL(urlString);
            else
                url = new URL("http://"+urlString);

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            Iterator it = properties.entrySet().iterator();

            while (it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                connection.setRequestProperty((String) pair.getKey(),(String) pair.getValue());
            }
            connection.setRequestProperty("Content-Length", "" + Integer.toString(dat.length));
            connection.connect();

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(dat);
            wr.flush();
            wr.close();

            int status = connection.getResponseCode();

            if(status >= 400)
                in = connection.getErrorStream();
            else
                in = connection.getInputStream();

            byte data[] = new byte[connection.getContentLength()];
            int readcount = 0;
            while (readcount< connection.getContentLength()){
                readcount+= in.read(data,readcount,in.available());
            }
            connection.disconnect();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    @Override
    protected void onPostExecute(byte[] result) {
        //Update the UI
        responseHandler.onResponse(result);
    }




    public interface ResponseHandler{
        void onResponse(byte[] result);
    }
}
