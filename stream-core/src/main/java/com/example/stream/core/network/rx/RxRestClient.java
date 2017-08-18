package com.example.stream.core.network.rx;

import android.content.Context;

import com.example.stream.core.network.HttpMethod;
import com.example.stream.core.network.RestCreator;
import com.example.stream.core.ui.loader.LoadStyle;
import com.example.stream.core.ui.loader.StreamLoader;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by StReaM on 8/13/2017.
 */

public class RxRestClient {

    private final String URL;
    private final static WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;
    private final LoadStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    public RxRestClient(String url, WeakHashMap<String, Object> params,
                        RequestBody body, LoadStyle style, File file,
                        Context context) {
        URL = url;
        PARAMS.putAll(params);
        BODY = body;
        LOADER_STYLE = style;
        FILE = file;
        CONTEXT = context;
    }

    public static RxRestClientBuilder Builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;

        if (LOADER_STYLE != null) {
            StreamLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable =service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.postRaw(URL, BODY);
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody
                        .create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody
                        .Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL,body);
            default:
                break;
        }

        return observable;
    }


    public final Observable<String> get(){

        return request(HttpMethod.GET);
    }

    public final Observable<String> post(){
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("parameters not null");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put(){
        if(BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("parameters not null");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete(){
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }

}
