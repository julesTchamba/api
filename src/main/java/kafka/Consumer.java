package kafka;

import kafka.utils.Json;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {
    public Consumer() {
    }
    public void start() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "test-group");

        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("devglan-test");
        kafkaConsumer.subscribe(topics);
        try{
            while (true){
                ConsumerRecords records = kafkaConsumer.poll(10);
                for (Object obj: records){
                    ConsumerRecord record = (ConsumerRecord)obj;
                    System.out.println(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(), record.partition(), record.value()));
                    sendTransaction((String)record.value());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            kafkaConsumer.close();
        }
    }

    private void sendTransaction(String value) throws IOException {
        String postUrl = "";
        if(value.contains("balance"))
            postUrl  = "http://localhost:8080/balances";
        else
            postUrl  = "http://localhost:8080/transactions";

        HttpClient httpClient    = HttpClientBuilder.create().build();
        HttpPost post          = new HttpPost(postUrl);
        StringEntity postingString = new StringEntity(value);
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);
        System.out.println(response);
        //https://hc.apache.org/httpcomponents-asyncclient-4.1.x/quickstart.html

    }
}