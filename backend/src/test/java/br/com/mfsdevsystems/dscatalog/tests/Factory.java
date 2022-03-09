package br.com.mfsdevsystems.dscatalog.tests;

import java.time.Instant;

import br.com.mfsdevsystems.dscatalog.dto.ProductDTO;
import br.com.mfsdevsystems.dscatalog.entities.Category;
import br.com.mfsdevsystems.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		
		Product product = new Product(1L, "phone","Good Phone",800.0,"https://img.com/img.png", Instant.parse("2020-01-03T10:32:00Z"));
		product.getCategories().add(new Category(2L, "Eletronics")) ;
		
		return product ;
		
	}
	
	public static ProductDTO createProductDTO() {
		
		Product product = createProduct();
		
		
		return new ProductDTO(product, product.getCategories());
	}

}
