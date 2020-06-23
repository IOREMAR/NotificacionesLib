package com.pagatodo.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Set;
import java.util.TreeSet;

public class NotificacionesProvider {

    private static NotificacionesProvider notificacionesProvider;

    private Context appContext;
    private int numeroNotificaciones;
    private NotificacionCallback notificacionCallback;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            final Set<String> notificacionesLeidas = PreferenceManager.getNotificaciones(appContext);
            copyNotificaciones.removeAll(notificacionesLeidas);
            setNumeroNotificaciones(copyNotificaciones.size());
        }
    };
    private TreeSet<String> copyNotificaciones = new TreeSet<>();

    private NotificacionesProvider(Context context) {
        appContext = context;

        obtenerTotalNotificacionesFirestore(context.getString(
                R.string.firestore_notificacion,
                AbstractDialogFragment.applicationId,
                "all"));

        obtenerTotalNotificacionesFirestore( context.getString(
                R.string.firestore_notificacion,
                AbstractDialogFragment.applicationId,
                AbstractDialogFragment.tpv));

        obtenerTotalNotificacionesFirestore(context.getString(
                R.string.firestore_mensajes,
                AbstractDialogFragment.applicationId,
                AbstractDialogFragment.tpv));

        obtenerTotalNotificacionesFirestore(context.getString(
                R.string.firestore_mensajes,
                AbstractDialogFragment.applicationId,
                "all"));

        PreferenceManager.addNotificacionesLeidasListener(context, preferenceListener);

    }

    public static NotificacionesProvider getInstance(Context context){
        if (notificacionesProvider==null) {
            notificacionesProvider = new NotificacionesProvider(context);
        }
        return notificacionesProvider;
    }

    private Set<String> notificationIds = new TreeSet<>();

    public void obtenerTotalNotificacionesFirestore(final String path){
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final Query query = database.collection(path);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(final @Nullable QuerySnapshot snapshots,
                                final @Nullable FirebaseFirestoreException exc) {

                if (exc != null || snapshots==null) {
                    return;
                }
                int numberNotifications=0;
                Notificacion notificacion;
                for (final DocumentChange documentChange : snapshots.getDocumentChanges()) {
                    notificacion = parseNotificacion(documentChange);
                    if(!notificacion.isLeida()){
                        numberNotifications = 1;
                        setNumeroNotificaciones(numberNotifications);
                    }
                }

                setNumeroNotificaciones(numberNotifications);
                Log.i("NUMERO NOTIFICACIONES", "No.: " +numberNotifications);
            }
        });
    }

    public void setNumeroNotificaciones(int numeroNotificaciones) {
        if (notificacionCallback!=null) {
            notificacionCallback.onUpdate(numeroNotificaciones);
        }
        this.numeroNotificaciones = numeroNotificaciones;
    }

    public NotificacionCallback getNotificacionCallback() {
        return notificacionCallback;
    }

    public void setNotificacionCallback(NotificacionCallback notificacionCallback) {
        this.notificacionCallback = notificacionCallback;
    }

    private Notificacion parseNotificacion(final DocumentChange documentChange){
        final Notificacion notificacion = documentChange.getDocument().toObject(Notificacion.class);
        notificacion.setId(documentChange.getDocument().getId());
        return notificacion;
    }

    public interface NotificacionCallback {
        void onUpdate(Integer notificacionesCount);
    }
}
