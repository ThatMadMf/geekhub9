'use strict';

angular.module('demo.services', []).factory('PostService',
    ["$http", "CONSTANTS", function ($http, CONSTANTS) {
        let service = {};
        service.getPopular = function () {
            const url = CONSTANTS.getPopular;
            return $http.get(url);
        };
        return service;
    }]);