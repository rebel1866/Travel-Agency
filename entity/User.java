package by.epamtc.stanislavmelnikov.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Serializable {
    private static final long SerialVersionUID = 256416541564156502L;
    private int userID;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String email;
    private String userRole;
    private String telephone;
    private String userStatus;
    private UserRole role;

    public User() {

    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", userRole='" + userRole + '\'' +
                ", telephone='" + telephone + '\'' +
                ", userStatus='" + userStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID && Objects.equals(login, user.login) && Objects.equals(password,
                user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName,
                user.lastName) && Objects.equals(birthDate, user.birthDate) && Objects.equals(gender,
                user.gender) && Objects.equals(email, user.email) && Objects.equals(userRole, user.userRole)
                && Objects.equals(telephone, user.telephone) && Objects.equals(userStatus, user.userStatus) && Objects.
                equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, login, password, firstName, lastName, birthDate, gender, email, userRole, telephone,
                userStatus, role);
    }
}
