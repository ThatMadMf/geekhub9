angular
    .module("redditUi")
    .component("subreddit", {
        templateUrl: "views/subreddit.html",
        controller: "SubredditController",
        bindings: {
            data: '='
        }
    });