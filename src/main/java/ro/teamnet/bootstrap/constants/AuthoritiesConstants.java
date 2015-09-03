package ro.teamnet.bootstrap.constants;

import java.lang.String;
/**
 * Constante utilizate de modulul de securitate pentru rolurile default propuse de platforma
 * ADMIN - RoleAdmin
 * USER - RoleUser
 * ANONYMOUS - ROLE_ANONYMOUS
 *
 */
public final class AuthoritiesConstants {

    private AuthoritiesConstants() {
    }

    //Rolul de ADMIN propus de platforma. Userii ce vor avea asociat acest rol, vor putea sa acceseze toate functionalitatile de ADMIN
    public static final String ADMIN = "ROLE_ADMIN";

    //Rol de utilizator propus de platforma. Utilizatorii ce vor avea acest rol vor putea sa acceseze functionalitati la nivel de USER
    public static final String USER = "ROLE_USER";

    //Rol de utilizator anonim. Utlizatorii ce au acest rol vor putea accesa functionalitatile pentru rolul de utilizator anonim
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
}
