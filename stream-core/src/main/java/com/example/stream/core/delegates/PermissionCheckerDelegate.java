package com.example.stream.core.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.stream.core.ui.camera.CameraImageBean;
import com.example.stream.core.ui.camera.RequestCodes;
import com.example.stream.core.ui.camera.StreamCamera;
import com.example.stream.core.ui.scanner.ScannerDelegate;
import com.example.stream.core.util.callback.CallbackManager;
import com.example.stream.core.util.callback.CallbackType;
import com.example.stream.core.util.callback.IGlobalCallback;
import com.example.stream.core.util.log.StreamLogger;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by StReaM on 8/13/2017.
 */
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    // 不是直接调用的方法，只是用来生成代码
    // 由于后续需要 crop，所以附加了读写存储权限，但是在前面的调用中，并没有显式请求，
    // 所以不存在 requestCode
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void startCamera() {
        StreamCamera.start(this);
    }

    // 调用这个
    public void startCameraWithCheck() {
        // 这个类是生成的代码
        PermissionCheckerDelegatePermissionsDispatcher
                .startCameraWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(StreamDelegate delegate) {
        delegate.startForResult(new ScannerDelegate(), RequestCodes.SCAN);
    }

    public void startScanWithCheck(StreamDelegate delegate) {
        PermissionCheckerDelegatePermissionsDispatcher
                .startScanWithCheck(this, delegate);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraDenied() {
        Toast.makeText(getContext(), "权限不足，无法继续进行", Toast.LENGTH_LONG).show();
    }

    // 注解在用于向用户解释为什么需要调用该权限的方法上，只有当第一次请求权限被用户拒绝，下次请求权限之前会调用
    @OnShowRationale({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 生成的类代码
        PermissionCheckerDelegatePermissionsDispatcher
                .onRequestPermissionsResult(this,requestCode,grantResults);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("你需要这些权限来启用功能")
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    StreamLogger.d("fuck dick", resultUri );
                    // 让目标路径直接覆盖原始路径
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400,400)
                            .start(getContext(), this);
                    StreamLogger.d("get here");
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        // 存放剪裁过的图片
                        StreamLogger.d("get sakura ", pickPath);
                        final String cropResultPath = StreamCamera.createCropFile().getPath();
                        StreamLogger.d("get sakura ", cropResultPath);
                        UCrop.of(pickPath, Uri.parse(cropResultPath))
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    @SuppressWarnings("unchecked")
                    final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback != null) {
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }
}
