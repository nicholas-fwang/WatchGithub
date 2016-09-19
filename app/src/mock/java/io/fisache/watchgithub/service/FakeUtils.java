package io.fisache.watchgithub.service;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.RepositoryResponse;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.model.UserResponse;

public class FakeUtils {

    public static UserResponse convertUserToResponse(User user) {
        return new UserResponse(user.id, user.login, user.name, user.avatar_url,
                user.email, user.followers, user.type);
    }

    public static User converResponseToUser(UserResponse userResponse) {
        return new User(userResponse.id, userResponse.login, userResponse.name, userResponse.avatar_url,
                userResponse.email, userResponse.followers, userResponse.type, "");
    }

    public static List<RepositoryResponse> convertReposToResponses(List<Repository> repositories) {
        List<RepositoryResponse> responses = new ArrayList<>();
        for(Repository repository : repositories) {
            RepositoryResponse response = new RepositoryResponse();
            response.id = repository.id;
            response.name = repository.name;
            response.html_url = repository.html_url;
            response.description = repository.desc;
            response.stargazers_count = repository.star_count;
            response.forks_count = repository.fork_count;
            response.fork = repository.fork;
            response.pushed_at = repository.pushed_at;
            responses.add(response);
        }
        return responses;
    }

    public static List<Repository> convertResponsesToRepos(List<RepositoryResponse> responses) {
        List<Repository> repositories = new ArrayList<>();
        for(RepositoryResponse response : responses) {
            Repository repository = new Repository();
            repository.id = response.id;
            repository.name = response.name;
            repository.html_url = response.html_url;
            repository.desc = response.description;
            repository.star_count = response.stargazers_count;
            repository.fork_count = response.forks_count;
            repository.fork = response.fork;
            repository.pushed_at = response.pushed_at;
            repositories.add(repository);
        }
        return repositories;
    }
}
