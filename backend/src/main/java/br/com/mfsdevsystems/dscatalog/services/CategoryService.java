package br.com.mfsdevsystems.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystems.dscatalog.entities.Category;
import br.com.mfsdevsystems.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository ;
	
	@Transactional(readOnly=true)
	public List<Category> findAll(){
		
		return repository.findAll();
	}

}
