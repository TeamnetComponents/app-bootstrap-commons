package ro.teamnet.bootstrap.domain.util;


import org.springframework.http.HttpMethod;

/**
 * This Enum contains all the CRUD permissions and their numeric representations
 */
public enum ModuleRightTypeEnum {

    NO_ACCESS((short) 0, ""),
    READ_ACCESS((short) 1, HttpMethod.GET.name()),
    WRITE_ACCESS((short) 2, HttpMethod.PUT.name()),
    INSERT_ACCESS((short) 4, HttpMethod.POST.name()),
    DELETE_ACCESS((short) 8, HttpMethod.DELETE.name());

    private final String httpMethod;
    private short right;

    public short getRight() {
        return right;
    }

    private ModuleRightTypeEnum(short right, String httpMethod) {
        this.right = right;
        this.httpMethod = httpMethod;
    }

    public static String getCodeByValue(int moduleRight) {
        String code = null;
        for (ModuleRightTypeEnum moduleRightTypeEnum : values()) {
            if (moduleRightTypeEnum.getRight() == moduleRight) {
                code = moduleRightTypeEnum.name();
            }
        }
        return code;
    }

    public static ModuleRightTypeEnum findByHttpMethod(String httpMethod) {
        for (ModuleRightTypeEnum moduleRightType : values()) {
            if (moduleRightType.httpMethod.equals(httpMethod)) {
                return moduleRightType;
            }
        }
        return null;
    }

}
