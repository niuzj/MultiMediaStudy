package niu.multimediastudy.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

import niu.multimediastudy.R;

/**
 * Created by Qinlian_niu on 2018/3/6.
 */

public class FileUtil {

    public String TAG = this.getClass().getSimpleName();
    public static String ROOT_PATH = "MultiMediaStudy";

    public String getFilePath(String fileName) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ROOT_PATH + File.separator + fileName;
    }

    /**
     * 获取根目录
     *
     * @return
     */
    public String getRootPath() {
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ROOT_PATH + File.separator;
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return rootPath;
    }

    /**
     * 获取路径的Uri，在N以上，权限更紧了
     *
     * @param fileName
     * @param context
     * @return
     */
    public Uri getUri(String fileName, Context context) {
        String filePath = getRootPath() + fileName;
        File file = new File(filePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, "niu.multimediastudy.fileProvider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 获取raw文件夹下的Uri
     *
     * @param resId
     * @param context
     * @return
     */
    public Uri getUriFromRaw(int resId, Context context) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
    }

    public void printFile() {
        String rootPath = getRootPath();
        File file = new File(rootPath);
        String[] list = file.list();
        if (list.length > 0) {
            for (String s : list) {
                Log.e(TAG, s);
            }
        }
    }


}
