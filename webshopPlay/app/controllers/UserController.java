package controllers;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.main;
import views.html.user.*;

public class UserController extends Controller {

	@Transactional
	public static Result createUserForm() {
		return ok(createUserForm.render());
	}

	@Transactional
	public static Result createUser() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		User user = new User();
		user.setPassword(form.get("password")[0]);
		user.setFirstname(form.get("firstname")[0]);
		user.setLastname(form.get("lastname")[0]);
		user.setStreetaddress(form.get("streetaddress")[0]);
		user.setPhonenumber(form.get("phonenumber")[0]);
		user.setEmail(form.get("email")[0]);
		user.setCity(form.get("city")[0]);
		user.setPostcode(form.get("postcode")[0]);

		boolean passwordIsEmpty = "".equals(form.get("password")[0]);
		boolean firstnameIsEmpty = "".equals(form.get("firstname")[0]);
		boolean lastnameIsEmpty = "".equals(form.get("lastname")[0]);
		boolean streetaddressIsEmpty = "".equals(form.get("streetaddress")[0]);
		boolean phonenumberIsEmpty = "".equals(form.get("phonenumber")[0]);
		boolean emailIsEmpty = "".equals(form.get("email")[0]);
		boolean cityIsEmpty = "".equals(form.get("city")[0]);
		boolean postcodeIsEmpty = "".equals(form.get("postcode")[0]);

		if (passwordIsEmpty || firstnameIsEmpty || lastnameIsEmpty
				|| streetaddressIsEmpty || phonenumberIsEmpty || emailIsEmpty
				|| cityIsEmpty || postcodeIsEmpty) {
			flash().put("field-is-empty", "yes");
			return redirect(routes.UserController.createUserForm());
		}
		JPA.em().persist(user);
		return ok(main.render(null, null));
	}

	@Transactional
	public static Result showUsers() {
		List<User> users = JPA.em()
				.createQuery("SELECT u from User AS u", User.class)
				.getResultList();
		return ok(Json.toJson(users));
	}

	@Transactional
	public static Result logout() {
		if(session().containsKey("username")){
			session().clear();
			return ok("Ok, your are logged out");
		}else{
			return ok("You have never been logged in");
		}
	}

	@Transactional
	public static Result loginUserForm() {
		return ok(showUserLoginForm.render());
	}

	@Transactional
	public static Result loginUser() {

		Map<String, String[]> form = request().body().asFormUrlEncoded();
		String email = form.get("username")[0];
		String passwd = form.get("password")[0];
		TypedQuery<models.User> query = JPA
				.em()
				.createQuery(
						"SELECT c FROM User c WHERE c.email = :username AND c.password = :password",
						models.User.class);

		List<models.User> matchingUsers = query.setParameter("username", email)
				.setParameter("password", passwd).getResultList();

		boolean usernameIsEmpty = "".equals(email);
		boolean passwordIsEmpty = "".equals(passwd);

		if (usernameIsEmpty || passwordIsEmpty) {
			if (usernameIsEmpty) {
				flash().put("username-empty", "yes");
			}
			if (passwordIsEmpty) {
				flash().put("password-empty", "yes");
			}
			return ok();
		}
		if (matchingUsers.size() == 1) {
			session().put("username", email);
			return ok("Ok, you are logged");
		} else {
			flash().put("no-username-password-match", "yes");
			return ok();
		}
	}

	@Transactional
	public static User getUserFromSession() {
		String username = session().get("username");
		TypedQuery<User> query = JPA.em().createQuery(
				"SELECT u FROM User u WHERE u.email = :username", User.class);
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		if (users == null || users.size() == 0) {
			return null;
		}
		User user = users.get(0);
		return user;
	}
}
