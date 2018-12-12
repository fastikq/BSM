package sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import payloads.AccessingContainersRequest;
import payloads.ApiResponse;
import payloads.ContainerRequest;
import sec.model.AccessingContainers;
import sec.model.Container;
import sec.repo.AccessingContainersRepository;
import sec.repo.ContainerRepository;
import sec.repo.HistoryRepository;
import sec.repo.UserRepository;

import javax.validation.Valid;
import java.net.URI;

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

}
