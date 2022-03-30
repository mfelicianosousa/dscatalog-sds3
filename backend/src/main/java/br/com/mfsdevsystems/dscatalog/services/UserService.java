package br.com.mfsdevsystems.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystems.dscatalog.dto.RoleDTO;
import br.com.mfsdevsystems.dscatalog.dto.UserDTO;
import br.com.mfsdevsystems.dscatalog.dto.UserInsertDTO;
import br.com.mfsdevsystems.dscatalog.dto.UserUpdateDTO;
import br.com.mfsdevsystems.dscatalog.entities.Role;
import br.com.mfsdevsystems.dscatalog.entities.User;
import br.com.mfsdevsystems.dscatalog.repositories.RoleRepository;
import br.com.mfsdevsystems.dscatalog.repositories.UserRepository;
import br.com.mfsdevsystems.dscatalog.services.exception.DatabaseException;
import br.com.mfsdevsystems.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class UserService {
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder ;
	
	@Autowired
	private UserRepository repository ;
	
	@Autowired 
	private RoleRepository roleRepository ;
	
	@Transactional(readOnly=true)
	public UserDTO findById(Long id ){
		
		Optional<User> obj = repository.findById( id );
		User entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		
		return new UserDTO( entity ) ;
	}

	@Transactional(readOnly=true)
	public Page<UserDTO> FindAllPage(Pageable pageable) {
		
		Page< User > list = repository.findAll( pageable );
		
		// expressão de alta ordem Lambda
		return list.map(x -> new UserDTO( x ));
	
	}
	
	
	@Transactional
	public UserDTO insert( UserInsertDTO dto ) {
		User entity = new User();
		copyDtoToEntity( dto, entity ) ;
		entity.setPassword( passwordEncoder.encode( dto.getPassword() ) );
		entity = repository.save( entity ) ;
		
		return new UserDTO( entity ) ;
	}

	@Transactional
	public UserDTO update( Long id, UserUpdateDTO dto ) {
		try {
			
			User entity = repository.getOne( id );
			copyDtoToEntity( dto, entity ) ;
 			entity = repository.save( entity ) ;
			
			return new UserDTO( entity ) ;
			
		} catch ( EntityNotFoundException e ) {
			
			throw new ResourceNotFoundException( "Id not found " + id ) ;
		}

	}


	

	public void delete( Long id ) {
		
		try {
	    	repository.deleteById( id );
	    	
		} catch (EmptyResultDataAccessException e ) {
			
			throw new ResourceNotFoundException( "Id not found " + id ) ;
			
		} catch ( DataIntegrityViolationException e ) {
			
			throw new DatabaseException( "Integrity Violation" ) ;
		}
		
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
        //
		entity.setFirstName( dto.getFirstName());
		entity.setLastName( dto.getLastName());
		entity.setEmail( dto.getEmail());
		
		
		entity.getRoles().clear();
		for ( RoleDTO roleDTO : dto.getRoles() ) {
			
			Role role = roleRepository.getOne( roleDTO.getId());
			
			entity.getRoles().add(role);
		}
	}
	
}
