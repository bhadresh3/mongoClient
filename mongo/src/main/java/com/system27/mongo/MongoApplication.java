package com.system27.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MongoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class, args);
	}

	public static MongoDatabase getDatabase(){
		MongoClient mc = new MongoClient("localhost", 59904);
		return mc.getDatabase("test");
	}
	@Override
	public void run(String... args) throws Exception {
		MongoDatabase db = getDatabase();
		MongoCollection<Document> people = db.getCollection("people");
		Document person1 = new Document();
		person1.append("name", "bob");
		person1.append("age", 28);
		person1.append("salary", 2000);
		people.insertOne(person1);
	}
}
