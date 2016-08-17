(function() {
    'use strict';

    angular
        .module('web')
        .factory('BankService', BankService);

    /** @ngInject */
    function BankService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/bank');
    }
    
})();
