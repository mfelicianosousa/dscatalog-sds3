package br.com.mfsdevsystems.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystems.dscatalog.dto.CategoryDTO;
import br.com.mfsdevsystems.dscatalog.entities.Category;
import br.com.mfsdevsystems.dscatalog.repositories.CategoryRepository;
import br.com.mfsdevsystems.dscatalog.services.exception.DatabaseException;
import br.com.mfsdevsystems.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository ;
	
	@Transactional(readOnly=true)
	public CategoryDTO findById(Long id ){
		
		Optional<Category> obj = repository.findById( id );
		Category entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		
		return new CategoryDTO( entity ) ;
	}

	@Transactional(readOnly=true)
	public Page<CategoryDTO> FindAllPage(Pageable pageable) {
		
		Page< Category > list = repository.findAll(pageable);
		
		// expressão de alta ordem Lambda
		return list.map(x -> new CategoryDTO( x ));
	
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


	public void delete(Long id) {
		
		try {
	    	repository.deleteById( id );
		} catch (EmptyResultDataAccessException e ) {
			throw new ResourceNotFoundException("Id not found "+id ) ;
		} catch ( DataIntegrityViolationException e ) {
			throw new DatabaseException("Integrity Violation") ;
		}
		
	}
	
}
