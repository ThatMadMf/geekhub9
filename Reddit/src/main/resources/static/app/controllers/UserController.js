var module = angular.module('redditUi.controllers');
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
                .then(function success(response) {
                        $window.location.href = "/";
                    },
                    function error(error) {
                        $scope.errorMessage = error.data.message;
                        if (error.data.messsage === undefined) {
                            $scope.errorMessage = 'Error adding user!';
                        }
                        $scope.message = '';
                    });
        }

    }]
);
