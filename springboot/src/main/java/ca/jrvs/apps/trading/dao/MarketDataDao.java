package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.JsonUtil;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote,String> {
    private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%S&types=quote&token=";
    private final String IEX_BATCH_URL;
    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
    }


    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));
        if (quotes.size() == 0){
            return Optional.empty();
        }else if (quotes.size() == 1){
            iexQuote = Optional.of(quotes.get(0));
        }else{
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }
        return iexQuote;
    }
    /**
     * Get quote from IEX
     * @param tickers is a list of tickers
     * @return a list of IexQuote objects
     * @throws IllegalArgumentException if any ticker is invalid or tickers is empty
     * @throws DataRetrievalFailureException if http request failed
     */
    @Override
    public List<IexQuote> findAllById(Iterable<String> tickers) {
        int count = (int) StreamSupport.stream(tickers.spliterator(),false).count();
        //convert string iterable to normal string
        String tickersParam = StreamSupport.stream(tickers.spliterator(),false
        ).collect(Collectors.joining(","));

        //build the url
        String url = String.format(IEX_BATCH_URL,tickersParam);
        //get response
        String response;
        try {
            response = executeHttpGet(url).get();
        } catch (DataRetrievalFailureException | IOException e) {
            throw new DataRetrievalFailureException("HTTP request failed", e);
        }


        JSONObject responseJson = new JSONObject(response);
        if (responseJson.length() == 0 || responseJson.length() != count){
            throw new IllegalArgumentException("Invalid ticker");
        }

        List<IexQuote> iexQuotes = new ArrayList<>();

        for (String ticker : tickers){
            JSONObject quoteJson = responseJson.getJSONObject(ticker).getJSONObject("quote");
            try {
                IexQuote iexQuote = JsonUtil.toObjectFromJson(quoteJson.toString(),IexQuote.class);
                iexQuotes.add(iexQuote);
            } catch (IOException e) {
                logger.debug("Unable to covert Create JSON str to Object");
            }
        }
        return iexQuotes;
    }
    /**
     * Execute a get and return http entity/body as a string
     * use EntityUtils.toString to process HTTP entity
     *
     * @param url resource URL
     * @return http response boy or Optional.empty for 404 response
     * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
     */
    private Optional<String> executeHttpGet (String url) throws IOException {
        try(
            CloseableHttpClient httpClient = getHttpClient();
            CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(url))){

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 404){
                return Optional.empty();
            }else if(statusCode != 200){
                throw new DataRetrievalFailureException("Unexpected status code:" + statusCode);
            }
            HttpEntity entity = httpResponse.getEntity();
            return Optional.ofNullable(EntityUtils.toString(entity));
        }catch (IOException e){
            throw new DataRetrievalFailureException("Failed to execute http get", e);
        }

    }

    /**
     * Borrow a Http client from the httpClientConnectionManager
     * @reutrn a httpClient
     */
    private CloseableHttpClient getHttpClient(){
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                //prevent connectionManager shutdown when calling httpClient.close()
                .setConnectionManagerShared(true)
                .build();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> S save(S s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
