package com.appian.microservices.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.appian.microservices.inventory.model.Delete;
import com.appian.microservices.inventory.model.Update;
import com.appian.microservices.inventory.repository.Inventory;

/**
 * Inventory application.
 *
 * @author touré
 */
@SpringBootApplication
@RestController
public class InventoryApplication extends WebMvcConfigurerAdapter {

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private CorrelationIdFilter correlationIdFilter;

  // TODO: error handling

  @RequestMapping(value = "/report")
  public @ResponseBody List<Inventory> report() {
    return inventoryService.list();
  }

  @RequestMapping(value = "/products")
  public @ResponseBody List<Inventory> list() {
    return inventoryService.getInventory();
  }

  @RequestMapping(value = "/products/{sku}")
  public @ResponseBody Inventory getInventory(@PathVariable String sku) {
    return inventoryService.get(sku);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Inventory update(@RequestBody Update update) {
    return inventoryService.update(update);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  Inventory delete(@RequestBody Delete delete) {
    return inventoryService.delete(delete);
  }

  @RequestMapping(value = "/status")
  public @ResponseBody String health() {
    return "UP";
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(correlationIdFilter);
  }


  public static void main(String[] args) {
    SpringApplication.run(InventoryApplication.class, args);
  }
}
