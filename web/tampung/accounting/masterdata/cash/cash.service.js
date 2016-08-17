(function() {
    'use strict';

    angular
        .module('web')
        .factory('CashService', CashService);

    /** @ngInject */
    function CashService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/cash');
    }
    
})();
