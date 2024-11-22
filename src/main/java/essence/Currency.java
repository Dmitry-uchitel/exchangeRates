package essence;

import java.util.Objects;

public class Currency {
    private Integer id;
    private String code;
    private String fullname;
    private String sign;

    public Currency(Integer id, String code, String fullname, String sign) {
        this.id = id;
        this.code = code;
        this.fullname = fullname;
        this.sign = sign;
    }

    public Integer getId() {
        return id;
    }
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("id: %d, code: %s, fullname: %s, sign: %s", this.id, this.code, this.fullname, this.sign);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(((Currency) obj).getId(), id);
    }
}
