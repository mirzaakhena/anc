(function() {
    'use strict';

    angular
        .module('web')
        .controller('BankInputController', BankInputController);

    /** @ngInject */
    function BankInputController($scope, $uibModalInstance, BankService) {

        $scope.save = save;
        $scope.cancel = cancel;

        $scope.bank = {};

        function save() {
            BankService.save($scope.bank, closeModal);
        };

        function closeModal() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }
})();
