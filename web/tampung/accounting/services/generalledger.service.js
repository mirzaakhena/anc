(function() {
    'use strict';

    angular
        .module('web')
        .factory('GeneralLedgerService', GeneralLedgerService);

    /** @ngInject */
    function GeneralLedgerService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/journalaccountbalance/:accountId', {
            date: '@date'
        });
    }
})();
