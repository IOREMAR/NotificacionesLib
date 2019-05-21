package com.pagatodo.notifications;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.pagatodo.notifications.databinding.FragmentLibDialogNotificacionesBinding;

import java.util.Objects;

public class NotificacionesDialogFragment extends AbstractFragment {



    //----------UI-------------------------------------------------------
    private FragmentLibDialogNotificacionesBinding binding;
    private NotificacionIconFragment notificacionIconFragment;
    //----- Inst ----------------------------------------------------------

    //----- Var ----------------------------------------------------------
    int detailId;
    private boolean isLandScape;
    ListaNotificacionesFragment fragmentLista;

    public void loadFragmentLista(){
        binding.configMenuDetail.setVisibility(isLandScape ? View.GONE : View.VISIBLE);
        cargarFragment(getChildFragmentManager(), fragmentLista, R.id.config_menu_items);
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = isLandscape();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        initUI(inflater, container);
        Objects.requireNonNull(getDialog().getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI(final LayoutInflater inflater, final ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lib_dialog_notificaciones, container, false);
        detailId = isLandScape ? R.id.config_menu_detail : R.id.config_menu_items;
        binding.configMenuDetail.setVisibility(isLandScape ? View.VISIBLE : View.GONE);

        fragmentLista = new ListaNotificacionesFragment();
        cargarFragment(getChildFragmentManager(), fragmentLista, R.id.config_menu_items);

        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                PreferenceManager.putNotificationTimestamp(getActivity());
                return false;
            }
        });
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void onStart() {
        super.onStart();
        final Dialog dialog = getDialog();
        if (dialog != null) {
            final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
            params.topMargin = 20;
            params.rightMargin = 20;
            params.bottomMargin = 20;
            params.leftMargin = 20;
            params.gravity = Gravity.CENTER;
            binding.getRoot().setLayoutParams(params);
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.background_notifications_fragment);
        }
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event ) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    return true;
                }
                return false;
            }
        });
    }

    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {

            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    public void setNotificacionIconFragment(final NotificacionIconFragment notificacionIconFragment) {
        this.notificacionIconFragment = notificacionIconFragment;
    }

    public void seleccionaNotificacion( final Notificacion notificacion) {

        binding.configMenuDetail.setVisibility(View.VISIBLE);
        cargarFragment(getChildFragmentManager(), NotificacionDetalleFragment.newInstance(notificacion), detailId);
    }

    public void actualizarDetalleNotificacion(final Notificacion notificacion) {
        if (binding.configMenuDetail.getVisibility() == View.VISIBLE) {
            final Fragment fragment = getChildFragmentManager().findFragmentByTag(NotificacionDetalleFragment.class.getSimpleName());
            if (fragment != null
                && fragment.isVisible()
                && ((NotificacionDetalleFragment) fragment).getNotificationId().equals(notificacion.getId())) {
                    cargarFragment(getChildFragmentManager(), NotificacionDetalleFragment.newInstance(notificacion), detailId);
            }
        }
    }
}
