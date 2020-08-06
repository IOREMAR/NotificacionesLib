package com.pagatodo.notifications;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Arrays;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<AbstractDialogFragment> fragmentList = Arrays.asList(new ListaNotificacionesFragment(), new ListMensajesFragment());
    private List<String> stringList = Arrays.asList("Notificaciones", "Enrolamiento");

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
