(function() {
    'use strict';

    angular
        .module('web')
        .controller('CustomerController', CustomerController);

    /** @ngInject */
    function CustomerController($uibModal, $state, $scope, CustomerService) {

        $scope.create = create;

        function reload() {
            $scope.customers = CustomerService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/customer/input/input.html',
                controller: 'CustomerInputController',
            }).result.then(reload);
        }

        reload();

    }


})();
