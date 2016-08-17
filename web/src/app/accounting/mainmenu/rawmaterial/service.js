(function() {
    'use strict';

    angular
        .module('web')
        .factory('RawMaterialService', RawMaterialService);

    /** @ngInject */
    function RawMaterialService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/rawmaterial/:rawmaterialId', {
            name: '@name'
        });
    }
})();
