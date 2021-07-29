package com.example.AzureTest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding({Source.class,Sink.class})
@SpringBootApplication
public class AzureTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzureTestApplication.class, args);
	}

}

@RestController
class AzureServiceBusDemo{
	private final MessageChannel output;
	
	public AzureServiceBusDemo(Source source) {
		this.output=source.output();
	}
	
	
	@GetMapping("/send")
	void send() {
		org.springframework.messaging.Message<String> message=MessageBuilder
				.withPayload("This is my first program")
				.setHeader("Content-Type","application/json")
				.build();
				
		
	}
	
	
	
}



@Component
class MessageListener{
	
	@StreamListener(Sink.INPUT)
	public void consume(Message<String> message) {
		System.out.println("message received-------");
		String payload=message.getPayload();
		message.getHeaders().forEach((d,v)->System.out.println(d+" "+v));
	}
	
	
}