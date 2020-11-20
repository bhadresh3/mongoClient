package com.system27.mongo.Repo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.system27.mongo.Model.Person;
import com.system27.mongo.MongoApplication;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepo {

    private final MongoDatabase db = MongoApplication.getDatabase();

    public List<Person> findAll() {
        MongoCollection<Document> collection = db.getCollection("people");
        List<Person> people = new ArrayList<>();
        for(Document document : collection.find()){
            people.add(new Person(
                    document.get("_id").toString(),
                document.getString("name"),
                document.getInteger("salary"),
                document.getInteger("age")));
        }
        return people;
    }

    public void add(Person person) {
        MongoCollection<Document> collection = db.getCollection("people");
        Document p = new Document();
        p.append("name", person.getName());
        p.append("age", person.getAge());
        p.append("salary", person.getSalary());
        try {
            collection.insertOne(p);
        }catch (Exception e){
            throw new RuntimeException("something went wrong" + e.getMessage());
        }
    }

    public void update(Person person) {
        MongoCollection<Document> collection = db.getCollection("people");
        Document p = new Document();
        p.append("name", person.getName());
        p.append("age", person.getAge());
        p.append("salary", person.getSalary());
        try{
            BasicDBObject filter = new BasicDBObject("_id", new ObjectId(person.getId()));
            collection.updateOne(filter, new BasicDBObject("$set", p));
        }catch (Exception e){
            throw new RuntimeException("something went wrong" + e.getMessage());
        }

    }

    public void delete(String id) {
        MongoCollection<Document> collection = db.getCollection("people");
        try{
            BasicDBObject filter = new BasicDBObject("_id", new ObjectId(id));
            collection.deleteOne(filter);
        }catch (Exception e){
            throw new RuntimeException("something went wrong" + e.getMessage());
        }
    }
}
