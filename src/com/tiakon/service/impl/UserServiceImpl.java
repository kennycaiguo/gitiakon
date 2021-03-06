package com.tiakon.service.impl;

import com.tiakon.dao.UserDao;
import com.tiakon.dao.impl.UserDaoImpl;
import com.tiakon.entity.User;
import com.tiakon.service.UserService;
import com.tiakon.utils.JDBCUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Hoictas on 2017/8/8.
 */
public class UserServiceImpl implements UserService {
    private Connection connection = JDBCUtil.getConnection();
    private UserDao userDao = new UserDaoImpl();
    private User resultSetUser = null;

    private boolean flag = false;

    @Override
    public User login(User user) throws SQLException {
        try {
            connection.setAutoCommit(false);
            resultSetUser = userDao.login(connection, user);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, null, connection);
        }
        return resultSetUser;
    }

    @Override
    public User userPreSave(User currentUser) throws SQLException, IOException {
        try {
            connection.setAutoCommit(false);
            resultSetUser = userDao.userPreSave(connection, currentUser);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, null, connection);
        }
        return resultSetUser;
    }

    @Override
    public boolean userSave(User modUser) throws SQLException {
        try {
            connection.setAutoCommit(false);
            flag = userDao.userSave(connection, modUser);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, null, connection);
        }
        return flag;
    }
}
