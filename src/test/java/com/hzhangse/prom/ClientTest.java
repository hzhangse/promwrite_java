// Copyright (c) 2019 Uber Technologies, Inc.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the 'Software'), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.hzhangse.prom;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ClientTest {
    @Test public void newClient() {
        RemoteClient client = new RemoteClient("http://localhost:7201/api/v1/prom/remote/write");
        assertTrue("client should not be null", client != null);
    }


    public static void main(String[] args) {
        RemoteClient client = new RemoteClient("http://10.1.2.61:30003/api/v1/write");


        Prometheus.Label label1 = Prometheus.Label.newBuilder().setName("__name__").setValue("hellobaby2").build();
        Prometheus.Label label2 = Prometheus.Label.newBuilder().setName("customer_label_a").setValue("customer_label_a").build();

        Prometheus.Sample sample =  Prometheus.Sample.newBuilder().setValue(45677).setTimestamp(new java.util.Date().getTime()).build();
        Prometheus.TimeSeries timeSeries = Prometheus.TimeSeries.newBuilder().addLabels(label1).addLabels(label2)
                .addSamples(sample)
                .build();
        Prometheus.WriteRequest req = Prometheus.WriteRequest.newBuilder().addTimeseries(timeSeries).build();
        try {

            client.WriteProto(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
