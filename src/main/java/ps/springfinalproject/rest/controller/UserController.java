package ps.springfinalproject.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import java.util.Map;
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
        model.addAttribute("userDtoList", userService.findAll().stream().map(UserDto::toDto).toList());
        model.addAttribute("roleDtoList", roleService.findAll().stream().map(RoleDto::toDto).toList());
        model.addAttribute("userDto", new UserDto()); // To use th:field in "view"
        return "add-user-page";
    }

    @PostMapping("/user/add")
    public String postAddUserPage(@Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userDtoList", userService.findAll().stream().map(UserDto::toDto).toList());
            model.addAttribute("roleDtoList", roleService.findAll().stream().map(RoleDto::toDto).toList());
            // UserDto() object is already in model, so we don't need to add it here to use th:field in "view"

            if (userService.findByEmail(userDto.getEmail()).isPresent()) {
                result.rejectValue("email", null, "There is already an account registered with that email");
            }
            if (!userDto.getPassword().equals(userDto.getPassword2())) {
                result.rejectValue("password2", null, "Passwords must match");
            }

            Map<String, Object> model1 = result.getModel();
            int i = 0;
            for (Map.Entry<String, Object> stringObjectEntry : model1.entrySet()) {
                System.out.println(i++);
                System.out.println("key string:" + stringObjectEntry.getKey() + " | value object: " + stringObjectEntry.getValue());
            }

            return "add-user-page";
        }
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
            model.addAttribute("userDto", UserDto.toDto(userFromBD.get())); // Sending userDto to model.
            model.addAttribute("userDtoList", userService.findAll().stream().map(UserDto::toDto).toList()); // Sending userDtoList to model.
            model.addAttribute("roleDtoList", roleService.findAll().stream().map(RoleDto::toDto).toList()); // Sending roleDtoList to model.
            return "edit-user-page";
        }
        return "404";
    }

    @PostMapping("/user/{id}/edit")
    public String postEditUserPage(@PathVariable("id") long id, @Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // userDto from form with all data is already in the model
            model.addAttribute("userDtoList", userService.findAll().stream().map(UserDto::toDto).toList());
            model.addAttribute("roleDtoList", roleService.findAll().stream().map(RoleDto::toDto).toList());

            // TODO: psw double check

            return "edit-user-page";
        }
        User userFromDto = UserDto.fromDto(userDto);
        Optional<User> userFromBD = userService.findById(Long.parseLong(userDto.getId()));
        // Here we can add user role existence check like in postAddUserPage if needed. For now, it's in constructor.
        if (userFromBD.isPresent()) {
            userService.update(new User(
                    userFromBD.get().getId(),
                    userFromDto.getName(),
                    userFromDto.getEmail(),
                    userFromDto.getPassword(),
                    userFromDto.getBirthDate(),
                    roleService.findById(userFromDto.getRole().getId()).get()));
            return "redirect:/user";
        }
        return "404";
    }

    @GetMapping("user/{id}/delete")
    public String deleteUserPage(@PathVariable("id") long id, Model model) {
        Optional<User> userFromBD = userService.findById(id);
        if (userFromBD.isPresent()) {
            model.addAttribute("userDto", UserDto.toDto(userFromBD.get()));
            model.addAttribute("userDtoList", userService.findAll().stream().map(UserDto::toDto).toList());
            return "delete-user-page";
        }
        return "404";
    }

    @PostMapping("user/{id}/delete")
    public String postDeleteUserPage(UserDto userDto) {
        System.out.println("userDto.getRoleId() = " + userDto.getRoleId());
        Optional<User> userFromBD = userService.findById(Long.parseLong(userDto.getId()));
        if (userFromBD.isPresent()) {
            userService.delete(userFromBD.get());
            return "redirect:/user";
        }
        return "404";
    }
}
