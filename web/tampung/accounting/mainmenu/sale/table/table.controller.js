(function() {
    'use strict';

    angular
        .module('web')
        .controller('SaleController', SaleController);

    /** @ngInject */
    function SaleController($uibModal, $state, $scope, SaleService) {

        $scope.create = create;

        function reload() {
            $scope.items = SaleService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/sale/input/saleinput.html',
                controller: 'SaleInputController',
                size:'lg'
            }).result.then(reload);
        }

        reload();

    }


})();
