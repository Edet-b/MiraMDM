package ng.org.mirabilia.mdm.domain.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN,
    PUBLISHER,
    REGULAR;

    public boolean isEmpty() {
        return false;
    }
}
