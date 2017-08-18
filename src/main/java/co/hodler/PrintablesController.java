package co.hodler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@EnableAutoConfiguration
@ComponentScan("co.hodler")
@SpringBootApplication
public class PrintablesController {

  @Autowired
  ContractService contractService;
  @Autowired
  Configuration configuration;

  @RequestMapping(value = "/printables/{printableId}/{userId}", method = GET)
  @ResponseBody
  Integer getPrintablesForUser(@PathVariable("printableId") String printableId,
                               @PathVariable("userId") String userId) {
    return contractService.amountAllowedToPrint(printableId, userId);
  }

  @RequestMapping(value = "/printables/{printableId}/{userId}", method = DELETE)
  @ResponseBody
  void resetPrintablesForUser(@PathVariable("printableId") String printableId,
                              @PathVariable("userId") String userId) {
    contractService.resetAmountAllowedToPrint(printableId, userId);
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(PrintablesController.class, args);
  }
}
