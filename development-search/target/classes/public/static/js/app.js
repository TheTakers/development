var app = angular.module('app', ['ngRoute', 'ngResource']);

app.config(['$routeProvider', '$httpProvider', function ($route) {
    $route
        .when('/', {templateUrl: '/home'})
        .when('/system/user', {templateUrl: '/system/user'})
        .when('/console/context', {templateUrl: '/console/context', controller: 'ConsoleContextCtrl'})
        .when('/console/memory', {templateUrl: '/console/memory', controller: 'MemoryContextCtrl'});
}]);

app.controller('NavCtrl', ['$scope', function ($scope) {
    $scope.$root.$on('nav:head', function (event, header) {
        $scope.header = header;
    });
    $scope.$root.$on('nav:crumb', function (event, breadcrumb) {
        $scope.breadcrumb = breadcrumb;
    });
    $scope.is = function (header) {
        return $scope.header === header;
    };
}]);
