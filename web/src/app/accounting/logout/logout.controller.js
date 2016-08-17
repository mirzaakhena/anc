(function() {
    'use strict';

    angular
        .module('web')
        .controller('LogoutController', LogoutController);

    /** @ngInject */
    function LogoutController($scope, $resource) {

        $scope.submit = submit;

        function submit() {
            $resource('/api/logout').save(function () {
                console.log('logout berhasil');
                $state.go('login');
            });
        }

    }
})();
