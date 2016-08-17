(function() {
    'use strict';

    angular
        .module('web')
        .controller('CashInputController', CashInputController);

    /** @ngInject */
    function CashInputController($scope, $uibModalInstance, CashService) {

        $scope.save = save;
        $scope.cancel = cancel;

        $scope.cash = {};

        function save() {
            CashService.save($scope.cash, closeModal);
        };

        function closeModal() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }
})();
