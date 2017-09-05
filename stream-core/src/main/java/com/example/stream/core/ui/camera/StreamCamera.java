package com.example.stream.core.ui.camera;

import android.net.Uri;

import com.example.stream.core.delegates.PermissionCheckerDelegate;
import com.example.stream.core.util.file.FileUtil;

/**
 * Created by StReaM on 9/5/2017.
 */

public class StreamCamera {

    public static Uri createCropFile() {
        return Uri.parse(FileUtil
                .createFile("crop_image", FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).startCameraDialog();
    }
}
