
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PermissionRequester {

    private static final int REQUEST_CODE = 153;

    private final static String[] ACCESS_CONTACTS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };
    private final static String[] STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };
    private final static String[] ALL = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    public enum Group {
        ACCESS_CONTACTS, ALL, STORAGE
    }

    public static int checkSelfPermissions(Context context, Group group) {
        List<String> neededRequests = new ArrayList<>();

        String[] permissions = getPermissions(group);

        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                neededRequests.add(permissions[i]);
            }
        }
        return neededRequests.size();
    }

    public static void requestPermissions(Context context, Group group) {
        String[] permissions = getPermissions(group);
        ActivityCompat.requestPermissions((AppCompatActivity) context, permissions, REQUEST_CODE);
    }
// RETURNS FALSE IF USER CHECKED "Never Ask Again"
    public static boolean showRationale(Context context, Group group) {
        String[] permissions = getPermissions(group);
        boolean show = false;
        for (String str : permissions) {
            show = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, str);
            if (show) {
                break;
            }
        }
        return show;
    }

    private static String[] getPermissions(Group group) {
        String[] permissions;
        if (group == Group.ACCESS_CONTACTS) {
            permissions = ACCESS_CONTACTS;
        } else if (group == Group.STORAGE) {
            permissions = STORAGE;
        } else {
            permissions = ALL;
        }
        return permissions;
    }

}
