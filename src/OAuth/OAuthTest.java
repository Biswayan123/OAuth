package OAuth;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import Pojo.Api;
import Pojo.GetCourse;
import Pojo.WebAutomation;

public class OAuthTest {

	public static void main(String[] args) {
		
		String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		
		String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
		.formParam("scope", "trust").when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		JsonPath js = new JsonPath(response);
		String access_token = js.getString("access_token");
		
		GetCourse gc = given().queryParam("access_token", access_token).when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
						.as(GetCourse.class);
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses = gc.getCourses().getApi();
		
		for(int i=0;i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		List<WebAutomation> w = gc.getCourses().getWebAutomation();
		ArrayList<String> actualList = new ArrayList<String>();
		
		for(int i=0;i<w.size();i++) {
			actualList.add(w.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitles);
		
		Assert.assertTrue(actualList.equals(expectedList));
	}

}
