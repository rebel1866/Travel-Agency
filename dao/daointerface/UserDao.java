package by.epamtc.stanislavmelnikov.dao.daointerface;


import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.entity.UserRole;

import java.util.List;
import java.util.Map;

public interface UserDao {
    Map<String, String> authorize(String login) throws DaoException;

    List<User> findUsersByParams(Map<String, String> params) throws DaoException;

    void addUser(User user) throws DaoException;

    void updateUser(User user) throws DaoException;

    int countUsersByParams(Map<String, String> paramsCount) throws DaoException;

    void removeUser(int userId) throws DaoException;

    String findUserRights(String userRole) throws DaoException;

    void addNewRole(Map<String, String> args) throws DaoException;

    List<UserRole> findUserRoles() throws DaoException;

    void giveAdminRights(int userId, int userRoleId) throws DaoException;

    Map<String, String> findUserStatus(int userId) throws DaoException;

    void updateUserStatus(String blockedStatus, int userId) throws DaoException;
}
