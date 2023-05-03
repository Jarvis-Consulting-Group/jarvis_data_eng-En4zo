package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.dao.*;
import ca.jrvs.apps.trading.model.domain.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao, PositionDao positionDao){
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Exucute a market order
     *
     * - validate the order (e.g. size, and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     *   - buy order: check account balance (calls helper method)
     *   - sell order: check position for the ticker/symbol (calls helper method)
     *   - (please don't forget to update securityOrder. Status)
     * - Save and return securityOrder
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto){
        if (orderDto.getAccountId() == null || orderDto == null || orderDto.getSize() == 0 || orderDto.getTicker() == null ||
        orderDto.getSize() == null){
            throw new IllegalArgumentException("Neither of the field can be null.");
        }
        SecurityOrder securityOrder = new SecurityOrder();
        Account account = accountDao.findById(orderDto.getAccountId()).orElseThrow(() ->
                new DataRetrievalFailureException("fail to retrieve account by accountId" + orderDto.getAccountId()));
        // size greater than 0 mean buy
        if (orderDto.getSize() > 0){
            handleBuyMarketOrder(orderDto,securityOrder,account);
        }
        if (orderDto.getSize() < 0){
            handleSellMarketOrder(orderDto,securityOrder,account);
        }
        return securityOrder;
    }

    protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account){
        Quote quote = quoteDao.findById(marketOrderDto.getTicker()).orElseThrow(() -> new DataRetrievalFailureException(
                "No such quote" + marketOrderDto.getTicker()));
        //check if amount in account is enough
        if(quote.getAskPrice() * marketOrderDto.getSize() > account.getAmount()){
            securityOrder.setStatus(SecurityOrder.Status.CANCELED);
            securityOrder.setNotes("Unable to process the buy order, please check the amount ");
        }else{
            securityOrder.setStatus(SecurityOrder.Status.FILLED);
            securityOrder.setNotes("Order fulfilled with accountId "+
                    account.getId() + " and ticker " + marketOrderDto.getTicker());
        }
        securityOrder.setTicker(marketOrderDto.getTicker());
        securityOrder.setAccountId(account.getId());
        securityOrder.setPrice(quote.getAskPrice());
        securityOrder.setSize(marketOrderDto.getSize());
        securityOrderDao.save(securityOrder);
    }
    protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account){
        Quote quote = quoteDao.findById(marketOrderDto.getTicker()).orElseThrow(() -> new DataRetrievalFailureException(
                "No such quote" + marketOrderDto.getTicker()));
        Position position = positionDao.findByAccountIdAndTicker(account.getId(),marketOrderDto.getTicker()).orElseThrow(()
                -> new DataRetrievalFailureException("No position for" + account.getId() + " and " + marketOrderDto.getTicker()));
        if (position.getPosition() + marketOrderDto.getSize() < 0){
            securityOrder.setStatus(SecurityOrder.Status.CANCELED);
            securityOrder.setNotes("Unable to find position base on accountId" +
                    account.getId() + " and ticker " + marketOrderDto.getTicker());
        }else {
            securityOrder.setStatus(SecurityOrder.Status.FILLED);
            securityOrder.setNotes("Order fulfilled with accountId "+
                    account.getId() + " and ticker " + marketOrderDto.getTicker());
        }
        securityOrder.setTicker(marketOrderDto.getTicker());
        securityOrder.setAccountId(account.getId());
        securityOrder.setPrice(quote.getAskPrice());
        securityOrder.setSize(marketOrderDto.getSize());
        securityOrderDao.save(securityOrder);
    }
}
