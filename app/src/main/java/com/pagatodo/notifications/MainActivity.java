package com.pagatodo.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        String notificacionApplicationId = "com_pagatodo_apidemo_PE_debug";
//        String tpvcod = ApiInstance.getInstance().getDatosSesion().getDatosTPV().getTpvcod();
        String tpvcod = "00001991";
        com.pagatodo.notifications.AbstractDialogFragment.initIdentity(
                notificacionApplicationId,
                tpvcod
        );

        abrirNotificaciones();
    }

    private void abrirNotificaciones() {
        NotificacionesDialogFragment notificacionesDialogFragment = new NotificacionesDialogFragment();
        notificacionesDialogFragment.setNotificacionIconFragment(new NotificacionIconFragment());
        notificacionesDialogFragment.show(getFragmentManager(), "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
