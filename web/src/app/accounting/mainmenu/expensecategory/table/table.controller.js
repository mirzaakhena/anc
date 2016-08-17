(function() {
    'use strict';

    angular
        .module('web')
        .controller('ExpenseCategoryController', ExpenseCategoryController);

    /** @ngInject */
    function ExpenseCategoryController($uibModal, $state, $scope, ExpenseCategoryService) {

        $scope.create = create;

        function reload() {
            $scope.expenseCategories = ExpenseCategoryService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/expenseCategory/input/input.html',
                controller: 'ExpenseCategoryInputController',
            }).result.then(reload);
        }

        reload();

    }


})();
