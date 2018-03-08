package niu.multimediastudy.chapter2;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import niu.multimediastudy.R;
import niu.multimediastudy.base.BaseActivity;
import niu.multimediastudy.databinding.ActivityChapter2Section1Binding;
import niu.multimediastudy.util.CameraParameterUtil;

/**
 * Created by Qinlian_niu on 2018/3/8.
 */

public class Section1Activity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback {

    private final String TAG = this.getClass().getSimpleName();
    private final int CHECK_CAMERA = 1;
    private CameraParameterUtil mParameterUtil;

    ActivityChapter2Section1Binding mBinding;
    //通过SurfaceHolder来监控SurfaceView
    SurfaceHolder mSurfaceHolder;
    Camera mCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chapter2_section1);
        mParameterUtil = new CameraParameterUtil(this);
        mSurfaceHolder = mBinding.surfaceview.getHolder();
        mSurfaceHolder.addCallback(this);
        mBinding.btTakePicture.setOnClickListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (checkPermission(Manifest.permission.CAMERA, CHECK_CAMERA)) {
            whenSurfaceCreated();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    private void whenSurfaceCreated() {
        //这里默认打开的是后置摄像头
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);

            Camera.Parameters parameters = mCamera.getParameters();
            mParameterUtil.setCameraDisplayOrientation(this, parameters, mCamera);
            mParameterUtil.setFocusMode(parameters);
            mParameterUtil.setColorEffect(parameters);
            mParameterUtil.setPreviewSize(parameters);
            mCamera.setParameters(parameters);

            //其他可能用到的回调方法
            /*
            mCamera.setPreviewCallback(null);//该方法重要
            mCamera.setPreviewCallbackWithBuffer(null);
            mCamera.setAutoFocusMoveCallback(null);
            mCamera.setOneShotPreviewCallback(null);
            mCamera.setErrorCallback(null);
            mCamera.setZoomChangeListener(null);
            */


            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            mCamera.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHECK_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            whenSurfaceCreated();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btTakePicture:
                if (mCamera != null) {
                    mCamera.takePicture(null, null, null, this);
                }
                break;
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, System.currentTimeMillis() + "Title");
        contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.WIDTH, mParameterUtil.previewHeight);
        contentValues.put(MediaStore.Images.Media.HEIGHT, mParameterUtil.previewWidth);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.Images.Media.SIZE, data.length);
        Uri insertUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        OutputStream outputStream = null;
        try {
            outputStream = getContentResolver().openOutputStream(insertUri);
            outputStream.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mCamera.startPreview();

    }
}
