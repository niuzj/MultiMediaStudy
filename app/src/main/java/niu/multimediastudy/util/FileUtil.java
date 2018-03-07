package niu.multimediastudy.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * Created by Qinlian_niu on 2018/3/6.
 */

public class FileUtil {

    public String TAG = this.getClass().getSimpleName();
    public static String ROOT_PATH = "MultiMediaStudy";

    public String getFilePath(String fileName) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ROOT_PATH + File.separator + fileName;
    }

    public Uri getUri(String fileName, Context context) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ROOT_PATH;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = dirPath + File.separator + fileName;
        File file = new File(filePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, "niu.multimediastudy.fileProvider", file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
