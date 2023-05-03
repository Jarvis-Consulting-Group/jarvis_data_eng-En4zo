package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

    private TraderAccountView savedView;
    private Trader savedTrader;
    @Autowired
    private TraderAccountService traderAccountService;
    @Autowired
    private TraderDao traderDao;
    @Autowired
    private AccountDao accountDao;


    @Before
    public void setup(){
        savedTrader = new Trader();
        LocalDate dateOfBirth = LocalDate.of(1999,5,4);
        savedTrader.setCountry("Canada");
        savedTrader.setDob(dateOfBirth);
        savedTrader.setEmail("andre@gmail.com");
        savedTrader.setFirstName("enzo");
        savedTrader.setId(1);
        savedTrader.setLastName("M");
        savedView = traderAccountService.createTraderAndAccount(savedTrader);
    }

    @After
    public void delete(){
        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Test
    public void createTraderAndAccount(){

        assertEquals(savedView.getAccount().getTraderId(),savedView.getTrader().getId());
        assertEquals(savedView.getAccount().getAmount(),Double.valueOf(0.0));
    }

    @Test
    public void deposit(){

        Account account = traderAccountService.deposit(savedView.getTrader().getId(),105500.0d);
        assertEquals(accountDao.findByTraderId(savedView.getTrader().getId()).get().getAmount(),account.getAmount());
    }
    @Test
    public void withdraw(){

        traderAccountService.deposit(savedView.getTrader().getId(),105500.0d);
        Account account = traderAccountService.withdraw(savedView.getTrader().getId(), 5500.0d);
        assertEquals(account.getAmount(),Double.valueOf(100000));
    }
    @Test
    public void deleteTraderById(){
        Trader savedTrader2 = new Trader();
        LocalDate dateOfBirth = LocalDate.of(1999,5,4);
        savedTrader2.setCountry("China");
        savedTrader2.setDob(dateOfBirth);
        savedTrader2.setEmail("andre@gmail.com");
        savedTrader2.setFirstName("javk");
        savedTrader2.setId(2);
        savedTrader2.setLastName("dfdf");
        TraderAccountView savedView2 = traderAccountService.createTraderAndAccount(savedTrader2);
        traderAccountService.deleteTraderById(savedView2.getTrader().getId());
        assertEquals(traderDao.existsById(savedView2.getTrader().getId()), false);
    }

}
