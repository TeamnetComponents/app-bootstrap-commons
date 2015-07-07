package ro.teamnet.bootstrap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * A Role. This entity is used as a {@link GrantedAuthority} and represents an authority.
 */
@Entity
@DiscriminatorValue("APPLICATION")
public class ApplicationRole extends RoleBase {


    @NotNull
    @Column(name = "VERSION")
    private Long version;


    @Column(name = "SORT")
    private Integer order;



    @Column(name = "LOCAL")
    private Short local;


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }



    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }



    public Short getLocal() {
        return local;
    }

    public void setLocal(Short local) {
        this.local = local;
    }


    /* (non-Javadoc)
                  * @see org.springframework.security.core.GrantedAuthority#getAuthority()
                  */
    @Override
    @Transient
    public String getAuthority() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationRole applicationRole = (ApplicationRole) o;

        if (version != null ? !version.equals(applicationRole.version) : applicationRole.version != null) return false;
        if (code != null ? !code.equals(applicationRole.code) : applicationRole.code != null) return false;
        if (description != null ? !description.equals(applicationRole.description) : applicationRole.description != null) return false;
        if (order != null ? !order.equals(applicationRole.order) : applicationRole.order != null) return false;
        if (validFrom != null ? !validFrom.equals(applicationRole.validFrom) : applicationRole.validFrom != null) return false;
        if (validTo != null ? !validTo.equals(applicationRole.validTo) : applicationRole.validTo != null) return false;
        if (active != null ? !active.equals(applicationRole.active) : applicationRole.active != null) return false;
        if (local != null ? !local.equals(applicationRole.local) : applicationRole.local != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (local != null ? local.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "Role{" +
                "id='+" + id + '\'' +
                ", version='" + version + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", order='" + order + '\'' +
                ", validFrom='" + validFrom + '\'' +
                ", validTo='" + validTo + '\'' +
                ", active='" + active + '\'' +
                ", local='" + local + '\'' +
                "}";
    }

}
