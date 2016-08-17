(function() {
    'use strict';

    angular
        .module('web')
        .factory('AccountReceivableService', AccountReceivableService);

    /** @ngInject */
    function AccountReceivableService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/accountreceivable/:accountreceivableId');
    }
    
})();
