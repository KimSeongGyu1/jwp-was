package controller;

import db.DataBase;
import view.ModelAndView;
import view.RedirectView;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateUserController extends AbstractController {

    public static final String PATH = "/user/create";

    @Override
    ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        save(httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));

        return new ModelAndView(new RedirectView("/"));
    }

    private void save(String userId, String password, String name, String email) {
        if (DataBase.findUserById(userId) == null) {
            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            return;
        }

        throw new IllegalArgumentException();
    }
}