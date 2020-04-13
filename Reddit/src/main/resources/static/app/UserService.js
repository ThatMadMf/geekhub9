angular.module("redditUi.services").factory('UserService',
    ["$http", function ($http) {
        let service = {};
        service.getUser = function () {
            const url = "api/user";
            return $http.get(url);
        };

        service.saveUser = function (user) {
            return $http({
                method: 'POST',
                url: "/registration",
                params: {
                    email: user.email, password: user.password,
                    login: user.login, matchingPassword: user.matchingPassword
                },
                headers: 'Accept:application/json'
            });
        };

        return service;
    }]
);



