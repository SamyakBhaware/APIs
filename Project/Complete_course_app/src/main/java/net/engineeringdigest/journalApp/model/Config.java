package net.engineeringdigest.journalApp.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter

@Document(collection = "Config")
@Data
@NoArgsConstructor
public class Config {
    private String key;
    private String value;

}
