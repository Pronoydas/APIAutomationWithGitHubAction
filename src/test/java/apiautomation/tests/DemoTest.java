package apiautomation.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pojos.Product;
import Pojos.RootProduct;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

public class DemoTest {
	RequestSpecBuilder reqspecbuilder;
	ResponseSpecBuilder responSpecBuilder;
	private String token="";
	private String userId="";
	private String productId="";
	private String orderId="";
	
	
	@BeforeClass (alwaysRun = true)
	public void buldingSpec() {
		reqspecbuilder= new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setBasePath("/api/ecom")
				.log(LogDetail.ALL);
		
		responSpecBuilder = new ResponseSpecBuilder()
				.log(LogDetail.ALL)
				.expectResponseTime(lessThan(3000L));
		
	}
	
	
	
	@Test(description = "Get the Access Token", priority = 0 , alwaysRun = true)
	public void getToken() {
		
		Map<String, String> map = new HashMap<>();
		map.put("userEmail", "das.pronoy@abc.com");
		map.put("userPassword", "Test#345");
		
		JsonPath jp=RestAssured.given()
		.baseUri("https://rahulshettyacademy.com")
		.basePath("/api")
		.accept(ContentType.ANY)
		.contentType(ContentType.JSON)
		.body(map)
		.log().all()
		.when().post("/ecom/auth/login")
		.then().assertThat()
		.log().all()
		.statusCode(200)
		.body("message", equalTo("Login Successfully"))
		.extract().response().jsonPath();
		token=jp.get("token");
		userId=jp.get("userId");	
	}
	
	@Test(description = "Create a Product for Test", priority = 1)
	public void createProduct() {
		
	 productId =RestAssured.
	given().spec(reqspecbuilder.build())
		.multiPart("productName", "AWS Note")
		.multiPart("productAddedBy",userId)
		.multiPart("productCategory", "Study")
		.multiPart("productSubCategory", "Cloud Study")
		.multiPart("productPrice", "978")
		.multiPart("productDescription","Hello World")
		.multiPart("productFor", "All")
		.multiPart("productImage", new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Image\\Automation_needs.png"))
		.contentType(ContentType.MULTIPART)
		.header("Authorization", token).
	 when()
	    .post("/product/add-product").
	 then()
	     .assertThat().spec(responSpecBuilder.build()).statusCode(201)
	     .body("message", equalToIgnoringCase("Product Added Successfully"))
	     .extract().response().jsonPath().get("productId");
		
	}
	
	@Test(description = "Add The Product to Cart", priority = 3)
	public void addToCart() {
		Product p = new Product();
		p.setId(productId);
		p.setProductName("AWS Note");
		p.setProductCategory("Study");
		p.setProductSubCategory("Cloud Study");
		p.setProductPrice(978);
		p.setProductDescription("Adidas Originals");
		p.setProductImage("https://rahulshettyacademy.com/api/ecom/uploads/productImage_1650649434146.jpeg");
		RootProduct rp = new RootProduct();
		rp.set_id(userId);
		rp.setProduct(p);
		
		RestAssured.given(reqspecbuilder.build())
		.contentType(ContentType.JSON)
		.header("Authorization",token)
		.body(rp)
		.when().post("/user/add-to-cart")
		.then().spec(responSpecBuilder.build()).assertThat()
		.statusCode(200)
		.body("message", equalTo("Product Added To Cart"));
	}
	
	@Test(description = "Place A New Order", priority = 4 )
	public void placeOrder() {
		List<Map<String, String>> payload = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		map.put("country", "India");
		map.put("productOrderedId", productId);
		payload.add(map);
		
		orderId=RestAssured.given().spec(reqspecbuilder.build())
		.contentType(ContentType.JSON)
		.header("Authorization",token)
		.body(payload)
		.when().post("/order/create-order")
		.then().spec(responSpecBuilder.build()).assertThat()
		.statusCode(201)
		.body("message", equalTo("Order Placed Successfully")).extract().response().jsonPath().get("orders[0]");
	}
	
	@Test(description = "Delete Placed  Order", priority = 5 )
	public void deletePlacedOrder() {
		RestAssured.given().spec(reqspecbuilder.build())
		.header("Authorization",token)
		.when().delete("/order/delete-order/"+orderId)
		.then().assertThat()
		.statusCode(200)
		.body("message", equalTo("Orders Deleted Successfully"));
		
	}
	
	@Test(description = "Delete Created Product", priority = 6 )
	public void deleteProduct() {
		RestAssured.given().spec(reqspecbuilder.build())
		.header("Authorization",token)
		.when().delete("/product/delete-product/"+productId)
		.then().assertThat()
		.statusCode(200)
		.body("message", equalTo("Product Deleted Successfully"));
		
	}

	
	

}
