app.controller('ConsoleContextCtrl', ['$scope', function () {
    $('.menu.item').tab();
}]);
app.controller('MemoryContextCtrl', ['$scope', '$location', function ($scope, $location) {
    $('.message .close').on('click', function () {
        $(this).closest('.message').transition('fade');
    });
    $('.ui.submit').on('click', function () {
        $('#gc').submit();
    });
    $scope.visible = false;
    if ($location.search().success) {
        $scope.visible = true;
    }
}]);