package web.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.models.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserDao dao;

    public UserServiceImp(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public List<User> getList() {
        return dao.getList();
    }

    @Override
    public User getById(long id) {
        return dao.getById(id);
    }

    @Transactional
    @Override
    public void store(User user) {
        dao.store(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Transactional
    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
