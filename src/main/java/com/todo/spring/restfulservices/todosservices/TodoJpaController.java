package com.todo.spring.restfulservices.todosservices;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todo.spring.restfulservices.todosservices.entity.Todo;
import com.todo.spring.restfulservices.todosservices.service.TodoJpaRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoJpaController {

	@Autowired
	private TodoJpaRepository todoJpaRepository;

	@GetMapping(path = "/jpa/users/{username}/todos")
	public List<Todo> getTodos(@PathVariable String username) {
		return todoJpaRepository.findByUsername(username);
	}

	@PostMapping(path="/jpa/users/{username}/todos")
	public  ResponseEntity<Void> createTodo(@RequestBody Todo todoBean, @PathVariable String username) {
		
		todoBean.setUsername(username);
		Todo createdTodo = todoJpaRepository.save(todoBean);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdTodo.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(path="/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, 
			@PathVariable int id, @RequestBody Todo todoBean) {
		todoJpaRepository.save(todoBean);
		return new ResponseEntity<Todo>(todoBean, HttpStatus.OK);
	}

	@GetMapping(path="/jpa/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable Long id) {
		Optional<Todo> TodoOptional = todoJpaRepository.findById(id);
		return TodoOptional.orElse(null);
	}

	@DeleteMapping(path="/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable Long id) {		
		todoJpaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
