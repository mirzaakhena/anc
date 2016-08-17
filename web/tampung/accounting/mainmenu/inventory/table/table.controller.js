(function() {
    'use strict';

    angular
        .module('web')
        .controller('InventoryController', InventoryController);

    /** @ngInject */
    function InventoryController($uibModal, $state, $scope, InventoryService) {

        $scope.newInventory = newInventory;

        function newInventory() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/inventory/input/input.html',
                controller: 'InputInventoryController'                
            });
        }

        function reload() {
            var inventories = InventoryService.query(function() {
                $scope.inventories = inventories;
            });
        }

        reload();

        function onSubAccountClicked(account) {

            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/inventory/stok/inventory.html',
                controller: 'InventoryBalanceController',
                size: 'lg',
                resolve: {
                    account: function() {
                        return account;
                    }
                }
            });

        }

        $scope.onSubAccountClicked = onSubAccountClicked;

        function ok() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
