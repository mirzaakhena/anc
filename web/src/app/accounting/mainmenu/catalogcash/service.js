(function() {
    'use strict';

    angular
        .module('web')
        .factory('CatalogCashService', CatalogCashService);

    /** @ngInject */
    function CatalogCashService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/catalogcash/:catalogcashId');
    }
})();
