package ca.jrvs.apps.trading.controller;


import ca.jrvs.apps.trading.model.domain.PortfolioView;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import ca.jrvs.apps.trading.service.DashboardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {
    private DashboardService dashboardService;
    public DashBoardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }

    @ApiOperation(value = "Show trader profile by trader ID",
            notes = "Show trader and account details. TraderId and AccountId should be identical")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "traderId or accountId is not found")})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/profile/traderId/{traderId}", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE})
    public TraderAccountView getAccount(@PathVariable Integer traderId){
        try{
            return dashboardService.getTraderAccount(traderId);
        } catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Show portfolio by trader ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "traderId is not found")})
    @GetMapping(path = "/portfolio/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PortfolioView getPortfolioView(@PathVariable Integer traderId){
        try{
            return dashboardService.getProfileViewByTraderId(traderId);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
