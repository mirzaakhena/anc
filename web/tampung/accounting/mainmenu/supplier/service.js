(function() {
    'use strict';

    angular
        .module('web')
        .factory('SupplierService', SupplierService);

    /** @ngInject */
    function SupplierService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/supplier/:supplierId');
    }
})();
