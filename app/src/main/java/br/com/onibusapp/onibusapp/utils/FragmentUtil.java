package br.com.onibusapp.onibusapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import br.com.onibusapp.onibusapp.R;

/**
 * Created by diego on 11/07/2018.
 */

public final class FragmentUtil {

    private static FragmentUtil instance;
    private FragmentManager fragmentManager;

    private FragmentUtil(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static FragmentUtil getInstance(FragmentManager fragmentManager) {
        if (instance == null) {
            instance = new FragmentUtil(fragmentManager);
        }
        return instance;
    }

    public void mudarTela(Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.rl_fragment_container, fragment, "mainFrag");
        ft.commit();
    }
}
