angular
    .module("redditUi")
    .component("post", {
        templateUrl: "views/post.html",
        controller: "SubredditController",
        bindings: {
            data: '='
        }
    });