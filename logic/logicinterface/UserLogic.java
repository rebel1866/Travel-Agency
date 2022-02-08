package by.epamtc.stanislavmelnikov.logic.logicinterface;

import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.entity.UserRole;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserLogic {
    User addUser(Map<String, String> params) throws LogicException;

    Map<String, String> signIn(String login, String password) throws LogicException;

    void signOut(HttpServletRequest request) throws LogicException;

    List<User> findUsersByParams(Map<String, String> params) throws LogicException;

    void updateUser(Map<String, String> params) throws LogicException;

    int countUsersByParams(Map<String, String> paramsCount) throws LogicException;

    void removeUser(int userId) throws LogicException;

    void resetPassword(String login, String email) throws LogicException;

    List<String> findUserRights(String userRole) throws LogicException;

    void addNewRole(Map<String, String> args) throws LogicException;

    List<UserRole> findUserRoles() throws LogicException;

    void giveAdminRights(int userId, int userRoleId) throws LogicException;

    String confirmRegistration(String email, String firstName, String lastName) throws LogicException;

    Map<String, String> findUserStatus(int userId) throws LogicException;

    void blockUser(int userId) throws LogicException;
}
