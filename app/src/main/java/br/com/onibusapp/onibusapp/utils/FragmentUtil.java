package br.com.onibusapp.onibusapp.utils;

import android.os.Bundle;
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
    private Bundle bundle;

    private FragmentUtil(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static FragmentUtil getInstance(FragmentManager fragmentManager) {
        if (instance == null) {
            instance = new FragmentUtil(fragmentManager);
        }
        return instance;
    }


    public FragmentUtil criarBundle() {
        this.bundle = new Bundle();
        return this;
    }
    public FragmentUtil parametros(String key, String value) {
        this.bundle.putString(key, value);
        return this;
    }

    public FragmentUtil parametros(String key, Integer value) {
        this.bundle.putInt(key, value);
        return this;
    }

    public FragmentUtil mudarTela(Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (this.bundle != null) {
            fragment.setArguments(this.bundle);
        }

        ft.replace(R.id.rl_fragment_container, fragment, "mainFrag");
        ft.commit();
        return this;
    }
}
