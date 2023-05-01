package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DashboardService {

    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QuoteDao quoteDao;

    public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao, QuoteDao quoteDao){
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }

    /**
     * Create and return a traderAccountView by trader ID
     * - get trader account by id
     * - get trader info by id
     * - create and return a traderAccountView
     *
     * @param traderId must not be null
     * @return traderAccountView
     */
    public TraderAccountView getTraderAccount(Integer traderId){
        if (traderId == null){
            throw new IllegalArgumentException("TraderId must not be null");
        }
        Account account = findAccountByTraderId(traderId);
        Trader trader = traderDao.findById(traderId).orElseThrow(() -> new IllegalArgumentException("Invalid traderId"));
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setTrader(trader);
        traderAccountView.setAccount(account);
        return traderAccountView;

    }

    /**
     * Create and return portfolioView by trader ID
     * - get account by trader id
     * - get positions by account id
     * - create and return a portfolioView
     *
     * @param traderId must not be null
     * @return portfolioView
     * @throws IllegalArgumentException if traderId is null or not found
     */
    public PortfolioView getProfileViewByTraderId(Integer traderId){
        if (traderId == null){
            throw new IllegalArgumentException("TraderId must not be null");
        }
        Account account = findAccountByTraderId(traderId);
        Iterable<Position> positions = positionDao.findAllById(Collections.singletonList(account.getId()));
        List<SecurityRow> securityRows = new ArrayList<>();
        positions.forEach(position -> {
            SecurityRow securityRow = new SecurityRow();
            securityRow.setTicker(position.getTicker());
            securityRow.setPosition(position);
            if (quoteDao.findById(position.getTicker()).isPresent()){
                securityRow.setQuote(quoteDao.findById(position.getTicker()).get());
            }else {
                throw new IllegalArgumentException("No such quote" + position.getTicker());
            }
            securityRows.add(securityRow);
        });

        PortfolioView portfolioView = new PortfolioView();
        portfolioView.setSecurityRows(securityRows);
        return portfolioView;
    }

    private Account findAccountByTraderId(Integer traderId){
        return accountDao.findByTraderId(traderId).orElseThrow(() -> new IllegalArgumentException("Invalid traderId"));
    }


}
