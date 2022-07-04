# promwrite for java language

Prometheus Remote Write java client with minimal dependencies

### build 

```
  # generate Prometheus.java base on prometheus.proto and build package
 ./gradlew clean build  

  # upload to local maven repo  
 ./gradlew uploadArchives
 
  # if want to change the local maven repo path , find below scripts in build.gradle and change it
  repository(url: uri("~/.m2/repository"))
```

### Example Usage

```java

        RemoteClient client = new RemoteClient("http://xxxxx/api/v1/write");
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
        
```
