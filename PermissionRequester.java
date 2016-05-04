
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

    public enum Group {
        ACCESS_CONTACTS {
            public String[] get() {
                return new String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS};
            }
        },
        STORAGE {
            public String[] get() {
                return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
            }
        };
            public abstract String[] get();
    }

    public static int checkSelfPermissions(Context context, Group group) {
        List<String> neededRequests = new ArrayList<>();

        for (String permission : group.get()) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                neededRequests.add(permission);
            }
        }
        return neededRequests.size();
    }

    public static void requestPermissions(Context context, Group group) {
        ActivityCompat.requestPermissions((AppCompatActivity) context, group.get(), REQUEST_CODE);
    }

    public static boolean showRationale(Context context, Group group) {
        boolean show = false;
        for (String str : group.get()) {
            show = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, str);
            if (show) {
                break;
            }
        }
        return show;
    }

}
