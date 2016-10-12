
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

  static final int REQUEST_CODE_LOCATION = 153;
    public static final int REQUEST_CODE_READ_CONTACTS = 155;
    public static final int REQUEST_CODE_CAMERA = 157;

    public enum Group {
        LOCATION {
            public String[] get() {
                return new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};
            }
        },
        CAMERA {
            @Override
            public String[] get() {
                return new String[]{Manifest.permission.CAMERA};
            }
        },
         READ_CONTACTS {
            public String[] get() {
                return new String[]{
                        Manifest.permission.READ_CONTACTS};
            }
        };

        public abstract String[] get();
    }

    public static List<String> checkSelfPermissions(Context context, Group group) {
        List<String> neededRequests = new ArrayList<>();

        for (String permission : group.get()) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                neededRequests.add(permission);
            }
        }
        return neededRequests;
    }

    public static void requestPermissions(Context context, Group group, int requestCode) {
        List<String> perms = checkSelfPermissions(context, group);
        if (perms.size() > 0)
            ActivityCompat.requestPermissions((AppCompatActivity) context, toArray(perms), requestCode);
    }

    public static void requestFragmentPermissions(Fragment fragment, Group group, int code) {
        List<String> perms = checkSelfPermissions(fragment.getActivity(), group);
        if (perms.size() > 0)
            FragmentCompat.requestPermissions(fragment, toArray(perms), code);
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
     
    private static String[] toArray(List<String> list) {
        String[] arr = new String[list.size()];
        return list.toArray(arr);

    }

}
