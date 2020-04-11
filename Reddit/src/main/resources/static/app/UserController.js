'use strict';

var module = angular.module('demo.controllers', []);
module.controller("UserController", ["$scope", "UserService",
    function ($scope, UserService) {

        UserService.getUser().then(function (value) {
            $scope.user = value.data;
        });
    }]);