package us.fellowtraveller.presentation.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import us.fellowtraveller.R;

/**
 * Created by arkadii on 3/19/17.
 */
public class ImageUtils {

    private static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_CODE_PERMISSIONS = 1010;
    private static final int REQUEST_CODE_IMAGE = 1011;

    public static void requestImage(Activity activity) {
        if (hasReadWriteStoragePermission(activity)) {
            startIntent(activity);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);
        }
    }

    private static void startIntent(Activity activity) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (pickPhoto.resolveActivity(activity.getPackageManager()) != null) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string.hint_select_photo)), REQUEST_CODE_IMAGE);
        } else {
            Toast.makeText(activity, R.string.error_gallery_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean hasReadWriteStoragePermission(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean onPermissionRequested(Activity activity, int requestCode) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (hasReadWriteStoragePermission(activity)) {
                startIntent(activity);
            } else {
                Toast.makeText(activity, R.string.warn_user_denied_storage_permission, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }

    public static boolean onActivityResult(int requestCode) {
        return requestCode == REQUEST_CODE_IMAGE;
    }

    @Nullable
    public static Uri fetchImageUri(Intent intent, int resultCode) {
        if (resultCode == Activity.RESULT_OK && intent != null && intent.getData() != null) {
            return intent.getData();
        }
        return null;
    }

    @Nullable
    public static String fetchImagePath(Context context, Intent intent, int resultCode) {
        if (resultCode == Activity.RESULT_OK && intent != null && intent.getData() != null) {
            return FilesUtils.getRealPathFromURI(context, intent.getData());
        }
        return null;
    }
}
