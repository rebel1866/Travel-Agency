package by.epamtc.stanislavmelnikov.dao.daoimpl;


import by.epamtc.stanislavmelnikov.dao.connection.ConnectionPool;
import by.epamtc.stanislavmelnikov.dao.daointerface.UserDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.sqlgenerator.SQLGenerator;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.entity.UserRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLUserDao implements UserDao {
    private static final String sqlAuthorization = "select password, login, user_id, user_role_name " +
            "from users inner join user_roles using (user_role_id) where login=? and user_status_id != (select user_status_id " +
            "from user_statuses where user_status_name='DELETED')";
    private static final String addUserSql = "insert users(login, password,first_name, last_name,birth_date, email," +
            " telephone,user_status_id, gender_id, user_role_id) values(?,?,?,?,?,?,?,1," +
            " (select user_gender_id from user_genders where user_gender_des = upper(?))," +
            " (select user_role_id from user_roles where user_role_name =?));";
    private static final String sqlByParams = "select login, user_id, first_name, last_name, user_status_name, " +
            "user_role_name, telephone, email,birth_date, user_gender_des from users u inner join user_roles using (user_role_id) " +
            " inner join user_statuses using (user_status_id) inner join user_genders on gender_id= user_gender_id" +
            " where user_status_id !=3 ";
    private static final String updateUserSQL = "update users set login=?, first_name=?, last_name = ?, " +
            "birth_date=?, email = ?, telephone = ?, gender_id= (select user_gender_id from user_genders" +
            " where user_gender_des=?) where user_id = ?";
    private static final String statusSQL = "select * from user_statuses where user_status_id = " +
            "(select user_status_id from users where user_id=?)";
    private static final String updatePasswordSQL = "update users set password =? where user_id = ?";
    private static final String sqlCountUsers = "select count(*) as amount from users u inner join user_roles using " +
            "(user_role_id) inner join user_statuses using (user_status_id) inner join user_genders on gender_id= user_gender_id " +
            "where user_status_id !=3 ";
    private static final String getRolesSQL = "select * from user_roles";
    private static final String removeUserSQL = "update users left join tour_feedbacks using (user_id) set user_status_id" +
            " = (select user_status_id from user_statuses where user_status_name = 'DELETED'), fbk_status = 'DELETED' where user_id = ?";
    private static final String userRightsSql = "select user_role_rights from user_roles where user_role_name =?";
    private static final String addUserRightsSQL = "insert user_roles( user_role_rights, user_role_name)" +
            "values(?,?)";
    private static final String giveAdminSQL = "update users set user_role_id = ? where user_id=?";
    private static final String blockedSQL = "update users set user_status_id =(select user_status_id from " +
            "user_statuses where user_status_name=?) where user_id=?";

    @Override
    public Map<String, String> authorize(String login) throws DaoException {
        Map<String, String> params = new HashMap<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlAuthorization)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    params.put("login", resultSet.getString("login"));
                    params.put("password", resultSet.getString("password"));
                    params.put("user_id", resultSet.getString("user_id"));
                    params.put("user_role", resultSet.getString("user_role_name"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get user params from db", e);
        }
        connectionPool.releaseConnection(connection);
        return params;
    }


    @Override
    public List<User> findUsersByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlByParams);
        List<User> users = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("user_id");
                    String login = resultSet.getString("login");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String userStatus = resultSet.getString("user_status_name");
                    String userRole = resultSet.getString("user_role_name");
                    String telephone = resultSet.getString("telephone");
                    String birthDate = resultSet.getString("birth_date");
                    String email = resultSet.getString("email");
                    String gender = resultSet.getString("user_gender_des");
                    User user = buildUser(login, firstName, lastName, userStatus, userRole, telephone, id, gender, email, birthDate);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get users from db", e);
        }
        connectionPool.releaseConnection(connection);
        return users;
    }

    @Override
    public int countUsersByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlCountUsers);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        int amount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    amount = resultSet.getInt("amount");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to count users in db", e);
        }
        connectionPool.releaseConnection(connection);
        return amount;
    }

    @Override
    public void addUser(User user) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(addUserSql)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getBirthDate().toString());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getTelephone());
            preparedStatement.setString(8, user.getGender());
            preparedStatement.setString(9, user.getUserRole());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Adding user has not been done");
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to add user in db", e);
        }
        connectionPool.releaseConnection(connection);
    }


    @Override
    public void updateUser(User user) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserSQL)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getBirthDate().toString());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getTelephone());
            preparedStatement.setString(7, user.getGender());
            preparedStatement.setInt(8, (int) user.getUserID());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Updating user has not been done");
            updatePassword(user, connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Exception while trying to rollback operation - updating user", ex);
            }
            throw new DaoException("Exception while trying to update user in db", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void removeUser(int userId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeUserSQL)) {
            preparedStatement.setInt(1, userId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Removing user has not been done");
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to remove user from db", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public String findUserRights(String userRole) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        String userRights = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userRightsSql)) {
            preparedStatement.setString(1, userRole);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userRights = resultSet.getString("user_role_rights");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get user rights", e);
        }
        connectionPool.releaseConnection(connection);
        return userRights;
    }

    @Override
    public void addNewRole(Map<String, String> args) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(addUserRightsSQL)) {
            preparedStatement.setString(1, args.get("rights_list"));
            preparedStatement.setString(2, args.get("role_name"));
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Updating user rights has not been done");
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to update user rights from db", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public List<UserRole> findUserRoles() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        List<UserRole> roles = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getRolesSQL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String userRights = resultSet.getString("user_role_rights");
                    int userRoleId = resultSet.getInt("user_role_id");
                    String userRoleName = resultSet.getString("user_role_name");
                    UserRole userRole = new UserRole();
                    userRole.setUserRoleId(userRoleId);
                    userRole.setUserRoleName(userRoleName);
                    userRole.setUserRoleRights(userRights);
                    roles.add(userRole);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get user roles", e);
        }
        connectionPool.releaseConnection(connection);
        return roles;
    }

    @Override
    public void giveAdminRights(int userId, int userRoleId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(giveAdminSQL)) {
            preparedStatement.setInt(1, userRoleId);
            preparedStatement.setInt(2, userId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Changing role user has not been done");
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to update user role in db", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public Map<String, String> findUserStatus(int userId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Map<String, String> params = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(statusSQL)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    params.put("status_name", resultSet.getString("user_status_name"));
                    params.put("status_rights", resultSet.getString("status_rights_constraints"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get user status from db", e);
        }
        connectionPool.releaseConnection(connection);
        return params;
    }

    @Override
    public void updateUserStatus(String blockedStatus, int userId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(blockedSQL)) {
            preparedStatement.setString(1, blockedStatus);
            preparedStatement.setInt(2, userId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Changing user status has not been done");
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to update user status", e);
        }
        connectionPool.releaseConnection(connection);
    }

    public void updatePassword(User user, Connection connection) throws SQLException, DaoException {
        if (user.getPassword() != null) {
            try (PreparedStatement preparedStatementPs = connection.prepareStatement(updatePasswordSQL)) {
                preparedStatementPs.setString(1, user.getPassword());
                preparedStatementPs.setInt(2, (int) user.getUserID());
                int rows2 = preparedStatementPs.executeUpdate();
                if (rows2 == 0) throw new DaoException("Updating password has not been done");
            }
        }
    }

    public User buildUser(String login, String firstName, String lastName, String userStatus, String userRole, String telephone,
                          String id, String gender, String email, String birthDate) {
        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserStatus(userStatus);
        user.setUserRole(userRole);
        user.setTelephone(telephone);
        user.setUserID(Integer.parseInt(id));
        user.setGender(gender);
        user.setEmail(email);
        user.setBirthDate(LocalDate.parse(birthDate));
        return user;
    }
}