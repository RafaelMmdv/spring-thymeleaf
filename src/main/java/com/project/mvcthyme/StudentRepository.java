package com.project.mvcthyme;


import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUsername(String username);
}
