(function() {
    'use strict';

    angular
        .module('web')
        .controller('SupplierController', SupplierController);

    /** @ngInject */
    function SupplierController($uibModal, $state, $scope, SupplierService) {

        $scope.create = create;

        function reload() {
            $scope.suppliers = SupplierService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/supplier/input/input.html',
                controller: 'SupplierInputController',
            }).result.then(reload);
        }

        reload();

    }


})();
