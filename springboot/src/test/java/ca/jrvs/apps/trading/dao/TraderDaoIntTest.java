package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
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
public class TraderDaoIntTest {
    @Autowired
    private TraderDao traderDao;
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
    }

    @After
    public void deleteOne(){
        traderDao.deleteAll();
    }

    @Test
    public void findAllById(){
        List<Trader> traders = Lists.newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getId())));
        assertEquals(1,traders.size());
        assertEquals(savedTrader.getCountry(),traders.get(0).getCountry());

    }


}
