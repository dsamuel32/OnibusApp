package br.com.onibusapp.onibusapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by diego on 05/08/2018.
 */

public final class PermissaoUtil {

    public static final int PERMISSAO_LOCALIZACAO = 1000;

    private PermissaoUtil() {

    }

    public static Boolean verificarPermissoes(Activity activity, String permissao, Integer codigoPermissao) {

        // Here, thisActivity is the current activity
        if (activity.checkSelfPermission(permissao) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (activity.shouldShowRequestPermissionRationale(permissao)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                return true;

            } else {

                // No explanation needed, we can request the permission.

                activity.requestPermissions(new String[]{ permissao }, codigoPermissao);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                return true;
            }

        }
        return false;

    }
}
