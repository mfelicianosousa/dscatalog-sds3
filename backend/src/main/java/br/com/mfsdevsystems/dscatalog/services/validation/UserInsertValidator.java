package br.com.mfsdevsystems.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mfsdevsystems.dscatalog.dto.UserInsertDTO;
import br.com.mfsdevsystems.dscatalog.entities.User;
import br.com.mfsdevsystems.dscatalog.repositories.UserRepository;
import br.com.mfsdevsystems.dscatalog.resources.exceptions.FieldMessage;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired 
	private UserRepository repository ;
	
	
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		User user = repository.findByEmail(dto.getEmail()) ;
		
		if (user != null ) {
			
			list.add(new FieldMessage("email","Email já existe")) ;
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}