(function() {
    'use strict';

    angular
        .module('web')
        .factory('AccountPayableService', AccountPayableService);

    /** @ngInject */
    function AccountPayableService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/accountpayable/:accountpayableId');
    }
    
})();
