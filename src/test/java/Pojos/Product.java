package Pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
	
	@JsonProperty("_id")
	@Getter @Setter
	private String id;
	@JsonProperty("productName")
	@Getter @Setter
	private String productName;
	@JsonProperty("productCategory")
	@Getter @Setter
	private String productCategory;
	@JsonProperty("productSubCategory")
	@Getter @Setter
	private String productSubCategory;
	@JsonProperty("productPrice")
	@Getter @Setter
	private Integer productPrice;
	@JsonProperty("productDescription")
	@Getter @Setter
	private String productDescription;
	@JsonProperty("productImage")
	@Getter @Setter
	private String productImage;
	@JsonProperty("productRating")
	@Getter @Setter
	private String productRating;
	@JsonProperty("productTotalOrders")
	@Getter @Setter
	private String productTotalOrders;
	@Getter @Setter
	@JsonProperty("productStatus")
	private Boolean productStatus;
	@Getter @Setter
	@JsonProperty("productAddedBy")
	private String productAddedBy;
	@Getter @Setter
	@JsonProperty("__v")
	private Integer v;
	@Getter @Setter
	@JsonProperty("productFor")
	private String productFor;

	
	

}
