var redditUi = angular.module("redditUi", ["ui.bootstrap", "ngRoute", "redditUi.controllers", "redditUi.services",
    "ngMessages"]);
redditUi.config(function ($routeProvider) {
    $routeProvider
        .when("/user", {
            templateUrl: "views/user.html",
            controller: "UserController",
        })
        .otherwise({
            template: "<h1>Nothing</h1><p>Nothing has been selected</p>"
        });
});
