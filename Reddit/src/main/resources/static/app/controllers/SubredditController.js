var module = angular.module('redditUi.controllers');
module.controller("SubredditController", ["$scope", "SubredditService",
    function ($scope, SubredditService) {

        $scope.getPopular = function () {
            SubredditService.getPopular()
                .then(function (value) {
                        $scope.posts = value.data;
                    },
                    function error(response) {
                        console.log(response);
                    });
        };
        $scope.getAllSubreddits = function () {
            SubredditService.getSubreddits()
                .then(function (value) {
                        $scope.subreddits = value.data;
                    },
                    function error(response) {
                        console.log(response);
                    });
        };
    }]
);
