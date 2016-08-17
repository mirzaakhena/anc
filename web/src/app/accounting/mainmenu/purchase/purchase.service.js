(function() {
    'use strict';

    angular
        .module('web')
        .factory('PurchaseService', PurchaseService);

    /** @ngInject */
    function PurchaseService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/purchase');
    }
    
})();
