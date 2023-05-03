package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;




@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {

    @Mock
    private TraderDao traderDao;
    @Mock
    private PositionDao positionDao;
    @Mock
    private AccountDao accountDao;
    @Mock
    private QuoteDao quoteDao;

    @InjectMocks
    private DashboardService dashboardService;

    private Quote quote;

    private Account account;
    private Trader trader;
    private Position position;
    @Before
    public void setup(){

        Quote savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10d);
        savedQuote.setBidSize(10);
        savedQuote.setId("FB");
        savedQuote.setLastPrice(10d);
        quote = savedQuote;


        Account savedAccount = new Account();
        savedAccount.setId(1);
        savedAccount.setAmount(1000000.0);
        savedAccount.setTraderId(1);
        account = savedAccount;


        Trader savedTrader = new Trader();
        LocalDate dateOfBirth = LocalDate.of(1999,5,4);
        savedTrader.setCountry("Canada");
        savedTrader.setDob(dateOfBirth);
        savedTrader.setEmail("andre@gmail.com");
        savedTrader.setFirstName("enzo");
        savedTrader.setId(1);
        savedTrader.setLastName("M");
        trader = savedTrader;

        Position savedPosition = new Position();
        savedPosition.setPosition(10);
        savedPosition.setTicker("FB");
        savedPosition.setAccountId(savedAccount.getId());
        position = savedPosition;
    }

    @Test
    public void getTraderAccount(){
        when(accountDao.findByTraderId(trader.getId())).thenReturn(Optional.of(account));
        when(traderDao.findById(trader.getId())).thenReturn(Optional.of(trader));

        TraderAccountView traderAccountView = dashboardService.getTraderAccount(trader.getId());

        assertEquals(traderAccountView.getAccount().getAmount(),Double.valueOf(1000000));
        assertEquals(traderAccountView.getTrader().getCountry(),"Canada");


        verify(accountDao, times(1)).findByTraderId(trader.getId());
        verify(traderDao, times(1)).findById(trader.getId());
    }

    @Test
    public void getProfileViewByTraderId(){
        when(accountDao.findByTraderId(trader.getId())).thenReturn(Optional.of(account));
        when(positionDao.findAllById(Collections.singletonList(account.getId()))).thenReturn(Arrays.asList(position));
        when(quoteDao.findById(position.getTicker())).thenReturn(Optional.of(quote));

        PortfolioView portfolioView = dashboardService.getProfileViewByTraderId(trader.getId());

        assertEquals(portfolioView.getSecurityRows().get(0).getTicker(), "FB");
        assertEquals(portfolioView.getSecurityRows().get(0).getQuote().getLastPrice(),Double.valueOf(10));
        assertEquals(quote, portfolioView.getSecurityRows().get(0).getQuote());
        verify(accountDao, times(1)).findByTraderId(trader.getId());
        verify(positionDao, times(1)).findAllById(Collections.singletonList(account.getId()));
        verify(quoteDao,times(2)).findById(position.getTicker());
    }

}
