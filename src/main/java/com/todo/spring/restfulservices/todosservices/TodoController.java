package com.todo.spring.restfulservices.todosservices;

import java.net.URI;
import java.util.List;

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
import com.todo.spring.restfulservices.todosservices.service.TodoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping(path = "/users/{username}/todos")
	public List<Todo> getTodos() {
		return todoService.findAll();
	}

	@PostMapping(path="/users/{username}/todos")
	public  ResponseEntity<Void> createTodo(@RequestBody Todo todoBean) {
		Todo createdTodo = todoService.createTodo(todoBean);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdTodo.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(path="/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, 
			@PathVariable int id, @RequestBody Todo todoBean) {
		todoService.createTodo(todoBean);
		return new ResponseEntity<Todo>(todoBean, HttpStatus.OK);
	}

	@GetMapping(path="/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable Long id) {
		return todoService.findById(id);
	}

	@DeleteMapping(path="/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable Long id) {
		Todo todo = todoService.deleteById(id);

		if(todo != null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.noContent().build();
	}
}
