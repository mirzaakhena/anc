(function() {
    'use strict';

    angular
        .module('web')
        .controller('RoleController', RoleController);

    /** @ngInject */
    function RoleController($scope, $resource) {

        $scope.submit = submit;

        function submit() {
            
            $resource('/role').save($scope.role, function () {
                console.log('role berhasil didaftarkan');
                $state.go('app.role');
            });

        }

    }
})();
