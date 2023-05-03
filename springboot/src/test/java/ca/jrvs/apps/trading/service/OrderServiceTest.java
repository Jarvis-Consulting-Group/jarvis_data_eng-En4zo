package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {


    @Captor
    ArgumentCaptor<SecurityOrder> captorSecurityOrder;

    //mock all dependencies
    @Mock
    private AccountDao accountDao;
    @Mock
    private SecurityOrderDao securityOrderDao;
    @Mock
    private QuoteDao quoteDao;
    @Mock
    private PositionDao positionDao;

    //injecting mocked dependencies to the testing class via constructor
    @InjectMocks
    private OrderService orderService;

    private Quote quote;

    private Account account;
    private MarketOrderDto marketOrderDto;
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


        MarketOrderDto savedmarketOrderDto = new MarketOrderDto();
        savedmarketOrderDto.setSize(5);
        savedmarketOrderDto.setTicker("FB");
        savedmarketOrderDto.setAccountId(savedAccount.getId());
        marketOrderDto = savedmarketOrderDto;

        Position savedPosition = new Position();
        savedPosition.setPosition(10);
        savedPosition.setTicker("FB");
        savedPosition.setAccountId(savedAccount.getId());
        position = savedPosition;
    }

    @Test
    public void executeMarketOrder_Buy(){
        when(accountDao.findById(marketOrderDto.getAccountId())).thenReturn(Optional.of(account));
        when(quoteDao.findById(marketOrderDto.getTicker())).thenReturn(Optional.of(quote));

        SecurityOrder securityOrder = orderService.executeMarketOrder(marketOrderDto);

        assertEquals(securityOrder.getStatus(), SecurityOrder.Status.FILLED);
        assertEquals(securityOrder.getNotes(),"Order fulfilled with accountId 1 and ticker FB");

        verify(accountDao,times(1)).findById(marketOrderDto.getAccountId());
        verify(quoteDao, times(1)).findById(marketOrderDto.getTicker());
        verify(securityOrderDao,times(1)).save(any(SecurityOrder.class));
    }

    @Test
    public void executeMarketOrder_Sell(){
        marketOrderDto.setSize(-5);
        when(accountDao.findById(marketOrderDto.getAccountId())).thenReturn(Optional.of(account));
        when(quoteDao.findById(marketOrderDto.getTicker())).thenReturn(Optional.of(quote));

        when(positionDao.findByAccountIdAndTicker(account.getId(),marketOrderDto.getTicker())).
                thenReturn(Optional.of(position));
        SecurityOrder securityOrder = orderService.executeMarketOrder(marketOrderDto);

        assertEquals(securityOrder.getStatus(), SecurityOrder.Status.FILLED);
        assertEquals(securityOrder.getNotes(),"Order fulfilled with accountId 1 and ticker FB");

        verify(accountDao,times(1)).findById(marketOrderDto.getAccountId());
        verify(quoteDao, times(1)).findById(marketOrderDto.getTicker());
        verify(positionDao,times(1)).findByAccountIdAndTicker(account.getId(),marketOrderDto.getTicker());
        verify(securityOrderDao,times(1)).save(any(SecurityOrder.class));



    }


}
