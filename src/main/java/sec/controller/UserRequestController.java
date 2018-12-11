package sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payloads.ContainerGetRequest;
import payloads.HistoryOfDayRequest;
import sec.model.Container;
import sec.model.History;
import sec.model.User;
import sec.repo.ContainerRepository;
import sec.repo.HistoryRepository;
import sec.repo.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/get")
public class UserRequestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping("/container")
    @PreAuthorize("hasRole('USER')")
    public List<Container> getUserContainers(@Valid @RequestBody ContainerGetRequest containerGetRequest) {

        return containerRepository.getContainer(containerGetRequest.getContainerId());
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('USER')")
    public List<History> getContainerHistory(@Valid @RequestBody ContainerGetRequest containerGetRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getName());
        return historyRepository.getHistoryContainer(user.get().getId(), containerGetRequest.getContainerId());
    }

    @GetMapping("/history-day")
    @PreAuthorize("hasRole('USER')")
    public List<History> getContainerHistoryOfDay(@Valid @RequestBody HistoryOfDayRequest historyOfDayRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getName());
        String startDateTime = historyOfDayRequest.getDate() + " 00:00:00";
        String endDateTime = historyOfDayRequest.getDate() + " 23:59:59";
        return historyRepository.getHistoryOfDayContainer(user.get().getId(), historyOfDayRequest.getContainerId(), startDateTime, endDateTime);
    }
}
