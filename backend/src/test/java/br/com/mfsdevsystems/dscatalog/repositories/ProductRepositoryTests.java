package br.com.mfsdevsystems.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.mfsdevsystems.dscatalog.entities.Product;
import br.com.mfsdevsystems.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository ;
	
	private long exintingId ;
	private long nonExistingId ;
	private long countTotalProducts = 25L ;
	
	@BeforeEach
	void setUp() throws Exception{
		
		exintingId = 1L ;
		nonExistingId = 1000L ;
		countTotalProducts = 25L;
	}

	@Test
	public void DeleteShouldDeleteObjectWhenIdExists() {
		
		/* Fase de preparação AAA Arrange -Act - Assert */
		
		repository.deleteById( exintingId );
		
		Optional<Product> result = repository.findById(exintingId) ;
		Assertions.assertFalse(result.isPresent());
		
		
	}
	
	@Test
	public void DeleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		
		/* Fase de preparação AAA Arrange -Act - Assert */
		
		
		Assertions.assertThrows(EmptyResultDataAccessException.class,() -> {
			
			repository.deleteById( nonExistingId );
			
		}) ; 
		
	}
	
	@Test
	public void SaveShouldPersistWithAutoIncrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save( product ) ;
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
		
		
	}
	
}
