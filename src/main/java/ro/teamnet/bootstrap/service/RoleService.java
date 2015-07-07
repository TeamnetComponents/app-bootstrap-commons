package ro.teamnet.bootstrap.service;


import ro.teamnet.bootstrap.domain.ApplicationRole;
import ro.teamnet.bootstrap.web.rest.dto.RoleDTO;

import java.util.Set;

public interface RoleService extends AbstractService<ApplicationRole,Long>{

    public ApplicationRole getOne(Long id);

    public ApplicationRole update(ApplicationRole applicationRole);

    public ApplicationRole update(ApplicationRole applicationRole, RoleDTO roleDTO);

    public Boolean updateRoleById(Long id, RoleDTO roleDTO);

    public ApplicationRole getOneById(Long id);

    Set<ApplicationRole> getAllWithModuleRights();

}
