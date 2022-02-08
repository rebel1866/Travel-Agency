package by.epamtc.stanislavmelnikov.dao.sqlgenerator;

public interface Action {
    void doAction(StringBuilder sql, String key, String value);
}
