package pe.edu.unfv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.unfv.model.Product;

@Repository
public interface IProduct extends JpaRepository<Product, Integer>{

	public Product findProductByNombre(String nombre);
}
