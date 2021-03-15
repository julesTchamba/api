package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Properties;
import java.util.Random;

public class Producer {
    private BigDecimal currentBalance = new BigDecimal(0.0);
    private String accountNumber = "";
    public Producer(BigDecimal initBalance,String accountNumber){
        this.currentBalance = initBalance;
        this.accountNumber = accountNumber;
    }

    public void send(String type, int k) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        try{
            kafkaProducer.send(new ProducerRecord("devglan-test", Integer.toString(k), getMessage(type) ));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            kafkaProducer.close();
        }
    }

    private String getMessage(String type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("accountNumber", this.accountNumber);
        //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
        if(type.equals("transaction")){
            String typeTransaction = getType();
            BigDecimal amount = new BigDecimal((Math.random() * 49 + 1)* 100.23);
            if(typeTransaction.equals("DEPOSIT"))
                this.currentBalance = this.currentBalance.add(amount);
            else{
                if(this.currentBalance.compareTo(amount) == -1)
                     amount = new BigDecimal(0.0);
                this.currentBalance = this.currentBalance.subtract(amount);
            }

            json.put("transactionTs", getTime());
            json.put("type", typeTransaction);
            json.put("amount", amount);
        } else {
            json.put("lastUpdateTimestamp", getTime());
            json.put("balance", this.currentBalance);
        }

        return json.toString();
    }

    private String getType() {
        if(this.currentBalance.compareTo(new BigDecimal(1)) == -1)
             return "DEPOSIT";
        double val =  Math.random() * 2 + 1;
        return (val < 1.7) ? "DEPOSIT":"WITHDRAW";
    }

    private Instant getTime(){
        Instant instant1 = Instant.now();
        return Timestamp.from(instant1).toInstant();
    }
}