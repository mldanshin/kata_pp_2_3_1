package web.services;

import web.models.User;

import java.util.List;

public interface UserService {
    List<User> getList();
    User getById(long id);
    void store(User user);
    void update(User user);
    void delete(long id);
}
