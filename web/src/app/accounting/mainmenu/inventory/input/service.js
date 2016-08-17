(function() {
    'use strict';

    angular
        .module('web')
        .factory('InventoryUnassignedService', InventoryUnassignedService);

    /** @ngInject */
    function InventoryUnassignedService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/inventorybalance/unassigned', {
            date:'@date'
        });

    }
})();
