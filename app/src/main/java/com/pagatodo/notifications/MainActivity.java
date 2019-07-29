package com.pagatodo.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String notificacionApplicationId = "com_pagatodo_apidemo_PE_debug";
//        String tpvcod = ApiInstance.getInstance().getDatosSesion().getDatosTPV().getTpvcod();
        String tpvcod = "00001991";
        com.pagatodo.notifications.AbstractDialogFragment.initIdentity(
                notificacionApplicationId,
                tpvcod
        );


        NotificacionesDialogFragment notificacionesDialogFragment = new NotificacionesDialogFragment();
        notificacionesDialogFragment.setNotificacionIconFragment(new NotificacionIconFragment());
        notificacionesDialogFragment.show(getFragmentManager(), "");
    }
}
