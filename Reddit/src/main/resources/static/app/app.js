'use strict';

var demoApp = angular.module('demo', [ 'ui.bootstrap', 'demo.controllers',
    'demo.services' ]);
demoApp.constant("CONSTANTS", {
    getUser : "/api/user",
    getUserPosts : "/api/user/posts",
    registration : "/registration",
    getPopular : "api/r/popular",
    getComments : "api/r/"
});