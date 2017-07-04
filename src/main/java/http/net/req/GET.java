package http.net.req;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GET extends AsyncTask<byte[], String, byte[]> {

    private static final byte[] ERROR = "ERROR".getBytes() ;
    private ResponseHandler responseHandler;
    private String url;

    GET(@NonNull String url,@NonNull ResponseHandler responseHandler){
        this.responseHandler = responseHandler;
        this.url = url;
    }

    GET(@NonNull String url,@NonNull ResponseHandler responseHandler,  HashMap<String,String> parameters){
        this.responseHandler = responseHandler;
        this.url = url;

        Iterator it = parameters.entrySet().iterator();

        this.url+="?";

        for(int i=0; it.hasNext(); i++){
            Map.Entry pair = (Map.Entry)it.next();
            if(i!=0){
                this.url+="&";
            }
            this.url+=pair.getKey()+"="+pair.getValue();
        }
    }

    public void execute(){
        this.execute(url.getBytes());
    }

    @Override
    protected byte[] doInBackground(byte[]... params) {
        String urlString = new String(params[0]);
        InputStream in = null;
        URL url;

        try {
            if(urlString.startsWith("http"))
                url = new URL(urlString);
            else
                url = new URL("http://"+urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            in = new BufferedInputStream(connection.getInputStream());

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
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(byte[] s) {
        super.onPostExecute(s);
        responseHandler.onResponse(s);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    public interface ResponseHandler{
        void onResponse(byte[] result);
    }

}
