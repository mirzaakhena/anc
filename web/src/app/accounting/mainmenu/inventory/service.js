(function() {
    'use strict';

    angular
        .module('web')
        .factory('InventoryService', InventoryService);

    /** @ngInject */
    function InventoryService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/parentinventory');
    }
})();
