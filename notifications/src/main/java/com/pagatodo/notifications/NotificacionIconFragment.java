package com.pagatodo.notifications;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pagatodo.notifications.databinding.FragmentLibNotificacionIconBinding;

import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

public class NotificacionIconFragment extends AbstractFragment {

    private static final String TAG = NotificacionIconFragment.class.getName();
    private FragmentLibNotificacionIconBinding binding;
    private final Set<String> notificationIds = new TreeSet<>();

    public static NotificacionIconFragment newInstance() {
        final NotificacionIconFragment fragment = new NotificacionIconFragment();
        final Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNotificationsReminder();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lib_notificacion_icon, container, false);
        binding.btnNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final NotificacionesDialogFragment notificaciones = new NotificacionesDialogFragment();
                notificaciones.setNotificacionIconFragment(NotificacionIconFragment.this);
                notificaciones.show(getFragmentManager(), "");
            }
        });

        final NotificacionesProvider.NotificacionCallback listener = new NotificacionesProvider.NotificacionCallback(){

            @Override
            public void onUpdate(Integer notificacionesCount) {
                actualizarNotificaciones(notificacionesCount);
            }
        };

//        NotificacionesProvider notificacionesProvider = NotificacionesProvider.getInstance(getActivity().getApplication());
//        notificacionesProvider.setNotificacionCallback(listener);

        return binding.getRoot();
    }

    private void actualizarNotificaciones(final int noLeidasCount) {
//        MposApplication.getInstance().setNotisNoLeidas(noLeidasCount);

        binding.tvNumeroNotificaciones.setText(Integer.toString(noLeidasCount));
        binding.tvNumeroNotificaciones.setVisibility(noLeidasCount<=0?View.INVISIBLE:View.VISIBLE);
    }

    public void initNotificationsReminder() {

        PreferenceManager.putNotificationTimestamp(getActivity());

        final long timelapse = 5 * 60 * 1000;

        if ( handler == null ) {
            handler = new Handler();
        }
        if ( runnableCode == null ) {
            runnableCode = new Runnable() {
                @Override
                public void run() {
                    Long timestamp = PreferenceManager.getNotificationTimestamp(getActivity());

                    if ((timestamp + timelapse) < Calendar.getInstance().getTimeInMillis()) {
                        if (getActivity() instanceof OnNotificacionInteraction) {
                            ((OnNotificacionInteraction) getActivity()).notificationReminder(
                                    getString(R.string.Cabecera_notificacion_aviso),
                                    getString(R.string.Cuerpo_notificacion_aviso)
                            );
                        }else{
                            Toast.makeText(getActivity(), "La libreria notificaciones requiere que la actividad donde se ocupe implemente OnNotificacionInteraction", Toast.LENGTH_SHORT).show();
                        }
                        PreferenceManager.putNotificationTimestamp(getActivity());
                    }
                    handler.postDelayed(this, 10000);

                }
            };
        }

        handler.post(runnableCode);

    }

    @Override
    public void onResume() {
        super.onResume();
        initNotificationsReminder();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnableCode);
    }

}
