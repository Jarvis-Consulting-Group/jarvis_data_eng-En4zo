package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.controller.QuoteController;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
public class AppConfig {
    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public MarketDataConfig marketDataConfig(){
        MarketDataConfig config = new MarketDataConfig();
        config.setHost("https://cloud.iexapis.com/v1/");
        config.setToken(System.getenv("IEX_PUB_TOKEN"));
        return config;
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }

}
