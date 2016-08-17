(function() {
    'use strict';

    angular
        .module('web')
        .controller('IncomeCategoryController', IncomeCategoryController);

    /** @ngInject */
    function IncomeCategoryController($uibModal, $state, $scope, IncomeCategoryService) {

        $scope.create = create;

        function reload() {
            $scope.incomeCategories = IncomeCategoryService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/incomecategory/input/input.html',
                controller: 'IncomeCategoryInputController',
            }).result.then(reload);
        }

        reload();

    }


})();
