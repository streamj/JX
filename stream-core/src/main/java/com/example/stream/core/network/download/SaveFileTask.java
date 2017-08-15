package com.example.stream.core.network.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.stream.core.app.StreamCore;
import com.example.stream.core.network.callback.IRequest;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by StReaM on 8/15/2017.
 */

public class SaveFileTask extends AsyncTask<Object, Void , File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... objects) {
        String downloadDir = (String) objects[0];
        String postfix = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String savedFilename = (String) objects[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "downloads";
        }
        if (postfix == null || postfix.equals("")) {
            postfix = "";
        }
        if (savedFilename == null) {
            return FileUtil.writeToDisk(is, downloadDir, postfix.toUpperCase(), postfix);
        } else {
            return FileUtil.writeToDisk(is, downloadDir,savedFilename);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getFilePostifx(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            StreamCore.getApplicationContext().startActivity(install);
        }
    }
}
