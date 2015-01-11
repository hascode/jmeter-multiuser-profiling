package it.webservice;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.config.RedirectConfig;
import com.jayway.restassured.config.RestAssuredConfig;

@RunWith(Arquillian.class)
public class AuthenticationWebserviceIT {
	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "hascode.war");
		return archive.addPackages(true, "com.hascode").addAsResource("META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.setWebXML(new File("src/main/webapp/WEB-INF", "/web.xml")).addAsWebResource(new File("src/main/webapp/secured.html")).addAsWebResource(new File("src/main/webapp/login.html"));
	}

	@ArquillianResource
	protected URL appUrl;

	@BeforeClass
	public static void autoAuthAsAdministrator() {
		RestAssured.basePath = "/hascode/";
		RestAssured.config = RestAssuredConfig.config().redirect(new RedirectConfig().followRedirects(true).allowCircularRedirects(true));
		RestAssured.authentication = RestAssured.form("admin", "test", new FormAuthConfig("j_security_check", "j_username", "j_password"));
	}

	@Test
	@InSequence(1)
	public void shouldGetUserSessionInformation() throws Exception {
		expect().body("userid", equalTo("admin"), "password", equalTo(null), "groups.groupid", equalTo("administrators")).statusCode(200).when().get(appUrl + "rs/session");
	}

	@Test
	@InSequence(2)
	public void shouldLogoutSuccessfully() throws Exception {
		expect().statusCode(200).when().post(appUrl + "rs/session/logout");
		RestAssured.reset();
		expect().body(containsString("Login")).when().get(appUrl + "secured.html");
	}
}
