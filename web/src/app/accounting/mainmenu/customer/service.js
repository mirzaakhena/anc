(function() {
    'use strict';

    angular
        .module('web')
        .factory('CustomerService', CustomerService);

    /** @ngInject */
    function CustomerService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/customer/:customerId');
    }
})();
