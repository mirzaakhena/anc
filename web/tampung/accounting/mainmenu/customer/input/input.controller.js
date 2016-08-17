(function() {
    'use strict';

    angular
        .module('web')
        .controller('CustomerInputController', CustomerInputController);

    /** @ngInject */
    function CustomerInputController($uibModal, $uibModalInstance, $scope, CustomerService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            CustomerService.save($scope.customer, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
