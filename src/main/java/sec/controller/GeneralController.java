package sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import payloads.AccessingContainersRequest;
import payloads.ApiResponse;
import payloads.ContainerRequest;
import payloads.HistoryRequest;
import sec.model.AccessingContainers;
import sec.model.Container;
import sec.model.History;
import sec.model.User;
import sec.repo.AccessingContainersRepository;
import sec.repo.ContainerRepository;
import sec.repo.HistoryRepository;
import sec.repo.UserRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

	@GetMapping("/user-containers")
	public List<Container> getUserContainers() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> currentUser = userRepository.findByUsername(auth.getName());
		return containerRepository.getUserContainers(currentUser.get().getId());
	}

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

	@PostMapping("/add/event")
	public ResponseEntity<?> postEventFromContainer(@Valid @RequestBody HistoryRequest historyRequest){

		//Creating event
		History event = new History(historyRequest.getContainerId(), historyRequest.getEventDateTime(), historyRequest.getEventText());

		History result = historyRepository.save(event);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/event-container/{id}")
				.buildAndExpand(result.getEventId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Event to the history has been successfully added!"));
	}
}
