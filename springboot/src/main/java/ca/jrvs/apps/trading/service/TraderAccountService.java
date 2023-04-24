package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TraderAccountService {
    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao, PositionDao positionDao, SecurityOrderDao securityOrderDao){
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input(all fields must be non-empty)
     * - create a trader
     * -create an account
     * create, setup and return a new traderAccountView
     * @param trader cannot be null
     * @return traderAccountView
     */
    public TraderAccountView createTraderAndAccount(Trader trader){
        if (trader.getDob() == null || trader.getEmail() == null || trader.getCountry() == null ||
                trader.getFirstName() == null || trader.getLastName() == null) {
            throw new IllegalArgumentException("All fields are required");
        }
        Trader trader1 = traderDao.save(trader);
        Account newAccount = new Account();
        newAccount.setTraderId(trader1.getId());
        newAccount.setAmount(0.0);
        Account account1 = accountDao.save(newAccount);
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setAccount(account1);
        traderAccountView.setTrader(trader1);
        return traderAccountView;
    }

    /**
     * A trader can be deleted iff it has no open position and 0 cash balance
     * -validate traderID
     * -get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     * @param traderId must not be null
     * @throws IllegalArgumentException if tradeId is null or not found or unable to delete
     */
    public void deleteTraderById(Integer traderId){
        if (traderId == null || !traderDao.existsById(traderId)){
            throw new IllegalArgumentException("trader not exist");
        }

        Account account = accountDao.findByTraderId(traderId).get();

        Optional<Position> position  = positionDao.findById(account.getId());
        if ( (!position.equals(Optional.empty()) && position.get().getPosition() !=0) || account.getAmount() != 0.0d){
            throw new IllegalArgumentException("trader should not have open positions and should have 0 cash balance");
        }

        if (!securityOrderDao.findByAccountId(account.getTraderId()).equals(Optional.empty())){
            securityOrderDao.deleteByAccountId(account.getId());
        }
        accountDao.deleteById(account.getId());
        traderDao.deleteById(traderId);

    }

    /**
     * Deposit a fund to an account by traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId must not be null
     * @param fund must be greater than 0
     * @return IllegalArgumentException if traderId is null or not found and fund is less or equal to 0
     */
    public Account deposit(Integer traderId, Double fund){
        if (traderId == null || !traderDao.existsById(traderId) || fund <= 0){
            throw new IllegalArgumentException("trader not exist or fund must greater than 0");
        }
        Account account = accountDao.findById(traderId).get();
        account.setAmount(account.getAmount() + fund);
        accountDao.updateOne(account);
        return  account;
    }

    /**
     * Withdraw a fund to an account by traderId
     *
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     * @param traderId traderId
     * @param fund amount can't be 0
     * @return IllegalArgumentException if trader is null or not found,
     *              fund is less or equal to 0, and insufficient fund.
     */
    public Account withdraw(Integer traderId, Double fund){
        if (traderId == null || !traderDao.existsById(traderId) || fund <= 0){
            throw new IllegalArgumentException("trader not exist or fund must greater than 0");
        }
        Account account = accountDao.findById(traderId).get();
        if (account.getAmount() >= fund){
            account.setAmount(account.getAmount() - fund);
            accountDao.updateOne(account);
        }else {
            throw new IllegalArgumentException("insufficient fund for withdraw");
        }
        return account;
    }

}
