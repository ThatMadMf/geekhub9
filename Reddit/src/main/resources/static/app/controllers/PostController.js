var module = angular.module('redditUi.controllers');
module.controller("PostController", ["$scope", "PostService", "$routeParams",
    function ($scope, PostService, $routeParams) {

        $scope.getComments = function () {
            PostService.loadComments($routeParams.id)
                .then(function (value) {
                        $scope.comments = value.data;
                    },
                    function error(response) {
                        console.log(response);
                    });
        };
    }]
);
