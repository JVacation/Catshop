package orders;

import catalogue.Basket;
import catalogue.Product;
import middle.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * A JUnit test of the Order class
 */
public class OrderTestX  extends OrderTest
{
  public OrderProcessing makeOrder()
  {
    return new OrderX();
  }

}
