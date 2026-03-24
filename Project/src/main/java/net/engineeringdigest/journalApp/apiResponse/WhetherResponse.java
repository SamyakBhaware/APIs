package net.engineeringdigest.journalApp.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WhetherResponse {

    private Current current;

    @Getter
    @Setter
    public static class Current{
        private String observation_time;
        @JsonProperty("temperature")
        private int temperature;
        public int weather_code;
        public ArrayList<String> weather_icons;
        public ArrayList<String> weather_descriptions;
        @JsonProperty("wind_speed")
        private int wind_speed;
        public int wind_degree;
        public String wind_dir;
        public int pressure;
        public int precip;
        public int humidity;
        public int cloudcover;
        @JsonProperty("feelslike")
        private int feelslike;

    }

}
