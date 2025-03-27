package Pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RootProduct {
	
	@Getter @Setter
	private String _id ;
	
	@Getter @Setter
	private Product product ;
	
	
	
	

}
