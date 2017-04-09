package us.fellowtraveller.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import us.fellowtraveller.R;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by arkadii on 3/19/17.
 */
public class ImageUtils {

    private static final int REQUEST_CODE_STORAGE_PERMISSIONS = 1010;
    private static final int REQUEST_CODE_CAMERA_PERMISSIONS = 1011;
    private static final int REQUEST_CODE_IMAGE = 1020;
    private static final int REQUEST_CODE_CAMERA_IMAGE = 1021;
    public static final String EXTRA_CAMERA_PATH = "extra_camera_path";
    @Nullable
    private static String photoPath;

    public static void requestImage(Activity activity) {
        if (hasReadWriteStoragePermission(activity)) {
            startIntent(activity);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSIONS);
        }
    }

    public static void requestPhoto(Activity activity) {
        photoPath = null;
        if (hasCameraPermission(activity) && hasReadWriteStoragePermission(activity)) {
            startCameraIntent(activity);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_CAMERA_PERMISSIONS);
        }
    }

    private static void startCameraIntent(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            Uri uri = FilesUtils.getOutputMediaFileUri();
            photoPath = uri.getPath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, REQUEST_CODE_CAMERA_IMAGE);
        } else {
            Toast.makeText(activity, R.string.error_camera_not_available, Toast.LENGTH_SHORT).show();
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

    private static boolean hasCameraPermission(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, CAMERA) == PERMISSION_GRANTED;
    }

    private static boolean hasReadWriteStoragePermission(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED;
    }

    public static boolean onPermissionRequested(Activity activity, int requestCode) {
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSIONS) {
            if (hasReadWriteStoragePermission(activity)) {
                startIntent(activity);
            } else {
                Toast.makeText(activity, R.string.warn_user_denied_storage_permission, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (requestCode == REQUEST_CODE_CAMERA_PERMISSIONS) {
            if (!hasCameraPermission(activity)) {
                Toast.makeText(activity, R.string.warn_user_denied_camera_permission, Toast.LENGTH_SHORT).show();
            } else if (!hasReadWriteStoragePermission(activity)) {
                Toast.makeText(activity, R.string.warn_user_denied_storage_permission, Toast.LENGTH_SHORT).show();
            } else {
                startCameraIntent(activity);
            }
            return true;
        }
        return false;
    }

    public static boolean onActivityResult(int requestCode) {
        return requestCode == REQUEST_CODE_IMAGE || requestCode == REQUEST_CODE_CAMERA_IMAGE;
    }

//    @Nullable
//    public static Uri fetchImageUri(Intent intent, int resultCode) {
//        if (resultCode == Activity.RESULT_OK && intent != null && intent.getData() != null) {
//            return intent.getData();
//        } else if (resultCode == Activity.RESULT_OK) {
//            if (intent != null) {
//                Log.e("intent", String.valueOf(intent.getData()));
//            }
//        }
//        return null;
//    }

    @Nullable
    public static String fetchImagePath(Context context, Intent intent, int resultCode, int requestCode) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && intent != null && intent.getData() != null) {
            return FilesUtils.getRealPathFromURI(context, intent.getData());
        } else if (requestCode == REQUEST_CODE_CAMERA_IMAGE && resultCode == Activity.RESULT_OK && photoPath != null) {
            String photoPath = ImageUtils.photoPath;
            ImageUtils.photoPath = null;
            return photoPath;
        }
        return null;
    }


    public static Bitmap decodeSampledBitmapFromResource(String file, int maxSize) {

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        options.inSampleSize = calculateInSampleSize(options, maxSize, maxSize);

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(file, options);
        Log.e("photo", String.format("was:%d*%d now:%d*%d", outWidth, outHeight, bitmap.getWidth(), bitmap.getHeight()));

        return bitmap;
    }


    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
