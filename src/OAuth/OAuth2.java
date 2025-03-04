package OAuth;

import static io.restassured.RestAssured.*;

//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;

import io.restassured.path.json.JsonPath;

public class OAuth2 {

	public static void main(String[] args) {
		
//		System.setProperty("webdriver.chrome.driver", "/Users/bchattop/Documents/applcoreqa/src/test/resources/macDrivers/chromedriver");
//		WebDriver driver = new ChromeDriver();
//		
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("srinath19830");
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
//		
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("password");
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(4000);
//		
//		String url = driver.getCurrentUrl();
		
		// Due to google update 
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVG7fiRigltMnOQEGRt8gpiLU8PT9rTbPOF8uwBcu7Oajn_I5FvTHj0YGY8A8VRlpoTAkA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=consent";
		String partial_code = url.split("code=")[1];
		String actual_code = partial_code.split("&scope")[0];
		
		
		String access_tokenResponse = given().urlEncodingEnabled(false)
									.queryParam("code", actual_code).queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
									.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
									.queryParam("grant_type", "authorization_code").when().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(access_tokenResponse);
		String access_token = js.getString(access_tokenResponse);
		
		String response = given().queryParam("access_token", access_token).when().get("https:/rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response);
	}
}
