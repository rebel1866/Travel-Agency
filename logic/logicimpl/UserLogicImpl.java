package by.epamtc.stanislavmelnikov.logic.logicimpl;

import by.epamtc.stanislavmelnikov.dao.daointerface.UserDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.factory.DaoFactory;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.entity.UserRole;
import by.epamtc.stanislavmelnikov.logic.email.EmailManager;
import by.epamtc.stanislavmelnikov.logic.passwordhashing.PasswordHashing;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserLogicImpl implements UserLogic {
    private static final String key = "fields.add.user";
    private static final String keyUpdate = "fields.user";

    @Override
    public User addUser(Map<String, String> params) throws LogicException {
        Validation.excludeInjections(params, key);
        String login = params.get("login");
        String password = params.get("password");
        String rPassword = params.get("r_password");
        String firstName = params.get("first_name");
        String lastName = params.get("last_name");
        String birthDate = params.get("birth_date");
        String email = params.get("email");
        String telephone = params.get("telephone");
        String gender = params.get("gender");
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        try {
            validateFields(login, password, rPassword, firstName, lastName, email, telephone, birthDate);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        String hashedPassword = PasswordHashing.hashPassword(password);
        User user = buildUser(login, hashedPassword, firstName, lastName, birthDate, email, telephone, gender);
        try {
            userDao.addUser(user);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return user;
    }


    @Override
    public Map<String, String> signIn(String login, String enteredPassword) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        login = Validation.excludeInjections(login);
        enteredPassword = Validation.excludeInjections(enteredPassword);
        Map<String, String> params;
        try {
            params = userDao.authorize(login);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        if (params.size() == 0) throw new LogicException("Логин не существует");
        String dbPassword = params.get("password");
        String enteredHashedPassword = PasswordHashing.hashPassword(enteredPassword, dbPassword);
        if (!dbPassword.equals(enteredHashedPassword)) throw new LogicException("Неверный пароль");
        params.remove("password");
        return params;
    }

    @Override
    public void signOut(HttpServletRequest request) throws LogicException {
        HttpSession session = request.getSession();
        Map<String, String> authorization = (Map<String, String>) session.getAttribute("authorization");
        if (authorization != null) {
            session.removeAttribute("authorization");
        } else {
            throw new LogicException("Authorization has not been done yet");
        }
    }

    @Override
    public List<User> findUsersByParams(Map<String, String> params) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        List<User> users;
        Validation.excludeInjections(params);
        try {
            users = userDao.findUsersByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public int countUsersByParams(Map<String, String> paramsCount) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Validation.excludeInjections(paramsCount);
        int amount;
        try {
            amount = userDao.countUsersByParams(paramsCount);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return amount;
    }

    @Override
    public void updateUser(Map<String, String> params) throws LogicException {
        Validation.excludeInjections(params, keyUpdate);
        int userId = Integer.parseInt(params.get("user_id"));
        String login = params.get("login");
        String password = params.get("old-password");
        String newPassword1 = params.get("new-password1");
        String newPassword2 = params.get("new-password2");
        String firstName = params.get("first_name");
        String lastName = params.get("last_name");
        String birthDate = params.get("birth_date");
        String email = params.get("email");
        String telephone = params.get("telephone");
        String gender = params.get("gender");
        String oldLogin = params.get("old_login");
        validateNewLogin(oldLogin, login);
        validateNewPassword(login, password, newPassword1, newPassword2);
        validateFields(firstName, lastName, email, telephone, birthDate);
        User user;
        if (password.equals("")) {
            user = buildUser(login, firstName, lastName, birthDate, email, telephone, gender, userId);
        } else {
            String newHashedPassword = PasswordHashing.hashPassword(newPassword1);
            user = buildUser(login, firstName, lastName, birthDate, email, telephone, gender, newHashedPassword, userId);
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.updateUser(user);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void removeUser(int userId) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.removeUser(userId);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void resetPassword(String login, String email) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        List<User> users;
        Map<String, String> params = new HashMap<>();
        params.put("restrictions", "login");
        params.put("login", login);
        Validation.excludeInjections(params);
        try {
            users = userDao.findUsersByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        if (users.size() == 0) throw new LogicException("Login does not exist");
        User user = users.get(0);
        String dbEmail = user.getEmail();
        if (!dbEmail.equals(email)) throw new LogicException("Wrong email");
        String newPassword = generatePassword();
        String hashedNewPass = PasswordHashing.hashPassword(newPassword);
        user.setPassword(hashedNewPass);
        try {
            userDao.updateUser(user);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        String message = "Ваш пароль был сброшен. Новый пароль: " + newPassword;
        String subject = "Ваш пароль успешно изменен";
        EmailManager.sendEmail(dbEmail, message, subject);
    }

    @Override
    public List<String> findUserRights(String userRole) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        String userRights;
        try {
            userRights = userDao.findUserRights(userRole);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return Arrays.stream(userRights.split(",")).map(String::trim).collect(Collectors.toList());
    }

    @Override
    public void addNewRole(Map<String, String> args) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.addNewRole(args);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public List<UserRole> findUserRoles() throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        List<UserRole> roles;
        try {
            roles = userDao.findUserRoles();
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return roles;
    }

    @Override
    public void giveAdminRights(int userId, int userRoleId) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.giveAdminRights(userId, userRoleId);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public String confirmRegistration(String email, String firstName, String lastName) throws LogicException {
        String subject = "Подтверждение регистрации на BEST-TOURS.BY";
        String key = generatePassword();
        String link = "http://localhost:8080/travel-agency/executor?command=registration&subcommand=main&key=" + key;
        String message = "Поздравляем с успешной регистрацией! Для завершения регистрации перейдите по ссылке: " + link;
        EmailManager.sendEmail(email, message, subject);
        return key;
    }

    @Override
    public Map<String, String> findUserStatus(int userId) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Map<String, String> userStatus;
        try {
            userStatus = userDao.findUserStatus(userId);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return userStatus;
    }

    @Override
    public void blockUser(int userId) throws LogicException {
        String blockedStatus = "BLOCKED";
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.updateUserStatus(blockedStatus, userId);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    public void validateNewPassword(String login, String enteredPassword, String newPassword1, String newPassword2) throws LogicException {
        if (!enteredPassword.equals("")) {
            DaoFactory daoFactory = DaoFactory.getInstance();
            UserDao userDao = daoFactory.getUserDao();
            String dbPassword;
            try {
                Map<String, String> map = userDao.authorize(login);
                dbPassword = map.get("password");
            } catch (DaoException e) {
                throw new LogicException(e.getMessage(), e);
            }
            String enteredHashedPassword = PasswordHashing.hashPassword(enteredPassword, dbPassword);
            if (!dbPassword.equals(enteredHashedPassword)) throw new LogicException("incorrect current password");
            if (!newPassword1.equals(newPassword2)) throw new LogicException("Passwords does not match");
            if (!Validation.isCorrectPassword(newPassword1)) throw new LogicException("Incorrect password");
        }
    }

    public void validateNewLogin(String oldLogin, String login) throws LogicException {
        if (!oldLogin.equals(login)) {
            try {
                if (!Validation.isCorrectLogin(login)) throw new LogicException("New login is already exists");
            } catch (DaoException e) {
                throw new LogicException(e.getMessage(), e);
            }
        }
    }

    public void validateFields(String firstName, String lastName, String email, String telephone, String birthDate)
            throws LogicException {
        if (Validation.isEmpty(firstName)) throw new LogicException("First name is empty");
        if (Validation.isEmpty(lastName)) throw new LogicException("Last name is empty");
        if (!Validation.isCorrectEmail(email)) throw new LogicException("Incorrect email");
        if (!Validation.isCorrectTelephone(telephone)) throw new LogicException("Incorrect telephone number");
        if (!Validation.isCorrectDate(birthDate)) throw new LogicException("Incorrect birth date");
    }

    public void validateFields(String login, String password, String rPassword, String firstName,
                               String lastName, String email, String telephone, String birthDate) throws LogicException, DaoException {
        if (!Validation.isCorrectLogin(login)) throw new LogicException("Incorrect login");
        if (!password.equals(rPassword)) throw new LogicException("Passwords does not match");
        if (!Validation.isCorrectPassword(password)) throw new LogicException("Incorrect password");
        validateFields(firstName, lastName, email, telephone, birthDate);
    }

    public User buildUser(String login, String firstName, String lastName, String birthDate,
                          String email, String telephone, String gender) {
        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDate(LocalDate.parse(birthDate));
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setGender(gender);
        user.setUserRole("Common");
        user.setUserStatus("ACTIVE");
        return user;
    }

    public User buildUser(String login, String password, String firstName, String lastName, String birthDate,
                          String email, String telephone, String gender) {
        User user = buildUser(login, firstName, lastName, birthDate, email, telephone, gender);
        user.setPassword(password);
        return user;
    }

    public User buildUser(String login, String firstName, String lastName, String birthDate,
                          String email, String telephone, String gender, int userId) {
        User user = buildUser(login, firstName, lastName, birthDate, email, telephone, gender);
        user.setUserID(userId);
        return user;
    }

    public User buildUser(String login, String firstName, String lastName, String birthDate,
                          String email, String telephone, String gender, String password, int userId) {
        User user = buildUser(login, firstName, lastName, birthDate, email, telephone, gender);
        user.setUserID(userId);
        user.setPassword(password);
        return user;
    }

    public String generatePassword() {
        int amountChars = 8;
        StringBuilder password = new StringBuilder();
        int randomUpperCaseIndex = generateRandom(0, 7);
        for (int i = 0; i <= amountChars; i++) {
            if (i == randomUpperCaseIndex) {
                password.append(String.valueOf(generateRandomChar()).toUpperCase());
            }
            password.append(generateRandomChar());
        }
        password.append(generateRandom(0, 9));
        password.append(generateRandom(0, 9));
        return password.toString();
    }


    public char generateRandomChar() {
        int min = 97;
        int max = 121;
        int random = generateRandom(min, max);
        return (char) random;
    }

    public int generateRandom(int min, int max) {
        return (int) ((max - min) * Math.random() + min);
    }
}