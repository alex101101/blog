package alexmallal.blog.core.dao;


import java.util.HashSet;

import org.springframework.data.jpa.repository.JpaRepository;

import alexmallal.blog.core.model.Role;

public interface RoleDao extends JpaRepository<Role, Long>{
	HashSet<Role> findByNameNot(String removedName);
}