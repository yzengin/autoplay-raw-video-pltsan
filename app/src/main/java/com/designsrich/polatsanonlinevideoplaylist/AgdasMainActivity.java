package com.designsrich.polatsanonlinevideoplaylist;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author Yakup ZENGİN - yakup [at] designsrich [dot] com
 * @version 0.0.3
 * @since 0.0.3
 */

public class AgdasMainActivity extends Activity implements View.OnClickListener {
    private Handler handler2;
    private Runnable r;
    private static String TAG = "AgdasMainActivity";

    private WebView myWebView;
    private MyWebChromeClient myWebChromeClient;
    private RelativeLayout childLayout;
    private RelativeLayout browserLayout;
    private Button mainCloseButton;
    private TextView titleText;

    /**
     * @param savedInstanceState - saved data bundle from the system
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpWidgets();
        setupBrowser("https://www.igdas.istanbul/");


        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.floatingActionButton );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( AgdasMainActivity.this, "Lütfen Bekleyin. Video Oynatıcı Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_LONG ).show();
                Intent intent = new Intent( AgdasMainActivity.this,PolatsanAnaActivity.class );
                startActivity( intent );
            }
        } );

        handler2 = new Handler();
        r = new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), PolatsanAnaActivity.class);
                startActivity(intent);
                Log.d(TAG, "Logged out after 3 minutes on inactivity.");
                finish();

                Toast.makeText(AgdasMainActivity.this, "Oturum İşleminiz doldu.", Toast.LENGTH_SHORT).show();
            }
        };

        startHandler();

    }

    public void stopHandler() {
        handler2.removeCallbacks(r);
        Log.d("HandlerRun", "stopHandlerMain");
    }

    public void startHandler() {
        handler2.postDelayed(r, 5 * 60 * 1000);
        Log.d("HandlerRun", "startHandlerMain");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    @Override
    protected void onPause() {

        stopHandler();
        Log.d("onPause", "onPauseActivity change");
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startHandler();

        Log.d("onResume", "onResume_restartActivity");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopHandler();
        Log.d("onDestroy", "onDestroyActivity change");

    }


    /**
     * Does all of the grunt work of setting up the app's main webview
     */
    private void setupBrowser(String url) {
        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings settings = myWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setGeolocationEnabled(false);  // normally set true

        settings.setSupportMultipleWindows(true);

        //These database/cache/storage calls might not be needed, but just in case
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCachePath(getApplicationContext().getDatabasePath("myAppCache").getAbsolutePath());
        settings.setDatabasePath(getApplicationContext().getDatabasePath("myDatabase").getAbsolutePath()); //deprecated in Android 4.4 KitKat (API level 19)

        myWebChromeClient = new MyWebChromeClient(AgdasMainActivity.this, childLayout, browserLayout, titleText);
        myWebView.setWebChromeClient(myWebChromeClient);

        myWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.v(Constants.LOG_TAG, "URL: " + url);
                view.loadUrl(url);
                return false;
            }

            //If no internet, redirect to error page
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.v(Constants.LOG_TAG, "Error: " + failingUrl);
            }

            public void onPageFinished(WebView view, String url) {
                Log.v(Constants.LOG_TAG, "Finished: " + url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
                handler.proceed(); // Ignore SSL certificate errors
            }

        });
        myWebView.loadUrl(url);
    }

    /**
     * Overrides the back key handler
     *
     * @param keyCode - the pressed key identifier
     * @param event   - the key event type
     * @return - true if we handled the key pressed
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (myWebView != null) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        if (myWebChromeClient.isChildOpen()) {
                            myWebChromeClient.closeChild();
                        } else if (myWebView.canGoBack()) {
                            myWebView.goBack();
                        }
                        return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Handles the pressing of all buttons on the activity
     *
     * @param v - the view (button) which triggered the click
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainCloseButton:
                myWebChromeClient.closeChild();
                break;
        }
    }

    /**
     * Button setup method
     */
    private void setUpWidgets() {
        browserLayout = (RelativeLayout) findViewById(R.id.mainBrowserLayout);
        childLayout = (RelativeLayout) findViewById(R.id.mainAdChildLayout);
        titleText = (TextView) findViewById(R.id.mainTitleText);
        mainCloseButton = (Button) findViewById(R.id.mainCloseButton);
        mainCloseButton.setOnClickListener(this);
    }
}