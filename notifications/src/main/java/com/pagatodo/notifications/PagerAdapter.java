package com.pagatodo.notifications;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Arrays;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    //    private List<AbstractDialogFragment> fragmentList = Arrays.asList(new ListaNotificacionesFragment(), new ListMensajesFragment());
//    private List<String> stringList = Arrays.asList("Notificaciones", "Enrolamiento");
    private List<ListaNotificacionesFragment> fragmentList = Arrays.asList(new ListaNotificacionesFragment());
    private List<String> stringList = Arrays.asList("Notificaciones");

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
