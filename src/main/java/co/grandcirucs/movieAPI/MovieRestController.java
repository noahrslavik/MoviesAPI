package co.grandcirucs.movieAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MovieRestController {

	@Autowired
	private MovieDao repository;

	// Aggregate root

	@GetMapping("/movies")
	List<Movie> all(@RequestParam(value = "name", required = false) String name) {

		if (name == null || name.isEmpty()) {
			return repository.findByOrderByName();
		} else {
			return repository.findByNameContainingIgnoreCaseOrderByName(name);
		}
	}

	@PostMapping("/movies")
	Movie newMovie(@RequestBody Movie movie) {

		return repository.save(movie);
	}

	// Single item

	@GetMapping("/movies/{id}")
	Movie one(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such movie."));
	}

	@DeleteMapping("/movies/{id}")
	void deleteProduct(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping("/movies/category")
	List<Movie> allCategories(@RequestParam(value = "category", required = false) String category) {

		if (category == null || category.isEmpty()) {
			return repository.findByOrderByName();
		} else {
			return repository.findByCategoryContainingIgnoreCase(category);
		}
	}

	@GetMapping("/movies/random")
	Movie random() {
		List<Movie> movies = repository.findAll();
		Random rand = new Random();
		int randomInt = rand.nextInt(movies.size());
		return movies.get(randomInt);
	}

	@GetMapping("/movies/randomfromcategory")
	Movie randomFromCategory(@RequestParam(value = "category", required = true) String category) {
		List<Movie> moviesByCat = repository.findByCategoryContainingIgnoreCase(category);
		Random rand = new Random();
		int randomInt = rand.nextInt(moviesByCat.size());
		return moviesByCat.get(randomInt);
	}

	@GetMapping("/movies/randomamount")
	List<Movie> randomNumber(@RequestParam(value = "number", required = true) Integer number) {
		List<Movie> allMovies = repository.findAll();
		Random rand = new Random();
		List<Movie> moviesOut = new ArrayList<Movie>();
		for (int i = 0; i < number; i++) {
			Movie randomElement = allMovies.get(rand.nextInt(allMovies.size()));
			moviesOut.add(randomElement);
			for (int j = 0; j < allMovies.size(); j++) {
				if (allMovies.get(j).equals(randomElement)) {
					allMovies.remove(j);
				}
			}
		}
		return moviesOut;
	}

	@GetMapping("/movies/allCategories")
	List<String> getAllCategories() {
		List<Movie> allMovies = repository.findAll();
		List<String> returnString = new ArrayList<String>();
		for (int i = 0; i < allMovies.size(); i++) {
			String currentCategory = allMovies.get(i).getCategory();
			if(returnString.contains(currentCategory)) {	
			}
			else {
				returnString.add(currentCategory);
			}

		}
		return returnString;
	}

}
