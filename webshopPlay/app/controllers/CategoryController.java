package controllers;

import java.util.List;
import java.util.Map;

import models.Category;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Controller;
import play.mvc.Security;
import views.html.main;
import views.html.category.*;

public class CategoryController extends Controller{

	@Transactional
	public static Result createCategoryForm(){
		return ok(createCategoryForm.render());
	}
	
	@Transactional
	@Security.Authenticated
	public static Result createCategory(){
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		Category category = new Category();
		category.setCategoryname(form.get("categoryname")[0]);
		 boolean categoryIsEmpty = "".equals(form.get("categoryname")[0]);
		 if (categoryIsEmpty) {
				flash().put("field-is-empty", "yes");
				return redirect(routes.CategoryController.createCategoryForm());
			}
			 JPA.em().persist(category);
		return ok();
		
	}
	
	@Transactional
	public static Result showCategories(){
		List<Category> categories = JPA.em().createQuery("SELECT c from Category AS c", Category.class).getResultList();
		return ok(Json.toJson(categories));
	}
	@Transactional
	public static Result showCategory(int id) {
		Category category = JPA.em().find(Category.class, id);
		return ok(Json.toJson(category));
	}


}
