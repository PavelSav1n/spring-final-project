package ps.springfinalproject.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ps.springfinalproject.domain.Role;
import ps.springfinalproject.domain.User;
import ps.springfinalproject.rest.dto.RoleDto;
import ps.springfinalproject.rest.dto.UserDto;
import ps.springfinalproject.services.RoleService;
import ps.springfinalproject.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/user")
    public String getUsersPage(Model model) {
        List<UserDto> userDtoList = userService.findAll().stream().map(UserDto::toDto).collect(Collectors.toList());
        model.addAttribute("userDtoList", userDtoList);
        return "get-users-page";
    }

    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable long id, Model model) {
        Optional<User> userFromBD = userService.findById(id);
        if (userFromBD.isPresent()) {
            model.addAttribute("userDto", UserDto.toDto(userFromBD.get()));
            return "get-user-page";
        }
        return "404";
    }

    @GetMapping("/user/add")
    public String addUserPage(Model model) {
        List<UserDto> userDtoList = userService.findAll().stream().map(UserDto::toDto).collect(Collectors.toList());
        model.addAttribute("userDtoList", userDtoList);
        List<RoleDto> roleDtoList = roleService.findAll().stream().map(RoleDto::toDto).toList();
        model.addAttribute("roleDtoList", roleDtoList);
        return "add-user-page";
    }

    @PostMapping("/user/add")
    public String postAddUserPage(UserDto userDto) {
        User userFromDto = UserDto.fromDto(userDto);
        Optional<Role> roleFromPersist = roleService.findById(userFromDto.getRole().getId());
        if (roleFromPersist.isPresent()) {
            User userToSave = new User(userFromDto.getName(), userFromDto.getEmail(), userFromDto.getPassword(), userFromDto.getBirthDate(), roleFromPersist.get());
            userService.create(userToSave);
            return "redirect:/user/add";
        }
        return "404";
    }

    @GetMapping("/user/{id}/edit")
    public String editUserPage(@PathVariable("id") long id, Model model) {
        Optional<User> userFromBD = userService.findById(id);
        if (userFromBD.isPresent()) {
            model.addAttribute("userDto", userFromBD.get()); // Sending userDto to model.
            List<UserDto> userDtoList = userService.findAll().stream().map(UserDto::toDto).toList();
            model.addAttribute("userDtoList", userDtoList); // Sending userDtoList to model.
            return "edit-user-page";
        }
        return "404";
    }

    @PostMapping("/user/{id}/edit")
    public String postEditUserPage(@PathVariable("id") long id, UserDto userDto) {
        User userFromDto = UserDto.fromDto(userDto);
        Optional<User> userFromBD = userService.findById(Long.parseLong(userDto.getId()));
        // Here we can add user role existence check like in postAddUserPage if needed. For now, it's in constructor.
        if (userFromBD.isPresent()) {
            User userToSave = new User(userFromDto.getName(), userFromDto.getEmail(), userFromDto.getPassword(), userFromDto.getBirthDate(), roleService.findById(userFromDto.getRole().getId()).get());
            userService.create(userToSave);
            return "redirect:/user";
        }
        return "404";
    }


}