package com.pagatodo.notifications;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.annotation.IdRes;

public class AbstractDialogFragment extends DialogFragment {

    public static Handler handler;
    public static Runnable runnableCode;

    public static String applicationId;
    public static String tpv;



    public static void initIdentity(String applicationId, String tpv){
        AbstractDialogFragment.applicationId = applicationId;
        AbstractDialogFragment.tpv = tpv;
    }

    public void cargarFragment(FragmentManager fragmentManager, final DialogFragment fragment, final @IdRes int containerId) {
//        final FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.replace(containerId, fragment, fragment.getClass().getSimpleName());
//        transaction.commitAllowingStateLoss();
        fragment.show(fragmentManager, fragment.getClass().getSimpleName());
    }

    public boolean isLandscape(){
        return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

    }



}
