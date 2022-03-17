package br.com.mfsdevsystems.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mfsdevsystems.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
