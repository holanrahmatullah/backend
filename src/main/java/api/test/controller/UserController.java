package api.test.controller;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import io.micronaut.http.MediaType;
import api.test.model.User;
import api.test.repository.UserInterface;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.reactivex.annotations.Nullable;

@Controller("/users")
public class UserController {

    private UserInterface repository;
    private Gson gson;

    UserController(UserInterface r) {
        this.repository = r;
        this.gson = new Gson();
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public String index(@QueryValue int page, @QueryValue int limit) {
        List<User> user = repository.findAll(page, limit);
        final HashMap<String, Object> data = new HashMap<>();
        try {
            data.put("paga", Math.ceil(repository.size() / limit));
            data.put("status", "ok");
            data.put("message", "Data User");
            data.put("data", user);
            return gson.toJson(data);
        } catch (Exception e) {

            data.put("status", "error");
            data.put("message", e.getMessage());
            return gson.toJson(data);
        }
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public String save(@Body User user) {
        return repository.save(user);
    }

    @Get("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String show(@PathVariable @Nullable final Long id) {
        return (new Gson().toJson(repository.findById(id)));
    }

    @Put("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathVariable @Nullable final Long id, @Body final User user) {
        final HashMap<String, Object> data = new HashMap<>();
        if (repository.update(id, user.getUsername(), user.getPassword())) {
            data.put("status", "ok");
        } else {
            data.put("status", "fail");
        }
        return (new Gson().toJson(data));
    }

    @Delete("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String destroy(@PathVariable @Nullable final Long id) {
        final HashMap<String, Object> data = new HashMap<>();
        if (repository.destroy(id)) {
            data.put("status", "ok");
        } else {
            data.put("status", "fail");
        }
        return (new Gson().toJson(data));
    }

}
