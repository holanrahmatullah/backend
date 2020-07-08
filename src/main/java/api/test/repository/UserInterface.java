package api.test.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import api.test.model.User;

public interface UserInterface {

    List<User> findAll(int page, int limit);

    String save(@NotNull User user);

    Long size();

    User findById(@NotNull Long id);

    boolean update(@NotNull Long id, String username, String password);

    boolean destroy(@NotNull Long id);
}