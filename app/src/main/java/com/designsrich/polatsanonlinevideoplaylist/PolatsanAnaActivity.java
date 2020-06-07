package com.designsrich.polatsanonlinevideoplaylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Yakup ZENGİN - yakup [at] designsrich [dot] com
 * @version 0.0.3
 * @since 0.0.3
 *
 */

public class PolatsanAnaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog pDialog;
    VideoView videoView;
    ArrayList<String> arrayList = new ArrayList<>( Arrays.asList( "http://pltsan.com/videolar/video1.mp4", "http://pltsan.com/videolar/video2.mp4" ) );
    int index = 0;

    Button buyuksehirBelButton;
    Button saskiButton;
    Button sedasButton;
    Button edevletButton;
    Button mhrsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_polatsan );

        buyuksehirBelButton = findViewById(R.id.sakaryabel);
        saskiButton = findViewById(R.id.saski);
        sedasButton = findViewById(R.id.sedas);
        edevletButton = findViewById(R.id.edevlet);
        mhrsButton = findViewById(R.id.mhrs);
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        videoView = findViewById( R.id.videoView );
        final MediaController mediacontroller = new MediaController( this );
        mediacontroller.setAnchorView( videoView );

        // Create a progressbar
        pDialog = new ProgressDialog( PolatsanAnaActivity.this );

        // Set progressbar title
        pDialog.setTitle( "PolatSAN Video Oynatıcı" );

        // Set progressbar message
        pDialog.setMessage( "Video Yükleniyor, İyi Seyirler..." );
        pDialog.setIndeterminate( false );
        pDialog.setCancelable( false );
        // Show progressbar
        pDialog.show();
        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            public void run() {
                pDialog.dismiss();
            }
        }, 2000 );
        String videoUriTest = "android.resource://com.designsrich.polatsanonlinevideoplaylist/"+R.raw.beykozbel;
        videoView.setVideoURI( Uri.parse( videoUriTest ) );
        videoView.setMediaController( mediacontroller );
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener( new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        videoView.setMediaController( mediacontroller );
                        mediacontroller.setAnchorView( videoView );

                    }
                } );
            }
        } );

        videoView.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();

            }
        } );

        videoView.setOnErrorListener( new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d( "API123", "What " + what + " extra " + extra );
                return false;
            }
        } );


        buyuksehirBelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( PolatsanAnaActivity.this, "Lütfen Bekleyin. BEYKOZ Belediyesi Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT ).show();

                Intent intent = new Intent( PolatsanAnaActivity.this, BuyukSehirBelActivity.class );
                startActivity( intent );
            }
        });

        saskiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( PolatsanAnaActivity.this, "Lütfen Bekleyin. İBB Beyaz Masa Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT ).show();

                Intent intent = new Intent( PolatsanAnaActivity.this, SaskiMainActivity.class );
                startActivity( intent );
            }
        });

        sedasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( PolatsanAnaActivity.this, "Lütfen Bekleyin. İETT İSTANBUL Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT ).show();

                Intent intent = new Intent( PolatsanAnaActivity.this, SedasMainActivity.class );
                startActivity( intent );
            }
        });

        edevletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( PolatsanAnaActivity.this, "Lütfen Bekleyin. E-Devlet Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT ).show();

                Intent intent = new Intent( PolatsanAnaActivity.this, EdevletMainActivity.class );
                startActivity( intent );
            }
        });

        mhrsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( PolatsanAnaActivity.this, "Lütfen Bekleyin. MHRS Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT ).show();

                Intent intent = new Intent( PolatsanAnaActivity.this, MhrsMainActivity.class );
                startActivity( intent );
            }
        });




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.belediye) {
            Toast.makeText( PolatsanAnaActivity.this, "Lütfen Bekleyin. Belediye Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT ).show();

            Intent intent = new Intent( PolatsanAnaActivity.this, SedasMainActivity.class );
            startActivity( intent );

            // Handle the camera action
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
