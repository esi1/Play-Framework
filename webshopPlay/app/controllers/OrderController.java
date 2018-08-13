package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Order;
import models.Product;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import views.html.order.createOrderForm;
import views.html.order.showorders;

public class OrderController extends Controller{
	
	@Transactional
	public static Result createOrderForm() {
		List<Product> products = JPA.em().createQuery("SELECT o from Product AS o", Product.class).getResultList();

		return ok(createOrderForm.render(products));
	}
	
	@Transactional
	 public static Result createOrder() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		Order orders = new Order();
		User user = UserController.getUserFromSession();
		if(user==null){
			flash().put("field-is-empty", "yes");
			return redirect(routes.OrderController.createOrderForm());
		}else{
			
			orders.setUser(user);
		}
		
		Integer quantity = Integer.parseInt(form.get("quantity")[0]);
		orders.setQuantity(quantity);
		Integer productid = Integer.parseInt(form.get("product-id")[0]);
		Product product = JPA.em().find(Product.class, productid);
		List<Product> products = new ArrayList<>();
		products.add(product);
		orders.setProducts(products);
		if(user!=null){		
		JPA.em().persist(orders);
		}
		return ok();
	    }
	
	@Transactional
	 public static Result showOrders() {
			List<Order> orders = JPA.em().createQuery("SELECT o from Order AS o", Order.class).getResultList();
		return ok(Json.toJson(orders));			
	    }
	    
	@Transactional
	 public static Result showOrdersProducts() {
		
		List<Order> orders = JPA.em().createQuery("SELECT o from Order AS o", Order.class).getResultList();
		return ok();
	    }

}
