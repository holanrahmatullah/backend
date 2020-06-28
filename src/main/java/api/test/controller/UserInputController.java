package api.test.controller;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import io.micronaut.http.MediaType;
import api.test.model.UserInput;
import api.test.repository.UserInputInterface;
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

@Controller("/user_input")
public class UserInputController {

    private UserInputInterface repository;
    private Gson gson;

    UserInputController(UserInputInterface r) {
        this.repository = r;
        this.gson = new Gson();
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public String index(@QueryValue int page, @QueryValue int limit) {
        List<UserInput> userInput = repository.findAll(page, limit);
        final HashMap<String, Object> data = new HashMap<>();
        try {
            data.put("paga", Math.ceil(repository.size() / limit));
            data.put("status", "ok");
            data.put("message", "Data User");
            data.put("data", userInput);
            return gson.toJson(data);
        } catch (Exception e) {

            data.put("status", "error");
            data.put("message", e.getMessage());
            return gson.toJson(data);
        }
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public String save(@Body UserInput userInput) {
        return repository.save(userInput);
    }

    @Get("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String show(@PathVariable @Nullable final Long id) {
        return (new Gson().toJson(repository.findById(id)));
    }

    @Put("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathVariable @Nullable final Long id, @Body final UserInput userInput) {
        final HashMap<String, Object> data = new HashMap<>();
        if (repository.update(id, userInput.getUser_name(), userInput.getUser_password(), userInput.getUser_logo())) {
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
