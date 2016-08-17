(function() {
    'use strict';

    angular
        .module('web')
        .factory('InventoryBalanceService', InventoryBalanceService);

    /** @ngInject */
    function InventoryBalanceService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/inventorybalance/:accountId', {
            date: '@date'
        });
    }
})();
