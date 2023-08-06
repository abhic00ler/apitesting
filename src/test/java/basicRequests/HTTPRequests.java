package basicRequests;
import java.util.*;
import org.testng.annotations.Test;

// Static packages for testing requierd for testcases
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

//basic 3 parts of any testcase are as follows
/*
 * given() -> content type, set cookies, add auth,etc
 * 
 * when()-> get, post, put, delete all requests
 *  
 *  then()-> validate status code, extract response, extrat heraders cookies & response body
 */
public class HTTPRequests {
	int id; // used in post and put response for unique response
	@Test(priority=1)
	void getUser() { // for get user only pageurl is sufficient
		
		given()
		
		.when()
			.get("https://reqres.in/api/users?page=2")
		
		.then()
			.statusCode(200)
			.body("page", equalTo(2))
			.log().all();
	}
	@Test(priority=2)
	void createUser() { 
		// for createUser i.e post request some data is needed
		// url + json
		// maintain key value pair in a HashMap
		
		HashMap data = new HashMap();
		data.put("name", "abhishek");
		data.put("job", "leader");
		
		// We need to store the id of the userCreated
		id = given()
				.contentType("application/json")
				.body(data)
			.when()
				.post("https://reqres.in/api/users")
				.jsonPath().getInt("id");
	}	
		
		
//		 To check post response 
//		given()
//			.contentType("application/json")
//			.body(data)
//		.when()
//			.post("https://reqres.in/api/users")
//		
//		.then()
//			.statusCode(201)
//			.log().all();
		
	
	@Test(priority=3, dependsOnMethods = {"createUser"})
	void updateUser(){
		HashMap data = new HashMap();
		data.put("name", "abhi");
		data.put("job", "contributer");
		
		// We need to store the id of the userCreated
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.put("https://reqres.in/api/users/"+id)
		.then()
			.statusCode(200)
			.log().all();
	}
	
	@Test(priority=4)
	void deleteUser() {
		given()
		
		.when()
			.delete("https://reqres.in/api/users/"+id)
			
		.then()
			.statusCode(204)
			.log().all();
	}
	
}
