package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private QuoteDao quoteDao;

    @Before
    public void setup(){
        quoteDao.deleteAll();

        Quote quote = new Quote();
        quote.setAskPrice(100d);
        quote.setAskSize(14);
        quote.setBidPrice(15.3d);
        quote.setBidSize(12);
        quote.setId("BA");
        quote.setLastPrice(17.3d);
        quoteDao.save(quote);

    }
    @After
    public void delete(){
        quoteDao.deleteAll();
    }

    @Test
    public void findIexQuoteByTicker(){
        String ticker = "AAPL";
        IexQuote iexQuote = quoteService.findIexQuoteByTicker(ticker);
        assertEquals(ticker, iexQuote.getSymbol());
    }

    @Test
    public void updateMarketData(){
        String ticker = "BA";
        quoteService.updateMarketData();
        assertEquals(ticker , quoteService.findIexQuoteByTicker("BA").getSymbol());
        assertNotEquals(17.3d ,quoteService.findIexQuoteByTicker("BA").getLatestPrice());

    }

    @Test
    public void findAllQuotes(){
        String ticker = "BA";
        assertEquals(ticker, quoteService.findAllQuotes().get(0).getTicker());
    }

    @Test
    public void saveQuotes(){
        List<String> tickers = new ArrayList<>(Arrays.asList("AAPL","HD","GE"));
        quoteService.saveQuotes(tickers);
        assertEquals("AAPL",quoteDao.findById("AAPL").get().getTicker());
        assertEquals("HD",quoteDao.findById("HD").get().getTicker());
        assertEquals("GE",quoteDao.findById("GE").get().getTicker());
    }
    @Test
    public void saveQuote(){
        String ticker = "HD";
        quoteService.saveQuote(ticker);
        assertEquals(ticker,quoteDao.findById(ticker).get().getTicker());
    }
    @Test
    public void saveQuoteManual(){
        Quote quote2 = new Quote();
        quote2.setAskPrice(100d);
        quote2.setAskSize(14);
        quote2.setBidPrice(15.3d);
        quote2.setBidSize(12);
        quote2.setId("FB");
        quote2.setLastPrice(17.3d);
       quoteService.saveQuote(quote2);
       assertEquals(quote2.getTicker(),quoteDao.findById("FB").get().getTicker());
    }

}
