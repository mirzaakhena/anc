(function() {
    'use strict';

    angular
        .module('web')
        .factory('AccountBalanceService', AccountBalanceService)
        .factory('AccountBalanceChildService', AccountBalanceChildService);

    /** @ngInject */
    function AccountBalanceService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/accountbalance/:accountId', {
            date:'@date'
        });
    }

    /** @ngInject */
    function AccountBalanceChildService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/accountbalance/:accountId/child', {
            date:'@date'
        });
    }
})();
