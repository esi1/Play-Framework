package controllers;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.loginadmin;
import play.mvc.Security;

public class StaffController extends Controller {

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
	public static Result loginAdministratorForm() {
		return ok(loginadmin.render());
	}

	@Transactional
	public static Result loginAdministrator() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		String email = form.get("username")[0];
		String passwd = form.get("password")[0];
		TypedQuery<models.Staff> query = JPA
				.em()
				.createQuery(
						"SELECT c FROM Staff c WHERE c.email = :username AND c.password = :password",
						models.Staff.class);

		List<models.Staff> matchingUsers = query.setParameter("username", email)
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
}
