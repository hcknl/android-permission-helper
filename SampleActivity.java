
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SampleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int neededRequestSize = PermissionRequester.checkSelfPermissions(SampleActivity.this, PermissionRequester.Group.ACCESS_CONTACTS);
            if (neededRequestSize == 0) {
                runApp();
            } else {
                PermissionRequester.requestPermissions(SampleActivity.this, PermissionRequester.Group.ACCESS_CONTACTS);
            }
        } else {
            runApp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int grantedPermissions = 0;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions++;
            }
        }
        if (grantedPermissions == permissions.length) {
            runApp();
        } else {
            // NOT ALL PERMISSIONS GRANTED
        }
    }

    private void runApp() {
        // DO STUFF HERE
    }

}
