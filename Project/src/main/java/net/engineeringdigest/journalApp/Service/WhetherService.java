package net.engineeringdigest.journalApp.Service;


import net.engineeringdigest.journalApp.AppCache;
import net.engineeringdigest.journalApp.apiResponse.WhetherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhetherService {

    @Value("${weather_api_key}")
    private String API_KEY;
//    public static final String  API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    private final RestTemplate restTemplate;
    private  final AppCache appCache;

    public WhetherService(RestTemplate restTemplate, AppCache appCache) {
        this.restTemplate = restTemplate;
        this.appCache = appCache;
    }

    public WhetherResponse getWhether(String city){
        String apiUrl = appCache.getCache().get("WEATHER_API_URL");
        String url = apiUrl.replace("API_KEY", API_KEY).replace("CITY", city);
        ResponseEntity<WhetherResponse> exchange = restTemplate
                .exchange(url, HttpMethod.GET, null, WhetherResponse.class);
        return exchange.getBody();
    }

} 
