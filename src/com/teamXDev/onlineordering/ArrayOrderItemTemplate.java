package com.teamXDev.onlineordering;
public class ArrayOrderItemTemplate {

	 //This class is used for template to array
	
	 String itemName ,itemServingSize;
	 double itemUnitCost,itemTotalCost;
	 int itemQty;
	 
	 public ArrayOrderItemTemplate(String _itemName,String _itemServingSize,double _itemUnitCost, double _itemTotalCost,int _itemQty)
	 {
		 this.itemName=_itemName;
		 this.itemServingSize=_itemServingSize;
		 this.itemUnitCost=_itemUnitCost;
		 this.itemTotalCost=_itemTotalCost;
		 this.itemQty=_itemQty;
	 }
	 
	 // functions for retrieving values
	 
	 public String getItemName(){
		return itemName;
	 }
	 
	 public String getItemServingSize(){
		return itemServingSize;
	 }
	 
	 public double getItemUnitCost(){
		 return itemUnitCost;
	 }
	
	 public double getitemTotalCost(){
		 return itemTotalCost;
	 }
	 
	 public int getitemQty(){
		 return itemQty;
	 }
}
