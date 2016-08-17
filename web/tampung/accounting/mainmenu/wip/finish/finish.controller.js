(function() {
    'use strict';

    angular
        .module('web')
        .controller('WIPFinishController', WIPFinishController);

    /** @ngInject */
    function WIPFinishController($uibModal, wip, $uibModalInstance, $scope, WIPFinishService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            WIPFinishService.save({
                wipCode: wip.code,
                finishQuantity: $scope.qtyFin,
                convertionCostLabour: $scope.ccLB,
                convertionCostOverhead: $scope.ccOH
            }, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
