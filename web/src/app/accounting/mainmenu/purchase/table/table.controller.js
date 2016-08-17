(function() {
    'use strict';

    angular
        .module('web')
        .controller('PurchaseController', PurchaseController);

    /** @ngInject */
    function PurchaseController($uibModal, $state, $scope, PurchaseService) {

        $scope.create = create;

        function reload() {
            $scope.items = PurchaseService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/purchase/input/purchaseinput.html',
                controller: 'PurchaseInputController',
                size:'lg'
            }).result.then(reload);
        }

        reload();

    }


})();
