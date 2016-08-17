(function() {
    'use strict';

    angular
        .module('web')
        .factory('SaleService', SaleService);

    /** @ngInject */
    function SaleService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/sale');
    }
    
})();
