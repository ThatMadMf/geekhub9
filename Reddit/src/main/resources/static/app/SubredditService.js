angular.module("redditUi.services").factory('SubredditService',
    ["$http", function ($http) {
        let service = {};
        service.getPopular = function () {
            const url = "api/r/popular";
            return $http.get(url);
        };

        service.getSubreddits = function () {
            const url = "api/r";
            return $http.get(url);
        };

        return service;
    }]
);



