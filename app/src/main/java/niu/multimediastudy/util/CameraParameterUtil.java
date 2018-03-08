package niu.multimediastudy.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by Qinlian_niu on 2018/3/8.
 */

public class CameraParameterUtil {

    private final String TAG = this.getClass().getSimpleName();
    public int screenWidth;
    public int screenHeight;
    public int previewWidth;
    public int previewHeight;

    public CameraParameterUtil(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.widthPixels;
        screenWidth = displayMetrics.heightPixels;
        Log.e(TAG, "屏幕尺寸：" + screenWidth + "x" + screenHeight);
    }

    /**
     * 设置摄像头角度
     * 官方文档里有另一种设置方式
     * https://developer.android.com/reference/android/hardware/Camera.html#setDisplayOrientation(int)
     *
     * @param activity
     * @param parameters
     * @param camera
     */
    public void setCameraDisplayOrientation(Activity activity, Camera.Parameters parameters, Camera camera) {
        if (activity.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "portrait");
            //该方法并不实际旋转图片，只是在EXIF中指定该图片应该旋转90度
            parameters.setRotation(90);
            camera.setDisplayOrientation(90);
        } else {
            parameters.set("orientation", "landscape");
            parameters.setRotation(0);
            camera.setDisplayOrientation(0);
        }
    }

    /**
     * 设置对焦模式
     * auto、macro、infinity、continuous-picture、continuous-video、manual、fullscan
     *
     * @param parameters
     */
    public void setFocusMode(Camera.Parameters parameters) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        for (String focusMode : supportedFocusModes) {
            Log.e(TAG, "focusMode == " + focusMode);
        }
        parameters.setFocusMode("auto");
    }

    /**
     * 设置曝光度，应该先调用getMinExposureCompensation()、getMaxExposureCompensation()确定范围
     *
     * @param parameters
     */
    public void setExposureMode(Camera.Parameters parameters) {
        int minExposureCompensation = parameters.getMinExposureCompensation();
        int maxExposureCompensation = parameters.getMaxExposureCompensation();
        Log.e(TAG, "曝光补偿范围：" + minExposureCompensation + " ===> " + maxExposureCompensation);
        parameters.setExposureCompensation(1);
    }

    /**
     * 设置白平衡模式
     * auto、incandescent(白炽灯)、fluorescent(荧光灯)、warm-fluorescent(温色荧光灯)、
     * daylight(日光)、cloudy-daylight(阴天)、twilight(黄昏)、shade(阴影)
     *
     * @param parameters
     */
    public void setWhiteBalance(Camera.Parameters parameters) {
        List<String> supportedWhiteBalance = parameters.getSupportedWhiteBalance();
        for (String whiteBalance : supportedWhiteBalance) {
            Log.e(TAG, "whiteBalance == " + whiteBalance);
        }
        parameters.setWhiteBalance("auto");
    }

    /**
     * 变焦，0～maxZoom
     *
     * @param parameters
     * @param value
     */
    public void setZoom(Camera.Parameters parameters, int value) {
        int maxZoom = parameters.getMaxZoom();
        Log.e(TAG, "maxZoom == " + maxZoom);
        if (value > maxZoom) {
            value = maxZoom;
        } else if (value < 0) {
            value = 0;
        }
        parameters.setZoom(value);
    }

    /**
     * 设置特效
     * none、mono、negative(底片)、sepia(深褐色)、aqua(浅绿色)、whiteboard(白底)、blackboard(黑底)
     *
     * @param parameters
     */
    public void setColorEffect(Camera.Parameters parameters) {
        List<String> supportedColorEffects = parameters.getSupportedColorEffects();
        for (String colorEffect : supportedColorEffects) {
            Log.e(TAG, "colorEffect == " + colorEffect);
        }
        parameters.setColorEffect(Camera.Parameters.EFFECT_WHITEBOARD);
    }

    /**
     * 设置情境模式
     *
     * @param parameters
     */
    public void setSceneMode(Camera.Parameters parameters) {
        List<String> supportedSceneModes = parameters.getSupportedSceneModes();
        for (String sceneMode : supportedSceneModes) {
            Log.e(TAG, "sceneMode == " + sceneMode);
        }
        parameters.setSceneMode(Camera.Parameters.SCENE_MODE_ACTION);
    }

    /**
     * 设置抗条带
     *
     * @param parameters
     */
    public void setAntiBinding(Camera.Parameters parameters) {
        List<String> supportedAntibanding = parameters.getSupportedAntibanding();
        for (String antiBinding : supportedAntibanding) {
            Log.e(TAG, "antiBinding == " + antiBinding);
        }
        parameters.setAntibanding(Camera.Parameters.ANTIBANDING_AUTO);
    }

    /**
     * 设置预览大小
     * 1、固定SurfaceView大小，根据比例来确定预览大小
     * 2、先确定预览比例，再选择合适的预览大小，最后调整SurfaceView大小
     * 如果SurfaceView与预览尺寸的比例不同，旋转时画面会扭曲
     *
     * @param parameters
     */
    public void setPreviewSize(Camera.Parameters parameters) {
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (int i = 0; i < supportedPreviewSizes.size(); i++) {
            Camera.Size size = supportedPreviewSizes.get(i);
            Log.e(TAG, "支持的预览大小：" + size.width + "x" + size.height);
            if (size.width * screenHeight == size.height * screenWidth
                    && (size.width > previewWidth && size.height > previewHeight)
                    && (size.width <= screenWidth && size.height <= screenHeight)) {
                previewWidth = size.width;
                previewHeight = size.height;
            }
        }
        if (previewWidth == 0 || previewHeight == 0) {
            Log.e(TAG, "没有与显示区域匹配的预览尺寸");
        } else {
            parameters.setPreviewSize(previewWidth, previewHeight);
        }
        Log.e(TAG, "previewSize:" + previewWidth + "x" + previewHeight);
    }

}
