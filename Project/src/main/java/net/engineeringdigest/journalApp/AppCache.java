package net.engineeringdigest.journalApp;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.engineeringdigest.journalApp.Repo.ConfigRepo;
import net.engineeringdigest.journalApp.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigRepo config;

    @Getter
    private Map<String, String> cache;

    @PostConstruct
    public void init(){
        cache = new HashMap<>();
        List<Config> configs = config.findAll();
        for(Config c : configs){
            cache.put(c.getKey(), c.getValue());
        }
    }

}
