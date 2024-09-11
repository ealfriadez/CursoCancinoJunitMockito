package pe.edu.unfv.testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import pe.edu.unfv.model.Product;
import pe.edu.unfv.repository.IProduct;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) //para que trabaje con la base de datos real
@TestMethodOrder(OrderAnnotation.class) //para ordenar la ejecucion de los metodos de tu clase de pruebas
public class ProductTest {

	@Autowired
	private IProduct iProduct;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void createProduct() {
		
		Product product = this.iProduct.save(new Product("Producto 20", 15340));
		System.out.println(product.toString());
		assertNotNull(product);
	}
	
	@Test
	@Order(2)
	public void findByName() {
		
		String nombre = "Product 1";
		Product product = this.iProduct.findProductByNombre(nombre);
		assertThat(product.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Order(3)
	public void findByNameNotExist() {
		
		String nombre = "Productos 1";
		Product product = this.iProduct.findProductByNombre(nombre);
		assertNull(product);
	}
	
	@SuppressWarnings("static-access")
	@Test
	@Rollback(false)
	@Order(4)
	public void updateProduct() {
		
		String nombre = "Productos 69 pruebas";
		
		Product product = new Product().builder()
				.nombre(nombre)
				.precio(15800)
				.build();
		product.setId(11);
		
		this.iProduct.save(product);
		
		Product productUpdate = this.iProduct.findProductByNombre(nombre);
		
		assertThat(productUpdate.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Order(5)
	public void listProducts() {
		
		List<Product> products = this.iProduct.findAll();
		
		for (Product product : products) {
			System.out.println(product.getNombre());			
		}
		
		assertThat(products).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void deleteProducts() {
		
		int id = 8;
		boolean exist = this.iProduct.findById(id).isPresent(); 
		this.iProduct.deleteById(id);
		boolean notExist = this.iProduct.findById(id).isPresent();
		assertTrue(exist);
		assertFalse(notExist);
	}
}
