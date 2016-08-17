(function() {
    'use strict';

    angular
        .module('web')
        .controller('BankController', BankController);

    /** @ngInject */
    function BankController($scope, $uibModal, $uibModalInstance, BankService) {

        $scope.add = add;
        $scope.ok = ok;

        function reload() {
            $scope.banks = BankService.query();
        }

        reload();

        function add() {
            $uibModal.open({
                templateUrl: 'app/accounting/masterdata/bank/input/bank-input.html',
                controller: 'BankInputController'                
            }).result.then(reload);
        }

        function ok() {
            $uibModalInstance.close();
        };

    }
})();
