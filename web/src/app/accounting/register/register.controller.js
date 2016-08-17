(function() {
    'use strict';

    angular
        .module('web')
        .controller('RegisterController', RegisterController);

    /** @ngInject */
    function RegisterController($scope, $resource, $state) {

        $scope.submit = submit;

        function submit() {

            $resource('/register').save($scope.register, function () {
                console.log('register berhasil');
                $state.go('app.account');
            });

        }

    }
})();
