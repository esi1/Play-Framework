package controllers;

import java.util.List;
import java.util.Map;

import models.Category;
import models.Product;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.product.*;

public class ProductController extends Controller {

	@Transactional
	@Security.Authenticated
	public static Result createProductForm() {
		List<Category> categories = JPA.em()
				.createQuery("SELECT c from Category AS c", Category.class)
				.getResultList();
		
		return ok(createProductForm.render(categories));
	}

	@Transactional
	@Security.Authenticated
	public static Result createProduct() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		Product product = new Product();
		product.setProductname(form.get("productname")[0]);
		product.setDescription(form.get("description")[0]);
		boolean costIsEmpty = "".equals((form.get("cost")[0]));
		boolean rrpIsEmpty = "".equals((form.get("rrp")[0]));
		boolean productnameIsEmpty = "".equals(form.get("productname")[0]);
		boolean descriptionIsEmpty = "".equals(form.get("description")[0]);		 
		if(productnameIsEmpty || descriptionIsEmpty || costIsEmpty || rrpIsEmpty){
			flash().put("field-is-empty", "yes");
			return redirect(routes.ProductController.createProductForm());
		}else{
			
			product.setCost(Double.parseDouble(form.get("cost")[0]));
			product.setRrp(Double.parseDouble(form.get("rrp")[0]));
		}
		Integer categoryid = Integer.parseInt(form.get("category-id")[0]);
		Category category = JPA.em().find(Category.class, categoryid);
		product.setCategory(category);
	
		JPA.em().persist(product);
		return ok();
	}

	@Transactional
	public static Result showProducts() {
		List<Product> products = JPA.em()
				.createQuery("SELECT p from Product AS p", Product.class)
				.getResultList();
		return ok(Json.toJson(products));
	}

	@Transactional
	public static Result showProductsInCategory() {
		List<Product> products = JPA.em()
				.createQuery("SELECT p from Product AS p", Product.class)
				.getResultList();
		return ok(Json.toJson(products));
	}

}
