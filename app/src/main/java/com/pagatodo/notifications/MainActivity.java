package com.pagatodo.notifications;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNotificaciones();
            }
        });

        String notificacionApplicationId = "com.pagatodo.yawallet/CO";
        //String tpvcod = "00002568";//Tpv Sin Mensajes
        String tpvcod = "00005880";//Tpv Con Mensajes

        com.pagatodo.notifications.AbstractDialogFragment.initIdentity(
                notificacionApplicationId,
                tpvcod
        );

        abrirNotificaciones();
    }

    private void abrirNotificaciones() {
        NotificacionesDialogFragment notificacionesDialogFragment = new NotificacionesDialogFragment();
        notificacionesDialogFragment.setNotificacionIconFragment(new NotificacionIconFragment());
        notificacionesDialogFragment.show(getSupportFragmentManager(), "");
    }
}
