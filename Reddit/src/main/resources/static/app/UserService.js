'use strict';

angular.module('demo.services', []).factory('UserService',
    ["$http", "CONSTANTS", function ($http, CONSTANTS) {
        let service = {};
        service.getUser = function () {
            const url = CONSTANTS.getUser;
            return $http.get(url);
        };

        service.getPosts = function () {

        }
        return service;
    }]);