package com.estore.domain.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.estore.service.order.ICart;
import com.estore.service.order.IOrderDetail;

public class Cart implements Serializable, ICart {

	private List<OrderDetail> items;
	private double total=0;
	
	public Cart(){
		items = new ArrayList<OrderDetail>();
	}
	
	@Override
	public void setItems(List<OrderDetail> lineItems) {
		items = lineItems;
	}

	@Override
	public List<OrderDetail> getItems() {
		return items;
	}

	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public double getTotal(){
		return total;
	}

	@Override
	public IOrderDetail findItem(Long productId){
		for (OrderDetail lineItem: items){
			if (lineItem.getProduct().getId().equals(productId)){
				return lineItem;
			}
		}
		return null;
	}
	
	@Override
	public void updateItemQty(IOrderDetail item, int quantity) {
		Long productId = item.getProduct().getId();
		for (OrderDetail lineItem: items){
			if (lineItem.getProduct().getId().equals(productId)){
				Double price = lineItem.getProduct().getPrice();
				total -= price * lineItem.getQuantity();
				lineItem.setQuantity(quantity);
				total += (price * quantity);
				return;
			}
		}
	}
	
	@Override
	public void addItem(IOrderDetail item) {
		Long productId = item.getProduct().getId();
		total += item.getProduct().getPrice();
		for (OrderDetail lineItem: items){
			if (lineItem.getProduct().getId().equals(productId)){
				lineItem.setQuantity(lineItem.getQuantity()+1);
				return;
			}
		}
		items.add((OrderDetail) item);
	}

	@Override
	public void removeItem(Long lineItemId) {
		for (OrderDetail lineItem: items){
			if (lineItem.getProduct().getId().equals(lineItemId)){
				total -= lineItem.getProduct().getPrice()*lineItem.getQuantity();
				items.remove(lineItem);
				return;
			}
		}
	}

}
