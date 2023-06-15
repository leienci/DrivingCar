package com.test.drivingcar.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 网络请求工具类
 *
 * @author duoma
 * @date 2019/02/22
 */
public class HttpUtils {
    public static final String TAG = "HttpRequest";
    private OkHttpClient mOkhttpClient;
    private Handler mHandler;

    private String method;
    private String json;
    private String url;
    private Map<String, String> headers;
    private Map<String, String> maps;

    private String name;
    private String fileType;
    private String fileDir;

    private String saveDir;

    public static HttpUtils getInstance() {
        return new HttpUtils();
    }

    private HttpUtils() {
        method = "GET";
        json = "";
        url = "";
        headers = new HashMap<>();
        maps = new HashMap<>();
        name = "";
        fileType = "";
        fileDir = "";
        saveDir = "";

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);

        mOkhttpClient = builder.build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static abstract class BaseCallBack {
        public abstract void onError(String e);

        public abstract void onResponse(String body);

        public void onProgress(int progress) {
        }
    }

    public static abstract class BaseHeadCallBack {
        public abstract void onError(String e);

        public abstract void onResponse(String body, Headers headers);
    }

    public HttpUtils url(String url) {
        this.url = url;
        Logger.t(TAG).d(url);
        return this;
    }

    public HttpUtils header(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpUtils get() {
        this.method = "GET";
        return this;
    }

    public HttpUtils post(Map<String, String> maps) {
        this.method = "POST";
        this.maps = maps;
        return this;
    }

    public HttpUtils put(Map<String, String> maps) {
        this.method = "PUT";
        this.maps = maps;
        return this;
    }

    public HttpUtils putJson(String json) {
        this.method = "PUT";
        this.json = json;
        Logger.t(TAG).json(json);
        return this;
    }

    public HttpUtils postJson(String json) {
        this.method = "POST_JSON";
        this.json = json;
        Logger.t(TAG).json(json);
        return this;
    }

    public HttpUtils name(String name) {
        this.name = name;
        return this;
    }

    public HttpUtils fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public HttpUtils fileDir(String fileDir) {
        this.fileDir = fileDir;
        return this;
    }

    public HttpUtils saveDir(String saveDir) {
        this.saveDir = saveDir;
        return this;
    }

    private Call createCall() {
        Request.Builder builder = new Request.Builder().url(url);
        // 添加头部鉴权
        if (headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder.header(key, headers.get(key));
            }
        }

        switch (method) {
            case "GET":
                builder.get();
                break;
            case "POST":
                FormBody.Builder forBodyBuilder = new FormBody.Builder();
                if (maps.size() > 0) {
                    for (String key : maps.keySet()) {
                        forBodyBuilder.add(key, maps.get(key));
                    }
                }
                FormBody formBody = forBodyBuilder.build();

                builder.post(formBody);
                break;
            case "PUT":
//                FormBody.Builder forBodyBuilder2 = new FormBody.Builder();
//                if (maps.size() > 0) {
//                    for (String key : maps.keySet()) {
//                        forBodyBuilder2.add(key, maps.get(key));
//                    }
//                }
//                FormBody formBody2 = forBodyBuilder2.build();
//                builder.put(formBody2);
                RequestBody putBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                builder.put(putBody);
                break;
            case "POST_JSON":
                RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                builder.post(body);
                break;
            default:
                break;
        }

        Request request = builder.build();
        return mOkhttpClient.newCall(request);
    }

    private Call createUploadCall(boolean isProgress, BaseCallBack callBack) {
        MultipartBody.Builder mBuilder = new MultipartBody.Builder();
        mBuilder.setType(MultipartBody.FORM);
        File file = new File(fileDir);

        if (isProgress) {
            // 带进度的上传
            mBuilder.addFormDataPart(name, file.getName(),
                    createRequestBody(MediaType.parse(fileType), file, callBack));
        } else {
            // 不带进度的上传
            mBuilder.addFormDataPart(name, file.getName(),
                    RequestBody.create(MediaType.parse(fileType), file));
        }

        switch (method) {
            case "POST":
                if (maps.size() > 0) {
                    for (String key : maps.keySet()) {
                        mBuilder.addFormDataPart(key, maps.get(key));
                    }
                }
                break;
            case "POST_JSON":
                break;
            default:
                break;
        }

        RequestBody requestBody = mBuilder.build();

        Request.Builder builder = new Request.Builder().url(url);

        // 添加头部鉴权
        if (headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder.header(key, headers.get(key));
            }
        }

        builder.post(requestBody);

        Request request = builder.build();
        return mOkhttpClient.newCall(request);
    }

    private Call createDownloadCall() {
        Request.Builder builder = new Request.Builder().url(url);

        Request request = builder.build();
        return mOkhttpClient.newCall(request);
    }

    /**
     * 发起异步网络请求
     */
    public void start(BaseCallBack callBack) {
        Call call = createCall();
        enqueue(call, callBack);
    }

    /**
     * 发起异步网络请求
     */
    public void startHead(BaseHeadCallBack callBack) {
        Call call = createCall();
        enqueueHead(call, callBack);
    }

    /**
     * 发起同步网络请求
     */
    public void startSync(BaseCallBack callBack) {
        Call call = createCall();
        execute(call, callBack);
    }

    /**
     * 文件上传
     */
    public void startUpload(boolean isProgress, BaseCallBack callBack) {
        Call call = createUploadCall(isProgress, callBack);
        enqueueUpload(call, callBack);
    }

    /**
     * 文件下载
     */
    public void startDownload(BaseCallBack callBack) {
        Call call = createDownloadCall();
        enqueueDownload(call, callBack);
    }

    /**
     * 异步请求
     */
    private void enqueue(Call call, final BaseCallBack callBack) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String eMessage = e.getMessage();

//                Log.e(HttpUtils.TAG, "requestUrl:" + call.request().url().toString() + e.getMessage());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onError("网络异常，请检查网络连接状态！");
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    Log.e(HttpUtils.TAG, "requestUrl:" + call.request().url().toString());
                    final String body = response.body().string();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onResponse(body);
                            }
                        }
                    });
//                }
            }
        });
    }

    /**
     * 异步请求
     */
    private void enqueueHead(Call call, final BaseHeadCallBack callBack) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String eMessage = e.getMessage();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onError(eMessage);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
                    final String body = response.body().string();
                    final Headers headers = response.headers();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onResponse(body, headers);
                            }
                        }
                    });
//                }
            }
        });
    }

    /**
     * 同步请求
     */
    private void execute(Call call, final BaseCallBack callBack) {
        try {
            final Response response = call.execute();

            if (response.isSuccessful()) {
                final String body = response.body().string();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onResponse(body);
                        }
                    }
                });
            }
        } catch (final IOException e) {
            e.printStackTrace();
            final String eMessage = e.getMessage();

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onError(eMessage);
                    }
                }
            });
        }
    }

    /**
     * 异步上传
     */
    private void enqueueUpload(Call call, final BaseCallBack callBack) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String eMessage = e.getMessage();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onError(eMessage);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String body = response.body().string();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onResponse(body);
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 异步下载
     */
    private void enqueueDownload(Call call, final BaseCallBack callBack) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String eMessage = e.getMessage();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onError(eMessage);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    if (callBack != null) {
                        ioDownload(response, callBack);
                    }
                }
            }
        });
    }

    /**
     * 下载流监听
     */
    private void ioDownload(Response response, final BaseCallBack callBack) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = response.body().byteStream();
            final File file = new File(saveDir, url.substring(url.lastIndexOf("/") + 1));
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file);

            long bytetotal = response.body().contentLength();
            long bytesum = 0;

            byte[] buffer = new byte[2048];
            int byteread;
            int oldProgress = 0;

            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);

                final int progress = (int) (bytesum * 100L / bytetotal);

                if (progress != oldProgress) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onProgress(progress);
                        }
                    });
                }
                oldProgress = progress;
            }

            out.flush();

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // 下载完成
                    callBack.onResponse(file.getAbsolutePath());
                }
            });

        } catch (Exception e) {
            callBack.onError(e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传流监听
     */
    private RequestBody createRequestBody(final MediaType contentType, final File file, final BaseCallBack callBack) {
        if (file == null) {
            throw new NullPointerException("content == null");
        }

        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(file);

                    long remaining = contentLength();
                    long bytesum = 0;

                    Buffer buf = new Buffer();
                    long readCount;
                    int oldProgress = 0;

                    while ((readCount = source.read(buf, 2048)) != -1) {
                        bytesum += readCount;
                        sink.write(buf, readCount);

                        final int progress = (int) (bytesum * 100L / remaining);

                        if (progress != oldProgress) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onProgress(progress);
                                }
                            });
                        }
                        oldProgress = progress;
                    }
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }
}
