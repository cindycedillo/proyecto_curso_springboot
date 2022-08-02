package com.cindycedillo.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cindycedillo.app.entity.User;
import com.cindycedillo.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//Crear usuario
	@PostMapping
	public ResponseEntity<?> create(@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}
	
	//Listar usuario por id
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id){
		Optional<User> oUser = userService.findyById(id);
		
		if(!oUser.isPresent()) {
			
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oUser);
	}
	
	//Actualizar usuario
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id){
		Optional<User> users = userService.findyById(id);
		
		if(!users.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		users.get().setName(user.getName());
		users.get().setSurname(user.getSurname());
		users.get().setEmail(user.getEmail());
		users.get().setEnabled(user.getEnabled());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(users.get()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		if(userService.findyById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		userService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	//Listar todos los usuarios
	@GetMapping
	public List<User> readAll(){
		List<User> users = StreamSupport.stream(userService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return users;
	}
	

}
