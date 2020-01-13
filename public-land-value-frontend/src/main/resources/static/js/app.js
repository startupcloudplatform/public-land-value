'use strict';

// Define the `publicLandValueApp` module
var app = angular.module('publicLandValueApp', [
    'ngRoute',
    'ui.bootstrap',
    'ngResource'
]);

app.config(['$routeProvider', '$httpProvider', '$locationProvider',
    function($routeProvider) {
        $routeProvider
            .when('/main', {
                templateUrl: '/views/main.html',
                controller: 'mapController as map'
            })
            .otherwise({
                redirectTo: '/main'
            });
    }
]);