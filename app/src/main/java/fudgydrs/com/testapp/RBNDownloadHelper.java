package fudgydrs.com.testapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.framed.FrameReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yetim on 3/8/2018.
 * Class to help resource test documents from rbnorway.org
 */

public class RBNDownloadHelper {
    private final OkHttpClient client = new OkHttpClient();
    private URL url = null;
    private String message = null;
    private Handler handler;
    private static final String LOG_TAG = "OkHttp";

    private void createURL(String name){
        try {
            url = new URL("http://rbnorway.org/" + name + "-t7-frames/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void callServer(String name) {
        createURL(name);
        handler = new Handler(Looper.getMainLooper());
        Request request = new Request.Builder()
                //.header("X-Client-Type", "Android")
                .url(url)
                //.post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                message = e.toString();
                Log.e(LOG_TAG, message); // no need inside run()
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //mTextView.setText(mMessage); // must be inside run()
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                message = response.toString();
                Log.i(LOG_TAG, message); // no need inside run()
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //mTextView.setText(mMessage); // must be inside run()
                    }
                });
            }
        });

    }
    public String getMessage() {
        return message;
    }
}
