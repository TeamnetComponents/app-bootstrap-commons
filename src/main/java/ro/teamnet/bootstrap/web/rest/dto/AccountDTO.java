package ro.teamnet.bootstrap.web.rest.dto;

import org.springframework.security.core.GrantedAuthority;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.domain.ApplicationRole;
import ro.teamnet.bootstrap.domain.Module;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.domain.util.ModuleRightSource;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AccountDTO {

    private long id;

    @Pattern(regexp = "^[a-z0-9]*$")
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String langKey;
    private String gender;
    private Boolean activated;
    private List<RoleDTO> roles = new ArrayList<>();
    private HashMap<String, ModuleRightDTO> moduleRights = new HashMap<>();

    public AccountDTO() {
    }

    public AccountDTO(Account account,Collection<GrantedAuthority> moduleRightSet){
        this.id = account.getId();
        this.login = account.getLogin();
        this.password = account.getPassword();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.email = account.getEmail();
        this.langKey = account.getLangKey();
        this.gender = account.getGender();

        for(GrantedAuthority grantedAuthority: moduleRightSet) {
            if(grantedAuthority instanceof ModuleRight && ((ModuleRight)grantedAuthority).getModule() != null){
                ModuleRight mr=(ModuleRight)grantedAuthority;
                moduleRights.put(
                        mr.getModule().getCode()+"_"+mr.getModuleRightCode(),
                        loadModuleRightDTO(mr, ModuleRightSource.ACCOUNT.name())
                );
            }else if(grantedAuthority instanceof ApplicationRole){
                ApplicationRole applicationRole = (ApplicationRole) grantedAuthority;
                roles.add(new RoleDTO(applicationRole.getId(), applicationRole.getVersion(), applicationRole.getCode(), applicationRole.getDescription(),
                        applicationRole.getOrder(), applicationRole.getValidFrom(), applicationRole.getValidTo(), applicationRole.getActive(), applicationRole.getLocal(), null));
            }
        }
    }

    private ModuleRightDTO loadModuleRightDTO(ModuleRight mr, String source) {
        Module module = mr.getModule();

        ModuleDTO moduleDTO = new ModuleDTO(module.getId(), module.getVersion(), module.getCode(),
                module.getDescription(), module.getType(), module.getParentModule(), null);

        return new ModuleRightDTO(mr.getId(), mr.getVersion(), mr.getRight(), moduleDTO, source);
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getLangKey() {
        return langKey;
    }

    public Collection<RoleDTO> getRoles() {
        return roles;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getGender() {
        return gender;
    }

    public HashMap<String, ModuleRightDTO> getModuleRights() {
        return moduleRights;
    }

    @Override
    public String toString() {
        return "AccountDTO{" + "id='" + id + '\'' + "login='" + login + '\'' + ", password='" + password + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", langKey='" + langKey + '\'' + ", roles=" + roles + ", moduleRights=" + moduleRights + '}';
    }
}
