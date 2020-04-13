var redditUi = angular.module("redditUi", ["ui.bootstrap", "ngRoute", "redditUi.controllers", "redditUi.services",
    "ngMessages"]);
var services = angular.module("redditUi.services", []);
var controllers = angular.module('redditUi.controllers', []);

redditUi.config(function ($routeProvider) {
    $routeProvider
        .when("/user", {
            templateUrl: "views/user.html",
            controller: "UserController",
        })
        .when("/popular", {
            templateUrl: "views/postList.html",
            controller: "SubredditController",
            post: '='
        })
        .otherwise({
            template: "<h1>Nothing</h1><p>Nothing has been selected</p>"
        });
});
