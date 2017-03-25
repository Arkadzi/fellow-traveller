package us.fellowtraveller.presentation.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by arkadii on 3/19/17.
 */
public class FilesUtils {
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public static File saveTempFile(Context c, String fileName, int size) throws IOException {
        Bitmap bitmap = ImageUtils.decodeSampledBitmapFromResource(fileName, size);

        File cacheDir = c.getExternalCacheDir();
        if (!cacheDir.exists()) cacheDir.mkdir();

        File result = new File(cacheDir.getPath() + File.separator
                + System.currentTimeMillis() + ".png");
        FileOutputStream fos = new FileOutputStream(result);
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
        fos.close();
        return result;
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }
}
