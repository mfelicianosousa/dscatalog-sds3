package br.com.mfsdevsystems.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.mfsdevsystems.dscatalog.dto.CategoryDTO;
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
	
	@GetMapping(value="/{id}")
	public ResponseEntity< CategoryDTO > findById(@PathVariable Long id){
	
		CategoryDTO dto = service.findById(id );
		
		return ResponseEntity.ok().body( dto ) ;
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto ){
		
		dto = service.insert( dto ) ;
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body( dto ) ;
		
	}

}
