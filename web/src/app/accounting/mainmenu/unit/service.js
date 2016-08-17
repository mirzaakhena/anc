(function() {
    'use strict';

    angular
        .module('web')
        .factory('UnitService', UnitService);

    /** @ngInject */
    function UnitService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/unit/:unitId');
    }
})();
