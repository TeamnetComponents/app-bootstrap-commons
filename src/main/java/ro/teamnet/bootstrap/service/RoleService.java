package ro.teamnet.bootstrap.service;


import ro.teamnet.bootstrap.domain.Role;
import ro.teamnet.bootstrap.web.rest.dto.RoleDTO;

import java.util.Set;

public interface RoleService extends AbstractService<Role,Long>{

    public Role getOne(Long id);

    public Role update(Role role);

    public Role update(Role role, RoleDTO roleDTO);

    public Role getOneById(Long id);

    Set<Role> getAllWithModuleRights();

}
