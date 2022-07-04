package com.hzhangse.prom;


import okhttp3.*;
import org.xerial.snappy.Snappy;

import java.io.IOException;

public class RemoteClient {

    private OkHttpClient client;

    private String writeUrl;

    public RemoteClient(String writeUrl) {
        this.client = new OkHttpClient();
        this.writeUrl = writeUrl;
    }

    public void WriteProto(Prometheus.WriteRequest req) throws IOException {
        byte[] compressed = Snappy.compress(req.toByteArray());
        MediaType mediaType = MediaType.parse("application/x-protobuf");
        RequestBody body = RequestBody.create(mediaType, compressed);
        Request request = new Request.Builder()
            .url(writeUrl)
            .addHeader("Content-Encoding", "snappy")
            .addHeader("User-Agent", "prom-remote-write-java/0.0.1")
            .addHeader("Content-Type", "application/x-protobuf")
            .addHeader("X-Prom-Remote-Write-Java-Version", "0.0.1")
            .post(body)
            .build();
        Response response = client.newCall(request).execute();
        if (response.code()/100 != 2) {
            throw new IOException(
                "expected 200 status code: actual=" + String.valueOf(response.code()) + ", " +
                "body=" + response.message());
        }
    }

}
