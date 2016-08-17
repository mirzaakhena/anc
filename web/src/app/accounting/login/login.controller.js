(function() {
    'use strict';

    angular
        .module('web')
        .controller('LoginController', LoginController);

    /** @ngInject */
    function LoginController($scope, $http, $state) {

        console.log('tesss');

        $scope.submit = submit;

        function submit() {
            var data =
                'j_username=' + encodeURIComponent($scope.login.username) +
                '&j_password=' + encodeURIComponent($scope.login.password) +
                '&remember-me=' + false +
                '&submit=Login';

            return $http.post('/api/authentication', data, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function(data) {
                console.log('> ' + data);
                $state.go('app.journal');
                
            }).then(function(data2) {
                console.log('>>> ' + data2);
            });
        }

    }
})();
