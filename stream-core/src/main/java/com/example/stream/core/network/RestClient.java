package com.example.stream.core.network;

import android.content.Context;

import com.example.stream.core.network.callback.IError;
import com.example.stream.core.network.callback.IFailure;
import com.example.stream.core.network.callback.IRequest;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.network.callback.RequestCallbacks;
import com.example.stream.core.network.download.DownloadHandler;
import com.example.stream.core.ui.LoadStyle;
import com.example.stream.core.ui.StreamLoader;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by StReaM on 8/13/2017.
 */

public class RestClient {

    private final String URL;
    private final static Map<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final LoadStyle LOADER_STYLE;
    private final File FILE;
    private final String DOWNLOAD_DIR;
    private final String POSTFIX;
    private final String SAVED_FILE_NAME;
    private final Context CONTEXT;

    public RestClient(String url, Map<String, Object> params, IRequest request,
                      ISuccess success, IError error, IFailure failure,
                      RequestBody body, LoadStyle style, File file,
                      String downloadDir, String postfix, String savedFileName,
                      Context context) {
        URL = url;
        PARAMS.putAll(params);
        REQUEST = request;
        SUCCESS = success;
        ERROR = error;
        FAILURE = failure;
        BODY = body;
        LOADER_STYLE = style;
        FILE = file;
        DOWNLOAD_DIR = downloadDir;
        POSTFIX = postfix;
        SAVED_FILE_NAME = savedFileName;
        CONTEXT = context;
    }

    public static RestClientBuilder Builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            StreamLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call =service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.postRaw(URL, BODY);
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody
                        .create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody
                        .Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL,body);
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(REQUEST, SUCCESS, ERROR, FAILURE, LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("parameters not null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if(BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("parameters not null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, SUCCESS, ERROR, FAILURE, DOWNLOAD_DIR,
                POSTFIX, SAVED_FILE_NAME).handleDownload();
    }

}
