package ro.teamnet.bootstrap.service;


import ro.teamnet.bootstrap.domain.Module;
import ro.teamnet.bootstrap.web.rest.dto.ModuleDTO;

import java.util.Set;

public interface ModuleService extends AbstractService<Module,Long>{

    public Module getOne(Long id);

    public boolean update(Long id, ModuleDTO moduleDTO);

//    public Module saveModule(ModuleDTO moduleDTO);

    public Set<Module> getAllModulesWithModuleRights();

}
