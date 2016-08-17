(function() {
    'use strict';

    angular
        .module('web')
        .factory('AccountUnitService', AccountUnitService);

    /** @ngInject */
    function AccountUnitService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/accountunit', {            
            accountId: '@accountId'    
        });
    }
})();