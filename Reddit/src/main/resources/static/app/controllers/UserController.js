var module = angular.module('redditUi.controllers', []);
module.controller("UserController", ["$scope", "UserService", "$window",
    function ($scope, UserService, $window) {

        $scope.getUser = function () {
            UserService.getUser()
                .then(function (value) {
                        $scope.user = value.data;
                    },
                    function error(response) {
                        console.log(response);
                    });
        };

        $scope.saveUser = function () {

            UserService.saveUser($scope.user)
                .then(function success() {
                        $window.location.href = "/home";
                    },
                    function error(response) {
                        $scope.errorMessage = response.data.message;
                        if (response.data.messsage === undefined) {
                            $scope.errorMessage = 'Error adding user!';
                        }
                        $scope.message = '';
                    });
        }

    }]
);
