package romario.cabo.com.br.consulta_api.model.enums;

public enum ProfileEnum {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private final int cod;
    private final String description;

    ProfileEnum(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static ProfileEnum toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (ProfileEnum profileEnum : ProfileEnum.values()) {
            if (cod.equals(profileEnum.getCod())) {
                return profileEnum;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
