package sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sec.model.Container;
import sec.model.History;
import sec.model.User;
import sec.repo.ContainerRepository;
import sec.repo.HistoryRepository;
import sec.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserRequestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping("/user-details")
    @PreAuthorize("hasRole('USER')")
    public List<String> getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> currentUser = userRepository.findByUsername(auth.getName());
        User user = userRepository.getUserDetails(currentUser.get().getId());
        List<String> listDetails = new ArrayList<String>();
        listDetails.add(user.getName());
        listDetails.add(user.getUsername());
        listDetails.add(user.getEmail());
        return listDetails;
    }


    @GetMapping("/container")
    @PreAuthorize("hasRole('USER')")
    public List<Container> getUserContainers(@RequestParam("containerId") Long containerId) {

        return containerRepository.getContainer(containerId);
    }

    @GetMapping("/history-container")
    @PreAuthorize("hasRole('USER')")
    public List<History> getContainerHistory(@RequestParam("containerId") Long containerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getName());
        return historyRepository.getHistoryContainer(user.get().getId(), containerId);
    }

    @GetMapping("/history-container-day")
    @PreAuthorize("hasRole('USER')")
    public List<History> getContainerHistoryOfDay(@RequestParam("containerId") Long containerId, @RequestParam("date") String date) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getName());
        String startDateTime = date + " 00:00:00";
        String endDateTime = date + " 23:59:59";
        return historyRepository.getHistoryOfDayContainer(user.get().getId(), containerId, startDateTime, endDateTime);
    }

    @GetMapping("/user-containers")
    @PreAuthorize("hasRole('USER')")
    public List<Container> getUserContainers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> currentUser = userRepository.findByUsername(auth.getName());
        return containerRepository.getUserContainers(currentUser.get().getId());
    }
}
