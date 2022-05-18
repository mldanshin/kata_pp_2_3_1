package web.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
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

    @Override
    public void store(User user) {
        dao.store(user);
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
