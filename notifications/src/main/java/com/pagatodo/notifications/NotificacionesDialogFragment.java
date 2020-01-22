package com.pagatodo.notifications;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import com.pagatodo.notifications.databinding.FragmentLibDialogNotificacionesBinding;

public class NotificacionesDialogFragment extends AbstractDialogFragment {

    //----------UI-------------------------------------------------------
    private FragmentLibDialogNotificacionesBinding binding;
    private NotificacionIconFragment notificacionIconFragment;
    //----- Inst ----------------------------------------------------------

    //----- Var ----------------------------------------------------------
    int detailId;
    private boolean isLandScape;

    public void loadFragmentLista() {
        binding.configMenuDetail.setVisibility(isLandScape ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = isLandscape();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        initUI(inflater, container);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI(final LayoutInflater inflater, final ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lib_dialog_notificaciones, container, false);
        detailId = isLandScape ? R.id.config_menu_detail : R.id.config_menu_items;
        binding.configMenuDetail.setVisibility(isLandScape ? View.VISIBLE : View.GONE);

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tablayout.setupWithViewPager(binding.viewPager);

        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                PreferenceManager.putNotificationTimestamp(getActivity());
                return false;
            }
        });

        binding.ivCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                dismiss();
            }
        });
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void onStart() {
        super.onStart();
        final Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            binding.getRoot().setLayoutParams(params);
        }
    }

    public void setNotificacionIconFragment(final NotificacionIconFragment notificacionIconFragment) {
        this.notificacionIconFragment = notificacionIconFragment;
    }

    public void seleccionaNotificacion(final Notificacion notificacion) {
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
