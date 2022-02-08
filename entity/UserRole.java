package by.epamtc.stanislavmelnikov.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserRole implements Serializable {
    private static final long SerialVersionUID = 6616416541564156145L;
    private int userRoleId;
    private String userRoleName;
    private String userRoleRights;

    public UserRole(){

    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getUserRoleRights() {
        return userRoleRights;
    }

    public void setUserRoleRights(String userRoleRights) {
        this.userRoleRights = userRoleRights;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "userRoleId=" + userRoleId +
                ", userRoleName='" + userRoleName + '\'' +
                ", userRoleRights='" + userRoleRights + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return userRoleId == userRole.userRoleId && Objects.equals(userRoleName, userRole.userRoleName) && Objects.equals
                (userRoleRights, userRole.userRoleRights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleId, userRoleName, userRoleRights);
    }
}
