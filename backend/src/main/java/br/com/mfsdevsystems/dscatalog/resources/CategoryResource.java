package br.com.mfsdevsystems.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mfsdevsystems.dscatalog.dto.CategoryDTO;
import br.com.mfsdevsystems.dscatalog.entities.Category;
import br.com.mfsdevsystems.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity< List< CategoryDTO >> findAll(){
	
		/* List Moocky
		List<Category> list = new ArrayList<>();
		list.add( new Category(1L, "Books")) ;
		list.add( new Category(2L, "Eletronics")) ;
	*/
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list) ;
	}

}
