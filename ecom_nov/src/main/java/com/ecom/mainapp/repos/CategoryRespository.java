package com.ecom.mainapp.repos;

import com.ecom.mainapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Integer> {
}
