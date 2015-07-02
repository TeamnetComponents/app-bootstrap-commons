package ro.teamnet.bootstrap.service;


import ro.teamnet.bootstrap.domain.Role;
import ro.teamnet.bootstrap.web.rest.dto.RoleDTO;

public interface RoleService extends AbstractService<Role,Long>{

    public Role getOne(Long id);

    public Role update(Role role);

    public void update(Role role, RoleDTO roleDTO);

    public Boolean updateRoleById(Long id, RoleDTO roleDTO);

}
