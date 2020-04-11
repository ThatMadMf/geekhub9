'use strict';

var module = angular.module('demo.controllers', []);
module.controller("PostController", ["$scope", "PostService",
    function ($scope, PostService) {

        $scope.getPopularPosts = function () {
            PostService.getPopular()
                .then(function (value) {
                        $scope.posts = value.data;
                    },
                    function error(response) {

                    });
        };
    }]);