(function() {
    'use strict';

    angular
        .module('web')
        .controller('SupplierInputController', SupplierInputController);

    /** @ngInject */
    function SupplierInputController($uibModal, $uibModalInstance, $scope, SupplierService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            SupplierService.save($scope.supplier, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
