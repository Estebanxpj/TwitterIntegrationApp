package co.com.movilbox.twitterintegrationapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.victor.loading.rotate.RotateLoading;

import co.com.movilbox.twitterintegrationapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    RotateLoading loading;
    boolean respuestaApp;
    private final Context CONTEXT = SplashScreenActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loading = (RotateLoading) findViewById(R.id.rotate_loading);
        loading.start();
        // Segun esta bariable se configura el estado en produccion o el estado de pruebas en la aplicacion
        //estadoApp = "pruebas";
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Eliminamos la configuracion
        new Handler().postDelayed(new Runnable() {
            public void run() {
                loading.stop();
                startActivity(new Intent(CONTEXT, LoginActivity.class));
                finish();
            }
        }, 4000);

    }

}
