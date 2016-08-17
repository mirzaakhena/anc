(function() {
    'use strict';

    angular
        .module('web')
        .controller('ExpenseCategoryInputController', ExpenseCategoryInputController);

    /** @ngInject */
    function ExpenseCategoryInputController($uibModal, $uibModalInstance, $scope, ExpenseCategoryService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            ExpenseCategoryService.save($scope.expenseCategory, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
