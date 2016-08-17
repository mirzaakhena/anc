(function() {
    'use strict';

    angular
        .module('web')
        .factory('CatalogBankService', CatalogBankService);

    /** @ngInject */
    function CatalogBankService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/catalogbank/:catalogbankId');
    }
})();
