package api.test.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import api.test.model.UserInput;

public interface UserInputInterface {

    List<UserInput> findAll(int page, int limit);

    String save(@NotNull UserInput userInput);

    Long size();

    UserInput findById(@NotNull Long id);

    boolean update(@NotNull Long id, String user_name, String user_password, String user_logo);

    boolean destroy(@NotNull Long id);
}