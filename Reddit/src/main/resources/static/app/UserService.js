'use strict';

angular.module('demo.services', []).factory('UserService',
    ["$http", "CONSTANTS", function ($http, CONSTANTS) {
        let service = {};
        service.getUser = function () {
            const url = CONSTANTS.getUser;
            return $http.get(url);
        };

        service.saveUser = function saveUser(user) {
            return $http({
                method: 'POST',
                url: CONSTANTS.registration,
                params: {
                    email: user.email, password: user.password,
                    login: user.login, matchingPassword: user.matchingPassword
                },
                headers: 'Accept:application/json'
            });
        };

        return service;
    }]);