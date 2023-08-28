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
import ps.springfinalproject.rest.dto.RoleDto;
import ps.springfinalproject.services.RoleService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/role")
    public String getRolesPage(Model model) {
        model.addAttribute("rolesList", roleService.findAll().stream().map(RoleDto::toDto).toList());
        return "get-roles-page";
    }

    @GetMapping("/role/{id}")
    public String getRolePage(@PathVariable("id") long id, Model model) {
        Optional<Role> roleFromBD = roleService.findById(id);
        if (roleFromBD.isPresent()) {
            model.addAttribute("role", RoleDto.toDto(roleFromBD.get()));
            return "get-role-page";
        }
        return "404";
    }

    @GetMapping("/role/add")
    public String addRolePage(Model model) {
        model.addAttribute("rolesList", roleService.findAll().stream().map(RoleDto::toDto).toList());
        model.addAttribute("roleDto", new RoleDto());
        return "add-role-page";
    }

    @PostMapping("/role/add")
    public String postAddRolePage(@Valid RoleDto roleDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("rolesList", roleService.findAll().stream().map(RoleDto::toDto).toList());
            return "add-role-page";
        }
        roleService.create(RoleDto.fromDto(roleDto));
        return "redirect:/role/add";
    }

    @GetMapping("/role/{id}/edit")
    public String editRolePage(@PathVariable("id") long id, Model model) {
        Optional<Role> roleFromBD = roleService.findById(id);
        if (roleFromBD.isPresent()) {
            model.addAttribute("role", RoleDto.toDto(roleFromBD.get()));
            model.addAttribute("rolesList", roleService.findAll().stream().map(RoleDto::toDto).toList());
            return "edit-role-page";
        }
        return "404";
    }

    // PathVariable "id" might be redundant here, because you can transfer different "id" via roleDto.
    @PostMapping("/role/{id}/edit")
    public String postEditRolePage(@PathVariable("id") long id, RoleDto roleDto) {
        Optional<Role> roleFromBD = roleService.findById(Long.parseLong(roleDto.getId()));
        if (roleFromBD.isPresent()) {
            roleService.update(RoleDto.fromDto(roleDto));
            return "redirect:/role";
        }
        return "404";
    }


    @GetMapping("/role/{id}/delete")
    public String deleteRolePage(@PathVariable("id") long id, Model model) {
        Optional<Role> roleFromBD = roleService.findById(id);
        if (roleFromBD.isPresent()) {
            model.addAttribute("role", RoleDto.toDto(roleFromBD.get()));
            model.addAttribute("rolesList", roleService.findAll().stream().map(RoleDto::toDto).toList());
            return "delete-role-page";
        }
        return "404";
    }


    @PostMapping("/role/{id}/delete")
    public String postDeleteRolePage(RoleDto roleDto) {
        Optional<Role> roleFromBD = roleService.findById(Long.parseLong(roleDto.getId()));
        System.out.println("Deleting role... " + RoleDto.fromDto(roleDto)); // debug
        if (roleFromBD.isPresent()) {
            roleService.delete(RoleDto.fromDto(roleDto));
            return "redirect:/role";
        }
        return "404";
    }
}
