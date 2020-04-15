angular.module("redditUi.services").factory('PostService',
    ["$http", function ($http) {
        let service = {};
        service.loadComments = function (id) {
            const url = "api/p/" + id + "/comments";
            return $http.get(url);
        };
        return service;
    }]
);



