package ru.geekbrains.musicportal.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.musicportal.entity.user.Role;
import ru.geekbrains.musicportal.enums.UserRoleEnum;
import ru.geekbrains.musicportal.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void fillTableByAll() {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            Role newRole = new Role();
            newRole.setName(role.name());
            newRole.setCreationDate(LocalDateTime.now());
            newRole.setLastUpdate(LocalDateTime.now());
            roleRepository.save(newRole);
        }
    }
}
