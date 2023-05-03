package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {
    @Autowired
    private QuoteDao quoteDao;
    private Quote savedQuote = new Quote();

    @Before
    public void insertOne(){
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setId("aapl");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);

    }

    @After
    public void deleteOne(){
        quoteDao.deleteById(savedQuote.getId());
        quoteDao.deleteAll();
    }

    @Test
    public void count(){
        assertEquals(quoteDao.count(),1);
    }

    @Test
    public void findAll(){
        Iterable<Quote> all = quoteDao.findAll();
        Iterator<Quote> iterator = all.iterator();
        if (iterator.hasNext()){
            Quote firstQuote = iterator.next();
            assertEquals(firstQuote.getId(),"aapl");
        }else {
            fail();
        }


    }

    @Test
    public void save(){
        Double askPrice = 11.1d;
        savedQuote.setAskPrice(askPrice);
        quoteDao.save(savedQuote);

        Quote testQuote = new Quote();
        testQuote.setAskPrice(100d);
        testQuote.setAskSize(14);
        testQuote.setBidPrice(15.3d);
        testQuote.setBidSize(12);
        testQuote.setId("tesla");
        testQuote.setLastPrice(17.3d);
        quoteDao.save(testQuote);

        assertEquals(quoteDao.findById("aapl").get().getAskPrice(),savedQuote.getAskPrice());
        assertEquals(quoteDao.findById("tesla").get().getBidPrice(),testQuote.getBidPrice());
    }

    @Test
    public void saveAll(){
        Quote testQuote1 = new Quote();
        testQuote1.setAskPrice(110d);
        testQuote1.setAskSize(15);
        testQuote1.setBidPrice(15.3d);
        testQuote1.setBidSize(13);
        testQuote1.setId("fb");
        testQuote1.setLastPrice(17.3d);

        Quote testQuote2 = new Quote();
        testQuote2.setAskPrice(100d);
        testQuote2.setAskSize(14);
        testQuote2.setBidPrice(15.3d);
        testQuote2.setBidSize(12);
        testQuote2.setId("bk");
        testQuote2.setLastPrice(17.3d);

        Quote testQuote3 = new Quote();
        testQuote3.setAskPrice(100d);
        testQuote3.setAskSize(14);
        testQuote3.setBidPrice(15.3d);
        testQuote3.setBidSize(12);
        testQuote3.setId("dc");
        testQuote3.setLastPrice(17.3d);

        List<Quote> quotes = new ArrayList<>(Arrays.asList(testQuote1,testQuote2,testQuote3));
        quoteDao.saveAll(quotes);

        assertEquals(quoteDao.findById("fb").get().getAskPrice(), testQuote1.getAskPrice());
        assertEquals(quoteDao.findById("fb").get().getBidPrice(),testQuote1.getBidPrice());
        assertEquals(quoteDao.findById("fb").get().getBidSize(),testQuote1.getBidSize());

        assertEquals(quoteDao.findById("bk").get().getAskPrice(), testQuote2.getAskPrice());
        assertEquals(quoteDao.findById("bk").get().getBidPrice(),testQuote2.getBidPrice());
        assertEquals(quoteDao.findById("bk").get().getBidSize(),testQuote2.getBidSize());

        assertEquals(quoteDao.findById("dc").get().getAskPrice(), testQuote3.getAskPrice());
        assertEquals(quoteDao.findById("dc").get().getBidPrice(),testQuote3.getBidPrice());
        assertEquals(quoteDao.findById("dc").get().getBidSize(),testQuote3.getBidSize());
    }

    @Test
    public void findById(){
        Quote quote = quoteDao.findById("aapl").get();
        assertEquals(quote.getAskPrice(),savedQuote.getAskPrice());
        assertEquals(quote.getBidSize(),savedQuote.getBidSize());
        assertEquals(quote.getBidPrice(),savedQuote.getBidPrice());
        assertEquals(quote.getLastPrice(),savedQuote.getLastPrice());

    }

    @Test
    public void existsById(){
        assertEquals(quoteDao.existsById("aapl"),true);

    }

    @Test
    public void deleteById(){
        quoteDao.deleteById("fb");
        assertEquals(quoteDao.existsById("fb"),false);
    }
}
