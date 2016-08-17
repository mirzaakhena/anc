(function() {
    'use strict';

    angular
        .module('web')
        .factory('IncomeCategoryService', IncomeCategoryService);

    /** @ngInject */
    function IncomeCategoryService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/incomecategory/:incomecategoryId');
    }
})();
