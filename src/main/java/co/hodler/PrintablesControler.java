package co.hodler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@EnableAutoConfiguration
public class PrintablesControler {

  @RequestMapping(value = "/printables/{printableId}/{userId}", method = GET)
  @ResponseBody
  String getPrintablesForUser(@PathVariable("userId") String userId) {
    return "Getting printables for: " + userId;
  }

  @RequestMapping(value = "/printables/{printableId}/{userId}", method = DELETE)
  @ResponseBody
  void resetPrintablesForUser(@PathVariable("printableId") String printableId,
                              @PathVariable("userId") String userId) {
    System.out.println(printableId + userId);
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(PrintablesControler.class, args);
  }
}
