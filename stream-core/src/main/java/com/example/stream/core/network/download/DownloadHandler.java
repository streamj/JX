package com.example.stream.core.network.download;

import android.os.AsyncTask;

import com.example.stream.core.network.RestCreator;
import com.example.stream.core.network.callback.IError;
import com.example.stream.core.network.callback.IFailure;
import com.example.stream.core.network.callback.IRequest;
import com.example.stream.core.network.callback.ISuccess;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by StReaM on 8/15/2017.
 */

public class DownloadHandler {
    private final String URL;
    private final static WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final String DOWNLOAD_DIR;
    private final String POSTFIX;
    private final String SAVED_FILE_NAME;

    public DownloadHandler(String url, IRequest request, ISuccess success, IError error,
                           IFailure failure, String downloadDir, String postfix,
                           String savedFileName) {
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.DOWNLOAD_DIR = downloadDir;
        this.POSTFIX = postfix;
        this.SAVED_FILE_NAME = savedFileName;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final SaveFileTask saveFileTask = new SaveFileTask(REQUEST, SUCCESS);
                            saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR, POSTFIX, response.body(), SAVED_FILE_NAME);

                            // 判断 asynctask 是否已经结束，否则下载文件不全
                            if (saveFileTask.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            } else {
                                if (ERROR != null) {
                                    ERROR.onError(response.code(), response.message());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
