(function() {
    'use strict';

    angular
        .module('web')
        .factory('ExpenseCategoryService', ExpenseCategoryService);

    /** @ngInject */
    function ExpenseCategoryService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/expensecategory/:expensecategoryId');
    }
})();
