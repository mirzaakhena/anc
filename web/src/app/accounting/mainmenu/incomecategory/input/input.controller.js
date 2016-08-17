(function() {
    'use strict';

    angular
        .module('web')
        .controller('IncomeCategoryInputController', IncomeCategoryInputController);

    /** @ngInject */
    function IncomeCategoryInputController($uibModal, $uibModalInstance, $scope, IncomeCategoryService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            IncomeCategoryService.save($scope.incomeCategory, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
