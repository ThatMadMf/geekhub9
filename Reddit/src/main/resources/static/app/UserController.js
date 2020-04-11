'use strict';

var module = angular.module('demo.controllers', []);
module.controller("UserController", ["$scope", "UserService", "$window",
    function ($scope, UserService, $window) {

        $scope.getUser = function () {
            UserService.getUser()
                .then(function (value) {
                        $scope.user = value.data;
                    },
                    function error(response) {

                    });
        };

        $scope.saveUser = function () {

            UserService.saveUser($scope.user)
                .then(function success() {
                        $window.location.href = "/home";
                    },
                    function error(response) {
                        if (response.status === 409) {
                            $scope.errorMessage = response.data.message;
                        } else {
                            $scope.errorMessage = 'Error adding user!';
                        }
                        $scope.message = '';
                    });
        }
    }]);