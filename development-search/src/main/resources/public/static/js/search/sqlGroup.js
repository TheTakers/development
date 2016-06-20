angular.module('app').controller('sqlGroupCtrl', function($scope,$http) {
	$scope.pagination = {};
	$scope.queryparams = {};
	$scope.search = function(){
		  $scope.$broadcast("goto");  
	}
});