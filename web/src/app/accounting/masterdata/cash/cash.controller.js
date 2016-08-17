(function() {
    'use strict';

    angular
        .module('web')
        .controller('CashController', CashController);

    /** @ngInject */
    function CashController($scope, $uibModal, $uibModalInstance, CashService) {

        $scope.add = add;
        $scope.ok = ok;

        function reload() {
            $scope.cashs = CashService.query();
        }

        reload();

        function add() {
            $uibModal.open({
                templateUrl: 'app/accounting/masterdata/cash/input/cash-input.html',
                controller: 'CashInputController'                
            }).result.then(reload);
        }

        function ok() {
            $uibModalInstance.close();
        };

    }
})();
