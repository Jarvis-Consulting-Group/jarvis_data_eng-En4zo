package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.assertj.core.util.Lists;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {
    @Autowired
    private SecurityOrderDao securityOrderDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TraderDao traderDao;
    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;
    private SecurityOrder savedOrder;
    private Account savedAccount;
    private Trader savedTrader;
    @Before
    public void insertOne(){
        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setId("FB");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);

        savedTrader = new Trader();
        LocalDate dateOfBirth = LocalDate.of(1999,5,4);
        savedTrader.setCountry("Canada");
        savedTrader.setDob(dateOfBirth);
        savedTrader.setEmail("andre@gmail.com");
        savedTrader.setFirstName("enzo");
        savedTrader.setId(1);
        savedTrader.setLastName("M");
        traderDao.save(savedTrader);

        savedAccount = new Account();
        savedAccount.setId(1);
        savedAccount.setAmount(1000000.0);
        savedAccount.setTraderId(1);
        accountDao.save(savedAccount);

        savedOrder = new SecurityOrder();
        savedOrder.setId(1);
        savedOrder.setNotes("This is a test notes");
        savedOrder.setAccountId(savedAccount.getId());
        savedOrder.setPrice(101.0);
        savedOrder.setSize(10);
        savedOrder.setStatus(SecurityOrder.Status.FILLED);
        savedOrder.setTicker("FB");
        securityOrderDao.save(savedOrder);
    }

    @After
    public void deleteAll(){
        securityOrderDao.deleteAll();
        accountDao.deleteAll();
        traderDao.deleteAll();
        quoteDao.deleteAll();
    }

    @Test
    public void findAllById(){
        List<SecurityOrder> orders = Lists.newArrayList(securityOrderDao.findAllById(Arrays.asList(savedOrder.getAccountId())));
        assertEquals(1,orders.size());
        assertEquals(savedOrder.getSize(),orders.get(0).getSize());

    }

}
