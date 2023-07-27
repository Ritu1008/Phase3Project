package API;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Phase3Project {
	
	Response response;
	String BaseURI = "http://localhost:8088/employees";
	public static int numberOfEmployees;
	public static String employeeName;
	
	@Test
	public void Test1() {
		
	
		 response = GetMethodAll();
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 response = CreateNewEmployee("John","Smith","10000","xyz@xyz.com");
		 Assert.assertEquals(response.getStatusCode(), 201);
		 Assert.assertEquals(employeeName, "John");
		 int EmpId = response.jsonPath().get("id");
		
		
		 response = UpdateEmployee(EmpId, "Tom","Smith","10000","xyz@xyz.com");
		 Assert.assertEquals(response.getStatusCode(), 200);
		 Assert.assertEquals(employeeName, "Tom");
		 Assert.assertNotEquals(response.jsonPath().getString("firstName"), "John");
		 
			 
		 response = DeleteEmployee(EmpId);
		 Assert.assertEquals(response.getStatusCode(), 200);
		 Assert.assertEquals(response.getBody().asString(), "");
		 
		 response = GetSingleEmployee(EmpId);
		 Assert.assertEquals(response.getStatusCode(), 400);
		 
		 
		 response = GetAllEmployee();
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
	}
	
	public Response GetMethodAll() {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		String body = response.getBody().asString();
		System.out.println("Response body is " + body);
		System.out.println("Response code is " + response.statusCode());
		System.out.println("Response header are  " + response.getHeader("Content-Type"));
		numberOfEmployees = response.jsonPath().getList("employees").size();
		return response;
		
	}
	
	public Response CreateNewEmployee(String Name, String LastName, String Salary, String EmailId) {
		HashMap<String, Object> requestBody = new HashMap<String, Object>();
		
		requestBody.put("firstName", Name);
		requestBody.put("lastName", LastName);
		requestBody.put("salary", Salary);
		requestBody.put("email", EmailId);
		
		RestAssured.baseURI = BaseURI;
		
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(requestBody).post();
		String body = response.getBody().asString();
		System.out.println("The response body is : " + body);
		System.out.println("Response code is " + response.statusCode());

		employeeName = response.jsonPath().getString("firstName");

		return response;
		
	}
	
	public Response UpdateEmployee(int EmpId, String Name,String LastName, String Salary, String EmailId) {
		
		HashMap<String, Object> requestBody = new HashMap<String, Object>();
		
		
		requestBody.put("id", EmpId);
		requestBody.put("firstName", Name);
		requestBody.put("lastName", LastName);
		requestBody.put("salary", Salary);
		requestBody.put("email", EmailId);
		

		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(requestBody).put("/" + EmpId);
		String body = response.getBody().asString();
		System.out.println("Response body is " + body);
		System.out.println("Response code is " + response.statusCode());

		employeeName = response.jsonPath().getString("firstName");

		return response;
	}
	
	public Response DeleteEmployee(int EmpId) {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete("/" + EmpId);
		String body = response.getBody().asString();
		System.out.println("Response body is " + body);
		System.out.println("Response code is " + response.statusCode());
		
		return response;
	}	
	
	public Response GetSingleEmployee(int EmpId) {
	
	RestAssured.baseURI = BaseURI ;
	RequestSpecification request = RestAssured.given();
	Response response = request.get("/" + EmpId);
	String body = response.getBody().asString();
	System.out.println("Response body is " + body);
	System.out.println("Response code is " + response.statusCode());
	
	return response;
	
   }
	
	public Response GetAllEmployee() {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		String body = response.getBody().asString();
		System.out.println("Response body is " + body);
		System.out.println("Response code is " + response.statusCode());
		System.out.println("Response header are  " + response.getHeader("Content-Type"));
		numberOfEmployees = response.jsonPath().getList("employees").size();
		
		return response;
		
		
	}
		 
		 
	

}

