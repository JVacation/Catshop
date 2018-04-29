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
public class OrderTest 
{
  private final int ORDER_NUMBER1 = 7;     // number
  private final int ORDER_NUMBER2 = 8;     // number
  private final int ORDER_NUMBER3 = 10;    // number
  private Basket theBought1 = null;        // items
  private Basket theBought2 = null;        // items
  private Basket theBought3 = null;        // items
  private StockReadWriter theStock = null; // list
  private OrderProcessing theOrder = null; //

  public OrderProcessing makeOrder()
  {
    return new Order();
  }

  @Before
  public void setUp()
  {
    // Local access to StockList / Order system
    //MiddleFactory mf = new LocalMiddleFactory();
    try
    {
      //theStock = mf.makeStockReadWriter(); // access
      theStock = new dbAccess.StockRW();
      //theOrder = mf.makeOrderProcessing(); // order
      theOrder = makeOrder();
      // To get a new order system for each test
      // uncomment the next line
      // theOrder = new Order();
      
    } catch (Exception e) {
      fail("Exception " + e.getMessage());
    }
  
    theBought1 = new Basket();              // bought
    theBought1.setOrderNum( ORDER_NUMBER1 );
    theBought2 = new Basket();              // bought
    theBought2.setOrderNum( ORDER_NUMBER2 );
    theBought3 = new Basket();              // bought
    theBought3.setOrderNum( ORDER_NUMBER3 );
  
    try 
    {
      Product pr = theStock.getDetails("0001");
      theBought1.add(pr);
      theBought2.add(pr);
    } catch (StockException e) {
      fail("StockException " + e.getMessage());
    }
  }
  
  @After
  public void tearDown() throws Exception 
  {
    theOrder = null; theStock = null;
  }
  
  @Test
  public void test() 
  {
    try
    {
      boolean ok;
      //Introduce two orders
      theOrder.newOrder( theBought1 );  // ORDER_NUMBER1
      theOrder.newOrder( theBought2 );  // ORDER_NUMBER2
      // Check if "Waiting" ORDER_NUMBER1, ORDER_NUMBER2
      /* +++ */
      if ( ! isInTray( "Waiting", new int[] { ORDER_NUMBER1, ORDER_NUMBER2 } ) )
      {
        String message = null;
        if ( ! isInTray( "Waiting", ORDER_NUMBER1 ) )
          message = String.format("ORDER NO #%d not in waiting tray", ORDER_NUMBER1);

        if ( ! isInTray( "Waiting", ORDER_NUMBER2 ) )
        {
          if ( message == null ) 
          {
            message = "";
          } else {
            message += "\n";
          }
          message += String.format("ORDER NO #%d not in waiting tray", ORDER_NUMBER2);
          fail( message );
        }
        fail ("ORDER NO 1 & ORDER NO 2 not in waiting tray");
      }
      /* --- */

      //Get order to pick
      Basket o2p = theOrder.getOrderToPick();

      /* +++ */
      if ( o2p == null )
      {
        fail( String.format("getOrderToPick() returns null should be order #%d", ORDER_NUMBER1) );
      }
      /* --- */

      // Check if is ORDER_NUMBER1
      /* +++ */
      if (o2p.getOrderNum() != ORDER_NUMBER1 )
      {
        fail (String.format("ORDER NO #%d not picked - picked #%d", 
             ORDER_NUMBER1, o2p.getOrderNum() ) );
      }
      /* --- */

      // Check if ORDER_NUMBER1 is "BeingPicked"
      /* +++ */
      if ( ! isInTray( "BeingPicked",  ORDER_NUMBER1 ) )
      {
        fail (String.format("ORDER NO #%d not in being picked tray", ORDER_NUMBER1) );
      }
      /* --- */

      // Check if ORDER_NUMBER2 is still "Waiting"
      /* +++ */
      if ( ! isInTray( "Waiting",  ORDER_NUMBER2 ) )
      {
        fail (String.format("ORDER NO #%d not in being picked tray", ORDER_NUMBER2) );
      }
      /* --- */
      
      //Check [order number 1] completion of being "picked"
      ok = theOrder.informOrderPicked( ORDER_NUMBER1 );
      /* +++ */
      if ( ! ok )
      {
        fail (String.format("informOrderPicked #%d returns false", ORDER_NUMBER1) );
      }
      if ( ! isInTray( "ToBeCollected",  ORDER_NUMBER1 ) )
      {
        fail (String.format("ORDER NO #%d not in to be collected tray", ORDER_NUMBER1) );
      }
      /* --- */

      //Check if [order number 3]can be completed
      // This should fail as no [order no 3]
      ok = theOrder.informOrderPicked( ORDER_NUMBER3 );
      /* +++ */
      if ( ok )
      {
        fail (String.format("inform order picked #%d return true", ORDER_NUMBER3) );
      }
      /* --- */
          
      //Check if [order number 1] removed from system
      ok = theOrder.informOrderCollected( ORDER_NUMBER1 );
      /* +++ */
      if ( ! ok )
      {
        fail (String.format("ORDER NO #%d can not be removed", ORDER_NUMBER1) );
      }
      if ( isInTray( "ToBeCollected",  ORDER_NUMBER1 ) )
      {
        fail (String.format("ORDER NO #%d is still in collected tray", ORDER_NUMBER1) );
      }
      
      ok = theOrder.informOrderCollected( ORDER_NUMBER1 );
      if ( ok )
      {
        fail (String.format("ORDER NO #%d collected twice", ORDER_NUMBER1) );
      }
      /* --- */

      //Check if [order number 3] removed from system
      // Order number 3 does not exist
      ok = theOrder.informOrderCollected( ORDER_NUMBER3 );
      /* +++ */
      if ( ok )
      {
        fail (String.format("ORDER NO #%d can be collected", ORDER_NUMBER3) );
      }
      /* --- */

      // Progress [order number 2]
      //Get order to pick
      o2p = theOrder.getOrderToPick();
      // Check if is ORDER_NUMBER2
      /* +++ */
      if (o2p.getOrderNum() != ORDER_NUMBER2 )
      {
        fail (String.format("ORDER NO #%d not picked - picked #%d", ORDER_NUMBER2, o2p.getOrderNum()) );
      } 
      /* --- */
 
      //Check [order number 2] completion of being "picked"
      ok = theOrder.informOrderPicked( ORDER_NUMBER2 );
      /* +++ */
      if ( ! ok )
      {
        fail (String.format("informOrderPicked: #%d return false", ORDER_NUMBER2) );
      }
      if ( ! isInTray( "ToBeCollected",  ORDER_NUMBER2 ) )
      {
        fail (String.format("ORDER NO #%d not in collection tray", ORDER_NUMBER2) );
      }
      /* --- */

      //Check [order number 2] collected
      ok = theOrder.informOrderCollected( ORDER_NUMBER2 );
      /* +++ */
      if ( ! ok )
      {
        fail (String.format("ORDER NO #%d can not be removed", ORDER_NUMBER2) );
      }
      if ( isInTray( "ToBeCollected",  ORDER_NUMBER2 ) )
      {
        fail (String.format("ORDER NO #%d is still in to be collected tray", ORDER_NUMBER2) );

      }
      
      ok = theOrder.informOrderCollected( ORDER_NUMBER2 );
      if ( ok )
      {
        fail (String.format("ORDER NO #%d collected twice", ORDER_NUMBER2) );
      }
      /* --- */
    } catch ( Exception e )
    {
      fail( "Exception " + e.getMessage() );
    }
  
  }
  
  /* +++ */
  /*
   * Check if orderNum is in tray name
   */
  public boolean isInTray( String name, int orderNum )
  {
    try
    {
      Map<String, List<Integer> > res = theOrder.getOrderState();
      List<Integer> orders = res.get( name );
    
      for ( Integer on: orders )
      {
        if ( on == orderNum ) return true;
      }
    } catch ( OrderException e )
    {
      fail("theOrder.getOrderState(): " + e.getMessage() );
    }
    return false;
  }
  
  /*
   * Check if the order numbers in orderNums are all in tray name
   */
  public boolean isInTray( String name, int orderNums[] )
  {
    boolean res = true;
    for ( int on : orderNums )
    res = res & isInTray( name, on );
    return res;
  }
  /* --- */


  @Test
  public void test2() 
  {
    try
    {
      boolean ok;
      //Introduce three orders

      theOrder.newOrder( theBought1 );  // ORDER_NUMBER1
      theOrder.newOrder( theBought2 );  // ORDER_NUMBER2
      theOrder.newOrder( theBought3 );  // ORDER_NUMBER3
      
      // Check if "Waiting" ORDER_NUMBER1, ORDER_NUMBER2, ORDER_NUMBER_3
      /* +++ */

      if ( ! isInTray( "Waiting", new int[] { ORDER_NUMBER1, ORDER_NUMBER2, ORDER_NUMBER3 } ) )
      {
        String message = "\n";
        for ( int on : new int[] {ORDER_NUMBER1, ORDER_NUMBER2, ORDER_NUMBER3 } )
        {
         if ( ! isInTray( "Waiting", on ) )
           message += String.format("ORDER NO #%d not in waiting tray\n", on );
        }
        fail( message );
      }
      /* --- */

    } catch ( Exception e )
    {
      fail( "Exception " + e.getMessage() );
    }
  }

}
