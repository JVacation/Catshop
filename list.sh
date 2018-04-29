#  PageSplit Y/N     Core S/X          FileName  
export PATH=../../../bin:$PATH
export LINE=-n
export FIRSTPAGE="yes"

function list_file
{
  if  test x$1 = xY
  then
    if test $FIRSTPAGE = "yes"
    then
      FIRSTPAGE="no"
    else
      echo ""                                  >> 00text
      echo -n "NEWPAGE "                       >> 00text
    fi
  else
    echo " "                                   >> 00text
    echo " "                                   >> 00text
    echo " "                                   >> 00text
    FIRSTPAGE="no"
  fi
  
  if  test x$2 = xC 
  then
   echo -n "+++ CORE FILE "                    >> 00text
  else
   echo -n "+++ DISTRIBUTED "                  >> 00text
  fi

  echo Processing $3
  
  echo CatShop file     +++ $3 +++             >> 00text
  echo                                         >> 00text
  # cat -n    # with line no's
  cat $3 | \
  sed -e "/..REMOVE-FROM-HERE../,/..REMOVE-TO-HERE../d" | \
  cat -n \
                                               >> 00text
}

echo -n ""                                     >  00text

list_file Y  C clients/Main.java
list_file Y  Y clients/Setup.java

list_file Y  C catalogue/Product.java
list_file Y  C catalogue/Basket.java
list_file Y  C catalogue/BetterBasket.java

list_file Y  C clients/cashier/CashierClient.java
list_file Y  C clients/cashier/CashierModel.java
list_file Y  C clients/cashier/CashierView.java
list_file Y  C clients/cashier/CashierController.java

list_file Y  C clients/shopDisplay/DisplayClient.java
list_file Y  C clients/shopDisplay/DisplayModel.java
list_file Y  C clients/shopDisplay/DisplayView.java
list_file Y  C clients/shopDisplay/DisplayController.java

list_file Y  C orders/Order.java

# list_file Y  C clients/CustomerGUI.java
# list_file Y  C clients/CashierGUI.java
# list_file Y  C clients/Picture.java
# list_file Y  C clients/WebCustomerClient.java
# list_file N  C clients/CustomerClient.java
# list_file N  C WebCustomerClient.htm

list_file Y  C dbAccess/DBAccess.java
list_file Y  C dbAccess/DBAccessFactory.java
list_file N  C dbAccess/LinuxAccess.java
list_file Y  C dbAccess/StockR.java
list_file Y  C dbAccess/StockRW.java
list_file Y  C dbAccess/WindowsAccess.java

#list_file N  C middle/MiddleFactory.java
#list_file Y  C middle/LocalMiddleFactory.java
#list_file Y  C middle/OrderException.java
#list_file N  C middle/OrderProcessing.java
#list_file N  C middle/StockException.java
#list_file Y  C middle/StockReadWriter.java
#list_file N  C middle/StockReader.java


# list_file N  X middle/Names.java
# list_file Y  X middle/F_Order.java
# list_file Y  X middle/F_StockR.java
# list_file Y  X middle/F_StockRW.java
# list_file Y  X middle/Server.java
# list_file Y  X middle/F_Order.java
# list_file Y  X middle/F_StockR.java
# list_file Y  X middle/F_StockRW.java


# list_file N  X Remote/RemoteOrder_I.java
# list_file Y  X Remote/RemoteStockRW_I.java
# list_file N  X Remote/RemoteStockR_I.java
# list_file Y  X Remote/R_Order.java
# list_file Y  X Remote/R_StockR.java
# list_file Y  X Remote/R_StockRW.java

cp 00text 000text

sh to_rtf6.sh 000text
