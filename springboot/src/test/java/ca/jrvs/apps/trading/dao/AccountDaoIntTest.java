package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoIntTest {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TraderDao traderDao;
    private Account savedAccount;
    private Trader savedTrader;
    @Before
    public void insertOne(){
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
        savedAccount.setAmount(10000.0);
        savedAccount.setTraderId(savedTrader.getId());
        accountDao.save(savedAccount);
    }

    @After
    public void deleteOne(){
        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Test
    public void findAllById(){
        List<Account> accounts = Lists.newArrayList(accountDao.findAllById
                (Arrays.asList(savedAccount.getId())));
        assertEquals(1,accounts.size());
        assertEquals(savedAccount.getAmount(),accounts.get(0).getAmount());
    }
    @Test
    public void findByTraderId(){
        Account account = accountDao.findByTraderId(savedTrader.getId()).get();
        assertEquals(account.getAmount(),savedAccount.getAmount());

    }


}
