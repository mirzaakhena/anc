(function() {
    'use strict';

    angular
        .module('web')
        .factory('AccountService', AccountService);

    /** @ngInject */
    function AccountService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/account/:accountId', {            
            side: '@side',
            level: '@level'            
        });
    }
})();
