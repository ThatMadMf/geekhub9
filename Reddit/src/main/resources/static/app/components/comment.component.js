angular
    .module("redditUi")
    .component("comment", {
        templateUrl: "views/comment.html",
        controller: "PostController",
        bindings: {
            data: '='
        }
    });