package com.Wether.Temperature.Repository;

import com.Wether.Temperature.model.Temp;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempRepo extends MongoRepository<Temp, String> {
    List<Temp> findByTime(int time);
}
