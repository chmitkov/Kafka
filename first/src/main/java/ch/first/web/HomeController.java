package ch.first.web;

import ch.first.model.Employee;
import com.google.gson.Gson;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class HomeController {


    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    public HomeController(KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    @PostMapping(consumes = "application/json")
    public void post(@RequestBody Employee employee){
        kafkaTemplate.send("topicFromInteliJ", gson.toJson(employee));
    }

    @KafkaListener(topics = {"topicFromInteliJ"})
    public void getDataFromTopic(String employee){
        System.out.println(employee);
    }
}
