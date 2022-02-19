package br.com.mfsdevsystems.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystems.dscatalog.dto.CategoryDTO;
import br.com.mfsdevsystems.dscatalog.entities.Category;
import br.com.mfsdevsystems.dscatalog.repositories.CategoryRepository;
import br.com.mfsdevsystems.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository ;
	
	@Transactional(readOnly=true)
	public List< CategoryDTO > findAll(){
		
		List< Category > list = repository.findAll();
		
		// expressão de alta ordem Lambda
		
		return list.stream().map(x -> new CategoryDTO( x )).collect(Collectors.toList());
		/*
		List< CategoryDTO > listDto = new ArrayList<>();
		for ( Category category : list ) {
			listDto.add( new CategoryDTO( category )) ;
		}
		
		return listDto ;
		*/
		
		
	}

	
	@Transactional(readOnly=true)
	public CategoryDTO findById(Long id ){
		
		Optional<Category> obj = repository.findById( id );
		Category entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		
		return new CategoryDTO( entity ) ;
	}


	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		
		entity = repository.save(entity) ;
		
		return new CategoryDTO( entity ) ;
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne( id );
			entity.setName(dto.getName());
			entity = repository.save(entity) ;
			
			return new CategoryDTO( entity ) ;
			
		} catch ( EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found "+id ) ;
		}

	}
}
