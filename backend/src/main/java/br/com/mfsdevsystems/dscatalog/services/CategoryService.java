package br.com.mfsdevsystems.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystems.dscatalog.dto.CategoryDTO;
import br.com.mfsdevsystems.dscatalog.entities.Category;
import br.com.mfsdevsystems.dscatalog.repositories.CategoryRepository;

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

}
