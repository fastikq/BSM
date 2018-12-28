package sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import payloads.AccessingContainersRequest;
import payloads.ApiResponse;
import payloads.ContainerRequest;
import payloads.UpdateUserRequest;
import sec.model.AccessingContainers;
import sec.model.Container;
import sec.model.User;
import sec.repo.AccessingContainersRepository;
import sec.repo.ContainerRepository;
import sec.repo.HistoryRepository;
import sec.repo.UserRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bsm")
public class GeneralController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContainerRepository containerRepository;

	@Autowired
	private AccessingContainersRepository accessingContainersRepository;

	@Autowired
	private HistoryRepository historyRepository;

	@PostMapping("/add/container")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addContainer(@Valid @RequestBody ContainerRequest containerRequest){

		//Creating container's
		Container container = new Container(containerRequest.getInfo(), containerRequest.getDateRegistration());

		Container result = containerRepository.save(container);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/containers/{id}")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Container added successfully"));
	}

    @GetMapping("/containers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Container> getContainers(){
        return containerRepository.getContainers();
    }


    @GetMapping("/accessing")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccessingContainers> getAccessing(){
        return accessingContainersRepository.getAccessing();
    }

	@PostMapping("/add/accessing-container")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addAccessToContainer(@Valid @RequestBody AccessingContainersRequest accessingContainersRequest){

		//Creating access
		AccessingContainers access = new AccessingContainers(accessingContainersRequest.getUserId(), accessingContainersRequest.getContainerId());

		AccessingContainers result = accessingContainersRepository.save(access);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/access-container/{id}")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Access to the container has been successfully given!"));
	}

	@PostMapping("/update/container")
	@PreAuthorize("hasRole('ADMIN')")
	public String updateContainer(@RequestParam("id") Long id, @RequestParam("info") String newInfo, @RequestParam("date") String newDate){
		containerRepository.updateContainer(id, newInfo, newDate);
		return "OK";
	}

	@DeleteMapping("/delete/container")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteContainer(@RequestParam("id") Long id){
		containerRepository.deleteContainer(id);
		return "OK";
	}

	@PostMapping("/update/accessing-containers")
	@PreAuthorize("hasRole('ADMIN')")
	public String updateAccessingContainers(@RequestParam("id") Long id, @RequestParam("userId") Long userId, @RequestParam("containerId") Long containerId){
		accessingContainersRepository.updateAccessingContainers(id, userId, containerId);
		return "OK";
	}

	@DeleteMapping("/delete/accessing-containers")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteAccessingContainers(@RequestParam("id") Long id){
		accessingContainersRepository.deleteAccessingContainers(id);
		return "OK";
	}

	@GetMapping("/users/request-register")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getGuestUsers(){
	    return userRepository.getGuestUsers();
    }


    @PostMapping("/update/request-register")
    @PreAuthorize("hasRole('ADMIN')")
    public String acceptRegistrationRequest(@RequestParam("user_id") Long user_id){
	    userRepository.acceptRegistrationRequest(user_id);
	    return "OK";
    }

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getUsers(){
		return userRepository.getUsers();
	}

    @PostMapping("/update/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest){
	    userRepository.updateUser(updateUserRequest.getId(), updateUserRequest.getName(), updateUserRequest.getUsername(), updateUserRequest.getEmail());
	    return "OK";
    }
}
