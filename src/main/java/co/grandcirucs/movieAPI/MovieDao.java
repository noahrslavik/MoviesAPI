package co.grandcirucs.movieAPI;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDao extends JpaRepository<Movie,Long> {
	
	List<Movie> findByNameContainingIgnoreCaseOrderByName(String name);
	List<Movie> findByCategoryContainingIgnoreCase(String category);
	List<Movie> findByOrderByName();

}
